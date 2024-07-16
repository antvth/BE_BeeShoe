package com.example.duantotnghiep.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCounterResponse {

    private UUID idGioHang;

    private UUID idHoaDon;

}
