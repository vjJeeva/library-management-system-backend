package com.jeeva.Smart.Library.Management.controller;


import com.jeeva.Smart.Library.Management.dto.member.Request.CreateMemberRequest;
import com.jeeva.Smart.Library.Management.dto.member.Request.UpdateMemberRequest;
import com.jeeva.Smart.Library.Management.dto.member.Response.MemberResponse;
import com.jeeva.Smart.Library.Management.enums.MemberStatus;
import com.jeeva.Smart.Library.Management.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/createmember")
    public ResponseEntity<MemberResponse> createNewMember (@RequestBody @Valid CreateMemberRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.createMember(request));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MemberResponse> memberUpdateRequest (@PathVariable Long id,@RequestBody @Valid UpdateMemberRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(memberService.updateMember(id,request));
    }

    @PatchMapping("/status/{id}")
    public ResponseEntity<MemberResponse> doStatusUpdate(@PathVariable Long id, @RequestBody MemberStatus status){
        return ResponseEntity.status(HttpStatus.OK).body(memberService.updateStatus(id,status));
    }

    @GetMapping("/getall")
    public ResponseEntity<Page<MemberResponse>> getAllMember (@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(memberService.allMembers(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.viewMember(id));
    }

}
