package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
    void save(DishDTO dishDTO);

    PageResult page(DishPageQueryDTO dishPageQueryDTO);

    DishVO findDishById(Long id);

    void updateDish(DishDTO dishDTO);

    void deleteBatch(List<Long> ids);

    List<Dish> findByCategoryId(Long categoryId);

    void updateStatus(Integer status, Long id);
}
