package com.edu.entity.view;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;

@Data
@TableName("v_teacher_info")
public class TeacherView {
    @TableId(value = "user_id")
    private String userId;         // 用户ID
    private String name;    // 教师姓名
    private String gender;        // 性别
    private LocalDate birthday;    // 生日
    private String phoneNumber;    // 电话号码
    private String email;          // 邮箱
    private Integer startYear;     // 入职年份
    private String departmentName; // 所在部门名称
    private String title;          // 职称
}