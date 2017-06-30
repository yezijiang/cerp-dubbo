package com.matthew.cerp.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.matthew.cerp.entity.TSLog;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * User: maxing
 * TIME: 2017-06-30 13:38
 */
public interface SystemLogDao {

    /**
     * 新增操作日志
     * @param log
     */
    void insertOperateLog(TSLog log);

    /**
     * 新增登陆日志
     * @param log
     */
    void insertLoginLog(TSLog log);

    /**
     * 查询操作日志数据
     * @param params
     * @param pageBounds
     * @return
     */
    List<TSLog> queryOperateLogData(Map<String, Object> params, PageBounds pageBounds);

    /**
     * 查询登录日志数据
     * @param params
     * @param pageBounds
     * @return
     */
    List<TSLog> queryLoginLogData(Map<String, Object> params, PageBounds pageBounds);

}
