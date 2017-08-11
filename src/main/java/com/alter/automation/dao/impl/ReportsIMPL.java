package com.alter.automation.dao.impl;




import com.alter.automation.connection.AlterDB;
import com.alter.automation.dao.ReportsDao;
import com.alter.automation.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by jlab13 on 08.05.2017.
 */
@Repository
public class ReportsIMPL implements ReportsDao {

    @Autowired
    private AlterDB alterDB;
    private Statement statement;

    @Override
    public List<Order> orders1356rows(int agentId){
        statement = alterDB.getStatement();
        List<Order> ords = new ArrayList<>();

        String query = "select * from (select  " +
                "REGNUMBER as ID, " +
                "clientsname cname, " +
                "CLIENTADRESSLOCATION ClAdr, " +
                "AgentsName agname, " +
                "Docdate, " +
                "EndSumm, " +
                "debt,'~ '||COMMENT1 as comment1, " +
                "(select case when maxdelay is null then 0 else maxdelay end from credlimits where ClientID = i.ClientID and paycatid=2) daydelay, " +
                "(select sname from agents " +
                "where treelevel = 2 " +
                "and parentid <> 0 " +
                "and ( " +
                "id = (select a.parentid from agents a where id=(select a.parentid from agents a where id=i.agentid)) " +
                "or " +
                "id = (select a.parentid from agents a where id=i.agentid) " +
                ")) napravl, " +
                "(select sname from agents " +
                "where treelevel = 3 " +
                "and parentid <> 0 " +
                "and ( " +
                "id = (select a.parentid from agents a where id=(select a.parentid from agents a where id=i.agentid)) " +
                "or " +
                "id = (select a.parentid from agents a where id=i.agentid) " +
                ")) sv, " +
                "2 formpay,  " +
                "i.RESPONSIBLEPERSON " +
                "from sprInnerMigration(null,null,null,'01.01.2006','"+today()+"',1,'1,2,3,6',0,"+agentId+") i " +
                "where (PAYPERCENT <> 100 or UDFUNULLAS0(PAYPERCENT)= 0) " +
                "and debt>0 " +
                "union " +
                "select  " +
                "REGNUMBER as ID, " +
                "clientsname cname, " +
                "CLIENTADRESSLOCATION ClAdr, " +
                "AgentsName agname, " +
                "Docdate, " +
                "EndSumm, " +
                "debt, '~ '||COMMENT1 as comment1, " +
                "(select case when maxdelay is null then 0 else maxdelay end from credlimits where ClientID = O.ClientID and paycatid= iif(o.storeid =0,1,2)) daydelay, " +
                "(select sname from agents " +
                "where treelevel = 2 " +
                "and parentid <> 0 " +
                "and ( " +
                "id = (select a.parentid from agents a where id=(select a.parentid from agents a where id=o.agentid)) " +
                "or " +
                "id = (select a.parentid from agents a where id=o.agentid) " +
                ")) napravl, " +
                "(select sname from agents " +
                "where treelevel = 3 " +
                "and parentid <> 0 " +
                "and ( " +
                "id = (select a.parentid from agents a where id=(select a.parentid from agents a where id=o.agentid)) " +
                "or " +
                "id = (select a.parentid from agents a where id=o.agentid) " +
                ") " +
                ") sv, " +
                "iif(o.storeid = 8, 2, 1) formpay,  " +
                "o.RESPONSIBLEPERSON " +
                "from sprOutcomeInvoice " +
                "(null,null,'01.01.2006','"+today()+"',0,1,'1,2,3,6',0,"+agentId+") o " +
                "where (PAYPERCENT <> 100 or  UDFUNULLAS0(PAYPERCENT)= 0) " +
                "and debt>0) order by cname DESC, docdate DESC ";
        System.out.println(query);

        try {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
               // if (rs.getString("agname") != null){
                Order order = new Order();
                order.setId(rs.getString("id"));
                order.setCname(rs.getString("cname"));
                order.setCladr(rs.getString("ClAdr"));
                order.setAGENTSNAME(rs.getString("agname"));

                Date d = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("Docdate"));
                String formattedDate = new SimpleDateFormat("dd.MM.yyyy").format(d);

                order.setDocdate(formattedDate);
                order.setEndSumm(rs.getDouble("EndSumm"));
                order.setDebt(rs.getDouble("debt"));
                order.setComment1(rs.getString("comment1"));
                order.setDaydelay(rs.getString("daydelay"));
                order.setNapravl(rs.getString("napravl"));
                order.setSv(rs.getString("sv"));
                order.setFormpay(rs.getString("formpay"));
                order.setRESPONSIBLEPERSON(rs.getString("RESPONSIBLEPERSON"));
                ords.add(order);/*}
                else{
                    String query1 =  "select  " +
                            "0 as ID, " +
                            "0 as cname, " +
                            "0 as ClAdr, " +
                            "sname as agname, " +
                            "0 as Docdate, " +
                            "0 as EndSumm, " +
                            "0 as comment1, " +
                            "0 as daydelay, " +
                            "0 as napravl, " +
                            "0 as sv, " +
                            "0 as formpay,  " +
                            "0 as RESPONSIBLEPERSON " +
                            "from agents where id = "+agentId;
                    ResultSet rs1 = statement.executeQuery(query1);
                    while (rs1.next()){
                            Order order = new Order();
                            order.setId(rs1.getString("id"));
                            order.setCname(rs1.getString("cname"));
                            order.setCladr(rs1.getString("ClAdr"));
                            order.setAGENTSNAME(rs1.getString("agname"));
                            order.setDocdate(rs1.getString("Docdate"));
                            order.setEndSumm(rs1.getDouble("EndSumm"));
                            order.setDebt(rs1.getDouble("debt"));
                            order.setComment1(rs1.getString("comment1"));
                            order.setDaydelay(rs1.getString("daydelay"));
                            order.setNapravl(rs1.getString("napravl"));
                            order.setSv(rs1.getString("sv"));
                            order.setFormpay(rs1.getString("formpay"));
                            order.setRESPONSIBLEPERSON(rs1.getString("RESPONSIBLEPERSON"));
                            ords.add(order);

                }}*/
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        alterDB.close();
        return ords;
    }


