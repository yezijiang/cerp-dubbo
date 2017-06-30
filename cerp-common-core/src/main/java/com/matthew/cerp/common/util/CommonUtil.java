package com.matthew.cerp.common.util;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * User: maxing
 * TIME: 2017-06-30 14:00
 */
public class CommonUtil {
    /**
     * 方法名称 :getIpAddr
     * 创  建 人 :ZX
     * 创建时间 :2016-3-15 下午4:53:14
     * 方法描述 :获取登录用户IP地址
     *   参   数  :@param request
     *   参   数  :@return
     *   结   果  :String
     *   异   常  :@throws
     */
    public static String getIpAddr(HttpServletRequest request)
    {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }
        if (ip.equals("0:0:0:0:0:0:0:1"))
        {
            ip = "本地";
        }
        return ip;
    }

    /**
     *
     * 方法名称 :toArrayString
     * 创  建 人 :ZX
     * 创建时间 :2016-4-22 下午1:44:13
     * 方法描述 : 对象转数组
     *   参   数  :@param list
     *   参   数  :@return
     *   结   果  :String[]
     *   异   常  :@throws
     */
    @SuppressWarnings("rawtypes")
    public static String[] toArrayString(List list,String field)
    {
        String[] result =new String[list.size()];
        if(null!=list)
        {
            for(int i=0;i<list.size();i++)
            {
                Object curObj=list.get(i);
                Method[] methods = curObj.getClass().getMethods();
                for (int fmidx =0; fmidx<methods.length ; fmidx++)
                {
                    if (("get" + field).equalsIgnoreCase(methods[fmidx].getName()))
                    {
                        try
                        {
                            result[i] =(String)methods[fmidx].invoke(curObj);
                            break;
                        }
                        catch (IllegalAccessException e)
                        {
                            e.printStackTrace();
                        }
                        catch (IllegalArgumentException e)
                        {
                            e.printStackTrace();
                        }
                        catch (InvocationTargetException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 生成treeList对象
     */
    public static List<TreeNode> proceeTree(List<TreeNode> treeNode){
    List<TreeNode> r = new ArrayList<TreeNode>();
    Map<Long,TreeNode> tempMap = new HashMap<Long,TreeNode>();

    for(TreeNode entity : treeNode){
        tempMap.put(entity.getId(),entity);
    }
    for(TreeNode entity : treeNode){
        if(tempMap.get(entity.getPid()) != null){
            tempMap.get(entity.getPid()).getChildren().add(entity);
        }else{
            r.add(entity);
        }
    }
    return r;
    }
}
