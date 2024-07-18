package com.example.duantotnghiep.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Vui lòng điền username")
    @Size(min = 8, max = 15, message = "Độ dài username phải từ 7 đến 13 ký tự")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Chỉ cho phép chữ và số")
    private String username;

    @NotBlank(message = "Vui lòng điền password")
    private String password;

}
