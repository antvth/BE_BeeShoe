package com.example.duantotnghiep.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoadVoucherCounterResponse {

    private UUID id;

    private BigDecimal tienGiam;
}
