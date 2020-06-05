package org.rd.poc.jreport.config;

import java.io.ByteArrayInputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rd.poc.jreport.dao.DaoData;
import org.rd.poc.jreport.data.Axis;
import org.rd.poc.jreport.util.JReportTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JsonDataSource;

@SpringBootApplication
public class Application implements CommandLineRunner
{
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception
    {
        logger.info("Début de l'exécution");

        // Jackson peut sérialiser/désérialiser la plupart de vos classes sans nécesiter de configuration
        // particulière. Nous construisons une série d'objet POJO/JAVA afin de produire une chaîne JSON.
        // Cette chaîne est par la suite utiliée pour reconstruire l'objet.
        ObjectMapper objectMapper = new ObjectMapper();

        // POJO->JSON
        Collection<Axis> colAxis = DaoData.createCollection();
        String strJSON = objectMapper.writeValueAsString(colAxis);
        //objectMapper.writeValue(new File("./Report.json"), colAxis); // Alternative.
        logger.info(strJSON); // Produire une chaîne JSON.

        // JSON->POJO
        List<Axis> lstAxis = objectMapper.readValue(strJSON, new TypeReference<List<Axis>>(){});
        Assert.notNull(lstAxis, "lstAxis == NULL");
        Assert.isTrue(lstAxis.size() == 4, "Nombre d'élément invalide.");
        logger.info("L'objet lstAxis est valide" + lstAxis.toString()); // Produire des Objects à partir de JSon.

        // Produire les données d'un rapport à partir des objets.
        String reportJSON  = DaoData.buildJSonStringReport();
        logger.info(reportJSON); // Notre rapport en format JSON.

        // JASPER ...

        // Un rapport (.JRXML) est construit en utilisant l'interface GUI. Lors de la création du rapport. Vous devez
        // spécifier un DATASOURCE. Pour les besoins, nous avons utilisé un fichier JSON qui contient le rapport
        // produit avec les instructions précédentes. VOIR src/main/resources ...
        
        // Un rapport (.JRXML) doit être compilé par JASPER afin de l'utiliser pour produire un rapport.
        // Ce processus est long et peut être effectué lors de la compilation. Nous avons inclus un plugin qui 
        // effectue ce travail. Voir : POM.XML. Pour les besoins de la démonstration, nous effectuons une compilation
        // à la main. Cette compilation est normalement conservé avec le même nom que la source. Cependant, l'extension
        // va être (.JASPER).
        JasperReport jasperReport = JReportTools.compileReport("/jasper/Report.jrxml", true);
        Assert.notNull(jasperReport, "jasperReport == NULL");

        // Production du rapport
        // =============================================================================================================
        // La production du rapport consiste à utiliser la version compilé (.JASPER) avec une source de données et des
        // paramètres (optionnel). Cette production va produire un rapport en format (.JRPRINT). Ce dernier est utilisé
        // afin de produire l'extrant final : pdf, xls, html ou csv.

        // Source de données. JRDatasource. Voir la documentation. PDF ci-joint; répertoire doc.
        // Notre source de données JSON possède plusieurs branches. Nous spécifions la branche à utiliser.
        JsonDataSource datasource = new JsonDataSource(new ByteArrayInputStream(reportJSON.getBytes("UTF-8")),"reportDetail");

        // Nos paramètres. Le titre seulement pour démonstration.
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", "Le super beau rapport avec des paramètres, sections, totaux et variables");

        // Production du rapport.
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, datasource);

        // Notre rapport est terminé. 
        // Il existe différente façon de l'exporter.
        final String target = "C:\\lq-sdk\\var\\github\\org-rd-poc-jreport\\src\\main\\resources\\jasper\\Report";
        JReportTools.exportToPdf(jasperPrint, target + ".pdf");
        JReportTools.exportToXlsx(jasperPrint,target + ".xlsx", "Rapport");
        JReportTools.exportToCsv(jasperPrint,target + ".csv");
        JReportTools.exportToHtml(jasperPrint,target + ".html");
 
        logger.info("Fin de l'exécution");
    }
}