package com.example.appcocherabyron;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.appcocherabyron.adapter.CarsAdapter;
import com.example.appcocherabyron.adapter.RegisterAdapter;
import com.example.appcocherabyron.api.ApiClient;
import com.example.appcocherabyron.services.CarsResponse;
import com.example.appcocherabyron.services.RegisterResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowRegister extends AppCompatActivity {
    RecyclerView recyclerView;
    RegisterAdapter registerAdapter;
    String today,token;
    TextView volver;
    public static SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_register);
        recyclerView=findViewById(R.id.recyclerview2);
        volver=findViewById(R.id.tvolver);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        registerAdapter = new RegisterAdapter(this::ClickedRegister);


        getToday();
        getAllRegisters(today);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

    }

    public void getAllRegisters(String dia){
        preferences = getSharedPreferences(getPackageName()+ "_preferences", Context.MODE_PRIVATE);
        token="Bearer"+ preferences.getString("token", "");

        Call<List<RegisterResponse>> registerList = ApiClient.getRegisterService().getRegisters(token,dia);
        registerList.enqueue(new Callback<List<RegisterResponse>>() {
            @Override
            public void onResponse(Call<List<RegisterResponse>> call, Response<List<RegisterResponse>> response) {
                if(response.isSuccessful()){
                    List<RegisterResponse> registerResponses = response.body();
                    registerAdapter.setData(registerResponses);
                    recyclerView.setAdapter(registerAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<RegisterResponse>> call, Throwable t) {
                Log.e("failure",t.getLocalizedMessage());
            }
        });
    }

    public void ClickedRegister(RegisterResponse registerResponse) {
       /* Intent intent = new Intent(this, RegisterActivity.class);
        _user="jorge";
        _dni=dni;
        _registrationplate=carsResponse.getRegistrationplate();
        intent.putExtra("user", _user);
        intent.putExtra("dni", _dni);
        intent.putExtra("registration_plate", _registrationplate);
        startActivity(intent);*/

        /*startActivity(new Intent(this,RegisterActivity.class).putExtra("data",carsResponse));*/
        /*Log.e("usuario clic",_user +" "+ _dni+" "+ _registrationplate);*/

    }
    public String getToday(){
        long ahora = System.currentTimeMillis();
        Date fecha = new Date(ahora);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
       return  today = df.format(fecha);
    }
}