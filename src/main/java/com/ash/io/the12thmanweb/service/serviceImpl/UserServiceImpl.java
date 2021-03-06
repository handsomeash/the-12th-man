package com.ash.io.the12thmanweb.service.serviceImpl;

import com.ash.io.the12thmanweb.entity.user.User;
import com.ash.io.the12thmanweb.entity.user.UserArticle;
import com.ash.io.the12thmanweb.entity.user.UserCollection;
import com.ash.io.the12thmanweb.entity.user.UserDetail;
import com.ash.io.the12thmanweb.enums.CalculationEnums;
import com.ash.io.the12thmanweb.mapper.UserArticleMapper;
import com.ash.io.the12thmanweb.mapper.UserCollectionMapper;
import com.ash.io.the12thmanweb.mapper.UserMapper;
import com.ash.io.the12thmanweb.service.UserDetailService;
import com.ash.io.the12thmanweb.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @ Author  ：FengYiJie
 * @ Date    ：Created in 2020-01-16
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserCollectionMapper userCollectionMapper;

    @Autowired
    private UserArticleMapper userArticleMapper;

    @Override
    public User register(User user) {
        LocalDate now = LocalDate.now();
        //设置创建时间
        user.setRegisterDate(now);
        //设置默认头像路径
        user.setPortraitUrl("../../../static/img/touxiang.png");
        //插入用户数据
        int result = userMapper.insert(user);
        //插入用户明细数据
        if (result > 0) {
            //创建用户明细表数据
            UserDetail userDetail = new UserDetail();
            //设置用户id
            userDetail.setUserId(user.getId());
            userDetailService.save(userDetail);
        }
        return user;
    }

    @Override
    public User getByUsername(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //忽略已逻辑删除的用户
        wrapper.eq("username", username).eq("is_delete", 0);
        User user = userMapper.selectOne(wrapper);
        return user;
    }

    @Override
    public User getByEmail(String email) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email);
        User user = userMapper.selectOne(wrapper);
        return user;
    }

    @Override
    public User getByPhone(String phone) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        User user = userMapper.selectOne(wrapper);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("register_date");
        List<User> users = userMapper.selectList(wrapper);
        return users;
    }

    @Override
//    @Cacheable(value = "users", key = "#PageIndex")
    public IPage<User> findAllUsersByPage(Integer PageIndex, Integer PageSize) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0).orderByAsc("register_date");
        //pageIndex:查询第几页,pageSize:查询几条数据
        Page<User> page = new Page<>(PageIndex, PageSize);
        return userMapper.selectPage(page, wrapper);
    }

    @Override
    public boolean deleteUserById(Integer id) {
        User user = userMapper.selectById(id);
        user.setIsDelete(1);
        int i = userMapper.updateById(user);
        return i > 0;
    }


    @Override
    public boolean collectArticle(Integer userId, Integer articleId) {

        //先判断是否已经收藏
        QueryWrapper<UserCollection> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("article_id", articleId);
        UserCollection find = userCollectionMapper.selectOne(wrapper);
        if (find != null) {
            return false;
        }

        //插入一条用户收藏数据
        UserCollection userCollection = new UserCollection();
        userCollection.setArticleId(articleId);
        userCollection.setUserId(userId);
        int insert = userCollectionMapper.insert(userCollection);
        //更新用户明细表的数据，收藏文章数 字段+1
        userDetailService.changeCollectionNum(userId, CalculationEnums.ADD);
        return insert > 0;
    }

    @Override
    public boolean cancelCollectArticle(Integer userId, Integer articleId) {
        QueryWrapper<UserCollection> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("article_id", articleId);
        int delete = userCollectionMapper.delete(wrapper);
        //用户明细表收藏文章数 字段-1
        userDetailService.changeCollectionNum(userId, CalculationEnums.DELETE);
        return delete > 0;
    }

    @Override
    public List<Integer> getCollectionArticleIdByUserId(Integer userId) {
        QueryWrapper<UserCollection> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        List<UserCollection> userCollections = userCollectionMapper.selectList(wrapper);
        List<Integer> result = new ArrayList<>();
        userCollections.forEach(a -> result.add(a.getArticleId()));
        return result;
    }

    @Override
    public List<Integer> getWriteArticleIdByUserId(Integer userId) {
        QueryWrapper<UserArticle> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        List<UserArticle> userArticles = userArticleMapper.selectList(wrapper);
        List<Integer> result = new ArrayList<>();
        userArticles.forEach(a -> result.add(a.getArticleId()));
        return result;
    }

    @Override
    public void writeArticle(Integer userId, Integer articleId) {
        //创建一条用户发表文章表记录
        UserArticle userArticle = new UserArticle();
        userArticle.setUserId(userId);
        userArticle.setArticleId(articleId);
        userArticleMapper.insert(userArticle);
        //用户明细表中，发表文章数+1
        userDetailService.writeArticle(userId);
    }
}
