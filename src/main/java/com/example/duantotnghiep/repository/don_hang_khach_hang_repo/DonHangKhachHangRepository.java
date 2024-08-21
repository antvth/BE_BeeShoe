package com.example.duantotnghiep.repository.don_hang_khach_hang_repo;

import com.example.duantotnghiep.entity.HoaDon;
import com.example.duantotnghiep.mapper.don_hang_khach_hang.DonHangKhachHangMap;
import com.example.duantotnghiep.response.HoaDonDTOResponse;
import com.example.duantotnghiep.response.HoaDonResponse;
import com.example.duantotnghiep.response.IdGioHangResponse;
import com.example.duantotnghiep.response.OrderCounterCartsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DonHangKhachHangRepository extends JpaRepository<HoaDon, UUID> {
    @Query(value = "WITH CTE AS (\n" +
            "    SELECT \n" +
            "        hd.id AS idhoadon,\n" +
            "        hd.ma AS mahoadon,\n" +
            "        sp.id AS idsanpham,\n" +
            "        sp.tensanpham,\n" +
            "        img.tenimage,\n" +
            "        s.size,\n" +
            "        cl.tenchatlieu,\n" +
            "        ms.tenmausac,\n" +
            "        hdct.soluong,\n" +
            "        hdct.dongia,\n" +
            "        hdct.dongiasaugiam,\n" +
            "        hd.thanhtien AS thanhtien,\n" +
            "        SUM(hdct.soluong) OVER (PARTITION BY hd.id) AS tongsoluong,\n" +
            "        tthd.trangthai AS trangthaihoadon,\n" +
            "        ROW_NUMBER() OVER (PARTITION BY hd.id ORDER BY hdct.id) AS RowNum,\n" +
            "        tthd.thoigian AS thoigian -- Chọn trường thoigian vào CTE\n" +
            "    FROM \n" +
            "        hoadon hd \n" +
            "        JOIN hoadonchitiet hdct ON hd.id = hdct.idhoadon \n" +
            "        JOIN sanphamchitiet spct ON hdct.idsanphamchitiet = spct.id \n" +
            "        JOIN sanpham sp ON spct.idsanpham = sp.id\n" +
            "        JOIN danhmuc dm ON sp.iddanhmuc = dm.id \n" +
            "        JOIN thuonghieu th ON sp.idthuonghieu = th.id \n" +
            "        JOIN xuatxu xx ON sp.idxuatxu = xx.id \n" +
            "        JOIN image img ON sp.id = img.idsanpham\n" +
            "        JOIN kieude kd ON sp.idkieude = kd.id \n" +
            "        JOIN size s ON spct.idsize = s.id \n" +
            "        JOIN mausac ms ON spct.idmausac = ms.id \n" +
            "        JOIN chatlieu cl ON spct.idchatlieu = cl.id\n" +
            "        JOIN trangthaihoadon tthd ON tthd.idhoadon = hd.id \n" +
            "        JOIN taikhoan tk ON tk.id = hd.idkhachhang\n" +
            "    WHERE \n" +
            "        img.isdefault = 'true' \n" +
            "        AND tk.username = :username \n" +
            "        AND tthd.trangthai = :trangthai  \n" +
            "        AND hdct.trangthai = :trangthai \n" +
            "        AND hd.trangthai = :trangthai\n" +
            ")\n" +
            "SELECT \n" +
            "    idhoadon,\n" +
            "    mahoadon,\n" +
            "    idsanpham,\n" +
            "    tensanpham,\n" +
            "    tenimage,\n" +
            "    size,\n" +
            "    tenchatlieu,\n" +
            "    tenmausac,\n" +
            "    soluong,\n" +
            "    dongia,\n" +
            "    dongiasaugiam,\n" +
            "    thanhtien,\n" +
            "    tongsoluong, \n" +
            "    trangthaihoadon \n" +
            "FROM \n" +
            "    CTE \n" +
            "WHERE \n" +
            "    RowNum = 1 AND NOT EXISTS (\n" +
            "        SELECT 1\n" +
            "        FROM trangthaihoadon tthd\n" +
            "        WHERE tthd.idhoadon = CTE.idhoadon\n" +
            "        AND tthd.trangthai = 7 -- Trạng thái của đơn trả hàng\n" +
            "    ) \n" +
            "ORDER BY \n" +
            "    thoigian DESC; -- Sắp xếp theo thoigian\n",nativeQuery = true)
    List<DonHangKhachHangMap> filterStatus(String username,Integer trangthai);

    @Query(value = "WITH CTE AS (SELECT hd.id AS idhoadon,hd.ma AS mahoadon,sp.id AS idsanpham,sp.tensanpham,img.tenimage,s.size,cl.tenchatlieu,ms.tenmausac,hdct.soluong,hdct.dongia,hdct.dongiasaugiam,hd.thanhtien as thanhtien,\n" +
            "                            SUM(hdct.soluong) OVER (PARTITION BY hd.id) AS tongsoluong,\n" +
            "                            ROW_NUMBER() OVER (PARTITION BY hd.id ORDER BY hdct.id) AS RowNum\n" +
            "                            FROM hoadon hd JOIN hoadonchitiet hdct ON hd.id = hdct.idhoadon JOIN sanphamchitiet spct ON hdct.idsanphamchitiet = spct.id JOIN sanpham sp ON spct.idsanpham = sp.id\n" +
            "                            JOIN danhmuc dm ON sp.iddanhmuc = dm.id JOIN thuonghieu th ON sp.idthuonghieu = th.id JOIN xuatxu xx ON sp.idxuatxu = xx.id JOIN image img ON sp.id = img.idsanpham\n" +
            "                            JOIN kieude kd ON sp.idkieude = kd.id JOIN size s ON spct.idsize = s.id JOIN mausac ms ON spct.idmausac = ms.id JOIN chatlieu cl ON spct.idchatlieu = cl.id\n" +
            "                            JOIN trangthaihoadon tthd ON tthd.idhoadon = hd.id JOIN taikhoan tk ON tk.id = hd.idkhachhang\n" +
            "                            WHERE img.isdefault = 'true' AND tk.username = :username AND tthd.trangthai = :trangthai  AND hdct.trangthai = :trangthai AND hd.trangthai = :trangthai) \n" +
            "                        SELECT idhoadon,mahoadon,idsanpham,tensanpham,tenimage,size,tenchatlieu,tenmausac,soluong,dongia,dongiasaugiam,thanhtien,tongsoluong FROM CTE WHERE RowNum = 1\n" +
            "                        AND (tensanpham LIKE %:tensanpham% OR mahoadon LIKE %:mahoadon%)\n",nativeQuery = true)
    List<DonHangKhachHangMap> searchByMaOrName(@Param("username") String username,@Param("trangthai") String trangthai, @Param("tensanpham") String tensanpham, @Param("mahoadon") String mahoadon);

    @Query(value = "WITH CTE AS (SELECT hd.id AS idhoadon,hd.ma AS mahoadon,sp.id AS idsanpham,sp.tensanpham,img.tenimage,s.size,cl.tenchatlieu,ms.tenmausac,hdct.soluong,hdct.dongia,hdct.dongiasaugiam,hd.thanhtien as thanhtien,\n" +
            "                            SUM(hdct.soluong) OVER (PARTITION BY hd.id) AS tongsoluong,\n" +
            "                            ROW_NUMBER() OVER (PARTITION BY hd.id ORDER BY hdct.id) AS RowNum\n" +
            "                            FROM hoadon hd JOIN hoadonchitiet hdct ON hd.id = hdct.idhoadon JOIN sanphamchitiet spct ON hdct.idsanphamchitiet = spct.id JOIN sanpham sp ON spct.idsanpham = sp.id\n" +
            "                            JOIN danhmuc dm ON sp.iddanhmuc = dm.id JOIN thuonghieu th ON sp.idthuonghieu = th.id JOIN xuatxu xx ON sp.idxuatxu = xx.id JOIN image img ON sp.id = img.idsanpham\n" +
            "                            JOIN kieude kd ON sp.idkieude = kd.id JOIN size s ON spct.idsize = s.id JOIN mausac ms ON spct.idmausac = ms.id JOIN chatlieu cl ON spct.idchatlieu = cl.id\n" +
            "                            JOIN trangthaihoadon tthd ON tthd.idhoadon = hd.id JOIN taikhoan tk ON tk.id = hd.idkhachhang\n" +
            "                            WHERE img.isdefault = 'true' AND tk.username = :username AND tthd.trangthai = 5  AND hdct.trangthai = 7\n" +
            "                        )\n" +
            "                        SELECT idhoadon,mahoadon,idsanpham,tensanpham,tenimage,size,tenchatlieu,tenmausac,soluong,dongia,dongiasaugiam,thanhtien,tongsoluong FROM CTE WHERE RowNum = 1\n",nativeQuery = true)
    List<DonHangKhachHangMap> loadTraHang(String username);

    @Query(value = "WITH CTE AS (SELECT hd.id AS idhoadon,hd.ma AS mahoadon,sp.id AS idsanpham,sp.tensanpham,img.tenimage,s.size,cl.tenchatlieu,ms.tenmausac,hdct.soluong,hdct.dongia,hdct.dongiasaugiam,hd.thanhtien as thanhtien,\n" +
            "                            SUM(hdct.soluong) OVER (PARTITION BY hd.id) AS tongsoluong,\n" +
            "                            ROW_NUMBER() OVER (PARTITION BY hd.id ORDER BY hdct.id) AS RowNum\n" +
            "                            FROM hoadon hd JOIN hoadonchitiet hdct ON hd.id = hdct.idhoadon JOIN sanphamchitiet spct ON hdct.idsanphamchitiet = spct.id JOIN sanpham sp ON spct.idsanpham = sp.id\n" +
            "                            JOIN danhmuc dm ON sp.iddanhmuc = dm.id JOIN thuonghieu th ON sp.idthuonghieu = th.id JOIN xuatxu xx ON sp.idxuatxu = xx.id JOIN image img ON sp.id = img.idsanpham\n" +
            "                            JOIN kieude kd ON sp.idkieude = kd.id JOIN size s ON spct.idsize = s.id JOIN mausac ms ON spct.idmausac = ms.id JOIN chatlieu cl ON spct.idchatlieu = cl.id\n" +
            "                            JOIN trangthaihoadon tthd ON tthd.idhoadon = hd.id JOIN taikhoan tk ON tk.id = hd.idkhachhang\n" +
            "                            WHERE img.isdefault = 'true' AND tk.username = :username AND tthd.trangthai = 5  AND hdct.trangthai = 7) \n" +
            "                        SELECT idhoadon,mahoadon,idsanpham,tensanpham,tenimage,size,tenchatlieu,tenmausac,soluong,dongia,dongiasaugiam,thanhtien,tongsoluong FROM CTE WHERE RowNum = 1\n" +
            "                        AND (tensanpham LIKE %:tensanpham% OR mahoadon LIKE %:mahoadon%)\n",nativeQuery = true)
    List<DonHangKhachHangMap> searchByMaOrNameTraHang(@Param("username") String username, @Param("tensanpham") String tensanpham, @Param("mahoadon") String mahoadon);

    @Query(value = "WITH CTE AS (SELECT hd.id AS idhoadon,hd.ma AS mahoadon,sp.id AS idsanpham,sp.tensanpham,img.tenimage,s.size,cl.tenchatlieu,ms.tenmausac,hdct.soluong,hdct.dongia,hdct.dongiasaugiam,hd.thanhtien as thanhtien,\n" +
            "                            SUM(hdct.soluong) OVER (PARTITION BY hd.id) AS tongsoluong,\n" +
            "                            ROW_NUMBER() OVER (PARTITION BY hd.id ORDER BY hdct.id) AS RowNum\n" +
            "                            FROM hoadon hd JOIN hoadonchitiet hdct ON hd.id = hdct.idhoadon JOIN sanphamchitiet spct ON hdct.idsanphamchitiet = spct.id JOIN sanpham sp ON spct.idsanpham = sp.id\n" +
            "                            JOIN danhmuc dm ON sp.iddanhmuc = dm.id JOIN thuonghieu th ON sp.idthuonghieu = th.id JOIN xuatxu xx ON sp.idxuatxu = xx.id JOIN image img ON sp.id = img.idsanpham\n" +
            "                            JOIN kieude kd ON sp.idkieude = kd.id JOIN size s ON spct.idsize = s.id JOIN mausac ms ON spct.idmausac = ms.id JOIN chatlieu cl ON spct.idchatlieu = cl.id\n" +
            "                            JOIN trangthaihoadon tthd ON tthd.idhoadon = hd.id JOIN taikhoan tk ON tk.id = hd.idkhachhang\n" +
            "                            WHERE img.isdefault = 'true' AND tk.username = :username AND tthd.trangthai = 6\n" +
            "                        )\n" +
            "                        SELECT idhoadon,mahoadon,idsanpham,tensanpham,tenimage,size,tenchatlieu,tenmausac,soluong,dongia,dongiasaugiam,thanhtien,tongsoluong FROM CTE WHERE RowNum = 1\n",nativeQuery = true)
    List<DonHangKhachHangMap> loadDonHuy(String username);

    @Query(value = "WITH CTE AS (SELECT hd.id AS idhoadon,hd.ma AS mahoadon,sp.id AS idsanpham,sp.tensanpham,img.tenimage,s.size,cl.tenchatlieu,ms.tenmausac,hdct.soluong,hdct.dongia,hdct.dongiasaugiam,hd.thanhtien as thanhtien,\n" +
            "                            SUM(hdct.soluong) OVER (PARTITION BY hd.id) AS tongsoluong,\n" +
            "                            ROW_NUMBER() OVER (PARTITION BY hd.id ORDER BY hdct.id) AS RowNum\n" +
            "                            FROM hoadon hd JOIN hoadonchitiet hdct ON hd.id = hdct.idhoadon JOIN sanphamchitiet spct ON hdct.idsanphamchitiet = spct.id JOIN sanpham sp ON spct.idsanpham = sp.id\n" +
            "                            JOIN danhmuc dm ON sp.iddanhmuc = dm.id JOIN thuonghieu th ON sp.idthuonghieu = th.id JOIN xuatxu xx ON sp.idxuatxu = xx.id JOIN image img ON sp.id = img.idsanpham\n" +
            "                            JOIN kieude kd ON sp.idkieude = kd.id JOIN size s ON spct.idsize = s.id JOIN mausac ms ON spct.idmausac = ms.id JOIN chatlieu cl ON spct.idchatlieu = cl.id\n" +
            "                            JOIN trangthaihoadon tthd ON tthd.idhoadon = hd.id JOIN taikhoan tk ON tk.id = hd.idkhachhang\n" +
            "                            WHERE img.isdefault = 'true' AND tk.username = :username AND tthd.trangthai = 6) \n" +
            "                        SELECT idhoadon,mahoadon,idsanpham,tensanpham,tenimage,size,tenchatlieu,tenmausac,soluong,dongia,dongiasaugiam,thanhtien,tongsoluong FROM CTE WHERE RowNum = 1\n" +
            "                        AND (tensanpham LIKE %:tensanpham% OR mahoadon LIKE %:mahoadon%)\n",nativeQuery = true)
    List<DonHangKhachHangMap> searchByMaOrNameDonHuy(@Param("username") String username, @Param("tensanpham") String tensanpham, @Param("mahoadon") String mahoadon);
}
