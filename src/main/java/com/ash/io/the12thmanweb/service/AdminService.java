package com.ash.io.the12thmanweb.service;

import com.ash.io.the12thmanweb.entity.admin.Admin;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-04-11
 */
public interface AdminService extends IService<Admin> {
    /**
     * 查找管理员
     *
     * @param admin
     * @return
     */
    Admin findAdmin(Admin admin);

    /**
     * 根据密码查找管理员
     *
     * @param password
     * @return
     */
    Admin findByPassword(String password);
}
