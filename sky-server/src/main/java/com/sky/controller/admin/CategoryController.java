package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Api("分类")
@RestController
@Slf4j
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    public Result page(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("分页查询:{}", categoryPageQueryDTO);
        PageResult pageResult = categoryService.page(categoryPageQueryDTO);
        return Result.success(pageResult);
    }
    @PostMapping
    public Result addCategory(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增菜品：{}", categoryDTO);
        categoryService.add(categoryDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    public Result enable(@PathVariable Integer status,Long id) {
        log.info("权限设置：{}", status);
        categoryService.enable(status,id);
        return Result.success();

    }


}
