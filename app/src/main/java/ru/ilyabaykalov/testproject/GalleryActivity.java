package ru.ilyabaykalov.testproject;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.ilyabaykalov.testproject.utils.HTTPService;

public class GalleryActivity extends AppCompatActivity {

//    private UserResponse.User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            UserResponse.User user = (UserResponse.User) arguments.get("user");
            if (user != null) {
                getPhotos(user);
            }
        }
    }

    private void getPhotos(UserResponse.User user) {
        HTTPService.getInstance()
                .getApi()
                .getPhotos(user.getToken(), user.getUserId())
                .enqueue(new Callback<PhotoResponse>() {
                    @Override
                    public void onResponse(Call<PhotoResponse> call, Response<PhotoResponse> response) {

                    }

                    @Override
                    public void onFailure(Call<PhotoResponse> call, Throwable t) {

                    }
                });
    }
}
