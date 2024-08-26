package com.zheng.travel.admin.service.adminlogs;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zheng.orm.travel.mapper.AdminLogsMapper;
import com.zheng.travel.admin.commons.enums.ResultStatusEnumA;
import com.zheng.travel.admin.commons.ex.TravelValidationException;
import com.zheng.travel.admin.commons.utils.fn.asserts.Vsserts;
import com.zheng.travel.admin.pojo.AdminLogs;
import com.zheng.travel.admin.vo.AdminLogsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Slf4j
public class AdminLogsServiceImpl extends ServiceImpl<AdminLogsMapper,AdminLogs> implements IAdminLogsService  {


    /**
     * 查看方法的耗时和次数
     * @return
     */
    @Override
    public List<Map<String, Object>> selectMethodTime(Long timer){
        return this.baseMapper.selectMethodTime(timer);
    }

    /**
     *  统计浏览器占比
     * @return
     */
    @Override
    public List<Map<String,Object>> stateLogsBrowerversion(){
        return this.baseMapper.stateLogsBrowerversion();
    }

    /**
     *  统计操作系统占比
     * @return
     */
    @Override
    public List<Map<String,Object>> stateLogsOSversion(){
        return this.baseMapper.stateLogsOSversion();
    }

    /**
     * 查询分页&搜索后台日志
     */
    @Override
	public IPage<AdminLogs> findAdminLogsPage(AdminLogsVo adminlogsVo){
	    // 设置分页信息
		Page<AdminLogs> page = new Page<>(adminlogsVo.getPageNo(),adminlogsVo.getPageSize());
		// 设置查询条件
        LambdaQueryWrapper<AdminLogs> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // lambdaQueryWrapper.select(AdminLogs.class, column -> !column.getColumn().equals("description"));
        // 根据关键词搜索信息
        lambdaQueryWrapper.like(Vsserts.isNotEmpty(adminlogsVo.getKeyword()), AdminLogs::getClassname,adminlogsVo.getKeyword());
        // 根据关键词搜索信息
        lambdaQueryWrapper.like(Vsserts.isNotEmpty(adminlogsVo.getKeyword()), AdminLogs::getUsername,adminlogsVo.getKeyword());
         //查询发布的 0 未发布  1 发布
        lambdaQueryWrapper.eq(adminlogsVo.getStatus() != null ,AdminLogs::getStatus,adminlogsVo.getStatus());
        // 多列搜索
        // lambdaQueryWrapper.and(Vsserts.isNotEmpty(adminlogsVo.getKeyword()),wrapper -> wrapper
        //         .like(AdminLogs::getName, adminlogsVo.getKeyword())
        //         .or()
        //         .like(AdminLogs::getCategoryName, adminlogsVo.getKeyword())
        // );
        // 根据时间排降序
        lambdaQueryWrapper.orderByDesc(AdminLogs::getCreateTime);
        // 查询分页返回
		IPage<AdminLogs> results = this.page(page,lambdaQueryWrapper);
		return results;
	}

    /**
     * 查询后台日志列表信息
    */
    @Override
    public List<AdminLogs> findAdminLogsList() {
     	// 1：设置查询条件
        LambdaQueryWrapper<AdminLogs> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 2：查询发布的 0 未发布  1 发布
        lambdaQueryWrapper.eq(AdminLogs::getStatus,1);
        lambdaQueryWrapper.eq(AdminLogs::getIsdelete,0);
        // 3: 查询返回
        return this.list(lambdaQueryWrapper);
    }

	/**
     * 根据id查询后台日志明细信息
     */
    @Override
    public AdminLogs getAdminLogsById(Long id) {
        return this.getById(id);
    }


    /**
     * 保存&修改后台日志
     */
    @Override
	public AdminLogs saveupdateAdminLogs(AdminLogs adminlogs){
		boolean flag = this.saveOrUpdate(adminlogs);
		return flag ? adminlogs : null;
	}


    /**
     * 根据id删除后台日志
     */
    @Override
    public int deleteAdminLogsById(Long id) {
        boolean b = this.removeById(id);
        return b ? 1 : 0;
    }

    /**
     * 根据id删除
     */
    @Override
    public boolean delBatchAdminLogs(String ids) {
        try {
            // 1 : 把数据分割
            String[] strings = ids.split(",");
            // 2: 组装成一个List<AdminLogs>
            List<AdminLogs> adminlogsList = Arrays.stream(strings).map(idstr -> {
                AdminLogs adminlogs = new AdminLogs();
                adminlogs.setId(new Long(idstr));
                adminlogs.setIsdelete(1);
                return adminlogs;
            }).collect(Collectors.toList());
            // 3: 批量删除
            return this.updateBatchById(adminlogsList);
        } catch (Exception ex) {
            throw new TravelValidationException(ResultStatusEnumA.SERVER_DB_ERROR);
        }
    }


}