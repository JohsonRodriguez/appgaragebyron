package com.example.appcocherabyron;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button btn_Escaner, btn_Buscar;
    TextView txthora, txtdia,txtshowRegisters,txttitulo;
    EditText et_dni;
    String dni, name, lastname,state, propietario,type,day,hour,token;
    LinearLayout linearLayout;
    Boolean enabled;
    public static SharedPreferences preferences;


    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        txthora=findViewById(R.id.tvhora);
        txtdia=findViewById(R.id.tvfecha);
        txttitulo=findViewById(R.id.tvtitulo);
        btn_Escaner=findViewById(R.id.btnEscanear);
        btn_Buscar=findViewById(R.id.btnBuscar);
        et_dni=findViewById(R.id.etdni);
        txtshowRegisters=findViewById(R.id.tvshowregisters);
         day = new SimpleDateFormat("EEE dd MMM yyyy").format(new Date());
         hour = new SimpleDateFormat("h:mm a").format(new Date());
        txthora.setText(hour);
        txtdia.setText(day.toUpperCase());
        linearLayout=findViewById(R.id.activity_main2);

        preferences = getSharedPreferences(getPackageName()+ "_preferences", Context.MODE_PRIVATE);
        token=preferences.getString("token", "");
        txttitulo.setText(preferences.getString("username", ""));

        btn_Escaner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escanera();
            }
        });

        btn_Buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar();
            }
        });

        txtshowRegisters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent (MainActivity.this, ShowRegister.class);
                startActivity(intent);
            }
        });

    }



    public void escanera(){
        IntentIntegrator intent = new IntentIntegrator((this));
        intent.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intent.setPrompt("Escanear dni del conductor");
        intent.setOrientationLocked(false);
        intent.setCameraId(0);
        intent.setBeepEnabled(true);
        intent.setCaptureActivity(CaptureActivityPortrait.class);
        intent.setBarcodeImageEnabled(false);
        intent.initiateScan();
    }

    private void buscar() {
        dni=et_dni.getText().toString();
        CargarDatos();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result!=null){
            if(result.getContents()==null){
                Toast.makeText(this,"Cancelaste el Escaneo",Toast.LENGTH_SHORT).show();
            }else{
                 dni =result.getContents();
                 if(dni.length()< 8 || !dni.contains("/")){
                     CargarDatos();
                 }else{
                     Snackbar snackbar = Snackbar
                             .make(linearLayout, "código de barras no válido", Snackbar.LENGTH_LONG);

                                snackbar.setBackgroundTintList(ColorStateList.valueOf(Color.RED));


                     snackbar.show();
                 }
            }
        }else{
            super.onActivityResult(requestCode,resultCode,data);
        }
    }


    private void CargarDatos() {
        final String url = "https://lordbyron-dev.azure-api.net/parking-service/person/find/";
        Intent intent = new Intent(this, ShowPerson.class);
        StringRequest jsonObjRequest = new StringRequest(
                Request.Method.GET,
                url + dni,
                response -> {
                    Log.d("CARGAR-DATOS: ", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        name=jsonObject.getString("name");
                        lastname=jsonObject.getString("lastname");
                        type=jsonObject.getString("type");
                        enabled=jsonObject.getBoolean("enabled");
                        propietario= name +" "+ lastname;

                        if (!enabled  ){
                            navigateToMain();
                        }else{
                        intent.putExtra("dni", dni);
                        intent.putExtra("type", type);
                        intent.putExtra("propietario", propietario);
                        intent.putExtra("enabled", "ACTIVO");
                        startActivity(intent);
                        }
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
                /*preferences = getSharedPreferences(getPackageName()+ "_preferences", Context.MODE_PRIVATE);
                token=preferences.getString("token", "");*/

                Map<String, String> params = new HashMap<String, String>();
                params.put("Ocp-Apim-Subscription-Key", "fdedf7de7f014ceea33b36bfedd8075e");
                params.put("Authorization", "Bearer" + token);
                return params;
            }

           /* @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);
                params.put("grant_type", "password");
                params.put("scope", "write");
                return params;
            }*/
        };
        Volley.newRequestQueue(this).add(jsonObjRequest);
    }

    private void navigateToMain() {
        startActivity(new Intent(getApplicationContext(), Dahsboard.class));
    }

}