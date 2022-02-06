package com.example.appcocherabyron.services;

import java.io.Serializable;

public class CarsResponse implements Serializable {
    private String registrationplate;
    private String carbrand;
    private String carmodel;
    private String carcolor;

    public String getRegistrationplate() {
        return registrationplate;
    }

    public void setRegistrationplate(String registrationplate) {
        this.registrationplate = registrationplate;
    }

    public String getCarbrand() {
        return carbrand;
    }

    public void setCarbrand(String carbrand) {
        this.carbrand = carbrand;
    }

    public String getCarmodel() {
        return carmodel;
    }

    public void setCarmodel(String carmodel) {
        this.carmodel = carmodel;
    }

    public String getCarcolor() {
        return carcolor;
    }

    public void setCarcolor(String carcolor) {
        this.carcolor = carcolor;
    }

    @Override
    public String toString() {
        return "UsersResponse{" +
                "registrationplate='" + registrationplate + '\'' +
                ", carbrand='" + carbrand + '\'' +
                ", carmodel='" + carmodel + '\'' +
                ", carcolor='" + carcolor + '\'' +
                '}';
    }
}
