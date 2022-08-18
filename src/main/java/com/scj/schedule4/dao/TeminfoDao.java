package com.scj.schedule4.dao;

import com.scj.schedule4.entity.Teminfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TeminfoDao extends JpaRepository<Teminfo,Integer> {
    //路线中文信息
    @Query(value = "select * from teminfo where routeid=?1",nativeQuery = true)
    public List<Teminfo> findTeminfoByRouteid(Integer routeid);
    //保存中文路线到表中
    @Modifying
    @Query(value = "insert into `teminfo`(`routeid`,`teminfoaddr`,`strategy`) values(?1,?2,?3)",nativeQuery = true)
    public void insertTeminfo(Integer routeid,String teminfoaddr,Integer strategy);
    //删除路线
    @Modifying
    @Query(value = "delete from teminfo where routeid=?1",nativeQuery = true)
    public void deleteTeminfo(Integer routeid);
}
