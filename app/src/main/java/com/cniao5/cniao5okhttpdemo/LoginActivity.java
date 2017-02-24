package com.cniao5.cniao5okhttpdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cniao5.cniao5okhttpdemo.model.BaseResult;
import com.cniao5.cniao5okhttpdemo.model.User;
import com.cniao5.cniao5okhttpdemo.okhttp.BaseCallback;
import com.cniao5.cniao5okhttpdemo.okhttp.SimpleHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.etxt_username)
    EditText mEtxtUsername;
    @BindView(R.id.etxt_password)
    EditText mEtxtPassword;
    @BindView(R.id.btn_login)
    Button mBtnLogin;

    private OkHttpClient httpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);



        httpClient= new OkHttpClient();



    }

    @OnClick(R.id.btn_login)
    public void onClick() {



        String username = mEtxtUsername.getText().toString().trim();
        String password = mEtxtPassword.getText().toString().trim();

        loginWithForm(username,password);


//        loginWithJSON(username,password);


    }

    private void loginWithJSON(String username, String password) {

        String url = Config.API.BASE_URL+"login/json";

        SimpleHttpClient.newBuilder()
                .addParam("username",username)
                .addParam("password",password)
                .json().url(url)
                .build().enqueue(new BaseCallback<BaseResult>() {

            @Override
            public void onSuccess(BaseResult baseResult) {

               Toast.makeText(LoginActivity.this,baseResult.getMessage(),Toast.LENGTH_LONG).show();


            }
        });


//        JSONObject jsonObj = new JSONObject();
//
//        try {
//            jsonObj.put("username",username);
//            jsonObj.put("password",password);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        String jsonParams =jsonObj.toString();
//
//        Log.d("LoginActivity","json params = "+jsonParams);
//
//        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonParams);
//
//        Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//
//                .build();
//
//
//        httpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//                Log.d("LoginActivity","请求服务器出差");
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//
//
//                if(response.isSuccessful()){
//
//
//                    String json = response.body().string();
//
//
//                    try {
//                        JSONObject jsonObj = new JSONObject(json);
//
//
//                        final String message = jsonObj.optString("message");
//                        final int success = jsonObj.optInt("success");
//
//
//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                if(success==1)
//
//                                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_LONG).show();
//
//                                else
//                                    Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
//                            }
//                        });
//
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//
//                }
//
//
//
//            }
//        });

    }

    private void loginWithForm(String username, String password) {


        String url = Config.API.BASE_URL+"login";

        SimpleHttpClient.newBuilder().addParam("username",username)
                .addParam("password",password)
                .post().url(url)
                .build().enqueue(new BaseCallback<BaseResult>() {

            @Override
            public void onSuccess(BaseResult baseResult) {

                Toast.makeText(LoginActivity.this,baseResult.getMessage(),Toast.LENGTH_LONG).show();


            }
        });

//        RequestBody body = new FormBody.Builder()
//                .add("username",username).
//                        add("password",password).build();
//
//        Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//
//                .build();
//
//        httpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//                Log.d("LoginActivity","请求服务器出差");
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//
//
//                if(response.isSuccessful()){
//
//
//                    String json = response.body().string();
//
//
//                    try {
//                        JSONObject jsonObj = new JSONObject(json);
//
//
//                        final String message = jsonObj.optString("message");
//                        final int success = jsonObj.optInt("success");
//
//
//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                if(success==1)
//
//                                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_LONG).show();
//
//                                else
//                                    Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
//                            }
//                        });
//
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//
//                }
//
//
//
//            }
//        });
//


    }


}
