package com.scj.schedule4.controller;

import com.scj.schedule4.dao.UserDao;
import com.scj.schedule4.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user",method = RequestMethod.GET)
public class UserController {
    @Autowired
    private UserDao userDao;

    //登录
    @GetMapping("/findUserByAccPwd/{useraccount}&{userpassword}")
    public List<User> findUserByAccPwd(@PathVariable("useraccount") String useraccount, @PathVariable("userpassword") String userpassword){
        return userDao.findUserByAccPwd(useraccount,userpassword);
    }

    //修改密码
    @Transactional
    @PutMapping("/updateUserPwd/{userpassword}&{useraccount}")
    public String updateUserPwd(@PathVariable("userpassword") String userpassword,@PathVariable("useraccount") String useraccount){
        userDao.updateUserPwd(userpassword,useraccount);
        return "success";
    }
}
