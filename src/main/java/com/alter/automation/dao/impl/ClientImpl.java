package com.alter.automation.dao.impl;

import com.alter.automation.connection.AlterDB;
import com.alter.automation.dao.ClientDao;
import com.alter.automation.pojo.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jlab13 on 08.05.2017.
 */
@Repository
public class ClientImpl implements ClientDao {
    @Autowired
    private AlterDB alterDB;

    private Statement statement;
    private ResultSet rs;


    @Override
    public ResultSet getClientByid(int clientId) {
        statement = alterDB.getStatement();
        try {
            rs = statement.executeQuery("select * from Client where ID="+clientId);
            return  rs;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void ConnectionClose(){
        try {
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        alterDB.close();
    }

}
