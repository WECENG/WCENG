package cn.gdou.amqp.listener;

import cn.gdou.DAO.entity.WeatherCondition;
import cn.gdou.service.WeatherConditionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
@Qualifier("messageConsumer")
public class RabbitMQMessageListener {
    @Autowired
    private WeatherConditionService service;

    private static final Logger log=LoggerFactory.getLogger(
            WeatherCondition.class);

    private void receiveMessage(WeatherCondition condition){
        log.info(" The message was received");
        WeatherCondition saved=service.saveTodayWeather(condition);
        log.info("The WeatherCondition "+saved+" was saved");
    }

    @RabbitListener(containerFactory = "rabbitListenerContainer",queues ="keyB")
    private void rceiveForecast(String forecastMessage){
        log.info("Got the forecast weather message");
        System.out.println(forecastMessage);
    }
}
