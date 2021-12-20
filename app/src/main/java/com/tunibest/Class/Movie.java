package com.tunibest.Class;

import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

public class Movie {
    public String Name;
    public Bitmap image;

    public Movie() {
    }

    public Movie(String name, Bitmap image) {
        Name = name;
        this.image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
