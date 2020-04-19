package com.ash.io.the12thmanweb.service.serviceImpl;

import com.ash.io.the12thmanweb.entity.admin.Admin;
import com.ash.io.the12thmanweb.mapper.AdminMapper;
import com.ash.io.the12thmanweb.service.AdminService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-04-11
 */
@Slf4j
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin findAdmin(Admin admin) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("username", admin.getUsername()).eq("password", admin.getPassword());
        return adminMapper.selectOne(wrapper);
    }

    @Override
    public Admin findByPassword(String password) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("password", password);
        return adminMapper.selectOne(wrapper);
    }

}
