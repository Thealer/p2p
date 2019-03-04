package cn.wolfcode;

import cn.wolfcode.p2p.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Import(ApplicationConfig.class)
@PropertySource("classpath:application-web.properties")
@EnableScheduling
public class ApplicationWebconfig {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationWebconfig.class, args);
    }
}
