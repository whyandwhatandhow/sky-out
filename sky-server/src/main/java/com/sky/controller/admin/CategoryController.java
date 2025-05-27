package com.sky.controller.admin;


import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类管理
 */
@Slf4j
@RestController
@RequestMapping("/admin/category")
@Api(tags = "分类管理")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    /**
     * 新增分类
     * @param categoryDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增分类")
    public Result addCategory(@RequestBody CategoryDTO categoryDTO){
        categoryService.save(categoryDTO);
        return Result.success();
    }


    /**
     * 分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> pageShow(CategoryPageQueryDTO categoryPageQueryDTO){
        PageResult pageResult=categoryService.pageShow(categoryPageQueryDTO);
        return Result.success(pageResult);
    }


    /**
     * 修改菜品分类状态
     * @param id
     * @param status
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("修改菜品分类状态")
    public Result setCategoryStatus(Long id,@PathVariable(value = "status") Integer status){
        categoryService.setStatus(id,status);
        return Result.success();
    }


    /**
     * 根据类型查找分类
     * @param type
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据类型查找分类")
    public Result<List<Category>> SearchCategory(@RequestParam Integer type){
        List<Category>list=categoryService.list(type);
        return Result.success(list);
    }


    /**
     * 修改菜品
     * @param categoryDTO
     * @return
     */
    @PutMapping
    @ApiOperation("修改菜品")
    public Result updateCategory(@RequestBody CategoryDTO categoryDTO){
        categoryService.updateCategory(categoryDTO);
        return Result.success();
    }


    /**
     * 删除分类
     * @param id
     * @return
     */
    @DeleteMapping
    @ApiOperation("删除分类")
    public Result deleteCateGory(Long id){
        categoryService.deleteCategory(id);
        return Result.success();
    }

}
