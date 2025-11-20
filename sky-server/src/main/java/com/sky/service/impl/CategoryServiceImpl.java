package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public PageResult page(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        List<Category> category = categoryMapper.page(categoryPageQueryDTO);
        Page page = (Page) category;
        return new PageResult(page.getTotal(), category);
    }

    @Override
    public void add(CategoryDTO categoryDTO) {
        Category category = Category.builder().type(categoryDTO.getType()).name(
                categoryDTO.getName())
                .sort(categoryDTO.getSort())
                .status(StatusConstant.DISABLE)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .createUser(BaseContext.getCurrentId())
                .updateUser(BaseContext.getCurrentId()).build();
        categoryMapper.add(category);
    }

    @Override
    public void enable(Integer status, Long id) {
        Category category =Category.builder().updateUser(BaseContext.getCurrentId())
                .status(status)
                .updateTime(LocalDateTime.now())
                .id(id)
                .build();
                categoryMapper.update(category);
    }
}
