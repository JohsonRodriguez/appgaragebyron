package com.example.appcocherabyron.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcocherabyron.R;
import com.example.appcocherabyron.services.CarsResponse;
import com.example.appcocherabyron.services.RegisterResponse;

import java.util.List;

public class RegisterAdapter extends RecyclerView.Adapter<RegisterAdapter.ViewHolder> {
    private List<RegisterResponse> registerResponseList;
    private Context context;
    private RegisterAdapter.ClickedItem clickedItem;

    public RegisterAdapter(RegisterAdapter.ClickedItem clickedItem) {
        this.clickedItem=clickedItem;
    }

    public void setData(  List<RegisterResponse> registerResponseList){
        this.registerResponseList=registerResponseList;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public RegisterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        return new RegisterAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_registers,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull RegisterAdapter.ViewHolder holder, int position) {
        RegisterResponse registerResponse = registerResponseList.get(position);
         String registrationPlate=registerResponse.getRegistrationplate();
         String checkIn=registerResponse.getCheckin();
         String obs_Checkin=registerResponse.getObs_checkin();
         String user_Checkin=registerResponse.getUser_checkin();
         String checkOut=registerResponse.getCheckout();
         String obs_Checkout=registerResponse.getObs_checkout();
         String user_Checkout=registerResponse.getUser_checkout();
        String conductor_=registerResponse.getPersonEntity().getName() + " "+ registerResponse.getPersonEntity().getLastname();
         String lastname=registerResponse.getPersonEntity().getLastname();
         String type=registerResponse.getPersonEntity().getType();
        Log.e("lista",registerResponseList.toString());
        Log.e("error",registrationPlate+""+checkIn+""+obs_Checkin+""+user_Checkin+""+checkOut+""+obs_Checkout+""+user_Checkout);

       /*holder.registrationplate_.setText(registrationPlate);*/
        holder.checkin_.setText(checkIn);
        holder.propietario_.setText(conductor_);
        holder.obs_checkin_.setText(obs_Checkin);
        /*holder.user_checkin_.setText(user_Checkin);*/
        holder.checkout_.setText(checkOut);
        holder.obs_checkout_.setText(obs_Checkout);
        /*holder.user_checkout_.setText(user_Checkout);*/
       /*  holder.name_.setText(name);
        holder.lastname_.setText(lastname);
        holder.type_.setText(type);*/



        holder.Imagemore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedItem.ClickedRegister(registerResponse);
            }
        });

    }
    public interface ClickedItem{
        public void ClickedRegister(RegisterResponse registerResponse);
    }

    @Override
    public int getItemCount() {
        return registerResponseList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView registrationplate_, checkin_, obs_checkin_, user_checkin_,checkout_, obs_checkout_,user_checkout_,propietario_,type_;
        ImageView Imagemore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkin_=itemView.findViewById(R.id._tvingreso);
            obs_checkin_=itemView.findViewById(R.id._tvobservation);
            checkout_=itemView.findViewById(R.id._tvsalida);
            obs_checkout_=itemView.findViewById(R.id._tvobservation2);
            propietario_=itemView.findViewById(R.id._tconductor);
            Imagemore=itemView.findViewById(R.id.imageMore);

        }
    }
}
