package com.tata.jiuye.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
public class area implements Serializable{
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "省")
    private String provinces;
    @ApiModelProperty(value = "市")
    private String city;



    @ApiModelProperty(value = "县")
    private String area;

    private List<area> citys;

    private List<area> areas;

    public List<area> getCitys() {
        return citys;
    }

    public void setCitys(List<area> citys) {
        this.citys = citys;
    }

    public List<area> getAreas() {
        return areas;
    }

    public void setAreas(List<area> areas) {
        this.areas = areas;
    }

    public String getProvinces() {
        return provinces;
    }

    public void setProvinces(String provinces) {
        this.provinces = provinces;
    }

    public String getCity() {
        return city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
