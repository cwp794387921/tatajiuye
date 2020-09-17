package com.tata.jiuye.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tata.jiuye.DTO.UmsMemberQueryParam;
import com.tata.jiuye.DTO.UmsMemberQueryResult;
import com.tata.jiuye.model.UmsMember;
import com.tata.jiuye.model.UmsMemberExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UmsMemberMapper extends BaseMapper<UmsMember> {

    long countByExample(UmsMemberExample example);

    int deleteByExample(UmsMemberExample example);

    int deleteByPrimaryKey(Long id);

    UmsMember selectById(String openid);

    UmsMember selectByUsername(String username);

    int insert(UmsMember record);

    int insertSelective(UmsMember record);

    List<UmsMember> selectByExample(UmsMemberExample example);

    UmsMember selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UmsMember record, @Param("example") UmsMemberExample example);

    int updateByExample(@Param("record") UmsMember record, @Param("example") UmsMemberExample example);

    int updateByPrimaryKeySelective(UmsMember record);

    int updateByPrimaryKey(UmsMember record);

    /**
     * 通过手机号定位用户信息
     * @param phone
     * @return
     */
    UmsMember getUmsMemberByPhone(@Param("phone")String phone);

    /**
     * 通过openID获取用户信息
     * @param openId
     * @return
     */
    UmsMember getUmsMemberByOpenId(@Param("openId")String openId);

    /**
     * 通过邀请码获取用户信息
     * @param inviteCode
     * @return
     */
    UmsMember getUmsMemberByInviteCode(@Param("inviteCode") String inviteCode);

    /**
     * 通过条件查询用户信息
     * @param param
     * @return
     */
    List<UmsMemberQueryResult> getUmsMemberByParam(@Param("param")UmsMemberQueryParam param);
}
