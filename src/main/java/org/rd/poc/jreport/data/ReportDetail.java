package org.rd.poc.jreport.data;

public class ReportDetail
{
    private Integer id;
    private String  axis;
    private String  step;
    private String  state;
    private String  exceptionCode;
    private String  exceptionDescription;
    private String  exceptionDetail;

    public ReportDetail()
    {
        id                   = null;
        axis                 = null;
        step                 = null;
        state                = null;
        exceptionCode        = null;
        exceptionDescription = null;
        exceptionDetail      = null;
    }

    public ReportDetail(Integer id, String axis, String step, String state, 
                  String exceptionCode, String exceptionDescription, String exceptionDetail)
    {
        this.id                   = id;
        this.axis                 = axis;
        this.step                 = step;
        this.state                = state;
        this.exceptionCode        = exceptionCode;
        this.exceptionDescription = exceptionDescription;
        this.exceptionDetail      = exceptionDetail;;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getAxis()
    {
        return axis;
    }

    public void setAxis(String axis)
    {
        this.axis = axis;
    }

    public String getStep()
    {
        return step;
    }

    public void setStep(String step)
    {
        this.step = step;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getExceptionCode()
    {
        return exceptionCode;
    }

    public void setExceptionCode(String exceptionCode)
    {
        this.exceptionCode = exceptionCode;
    }

    public String getExceptionDescription()
    {
        return exceptionDescription;
    }

    public void setExceptionDescription(String exceptionDescription)
    {
        this.exceptionDescription = exceptionDescription;
    }

    public String getExceptionDetail()
    {
        return exceptionDetail;
    }

    public void setExceptionDetail(String exceptionDetail)
    {
        this.exceptionDetail = exceptionDetail;
    }
}