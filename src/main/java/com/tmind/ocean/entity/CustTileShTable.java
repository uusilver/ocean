package com.tmind.ocean.entity;

import javax.persistence.*;

/**
 * Created by lijunying on 16/7/5.
 */
@Entity
@Table(name = "cust_tile_sh_table")
public class CustTileShTable {

    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;
    @Column(name="key_t")
    private String key_t;
    @Column(name="value_t")
    private String value_t;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getKey_t() {
        return key_t;
    }

    public void setKey_t(String key_t) {
        this.key_t = key_t;
    }

    public String getValue_t() {
        return value_t;
    }

    public void setValue_t(String value_t) {
        this.value_t = value_t;
    }
}
