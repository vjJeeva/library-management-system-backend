package com.jeeva.Smart.Library.Management.dto.member.Request;

import com.jeeva.Smart.Library.Management.enums.MemberStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateMemberRequest {

    @NotBlank(message = "name cannot be blank")
    private String name;

    @NotBlank
    @Email(message = "should be an valid email")
    private String email;

    @NotBlank
    @Pattern(regexp = "^[0-9]{10}$|^\\+[0-9]{1,3}[0-9]{10}$", message = "Invalid phone number format")
    private String phone;

    @NotNull(message = "Status must be Active or Blocked")
    private MemberStatus status;
}
