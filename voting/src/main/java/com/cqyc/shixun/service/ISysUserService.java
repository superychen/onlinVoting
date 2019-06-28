package com.cqyc.shixun.service;

import com.cqyc.shixun.comm.MyPage;
import com.cqyc.shixun.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cqyc
 * @since 2019-06-17
 */
public interface ISysUserService extends IService<SysUser> {

    void registerUser(SysUser sysUser);

    boolean isRegisterUser(String loginName);

    MyPage<SysUser> selectAllUsers(String loginName, Integer page, Integer limit);

    SysUser isUserId(Integer id);

    void deleteUser(Integer id);

    void updateUser(SysUser sysUser);

    void deleteUsers(Integer[] ids);

    SysUser login(String loginName);
}
