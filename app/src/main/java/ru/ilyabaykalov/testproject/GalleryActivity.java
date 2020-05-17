package ru.ilyabaykalov.testproject;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.ilyabaykalov.testproject.utils.HTTPService;

public class GalleryActivity extends AppCompatActivity {

    @BindView(R.id.photo_recycler_view)
    RecyclerView photoRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);

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
                        if (response.body() != null) {
                            GalleryViewAdapter adapter = new GalleryViewAdapter(response.body().getPhotos(), position -> {
                                runOnUiThread(() -> {
                                    int photoId = response.body().getPhotos().get(position).getPhotoId();
                                    Toast.makeText(getApplicationContext(),
                                            "photo_id: " + photoId,
                                            Toast.LENGTH_SHORT).show();
                                });
                            });

                            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
                            layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
                            photoRecyclerView.setLayoutManager(layoutManager);

                            photoRecyclerView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<PhotoResponse> call, Throwable t) {

                    }
                });
    }
}
