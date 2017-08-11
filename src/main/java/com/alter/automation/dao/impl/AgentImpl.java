package com.alter.automation.dao.impl;

import com.alter.automation.connection.AlterDB;
import com.alter.automation.dao.AgentDao;
import com.alter.automation.dao.ReportsDao;
import com.alter.automation.pojo.Agent;
import com.alter.automation.pojo.TreeJson;
import com.alter.automation.utils.SendAttachmentInEmail;
import com.alter.automation.xls.Report1521;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jlab13 on 19.04.2017.
 */
@Repository
public class AgentImpl implements AgentDao {
    @Autowired
    private AlterDB alterDB;

    @Autowired
    ReportsDao reportsDao;

    @Autowired
    Report1521 report1521;



    private Statement statement;

    @Override
    public List<Agent> getAgentsByLevelTWO() {

        statement = alterDB.getStatement();
        List<Agent> agents = new ArrayList<>();
        try {
            ResultSet agentsSql = statement.executeQuery("select id, parentid, sname, treelevel, beepresemail from agents a where a.treelevel = 2 and a.parentid = 1");
            while (agentsSql.next()){
                Agent agent = new Agent(agentsSql.getInt("id"),
                        agentsSql.getInt("parentid"),
                        agentsSql.getString("sname"),
                        agentsSql.getInt("treelevel"),
                        agentsSql.getString("beepresemail"));
                agents.add(agent);
            }
            alterDB.close();
            return agents;

        } catch (Exception e){
        e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Agent> getAgentByParentAndTree(int treelevel, int parentid) {

        statement = alterDB.getStatement();
        List<Agent> agents = new ArrayList<>();
        try {
            ResultSet agentsSql = statement.executeQuery("select id, parentid, sname, treelevel, beepresemail from agents a where a.treelevel = "+treelevel+" and a.parentid = "+parentid);
            while (agentsSql.next()){
                Agent agent = new Agent(agentsSql.getInt("id"),
                        agentsSql.getInt("parentid"),
                        agentsSql.getString("sname"),
                        agentsSql.getInt("treelevel"),
                        agentsSql.getString("beepresemail"));
                agents.add(agent);

            }
            alterDB.close();
            return agents;

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Agent getAgentById(int id){
        statement = alterDB.getStatement();
        try {
            ResultSet agentsSql = statement.executeQuery("select a.id, a.parentid, a.sname, a.treelevel, b.smtpfrom from agents a,  BPOPTIONS b where a.id = " + id+" and b.agentid = a.id");
            Agent agent = null;
            while (agentsSql.next()) {
                agent = new Agent(agentsSql.getInt("id"),
                        agentsSql.getInt("parentid"),
                        agentsSql.getString("sname"),
                        agentsSql.getInt("treelevel"),
                        agentsSql.getString("smtpfrom"));

            }
            agentsSql.close();
            alterDB.close();
            return agent;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void sendToAgent(List<TreeJson> treeJsons){

        for (TreeJson node : treeJsons){
            if (node.getTags().get(0) != null) {
                    int agentId = Integer.valueOf(node.getTags().get(0));
                    String reportPath = "";
                    try {
                        reportPath = report1521.createReport(agentId);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    Agent agent = getAgentById(agentId);
                    new SendAttachmentInEmail().sendMessage(agent.getEmail(), "Alter Debit report", "",reportPath);

            }
        }
        //get emails and ids
        //create class for report
        alterDB.close();
    }

    @Override
    public List<TreeJson> getTreeJson() {

        List<Agent> agentsLvl2 = getAgentsByLevelTWO();
        List<TreeJson> treeJson = new ArrayList<TreeJson>();

        for (Agent agent2: agentsLvl2){
            TreeJson agent2Tree = new TreeJson();
            agent2Tree.setText(agent2.getSname());
            agent2Tree.setHref("#id"+agent2.getId());
            agent2Tree.setNodes(new ArrayList<>());
            agent2Tree.getTags().add(String.valueOf(agent2.getId()));

            List<Agent> childs = getAgentByParentAndTree(3, agent2.getId());
            for (Agent child : childs){
                TreeJson childTree = new TreeJson();
                childTree.setNodes(new ArrayList<>());
                childTree.setText(child.getSname());
                childTree.setHref("#id"+child.getId());
                childTree.getTags().add(String.valueOf(child.getId()));

                List<Agent> grandChild = getAgentByParentAndTree(4, child.getId());
                    for (Agent grand : grandChild){
                        TreeJson grandchildTree = new TreeJson();
                        grandchildTree.setText(grand.getSname());
                        grandchildTree.setHref("#id"+child.getId());
                        grandchildTree.getTags().add(String.valueOf(grand.getId()));
                        childTree.getNodes().add(grandchildTree);
                    }


                agent2Tree.getNodes().add(childTree);
            }
            treeJson.add(agent2Tree);
        }

        return treeJson;
    }
}
