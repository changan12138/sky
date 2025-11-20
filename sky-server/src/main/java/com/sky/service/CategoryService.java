package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;

public interface CategoryService {
    PageResult page(CategoryPageQueryDTO categoryPageQueryDTO);

    void add(CategoryDTO categoryDTO);

    void enable(Integer status, Long id);
}
