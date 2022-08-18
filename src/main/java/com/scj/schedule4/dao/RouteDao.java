package com.scj.schedule4.dao;

import com.scj.schedule4.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RouteDao extends JpaRepository<Route,Integer> {
    //插入新运单
    @Modifying
    @Query(value = "insert into route(carid,routeweight,routevolume,routetime,routenum,routetotal,routestate) values(?1,?2,?3,NOW(),?4,?5,'待配送')",nativeQuery = true)
    public void insertRoute(Integer carid,Double routeweight,Double routevolume,Integer routenum,Integer routetotal);

    //查询待配送和配送中的运单
    @Query(value = "select * from route where routestate='待配送' or routestate='配送中'",nativeQuery = true)
    public List<Route> findRouteByRoutestate();

    //根据运单编号查询待配送和配送中的运单
    @Query(value = "select * from route where (routestate='待配送' or routestate='配送中') and routeid=?1",nativeQuery = true)
    public List<Route> findRouteByRoutestateAndRouteid(Integer routeid);

    //查出已完成的运单信息包括车排号和司机姓名
    @Query(value = "select * from v_route where routestate='已完成'",nativeQuery = true)
    public List<RouteCarUser> findVRoute();

    //查出某个司机的待配送或配送中运单
    @Query(value = "select * from v_route where (routestate='待配送' or routestate='配送中') and useraccount=?1",nativeQuery = true)
    public List<RouteCarUser> findVRouteByUser(String useraccount);

    //查出离当前最近时间的运单
    @Query(value = "select * from route order by ABS(NOW()-routetime) ASC limit 1",nativeQuery = true)
    public List<Route> findRouteByRoutetime();

    //根据运单号查route
    @Query(value = "select * from v_route where routeid=?1",nativeQuery = true)
    public List<RouteCarUser> findVRouteByRouteid(Integer routeid);

    //查出某个司机的已完成运单
    @Query(value = "select * from v_route where routestate='已完成' and useraccount=?1",nativeQuery = true)
    public List<RouteCarUser> findVRouteByRouteAndUser(String useraccount);

    //更新运单的状态为配送中
    @Modifying
    @Query(value = "update route set routestate='配送中' where routeid=?1",nativeQuery = true)
    public void updateRoutestateDeal(Integer routeid);

    //更新运单的状态为已完成
    @Modifying
    @Query(value = "update route set routestate='已完成' where routeid=?1",nativeQuery = true)
    public void updateRoutestateEnd(Integer routeid);

    //删除运单
    @Modifying
    @Query(value = "delete from route where routeid=?1",nativeQuery = true)
    public void deleteRoute(Integer routeid);
}
