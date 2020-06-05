package org.rd.poc.jreport.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.rd.poc.jreport.data.Axis;
import org.rd.poc.jreport.data.Message;
import org.rd.poc.jreport.data.Report;
import org.rd.poc.jreport.data.ReportDetail;
import org.rd.poc.jreport.data.Step;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DaoData
{
	/**
	 * Construire artificiellement un jeu de données pour le rapport.
	 * 
	 * @return Collection Axis.
	 */
    public static Collection<Axis> createCollection()
    {
        Axis axis; Step step; Message message;
        List<Step>    lstStep;
        List<Message> lstMessage;
        List<Axis>    lstAxis    = new ArrayList<Axis>();

        // Axis 1.
        lstStep    = new ArrayList<Step>();
        lstMessage = new ArrayList<Message>();

        message = new Message("", "", "OK.");                lstMessage.add(message);
        step    = new Step("Étape 1", "Succès", lstMessage); lstStep.add(step);
        step    = new Step("Étape 2", "Succès", lstMessage); lstStep.add(step);
        step    = new Step("Étape 3", "Succès", lstMessage); lstStep.add(step);
        axis    = new Axis(1, "Référentiel", lstStep);
        lstAxis.add(axis);

        // Axis 2.
        lstStep    = new ArrayList<Step>();
        lstMessage = new ArrayList<Message>();

        message = new Message("", "", "OK.");                lstMessage.add(message);
        step    = new Step("Étape 1", "Succès", lstMessage); lstStep.add(step);
        step    = new Step("Étape 2", "Succès", lstMessage); lstStep.add(step);
        step    = new Step("Étape 3", "Succès", lstMessage); lstStep.add(step);
        lstMessage = new ArrayList<Message>();
        message = new Message("ERR-001", "Erreur 001.", "La table XXX, ROWID=YYYY, colonne ABC est NULL."); lstMessage.add(message);
        message = new Message("ERR-222", "Erreur 222.", "La table XXX, ROWID=YYYY, colonne ABC est NULL."); lstMessage.add(message);
        message = new Message("ERR-333", "Erreur 333.", "La table XXX, ROWID=YYYY, colonne ABC est NULL."); lstMessage.add(message);
        message = new Message("ERR-123", "Erreur 123.", "La table XXX, ROWID=YYYY, colonne ABC est NULL."); lstMessage.add(message);
        message = new Message("ERR-999", "Erreur 999.", "La table XXX, ROWID=YYYY, colonne ABC est NULL."); lstMessage.add(message);
        step    = new Step("Étape 4", "Succès", lstMessage); lstStep.add(step);
        lstMessage = new ArrayList<Message>();
        message = new Message("", "", "OK.");                lstMessage.add(message);
        step    = new Step("Étape 5", "Succès", lstMessage); lstStep.add(step);
        axis    = new Axis(2, "Comptable",   lstStep);
        lstAxis.add(axis);

        // Axis 3.
        lstStep    = new ArrayList<Step>();
        lstMessage = new ArrayList<Message>();

        message = new Message("", "", "OK.");                lstMessage.add(message);
        step    = new Step("Étape 1", "Succès", lstMessage); lstStep.add(step);
        step    = new Step("Étape 2", "Succès", lstMessage); lstStep.add(step);
        axis    = new Axis(3, "Historique",  lstStep);
        lstAxis.add(axis);

        // Axis 4.
        lstStep    = new ArrayList<Step>();
        lstMessage = new ArrayList<Message>();

        message = new Message("DIV/0", "Divide by 0", "StackTrace ... AoooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooA"); lstMessage.add(message);
        step    = new Step("Étape 1", "Fail", lstMessage); lstStep.add(step);
        axis    = new Axis(4, "Valeur", lstStep);
        lstAxis.add(axis);

        return lstAxis;
    }

    /**
     * JasperSoft peut uutiliser une chaîne Json pour produire le rapport.
     * Cependant, Il faut que la chaîne JSON ressemble à un ResultSet.
     * 
     * @return JSonString qui reprrésente le rapport.
     */
    public static String buildJSonStringReport()
    {
        String JSonStringReport  = null;
        Collection<Axis> colAxis = createCollection();

        Report report = new Report(1, "Le magnifique rapport", new Date());
        ReportDetail reportDetail;
        for (Axis axis : colAxis)
        {
            List<Step> lstStep = axis.getLstStep();
            for (Step step : lstStep)
            {
                List<Message> lstMessage = step.getLstMessage();
                for (Message message : lstMessage)
                {
                    reportDetail = new ReportDetail(axis.getId(), axis.getAxis(), step.getStep(), step.getState(),
                                                    message.getExceptionCode(), message.getExceptionDescription(),
                                                    message.getExceptionDetail());
                    report.add(reportDetail);
                }
            }
        }

        // Création JSON.
        ObjectMapper objectMapper = new ObjectMapper();
        try
        {
            JSonStringReport = objectMapper.writeValueAsString(report);
        } 
        catch (JsonProcessingException ex)
        {
            ex.printStackTrace();
        }
        return JSonStringReport;
    }
}