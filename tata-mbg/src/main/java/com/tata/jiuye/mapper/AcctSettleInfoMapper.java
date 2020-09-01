package com.tata.jiuye.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tata.jiuye.model.AcctSettleInfo;
import com.tata.jiuye.model.AcctSettleInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AcctSettleInfoMapper extends BaseMapper<AcctSettleInfo> {
    long countByExample(AcctSettleInfoExample example);

    int deleteByExample(AcctSettleInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AcctSettleInfo record);

    int insertSelective(AcctSettleInfo record);

    List<AcctSettleInfo> selectByExample(AcctSettleInfoExample example);

    AcctSettleInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") AcctSettleInfo record, @Param("example") AcctSettleInfoExample example);

    int updateByExample(@Param("record") AcctSettleInfo record, @Param("example") AcctSettleInfoExample example);

    int updateByPrimaryKeySelective(AcctSettleInfo record);

    int updateByPrimaryKey(AcctSettleInfo record);
}
