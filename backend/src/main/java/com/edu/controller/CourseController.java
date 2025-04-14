package com.edu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.dto.CourseRequest;
import com.edu.dto.CourseSelectRequest;
import com.edu.entity.course.Course;
import com.edu.entity.view.CourseView;
import com.edu.entity.view.CourseWithTeacherView;
import com.edu.service.CourseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程控制器，处理课程相关的HTTP请求
 * 包括课程查询、分页获取等功能
 */
@RestController
public class CourseController {
    private final CourseService courseService;
    
    /**
     * 构造函数，通过依赖注入初始化服务
     *
     * @param courseService 课程服务，处理课程相关的业务逻辑
     */
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * 分页获取所有课程信息
     * <p>
     * 该方法用于分页获取系统中所有课程的基本信息。通过CourseService调用数据访问层，
     * 检索并返回指定页码和每页记录数的课程记录。支持按院系ID列表筛选课程。
     *
     * @param current 当前页码，默认为1
     * @param size    每页记录数，默认为16
     * @param departmentIds 院系ID列表，用于筛选特定院系的课程，为空时获取所有课程
     * @return 包含分页课程信息的Page对象，包含总记录数、总页数、当前页数据等信息
     */
    @GetMapping("/course/getPage")
    public Page<CourseView> getAllCourse(
            @RequestParam(value = "current", defaultValue = "1") long current,
            @RequestParam(value = "size", defaultValue = "16") long size,
            @RequestParam(value = "departmentIds", required = false) List<String> departmentIds) {
        return courseService.getAllCoursesByPage(current, size, departmentIds);
    }

    /**
     * 根据课程ID获取课程信息及关联的教师信息
     * <p>
     * 该方法用于根据课程ID获取课程的基本信息和关联的教师信息。通过CourseService调用数据访问层，
     * 检索并返回指定课程ID的课程记录及其关联的教师信息。支持分页查询。
     *
     * @param current 当前页码，默认为1
     * @param current
     * @param size
     * @param courseId
     * @return
     */
    @GetMapping("/course/{courseId}")
    public Page<CourseWithTeacherView> getCourseWithTeachersByCourseId(
            @RequestParam(value = "current", defaultValue = "1") long current,
            @RequestParam(value = "size", defaultValue = "16") long size,
            @PathVariable Integer courseId) {
        return courseService.getCourseWithTeachersByCourseId(courseId, current, size);
    }

    /**
     * 创建或更新课程
     * <p>
     * 该方法用于创建新课程或更新现有课程的信息。通过CourseService调用数据访问层，
     * 将课程信息和教师关联关系保存到数据库。创建者ID从当前登录用户中获取。
     *
     * @param request HTTP请求对象，用于获取当前登录用户的ID
     * @param courseRequest 包含课程信息和教师ID列表的请求对象
     * @return 课程ID
     */
    @PostMapping("/course/createOrUpdate")
    public Integer createOrUpdateCourse(
            HttpServletRequest request,
            @RequestBody CourseRequest courseRequest) {
        String creatorId = (String) request.getAttribute("userId");
        return courseService.createOrUpdateCourse(
                courseRequest.course(),
                courseRequest.teacherIds(),
                creatorId);
    }

    @GetMapping("/course/{courseId}/teacherIds")
    public List<String> getCourseTeachersByCourseId(@PathVariable Integer courseId) {
        return courseService.getCourseTeachersByCourseId(courseId);
    }

    @GetMapping("/teacher/course/getPage")
    public Page<CourseWithTeacherView> getCourseWithTeachersByTeacherId(
            @RequestParam(value = "current", defaultValue = "1") long current,
            @RequestParam(value = "size", defaultValue = "16") long size,
            HttpServletRequest request) {
        String teacherId = (String) request.getAttribute("userId");
        return courseService.getCourseWithTeachersByTeacherId(teacherId, current, size);
    }

    @PostMapping("/course/select")
    public boolean selectCourse(
            HttpServletRequest request,
            @RequestBody CourseSelectRequest courseSelectRequest
    ) {
        String studentId = (String) request.getAttribute("userId");
        return courseService.selectCourse(
            courseSelectRequest.courseId(),
            studentId,
            courseSelectRequest.teacherId()
        );
    }
}
