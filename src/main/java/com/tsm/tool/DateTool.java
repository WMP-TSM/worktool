package com.tsm.tool;

import com.tsm.constant.DatePool;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Description: 日期工具
 * @Author: ex_kjkfb_wenmuping
 * @Date: 2021/07/19/17:11
 * @Version: 1.0
 */
public class DateTool {

    public static String format(Date date){
        return format(DatePool.YYYY_MM_DD_HH_MM_SS, date);
    }

    public static String format(LocalDate date){
        return format(DatePool.YYYY_MM_DD_HH_MM_SS, date);
    }

    public static String format(LocalDateTime date){
        return format(DatePool.YYYY_MM_DD_HH_MM_SS, date);
    }

    public static String format(String pattern, Date date){
        return DateFormatUtils.format(date, pattern);
    }

    public static String format(String pattern, LocalDate date){
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String format(String pattern, LocalDateTime date){
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }


}
