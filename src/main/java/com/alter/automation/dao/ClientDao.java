package com.alter.automation.dao;

import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jlab13 on 08.05.2017.
 */
public interface ClientDao {
    ResultSet getClientByid(int clientId);
    void ConnectionClose();
}
