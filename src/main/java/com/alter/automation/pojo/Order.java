package com.alter.automation.pojo;

/**
 * Created by jlab13 on 12.05.2017.
 */
public class Order {
    Integer clientid;
    String cladr;
    String cname;
    String docdate;
    String id;
    String AGENTSNAME;
    Double EndSumm;
    Double debt;
    String comment1;
    String napravl;
    String daydelay;
    String sv;
    String formpay;
    String RESPONSIBLEPERSON;

    public Order(){}

    public String getRESPONSIBLEPERSON() {
        return RESPONSIBLEPERSON;
    }

    public void setRESPONSIBLEPERSON(String RESPONSIBLEPERSON) {
        this.RESPONSIBLEPERSON = RESPONSIBLEPERSON;
    }

    public String getNapravl() {
        return napravl;
    }

    public String getSv() {
        return sv;
    }

    public String getFormpay() {
        return formpay;
    }

    public void setFormpay(String formpay) {
        this.formpay = formpay;
    }

    public void setSv(String sv) {
        this.sv = sv;
    }

    public void setNapravl(String napravl) {
        this.napravl = napravl;
    }

    public String getCname() {
        return cname;
    }

    public String getComment1() {
        return comment1;
    }

    public void setComment1(String comment1) {
        this.comment1 = comment1;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public void setClientid(Integer clientid) {
        this.clientid = clientid;
    }

    public void setCladr(String cladr) {
        this.cladr = cladr;
    }

    public void setDocdate(String docdate) {
        this.docdate = docdate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAGENTSNAME(String AGENTSNAME) {
        this.AGENTSNAME = AGENTSNAME;
    }

    public void setEndSumm(Double endSumm) {
        EndSumm = endSumm;
    }

    public void setDebt(Double debt) {
        this.debt = debt;
    }

    public void setDaydelay(String daydelay) {
        this.daydelay = daydelay;
    }

    public Integer getClientid() {
        return clientid;
    }

    public String getCladr() {
        return cladr;
    }

    public String getDocdate() {
        return docdate;
    }

    public String getId() {
        return id;
    }

    public String getAGENTSNAME() {
        return AGENTSNAME;
    }

    public Double getEndSumm() {
        return EndSumm;
    }

    public Double getDebt() {
        return debt;
    }

    public String getDaydelay() {
        return daydelay;
    }
}
