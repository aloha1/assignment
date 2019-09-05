package com.yunwen.assignment.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "data_table")
public class Data {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "userId")
    private int userId;

    @NonNull
    @ColumnInfo(name = "largeImageURL")
    private String largeImageURL;

    @NonNull
    @ColumnInfo(name = "webformatHeight")
    private long webformatHeight;

    @NonNull
    @ColumnInfo(name = "webformatWidth")
    private long webformatWidth;

    @NonNull
    @ColumnInfo(name = "likes")
    private int likes;

    @NonNull
    @ColumnInfo(name = "imageWidth")
    private long imageWidth;

    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "comments")
    private int comments;

    @NonNull
    @ColumnInfo(name = "imageHeight")
    private long imageHeight;

    @NonNull
    @ColumnInfo(name = "webformatURL")
    private String webformatURL;

    @NonNull
    @ColumnInfo(name = "tags")
    private String tags;

    @NonNull
    @ColumnInfo(name = "user")
    private String user;

    @NonNull
    @ColumnInfo(name = "userImageURL")
    private String userImageURL;

    public Data(int userId, String largeImageURL, long webformatHeight,long webformatWidth,
                int likes,long imageWidth,int id,int comments,long imageHeight, String webformatURL,
                String tags,String user, String userImageURL) {
        this.userId = userId;
        this.largeImageURL = largeImageURL;
        this.webformatHeight = webformatHeight;
        this.webformatWidth = webformatWidth;
        this.likes = likes;
        this.imageWidth = imageWidth;
        this.id = id;
        this.comments = comments;
        this.imageHeight = imageHeight;
        this.webformatURL = webformatURL;
        this.tags = tags;
        this.user = user;
        this.userImageURL = userImageURL;
    }

    @NonNull
    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @NonNull
    public long getWebformatHeight() {
        return webformatHeight;
    }

    public void setWebformatHeight(long webformatHeight) {
        this.webformatHeight = webformatHeight;
    }

    @NonNull
    public String getLargeImageURL() {
        return largeImageURL;
    }

    public void setLargeImageURL(@NonNull String largeImageURL) {
        this.largeImageURL = largeImageURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    @NonNull
    public String getUser() {
        return user;
    }

    public void setUser(@NonNull String user) {
        this.user = user;
    }

    public long getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(long imageHeight) {
        this.imageHeight = imageHeight;
    }

    public long getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(long imageWidth) {
        this.imageWidth = imageWidth;
    }

    public long getWebformatWidth() {
        return webformatWidth;
    }

    public void setWebformatWidth(long webformatWidth) {
        this.webformatWidth = webformatWidth;
    }

    @NonNull
    public String getTags() {
        return tags;
    }

    public void setTags(@NonNull String tags) {
        this.tags = tags;
    }

    @NonNull
    public String getUserImageURL() {
        return userImageURL;
    }

    public void setUserImageURL(@NonNull String userImageURL) {
        this.userImageURL = userImageURL;
    }

    @NonNull
    public String getWebformatURL() {
        return webformatURL;
    }

    public void setWebformatURL(@NonNull String webformatURL) {
        this.webformatURL = webformatURL;
    }
}
