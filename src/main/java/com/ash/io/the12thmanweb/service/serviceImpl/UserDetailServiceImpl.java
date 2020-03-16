package com.ash.io.the12thmanweb.service.serviceImpl;

import com.ash.io.the12thmanweb.entity.user.UserDetail;
import com.ash.io.the12thmanweb.enums.CalculationEnums;
import com.ash.io.the12thmanweb.mapper.UserDetailMapper;
import com.ash.io.the12thmanweb.service.UserDetailService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-03-16
 */
@Slf4j
@Service
public class UserDetailServiceImpl extends ServiceImpl<UserDetailMapper, UserDetail> implements UserDetailService {
    @Autowired
    UserDetailMapper userDetailMapper;

    @Override
    @Cacheable(value = "userDetail", key = "#userId")
    public UserDetail getDetailByUserId(Integer userId) {
        QueryWrapper<UserDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        UserDetail userDetail = userDetailMapper.selectOne(wrapper);
        return userDetail;
    }

    @Override
    @CachePut(value = "userDetail", key = "#userId")
    public UserDetail collectArticle(Integer userId, CalculationEnums enums) {
        UserDetail userDetail = getDetailByUserId(userId);
        if(enums == CalculationEnums.ADD){
            userDetail.setCollectionNum(userDetail.getCollectionNum() + 1);
        }else{
            userDetail.setCollectionNum(userDetail.getCollectionNum() - 1);
        }
        UpdateWrapper<UserDetail> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", userDetail.getId());
        userDetailMapper.update(userDetail, updateWrapper);
        return userDetail;
    }
}
