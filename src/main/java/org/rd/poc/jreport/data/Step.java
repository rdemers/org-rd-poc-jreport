package org.rd.poc.jreport.data;

import java.util.ArrayList;
import java.util.List;

import org.rd.poc.jreport.data.Message;

public class Step
{
    private String  step;
    private String  state;
    private List<Message> lstMessage;

    public Step()
    {
        step  = null;
        state = null;
        lstMessage = new ArrayList<Message>();
    }

    public Step(String step, String state, List<Message> lstMessage)
    {
        this.step       = step;
        this.state      = state;
        this.lstMessage = lstMessage;
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

    public List<Message> getLstMessage()
    {
        return lstMessage;
    }

    public void setLstMessage(List<Message> lstMessage)
    {
        this.lstMessage = lstMessage;
    }
}