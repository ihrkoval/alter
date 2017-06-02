package com.alter.automation.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jlab13 on 02.05.2017.
 */
public class TreeJson {
String text;
String href;
List<String> tags;
List<TreeJson> nodes;

    public TreeJson(String text, String href, List<String> tags, List<TreeJson> nodes) {
        this.text = text;
        this.href = href;
        this.tags = tags;
        this.nodes = nodes;
    }

    public TreeJson() {
        tags = new ArrayList<>();
       // tags.add("0");
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<TreeJson> getNodes() {
        return nodes;
    }

    public void setNodes(List<TreeJson> nodes) {
        this.nodes = nodes;
    }
}
