package com.forthelight.controller;

import com.forthelight.biz.TeacherBiz;
import com.forthelight.biz.impl.TeacherBizImpl;
import com.forthelight.dao.TeacherDao;
import com.forthelight.domain.Course;
import com.forthelight.domain.Teacher;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TeacherController {
    private TeacherBiz teacherBiz;

    @Autowired
    public void setTeacherBiz(TeacherBiz teacherBiz) {
        this.teacherBiz = teacherBiz;
    }

    @RequestMapping("/teacherInfo/{teacherId}")
    @ResponseBody
    public String getTeacherInfo(@PathVariable Integer teacherId){
        Gson gson = new Gson();
        Map<String, String> rsp = new HashMap<>();

        Teacher teacher = teacherBiz.findById(teacherId);
        if(teacher == null){
            rsp.put("success", "false");
            rsp.put("data", String.format("id为 %d 教师不存在", teacherId));
        }
        else{
            List<Course> courses = teacher.getCourses();
            rsp.put("teacher", gson.toJson(teacher));
            rsp.put("courses", gson.toJson(courses));
        }

        return gson.toJson(rsp);
    }
}