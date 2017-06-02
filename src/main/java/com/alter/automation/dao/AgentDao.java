package com.alter.automation.dao;

import com.alter.automation.pojo.Agent;
import com.alter.automation.pojo.TreeJson;

import java.util.List;

/**
 * Created by jlab13 on 19.04.2017.
 */

public interface AgentDao {
    List<Agent> getAgentsByLevelTWO();
    List<Agent> getAgentByParentAndTree(int treelevel, int parentid);
    List<TreeJson> getTreeJson();
    void sendToAgent(List<TreeJson> treeJsons);
    Agent getAgentById(int id);
}
