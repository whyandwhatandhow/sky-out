package com.sky.mapper;


import com.sky.annotation.AutoFill;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {


    void insertBatch(List<DishFlavor> dishFlavorList);


    @Select("select * from dish_flavor where dish_id=#{id}")
    List<DishFlavor> selectByDishId(Long id);


    @Delete("delete from dish_flavor where dish_id=#{id}")
    void deleteByDishId(Long id);
}
