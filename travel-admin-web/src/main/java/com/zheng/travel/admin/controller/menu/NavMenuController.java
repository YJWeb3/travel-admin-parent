package com.zheng.travel.admin.controller.menu;

import com.zheng.travel.admin.controller.BaseController;
import com.zheng.travel.admin.pojo.NavMenu;
import com.zheng.travel.admin.service.menu.INavMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class NavMenuController extends BaseController {

    private final INavMenuService slideMenuService;

    /**
     * 查询分类的接口信息-tree
     *
     * @return
     */
    @GetMapping("/navmenu/tree")
    @ResponseBody
    public List<NavMenu> tree() {
        return slideMenuService.findNavMenuTree();
    }

}
