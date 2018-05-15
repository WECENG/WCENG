package cn.gdou.amqp.config;

import cn.gdou.amqp.listener.RabbitMQMessageListener;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableRabbit
@PropertySource("classpath:application.properties")
public class AMQPConfig {
    @Value("${spring.rabbitmq.host}")
    private String rabbitmqHost;
    @Value("${spring.rabbitmq.port}")
    private Integer rabbitmqPort;
    @Value("${spring.rabbitmq.username}")
    private String rabbitmqUsername;
    @Value("${spring.rabbitmq.password}")
    private String rabbitmqPassword;
    @Value("${spring.rabbitmq.queue-a}")
    private String queueNameA;
    @Value("${spring.rabbitmq.queue-b}")
    private String queueNameB;

    @Bean(name = "connectionFactory")
    public CachingConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory=
                new CachingConnectionFactory();
        connectionFactory.setHost(rabbitmqHost);
        connectionFactory.setPort(rabbitmqPort);
        connectionFactory.setUsername(rabbitmqUsername);
        connectionFactory.setPassword(rabbitmqPassword);
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(
            CachingConnectionFactory connectionFactory){
        RabbitTemplate template=new RabbitTemplate();
        template.setConnectionFactory(connectionFactory);
//        template.setQueue(queueNameA);        //不指定的话都会调用
        return template;
    }

    @Bean(name = "queueA")
    public Queue queueA(){
        return new Queue("keyA");       //他的名字其实就是routing key
    }

    @Bean(name = "queueB")
    public Queue queueB(){
        return new Queue("keyB");
    }

    /*
    *创建一个转换器，并设置转换器名称，该转换器要求消息的routing key
    * 要与binding的routing key一致，才能将消息路由到该队列上
    */
    @Bean(name = "directExchange")
    public DirectExchange directExchange(){
        return new DirectExchange(queueNameA+"-directExchange");
    }

    /*
    * 该转换器要求消息的routing key要与binding的routing key
    * 符合通配符匹配，就能将消息路由到该队列上*/
    @Bean(name = "topicExchange")
    public TopicExchange topicExchange(){
        return new TopicExchange(queueNameB+"topicExchange");
    }

    @Bean
    public Binding directBinding(@Qualifier("queueA") Queue queueA,
                                  DirectExchange directExchange){
        return BindingBuilder
                .bind(queueA)
                .to(directExchange)
                .with("keyA");           //将队列名称作为routing key,可以设置多个

    }

    @Bean(name = "topicBinding")
    public Binding topicBinding(@Qualifier("queueB") Queue queue,TopicExchange topicExchange){
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with("keyB");
    }

    @Bean(name = "listenerContainer")
    public SimpleMessageListenerContainer container(
            CachingConnectionFactory connectionFactory,
           @Qualifier("messageListener") MessageListener listener){
        SimpleMessageListenerContainer container=
                new SimpleMessageListenerContainer();
        container.setQueueNames("keyA");    //这里必须指定，不然不知道监听哪个队列,这里设置的是routing key
        container.setConnectionFactory(connectionFactory);
        container.setMessageListener(listener); //一旦监听到消息就将消息传给listener对象
        return container;
    }

    /*想要将自己创建的RabbitMQMessageListener放在SimpleMessageListenerContainer中
    * 需要通过MessageListener类封装*/
    @Bean(name = "messageListener")
    public MessageListener messageListener(
            RabbitMQMessageListener listener){
        return new MessageListenerAdapter(listener,"receiveMessage");
    }


    /*不同于上述的监听方式，该监听容器会扫得到带有@RabbitListener的方法
    * 并根据该注解所指定queue进行监听。个人喜欢这种方法*/
    @Bean(name = "rabbitListenerContainer")
    public RabbitListenerContainerFactory mqMessageListener(
            CachingConnectionFactory connectionFactory){
        SimpleRabbitListenerContainerFactory containerFactory=
                new SimpleRabbitListenerContainerFactory();
        containerFactory.setConnectionFactory(connectionFactory);
        return containerFactory;
    }
}
