package cn.wolfcode;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMqConfig {
    static final String DIRECT_QUEUE = "direct_queue";

    @Bean
    public Queue topicQueue(){
        return new Queue(DIRECT_QUEUE,true);
    }
}
