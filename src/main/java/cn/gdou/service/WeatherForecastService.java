package cn.gdou.service;

import cn.gdou.DAO.entity.WeatherForecast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherForecastService {
    @Autowired
    WeatherForecast forecast;

    public String getForecast(){
        return forecast.getForecastDesc();
    }
}
