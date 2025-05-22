package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {
    void save(CategoryDTO categoryDTO);

    PageResult pageShow(CategoryPageQueryDTO categoryPageQueryDTO);

    void setStatus(Long id, Integer status);



    void updateCategory(CategoryDTO categoryDTO);

    void deleteCategory(Long id);

    List<Category> list(Integer type);
    /**
     * 分类管理
     */
}
