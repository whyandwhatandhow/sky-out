package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetMealDishMapper setMealDishMapper;


    @Override
    @Transactional
    public void save(DishDTO dishDTO) {
        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.insert(dish);
        List<DishFlavor> dishFlavorList=dishDTO.getFlavors();
        Long id=dish.getId();
        if(dishFlavorList!=null && dishFlavorList.size()>0){
            for(int i=0;i<dishFlavorList.size();i++){
                dishFlavorList.get(i).setDishId(id);

            }
            dishFlavorMapper.insertBatch(dishFlavorList);
        }
    }

    @Override
    public PageResult page(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<Dish> page=dishMapper.page(dishPageQueryDTO);
        long total=page.getTotal();
        List<Dish> recodes=page.getResult();
        return new PageResult(total,recodes);
    }

    @Override
    public DishVO findDishById(Long id) {
        Dish dish=dishMapper.findDishById(id);
        List<DishFlavor> dishFlavorList=dishFlavorMapper.selectByDishId(id);
        DishVO dishVO=new DishVO();
        BeanUtils.copyProperties(dish,dishVO);
        dishVO.setFlavors(dishFlavorList);
        return dishVO;
    }

    @Override
    @Transactional
    public void updateDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.update(dish);
        //删除原有数据
        dishFlavorMapper.deleteByDishId(dishDTO.getId());
        //插入最新数据
        List<DishFlavor> dishFlavorList=dishDTO.getFlavors();

        if(dishFlavorList!=null && dishFlavorList.size()>0){
            for(int i=0;i<dishFlavorList.size();i++){
                dishFlavorList.get(i).setDishId(dishDTO.getId());

            }
            dishFlavorMapper.insertBatch(dishFlavorList);
        }

    }

    @Override
    @Transactional
    public void deleteBatch(List<Long> ids) {
        //判断时候可以删除--起售不能删除
        for(Long id:ids){
            Dish dish=dishMapper.findDishById(id);
            if(dish.getStatus()== StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //判断能否删除--关联了不能删除
        for(Long id:ids){
            int flag=setMealDishMapper.findSetMeal(id);
            if(flag>0){
                throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
            }
        }

        //删除菜品
        for(Long id:ids){
            dishMapper.deleteById(id);
            //删除关联口味
            dishFlavorMapper.deleteByDishId(id);
        }
    }

    @Override
    public List<Dish> findByCategoryId(Long categoryId) {
        List<Dish> dishes=dishMapper.findByCategory(categoryId);
        return dishes;
    }

    @Override
    public void updateStatus(Integer status, Long id) {
        Dish dish=new Dish();
        dish.setId(id);
        dish.setStatus(status);
        dishMapper.update(dish);
    }
}
