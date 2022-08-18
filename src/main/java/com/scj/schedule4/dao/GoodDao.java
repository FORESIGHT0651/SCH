package com.scj.schedule4.dao;

import com.scj.schedule4.entity.Good;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface GoodDao extends JpaRepository<Good,Integer> {
    //查待分配订单
    @Query(value = "select * from good where goodstate='待分配'",nativeQuery = true)
    public List<Good> findGoodByGoodstate();
    //根据起送区域查询待分配订单
    @Query(value = "select * from good where goodstate='待分配' and startlbs=?1",nativeQuery = true)
    public List<Good> findGoodByStartlbs(String startlbs);
    //根据终点区域查询待分配订单
    @Query(value = "select * from good where goodstate='待分配' and endlbs=?1",nativeQuery = true)
    public List<Good> findGoodByEndlbs(String endlbs);
    //根据起送区域、终点区域查询待分配订单
    @Query(value = "select * from good where goodstate='待分配' and startlbs=?1 and endlbs=?2",nativeQuery = true)
    public List<Good> findGoodByStartlbsAndEndlbs(String startlbs,String endlbs);
    //更新订单的运单号和状态
    @Modifying
    @Query(value = "update good set routeid=?1,goodstate='处理中' where goodid=?2",nativeQuery = true)
    public void updateGoodRouteid(Integer routeid,Integer goodid);
    //查运单下的订单信息
    @Query(value = "select * from good where routeid=?1",nativeQuery = true)
    public List<Good> findGoodByRouteid(Integer routeid);
    //查出当前路线上的待分配订单
    @Query(value = "select * from good where startaddr like ?1 and endaddr like ?2 and goodstate='待分配'",nativeQuery = true)
    public List<Good> findGoodByAddrAndState(String startaddr,String endaddr);
    //更新订单的状态为已完成
    @Modifying
    @Query(value = "update good set goodstate='已完成' where goodid=?1",nativeQuery = true)
    public void updateGoodRoutestate(Integer goodid);
    //更新订单的状态为订单异常
    @Modifying
    @Query(value = "update good set goodstate='订单异常' where goodid=?1",nativeQuery = true)
    public void updateGoodstateError(Integer goodid);
    //解除订单分配状态
    @Modifying
    @Query(value = "update good set routeid=NULL,goodstate='待分配' where routeid=?1",nativeQuery = true)
    public void updateGoodRouteNull(Integer routeid);
}
