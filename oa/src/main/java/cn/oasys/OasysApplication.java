package cn.oasys;

import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("cn.oasys.dao")
public class OasysApplication {
    public static void main(String[] args) {
        SpringApplication.run(OasysApplication.class,args);
    }
}
