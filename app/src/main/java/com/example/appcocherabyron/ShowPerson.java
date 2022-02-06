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
import android.widget.TextView;

import com.example.appcocherabyron.adapter.CarsAdapter;
import com.example.appcocherabyron.api.ApiClient;
import com.example.appcocherabyron.services.CarsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowPerson extends AppCompatActivity implements CarsAdapter.ClickedItem {
    TextView tvDni, tvProprietary, tvCargo, tvEstate;
    String dni, type, proprietary, enabled, _user, username,_dni,_registrationplate, token;

    public static SharedPreferences preferences;
    RecyclerView recyclerView;
    CarsAdapter carsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_person);

        preferences = getSharedPreferences(getPackageName()+ "_preferences", Context.MODE_PRIVATE);
        token="Bearer"+ preferences.getString("token", "");
        username=preferences.getString("username", "");

        //obtener persona
        tvDni=findViewById(R.id.tv_dni);
        tvProprietary =findViewById(R.id.tv_conductor);
        tvCargo=findViewById(R.id.tv_tipo);
        tvEstate =findViewById(R.id.tv_estado);
        Intent intent = this.getIntent();
        Bundle extra = intent.getExtras();
        dni=extra.getString("dni");
        type=extra.getString("type");
        proprietary =extra.getString("propietario");
        enabled=extra.getString("enabled");

        tvDni.setText(dni);
        tvCargo.setText(type);
        tvProprietary.setText(proprietary);
        tvEstate.setText(enabled.toString());
        //

        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        carsAdapter = new CarsAdapter(this::ClickedCar);

        getAllUsers(dni);
    }

    public void getAllUsers(String dni){


        Call<List<CarsResponse>> personlist = ApiClient.getPersonaService().getCars(token,dni);
        personlist.enqueue(new Callback<List<CarsResponse>>() {
            @Override
            public void onResponse(Call<List<CarsResponse>> call, Response<List<CarsResponse>> response) {
                if(response.isSuccessful()){
                    List<CarsResponse> userResponses = response.body();
                    carsAdapter.setData(userResponses);
                    recyclerView.setAdapter(carsAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<CarsResponse>> call, Throwable t) {
                Log.e("failure",t.getLocalizedMessage());
            }
        });
    }

    /*@Override*/
    public void ClickedCar(CarsResponse carsResponse) {
        Intent intent = new Intent(this, RegisterActivity.class);
        _user=username;
        _dni=dni;
        _registrationplate=carsResponse.getRegistrationplate();
        intent.putExtra("user", _user);
        intent.putExtra("dni", _dni);
        intent.putExtra("registration_plate", _registrationplate);
        startActivity(intent);

        /*startActivity(new Intent(this,RegisterActivity.class).putExtra("data",carsResponse));*/
        /*Log.e("usuario clic",_user +" "+ _dni+" "+ _registrationplate);*/

    }
}