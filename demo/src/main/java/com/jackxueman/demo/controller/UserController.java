package com.jackxueman.demo.controller;

import com.jackxueman.demo.common.exception.BaseException;
import com.jackxueman.demo.common.response.DataResponse;
import com.jackxueman.demo.pojo.entity.User;
import com.jackxueman.demo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * created by hzm.
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @RequiresUser 执行认证（登录）AuthRealm.doGetAuthenticationInfo方法
     * @RequiresPermissions 执行授权（访问控制） AuthRealm.doGetAuthorizationInfo方法
     * @RequiresAuthentication 和 RequiresUser方法使用一样，验证用户是否登录，等同于方法subject.isAuthenticated() 结果为true时。
     * @RequiresRoles("aRoleName"); void someMethod();如果subject中有aRoleName角色才可以访问方法someMethod。如果没有这个权限则会抛出异常AuthorizationException。
     * @RequiresRoles({"user","admin"}) //必须同时属于user和admin角色 ，@RequiresRoles(value={"user","admin"},logical=Logical.OR) //属于user或者admin之一;修改logical为OR 即可
     * @param user
     * @return
     */
//    @RequiresAuthentication
//    @RequiresUser
    @RequiresPermissions("362334199007247115")
    @RequestMapping(value = "/addUser",method = {RequestMethod.POST})
    public DataResponse<Integer> adduser(@RequestBody User user){
        Subject subject = SecurityUtils.getSubject();
        String principal = (String)subject.getPrincipal();
        user.setPassword(generatePassword(user.getSalt(),user.getPassword()));
        int i = userService.adduser(user);
//        int i = 1;
        if(i == 1){
            return new DataResponse<>(1);
        }else {
            return new DataResponse<>(0);
        }
    }

    @RequestMapping(value = "login",method = {RequestMethod.POST})
    public DataResponse<UsernamePasswordToken> login(@RequestBody User user){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getIdentity(),user.getPassword());
        try {
            subject.login(token);
        }catch (AuthenticationException e){
            Throwable cause = e.getCause();
            if(cause != null && cause instanceof BaseException){
                throw (BaseException) cause;
            }
        }

        return new DataResponse<>(token);
    }

    /**
     * 生成密码
     *
     * @param salt     盐
     * @param password 密码
     * @return
     */
    private String generatePassword(String salt, String password) {
        return new SimpleHash("md5", password, salt, 1).toString();
    }

    @RequestMapping(value="/index",method=RequestMethod.GET)
    public String home(){
        Subject subject = SecurityUtils.getSubject();
        String principal = (String)subject.getPrincipal();

        return "Home";
    }
}
