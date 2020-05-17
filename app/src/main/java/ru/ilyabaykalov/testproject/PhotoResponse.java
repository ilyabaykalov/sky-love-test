package ru.ilyabaykalov.testproject;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhotoResponse {

    @SerializedName("photos")
    private List<Photo> photos;

    public static class Photo {
        @SerializedName("photo_id")
        private int photoId;

        @SerializedName("photo")
        private String link;

        public int getPhotoId() {
            return photoId;
        }

        public String getLink() {
            return link;
        }
    }
}
