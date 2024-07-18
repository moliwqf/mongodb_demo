package com.moli.mongodb.controller;

import com.moli.mongodb.common.ReturnData;
import com.moli.mongodb.entity.PageParam;
import com.moli.mongodb.entity.PageVO;
import com.moli.mongodb.entity.Student;
import com.moli.mongodb.service.MongoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author moli
 * @time 2024-07-18 08:46:13
 */
@RestController
@RequestMapping("/mongo")
public class MongoController {

    @Resource
    private MongoService mongoService;

    @PostMapping("insertData")
    public ReturnData<?> insertData(@RequestBody Student student) {
        boolean success = mongoService.insertData(student);
        if (success) {
            return ReturnData.ok();
        }
        return ReturnData.fail();
    }

    @PostMapping("updateData")
    public ReturnData<?> updateData(@RequestBody Student student) {
        boolean success = mongoService.updateData(student);
        if (success) {
            return ReturnData.ok();
        }
        return ReturnData.fail();
    }

    @PostMapping("deleteData")
    public ReturnData<?> deleteData(@RequestParam("id") Long id) {
        boolean success = mongoService.deleteData(id);
        if (success) {
            return ReturnData.ok();
        }
        return ReturnData.fail();
    }

    @GetMapping("findDataById")
    public ReturnData<?> findDataById(@RequestParam("id") Long id) {
        Student student = mongoService.findDataById(id);
        return ReturnData.ok(student);
    }

    @PostMapping("findList")
    public ReturnData<?> findList(@RequestParam("username") String username) {
        List<Student> studentList = mongoService.findList(username);
        return ReturnData.ok(studentList);
    }

    @GetMapping("getAllTimerDesc")
    public ReturnData<?> getAllTimerDesc() {
        List<Student> studentList = mongoService.getAllTimerDesc();
        return ReturnData.ok(studentList);
    }

    @GetMapping("pageStudentList")
    public ReturnData<?> pageStudentList(PageParam pageParam, String username) {
        PageVO<Student> studentList = mongoService.pageStudentList(pageParam, username);
        return ReturnData.ok(studentList);
    }
}
