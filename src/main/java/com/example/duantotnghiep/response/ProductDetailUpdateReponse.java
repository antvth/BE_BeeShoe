package com.example.duantotnghiep.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailUpdateReponse {

    private UUID id;

    private String color;

    private String chatLieu;

    private Integer size;

    private Integer quantity;

    private String qrcode;

    private Integer trangThai;
}
