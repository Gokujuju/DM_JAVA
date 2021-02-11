package com.julien.dmJava;

import java.io.Serializable;
import java.util.HashMap;

public class Currency implements Serializable {
    private String date;
    private HashMap<String,Double> rates = new HashMap<>();
    private String base;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public HashMap<String, Double> getRates() {
        return rates;
    }

    public void setRates(HashMap<String, Double> rates) {
        this.rates = rates;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }
}