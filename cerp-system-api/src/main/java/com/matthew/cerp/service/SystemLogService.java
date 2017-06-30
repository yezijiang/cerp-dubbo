package com.matthew.cerp.service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.matthew.cerp.entity.TSLog;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * User: maxing
 * TIME: 2017-06-29 11:22
 */
public interface SystemLogService {
    void insertOperateLog(TSLog log);

    void insertOperateFailLog(TSLog log);

    void insertLoginLog(TSLog log);

    List<TSLog> queryLogData(Map<String, Object> params, PageBounds pageBounds);

}
