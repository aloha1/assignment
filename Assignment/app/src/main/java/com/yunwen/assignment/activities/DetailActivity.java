package com.yunwen.assignment.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yunwen.assignment.R;

public class DetailActivity extends Activity {
    private final int DEFAULT_VALUE = 0;
    private String likes, comments, imageUrl;
    private int width, height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
    }

    private void initView(){
        likes = getIntent().getStringExtra("likes");
        comments = getIntent().getStringExtra("comments");imageUrl = getIntent().getStringExtra("imageUrl");
        width = getIntent().getIntExtra("width", DEFAULT_VALUE);
        height = getIntent().getIntExtra("height", DEFAULT_VALUE);
        ImageView imagePicture = findViewById(R.id.image_picture);
        TextView textLike = findViewById(R.id.text_like);
        TextView textComments = findViewById(R.id.text_comments);
        textLike.setText(likes);
        textComments.setText(comments);
        Glide.with(this)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .override(width,height)
                .into(imagePicture);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("likes", likes);
        savedInstanceState.putString("comments", comments);
        savedInstanceState.putString("imageUrl", imageUrl);
        savedInstanceState.putInt("width", width);
        savedInstanceState.putInt("height", height);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        width = savedInstanceState.getInt("width");
        height = savedInstanceState.getInt("height");
        likes = savedInstanceState.getString("likes");
        comments = savedInstanceState.getString("comments");
        imageUrl = savedInstanceState.getString("imageUrl");
    }
}
