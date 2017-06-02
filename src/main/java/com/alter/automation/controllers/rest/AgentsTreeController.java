package com.alter.automation.controllers.rest;

import com.alter.automation.dao.AgentDao;
import com.alter.automation.dao.ReportsDao;
import com.alter.automation.pojo.Agent;
import com.alter.automation.pojo.TreeJson;
import com.alter.automation.xls.Report1356;
import com.alter.automation.xls.Report1521;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jlab13 on 26.04.2017.
 */
@RestController
@RequestMapping(value = "/agent")
public class AgentsTreeController {
    @Autowired
    AgentDao agentDao;
    @Autowired
    ReportsDao reportsDao;

    @Autowired
    Report1356 report1356;

    @RequestMapping("/tree")
    public List<Agent> orders(
                              @RequestParam(value = "treelevel") int treelevel,
                              @RequestParam(value = "parentid") int parentid) {
        List<Agent> agents =  agentDao.getAgentByParentAndTree(treelevel, parentid);
        return  agents;
    }

    @RequestMapping("/jsontree")
    public List<TreeJson> tree() {
        return  agentDao.getTreeJson();
    }

    @RequestMapping(value ="/submit",  method = RequestMethod.POST)
    public void submitordr(@RequestBody List<TreeJson>  treeJson) {
        report1356.sendReport(treeJson);
    }
}
