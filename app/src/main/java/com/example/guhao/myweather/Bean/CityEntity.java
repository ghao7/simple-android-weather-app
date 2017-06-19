package com.example.guhao.myweather.Bean;

/**
 * Created by guhao on 6/15/17.
 */

public class CityEntity {
    private String area_code;
    private String city_en;
    private String city_cn;
    private String cnty_en;
    private String cnty_cn;
    private String state_en;
    private String state_cn;
    private String up_en;
    private String up_cn;

    public CityEntity(String area_code, String city_en, String city_cn, String cnty_en, String cnty_cn, String state_en, String state_cn, String up_en, String up_cn) {
        this.area_code = area_code;
        this.city_en = city_en;
        this.city_cn = city_cn;
        this.cnty_en = cnty_en;
        this.cnty_cn = cnty_cn;
        this.state_en = state_en;
        this.state_cn = state_cn;
        this.up_en = up_en;
        this.up_cn = up_cn;
    }
        
    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public String getCity_en() {
        return city_en;
    }

    public void setCity_en(String city_en) {
        this.city_en = city_en;
    }

    public String getCity_cn() {
        return city_cn;
    }

    public void setCity_cn(String city_cn) {
        this.city_cn = city_cn;
    }

    public String getCnty_en() {
        return cnty_en;
    }

    public void setCnty_en(String cnty_en) {
        this.cnty_en = cnty_en;
    }

    public String getCnty_cn() {
        return cnty_cn;
    }

    public void setCnty_cn(String cnty_cn) {
        this.cnty_cn = cnty_cn;
    }

    public String getState_en() {
        return state_en;
    }

    public void setState_en(String state_en) {
        this.state_en = state_en;
    }

    public String getState_cn() {
        return state_cn;
    }

    public void setState_cn(String state_cn) {
        this.state_cn = state_cn;
    }

    public String getUp_en() {
        return up_en;
    }

    public void setUp_en(String up_en) {
        this.up_en = up_en;
    }

    public String getUp_cn() {
        return up_cn;
    }

    public void setUp_cn(String up_cn) {
        this.up_cn = up_cn;
    }
}
