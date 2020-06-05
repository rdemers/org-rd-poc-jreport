package org.rd.poc.jreport.util;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

public class JReportTools
{
    private static final Logger logger = LoggerFactory.getLogger(JReportTools.class);
    private JReportTools() { } // Usage statique seulement.

    static public JasperReport compileReport(String reportSource, boolean bFile) throws JRException, URISyntaxException
    {
        Assert.notNull(reportSource, "JReportTools::compileReport - reportSource is NULL.");
        InputStream reportStream = reportSource.getClass().getResourceAsStream(reportSource);
        Assert.notNull(reportStream, "JReportTools::compileReport - reportStream is NULL.");

        // Déterminer la source et la cible.
        URL resSource = reportSource.getClass().getResource(reportSource);
        File file = Paths.get(resSource.toURI()).toFile();
        String jasperReportSource = file.getAbsolutePath();
        String jasperReportTarget = jasperReportSource.replace(".jrxml", ".jasper");
        logger.info("Compilation du rapport Jasper de la source {} vers la cible {}.", 
                    jasperReportSource,jasperReportTarget);

        // Compilation du rapport en ".jasper".
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        if (bFile) // La source doit être sur le PATH. Mais pas dans un JAR (responsabilité du développeur) ;-)
            JRSaver.saveObject(jasperReport, jasperReportTarget);

        return jasperReport;
    }

    public static void exportToPdf(JasperPrint jasperPrint, String pdfTarget) throws JRException
    {
        Assert.notNull(jasperPrint, "JReportTools::exportToPdf - jasperPrint is NULL.");
        Assert.notNull(pdfTarget, "JReportTools::exportToPdf - target is NULL.");

        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfTarget));

        SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
        reportConfig.setSizePageToContent(true);
        reportConfig.setForceLineBreakPolicy(false);

        SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
        exportConfig.setMetadataAuthor("Loto-Québec. Tous droits réservés.");
        exportConfig.setEncrypted(true);
        exportConfig.setAllowedPermissionsHint("PRINTING");

        exporter.setConfiguration(reportConfig);
        exporter.setConfiguration(exportConfig);

        exporter.exportReport();
    }

    public static void exportToXlsx(JasperPrint jasperPrint, String xlsTarget, String xlsSheetName) throws JRException
    {
        Assert.notNull(jasperPrint, "JReportTools::exportToXlsx - jasperPrint is NULL.");
        Assert.notNull(xlsTarget, "JReportTools::exportToXlsx - xlsTarget is NULL.");
        Assert.notNull(xlsSheetName, "JReportTools::exportToXlsx - xlsSheetName is NULL.");

        JRXlsxExporter exporter = new JRXlsxExporter();

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(xlsTarget));

        SimpleXlsxReportConfiguration reportConfig = new SimpleXlsxReportConfiguration();
        reportConfig.setSheetNames(new String[] { xlsSheetName });

        exporter.setConfiguration(reportConfig);
        exporter.exportReport();
    }
    
    public static void exportToCsv(JasperPrint jasperPrint, String csvTarget) throws JRException
    {
        Assert.notNull(jasperPrint, "JReportTools::exportToCsv - jasperPrint is NULL.");
        Assert.notNull(csvTarget, "JReportTools::exportToCsv - csvTarget is NULL.");

        JRCsvExporter exporter = new JRCsvExporter();

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleWriterExporterOutput(csvTarget));

        exporter.exportReport();
     }

    public static void exportToHtml(JasperPrint jasperPrint, String htmlTarget) throws JRException
    {
        Assert.notNull(jasperPrint, "JReportTools::exportToCsv - jasperPrint is NULL.");
        Assert.notNull(htmlTarget, "JReportTools::exportToCsv - htmlTarget is NULL.");

        HtmlExporter exporter = new HtmlExporter();

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleHtmlExporterOutput(htmlTarget));

        exporter.exportReport();
    }
}