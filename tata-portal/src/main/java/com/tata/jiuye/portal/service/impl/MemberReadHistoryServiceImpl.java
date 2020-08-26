//package com.tata.jiuye.portal.service.impl;
//
//import com.tata.jiuye.model.UmsMember;
//import com.tata.jiuye.portal.domain.MemberReadHistory;
//import com.tata.jiuye.portal.repository.MemberReadHistoryRepository;
//import com.tata.jiuye.portal.service.MemberReadHistoryService;
//import com.tata.jiuye.portal.service.UmsMemberService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
///**
// * 会员浏览记录管理Service实现类
// * Created by macro on 2018/8/3.
// */
//@Service
//@RequiredArgsConstructor
//public class MemberReadHistoryServiceImpl implements MemberReadHistoryService {
//
//    private final UmsMemberService memberService;
//    private final MemberReadHistoryRepository memberReadHistoryRepository;
//
//    @Override
//    public int create(MemberReadHistory memberReadHistory) {
//        UmsMember member = memberService.getCurrentMember();
//        memberReadHistory.setMemberId(member.getId());
//        memberReadHistory.setMemberNickname(member.getNickname());
//        memberReadHistory.setMemberIcon(member.getIcon());
//        memberReadHistory.setId(null);
//        memberReadHistory.setCreateTime(new Date());
//        memberReadHistoryRepository.save(memberReadHistory);
//        return 1;
//    }
//
//    @Override
//    public int delete(List<String> ids) {
//        List<MemberReadHistory> deleteList = new ArrayList<>();
//        for (String id : ids) {
//            MemberReadHistory memberReadHistory = new MemberReadHistory();
//            memberReadHistory.setId(id);
//            deleteList.add(memberReadHistory);
//        }
//        memberReadHistoryRepository.deleteAll(deleteList);
//        return ids.size();
//    }
//
//    @Override
//    public Page<MemberReadHistory> list(Integer pageNum, Integer pageSize) {
//        UmsMember member = memberService.getCurrentMember();
//        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
//        return memberReadHistoryRepository.findByMemberIdOrderByCreateTimeDesc(member.getId(), pageable);
//    }
//
//    @Override
//    public void clear() {
//        UmsMember member = memberService.getCurrentMember();
//        memberReadHistoryRepository.deleteAllByMemberId(member.getId());
//    }
//}
