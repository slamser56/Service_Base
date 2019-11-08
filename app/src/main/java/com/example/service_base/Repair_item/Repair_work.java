package com.example.service_base.Repair_item;

public class Repair_work {


    public static final String TAG_REPAIR_WORK = "repairwork";
    public static final String TAG_ID = "id_w";
    public static final String TAG_ID_WORK = "id_work";
    public static final String TAG_WORK_NAME = "work_name";
    public static final String TAG_COST = "cost";
    public static final String TAG_DATE = "date_w";
    public static final String TAG_TIME = "time";
    public static final String TAG_WORKER = "worker_w";


    private int id_w;
    private int price_w;
    private int time_w;
    private String work_w;
    private String worker_w;
    private String date_w;

    public Repair_work(int id_w, int price_w, int time_w, String work_w, String worker_w, String date_w) {
        this.id_w = id_w;
        this.price_w = price_w;
        this.time_w = time_w;
        this.work_w = work_w;
        this.worker_w = worker_w;
        this.date_w = date_w;
    }

    public int getId_w() {
        return id_w;
    }

    public void setId_w(int id_w) {
        this.id_w = id_w;
    }

    public int getPrice_w() {
        return price_w;
    }

    public void setPrice_w(int price_w) {
        this.price_w = price_w;
    }

    public int getTime_w() {
        return time_w;
    }

    public void setTime_w(int time_w) {
        this.time_w = time_w;
    }

    public String getWork_w() {
        return work_w;
    }

    public void setWork_w(String work_w) {
        this.work_w = work_w;
    }

    public String getWorker_w() {
        return worker_w;
    }

    public void setWorker_w(String worker_w) {
        this.worker_w = worker_w;
    }

    public String getDate_w() {
        return date_w;
    }

    public void setDate_w(String date_w) {
        this.date_w = date_w;
    }
}
