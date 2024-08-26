package com.zheng.travel.admin.service.menu;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zheng.travel.admin.pojo.NavMenu;

import java.util.List;

public interface INavMenuService extends IService<NavMenu> {


    /**
     * 返回分类tree
     *
     * @return
     */
    List<NavMenu> findNavMenuTree();
}
