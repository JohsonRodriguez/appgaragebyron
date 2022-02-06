package com.example.appcocherabyron;

import static com.android.volley.Request.Method.POST;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import retrofit2.http.PUT;

public class RegisterActivity extends AppCompatActivity {
    TextView tplaca;
    EditText tobservacion;
    String dni, user, placa, observacion,token;
    Button btncheckin, btncheckout;
    public static SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tplaca=findViewById(R.id.tvplaca);
        tobservacion=findViewById(R.id.edobservacion);


        btncheckin=findViewById(R.id.btnCheckin);
        btncheckout=findViewById(R.id.btnCheckout);

        Intent intent = this.getIntent();
        Bundle extra = intent.getExtras();
        dni=extra.getString("dni");
        user=extra.getString("user");
        placa=extra.getString("registration_plate");
        tplaca.setText(placa);

        btncheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkinCar(); }
        });

        btncheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkoutCar();
            }
        });
    }

    /*private void checkoutCar() {

        String url ="https://cochera-byron.herokuapp.com/register/save";

        JsonObjectRequest postRequest = new JsonObjectRequest(Method.PUT, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    showSuccessMessage(response);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                JSONObject testV = new JSONObject();
                try {
                    testV = new JSONObject(new String(error.networkResponse.data));
                    showErrorMessage(testV);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Volley.newRequestQueue(this).add(postRequest);
    }*/
    private void checkoutCar(){
        String url ="https://lordbyron-dev.azure-api.net/parking-service/register/checkout";
        JSONObject postData = new JSONObject();
        try {
            postData.put("user", user);
            postData.put("registrationplate", placa);
            postData.put("dni", dni);
            if(tobservacion.getText().toString().equals("")){
                observacion="sin novedad";
            }else{
                observacion=tobservacion.getText().toString();
            }
            tobservacion.getText();
            postData.put("observation", observacion);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest postRequest = new JsonObjectRequest(POST, url, postData, new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                try {
                    showSuccessMessage(response);
                    startActivity(new Intent(getApplicationContext(), ShowRegister.class));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                JSONObject testV = new JSONObject();
                try {
                    testV = new JSONObject(new String(error.networkResponse.data));
                    showErrorMessage(testV);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        })
        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                preferences = getSharedPreferences(getPackageName()+ "_preferences", Context.MODE_PRIVATE);
                token=preferences.getString("token", "");

                Map<String, String> params = new HashMap<String, String>();
                params.put("Ocp-Apim-Subscription-Key", "fdedf7de7f014ceea33b36bfedd8075e");
                params.put("Authorization", "Bearer" + token);
                return params;
            }
        }
                ;
        Volley.newRequestQueue(this).add(postRequest);
    }





    private void checkinCar(){
        String url ="https://lordbyron-dev.azure-api.net/parking-service/register/save";
        JSONObject postData = new JSONObject();
        try {
            postData.put("user", user);
            postData.put("registrationplate", placa);
            postData.put("dni", dni);
            if(tobservacion.getText().toString().equals("")){
                observacion="sin novedad";
            }else{
                observacion=tobservacion.getText().toString();
            }
            tobservacion.getText();
            postData.put("observation", observacion);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest postRequest = new JsonObjectRequest(POST, url, postData, new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                try {
                    showSuccessMessage(response);
                    startActivity(new Intent(getApplicationContext(), ShowRegister.class));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                 JSONObject testV = new JSONObject();
                     try {
                         testV = new JSONObject(new String(error.networkResponse.data));
                         showErrorMessage(testV);
                     } catch (JSONException e) {
                         e.printStackTrace();
                     }
                 }
        })
        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                preferences = getSharedPreferences(getPackageName()+ "_preferences", Context.MODE_PRIVATE);
                token=preferences.getString("token", "");

                Map<String, String> params = new HashMap<String, String>();
                params.put("Ocp-Apim-Subscription-Key", "fdedf7de7f014ceea33b36bfedd8075e");
                params.put("Authorization", "Bearer" + token);
                return params;
            }
        }
        ;
        Volley.newRequestQueue(this).add(postRequest);
    }

    private void showSuccessMessage(JSONObject response) throws JSONException {
        String message = response.getString("mensaje");
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    private void showErrorMessage(JSONObject testV) throws JSONException {
        String message= testV.getString("exception");
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();

    }

}