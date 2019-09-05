package com.yunwen.assignment.room;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yunwen.assignment.R;
import com.yunwen.assignment.activities.DetailActivity;

import java.util.List;


public class DataListAdapter extends RecyclerView.Adapter<DataListAdapter.DataViewHolder> {

    class DataViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imagePicture;
        private final ImageView imageUser;
        private final ImageView imageLike;
        private final TextView textName;
        private final TextView textTag;
        private final TextView textLike;
        private final TextView textcomments;

        private DataViewHolder(View itemView) {
            super(itemView);
            imagePicture = itemView.findViewById(R.id.image_picture);
            imageUser = itemView.findViewById(R.id.image_user);
            imageLike = itemView.findViewById(R.id.image_like);
            textName = itemView.findViewById(R.id.text_name);
            textTag = itemView.findViewById(R.id.text_tag);
            textLike = itemView.findViewById(R.id.text_like);
            textcomments = itemView.findViewById(R.id.text_comments);
        }
    }

    private Context context;
    private final LayoutInflater mInflater;
    private List<Data> mData;

    public DataListAdapter(Context context, List<Data> arrayList) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.mData = arrayList;
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        try {
            Data current = mData.get(position);
            final String strLikes = String.valueOf(current.getLikes());
            holder.textLike.setText(strLikes);
            final String strComments = String.valueOf(current.getComments());
            holder.textcomments.setText(strComments);
            holder.textName.setText(current.getUser());
            holder.textTag.setText(current.getTags());
            final String strImageUrl = current.getLargeImageURL();
            final int width = (int) current.getWebformatWidth();
            final int height = (int) current.getWebformatHeight();
            Glide.with(context)
                    .load(strImageUrl)
                .error(R.drawable.picture_error)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .override(width,height )
                    .into(holder.imagePicture);
            String userImageUrl = current.getUserImageURL();
            Glide.with(context)
                    .load(userImageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .override(100, 100)
                    .into(holder.imageUser);
            holder.imagePicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("likes", strLikes);
                    intent.putExtra("comments", strComments);
                    intent.putExtra("imageUrl", strImageUrl);
                    intent.putExtra("width",width);
                    intent.putExtra("height",height);
                    context.startActivity(intent);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setData(List<Data> dataList) {
        mData = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}


