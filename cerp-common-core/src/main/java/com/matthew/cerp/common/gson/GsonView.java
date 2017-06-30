package com.matthew.cerp.common.gson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;


/**
 * Created with IntelliJ IDEA
 * User: maxing
 * TIME: 2017-06-29 10:58
 */
public class GsonView extends AbstractView
{

    private int responseStatus = HttpStatus.OK.value();

    private String jsonObjectName;
    private SerializerFeature[] features;

    public GsonView() {
        super();
    }

    public GsonView(String jsonObjectName) {
        super();
        this.jsonObjectName = jsonObjectName;
    }

    public GsonView(String jsonObjectName, SerializerFeature[] features) {
        super();
        this.jsonObjectName = jsonObjectName;
        this.features = features;
    }


    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setStatus(getResponseStatus());
        response.setContentType(getContentType());
        Object obj= (jsonObjectName == null ? model : model.get(jsonObjectName));
        String jsonStr = "";
        if(features == null) {
            jsonStr = JSON.toJSONString(obj);
        }
        else{
            jsonStr = JSON.toJSONString(obj, features);
        }
        PrintWriter out = response.getWriter();
        out.write(jsonStr);
        out.flush();
    }
    @Override
    public String getContentType() {
        return "text/html;charset=utf-8";
    }

    public int getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getJsonObjectName() {
        return jsonObjectName;
    }

    public void setJsonObjectName(String jsonObjectName) {
        this.jsonObjectName = jsonObjectName;
    }

    public SerializerFeature[] getFeatures() {
        return features;
    }

    public void setFeatures(SerializerFeature[] features) {
        this.features = features;
    }

}
