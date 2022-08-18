package com.scj.schedule4.controller;

import com.scj.schedule4.dao.TeminfoDao;
import com.scj.schedule4.entity.Teminfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/teminfo",method = RequestMethod.GET)
public class TeminfoController {
    @Autowired
    private TeminfoDao teminfoDao;

    //路线中文信息
    @GetMapping("/findTeminfoByRouteid/{routeid}")
    public List<Teminfo> findTeminfoByRouteid(@PathVariable("routeid") Integer routeid){
        return teminfoDao.findTeminfoByRouteid(routeid);
    }

    //保存中文路线到表中
    @Transactional
    @PostMapping("/insertTeminfo/{routeid}&{teminfoaddr}&{strategy}")
    public String insertTeminfo(@PathVariable("routeid") Integer routeid,@PathVariable("teminfoaddr") String teminfoaddr,@PathVariable("strategy") Integer strategy){
        teminfoDao.insertTeminfo(routeid,teminfoaddr,strategy);
        return "success";
    }

    //删除路线
    @Transactional
    @PostMapping("/deleteTeminfo/{routeid}")
    public String deleteTeminfo(@PathVariable("routeid") Integer routeid){
        teminfoDao.deleteTeminfo(routeid);
        return "success";
    }
}
