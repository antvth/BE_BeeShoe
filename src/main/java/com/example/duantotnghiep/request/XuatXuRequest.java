package com.example.duantotnghiep.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class XuatXuRequest {

    @NotBlank(message = "Tên xuất xứ không được để trống")
    private String tenXuatXu;

    @NotNull(message = "Vui lòng chọn trạng thái")
    private Integer trangThai;

}
