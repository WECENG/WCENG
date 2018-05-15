package cn.gdou.DAO.repository;

import cn.gdou.DAO.entity.WeatherCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface WeatherConditionRepository extends
        JpaRepository<WeatherCondition,Integer> {
    WeatherCondition findByDate(Date date);
}
