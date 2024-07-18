package com.moli.mongodb;

import com.moli.mongodb.entity.first.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author moli
 * @time 2024-07-18 16:20:35
 */
@SpringBootTest
public class TestMain {

    @Resource(name = "firstMongo")
    private MongoTemplate mongoTemplate;

    @Test
    public void insertData() {
        List<TStudent> students = Arrays.asList(
                new TStudent(1L, "小明", 1L),
                new TStudent(2L, "小红", 2L),
                new TStudent(3L, "小菜", 2L));


        List<StudentClass> studentClasses = Arrays.asList(
                new StudentClass(1L, "三年级一班", 1L),
                new StudentClass(2L, "三年级二班", 2L));


        List<School> schools = Arrays.asList(
                new School(1L, "旺仔小学", 1L),
                new School(2L, "蒙牛小学", 1L));

        City city = new City();
        city.setId(1L);
        city.setCityName("希望市");

        try {
            mongoTemplate.insertAll(students);
            mongoTemplate.insertAll(studentClasses);
            mongoTemplate.insertAll(schools);
            mongoTemplate.save(city);
            HashMap<String, Object> map = new HashMap<>(4);
            map.put("student", students);
            map.put("studentClass", studentClasses);
            map.put("schools", schools);
            map.put("city", city);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
