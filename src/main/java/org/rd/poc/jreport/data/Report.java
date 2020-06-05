package org.rd.poc.jreport.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Report
{
    private Integer            id;
    private String             name;
    private Date               date;
    private List<ReportDetail> reportDetail;
    
    public Report()
    {
        id           = null;
        name         = null;
        date         = null;
        reportDetail = new ArrayList<ReportDetail>();
    }

    public Report(Integer id, String name, Date date)
    {
        this.id           = id;
        this.name         = name;
        this.date         = date;
        this.reportDetail = new ArrayList<ReportDetail>();
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public List<ReportDetail> getReportDetail()
    {
        return reportDetail;
    }

    public void setReportDetail(List<ReportDetail> reportDetail)
    {
        this.reportDetail = reportDetail;
    }

    public void add(ReportDetail rptDetail)
    {
        reportDetail.add(rptDetail);
    }
}