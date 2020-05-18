package ru.ilyabaykalov.testproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.ShapeAppearanceModel;
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

    @BindView(R.id.background)
    ShapeableImageView background;

    @BindView(R.id.input_password)
    EditText inputPassword;

    @BindView(R.id.ok_button)
    Button okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);

        setUpView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initForm();
    }

    private void setUpView() {
        background.setShapeAppearanceModel(new ShapeAppearanceModel().withCornerSize(5));
    }

    private void initForm() {
        inputEmail.setText("");
        inputEmail.setVisibility(VISIBLE);

        inputPassword.setText("");
        inputPassword.setVisibility(GONE);
    }

    @OnClick(R.id.ok_button)
    public void onOKButtonClick() {
        switch (inputEmail.getVisibility()) {
            case VISIBLE:
                inputEmail.setVisibility(GONE);
                inputPassword.setVisibility(VISIBLE);
                break;
            case GONE:
                sendData();
                break;
        }
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
                        } else if (response.errorBody() != null) {
                            runOnUiThread(() -> {
                                initForm();
                                Toast.makeText(getApplicationContext(),
                                        "Ошибка авторизации",
                                        Toast.LENGTH_LONG).show();
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {

                    }
                });
    }
}
