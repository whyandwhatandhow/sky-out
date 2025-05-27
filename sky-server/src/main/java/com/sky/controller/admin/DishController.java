package com.sky.controller.admin;


import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "菜品管理")
public class DishController {
    @Autowired
    private DishService dishService;


    @ApiOperation("添加菜品")
    @PostMapping
    public Result save(@RequestBody DishDTO dishDTO){
        dishService.save(dishDTO);
        return Result.success();
    }


    //TODO 没有显示出菜品分类
    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> pageQuery(DishPageQueryDTO dishPageQueryDTO){
        PageResult pageResult=dishService.page(dishPageQueryDTO);
        return Result.success(pageResult);
    }


    @GetMapping("/{id}")
    @ApiOperation("根据id查找菜")
    public Result<DishVO> findDishById(@PathVariable Long id){
        DishVO dishVo = dishService.findDishById(id);
        return Result.success(dishVo);
    }


    @PutMapping
    @ApiOperation("修改菜品")
    public Result updateDish(@RequestBody DishDTO dishDTO){
        dishService.updateDish(dishDTO);
        return Result.success();
    }



    @DeleteMapping
    @ApiOperation("批量删除")
    public Result deleteDish(@RequestParam List<Long> ids){
        dishService.deleteBatch(ids);
        return Result.success();
    }


    //TODO 前端为什么没有这个接口
    @GetMapping("/list")
    @ApiOperation("根据分类查询")
    public Result<List<Dish>> findDishByCategoryId(@RequestParam Long categoryId){
       List<Dish> dishes=dishService.findByCategoryId(categoryId);
        return Result.success(dishes);
    }


    @PostMapping("/status/{status}")
    @ApiOperation("修改状态")
    public Result updateStatus(@PathVariable(value = "status") Integer status,Long id){
        dishService.updateStatus(status,id);
        return Result.success();
    }

}
