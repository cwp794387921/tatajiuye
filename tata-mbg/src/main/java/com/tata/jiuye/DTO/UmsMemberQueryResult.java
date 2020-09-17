package com.tata.jiuye.DTO;

import com.tata.jiuye.model.UmsMember;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UmsMemberQueryResult extends UmsMember {

    @ApiModelProperty(value = "直属上级")
    private String fatherMemberName;


    @ApiModelProperty(value = "间接上级")
    private String grandpaMemberName;


}
