package com.scj.schedule4.controller;

import com.scj.schedule4.dao.PositionDao;
import com.scj.schedule4.entity.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/position",method = RequestMethod.GET)
public class PositionController {
    @Autowired
    private PositionDao positionDao;

    //保存新的路线位置数据
    @Transactional
    @PostMapping("/insertPosition/{routeid}&{longitude}&{latitude}")
    public String insertPosition(@PathVariable("routeid") Integer routeid, @PathVariable("longitude") Double longitude, @PathVariable("latitude") Double latitude){
        positionDao.insertPosition(routeid,longitude,latitude);
        return "success";
    }

    //路线id查询
    @GetMapping("/findPositionByRouteid/{routeid}")
    public List<Position> findPositionByRouteid(@PathVariable("routeid") Integer routeid) {
        return positionDao.findPositionByRouteid(routeid);
    }

    //删除路线
    @Transactional
    @DeleteMapping("/deletePosition/{routeid}")
    public String deletePosition(@PathVariable("routeid") Integer routeid){
        positionDao.deletePosition(routeid);
        return "success";
    }

}
