package com.tmind.ocean.entity;

import javax.persistence.*;

/**
 * Created by lijunying on 15/11/13.
 */
@Entity
@Table(name = "User")
public class UserEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;
    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;
    @Column(name="query_qrcode_table")
    private String query_qrcode_table;
    @Column(name="user_type")
    private String user_type;
    @Column(name="user_email")
    private String user_email;
    @Column(name="user_telno")
    private String user_telno;
    @Column(name="user_factory_name")
    private String user_factory_name;
    @Column(name="user_factory_address")
    private String user_factory_address;
    @Column(name="user_contact_person_name")
    private String user_contact_person_name;
    @Column(name="active_flag")
    private Integer active_flag;
    @Column(name="agency_id")
    private Integer agency_id;
    @Column(name="create_date")
    private String create_date;
    @Column(name="expire_date")
    private String expire_date;
    @Column(name="lottery_ability_flag")
    private Character lottery_ability_flag;

    public Integer getId() {
        return Id;
    }
    public void setId(Integer id) {
        Id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getQuery_qrcode_table() {
        return query_qrcode_table;
    }

    public void setQuery_qrcode_table(String query_qrcode_table) {
        this.query_qrcode_table = query_qrcode_table;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_telno() {
        return user_telno;
    }

    public void setUser_telno(String user_telno) {
        this.user_telno = user_telno;
    }

    public String getUser_factory_name() {
        return user_factory_name;
    }

    public void setUser_factory_name(String user_factory_name) {
        this.user_factory_name = user_factory_name;
    }

    public String getUser_factory_address() {
        return user_factory_address;
    }

    public void setUser_factory_address(String user_factory_address) {
        this.user_factory_address = user_factory_address;
    }

    public String getUser_contact_person_name() {
        return user_contact_person_name;
    }

    public void setUser_contact_person_name(String user_contact_person_name) {
        this.user_contact_person_name = user_contact_person_name;
    }

    public Integer getActive_flag() {
        return active_flag;
    }

    public void setActive_flag(Integer active_flag) {
        this.active_flag = active_flag;
    }

    public Integer getAgency_id() {
        return agency_id;
    }

    public void setAgency_id(Integer agency_id) {
        this.agency_id = agency_id;
    }

    public String getExpire_date() {
        return expire_date;
    }

    public void setExpire_date(String expire_date) {
        this.expire_date = expire_date;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public Character getLottery_ability_flag() {
        return lottery_ability_flag;
    }

    public void setLottery_ability_flag(Character lottery_ability_flag) {
        this.lottery_ability_flag = lottery_ability_flag;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "Id=" + Id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", query_qrcode_table='" + query_qrcode_table + '\'' +
                ", user_type='" + user_type + '\'' +
                ", user_email='" + user_email + '\'' +
                ", user_telno='" + user_telno + '\'' +
                ", user_factory_name='" + user_factory_name + '\'' +
                ", user_factory_address='" + user_factory_address + '\'' +
                ", user_contact_person_name='" + user_contact_person_name + '\'' +
                ", active_flag=" + active_flag +
                ", agency_id=" + agency_id +
                ", create_date='" + create_date + '\'' +
                ", expire_date='" + expire_date + '\'' +
                ", lottery_ability_flag=" + lottery_ability_flag +
                '}';
    }
}
