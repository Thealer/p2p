package cn.wolfcode.p2p.base.rabbitmq;

import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMqConfig {

    /**
     * 队列
     */
    static final String DIRECT_QUEUE = "direct_queue";
    /**
     * 队列
     */
    static final String TOPIC_QUEUE = "topic.queue";

    /**
     * 交换器
     */
    public final static String DIRECT_EXCHANGE = "direct_exchange";
    /**
     * 交换器
     */
    public final static String TOPIC_EXCHANGE = "topic.exchange";

    /**
     * 绑定路由
     */
    public final static String ROUTING_KEY = "routing_key";
    /**
     * 绑定路由
     */
    public final static String TOPIC_KEY = "topic.key";

    /**
     *建立交换器
     * @return
     */
//    @Bean
//    public Exchange processExchange() {
//        return ExchangeBuilder
//                .topicExchange(TOPIC_EXCHANGE)
//                .durable(true)
//                .build();
//    }
//
//    /**
//     * 建立队列
//     * @return
//     */
//    @Bean
//    public Queue directQueue(){
//        return QueueBuilder.durable(DIRECT_QUEUE).build();
//    }
//    /**
//     * 建立队列
//     * @return
//     */
//    @Bean
//    public Queue topicQueue(){
//        return QueueBuilder.durable(TOPIC_QUEUE).build();
//    }
//
//    /**
//     * 将队列通过路由绑定到交换器
//     */
//    @Bean
//    public Binding binding(){
//        return BindingBuilder.bind(topicQueue()).to(processExchange()).with(TOPIC_KEY).noargs();
//    }

//    @Bean
//    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
//        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
//        //设置忽略声明异常
//        rabbitAdmin.setIgnoreDeclarationExceptions(true);
//        return rabbitAdmin;
//    }


}
