package cn.gdou.service;

import cn.gdou.DAO.entity.WeatherCondition;
import cn.gdou.DAO.repository.WeatherConditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class WeatherConditionService {
    @Autowired
    private WeatherConditionRepository wcr;

    public WeatherCondition saveTodayWeather(WeatherCondition wc){
       return wcr.save(wc);
    }

    public WeatherCondition findSomeDayWeather(Date date){
        return wcr.findByDate(date);
    }
}
