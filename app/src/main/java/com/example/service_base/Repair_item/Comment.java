package com.example.service_base.Repair_item;





public class Comment {


    //DATA COMMENT
    public static final String TAG_ID_COMMENT = "id_c";
    public static final String TAG_COMMENT = "comment";
    public static final String TAG_WORKER = "worker";
    public static final String TAG_DATE_COMMENT = "date_c";

    //comment data
    private int id_c;
    private String Comment;
    private String worker;
    private String date_c;

    public Comment(int id_c, String comment, String worker, String date_c) {
        this.id_c = id_c;
        Comment = comment;
        this.worker = worker;
        this.date_c = date_c;
    }

    public int getId_c() {
        return id_c;
    }

    public void setId_c(int id_c) {
        this.id_c = id_c;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getDate_c() {
        return date_c;
    }

    public void setDate_c(String date_c) {
        this.date_c = date_c;
    }
}
