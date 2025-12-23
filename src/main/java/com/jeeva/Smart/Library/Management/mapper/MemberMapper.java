package com.jeeva.Smart.Library.Management.mapper;

import com.jeeva.Smart.Library.Management.dto.member.Request.CreateMemberRequest;
import com.jeeva.Smart.Library.Management.dto.member.Response.MemberResponse;
import com.jeeva.Smart.Library.Management.model.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    @Mapping(target = "id", ignore = true)
    Member toEntity(CreateMemberRequest request);

    MemberResponse toResponse(Member member);
}
