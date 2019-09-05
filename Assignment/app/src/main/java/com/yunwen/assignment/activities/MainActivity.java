package com.yunwen.assignment.activities;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.yunwen.assignment.R;
import com.yunwen.assignment.room.Data;
import com.yunwen.assignment.room.DataListAdapter;
import com.yunwen.assignment.room.DataRoomDatabase;
import com.yunwen.assignment.room.DataViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static String FETCH_URL = "https://pixabay.com/api/?key=13148476-1f6e7e6df91b8170023dfcfb0&q=yellow+flowers&image_type=photo&pretty=true";
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    private DataViewModel mDataViewModel;
    private ArrayList<Data> arrayList;
    private List<Data> dataModels;
    private DataListAdapter adapter;

    private RecyclerView recyclerView;
    private boolean recyclerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        arrayList = new ArrayList<>();
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            fetchFromServer();
        }
        fetchFromRoom();
        recyclerLayout = false;
        recyclerView = findViewById(R.id.recyclerview);
        adapter = new DataListAdapter(this, arrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDataViewModel = ViewModelProviders.of(this).get(DataViewModel.class);
        mDataViewModel.getAllData().observe(this, new Observer<List<Data>>() {
            @Override
            public void onChanged(@Nullable List<Data> dataModels) {
                adapter.setData(dataModels);
            }
        });
    }

    private void fetchFromServer() {
        //pb.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.GET, FETCH_URL,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response == null) {
                    Log.d("response:", "Couldn't fetch the menu_main! Pleas try again.");
                    return;
                }
                try {

                    JsonParser jsonParser = new JsonParser();
                    JsonObject jo = (JsonObject)jsonParser.parse(response);
                    JsonArray jsonArr = jo.getAsJsonArray("hits");
                    Gson googleJson = new Gson();
                    ArrayList jsonObjList = googleJson.fromJson(jsonArr, ArrayList.class);
                    Log.d("response","List size is : "+jsonObjList.size());
                    Log.d("response","List Elements are  : "+jsonObjList.toString());
                    dataModels = new Gson().fromJson(jsonArr, new TypeToken<List<Data>>() {
                    }.getType());
                    arrayList.clear();
                    arrayList.addAll(dataModels);
                    // refreshing recycler view
                    for(Data dataSet:dataModels) {
                        mDataViewModel.insert(dataSet);
                    }
                    adapter.setData(dataModels);
                    adapter.notifyDataSetChanged();
                    saveTask();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    //This indicates that the reuest has either time out or there is no connection
                    Log.e("TAG", "Error: TimeOut or No Connection");
                } else if (error instanceof AuthFailureError) {
                    //Error indicating that there was an Authentication Failure while performing the request
                    Log.e("TAG", "Error: AuthFailureError");
                } else if (error instanceof ServerError) {
                    //Indicates that the server responded with a error response
                    Log.e("TAG", "Error: ServerError");
                } else if (error instanceof NetworkError) {
                    //Indicates that there was network error while performing the request
                    Log.e("TAG", "Error: NetworkError");
                } else if (error instanceof ParseError) {
                    // Indicates that the server response could not be parsed
                    Log.e("TAG", "Error: ParseError");
                }

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        request.setShouldCache(false);
        requestQueue.add(request);
    }


    private void fetchFromRoom() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    LiveData<List<Data>> listLiveData = DataRoomDatabase.getDatabase(MainActivity.this).dataDao().getAll();
                    arrayList.clear();
                    for (Data dataSet : listLiveData.getValue()) {
                        Data data = new Data(dataSet.getUserId(), dataSet.getLargeImageURL(),
                                dataSet.getWebformatHeight(),
                                dataSet.getWebformatWidth(),
                                dataSet.getLikes(),
                                dataSet.getImageWidth(),
                                dataSet.getId(),
                                dataSet.getComments(),
                                dataSet.getImageHeight(),
                                dataSet.getWebformatURL(),
                                dataSet.getTags(),
                                dataSet.getUser(),
                                dataSet.getUserImageURL());
                        arrayList.add(data);
                        mDataViewModel.insert(data);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                // refreshing recycler view
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
        thread.start();
    }

    private void saveTask() {
        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                for(Data dataSet:dataModels) {
                    mDataViewModel.insert(dataSet);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }

        SaveTask st = new SaveTask();
        st.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu_main; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_grid) {
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));
            return true;
        }

        if (id == R.id.action_linear) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
