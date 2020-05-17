package ru.ilyabaykalov.testproject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface API {
    @Multipart
    @POST("/v1/login/email")
    Call<UserResponse> authorize(@PartMap Map<String, RequestBody> params);

    @GET("/v1/users/photos")
    Call<PhotoResponse> getPhotos(@Header("authentication") String token, @Query("user_id") int userId);
}
