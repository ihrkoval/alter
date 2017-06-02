package com.alter.automation.dao;

import com.alter.automation.pojo.Order;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by jlab13 on 08.05.2017.
 */
public interface ReportsDao {
    ResultSet rows1512(int agentId);
    Double getClientSaldo1512(int clientId);
    Double getVozvratSumm1512(int agentId, int clientId);
    String today();
    List<Order> orders1356rows(int agentId);
    void ConnectionClose();
}
