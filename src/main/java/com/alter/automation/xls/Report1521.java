
package com.alter.automation.xls;

import com.alter.automation.dao.ClientDao;
;
import com.alter.automation.dao.ReportsDao;
import com.alter.automation.pojo.Order;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Component
public class Report1521 {

    @Autowired
    private ClientDao clientDao;

    @Autowired
    private ReportsDao reportsDao;

    public String createReport(int agentId) throws SQLException {
        File file = new File("C:\\test\\1.xls");
        HSSFWorkbook myExcelBook = null;
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(file);
            myExcelBook = new HSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HSSFSheet templateSheet = myExcelBook.getSheetAt(1);
        HSSFSheet reportSheet = myExcelBook.getSheetAt(0);

        HSSFRow clientRowTempl = templateSheet.getRow(6);
        HSSFRow orderRow = templateSheet.getRow(7);


        List<Order> orders = getOrders(agentId); //resultSet;//
        String today = reportsDao.today();
        int clientid = 0;
        double saldo = 0.0;
        String agenttsName = "";
        //double otgruzSum = 0.0;
        //double itogSum = 0.0;
        for (Order order: orders){
            if (order.getClientid() != clientid) {
                reportSheet.shiftRows(6, reportSheet.getLastRowNum(), 2, true, false);
                saldo = getSaldo(order.getClientid()); //reportsDao.getClientSaldo1512(orders.getInt("clientid"));
            }

                Row clientRow = reportSheet.createRow(6);
                Cell clientCell = clientRow.createCell(0);
                clientCell.setCellStyle(clientRowTempl.getCell(0).getCellStyle());
                clientCell.setCellValue(order.getCladr());

                reportSheet.shiftRows(7, reportSheet.getLastRowNum(), 1, true, false);
                Row nakladRow = reportSheet.createRow(7);
                for (int i = 0; i < orderRow.getLastCellNum() ; i++) {
                    Cell cell = nakladRow.createCell(i);
                    cell.setCellStyle(orderRow.getCell(i).getCellStyle());
                }
                //7
                Cell edrpou = nakladRow.getCell(1);
                String okpo = getOkpo(order.getClientid());
                /*ResultSet rsOKPO = clientDao.getClientByid(orders.getInt("clientid"));
                while (rsOKPO.next()){
                    okpo = rsOKPO.getString("BankOKPO");
                }*/
                edrpou.setCellValue(okpo);
                //rsOKPO.close();




                Cell docdate = nakladRow.getCell(2);
                docdate.setCellValue(order.getDocdate());

            Cell regnum = nakladRow.getCell(3);
            regnum.setCellValue(order.getId());
            System.out.println(order.getId());

            Cell agent = nakladRow.getCell(4);
            agent.setCellValue(order.getAGENTSNAME());
            reportSheet.getRow(3).getCell(3).setCellValue(order.getAGENTSNAME());

            reportSheet.getRow(2).getCell(9).setCellValue(today);

            Cell otgruz = nakladRow.getCell(5);
            otgruz.setCellValue(order.getEndSumm());

           // otgruzSum += orders.getDouble("EndSumm");

            Cell vozvr = nakladRow.getCell(6);
            //vozvr.setCellValue(orders.getDouble("RETURNSUMMA"));
            double vozvratSum = reportsDao.getVozvratSumm1512(agentId, clientid);
            vozvr.setCellValue(vozvratSum);
            reportsDao.ConnectionClose();

            Cell prosrochday = nakladRow.getCell(7);
           // prosrochday.setCellType(prosrochCell.getCellTypeEnum());
            prosrochday.setCellFormula(orderRow.getCell(7).getCellFormula());

            Cell week = nakladRow.getCell(8);
            week.setCellFormula(orderRow.getCell(8).getCellFormula());

            Cell overweek = nakladRow.getCell(9);
            overweek.setCellFormula(orderRow.getCell(9).getCellFormula());

            Cell over2week = nakladRow.getCell(10);
            over2week.setCellFormula(orderRow.getCell(10).getCellFormula());

            Cell month = nakladRow.getCell(11);
            month.setCellFormula(orderRow.getCell(11).getCellFormula());

            Cell overM = nakladRow.getCell(12);
            overM.setCellFormula(orderRow.getCell(12).getCellFormula());

            Cell itog = nakladRow.getCell(13);
            itog.setCellValue(order.getDebt());
           // itogSum += orders.getDouble("debt");

            Cell deley = nakladRow.getCell(14);
            deley.setCellValue(order.getDaydelay());



            Cell saldoCell = nakladRow.getCell(15);
            saldoCell.setCellValue(saldo);

            Cell shtraf = nakladRow.getCell(16);
            shtraf.setCellFormula(orderRow.getCell(16).getCellFormula());

                clientid = order.getClientid();

        }


        try {
            outputStream = new FileOutputStream(new File("C:\\test\\Agent-"+agentId+".xls"));
            myExcelBook.write(outputStream);
            myExcelBook.close();
            inputStream.close();
            outputStream.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
        reportsDao.ConnectionClose();
        clientDao.ConnectionClose();
return "C:\\test\\Agent-"+agentId+".xls";
    }

    private String getOkpo(int clientId){
        ResultSet rsOKPO = clientDao.getClientByid(clientId);
        String okpo = "";
        try {
            while (rsOKPO.next()){
                okpo = rsOKPO.getString("BankOKPO");
            }
            rsOKPO.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        clientDao.ConnectionClose();
       return okpo;
    }

    private double getSaldo(int clientid)
    {
        double saldo = reportsDao.getClientSaldo1512(clientid);
        reportsDao.ConnectionClose();
        return saldo;
    }

    private  List<Order>  getOrders(int agentId){
        ResultSet rs = reportsDao.rows1512(agentId);
        List<Order> orders = new ArrayList<>();
        try {
            while (rs.next()){
                Order order = new Order();
                order.setClientid(rs.getInt("clientid"));
                order.setAGENTSNAME(rs.getString("agentsname"));
                order.setCladr(rs.getString("cladr"));
                order.setDocdate(rs.getString("docdate"));
                order.setId(rs.getString("id"));
                order.setAGENTSNAME(rs.getString("agentsname"));
                order.setEndSumm(rs.getDouble("endsumm"));
                order.setDebt(rs.getDouble("debt"));
                order.setDaydelay(rs.getString("daydelay"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        reportsDao.ConnectionClose();
        return orders;
    }

}

