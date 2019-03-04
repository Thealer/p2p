package cn.wolfcode;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consummer {

    @RabbitListener(queues = RabbitMqConfig.DIRECT_QUEUE)
    public void consummerDirectQueue(User user){
        log.info("==>开始消费directQueue消息" + user.toString());
    }
}
