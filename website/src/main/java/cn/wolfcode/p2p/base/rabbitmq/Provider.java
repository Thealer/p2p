package cn.wolfcode.p2p.base.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@SuppressWarnings("ALL")
public class Provider {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /*public static void main(String[] args) {
        System.out.println(new SimpleDateFormat("yy", Locale.CHINESE).format(new Date()));
    }*/

    public void directQueueProvider() {
        List<String> list = new ArrayList<String>();
        list.add("小明");
        list.add("小红");
        list.add("小芳");
//        User user = new User();
//        user.setName("小明");
//        user.setAge("17");
        log.info("==>开始发送消息");
        try {
            this.rabbitTemplate.convertAndSend(RabbitMqConfig.TOPIC_EXCHANGE,"topic.a.b",list);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("==>发送失败");
        }
    }

    public void sendTopicfQueue() {
        List<String> list = new ArrayList<String>();
        list.add("小刚");
//        User user = new User();
//        user.setName("小明");
//        user.setAge("17");
        log.info("==>开始发送消息1");
        try {
            this.rabbitTemplate.convertAndSend(RabbitMqConfig.TOPIC_EXCHANGE,"topic.d",list);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("==>发送失败");
        }
    }
}
