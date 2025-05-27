package com.sky.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SetMealDishMapper {

    @Select("select count(*) from setmeal_dish where dish_id=#{id}")
    int findSetMeal(Long id);
}
