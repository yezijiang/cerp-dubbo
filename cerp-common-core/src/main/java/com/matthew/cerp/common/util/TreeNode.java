package com.matthew.cerp.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * User: maxing
 * TIME: 2017-06-30 14:02
 */
public class TreeNode {


    private Long id;
    private Long pid;
    private String text;
    private String code;
    private List<TreeNode> children = new ArrayList<TreeNode>();

    public TreeNode(){

    }

    public TreeNode(Long id,Long pid,String text,String code){
        this.id = id;
        this.pid = pid;
        this.text = text;
        this.code = code;
//        children = new ArrayList<TreeNode>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", pid=" + pid +
                ", code=" + code +
                ", text='" + text + '\'' +
                ", children=" + children +
                '}';
    }
}
