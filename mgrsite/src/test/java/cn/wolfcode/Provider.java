package cn.wolfcode;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Provider {

    @Autowired
    private  AmqpTemplate amqpTemplate;

    /*public static void main(String[] args) {
        System.out.println(new SimpleDateFormat("yy", Locale.CHINESE).format(new Date()));
    }*/

    @Test
    public void directQueueProvider() {
        User user = new User();
        user.setName("小明");
        user.setAge("17");
        log.info("==>开始发送directQueue消息");
        this.amqpTemplate.convertAndSend(RabbitMqConfig.DIRECT_QUEUE,user);
    }
}
