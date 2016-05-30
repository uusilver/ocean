package com.tmind.ocean.entity;

import javax.persistence.*;

/**
 * Created by lijunying on 15/11/21.
 */
@Entity
@Table(name = "m_user_product_meta")
public class UserProductMetaEntity {

    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;
    @Column(name="product_name")
    private String product_name;
    @Column(name="product_category") //产品类别
    private String product_category;
    @Column(name="qrcode_total_no")  //二维码数量
    private Integer qrcode_total_no;
    @Column(name="update_time")
    private String update_time;
    @Column(name="product_desc")  //产品描述
    private String product_desc;
    @Column(name="user_id")
    private Integer user_id;
    @Column(name="product_id") //产品ID
    private String product_id;
    @Column(name="advice_temp")
    private String advice_temp;
    @Column(name="product_address")
    private String product_address;
    @Column(name="tel_no")
    private String tel_no;
    @Column(name="product_factory")
    private String product_factory;
    @Column(name="show_desc")
    private char show_desc;


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public String getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public Integer getQrcode_total_no() {
        return qrcode_total_no;
    }

    public void setQrcode_total_no(Integer qrcode_total_no) {
        this.qrcode_total_no = qrcode_total_no;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getAdvice_temp() {
        return advice_temp;
    }

    public void setAdvice_temp(String advice_temp) {
        this.advice_temp = advice_temp;
    }

    public String getProduct_address() {
        return product_address;
    }

    public void setProduct_address(String product_address) {
        this.product_address = product_address;
    }

    public String getTel_no() {
        return tel_no;
    }

    public void setTel_no(String tel_no) {
        this.tel_no = tel_no;
    }

    public String getProduct_factory() {
        return product_factory;
    }

    public void setProduct_factory(String product_factory) {
        this.product_factory = product_factory;
    }

    public char getShow_desc() {
        return show_desc;
    }

    public void setShow_desc(char show_desc) {
        this.show_desc = show_desc;
    }
}
