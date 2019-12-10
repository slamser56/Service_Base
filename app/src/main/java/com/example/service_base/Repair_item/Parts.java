package com.example.service_base.Repair_item;

public class Parts {


    public static final String TAG_PARTS = "parts";
    public static final String TAG_ID = "id_parts";
    public static final String TAG_NAME = "name_p";
    public static final String TAG_DATE = "date_p";
    public static final String TAG_SN = "sn_p";
    public static final String TAG_PN = "pn_p";
    public static final String TAG_DESCRIPTION = "description_p";
    public static final String TAG_COST = "cost_p";
    public static final String TAG_WORKER = "worker_name";


    private int id_p;
    private String name_p;
    private String date_p;
    private String sn_p;
    private String pn_p;
    private String description_p;
    private String worker_p;
    private int cost_p;

    public Parts(int id_p, String name_p, String date_p, String sn_p, String pn_p, String description_p, int cost_p, String worker_p) {
        this.id_p = id_p;
        this.name_p = name_p;
        this.date_p = date_p;
        this.sn_p = sn_p;
        this.pn_p = pn_p;
        this.description_p = description_p;
        this.cost_p = cost_p;
        this.worker_p = worker_p;
    }

    public String getWorker_p() {
        return worker_p;
    }

    public void setWorker_p(String worker_p) {
        this.worker_p = worker_p;
    }

    public int getId_p() {
        return id_p;
    }

    public void setId_p(int id_p) {
        this.id_p = id_p;
    }

    public String getName_p() {
        return name_p;
    }

    public void setName_p(String name_p) {
        this.name_p = name_p;
    }

    public String getDate_p() {
        return date_p;
    }

    public void setDate_p(String date_p) {
        this.date_p = date_p;
    }

    public String getSn_p() {
        return sn_p;
    }

    public void setSn_p(String sn_p) {
        this.sn_p = sn_p;
    }

    public String getPn_p() {
        return pn_p;
    }

    public void setPn_p(String pn_p) {
        this.pn_p = pn_p;
    }

    public String getDescription_p() {
        return description_p;
    }

    public void setDescription_p(String description_p) {
        this.description_p = description_p;
    }

    public int getCost_p() {
        return cost_p;
    }

    public void setCost_p(int cost_p) {
        this.cost_p = cost_p;
    }
}
