package com.example.finalprojectapplication;

public class PaymentRecord {
    private int id;
    private String account;
    private double amount;
    private String date;

    // 添加了一个静态变量来存储全局电费数据
    private static double electricityBill = 0;

    public static void setElectricityBill(double bill) {
        electricityBill = bill;
    }

    public static double getElectricityBill() {
        return electricityBill;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
