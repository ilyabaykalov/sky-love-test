package ru.ilyabaykalov.testproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.ilyabaykalov.testproject.utils.AESTest;
import ru.ilyabaykalov.testproject.utils.HTTPService;

import java.util.HashMap;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.input_email)
    EditText inputEmail;

    @BindView(R.id.input_password)
    EditText inputPassword;

    @BindView(R.id.ok_button)
    Button okButton;
    View.OnClickListener getData = v -> {
        switch (inputEmail.getVisibility()) {
            case VISIBLE:
                inputEmail.setVisibility(GONE);
                inputPassword.setVisibility(VISIBLE);
                break;
            case GONE:
                sendData();
                break;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        ButterKnife.bind(this);

        okButton.setOnClickListener(getData);
    }

    private void sendData() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        String AESPassword = AESTest.encode(password);

        RequestBody rbEmail = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody rbPassword = RequestBody.create(MediaType.parse("text/plain"), AESPassword);
        RequestBody rbAppId = RequestBody.create(MediaType.parse("text/plain"), "test");

        HashMap<String, RequestBody> body = new HashMap<>();
        body.put("email", rbEmail);
        body.put("password", rbPassword);
        body.put("app_id", rbAppId);

        HTTPService.getInstance()
                .getApi()
                .authorize(body)
                .enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if (response.body() != null) {
                            Intent intent = new Intent(getApplicationContext(), GalleryActivity.class);
                            intent.putExtra("user", response.body().getUser());
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {

                    }
                });
    }
}
