package com.edu.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.entity.account.User;
import com.edu.entity.view.StudentView;
import com.edu.entity.view.TeacherView;
import com.edu.entity.view.UserView;
import com.edu.mapper.UserMapper;
import com.edu.mapper.UserViewMapper;
import com.edu.mapper.TeacherViewMapper;
import com.edu.mapper.StudentViewMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private final UserMapper userMapper;
    private final UserViewMapper userViewMapper;
    private final TeacherViewMapper teacherViewMapper;
    private final StudentViewMapper studentViewMapper;

    public UserServiceImpl(UserMapper userMapper, UserViewMapper userViewMapper, TeacherViewMapper teacherViewMapper, StudentViewMapper studentViewMapper){
        this.userMapper = userMapper;
        this.userViewMapper = userViewMapper;
        this.teacherViewMapper = teacherViewMapper;
        this.studentViewMapper = studentViewMapper;
    }

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    /**
     * 根据用户ID查找用户信息（包含密码）
     * @param userId 用户ID
     * @return 用户对象
     */
    @Override
    public User findUserWithPasswordById(String userId){
        return userMapper.selectById(userId);
    }

    /**
     * 获取用户基本信息
     * @param userId 用户ID
     * @return 用户视图对象
     */
    @Override
    public UserView getUserInfo(String userId){
        return userViewMapper.selectById(userId);
    }

    /**
     * 获取包含用户名的用户信息
     * @param userId 用户ID
     * @return 包含用户名的用户视图对象
     */
    @Override
    public UserView getUserInfoWithUserName(String userId){
        UserView userView = userViewMapper.selectById(userId);
        if (userView == null) {
            return null;
        }
        userView.setUserName(switch (userView.getRoleName()) {
            case "teacher" -> teacherViewMapper.selectById(userId).getName();
            case "student" -> studentViewMapper.selectById(userId).getName();
            default -> null;
        });
        return userView;
    }

    /**
     * 根据用户ID获取用户角色名称
     * @param userId 用户ID
     * @return 角色名称
     */
    @Override
    public String getUserRoleNameByUserId(String userId) {
        return userViewMapper.selectById(userId).getRoleName();
    }

    /**
     * 获取教师信息
     * @param userId 用户ID
     * @return 教师视图对象
     */
    @Override
    public TeacherView getTeacherInfo(String userId) {
        return teacherViewMapper.selectById(userId);
    }

    /**
     * 获取学生信息
     * @param userId 用户ID
     * @return 学生视图对象
     */
    @Override
    public StudentView getStudentInfo(String userId) {
        return studentViewMapper.selectById(userId);
    }

    /**
     * 验证用户凭证
     * @param userId 用户ID
     * @param password 密码
     * @return 验证是否成功
     */
    @Override
    public boolean validateUserCredentials(String userId, String password) {
        // debug
        System.out.println("Validating user: " + userId);
        System.out.println("Password: " + password);

        User user = findUserWithPasswordById(userId);
        if (user == null) {
            System.out.println("User not found");
            return false;
        }
        return bCryptPasswordEncoder.matches(password, user.getEncryptedPassword());
    }

    /**
     * 获取所有用户信息（不分页）
     * @return 所有用户列表
     */
    @Override
    public List<User> getAllUsers() {
        return userMapper.selectList(new QueryWrapper<User>().select("*"));
    }
    
    /**
     * 分页获取所有用户信息
     * @param current 当前页码
     * @param size 每页记录数
     * @return 分页用户列表
     */
    @Override
    public Page<User> getUsersByPage(long current, long size) {
        Page<User> page = new Page<>(current, size);
        return userMapper.selectPage(page, new QueryWrapper<User>().select("*"));
    }

    /**
     * 创建或更新用户信息
     * @param user 用户对象
     */
    @Override
    public void createOrUpdateUser(User user) {
        userMapper.insertOrUpdate(user);
    }

    /**
     * 根据用户ID删除用户
     * @param userId 用户ID
     */
    @Override
    public void removeUserById(String userId) {
        userMapper.deleteById(userId);
    }
}
