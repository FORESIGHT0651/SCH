package com.scj.schedule4.controller;

import com.scj.schedule4.dao.RouteCarUser;
import com.scj.schedule4.dao.RouteDao;
import com.scj.schedule4.entity.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/route",method = RequestMethod.GET)
public class RouteController {
    @Autowired
    private RouteDao routeDao;

    //插入新路线
    @Transactional
    @PostMapping("/insertRoute/{carid}&{routeweight}&{routevolume}&{routenum}&{routetotal}")
    public String insertRoute(@PathVariable("carid") Integer carid, @PathVariable("routeweight") Double routeweight, @PathVariable("routevolume") Double routevolume, @PathVariable("routenum") Integer routenum, @PathVariable("routetotal") Integer routetotal){
        routeDao.insertRoute(carid,routeweight,routevolume,routenum,routetotal);
        return "success";
    }

    //查询待配送和配送中的运单
    @GetMapping("/findRouteByRoutestate")
    public List<Route> findRouteByRoutestate(){
        return routeDao.findRouteByRoutestate();
    }

    //根据运单编号查询待配送和配送中的运单
    @GetMapping("/findRouteByRoutestateAndRouteid/{routeid}")
    public List<Route> findRouteByRoutestateAndRouteid(@PathVariable("routeid") Integer routeid){
        return routeDao.findRouteByRoutestateAndRouteid(routeid);
    }

    //查出已完成的运单信息包括车排号和司机姓名
    @GetMapping("/findVRoute")
    public List<RouteCarUser> findVRoute(){
        return routeDao.findVRoute();
    }

    //查出某个司机的待配送或配送中运单
    @GetMapping("/findVRouteByUser/{useraccount}")
    public List<RouteCarUser> findVRouteByUser(@PathVariable("useraccount") String useraccount){
        return routeDao.findVRouteByUser(useraccount);
    }

    //查出离当前最近时间的运单
    @GetMapping("/findRouteByRoutetime")
    public List<Route> findRouteByRoutetime(){
        return routeDao.findRouteByRoutetime();
    }

    //根据运单号查route
    @GetMapping("/findVRouteByRouteid/{routeid}")
    public List<RouteCarUser> findVRouteByRouteid(@PathVariable("routeid") Integer routeid){
        return routeDao.findVRouteByRouteid(routeid);
    }

    //查出某个司机的已完成运单
    @GetMapping("/findVRouteByRouteAndUser/{useraccount}")
    public List<RouteCarUser> findVRouteByRouteAndUser(@PathVariable("useraccount") String useraccount){
        return routeDao.findVRouteByRouteAndUser(useraccount);
    }

    //更新运单的状态为配送中
    @Transactional
    @PutMapping("/updateRoutestateDeal/{routeid}")
    public String updateRoutestateDeal(@PathVariable("routeid") Integer routeid){
        routeDao.updateRoutestateDeal(routeid);
        return "success";
    }

    //更新运单的状态为已完成
    @Transactional
    @PutMapping("/updateRoutestateEnd/{routeid}")
    public String updateRoutestateEnd(@PathVariable("routeid") Integer routeid){
        routeDao.updateRoutestateEnd(routeid);
        return "success";
    }

    //删除运单
    @Transactional
    @PutMapping("/deleteRoute/{routeid}")
    public String deleteRoute(@PathVariable("routeid") Integer routeid){
        routeDao.deleteRoute(routeid);
        return "success";
    }

}
