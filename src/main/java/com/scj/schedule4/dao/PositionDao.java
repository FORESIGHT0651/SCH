package com.scj.schedule4.dao;

import com.scj.schedule4.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface PositionDao extends JpaRepository<Position,Integer> {
    //保存新的路线位置数据
    @Modifying
    @Query(value = "insert into `position`(`routeid`,`longitude`,`latitude`) values(?1,?2,?3)",nativeQuery = true)
    public void insertPosition(Integer routeid,Double longitude,Double latitude);
    //路线id查询
    @Query(value = "select * from position where routeid=?1 order by positionid",nativeQuery = true)
    public List<Position> findPositionByRouteid(Integer routeid);
    //删除路线
    @Modifying
    @Query(value = "delete from position where routeid=?1",nativeQuery = true)
    public void deletePosition(Integer routeid);
}