    @Override
    public ResultSet rows1512(int agentId)  {

        statement = alterDB.getStatement();

        String query = "select * from ( select" +
                "      case" +
                "        when (substring(COMMENT2 from 1 for 5) like 'грибы') then PAYTYPEID||'_'||REGNUMBER||'_г'" +
                "        else PAYTYPEID||'_'||REGNUMBER" +
                "      end ID," +
                "      clientsname||'            '||CLIENTADRESSLOCATION ClAdr," +
                "      AgentsName," +
                "      ClientID," +
                "      Docdate," +
                "      EndSumm," +
                "      PAYPERCENT," +
                "      debt," +
                "      (select maxdelay from credlimits where ClientID = O.ClientID and paycatid=1) daydelay," +
                "      storesname," +
                "      0 RETURNSUMMA " +
                "from sprOutcomeInvoice" +
                "(null,null," +
                "'01.01.2006','"+today()+"'," +
                "0,1," +
                "'1,3'," +
                "0," +
                agentId +") o " +
                "where (PAYPERCENT <> 100 or  UDFUNULLAS0(PAYPERCENT)= 0)" +
                "      and debt>0" +
                "" +
                "      union" +
                "" +
                "      select " +
                "      PAYTYPEID||'_'||REGNUMBER as ID," +
                "      clientsname||'            '||CLIENTADRESSLOCATION ClAdr," +
                "      AgentsName," +
                "      ClientID," +
                "      Docdate," +
                "      EndSumm," +
                "      PAYPERCENT," +
                "      debt," +
                "      (select maxdelay from credlimits where ClientID = i.ClientID and paycatid=2) daydelay," +
                "      storesnameSRC," +
                "      0 RETURNSUMMA " +
                "from sprInnerMigration(null," +
                "null,null,'01.01.2006','"+today()+"'," +
                "1,'1,3'," +
                "0," +
                agentId +") i" +
                "     where (PAYPERCENT <> 100 " +
                "        or  UDFUNULLAS0(PAYPERCENT)= 0)" +
                "        and debt>0)" +
                "" +
                "        order by  ClientID, Docdate";
        try {
            ResultSet rs = statement.executeQuery(query);

            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Double getClientSaldo1512(int clientID){
       String query = "select saldo from  sprClientCard_SaldoOnDate ("+clientID+",'"+today()+"','1,3')";
       statement = alterDB.getStatement();
        double saldo = 0.0;
        try {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                saldo = rs.getDouble("saldo");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  saldo;

    }

    @Override
    public Double getVozvratSumm1512(int agentId, int clientId) {
        String query =  "select sum(endsumm) vozvr from  sprIncomeInvoice(null,null,'01.01.2006','"+today()+"','1,3',0,0,"+agentId+") where ClientID="+clientId+" and ISRETURN=1";
        double vozvr = 0.0;
        statement = alterDB.getStatement();
        try {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()){
                vozvr = rs.getDouble("vozvr");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vozvr;
    }

    @Override
    public String today(){
        Date dateNow = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String date = formatter.format(dateNow);

        return date;
    }

    @Override
    public void ConnectionClose(){
        alterDB.close();
    }
}
