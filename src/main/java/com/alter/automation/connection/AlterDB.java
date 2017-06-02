package com.alter.automation.connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by jlab13 on 19.04.2017.
 */
public class AlterDB {


    String url;
    String name;
    String password;
    Connection connection;

    public Statement getStatement(){
        try {
            Class.forName("org.firebirdsql.jdbc.FBDriver");
            connection = DriverManager.getConnection(url, name, password);
            Statement statement =  connection.createStatement();
            return statement;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    public void close(){
        try {

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public AlterDB(){

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }




}
