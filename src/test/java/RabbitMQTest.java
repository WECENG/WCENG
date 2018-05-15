import cn.gdou.DAO.entity.WeatherCondition;
import cn.gdou.amqp.producer.RabbitMQMessageProducer;
import cn.gdou.web.config.RootConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@ContextConfiguration(classes = RootConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class RabbitMQTest {
    @Autowired
    private RabbitMQMessageProducer producer;

    @Test
    public void amqpTest()throws ParseException {
        Date date=new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
        date=dateFormat.parse(dateFormat.format(date));
        WeatherCondition condition=
                new WeatherCondition(date,20,"rind",4);
        producer.sendTodayWeather(condition);
        producer.getForecastWeather();
    }
}
