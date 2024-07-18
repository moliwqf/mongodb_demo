package com.moli.mongodb.service;

import com.moli.mongodb.entity.PageParam;
import com.moli.mongodb.entity.PageVO;
import com.moli.mongodb.entity.StudentCountVo;
import com.moli.mongodb.entity.first.Student;
import com.moli.mongodb.entity.second.Teacher;

import java.util.List;

/**
 * @author moli
 * @time 2024-07-18 08:46:50
 * @description 操作mongodb
 */
public interface MongoService {

    boolean insertData(Student student);

    boolean updateData(Student student);

    boolean deleteData(Long id);

    Student findDataById(Long id);

    List<Student> findList(String username);

    List<Student> getAllTimerDesc();

    PageVO<Student> pageStudentList(PageParam pageParam, String username);

    boolean insertTeacher(Teacher teacher);

    StudentCountVo countStudentByMonth(int month);

    Object moreToOne();
}
