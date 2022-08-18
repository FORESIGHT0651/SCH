package com.scj.schedule4.controller;

import com.scj.schedule4.dao.GoodDao;
import com.scj.schedule4.entity.Good;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/good",method = RequestMethod.GET)
public class GoodController {
    @Autowired
    private GoodDao goodDao;

    //查待分配订单
    @GetMapping("/findGoodByGoodstate")
    public List<Good> findGoodByGoodstate() {
        return goodDao.findGoodByGoodstate();
    }

    //根据起送区域查询待分配订单
    @GetMapping("/findGoodByStartlbs/{startlbs}")
    public List<Good> findGoodByStartlbs(@PathVariable("startlbs") String startlbs) {
        return goodDao.findGoodByStartlbs(startlbs);
    }

    //根据终点区域查询待分配订单
    @GetMapping("/findGoodByEndlbs/{endlbs}")
    public List<Good> findGoodByEndlbs(@PathVariable("endlbs") String endlbs) {
        return goodDao.findGoodByEndlbs(endlbs);
    }

    //根据起送区域、终点区域查询待分配订单
    @GetMapping("/findGoodByStartlbsAndEndlbs/{startlbs}&{endlbs}")
    public List<Good> findGoodByStartlbsAndEndlbs(@PathVariable("startlbs") String startlbs,@PathVariable("endlbs") String endlbs) {
        return goodDao.findGoodByStartlbsAndEndlbs(startlbs,endlbs);
    }

    //更新订单的运单号和状态
    @Transactional
    @PutMapping("/updateGoodRouteid/{routeid}&{goodid}")
    public String updateGoodRouteid(@PathVariable("routeid") Integer routeid,@PathVariable("goodid") Integer goodid){
        goodDao.updateGoodRouteid(routeid,goodid);
        return "success";
    }

    //查运单下的订单信息
    @GetMapping("/findGoodByRouteid/{routeid}")
    public List<Good> findGoodByRouteid(@PathVariable("routeid") Integer routeid) {
        return goodDao.findGoodByRouteid(routeid);
    }

    //查出当前路线上的待分配订单
    @GetMapping("/findGoodByAddrAndState/{startaddr}&{endaddr}")
    public List<Good> findGoodByAddrAndState(@PathVariable("startaddr") String startaddr,@PathVariable("endaddr") String endaddr) {
        return goodDao.findGoodByAddrAndState("%"+startaddr+"%","%"+endaddr+"%");
    }

    //更新订单的状态为已完成
    @Transactional
    @PutMapping("/updateGoodRoutestate/{goodid}")
    public String updateGoodRoutestate(@PathVariable("goodid") Integer goodid){
        goodDao.updateGoodRoutestate(goodid);
        return "success";
    }

    //更新订单的状态为订单异常
    @Transactional
    @PutMapping("/updateGoodstateError/{goodid}")
    public String updateGoodstateError(@PathVariable("goodid") Integer goodid){
        goodDao.updateGoodstateError(goodid);
        return "success";
    }

    //解除订单分配状态
    @Transactional
    @PutMapping("/updateGoodRouteNull/{routeid}")
    public String updateGoodRouteNull(@PathVariable("routeid") Integer routeid){
        goodDao.updateGoodRouteNull(routeid);
        return "success";
    }
}