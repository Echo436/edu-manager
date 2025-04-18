package com.edu.entity.view;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;

@Data
@TableName("v_student_info")
public class StudentView {
    @TableId(value = "user_id")
    private String userId;         // 用户ID
    private String name;    // 学生姓名
    private String gender;        // 性别
    private LocalDate birthday;    // 生日
    private String phoneNumber;    // 电话号码
    private String email;          // 邮箱
    private Integer startYear;     // 入学年份
    private String departmentName; // 学院名称
    private String major;          // 专业名称
    private Double credit;         // 学分
} 