package com.example.appcocherabyron.services;

import java.io.Serializable;

public class RegisterResponse implements Serializable {
    private String registrationplate;
    private String checkin;
    private String obs_checkin;
    private String user_checkin;
    private String checkout;
    private String obs_checkout;
    private String user_checkout;
    private PersonEntity person;

    public String getRegistrationplate() {
        return registrationplate;
    }

    public void setRegistrationplate(String registrationplate) {
        this.registrationplate = registrationplate;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getObs_checkin() {
        return obs_checkin;
    }

    public void setObs_checkin(String obs_checkin) {
        this.obs_checkin = obs_checkin;
    }

    public String getUser_checkin() {
        return user_checkin;
    }

    public void setUser_checkin(String user_checkin) {
        this.user_checkin = user_checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public String getObs_checkout() {
        return obs_checkout;
    }

    public void setObs_checkout(String obs_checkout) {
        this.obs_checkout = obs_checkout;
    }

    public String getUser_checkout() {
        return user_checkout;
    }

    public void setUser_checkout(String user_checkout) {
        this.user_checkout = user_checkout;
    }

    public PersonEntity getPersonEntity() {
        return person;
    }

    public void setPersonEntity(PersonEntity person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "RegisterResponse{" +
                "registrationplate='" + registrationplate + '\'' +
                ", checkin='" + checkin + '\'' +
                ", obs_checkin='" + obs_checkin + '\'' +
                ", user_checkin='" + user_checkin + '\'' +
                ", checkout='" + checkout + '\'' +
                ", obs_checkout='" + obs_checkout + '\'' +
                ", user_checkout='" + user_checkout + '\'' +
                ", person=" + person +
                '}';
    }
}
