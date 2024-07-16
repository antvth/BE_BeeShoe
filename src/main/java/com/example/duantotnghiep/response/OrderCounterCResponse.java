package com.example.duantotnghiep.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCounterCResponse {

    private UUID id;

    private UUID idKhach;
}
