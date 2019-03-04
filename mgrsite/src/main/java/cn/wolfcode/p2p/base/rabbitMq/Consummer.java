package cn.wolfcode.p2p.base.rabbitMq;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@SuppressWarnings("ALL")
public class Consummer implements ChannelAwareMessageListener {

    @RabbitListener(queues = RabbitMqConfig.TOPIC_QUEUE)
    public void queue1(List<String> list, Message message, Channel channel) throws Exception{
        try {
            log.info("==>队列1开始消费消息" + list.toString());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("==>消费异常");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
    @RabbitListener(queues = RabbitMqConfig.TOPIC_QUEUE1)
    public void queue2(List<String> list, Message message, Channel channel) throws Exception{
        try {
            log.info("==>队列2开始消费消息" + list.toString());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("==>消费异常");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

    public void onMessage(Message message, Channel channel) throws Exception {

    }
}
