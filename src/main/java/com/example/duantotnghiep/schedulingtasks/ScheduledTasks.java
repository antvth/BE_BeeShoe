package com.example.duantotnghiep.schedulingtasks;


import com.example.duantotnghiep.service.giam_gia_service.impl.GiamGiaServiceimpl;
import com.example.duantotnghiep.service.voucher_service.impl.VoucherServiceimpl;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

// ScheduledTasks.java
@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private final GiamGiaServiceimpl giamGiaService;
    private final VoucherServiceimpl voucherService;

    @Autowired
    public ScheduledTasks(GiamGiaServiceimpl giamGiaService, VoucherServiceimpl voucherService) {
        this.giamGiaService = giamGiaService;
        this.voucherService = voucherService;
    }

    @PostConstruct
    public void init() {
        // Chạy phương thức ngay khi bean được khởi tạo
        runTask();

        // Lên lịch chạy vào 00:00 giờ mỗi ngày
        scheduleDailyTask();
    }

    private void runTask() {
        log.info("Running task to check and update GiamGia status");
        giamGiaService.checkAndSetStatus();

        log.info("Running task to check and update Voucher status");
        voucherService.checkAndSetStatus();
    }

    @Scheduled(cron = "0 0 0 * * ?") // Lịch chạy vào 00:00 giờ mỗi ngày
    private void scheduleDailyTask() {
        log.info("Running daily task at 00:00");
        log.info("Running task to check and update GiamGia status");
        giamGiaService.checkAndSetStatus();

        log.info("Running task to check and update Voucher status");
        voucherService.checkAndSetStatus();
        // Thực hiện công việc cần thiết
    }
}
