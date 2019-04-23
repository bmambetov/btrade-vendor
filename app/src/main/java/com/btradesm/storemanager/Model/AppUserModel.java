package com.btradesm.storemanager.Model;

public class AppUserModel {

    String id;
    String name;
    String phone;
    String email;
    String totalorder;
    String wallet;
    String status;
    String rewards;
    String totalammount;

    public AppUserModel() {

    }

    public AppUserModel(String id, String name, String phone, String email, String totalorder, String wallet, String status, String rewards, String totalammount) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.totalorder = totalorder;
        this.wallet = wallet;
        this.status = status;
        this.rewards = rewards;
        this.totalammount = totalammount;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTotalorder() {
        return totalorder;
    }

    public void setTotalorder(String totalorder) {
        this.totalorder = totalorder;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getRewards() {
        return rewards;
    }

    public void setRewards(String rewards) {
        this.rewards = rewards;
    }

    public String getTotalammount() {
        return totalammount;
    }

    public void setTotalammount(String totalammount) {
        this.totalammount = totalammount;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
