package com.zheng.orm.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zheng.travel.admin.pojo.AdminLogs;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AdminLogsMapper extends BaseMapper<AdminLogs> {

    /**
     * 统计浏览器占比
     *
     * @return
     */
    List<Map<String, Object>> stateLogsBrowerversion();

    /**
     * 统计操作系统占比
     *
     * @return
     */
    List<Map<String, Object>> stateLogsOSversion();

    /**
     * 查看方法的耗时和次数
     *
     * @return
     */
    List<Map<String, Object>> selectMethodTime(@Param("timer") Long time);

}