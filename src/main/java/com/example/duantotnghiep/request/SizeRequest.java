package com.example.duantotnghiep.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SizeRequest {

    @NotNull(message = "Size không được để trống")
    private Integer size;

    @NotNull(message = "Vui lòng chọn trạng thái")
    private Integer trangThai;
}
