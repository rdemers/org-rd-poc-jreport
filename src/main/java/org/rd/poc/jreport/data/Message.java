package org.rd.poc.jreport.data;

public class Message
{
    private String exceptionCode;
    private String exceptionDescription;
    private String exceptionDetail;

    public Message()
    {
        exceptionCode        = null;
        exceptionDescription = null;
        exceptionDetail      = null;;
    }

    public Message(String exceptionCode, String exceptionDescription, String exceptionDetail)
    {
        this.exceptionCode        = exceptionCode;
        this.exceptionDescription = exceptionDescription;
        this.exceptionDetail      = exceptionDetail;
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