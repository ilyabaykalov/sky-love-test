package ru.ilyabaykalov.testproject;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserResponse {

    @SerializedName("user")
    private User user;

    public User getUser() {
        return user;
    }

    public static class User implements Serializable {
        @SerializedName("user_id")
        private int userId;

        @SerializedName("token")
        private String token;

        public int getUserId() {
            return userId;
        }

        public String getToken() {
            return token;
        }
    }
}
