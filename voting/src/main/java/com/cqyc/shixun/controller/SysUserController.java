package com.cqyc.shixun.controller;


import com.alibaba.fastjson.JSONObject;
import com.cqyc.shixun.comm.MyPage;
import com.cqyc.shixun.comm.exception.ExceptionEnums;
import com.cqyc.shixun.comm.exception.YcException;
import com.cqyc.shixun.domain.SysUser;
import com.cqyc.shixun.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cqyc
 * @since 2019-06-17
 */
@RestController
@RequestMapping("/sysUser")
@Slf4j
public class SysUserController {

    @Autowired
    private ISysUserService userService;

    /**
     * 检测访问需要登录的页面时，需要跳转到这里
     */
    @RequestMapping("/unauth")
    public Object unauth(){
        System.out.println("用户未登录就访问要登陆的页面了");
        Map<String , Object> map = new HashMap<>();
        map.put("code","1000");
        map.put("msg","未登录");
        return map;
    }

    /**
     * 注册用户
     */
    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody @Validated SysUser sysUser, BindingResult result){
        HashMap<String, String> map = new HashMap<>();
        if(result.hasFieldErrors()){
            throw new YcException(ExceptionEnums.USER_REGISTER_ERROR);
        }
        userService.registerUser(sysUser);
        map.put("status","200");
        return ResponseEntity.ok(JSONObject.toJSONString(map));
    }

    /**
     * 查询当前用户是否已经被注册过了
     */
    @GetMapping("isRegister")
    public ResponseEntity<Object> isRegisterUser(String loginName){
        Map<String, String> map = new HashMap<>();
        boolean isRegister = userService.isRegisterUser(loginName);
        if(isRegister){
            map.put("status","该账号已经有人注册了哟");
        }else{
            map.put("status","格式合格");
        }
        return ResponseEntity.ok(map);
    }

    /**
     * 查询所有的用户信息
     */
    @GetMapping("users")
    public ResponseEntity<MyPage<SysUser>> selectAllUsers(@RequestParam(value = "loginName",required = false) String loginName,
                                                        Integer page, Integer limit){
        MyPage<SysUser> myPage = userService.selectAllUsers(loginName, page, limit);
        myPage.setData(myPage.getRecords());
        myPage.setCount(myPage.getTotal());
        return ResponseEntity.ok(myPage);
    }

    /**
     * 查询用户id是否存在，如果存在返回一个user数据后显示到前台，如果没有返回为空
     */

    @GetMapping("userId")
    public ResponseEntity<SysUser> isUserId(@RequestParam(value = "id",required = false) Integer id){
        if (id == null) {
            SysUser sysUser = new SysUser();
            sysUser.setUserType(null);
            return ResponseEntity.ok(sysUser);
        }
        return ResponseEntity.ok(userService.isUserId(id));
    }


    /**
     * 逻辑删除根据id删除
     */
    @DeleteMapping("delUser")
    public ResponseEntity<Object> deleteUser(@RequestParam("id") Integer id){
        Map<String, String> map = new HashMap<>();
        userService.deleteUser(id);
        map.put("status","ok");
        map.put("msg","删除成功");
        return ResponseEntity.ok(map);
    }

    /**
     * 根据id修改用户
     */
    @PutMapping("updateUser")
    public ResponseEntity<Object> updateUser(@RequestBody @Validated SysUser sysUser,BindingResult result){
        if(result.hasFieldErrors()){
            throw new YcException(ExceptionEnums.USER_UPDATE_ERROR);
        }
        Map<String, String> map = new HashMap<>();
        userService.updateUser(sysUser);
        map.put("status","ok");
        map.put("msg","修改成功");
        return ResponseEntity.ok(map);
    }

    /**
     * 根据id批量删除用户数据
     */
    @DeleteMapping("delUsers")
    public ResponseEntity<Object> delUsers(@RequestParam(value = "ids",required = false) Integer[] ids){
        Map<String, String> map = new HashMap<>();
        userService.deleteUsers(ids);
        map.put("status","ok");
        map.put("msg","批量删除成功");
        return ResponseEntity.ok(map);
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestParam("loginName") String loginName,
                                             @RequestParam("password") String password){

        Map<String, String> map = new HashMap<>();
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.isAuthenticated()){
            //将用户名和密码为UsernamePasswordToken
            UsernamePasswordToken token = new UsernamePasswordToken(loginName,password);
            token.setRememberMe(true);
            try {
                //暂时先不用输入验证码
                currentUser.login(token);
                map.put("code","200");
                map.put("msg","登录成功");
            }catch (AuthenticationException e){
                log.debug("登录错误信息：{}",e.getMessage());
                throw new AuthenticationException("登录错误，请检查您的账号密码");
            }

        }
        return ResponseEntity.ok(map);

    }


    @GetMapping("/loginOut")
    public ResponseEntity<Object> loginOut(){
        Map<String, String> map = new HashMap<>();
        SecurityUtils.getSubject().logout();
        map.put("code","200");
        map.put("msg","退出成功");
        return ResponseEntity.ok(map);
    }

    /**
     * 每个页面都判断当前是否登录，如果登录，每个页面都能访问，如果没有，则直接跳转到
     */
    @GetMapping("isLogin")
    public ResponseEntity<Object> isLogin(HttpSession session){
        Map<String, Object> map = new HashMap<>();
        SysUser user = (SysUser) session.getAttribute("user");
        if (user == null) {
            map.put("msg","还没有登录哟");
        }else{
            map.put("msg",user);
        }
        return ResponseEntity.ok(map);
    }



}
