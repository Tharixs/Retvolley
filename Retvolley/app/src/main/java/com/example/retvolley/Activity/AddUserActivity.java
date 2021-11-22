package com.example.retvolley.Activity;

import android.app.ProgressDialog;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.retvolley.R;
import com.example.retvolley.model.Request;
import com.example.retvolley.model.User;
import com.example.retvolley.retrofit.MethodHTTP;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

public class AddUserActivity extends AppCompatActivity {

    private TextView txtTitleLibrary;
    private EditText edtFullname, edtEmail,edtPasswoard;
    private Button btnSubmit;
    private String typeConn = "retrofit";

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        edtFullname = findViewById(R.id.adt_fullname);
        edtEmail = findViewById(R.id.adt_email);
        edtPasswoard = findViewById(R.id.adt_password);
        btnSubmit = findViewById(R.id.btn_submit);
        txtTitleLibrary = findViewById(R.id.txt_title_library);

        setTitle("Tambah_user");

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            typeConn = extras.getString("typeConnection","Undifine");
            if (typeConn.equalsIgnoreCase("retrofit"))
                txtTitleLibrary.setText("Send using retrifit");
            else txtTitleLibrary.setText("send using volley");
        }

    }

    public void submitByRetrofit(User user){
        ProgressDialog proDialog = new ProgressDialog(this);
        proDialog.setTitle(getString(R.string.title_activity_retrofit));
        proDialog.setMessage("Sedang disubmit");
        proDialog.show();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.43.51")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        MethodHTTP client = retrofit.create(MethodHTTP.class);
        Call<Request> call = client.sendUser(user);

        call.enqueue(new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                proDialog.dismiss();
                if (response.body() != null){
                    if (response.body().getCode()==201){
                        Toast.makeText(getApplicationContext(),"Response :"+response.body().getStatus(),Toast.LENGTH_SHORT).show();
                        finish();
                    }else if(response.body().getCode() == 406){
                        Toast.makeText(getApplicationContext(). "Response :"+response.body().getStatus(),Toast.LENGTH_SHORT).show();
                    edtEmail.requestFocus();
                    }else{
                        Toast.makeText(getApplicationContext(), "Response :"+response.body().getStatus(),Toast.LENGTH_SHORT).show();
                    finish();
                    }
                }
                Log.e(TAG, "Error : "+response.message());
            }

            @Override
            public void onFailure(Call<Request> call, Throwable t) {
                proDialog.dismiss();
                Log.e(TAG, "Error2 : "+t.getMessage());
            }
        });
    }

    public void actionSubmit(View view){
        boolean isInputValid = false;

        if (edtFullname.getText().toString().isEmpty()){
            edtFullname.setError("Tidak Boleh Kosong");
            edtFullname.requestFocus();
            isInputValid = false;
        }else{
            isInputValid = true;
        }

        if (edtEmail.getText().toString().isEmpty()){
            edtEmail.setError("Tidak Boleh Kosong");
            edtEmail.requestFocus();
            isInputValid = false;
        }else{
            isInputValid = true;
        }
        if (edtPasswoard.getText().toString().isEmpty()){
            edtPasswoard.setError("Tidak Boleh Kosong");
            edtPasswoard.requestFocus();
            isInputValid = false;
        }else{
            isInputValid = true;
        }
        if (isInputValid){
            User user = new User();
            user.setUser_fullname(edtFullname.getText().toString());
            user.setUser_email(edtEmail.getText().toString());
            user.setUser_password(edtPasswoard.getText().toString());
            if (typeConn.equalsIgnoreCase("retrofit"))
                submitByRetrofit(user);
            else submitByVolley(user);
        }
    }

    public void submitByVolley(User user){
        Gson gson = new Gson();
        String url = "http//192.168.43.51/volley/User_Registration.php"

        ProgressDialog proDialog = new ProgressDialog(this);
        proDialog.setTitle("volley");
        proDialog.setMessage("sedang diskusi");
        proDialog.show();

        String userRequest = gson.toJson(user);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, null, new com.android.volley.Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response){
                proDialog.dismiss();
                if (response != null) {
                    Request requestFormat = gson.fromJson(response.toString(), Request.class);
                    if (response != null) {
                        Toast.makeText(getApplicationContext(), "Response : " + requestFormat.getStatus(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else if (requestFormat.getCode() == 406) {
                        Toast.makeText(getApplicationContext(), "Response : " + requestFormat.getStatus(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Response : " + requestFormat.getStatus(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                }
            }. new com.android.volley.Response.ErrorListener(){
                proDialog.dismiss();
                Log.e(TAG, "Error POST Volley : "+error.getMessage());
            }
    }){
                @Override
                public byte[] getBody(){
                    return userRequest.getBytes();
            }
            };
    requestQueue.add(request);
    requestQueue.start();
}