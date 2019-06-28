package com.cqyc.shixun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cqyc.shixun.comm.MD5Hash;
import com.cqyc.shixun.comm.MyPage;
import com.cqyc.shixun.comm.exception.ExceptionEnums;
import com.cqyc.shixun.comm.exception.YcException;
import com.cqyc.shixun.domain.SysUser;
import com.cqyc.shixun.mapper.SysUserMapper;
import com.cqyc.shixun.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cqyc
 * @since 2019-06-17
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private SysUserMapper userMapper;

    /**
     *  注册用户
     */
    @Transactional
    public void registerUser(SysUser sysUser) {
        if(StringUtils.isBlank(sysUser.getUserType())){
            sysUser.setUserType("0");
        }
        sysUser.setPasswrod(MD5Hash.addSalt(sysUser.getPasswrod(),sysUser.getLoginName()));
        int insert = userMapper.insert(sysUser);
        if (insert != 1) {
            throw new YcException(ExceptionEnums.USER_REGISTER_ERROR);
        }
    }

    /**
     * 判断是否有用户登录，有返回TRUE，没有则返回false
     */
    public boolean isRegisterUser(String loginName) {
        SysUser sysUser = userMapper.selectOne(new QueryWrapper<SysUser>().setEntity(new SysUser().setLoginName(loginName)));
        if(sysUser != null){
            return true;
        }else {
            return false;
        }
    }

    /**
     *  分页查询所有的用户
     */
    public MyPage<SysUser> selectAllUsers(String loginName, Integer page, Integer limit) {
        MyPage<SysUser> myPage = new MyPage<>(page, limit);
        if(StringUtils.isNotBlank(loginName)){
            myPage.setSelectStr(loginName);
        }
        return userMapper.selectAllUsers(myPage);
    }

    /**
     *查询用户id是否存在，如果存在返回一个user数据后显示到前台，如果没有返回为空
     */
    public SysUser isUserId(Integer id) {
        SysUser sysUser = userMapper.selectOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getId, id));
        if (sysUser== null) {
            throw new YcException(ExceptionEnums.USER_UPDATE_NOT_FOUNT);
        }
        return sysUser;
    }

    /**
     * 根据id逻辑删除一个数据
     */
    @Transactional
    public void deleteUser(Integer id) {
        int i = userMapper.deleteById(id);
        if (i != 1) {
            throw new YcException(ExceptionEnums.USER_DELETE_ERROR);
        }
    }

    /**
     * 根据id修改用户
     */
    @Transactional
    public void updateUser(SysUser sysUser) {
        if(sysUser.getId() == null){
            throw new YcException(ExceptionEnums.USER_UPDATE_ERROR);
        }else if(StringUtils.isBlank(sysUser.getUserType())){
            sysUser.setUserType("0");
        }
        System.out.println("sysUser = " + sysUser);
        int i = userMapper.updateById(sysUser);

        if (i != 1) {
            throw new YcException(ExceptionEnums.USER_UPDATE_ERROR);
        }
    }

    /**
     * 批量删除用户
     */
    @Transactional
    public void deleteUsers(Integer[] ids) {
        userMapper.deleteBatchIds(Arrays.asList(ids));
    }


    /**
     *  用户登录
     */
    public SysUser login(String loginName) {
        SysUser sysUser = userMapper.selectOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getLoginName, loginName));
        if(sysUser == null){
            throw new YcException(ExceptionEnums.USER_LOGIN_ERROR);
        }
        return sysUser;
    }
}
