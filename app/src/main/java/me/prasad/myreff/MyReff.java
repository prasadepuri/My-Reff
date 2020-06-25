package me.prasad.myreff;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "news_table")
public class MyReff {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "title")
    public String title;

    @NonNull
    @ColumnInfo(name="url")
    public String url;

    public MyReff(@NonNull String title, @NonNull String url, @NonNull String category, long timestamp, String imageurl) {
        this.title = title;
        this.url = url;
        this.category = category;
        this.timestamp = timestamp;
        this.imageurl = imageurl;
    }

    @NonNull

    public String getCategory() {
        return category;
    }

    public void setCategory(@NonNull String category) {
        this.category = category;
    }

    @NonNull

    @ColumnInfo(name="category")
    public String category;

    @NonNull
    @ColumnInfo(name="timestamp")
    public long timestamp;

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    public void setUrl(@NonNull String url) {
        this.url = url;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    @ColumnInfo(name="imageurl")
    public String imageurl;



}
