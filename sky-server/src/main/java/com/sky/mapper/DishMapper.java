package com.sky.mapper;


import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {


    @Select("select count(*) from dish where category_id=#{id}")
    int selectDish(Long id);


    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);


    Page<Dish> page(DishPageQueryDTO dishPageQueryDTO);


    @Select("select * from dish where id=#{id}")
    Dish findDishById(Long id);


    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);


    @Delete("delete from dish where id=#{id}")
    void deleteById(Long id);


    @Select("select * from dish where category_id=#{categoryId}")
    List<Dish> findByCategory(Long categoryId);
}
