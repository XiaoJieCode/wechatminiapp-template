package cn.wscsoft.backend.common.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UploadUtil {
    public static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static String getTodayDir() {
        return dtf.format(LocalDate.now());
    }
}
