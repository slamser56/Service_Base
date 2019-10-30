package com.example.service_base.Repair_item;

public class Repair_item {

    private int id;
    private String date;
    private String status;

    public Repair_item(int id, String date, String status)
    {
        this.id = id;
        this.date = date;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
