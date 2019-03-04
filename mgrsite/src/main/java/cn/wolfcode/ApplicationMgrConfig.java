package cn.wolfcode;

import cn.wolfcode.p2p.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Import(ApplicationConfig.class)
@PropertySource("classpath:application-mgr.properties")
@EnableScheduling
//@EnableEurekaServer
public class ApplicationMgrConfig {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationMgrConfig.class, args);
    }
}
