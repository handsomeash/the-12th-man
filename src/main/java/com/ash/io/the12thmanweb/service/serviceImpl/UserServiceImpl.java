package com.ash.io.the12thmanweb.service.serviceImpl;

import com.ash.io.the12thmanweb.entity.User;
import com.ash.io.the12thmanweb.repository.UserRepository;
import com.ash.io.the12thmanweb.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-01-16
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public int save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.selectByUsernameAndPassword(username, null);

    }

    @Override
    public User findByEmail(String email) {
        return userRepository.selectByEmail(email);
    }

    @Override
    public User findById(Integer id) {
        return userRepository.selectById(id);
    }
}
