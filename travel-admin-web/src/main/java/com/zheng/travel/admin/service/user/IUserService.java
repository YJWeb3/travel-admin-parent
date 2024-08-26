package com.zheng.travel.admin.service.user;//package com.zheng.travel.admin.service.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zheng.travel.admin.pojo.SysLoginUser;
import com.zheng.travel.admin.vo.SysLoginUserVo;

import java.util.List;

public interface IUserService extends IService<SysLoginUser> {

    /**
     * 多表关联查询
     */
    IPage<SysLoginUser> findLoginUserPage(int pageNo, int pageSize);

    /**
     * 查询轮播图列表信息
     */
    List<SysLoginUser> findSysLoginUserList() ;

    /**
     * 查询轮播图列表信息并分页
     */
    IPage<SysLoginUser> findSysLoginUserPage(SysLoginUserVo bannerVo);

    /**
     * 保存&修改轮播图
     */
    SysLoginUser saveupdateSysLoginUser(SysLoginUser banner);

    /**
     * 根据Id删除轮播图
     */
    int deleteSysLoginUserById(Long id) ;

    /**
     * 根据Id查询轮播图明细信息
     */
    SysLoginUser getSysLoginUserById(Long id);

    /**
     * 根据轮播图ids批量删除轮播图
     */
    boolean delBatchSysLoginUser(String ids);
}
