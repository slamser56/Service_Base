package com.example.service_base.Repair_item;

public class Repair_item {

    //TAG
    public static final String TAG_REPAIR = "repair";
    public static final String TAG_ERROR = "error";
    public static final String TAG_SUCCESS = "success";
    public static final String TAG_NOT_FOUND_REPAIR = "repair_not_found";

    //DATA REPAIR
    public static final String TAG_ID = "id";
    public static final String TAG_ID_ORDER = "id_order";
    public static final String TAG_DATE = "date";
    public static final String TAG_STATUS = "status";
    public static final String TAG_ID_STATUS = "id_status";
    public static final String TAG_DATE_COMPLETE = "date_complete";
    public static final String TAG_TYPE_OF_REPAIR = "type_of_repair";
    public static final String TAG_ID_TYPE_OF_REPAIR = "id_type";
    public static final String TAG_SN = "sn";
    public static final String TAG_IMEI = "imei";
    public static final String TAG_UNUQUE_NUMBER = "unuque_number";
    public static final String TAG_PRODUCT = "product";
    public static final String TAG_DATE_OF_WARRANTY = "date_of_warranty";
    public static final String TAG_APPEARANCE = "appearance";
    public static final String TAG_ADDITIONAL_DESCRIPTION = "additional_description";
    public static final String TAG_MALFUNCTION = "malfunction";
    public static final String TAG_ID_MALFUNCTION = "id_malfunction";
    public static final String TAG_CONTRACTOR = "contractor";
    public static final String TAG_CONTACT_PERSON = "contact_person";
    public static final String TAG_PHONE = "phone";
    public static final String TAG_MAIL = "mail";
    public static final String TAG_ADRESS = "adress";


    //first data
    private int id;
    private String date;
    private int id_status;

    //all data
    private String date_complete;
    private int id_type_of_repair;
    private String sn;
    private int imei;
    private String unique_number;
    private String product;
    private String date_of_warranty;
    private String appearance;
    private String additional_description;
    private int id_malfunction;
    private String contractor;
    private String contact_person;
    private String phone;
    private String mail;
    private String adress;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Repair_item(int id, String date, String status) {
        this.id = id;
        this.date = date;
        this.status = status;
    }

    public Repair_item(int id, String date, int id_status, String date_complete, int id_type_of_repair, String sn, int imei, String unique_number, String product, String date_of_warranty, String appearance, String additional_description, int id_malfunction, String contractor, String contact_person, String phone, String mail, String adress) {
        this.id = id;
        this.date = date;
        this.id_status = id_status;
        this.date_complete = date_complete;
        this.id_type_of_repair = id_type_of_repair;
        this.sn = sn;
        this.imei = imei;
        this.unique_number = unique_number;
        this.product = product;
        this.date_of_warranty = date_of_warranty;
        this.appearance = appearance;
        this.additional_description = additional_description;
        this.id_malfunction = id_malfunction;
        this.contractor = contractor;
        this.contact_person = contact_person;
        this.phone = phone;
        this.mail = mail;
        this.adress = adress;
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


    public String getDate_complete() {
        return date_complete;
    }

    public void setDate_complete(String date_complete) {
        this.date_complete = date_complete;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public int getImei() {
        return imei;
    }

    public void setImei(int imei) {
        this.imei = imei;
    }

    public String getUnique_number() {
        return unique_number;
    }

    public void setUnique_number(String unique_number) {
        this.unique_number = unique_number;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDate_of_warranty() {
        return date_of_warranty;
    }

    public void setDate_of_warranty(String date_of_warranty) {
        this.date_of_warranty = date_of_warranty;
    }

    public String getAppearance() {
        return appearance;
    }

    public void setAppearance(String appearance) {
        this.appearance = appearance;
    }

    public String getAdditional_description() {
        return additional_description;
    }

    public void setAdditional_description(String additional_description) {
        this.additional_description = additional_description;
    }

    public int getId_status() {
        return id_status;
    }

    public void setId_status(int id_status) {
        this.id_status = id_status;
    }

    public int getId_type_of_repair() {
        return id_type_of_repair;
    }

    public void setId_type_of_repair(int id_type_of_repair) {
        this.id_type_of_repair = id_type_of_repair;
    }

    public int getId_malfunction() {
        return id_malfunction;
    }

    public void setId_malfunction(int id_malfunction) {
        this.id_malfunction = id_malfunction;
    }

    public String getContractor() {
        return contractor;
    }

    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    public String getContact_person() {
        return contact_person;
    }

    public void setContact_person(String contact_person) {
        this.contact_person = contact_person;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
