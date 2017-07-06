package com.matthew.cerp.common.annotation;


import com.matthew.cerp.common.util.Constants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 项目名称 :cerp
 * 类  名 称 :SystemLog
 * 创  建 人 :ZX
 * 创建时间 :2016-3-14 下午6:20:12
 * 类  描 述 :系统日志
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SystemLog
{
    /**
     * 方法名称 :description
     * 创  建 人 :ZX
     * 创建时间 :2016-3-14 下午6:26:09
     * 方法描述 :操作內容，默认操作内容(例如：新增商品，修改商品，删除商品等)
     *   参   数  :@return
     *   结   果  :String
     *   异   常  :@throws
     */
    String description() default Constants.LOG_DESC_DEFAULT;
    
    /**
     * 方法名称 :operateCode
     * 创  建 人 :ZX
     * 创建时间 :2016-3-14 下午6:26:09
     * 方法描述 :操作编码，默认操作0(0:其他，1：新增，2：修改，3：删除，99：异常【系统预留】)
     *   参   数  :@return
     *   结   果  :String
     *   异   常  :@throws
     */
    String operateCode() default Constants.LOG_OPER_CODE_DEFAULT;
}
