package com.zheng.travel.admin.service.adminlogs;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zheng.travel.admin.pojo.AdminLogs;
import com.zheng.travel.admin.vo.AdminLogsVo;

import java.util.List;
import java.util.Map;

/**
 * IAdminLogsService接口
 * 创建人:yykk<br/>
 * 时间：2022-04-18 21:24:11 <br/>
 * 源码下载：前台代码 git clone https://gitee.com/kekesam/kuangstudy-pug-ui.git
 * 飞哥B站地址：后台代码 git clone https://gitee.com/kekesam/kuangstudy-pug-parent.git
 * @version 1.0.0<br/>
 *
*/
public interface IAdminLogsService extends IService<AdminLogs>{


    /**
     * 查看方法的耗时和次数
     *
     * @return
     */
    List<Map<String, Object>> selectMethodTime(Long timer);

    /**
     *  统计浏览器占比
     * @return
     */
    List<Map<String,Object>> stateLogsBrowerversion();

    /**
     *  统计操作系统占比
     * @return
     */
    List<Map<String,Object>> stateLogsOSversion();

    /**
     * 查询后台日志列表信息
     */
    List<AdminLogs> findAdminLogsList() ;

	/**
     * 查询后台日志列表信息并分页
    */
	IPage<AdminLogs> findAdminLogsPage(AdminLogsVo adminlogsVo);

    /**
     * 保存&修改后台日志
    */
    AdminLogs saveupdateAdminLogs(AdminLogs adminlogs);

    /**
     * 根据Id删除后台日志
     */
    int deleteAdminLogsById(Long id) ;

    /**
     * 根据Id查询后台日志明细信息
    */
    AdminLogs getAdminLogsById(Long id);

    /**
     * 根据后台日志ids批量删除后台日志
    */
    boolean delBatchAdminLogs(String ids);

}