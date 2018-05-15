package cn.gdou.amqp.producer;

import cn.gdou.DAO.entity.WeatherCondition;
import cn.gdou.service.WeatherForecastService;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("messageProducer")
public class RabbitMQMessageProducer {
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private WeatherForecastService forecastService;

    @Autowired
    @Qualifier("directBinding")
    private Binding directBinding;

    @Autowired
    @Qualifier("topicBinding")
    private Binding topicBinding;

    public void sendTodayWeather(
            WeatherCondition condition){
        System.out.println(directBinding.getRoutingKey());
        template.convertAndSend(directBinding.getRoutingKey(),
                condition);
    }

    public void getForecastWeather(){
        template.convertAndSend(topicBinding.getRoutingKey(),
                forecastService.getForecast());
    }
}
