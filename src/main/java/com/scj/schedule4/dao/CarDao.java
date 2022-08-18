package com.scj.schedule4.dao;

import com.scj.schedule4.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface CarDao extends JpaRepository<Car,Integer> {
    //查询车辆与司机姓名且在送运单数为0的车辆
    @Query(value = "select car.*,user.username from car,user where car.userid=user.userid and car.carway=0",nativeQuery = true)
    public List<CarUser> findCarByCarway();
    //根据车牌号查询车辆与司机姓名且在送运单数为0的车辆
    @Query(value = "select car.*,user.username from car,user where car.userid=user.userid and car.carnumber like ?1 and car.carway=0",nativeQuery = true)
    public List<CarUser> findCarByCarnumber(String carnumber);
    //查出车辆按在送运单数降序排列
    @Query(value = "select * from car order by carway desc",nativeQuery = true)
    public List<Car> findCarOrderByCarway();
    //根据车牌号模糊查询车辆按在送运单数降序排列
    @Query(value = "select * from car where carnumber like ?1 order by carway desc",nativeQuery = true)
    public List<Car> findCarByCarnumberOrder(String carnumber);
    //更新车辆的在送运单数为1
    @Modifying
    @Query(value = "update car set carway=1 where carid=?1",nativeQuery = true)
    public void updateCarwayToOne(Integer carid);
    //更新车辆的在送运单数为0
    @Modifying
    @Query(value = "update car set carway=0 where carid=?1",nativeQuery = true)
    public void updateCarwayToZero(Integer carid);
    //根据车辆编号查询车辆
    @Query(value = "select * from car where carid=?1",nativeQuery = true)
    public List<Car> findCarByCarid(Integer carid);
    //根据区域查询车辆与司机姓名且在送运单数为0的车辆
    @Query(value = "select car.*,user.username from car,user where car.userid=user.userid and car.carlbs=?1 and car.carway=0",nativeQuery = true)
    public List<CarUser> findCarByCarlbs(String carlbs);
    //根据区域和车牌号查询车辆与司机姓名且在送运单数为0的车辆
    @Query(value = "select car.*,user.username from car,user where car.userid=user.userid " +
            "and car.carnumber like ?1 and car.carlbs=?2 and car.carway=0",nativeQuery = true)
    public List<CarUser> findCarByCarnumberAndCarlbs(String carnumber,String carlbs);
}
