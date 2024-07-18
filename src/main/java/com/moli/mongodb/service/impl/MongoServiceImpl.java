package com.moli.mongodb.service.impl;

import com.moli.mongodb.entity.PageParam;
import com.moli.mongodb.entity.PageVO;
import com.moli.mongodb.entity.Student;
import com.moli.mongodb.service.MongoService;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author moli
 * @time 2024-07-18 08:47:12
 */
@Service
public class MongoServiceImpl implements MongoService {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public boolean insertData(Student student) {
        student.setTimer(LocalDateTime.now());
        mongoTemplate.insert(student);
        return true;
    }

    @Override
    public boolean updateData(Student student) {
        // 通过 query 根据 id 查询出对应对象，通过 update 对象进行修改
        Query query = new Query(Criteria.where("_id").is(student.getId()));
        Update update = new Update().set("username", student.getUsername());
        long count = mongoTemplate.updateFirst(query, update, Student.class).getModifiedCount();
        return count == 1;
    }

    @Override
    public boolean deleteData(Long id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.remove(query, Student.class).getDeletedCount() == 1;
    }

    @Override
    public Student findDataById(Long id) {
        return mongoTemplate.findById(id, Student.class);
    }

    @Override
    public List<Student> findList(String username) {
        Pattern pattern = Pattern.compile("^.*" + username.trim() + ".*$", Pattern.CASE_INSENSITIVE);
        Query query = new Query(Criteria.where("username").regex(pattern));
        return mongoTemplate.find(query, Student.class);
    }

    @Override
    public List<Student> getAllTimerDesc() {
        Query query = new Query();
        query.with(Sort.by("timer").descending());
        return mongoTemplate.find(query, Student.class);
    }

    @Override
    public PageVO<Student> pageStudentList(PageParam pageParam, String username) {
        // 模糊查询 username
        Query query = new Query();
        if (StringUtils.hasLength(username)) {
            Pattern pattern = Pattern.compile("^.*" + username.trim() + ".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("username").regex(pattern));
        }
        // 查询结果
        long count = mongoTemplate.count(query, Student.class);
        if (count < 1) return PageVO.emptyResult();

        query.with(Sort.by("timer").descending());
        int start = (pageParam.getPageIndex() - 1) * pageParam.getPageSize();
        query.skip(start).limit(pageParam.getPageSize());

        List<Student> studentList = mongoTemplate.find(query, Student.class);
        return PageVO.getPageResult(studentList, pageParam.getPageIndex(), pageParam.getPageSize(), (int) count);
    }
}
