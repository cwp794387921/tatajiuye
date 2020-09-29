package com.tata.jiuye.DTO;

import lombok.Data;

@Data
public class TemplateData {
    //keyword1：订单类型，keyword2：下单金额，keyword3：配送地址，keyword4：取件地址，keyword5备注
    private String value;

    public TemplateData(String value) {
        this.value = value;
    }

    public TemplateData() {
    }
}
