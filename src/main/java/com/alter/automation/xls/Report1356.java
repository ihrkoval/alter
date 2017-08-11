package com.alter.automation.xls;

import com.alter.automation.dao.AgentDao;
import com.alter.automation.dao.ClientDao;
import com.alter.automation.dao.ReportsDao;
import com.alter.automation.pojo.Order;
import com.alter.automation.pojo.TreeJson;
import com.alter.automation.utils.SendAttachmentInEmail;
import com.alter.automation.utils.ZipFiles;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jlab13 on 15.05.2017.
 */
@Component
public class Report1356 {
    @Autowired
    private AgentDao agentDao;

    @Autowired
    private ReportsDao reportsDao;

    private List<String> generateReports(TreeJson treeJson){
        List<String> reportPaths = new ArrayList<>();
            List<TreeJson> childs = treeJson.getNodes();
            if (childs != null){
            for (TreeJson tjChilds: childs){
                String path = createFile(Integer.valueOf(tjChilds.getTags().get(0)));
                reportPaths.add(path);
            }} else {
                String path = createFile(Integer.valueOf(treeJson.getTags().get(0)));
                reportPaths.add(path);
            }

        return reportPaths;
    }

    private String createFile(int agentId){
        File file = new File("C:\\reports\\templates\\template1356.xls");

        HSSFWorkbook myExcelBook = null;
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        List<Order> agentrows = reportsDao.orders1356rows(agentId);
        try {
            inputStream = new FileInputStream(file);
            myExcelBook = new HSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HSSFSheet templateSheet = myExcelBook.getSheetAt(1);
        HSSFSheet reportSheet = myExcelBook.getSheetAt(0);
        HSSFRow orderRowtmpl = templateSheet.getRow(4);
        HSSFRow orderRow = reportSheet.getRow(4);
        String agentsname = "";

        for (Order order: agentrows){
            reportSheet.shiftRows(4, reportSheet.getLastRowNum(), 1, true, false);
            HSSFRow newRow = reportSheet.createRow(4);
            for (int i = 0; i < orderRowtmpl.getLastCellNum() ; i++) {
                Cell cell = newRow.createCell(i);
                cell.setCellStyle(orderRowtmpl.getCell(i).getCellStyle());
            }

            Cell fo = newRow.getCell(1);
            fo.setCellValue(order.getFormpay());

            Cell sv = newRow.getCell(2);
            sv.setCellValue(order.getSv());

            Cell agent = newRow.getCell(3);
            agentsname = order.getAGENTSNAME();
            agent.setCellValue(order.getAGENTSNAME());

            Cell otvetstv = newRow.getCell(4);
            otvetstv.setCellValue(order.getAGENTSNAME());

            Cell client = newRow.getCell(5);
            client.setCellValue(order.getCname());

            Cell adr = newRow.getCell(6);
            adr.setCellValue(order.getCladr());

            Cell docdate = newRow.getCell(7);
            docdate.setCellValue(order.getDocdate());

            Cell docNum = newRow.getCell(8);
            docNum.setCellValue(order.getId());

            Cell endSum = newRow.getCell(9);
            endSum.setCellValue(order.getEndSumm());

            Cell debt = newRow.getCell(10);
            debt.setCellValue(order.getDebt());

            Cell dayDelay = newRow.getCell(11);
            try {
                dayDelay.setCellValue(Integer.valueOf(order.getDaydelay()));
            } catch (NumberFormatException e){
                dayDelay.setCellValue(0);
            }

            Cell prosrochKD = newRow.getCell(12);
            prosrochKD.setCellFormula(orderRowtmpl.getCell(12).getCellFormula());

            Cell neProsrochKD = newRow.getCell(13);
            neProsrochKD.setCellFormula(orderRowtmpl.getCell(13).getCellFormula());

            Cell prosroch= newRow.getCell(14);
            prosroch.setCellFormula(orderRowtmpl.getCell(14).getCellFormula());

            Cell shtraf = newRow.getCell(15);
            shtraf.setCellFormula(orderRowtmpl.getCell(15).getCellFormula());

            Cell comment = newRow.getCell(16);
            comment.setCellValue(order.getComment1());
        }

        int lastRowNum = +reportSheet.getLastRowNum();
        reportSheet.getRow(0).getCell(2).setCellValue(reportsDao.today());
        reportSheet.getRow(0).getCell(9).setCellValue(reportsDao.today());
        reportSheet.getRow(2).getCell(10).setCellFormula("SUBTOTAL(9,K5:K"+lastRowNum+")");
        reportSheet.getRow(2).getCell(13).setCellFormula("SUBTOTAL(9,N5:N"+lastRowNum+")");
        reportSheet.getRow(2).getCell(14).setCellFormula("SUBTOTAL(9,O5:O"+lastRowNum+")");
        if (agentsname.length() < 1){
            try {
                agentsname = agentDao.getAgentById(agentId).getSname();
            } catch (NullPointerException e){
                agentsname = "agent_kod_"+agentId+"_not_found(BeePres)";
            }
        }

        try {
            File oututPath = new File("C:\\reports\\"+agentsname+".xls");
            outputStream = new FileOutputStream(oututPath);
            myExcelBook.write(outputStream);
            myExcelBook.close();
            inputStream.close();
            outputStream.close();

        }catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(agentsname);

        return "C:\\reports\\"+agentsname+".xls";
    }

    public void sendReport(List<TreeJson> treeJsons){
        for (TreeJson tj : treeJsons) {
            List<String> excelFiles = generateReports(tj);
            String reportsArch = new ZipFiles().zipFiles(excelFiles);
            String email = agentDao.getAgentById(Integer.valueOf(tj.getTags().get(0))).getEmail();
            System.out.println(email + " " +reportsArch);
           // new SendAttachmentInEmail().sendMessage(email, "alter-debit-report", "", reportsArch);
        }
}


}
