package com.zheng.travel.admin.controller.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zheng.travel.admin.commons.enums.ResultStatusEnumA;
import com.zheng.travel.admin.commons.ex.TravelValidationException;
import com.zheng.travel.admin.commons.utils.fn.asserts.Vsserts;
import com.zheng.travel.admin.controller.BaseController;
import com.zheng.travel.admin.pojo.SysLoginUser;
import com.zheng.travel.admin.service.sysloginuser.ISysLoginUserService;
import com.zheng.travel.admin.vo.SysLoginUserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdminUserController extends BaseController {

    private final ISysLoginUserService userService;



    /**
     * 查询轮播图列表信息
     */
    @GetMapping("/user/load")
    public List<SysLoginUser> findSysLoginUserList() {
        return userService.findSysLoginUserList();
    }

    /**
     * 查询轮播图列表信息并分页
     */
    @PostMapping("/user/list")
    public IPage<SysLoginUser> findSysLoginUsers(@RequestBody SysLoginUserVo sysLoginUserVo) {
        return userService.findSysLoginUserPage(sysLoginUserVo);
    }


    /**
     * 根据轮播图id查询明细信息
     */
    @GetMapping("/user/get/{id}")
    public SysLoginUser getSysLoginUserById(@PathVariable("id") String id) {
        if (Vsserts.isEmpty(id)) {
            throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return userService.getSysLoginUserById(new Long(id));
    }


    /**
     * 保存和修改轮播图
     */
    @PostMapping("/user/saveupdate")
    public SysLoginUser saveupdateSysLoginUser(@RequestBody SysLoginUser sysLoginUser) {
        return userService.saveupdateSysLoginUser(sysLoginUser);
    }


    /**
     * 根据轮播图id删除轮播图
     */
    @PostMapping("/user/delete/{id}")
    public int deleteSysLoginUserById(@PathVariable("id") String id) {
        if (Vsserts.isEmpty(id)) {
            throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return userService.deleteSysLoginUserById(new Long(id));
    }


    /**
     * 根据轮播图ids批量删除轮播图
     */
    @PostMapping("/user/delBatch")
    public boolean delSysLoginUser(@RequestBody SysLoginUserVo sysLoginUserVo) {
        log.info("你要批量删除的IDS是:{}", sysLoginUserVo.getBatchIds());
        if (Vsserts.isEmpty(sysLoginUserVo.getBatchIds())) {
            throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return userService.delBatchSysLoginUser(sysLoginUserVo.getBatchIds());
    }

}
