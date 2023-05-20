package com.xl.ruiji.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xl.ruiji.pojo.Category;

public interface CategoryService extends IService<Category> {
    public void move(String id);
}
