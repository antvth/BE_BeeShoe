package com.example.duantotnghiep.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class LoginRequest {

    @NotBlank(message = "Không được để trống username")
    private String username;

    @NotBlank(message = "Không được để trống password")
    private String password;

}
