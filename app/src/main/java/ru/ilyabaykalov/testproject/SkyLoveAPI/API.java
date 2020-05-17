package ru.ilyabaykalov.testproject.SkyLoveAPI;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;
import ru.ilyabaykalov.testproject.PhotoResponse;
import ru.ilyabaykalov.testproject.UserResponse;

import java.util.Map;

public interface API {
    @Multipart
    @POST("/v1/login/email")
    Call<UserResponse> authorize(@PartMap Map<String, RequestBody> params);

    @GET("/v1/users/photos/{userId}")
    Call<PhotoResponse> getPhotos(@Header("authentication") String token, @Path("user_id") int userId);
}
