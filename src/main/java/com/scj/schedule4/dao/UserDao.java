package com.scj.schedule4.dao;

import com.scj.schedule4.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserDao extends JpaRepository<User,Integer> {
    //登陆查询以及验证密码输入是否正确
    @Query(value = "select * from user where useraccount=?1 and userpassword=?2",nativeQuery = true)
    public List<User> findUserByAccPwd(String useraccount, String userpassword);
    //更改密码
    @Modifying
    @Query(value = "update user set userpassword=?1 where useraccount=?2",nativeQuery = true)
    public void updateUserPwd(String userpassword, String useraccount);
}
