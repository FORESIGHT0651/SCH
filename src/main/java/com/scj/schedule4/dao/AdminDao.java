package com.scj.schedule4.dao;

import com.scj.schedule4.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface AdminDao extends JpaRepository<Admin,Integer> {
    //登陆查询以及验证密码输入是否正确
    @Query(value = "select * from admin where adminaccount=?1 and adminpassword=?2",nativeQuery = true)
    public List<Admin> findAdminByAccPwd(String adminaccount, String adminpassword);
    //更改密码
    @Modifying
    @Query(value = "update admin set adminpassword=?1 where adminaccount=?2",nativeQuery = true)
    public void updateAdminPwd(String adminpassword, String adminaccount);
}
