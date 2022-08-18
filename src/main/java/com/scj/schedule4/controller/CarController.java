package com.scj.schedule4.controller;

import com.scj.schedule4.dao.CarDao;
import com.scj.schedule4.dao.CarUser;
import com.scj.schedule4.entity.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/car",method = RequestMethod.GET)
public class CarController {
    @Autowired
    private CarDao carDao;

    @GetMapping("/findAll")
    public List<Car> findAll(){
        return carDao.findAll();
    }

    //查询车辆与司机姓名且在送运单数为0的车辆
    @GetMapping("/findCarByCarway")
    public List<CarUser> findCarByCarway(){
        return carDao.findCarByCarway();
    }

    //根据车牌号查询车辆与司机姓名且在送运单数为0的车辆
    @GetMapping("/findCarByCarnumber/{carnumber}")
    public  List<CarUser> findCarByCarnumber(@PathVariable("carnumber") String carnumber){
        return carDao.findCarByCarnumber("%"+carnumber+"%");
    }

    //查出车辆按在送运单数降序排列
    @GetMapping("/findCarOrderByCarway")
    public  List<Car> findCarOrderByCarway(){
        return carDao.findCarOrderByCarway();
    }

    //根据车牌号模糊查询车辆按在送运单数降序排列
    @GetMapping("/findCarByCarnumberOrder/{carnumber}")
    public  List<Car> findCarByCarnumberOrder(@PathVariable("carnumber") String carnumber){
        return carDao.findCarByCarnumberOrder("%"+carnumber+"%");
    }

    //更新车辆的在送运单数为1
    @Transactional
    @PutMapping("/updateCarwayToOne/{carid}")
    public String updateCarwayToOne(@PathVariable("carid") Integer carid){
        carDao.updateCarwayToOne(carid);
        return "success";
    }

    //更新车辆的在送运单数为0
    @Transactional
    @PutMapping("/updateCarwayToZero/{carid}")
    public String updateCarwayToZero(@PathVariable("carid") Integer carid){
        carDao.updateCarwayToZero(carid);
        return "success";
    }

    //根据车辆编号查询车辆
    @GetMapping("/findCarByCarid/{carid}")
    public  List<Car> findCarByCarid(@PathVariable("carid") Integer carid){
        return carDao.findCarByCarid(carid);
    }

    //根据区域查询车辆与司机姓名且在送运单数为0的车辆
    @GetMapping("/findCarByCarlbs/{carlbs}")
    public  List<CarUser> findCarByCarlbs(@PathVariable("carlbs") String carlbs){
        return carDao.findCarByCarlbs(carlbs);
    }

    //根据区域、车牌号查询车辆与司机姓名且在送运单数为0的车辆
    @GetMapping("/findCarByCarnumberAndCarlbs/{carnumber}&{carlbs}")
    public  List<CarUser> findCarByCarnumberAndCarlbs(
            @PathVariable("carnumber") String carnumber,@PathVariable("carlbs") String carlbs){
        return carDao.findCarByCarnumberAndCarlbs("%"+carnumber+"%",carlbs);
    }
}
