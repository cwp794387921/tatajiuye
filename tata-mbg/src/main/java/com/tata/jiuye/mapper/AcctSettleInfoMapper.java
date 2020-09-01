package com.tata.jiuye.mapper;

import com.tata.jiuye.model.AcctSettleInfo;
import com.tata.jiuye.model.AcctSettleInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AcctSettleInfoMapper {
    long countByExample(AcctSettleInfoExample example);

    int deleteByExample(AcctSettleInfoExample example);

    int insert(AcctSettleInfo record);

    int insertSelective(AcctSettleInfo record);

    List<AcctSettleInfo> selectByExample(AcctSettleInfoExample example);

    int updateByExampleSelective(@Param("record") AcctSettleInfo record, @Param("example") AcctSettleInfoExample example);

    int updateByExample(@Param("record") AcctSettleInfo record, @Param("example") AcctSettleInfoExample example);
}