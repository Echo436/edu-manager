package com.edu.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edu.entity.account.User;
import com.edu.mapper.UserMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    private final UserMapper userMapper;
    public UserServiceImpl(UserMapper userMapper){ this.userMapper = userMapper; }

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public User findUserById(String userId){
        return userMapper.selectOne(new QueryWrapper<User>().eq("user_id", userId));
    }

    @Override
    public boolean validateUser(String userId, String password) {
        System.out.println("Validating user: " + userId);
        System.out.println("Password: " + password);
        User user = findUserById(userId);
        if (user == null) {
            System.out.println("User not found");
            return false;
        }
        return bCryptPasswordEncoder.matches(password, user.getEncryptedPassword());
    }
}
