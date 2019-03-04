package cn.wolfcode.p2p.base.rabbitMq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@SuppressWarnings("ALL")
public class Provider {

//    @Autowired
//    private  AmqpTemplate amqpTemplate;
//
//    /*public static void main(String[] args) {
//        System.out.println(new SimpleDateFormat("yy", Locale.CHINESE).format(new Date()));
//    }*/
//
//    public void directQueueProvider() {
//        User user = new User();
//        user.setName("小明");
//        user.setAge("17");
//        log.info("==>开始发送消息");
//        try {
//            this.amqpTemplate.convertAndSend(RabbitMqConfig.TOPIC_EXCHANGE,RabbitMqConfig.TOPIC_KEY,user);
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.info("==>发送失败");
//        }
//    }
}
