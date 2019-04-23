package com.btradesm.storemanager.Model;

public class StockModel {

    String id;
    String name;
    String unit;
    String price;


    public StockModel() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public StockModel(String id, String name, String unit, String price) {
        this.id = id;
        this.name = name;

        this.unit = unit;
        this.price = price;

    }




}
