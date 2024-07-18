package com.moli.mongodb.service.impl;

import com.moli.mongodb.entity.PageParam;
import com.moli.mongodb.entity.PageVO;
import com.moli.mongodb.entity.StudentCountVo;
import com.moli.mongodb.entity.first.Student;
import com.moli.mongodb.entity.second.Teacher;
import com.moli.mongodb.service.MongoService;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author moli
 * @time 2024-07-18 08:47:12
 */
@Service
public class MongoServiceImpl implements MongoService {

    @Resource(name = "firstMongo")
    private MongoTemplate mongoTemplate;

    @Resource(name = "secondMongo")
    private MongoTemplate secondMongo;

    @Override
    public boolean insertData(Student student) {
        student.setTimer(LocalDateTime.now());
        mongoTemplate.insert(student);
        return true;
    }

    @Override
    // 这种方式事务管理器只负责回滚第一个数据源的事务操作 -> chainedTransactionManager
//    @Transactional(transactionManager = "firstTransactionManager", rollbackFor = Exception.class)
    @Transactional(transactionManager = "chainedTransactionManager", rollbackFor = Exception.class)
    public boolean updateData(Student student) {
        // 通过 query 根据 id 查询出对应对象，通过 update 对象进行修改
        Query query = new Query(Criteria.where("_id").is(student.getId()));
        Update update = new Update().set("username", student.getUsername());
        long count = mongoTemplate.updateFirst(query, update, Student.class).getModifiedCount();
        return count == 1;
    }

    @Override
    @Transactional(transactionManager = "firstTransactionManager", rollbackFor = Exception.class)
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

    @Override
    public boolean insertTeacher(Teacher teacher) {
        teacher.setTimer(LocalDateTime.now());
        secondMongo.insert(teacher);
        return true;
    }

    @Override
    public StudentCountVo countStudentByMonth(int month) {
        List<AggregationOperation> operationList = new ArrayList<>();
        ProjectionOperation createTime = Aggregation.project()
                .andExpression("substr(timer,5,2)")
                .as("createMonth");
        operationList.add(createTime);
        operationList.add(Aggregation.match(Criteria.where("createMonth").is(month < 10 ? "0" + month : month)));
        operationList.add(Aggregation.count().as("count"));
//        operationList.add(Aggregation.group("createTime").count().as("count"));

        Aggregation aggregation = Aggregation.newAggregation(operationList);
        AggregationResults<StudentCountVo> countVos = mongoTemplate.aggregate(aggregation, "student", StudentCountVo.class);

        List<StudentCountVo> results = countVos.getMappedResults();
        return results.get(0);
    }

    @Override
    public Object moreToOne() {
        // city - school
        LookupOperation schoolLookup = LookupOperation.newLookup()
                // 从表
                .from("school")
                // 从表中与主表关联的字段
                .localField("cityId")
                // 主表的字段
                .foreignField("_id")
                .as("school");
        // school - stuClass
        LookupOperation classLookup = LookupOperation.newLookup()
                // 从表
                .from("studentClass")
                // 从表中与主表关联的字段
                .localField("schoolId")
                // 主表的字段
                .foreignField("school._id")
                .as("class");
        // stuClass - stu
        LookupOperation stuLookup = LookupOperation.newLookup()
                // 从表
                .from("tStudent")
                // 从表中与主表关联的字段
                .localField("classId")
                // 主表的字段
                .foreignField("class._id")
                .as("student");
        Aggregation agg = Aggregation.newAggregation(schoolLookup, classLookup, stuLookup);
        try {
            AggregationResults<Map> studentAggregation = mongoTemplate.aggregate(agg, "city", Map.class);
            return studentAggregation.getMappedResults();
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
