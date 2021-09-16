package com.tsm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description: 工作工具箱
 * @Author: ex_kjkfb_wenmuping
 * @Date: 2021/07/15/16:45
 * @Version: 1.0
 */
@SpringBootApplication
public class WorkToolApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkToolApplication.class, args);
        System.out.println("WorkToolApplication--->启动成功");
    }
}
