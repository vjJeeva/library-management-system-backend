package com.jeeva.Smart.Library.Management.service;

import com.jeeva.Smart.Library.Management.dto.member.Request.CreateMemberRequest;
import com.jeeva.Smart.Library.Management.dto.member.Request.UpdateMemberRequest;
import com.jeeva.Smart.Library.Management.dto.member.Response.MemberResponse;
import com.jeeva.Smart.Library.Management.enums.MemberStatus;
import com.jeeva.Smart.Library.Management.exception.ResourceNotFoundException;
import com.jeeva.Smart.Library.Management.mapper.MemberMapper;
import com.jeeva.Smart.Library.Management.model.Member;
import com.jeeva.Smart.Library.Management.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Transactional
    public MemberResponse createMember (CreateMemberRequest request){

        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        Member member = memberMapper.toEntity(request);

        Member savedMember = memberRepository.save(member);
        return memberMapper.toResponse(savedMember);
    }

    @Transactional
    public MemberResponse updateMember (Long id, UpdateMemberRequest request){

        Member member=memberRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("member not found"));

        member.setName(request.getName());
        member.setPhone(request.getPhone());
        member.setStatus(request.getStatus());

        return memberMapper.toResponse(member);

    }

    @Transactional
    public MemberResponse updateStatus (Long id,MemberStatus status){

        Member member=memberRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("member not found"));

        member.setStatus(status);

        return memberMapper.toResponse(member);
    }

    public Page<MemberResponse> allMembers(int page, int size){
        Pageable pageable = PageRequest.of(page,size, Sort.by("id").ascending());
        return memberRepository.findAll(pageable).map(memberMapper::toResponse);
    }

    public MemberResponse viewMember (Long id){
        return memberMapper.toResponse(memberRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Member not found")));
    }


}
