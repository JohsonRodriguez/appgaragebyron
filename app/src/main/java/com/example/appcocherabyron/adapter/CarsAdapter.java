package com.example.appcocherabyron.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcocherabyron.R;
import com.example.appcocherabyron.services.CarsResponse;

import java.util.List;

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.ViewHolder> {
    private List<CarsResponse> userResponseList;
    private Context context;
    private ClickedItem clickedItem;

    public CarsAdapter(ClickedItem clickedItem) {
        this.clickedItem=clickedItem;
    }

    public void setData(  List<CarsResponse> userResponseList){
        this.userResponseList=userResponseList;
        notifyDataSetChanged();

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        return new CarsAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_cars,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CarsResponse carsResponse = userResponseList.get(position);
        String Registrationplate = carsResponse.getRegistrationplate();
        String Carmodel = carsResponse.getCarmodel();
        String Carcolor = carsResponse.getCarcolor();
        String Carbrand = carsResponse.getCarbrand();

        holder._registrationplate.setText(Registrationplate);
        holder._carmodel.setText(Carmodel);
        holder._carcolor.setText(Carcolor);
        holder._carbrand.setText(Carbrand);

        holder.Imagemore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedItem.ClickedCar(carsResponse);
            }
        });

    }

    public interface ClickedItem{
        public void ClickedCar(CarsResponse carsResponse);
    }

    @Override
    public int getItemCount() {

        return userResponseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView _registrationplate, _carbrand, _carcolor, _carmodel;
        ImageView Imagemore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            _registrationplate=itemView.findViewById(R.id.tvregistrationplate);
            _carbrand=itemView.findViewById(R.id.tvmarca);
            _carcolor=itemView.findViewById(R.id.tvcolor);
            _carmodel=itemView.findViewById(R.id.tvmodelo);
            Imagemore=itemView.findViewById(R.id.imageMore);

        }
    }
}
