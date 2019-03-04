package cn.wolfcode.p2p;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@MapperScan(basePackages = "cn.wolfcode.p2p.*.mapper")
@ImportResource("classpath:application-tx.xml")
public class ApplicationConfig {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationConfig.class, args);
    }
}
