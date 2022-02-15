package com.step.currencyapp;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Rate {
    private int r030;
    private String txt;
    private double rate;
    private String cc;
    private Date exchangeDate;

    private final SimpleDateFormat parser = new SimpleDateFormat("dd.MM.yyyy");

    public Rate(int r030, String txt, double rate, String cc, String dateString) {
        this.r030 = r030;
        this.txt = txt;
        this.rate = rate;
        this.cc = cc;
        this.setExchangeDate(dateString);
    }

    public int getR030() {
        return r030;
    }

    public void setR030(int r030) {
        this.r030 = r030;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public Date getExchangeDate() {
        return exchangeDate;
    }

    public void setExchangeDate(String dateString) {

        try {
            this.exchangeDate = parser.parse(dateString);
        } catch (ParseException e) {
            Log.e("setExchangeDate(String): ", e.getMessage());
        }
    }

    public void setExchangeDate(Date exchangeDate) {
        this.exchangeDate = exchangeDate;
    }
}
