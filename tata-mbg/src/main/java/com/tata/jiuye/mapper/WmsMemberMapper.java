package com.tata.jiuye.mapper;

import com.tata.jiuye.model.WmsMember;
import com.tata.jiuye.model.WmsMemberAreaDetail;
import com.tata.jiuye.model.WmsMemberExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface WmsMemberMapper {
    long countByExample(WmsMemberExample example);

    int deleteByExample(WmsMemberExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WmsMember record);

    int insertSelective(WmsMember record);

    List<WmsMember> selectByExample(WmsMemberExample example);




    WmsMember selectByPrimaryKey(Long id);

    WmsMemberAreaDetail selectByUmsId(Long id);

    List<WmsMemberAreaDetail> queryAllWmsUser(Map<String,Object> params);

    List<WmsMemberAreaDetail> queryMemberDetail(Map<String,Object> params);

    int updateByExampleSelective(@Param("record") WmsMember record, @Param("example") WmsMemberExample example);

    int updateByExample(@Param("record") WmsMember record, @Param("example") WmsMemberExample example);

    int updateByPrimaryKeySelective(WmsMember record);

    int updateByPrimaryKey(WmsMember record);

    WmsMember getAvailableByMemberId(@Param("memberId")Long memberId);
}
