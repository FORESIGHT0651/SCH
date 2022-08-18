package com.scj.schedule4.controller;

import com.scj.schedule4.dao.AdminDao;
import com.scj.schedule4.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin",method = RequestMethod.GET)
public class AdminController {
    @Autowired
    private AdminDao adminDao;
    //登录
    @GetMapping("/findAdminByAccPwd/{adminaccount}&{adminpassword}")
    public List<Admin> findAdminByAccPwd(@PathVariable("adminaccount") String adminaccount, @PathVariable("adminpassword") String adminpassword){
        return adminDao.findAdminByAccPwd(adminaccount,adminpassword);
    }

    //修改调度员密码
    @Transactional
    @PutMapping("/updateAdminPwd/{adminpassword}&{adminaccount}")
    public String updateAdminPwd(@PathVariable("adminpassword") String adminpassword,@PathVariable("adminaccount") String adminaccount){
        adminDao.updateAdminPwd(adminpassword,adminaccount);
        return "success";
    }
}
