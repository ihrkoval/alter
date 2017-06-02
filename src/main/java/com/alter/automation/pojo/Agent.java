package com.alter.automation.pojo;

/**
 * Created by jlab13 on 24.04.2017.
 */
public class Agent {
    int id;
    int parrentid;
    String sname;
    int treelevel;
    String email;

    public Agent(int id, int parrentid, String sname, int treelevel, String email) {
        this.id = id;
        this.parrentid = parrentid;
        this.sname = sname;
        this.treelevel = treelevel;
        this.email = email;
    }

    public Agent() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParrentid() {
        return parrentid;
    }

    public void setParrentid(int parrentid) {
        this.parrentid = parrentid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public int getTreelevel() {
        return treelevel;
    }

    public void setTreelevel(int treelevel) {
        this.treelevel = treelevel;
    }


}
