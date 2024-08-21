package com.example.duantotnghiep.repository.mua_hang_not_login_repo;

import com.example.duantotnghiep.entity.GioHangChiTiet;
import com.example.duantotnghiep.mapper.GioHangCustom_not_login;
import com.example.duantotnghiep.mapper.NameAndQuantityCart_Online;
import com.example.duantotnghiep.mapper.TongTienCustom_Online;
import com.example.duantotnghiep.mapper.not_login.loadquantity_not_login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface GioHangChiTietRepository_not_login extends JpaRepository<GioHangChiTiet, UUID> {
    //load giỏ hàng chi tiết
    @Query(value = "SELECT ghct.id,img.tenimage,sp.tensanpham,giaban,ghct.soluong,spgg.dongiakhigiam  FROM sanphamchitiet sct\n" +
            " join sanpham sp on sct.idsanpham=sp.id\n" +
            "LEFT JOIN spgiamgia spgg ON sp.id = spgg.idsanpham\n" +
            "LEFT JOIN GiamGia gg ON spgg.idgiamgia = gg.id\n" +
            "join size s on sct.idsize = s.id\n" +
            "join image img on sp.id = img.idsanpham\n" +
            "join giohangchitiet ghct on ghct.idsanphamchitiet = sct.id\n" +
            "join giohang gh on gh.id = ghct.idgiohang\n" +
            "join mausac ms on ms.id=sct.idmausac\n" +
            "where img.isdefault = 'true' and gh.id= :idgh and ghct.soluong > 0 and gh.trangthai = 1\n" +
            "AND (spgg.id IS NULL OR (spgg.id IS NOT NULL\n" +
            "AND spgg.id = (SELECT MIN(spgg_inner.id) FROM spgiamgia spgg_inner WHERE spgg_inner.idsanpham = sp.id)))\n", nativeQuery = true)
    List<GioHangCustom_not_login> loadOnGioHang(UUID idgh);

    // Tìm mục trong giỏ hàng chi tiết dựa trên idGioHang và idSanPhamChiTiet
    GioHangChiTiet findByGioHang_IdAndSanPhamChiTiet_Id(UUID idGioHang, UUID idSanPhamChiTiet);

    //load tong tien
    @Query(value = "SELECT\n" +
            "    SUM(\n" +
            "        CASE\n" +
            "            WHEN spgg.dongiakhigiam IS NOT NULL THEN spgg.dongiakhigiam * ghct.soluong\n" +
            "            ELSE sp.giaban * ghct.soluong\n" +
            "        END\n" +
            "    ) AS TongSoTien\n" +
            "FROM\n" +
            "    sanphamchitiet sct\n" +
            "    JOIN sanpham sp ON sct.idsanpham = sp.id\n" +
            "    LEFT JOIN spgiamgia spgg ON sp.id = spgg.idsanpham\n" +
            "    LEFT JOIN GiamGia gg ON spgg.idgiamgia = gg.id\n" +
            "    JOIN size s ON sct.idsize = s.id\n" +
            "    JOIN image img ON sp.id = img.idsanpham\n" +
            "    JOIN giohangchitiet ghct ON ghct.idsanphamchitiet = sct.id\n" +
            "    JOIN giohang gh ON gh.id = ghct.idgiohang\n" +
            "    JOIN mausac ms ON ms.id = sct.idmausac\n" +
            "WHERE\n" +
            "    img.isdefault = 'true' AND gh.id = :idgh\n" +
            "    AND (\n" +
            "        spgg.id IS NULL \n" +
            "        OR (\n" +
            "            spgg.id IS NOT NULL\n" +
            "            AND spgg.id = (\n" +
            "                SELECT MIN(spgg_inner.id)\n" +
            "                FROM spgiamgia spgg_inner\n" +
            "                WHERE spgg_inner.idsanpham = sp.id\n" +
            "            )\n" +
            "        )\n" +
            "    );\n", nativeQuery = true)
    List<TongTienCustom_Online> getTongTien(@Param("idgh") UUID idgh);

    @Query(value = "SELECT\n" +
            "    sp.tensanpham,\n" +
            "    SUM(CASE WHEN spgg.dongiakhigiam IS NOT NULL THEN spgg.dongiakhigiam * ghct.soluong ELSE sp.giaban * ghct.soluong END) AS tongtien\n" +
            "FROM\n" +
            "    giohang gh\n" +
            "    JOIN giohangchitiet ghct ON gh.id = ghct.idgiohang\n" +
            "    JOIN sanphamchitiet sct ON ghct.idsanphamchitiet = sct.id\n" +
            "    JOIN sanpham sp ON sct.idsanpham = sp.id\n" +
            "    LEFT JOIN spgiamgia spgg ON sp.id = spgg.idsanpham\n" +
            "    LEFT JOIN GiamGia gg ON spgg.idgiamgia = gg.id\n" +
            "WHERE\n" +
            "    gh.id = :idgh AND gh.trangthai = 1\n" +
            "    AND (spgg.id IS NULL OR (spgg.id IS NOT NULL AND spgg.id = (SELECT MIN(spgg_inner.id) FROM spgiamgia spgg_inner WHERE spgg_inner.idsanpham = sp.id)))\n" +
            "GROUP BY\n" +
            "    sp.tensanpham;\n", nativeQuery = true)
    List<NameAndQuantityCart_Online> getNameAndQuantity(@Param("idgh") UUID idgh);

    @Query(value = "SELECT\n" +
            "    COUNT(*) AS tongSoLuong\n" +
            "FROM giohang gh\n" +
            "JOIN giohangchitiet ghct ON gh.id = ghct.idgiohang\n" +
            "JOIN sanphamchitiet sct ON ghct.idsanphamchitiet = sct.id\n" +
            "JOIN sanpham sp ON sct.idsanpham = sp.id\n" +
            "WHERE gh.id = :idgh AND gh.trangthai = 1;\n", nativeQuery = true)
    loadquantity_not_login getQuanTiTyAll(@Param("idgh") UUID idgh);

}
