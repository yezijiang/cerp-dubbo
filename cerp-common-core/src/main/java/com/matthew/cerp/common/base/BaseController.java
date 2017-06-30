package com.matthew.cerp.common.base;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import com.matthew.cerp.common.gson.GsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller类公用基础类，用来扩展公用方法
 * Created with IntelliJ IDEA
 * User: maxing
 * TIME: 2017-06-28 16:04
 */
public class BaseController {
    static Logger log = LoggerFactory.getLogger(BaseController.class);
    public static final String VIEW = "view";
    public static final String LIST = "list";
    public static final String STATUS = "status";
    public static final String WARN = "warn";
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    public static final String MESSAGE = "message";
    public static final String MESSAGES = "messages";
    public static final String CONTENT = "content";

    /**
     * 对象可以是map、list等类型
     * @param object
     * @return
     */
    protected <T> View toView(Model model, Object object) {
        model.addAttribute("root", object);
        return new GsonView("root", null);
    }

    /**
     * 根据结果集返回bootstrap-table所需视图
     * @param model
     * @param list
     * @param <T>
     * @return
     */
    protected <T> View toTableView(Model model, List<T> list) {
        Map<String, Object> map = toJsonList(list);
        model.addAttribute("root", map);
        return new GsonView("root", null);
    }

    /**
     * 转为bootstrap-table能够识别的数据结构
     * @param list
     * @param <T>
     * @return
     */
    protected <T> Map<String, Object> toJsonList(List<T> list) {
        Map<String, Object> map = new HashMap<String, Object>();
        if(list instanceof PageList) {
            map = generateMapFromPageList((PageList) list);
        }
        else{
            map.put("rows", list);
        }
        return map;
    }

    /**
     *
     * @param pagelist
     * @param <T>
     * @return
     */
    private <T> Map<String,Object> generateMapFromPageList(PageList<T> pagelist) {
        Map<String, Object> map = new HashMap<String, Object>();
        Paginator paginator = pagelist.getPaginator();
        map.put("total", paginator.getTotalCount());
        map.put("totalPages", paginator.getTotalPages());
        map.put("page", paginator.getPage());
        map.put("limit", paginator.getLimit());
        map.put("rows", new ArrayList(pagelist));

        map.put("startRow", paginator.getStartRow());
        map.put("endRow", paginator.getEndRow());

        map.put("offset", paginator.getOffset());
        map.put("slider", paginator.getSlider());

        map.put("prePage", paginator.getPrePage());
        map.put("nextPage", paginator.getNextPage());

        map.put("firstPage", paginator.isFirstPage());
        map.put("hasNextPage", paginator.isHasNextPage());
        map.put("hasPrePage", paginator.isHasPrePage());
        map.put("lastPage", paginator.isLastPage());
        return map;
    }

    /**
     * 获取表格分页用的对象
     * @param params
     * @return
     */
    public PageBounds getPageBounds(Map<String,Object> params)
    {
        int offset=Integer.valueOf(params.get("offset").toString());//偏移量
        int limit = Integer.valueOf(params.get("limit").toString());//每页记录数
        int currentPage = offset/limit + 1;//当前页数
        return new PageBounds(currentPage, limit);
    }

    /**
     * 获取表格分页用的对象
     * @param params
     * @param sortString
     * @return
     */
    public PageBounds getPageBounds(Map<String,Object> params,String sortString)
    {
        int offset=Integer.valueOf(params.get("offset").toString());//偏移量
        int limit = Integer.valueOf(params.get("limit").toString());//每页记录数
        int currentPage = offset/limit + 1;//当前页数
        return new PageBounds(currentPage, limit, Order.formString(sortString));
    }
    /**
     * AJAX输出，返回null
     *
     * @param content
     * @param type
     * @return
     */
    public String ajax(HttpServletResponse response, String content, String type) {
        try {
            response.setContentType(type + ";charset=UTF-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.getWriter().write(content);
            response.getWriter().flush();
        } catch (IOException e) {
            log.error("IOException:", e);
        }
        return null;
    }
}
