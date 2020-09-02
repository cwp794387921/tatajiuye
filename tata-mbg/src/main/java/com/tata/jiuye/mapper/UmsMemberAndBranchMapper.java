package com.tata.jiuye.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tata.jiuye.model.UmsMemberAndBranch;
import com.tata.jiuye.model.UmsMemberAndBranchExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UmsMemberAndBranchMapper extends BaseMapper<UmsMemberAndBranch> {
    long countByExample(UmsMemberAndBranchExample example);

    int deleteByExample(UmsMemberAndBranchExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UmsMemberAndBranch record);

    UmsMemberAndBranch selectById(String userName);

    int insertSelective(UmsMemberAndBranch record);

    List<UmsMemberAndBranch> selectByExample(UmsMemberAndBranchExample example);

    UmsMemberAndBranch selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UmsMemberAndBranch record, @Param("example") UmsMemberAndBranchExample example);

    int updateByExample(@Param("record") UmsMemberAndBranch record, @Param("example") UmsMemberAndBranchExample example);

    int updateByPrimaryKeySelective(UmsMemberAndBranch record);

    int updateByPrimaryKey(UmsMemberAndBranch record);

    /**
     * 通过被邀请人/被绑定人的用户ID获取关系表
     * @param memberId
     * @return
     */
    UmsMemberAndBranch getByMemberId (@Param("memberId") Long memberId);
}
