package com.zheng.travel.admin.controller.adminlogs;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zheng.travel.admin.service.adminlogs.IAdminLogsService;
import com.zheng.travel.admin.pojo.AdminLogs;
import com.zheng.travel.admin.vo.AdminLogsVo;
import com.zheng.travel.admin.commons.enums.ResultStatusEnumA;
import com.zheng.travel.admin.commons.ex.TravelValidationException;
import com.zheng.travel.admin.commons.utils.fn.asserts.Vsserts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.zheng.travel.admin.controller.BaseController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdminLogsController extends BaseController {

    private final IAdminLogsService adminlogsService;

    /**
     * 查看方法的耗时和次数
     * @return
     */
    @PostMapping("/logs/state/method/{timer}")
    public List<Map<String, Object>> selectMethodTime(@PathVariable("timer") Long timer) {
        return adminlogsService.selectMethodTime(timer);
    }

    /**
     * 统计浏览器占比
     * @return
     */
    @PostMapping("/logs/state/broswer")
    public List<Map<String, Object>> stateLogsBrowerversion() {
        return adminlogsService.stateLogsBrowerversion();
    }

    /**
     * 统计操作系统占比
     *
     * @return
     */
    @PostMapping("/logs/state/os")
    public List<Map<String, Object>> stateLogsOSversion() {
        return adminlogsService.stateLogsOSversion();
    }

    /**
     * 查询后台日志列表信息
     */
    @GetMapping("/logs/load")
    public List<AdminLogs> findAdminLogsList() {
        return adminlogsService.findAdminLogsList();
    }

    /**
     * 查询后台日志列表信息并分页
     */
    @PostMapping("/logs/list")
    public IPage<AdminLogs> findAdminLogss(@RequestBody AdminLogsVo adminlogsVo) {
        return adminlogsService.findAdminLogsPage(adminlogsVo);
    }


    /**
     * 根据后台日志id查询明细信息
     */
    @GetMapping("/logs/get/{id}")
    public AdminLogs getAdminLogsById(@PathVariable("id") String id) {
        if (Vsserts.isEmpty(id)) {
            throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return adminlogsService.getAdminLogsById(new Long(id));
    }


    /**
     * 保存和修改后台日志
     */
    @PostMapping("/logs/saveupdate")
    public AdminLogs saveupdateAdminLogs(@RequestBody AdminLogs adminlogs) {
        return adminlogsService.saveupdateAdminLogs(adminlogs);
    }


    /**
     * 根据后台日志id删除后台日志
     */
    @PostMapping("/logs/delete/{id}")
    public int deleteAdminLogsById(@PathVariable("id") String id) {
        if (Vsserts.isEmpty(id)) {
            throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return adminlogsService.deleteAdminLogsById(new Long(id));
    }


    /**
     * 根据后台日志ids批量删除后台日志
     */
    @PostMapping("/logs/delBatch")
    public boolean delAdminLogs(@RequestBody AdminLogsVo adminlogsVo) {
        log.info("你要批量删除的IDS是:{}", adminlogsVo.getBatchIds());
        if (Vsserts.isEmpty(adminlogsVo.getBatchIds())) {
            throw new TravelValidationException(ResultStatusEnumA.ID_NOT_EMPTY);
        }
        return adminlogsService.delBatchAdminLogs(adminlogsVo.getBatchIds());
    }

}