package com.tata.jiuye.model;

import java.util.List;

public class WmsMemberAreaDetail extends WmsMember {

    private String province;

    private String city;

    private String area;

    private String address;

    private List<WmsMemberAreaDetail> areas;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<WmsMemberAreaDetail> getAreas() {
        return areas;
    }

    public void setAreas(List<WmsMemberAreaDetail> areas) {
        this.areas = areas;
    }
}
