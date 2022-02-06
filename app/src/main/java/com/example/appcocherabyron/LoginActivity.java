package com.example.appcocherabyron;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    public static SharedPreferences preferences;
    private TextView tvUsername;
    private TextView tvPassword;
    String username,name,token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvUsername = findViewById(R.id.etusername);
        tvPassword = findViewById(R.id.etpassword);
        Button btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(v -> {
            String username = tvUsername.getText()
                    .toString().trim();
            Log.d("LOGIN-", username);
            String password = tvPassword.getText()
                    .toString().trim();
            Log.d("LOGIN-", password);
            login(username, password);
        });
    }

    private void login(String username, String password) {
        final String url = "https://lordbyron-dev.azure-api.net/oauth-service";

        StringRequest jsonObjRequest = new StringRequest(
                Request.Method.POST,
                url + "/oauth/token",
                response -> {
                    Log.d("LOGIN-RESPONSE: ", response);
                    try {
                        saveToken(response);
                        decodeToken(response);
                        CargarUser();
                        navigateToMain();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getBaseContext(), "Bienvenido!",  Toast.LENGTH_SHORT).show();
                },
                error -> {
                    if (error.networkResponse.data != null) {
                        String bodyError = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                        Log.d("LOGIN-ERROR: ", bodyError);
                    }
                    Toast.makeText(getBaseContext(), "Usuario o password incorrectos",  Toast.LENGTH_SHORT).show();
                }){

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Ocp-Apim-Subscription-Key", "fdedf7de7f014ceea33b36bfedd8075e");
                params.put("Authorization", "Basic Zmlyc3QtY2xpZW50OmFzZGYxMjM0");
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);
                params.put("grant_type", "password");
                params.put("scope", "write");
                return params;
            }
        };
        Volley.newRequestQueue(this).add(jsonObjRequest);
    }

    private void navigateToMain() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    private void saveToken(String response) throws JSONException {
        JSONObject body = new JSONObject(response);
        preferences = getSharedPreferences(getPackageName()+ "_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token", body.getString("access_token"));
        editor.apply();
    }
    private void decodeToken(String response)throws JSONException{
        JSONObject body = new JSONObject(response);
        Log.d("TOKEN: ", body.getString("access_token"));
        String token =  body.getString("access_token");
        JWT jwt = new JWT(token);
        Claim subscriptionMetaData = jwt.getClaim("user_name");
        String parsedValue = subscriptionMetaData.asString();
        Log.d("username: ", parsedValue);
        username=parsedValue;
    }
    private void CargarUser() {
        final String url = "https://lordbyron-dev.azure-api.net/users-service/api/users?username=";

        StringRequest jsonObjRequest = new StringRequest(
                Request.Method.GET,
                url + username,
                response -> {
                    Log.d("CARGAR-DATOS: ", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        name=jsonObject.getString("name");
                        Log.d("Nombre de usuario es: ", name);
                        preferences = getSharedPreferences(getPackageName()+ "_preferences", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("username", name);
                        editor.apply();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> {
                    if (error.networkResponse.data != null) {
                        String bodyError = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                        Log.d("MAIN-ERROR: ", bodyError);
                    }
                    Toast.makeText(getBaseContext(), "Error al buscar documento",  Toast.LENGTH_SHORT).show();
                }){

           /* @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }*/

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                preferences = getSharedPreferences(getPackageName()+ "_preferences", Context.MODE_PRIVATE);
                token=preferences.getString("token", "");

                Map<String, String> params = new HashMap<String, String>();
                params.put("Ocp-Apim-Subscription-Key", "fdedf7de7f014ceea33b36bfedd8075e");
                params.put("Authorization", "Bearer" + token);
                return params;
           }

        };
        Volley.newRequestQueue(this).add(jsonObjRequest);
    }
}