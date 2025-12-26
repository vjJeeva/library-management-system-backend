package com.jeeva.Smart.Library.Management.service;

import com.jeeva.Smart.Library.Management.dto.member.Request.CreateMemberRequest;
import com.jeeva.Smart.Library.Management.dto.member.Request.UpdateMemberRequest;
import com.jeeva.Smart.Library.Management.dto.member.Response.MemberResponse;
import com.jeeva.Smart.Library.Management.enums.MemberStatus;
import com.jeeva.Smart.Library.Management.exception.ResourceNotFoundException;
import com.jeeva.Smart.Library.Management.mapper.MemberMapper;
import com.jeeva.Smart.Library.Management.model.Member;
import com.jeeva.Smart.Library.Management.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberMapper memberMapper;

    @InjectMocks
    private MemberService memberService;

    // --- createMember Tests ---

    @Test
    void createMember_Success() {
        CreateMemberRequest request = new CreateMemberRequest();
        request.setEmail("test@gmail.com");
        Member member = new Member();

        when(memberRepository.existsByEmail("test@gmail.com")).thenReturn(false);
        when(memberMapper.toEntity(request)).thenReturn(member);
        when(memberRepository.save(any(Member.class))).thenReturn(member);
        when(memberMapper.toResponse(member)).thenReturn(new MemberResponse());

        MemberResponse response = memberService.createMember(request);

        assertNotNull(response);
        verify(memberRepository).save(any());
    }

    @Test
    void createMember_Failure_EmailExists() {
        CreateMemberRequest request = new CreateMemberRequest();
        request.setEmail("duplicate@gmail.com");

        when(memberRepository.existsByEmail("duplicate@gmail.com")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> memberService.createMember(request));
    }

    // --- updateMember Tests ---

    @Test
    void updateMember_Success() {
        Long id = 1L;
        UpdateMemberRequest request = new UpdateMemberRequest();
        request.setName("Jeeva Updated");
        request.setStatus(MemberStatus.ACTIVE);

        Member member = new Member();
        member.setId(id);
        member.setName("Old Name");

        when(memberRepository.findById(id)).thenReturn(Optional.of(member));
        when(memberMapper.toResponse(member)).thenReturn(new MemberResponse());

        memberService.updateMember(id, request);

        assertEquals("Jeeva Updated", member.getName());
        verify(memberRepository).findById(id);
    }

    // --- updateStatus Tests ---

    @Test
    void updateStatus_Success() {
        Long id = 1L;
        Member member = new Member();
        member.setStatus(MemberStatus.ACTIVE);

        when(memberRepository.findById(id)).thenReturn(Optional.of(member));
        when(memberMapper.toResponse(member)).thenReturn(new MemberResponse());

        memberService.updateStatus(id, MemberStatus.BLOCKED);

        assertEquals(MemberStatus.BLOCKED, member.getStatus());
    }

    // --- viewMember Tests ---

    @Test
    void viewMember_Success() {
        Long id = 1L;
        Member member = new Member();
        when(memberRepository.findById(id)).thenReturn(Optional.of(member));
        when(memberMapper.toResponse(member)).thenReturn(new MemberResponse());

        assertNotNull(memberService.viewMember(id));
    }

    @Test
    void viewMember_Failure_NotFound() {
        when(memberRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> memberService.viewMember(99L));
    }

    // --- allMembers Tests (Pagination) ---

    @Test
    void allMembers_Success() {
        Page<Member> memberPage = new PageImpl<>(List.of(new Member()));
        when(memberRepository.findAll(any(Pageable.class))).thenReturn(memberPage);

        Page<MemberResponse> result = memberService.allMembers(0, 5);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
    }
}