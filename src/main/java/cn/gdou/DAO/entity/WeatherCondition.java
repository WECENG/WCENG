package cn.gdou.DAO.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tb_weather")
//如果使用默认转换器就需要系列化
public class WeatherCondition implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date date;
    private double temperature;
    private String weather;
    private double windPower;

    public WeatherCondition() {
    }

    public WeatherCondition(Date date, double temperature, String weather, double windPower) {
        this.date = date;
        this.temperature = temperature;
        this.weather = weather;
        this.windPower = windPower;
    }

    public Integer getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getWeather() {
        return weather;
    }

    public double getWindPower() {
        return windPower;
    }
}
