package com.tata.jiuye.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tata.jiuye.DTO.WithdrawExamineQueryParam;
import com.tata.jiuye.DTO.WithdrawExamineQueryResult;
import com.tata.jiuye.model.WithdrawalExamine;
import com.tata.jiuye.model.WithdrawalExamineExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface WithdrawalExamineMapper extends BaseMapper<WithdrawalExamine> {
    long countByExample(WithdrawalExamineExample example);

    int deleteByExample(WithdrawalExamineExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WithdrawalExamine record);

    int insertSelective(WithdrawalExamine record);

    List<WithdrawalExamine> selectByExample(WithdrawalExamineExample example);

    WithdrawalExamine selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WithdrawalExamine record, @Param("example") WithdrawalExamineExample example);

    int updateByExample(@Param("record") WithdrawalExamine record, @Param("example") WithdrawalExamineExample example);

    int updateByPrimaryKeySelective(WithdrawalExamine record);

    int updateByPrimaryKey(WithdrawalExamine record);

    /**
     * 获取我的提现审批申请表
     * @param memberId              用户ID
     * @return
     */
    List<WithdrawExamineQueryResult> getMyWithdrawalExamineList(@Param("applicantMemberId")Long memberId);

    /**
     * 通过查询参数查询提现申请表
     * @param queryParam
     * @return
     */
    List<WithdrawExamineQueryResult> getAllWithdrawalExamineListByQueryParam(@Param("param") WithdrawExamineQueryParam queryParam);

    List<WithdrawExamineQueryResult> queryList(Map<String,Object>params);

}
