package com.college.crushconnect.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "app_config")
public class AppConfig {

    @Id
    @Column(name = "config_key")
    private String key;

    @Column(name = "config_value", nullable = false)
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
