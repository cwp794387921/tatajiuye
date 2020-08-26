//package com.tata.jiuye.portal.service.impl;
//
//import com.tata.jiuye.model.UmsMember;
//import com.tata.jiuye.portal.domain.MemberBrandAttention;
//import com.tata.jiuye.portal.repository.MemberBrandAttentionRepository;
//import com.tata.jiuye.portal.service.MemberAttentionService;
//import com.tata.jiuye.portal.service.UmsMemberService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//
///**
// * 会员关注Service实现类
// * Created by macro on 2018/8/2.
// */
//@Service
//@RequiredArgsConstructor
//public class MemberAttentionServiceImpl implements MemberAttentionService {
//
//    private final UmsMemberService memberService;
//    private final MemberBrandAttentionRepository memberBrandAttentionRepository;
//
//    @Override
//    public int add(MemberBrandAttention memberBrandAttention) {
//        int count = 0;
//        UmsMember member = memberService.getCurrentMember();
//        memberBrandAttention.setMemberId(member.getId());
//        memberBrandAttention.setMemberNickname(member.getNickname());
//        memberBrandAttention.setMemberIcon(member.getIcon());
//        memberBrandAttention.setCreateTime(new Date());
//        MemberBrandAttention findAttention = memberBrandAttentionRepository.findByMemberIdAndBrandId(memberBrandAttention.getMemberId(), memberBrandAttention.getBrandId());
//        if (findAttention == null) {
//            memberBrandAttentionRepository.save(memberBrandAttention);
//            count = 1;
//        }
//        return count;
//    }
//
//    @Override
//    public int delete(Long brandId) {
//        UmsMember member = memberService.getCurrentMember();
//        return memberBrandAttentionRepository.deleteByMemberIdAndBrandId(member.getId(), brandId);
//    }
//
//    @Override
//    public Page<MemberBrandAttention> list(Integer pageNum, Integer pageSize) {
//        UmsMember member = memberService.getCurrentMember();
//        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
//        return memberBrandAttentionRepository.findByMemberId(member.getId(), pageable);
//    }
//
//    @Override
//    public MemberBrandAttention detail(Long brandId) {
//        UmsMember member = memberService.getCurrentMember();
//        return memberBrandAttentionRepository.findByMemberIdAndBrandId(member.getId(), brandId);
//    }
//
//    @Override
//    public void clear() {
//        UmsMember member = memberService.getCurrentMember();
//        memberBrandAttentionRepository.deleteAllByMemberId(member.getId());
//    }
//}
