package com.alter.automation.controllers;

import com.alter.automation.dao.AgentDao;
import com.alter.automation.dao.ClientDao;
import com.alter.automation.dao.ReportsDao;
import com.alter.automation.xls.Report1521;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;


/**
 * Created by jlab13 on 19.04.2017.
 */
@Controller
public class MainController {

    @Autowired
    AgentDao agentDao;

    @Autowired
    ReportsDao reportsDao;

    @Autowired
    ClientDao clientDao;

    @Autowired
    Report1521 report1521;

    ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(value ="/")
    public ModelAndView index() throws JsonProcessingException {
        ModelAndView index = new ModelAndView("index");
        index.addObject("agentsLvlTwo", mapper.writeValueAsString(agentDao.getAgentsByLevelTWO()));
        return index;
    }

    @RequestMapping(value ="/test")
    public ModelAndView test() throws SQLException {
        ModelAndView index = new ModelAndView("index");

          /* Report1521 report1521 = new Report1521();
           report1521.createReport(101, reportsDao.rows1512(101));*/
       // reportsDao.rows1512(101);

        return index;
    }
}
