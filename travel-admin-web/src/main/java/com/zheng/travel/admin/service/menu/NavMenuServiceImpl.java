package com.zheng.travel.admin.service.menu;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zheng.orm.travel.mapper.NavMenuMapper;
import com.zheng.travel.admin.commons.utils.collection.CollectionUtils;
import com.zheng.travel.admin.pojo.NavMenu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class NavMenuServiceImpl extends ServiceImpl<NavMenuMapper, NavMenu> implements INavMenuService {


    @Override
    public List<NavMenu> findNavMenuTree() {
        // 1 :查询表中所有的数据
        LambdaQueryWrapper<NavMenu> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(NavMenu::getStatus,1);
        List<NavMenu> allList = this.list(lambdaQueryWrapper); // 思考空间，为什么查询的是所有
        // 2: 找到所有的根节点 pid = 0
        List<NavMenu> rootList = allList.stream().filter(category -> category.getPid().equals(0L))
                .sorted((a, b) -> a.getSorted() - b.getSorted()).collect(Collectors.toList());
        // 3 : 查询所有的非根节点
        List<NavMenu> subList = allList.stream().filter(category -> !category.getPid().equals(0L)).collect(Collectors.toList());
        // 4 : 循环根节点去subList去找对应的子节点
        rootList.forEach(root -> buckForback(root, subList));

        return rootList;
    }


    private void buckForback(NavMenu root, List<NavMenu> subList) {
        // 通过根节点去id和子节点的pid是否相等，如果相等的话，代表是当前根的子集
        List<NavMenu> childrenList = subList.stream().filter(category -> category.getPid().equals(root.getId()))
                .sorted((a, b) -> a.getSorted() - b.getSorted())
                .collect(Collectors.toList());
        // 如果你当前没一个子集，初始化一个空数组
        if (!CollectionUtils.isEmpty(childrenList)) {
            // 查询以后放回去
            root.setChildrens(childrenList);
            // 再次递归构建即可
            childrenList.forEach(category -> buckForback(category, subList));
        } else {
            root.setChildrens(new ArrayList<>());
        }
    }

}
