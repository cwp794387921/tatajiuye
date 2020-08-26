//package com.tata.jiuye.portal.service.impl;
//
//import com.tata.jiuye.model.UmsMember;
//import com.tata.jiuye.portal.domain.MemberProductCollection;
//import com.tata.jiuye.portal.repository.MemberProductCollectionRepository;
//import com.tata.jiuye.portal.service.MemberCollectionService;
//import com.tata.jiuye.portal.service.UmsMemberService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
///**
// * 会员收藏Service实现类
// * Created by macro on 2018/8/2.
// */
//@Service
//@RequiredArgsConstructor
//public class MemberCollectionServiceImpl implements MemberCollectionService {
//
//    private final UmsMemberService memberService;
//    private final MemberProductCollectionRepository productCollectionRepository;
//
//    @Override
//    public int add(MemberProductCollection productCollection) {
//        int count = 0;
//        UmsMember member = memberService.getCurrentMember();
//        productCollection.setMemberId(member.getId());
//        productCollection.setMemberNickname(member.getNickname());
//        productCollection.setMemberIcon(member.getIcon());
//        MemberProductCollection findCollection = productCollectionRepository.findByMemberIdAndProductId(productCollection.getMemberId(), productCollection.getProductId());
//        if (findCollection == null) {
//            productCollectionRepository.save(productCollection);
//            count = 1;
//        }
//        return count;
//    }
//
//    @Override
//    public int delete(Long productId) {
//        UmsMember member = memberService.getCurrentMember();
//        return productCollectionRepository.deleteByMemberIdAndProductId(member.getId(), productId);
//    }
//
//    @Override
//    public Page<MemberProductCollection> list(Integer pageNum, Integer pageSize) {
//        UmsMember member = memberService.getCurrentMember();
//        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
//        return productCollectionRepository.findByMemberId(member.getId(), pageable);
//    }
//
//    @Override
//    public MemberProductCollection detail(Long productId) {
//        UmsMember member = memberService.getCurrentMember();
//        return productCollectionRepository.findByMemberIdAndProductId(member.getId(), productId);
//    }
//
//    @Override
//    public void clear() {
//        UmsMember member = memberService.getCurrentMember();
//        productCollectionRepository.deleteAllByMemberId(member.getId());
//    }
//}
