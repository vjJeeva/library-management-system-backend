package com.jeeva.Smart.Library.Management.dto.member.Response;

import com.jeeva.Smart.Library.Management.enums.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponse {

    private Long id;

    private String name;

    private String email;

    private String phone;

    private MemberStatus status;
}
