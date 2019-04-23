package com.btradesm.storemanager.Model;

public class AlllProductModel {

    String id;
    String title;
    String image;
    String catogary_id;
    String stock;
    String price;
    String unit_value;
    String unit;
    String increment;
    String reward;


    public AlllProductModel() {

    }



    public AlllProductModel(String id, String title, String image, String catogary_id, String stock, String price, String unit_value, String unit, String increment, String reward) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.catogary_id = catogary_id;
        this.stock = stock;
        this.price = price;
        this.image=image;
        this.unit_value = unit_value;
        this.unit = unit;
        this.increment = increment;
        this.reward = reward;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCatogary_id() {
        return catogary_id;
    }

    public void setCatogary_id(String catogary_id) {
        this.catogary_id = catogary_id;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnit_value() {
        return unit_value;
    }

    public void setUnit_value(String unit_value) {
        this.unit_value = unit_value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getIncrement() {
        return increment;
    }

    public void setIncrement(String increment) {
        this.increment = increment;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }


}
