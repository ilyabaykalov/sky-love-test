package ru.ilyabaykalov.testproject;

import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @SerializedName("user")
    private User user;

    public static class User {
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
