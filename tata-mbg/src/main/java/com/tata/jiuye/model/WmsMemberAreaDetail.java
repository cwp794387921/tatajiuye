package com.tata.jiuye.model;

import java.util.List;

public class WmsMemberAreaDetail extends WmsMember {

    private String province;

    private String pic;

    private String citys;

    private String area;

    private String address;

    private List<WmsMemberAreaDetail> areas;

    public String getProvince() {
        return province;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCitys() {
        return citys;
    }

    public void setCitys(String citys) {
        this.citys = citys;
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
