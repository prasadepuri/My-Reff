package me.prasad.myreff;

class AppData {
    String title;
    String link;
    String date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public AppData(String title, String link, String date, String category, String imageurl, long timestamp) {
        this.title = title;
        this.link = link;
        this.date = date;
        this.category = category;
        this.imageurl = imageurl;
        this.timestamp = timestamp;
    }

    String category;
    String imageurl;
    long timestamp;
}
