package org.rd.poc.jreport.data;

import java.util.ArrayList;
import java.util.List;

public class Axis
{
    private Integer id;
    private String  axis;
    private List<Step> lstStep;

    public Axis()
    {
        id      = null;
        axis    = null;
        lstStep = new ArrayList<Step>();
    }

    public Axis(Integer id, String axis, List<Step> lstStep)
    {
        this.id      = id;
        this.axis    = axis;
        this.lstStep = lstStep;
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

    public List<Step> getLstStep()
    {
        return lstStep;
    }

    public void setLstStep(List<Step> lstStep)
    {
        this.lstStep = lstStep;
    }
}