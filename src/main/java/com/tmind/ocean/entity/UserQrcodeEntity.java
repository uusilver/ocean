package com.tmind.ocean.entity;

import javax.persistence.*;

/**
 * Created by lijunying on 15/11/24.
 */
@Entity
@Table(name = "M_USER_QRCODE")
public class UserQrcodeEntity {

    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;
    @Column(name="user_id")
    private Integer user_id;
    @Column(name="product_id")
    private String product_id;
    @Column(name="product_batch")
    private String product_batch;
    @Column(name="qr_query_string")
    private String qr_query_string;
    @Column(name="query_times")
    private Integer query_times;
    @Column(name="query_date")
    private String query_date;
    @Column(name="active_flag")
    private String active_flag;
    @Column(name="create_date")
    private String create_date;
    @Column(name="query_match")
    private String query_match;
    @Column(name="vistor_ip_addr")
    private String vistor_ip_addr;
    @Column(name="vistor_phy_addr")
    private String vistor_phy_addr;
    @Column(name="ip_check_flag")
    private String ip_check_flag;
    @Column(name="cache_flag")
    private char cache_flag;
    @Column(name="lottery_flag")
    private char lottery_flag;
    @Column(name="lottery_desc")
    private char lottery_desc;
    @Column(name="get_lottery_flag")
    private char get_lottery_flag;
    @Column(name="delete_flag")
    private char delete_flag;
    @Column(name="lottery_check_flag")
    private char lottery_check_flag;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
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

    public String getProduct_batch() {
        return product_batch;
    }

    public void setProduct_batch(String product_batch) {
        this.product_batch = product_batch;
    }

    public String getQr_query_string() {
        return qr_query_string;
    }

    public void setQr_query_string(String qr_query_string) {
        this.qr_query_string = qr_query_string;
    }

    public Integer getQuery_times() {
        return query_times;
    }

    public void setQuery_times(Integer query_times) {
        this.query_times = query_times;
    }

    public String getQuery_date() {
        return query_date;
    }

    public void setQuery_date(String query_date) {
        this.query_date = query_date;
    }

    public String getActive_flag() {
        return active_flag;
    }

    public void setActive_flag(String active_flag) {
        this.active_flag = active_flag;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getQuery_match() {
        return query_match;
    }

    public void setQuery_match(String query_match) {
        this.query_match = query_match;
    }

    public String getVistor_ip_addr() {
        return vistor_ip_addr;
    }

    public void setVistor_ip_addr(String vistor_ip_addr) {
        this.vistor_ip_addr = vistor_ip_addr;
    }

    public String getVistor_phy_addr() {
        return vistor_phy_addr;
    }

    public void setVistor_phy_addr(String vistor_phy_addr) {
        this.vistor_phy_addr = vistor_phy_addr;
    }

    public String getIp_check_flag() {
        return ip_check_flag;
    }

    public void setIp_check_flag(String ip_check_flag) {
        this.ip_check_flag = ip_check_flag;
    }

    public char getCache_flag() {
        return cache_flag;
    }

    public void setCache_flag(char cache_flag) {
        this.cache_flag = cache_flag;
    }

    public char getLottery_flag() {
        return lottery_flag;
    }

    public void setLottery_flag(char lottery_flag) {
        this.lottery_flag = lottery_flag;
    }

    public char getLottery_desc() {
        return lottery_desc;
    }

    public void setLottery_desc(char lottery_desc) {
        this.lottery_desc = lottery_desc;
    }

    public char getGet_lottery_flag() {
        return get_lottery_flag;
    }

    public void setGet_lottery_flag(char get_lottery_flag) {
        this.get_lottery_flag = get_lottery_flag;
    }

    public char getDelete_flag() {
        return delete_flag;
    }

    public void setDelete_flag(char delete_flag) {
        this.delete_flag = delete_flag;
    }

    public char getLottery_check_flag() {
        return lottery_check_flag;
    }

    public void setLottery_check_flag(char lottery_check_flag) {
        this.lottery_check_flag = lottery_check_flag;
    }
}
