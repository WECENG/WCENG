package cn.gdou.DAO.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class WeatherForecast implements Serializable {
    private String forecastDesc;

    public WeatherForecast() {
        this.forecastDesc="no information";
    }

    public String getForecastDesc() {
        return forecastDesc;
    }

    public void setForecastDesc(String forecastDesc) {
        this.forecastDesc = forecastDesc;
    }
}
