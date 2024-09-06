package controller;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

public class GeraRelatorio {

    public GeraRelatorio() {
        Connection connection = null;
        
        File file = new File("GeraRelatorio.java");
        String pathAbsoluto = file.getAbsolutePath();
        //C:\Users\renat\Documents\workspace-spring-tool-suite-4-4.24.0.RELEASE\Login\src\controller\GeraRelatorio.java
        String pathAbsolutoParcial = pathAbsoluto.substring(0,pathAbsoluto.lastIndexOf('\\')) + "\\relatorio\\Coffee.jrxml";
        
        try {
            // Conectar ao banco de dados
            String dbUrl = "jdbc:sqlite:C:/Users/renat/Documents/workspace-spring-tool-suite-4-4.24.0.RELEASE/Login/resources/bdclientes.db";
            connection = DriverManager.getConnection(dbUrl);

            // Caminho para o arquivo .jrxml
            String jrxmlFilePath = "C:\\Users\\renat\\Documents\\workspace-spring-tool-suite-4-4.24.0.RELEASE\\Login\\relatorio\\Coffee.jrxml";

            // Compilar o relatório .jrxml
            JasperReport jasperReport = JasperCompileManager.compileReport(pathAbsolutoParcial);

            // Preencher o relatório com dados
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), connection);

            // Visualizar o relatório
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);

        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
