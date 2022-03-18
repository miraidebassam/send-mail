/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sendreporting;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sendreporting.script.ChatBotFile;
import org.apache.poi.xssf.usermodel.*;
import sendreporting.config.Email;

import sendreporting.entity.TelechargementEntity;
import sendreporting.entity.TransactionsEntity;

/**
 *
 * @author Caitson Canpintam <caitson.canpintam@orange-sonatel.com>
 */
public class SendReporting {

    static ChatBotFile cbfile = new ChatBotFile();
    int indexCount = 0;

    String separdor = System.getProperty("file.separator");
//    String excelFilePathList = "Report_ChatBot_v2.xlsx";
    String excelFilePathList = "Report_Barkafon_Telechargement_"+new SimpleDateFormat("yyyyMMdd").format(new Date())+".xlsx";
    String excelFilePathCount = "Report_NhaOrange_Transactions_"+new SimpleDateFormat("yyyyMMdd").format(new Date())+".xlsx";
    String raiz = new File("").getAbsolutePath().concat(separdor).concat("Orange").concat(separdor).concat("nha-orange").concat(separdor).concat("File_send");
    String raiz_buckup = new File("").getAbsolutePath().concat(separdor).concat("Orange").concat(separdor).concat("nha-orange").concat(separdor).concat("File_Buckup");

    Path diretorioPath = Paths.get(raiz);
    Path diretorio_backup = Paths.get(raiz_buckup);

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws IOException, SQLException {
        //final SendReporting sr = new SendReporting();
        try {
            //sr.exportFileList();
//            sr.exportTransaction();            
            //List<String> listEmails = cbfile.getAllEamils();
            Email.envoiEmailContrat();
            Email.envoiEmailDocument();

        } catch ( SQLException e) {
            System.out.println("Error in main : " + e);
        }

    }
    
    
    
    
    
    
    
    
    

    public void exportFileList() throws SQLException, IOException {
        Calendar c = Calendar.getInstance();
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        c.add(Calendar.DAY_OF_MONTH, -1);
        d = c.getTime();
        String data = sdf.format(d);
        String excelTelechargement = "Rapport_Barkafon_"+data+".xlsx";
        File f = new File(raiz.concat(separdor).concat(excelTelechargement));
        try {
            Files.createDirectories(diretorioPath);
            Files.createDirectories(diretorio_backup);
            if (f.exists() && !f.isDirectory()) {
                Path temp = Files.move(Paths.get(raiz.concat(separdor).concat(excelTelechargement)), Paths.get(raiz_buckup.concat(separdor).concat("Rapport_Barkafon_" + data + "HOLD.xlsx")));
                if (temp != null) {
                    System.out.println("Fichier (" + excelFilePathCount + ") renommé en (" + temp.getFileName() + ") et déplacé dans (" + temp + ") avec succès");
                } else {
                    System.out.println("Échec du déplacement du fichier");
                }
                
            } else {
                System.out.println("Aucun fichier trouvé");
            }
        } catch (IOException e) {
            throw new RuntimeException("Problèmes lors de creation du fichier. ", e);
        }

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Telechargement");
            //XSSFSheet sheet2 = workbook.createSheet("Transaction_Detail");

            writeHeaderListLine(sheet);
            //writeDataListLines(sheet);
            
           // writeHeaderTransactionLine(sheet2);
            //writeTransactionsLines(sheet2);

            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(raiz.concat(separdor).concat(excelFilePathList));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SendReporting.class.getName()).log(Level.SEVERE, null, ex);
            }
            workbook.write(outputStream);
        }
    }

    public void exportTransaction() throws SQLException, IOException {
        Calendar c = Calendar.getInstance();
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        c.add(Calendar.DAY_OF_MONTH, -1);
        d = c.getTime();
        String data = sdf.format(d);
        String excelTransaction = "Report_NhaOrange_Transactions_"+data+".xlsx";
        File f = new File(raiz.concat(separdor).concat(excelTransaction));
        try {
            Files.createDirectories(diretorioPath);
            Files.createDirectories(diretorio_backup);
            if (f.exists() && !f.isDirectory()) {
                Path temp = Files.move(Paths.get(raiz.concat(separdor).concat(excelTransaction)), Paths.get(raiz_buckup.concat(separdor).concat("Report_NhaOrange_Transactions_" + data + "HOLD.xlsx")));
                if (temp != null) {
                    System.out.println("Fichier (" + excelFilePathCount + ") renommé en (" + temp.getFileName() + ") et déplacé dans (" + temp + ") avec succès");
                } else {
                    System.out.println("Échec du déplacement du fichier");
                }
            } else {
                System.out.println("Aucun fichier trouvé");
            }
        } catch (IOException e) {
            throw new RuntimeException("Problèmes lors de creation du fichier. ", e);
        }

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Transaction_Detail");
            writeHeaderTransactionLine(sheet);
            writeTransactionsLines(sheet);
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(raiz.concat(separdor).concat(excelFilePathCount));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SendReporting.class.getName()).log(Level.SEVERE, null, ex);
            }
            workbook.write(outputStream);
        }
    }

    private void writeHeaderListLine(XSSFSheet sheet) {
        XSSFRow headerRow = sheet.createRow(0);
        XSSFCell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("telecharge_android");
        XSSFCell headerCel2 = headerRow.createCell(1);
        headerCel2.setCellValue("telechargement_ios");
        XSSFCell headerCel3 = headerRow.createCell(2);
        headerCel3.setCellValue("premiere_connexion");
        XSSFCell headerCel4 = headerRow.createCell(3);
        headerCel4.setCellValue("data_action");
    }

	/*
	 * private void writeDataListLines(XSSFSheet sheet) throws SQLException { int
	 * rowCount = 1; List<TelechargementEntity> histGroupByPhone =
	 * cbfile.listTelechargement(); indexCount = indexCount + 1; for
	 * (TelechargementEntity chatBotHisEntity : histGroupByPhone) { XSSFRow row =
	 * sheet.createRow(rowCount++); int columnCount = 0; XSSFCell cell =
	 * row.createCell(columnCount++);
	 * cell.setCellValue(chatBotHisEntity.getTelecharge_android()); cell =
	 * row.createCell(columnCount++);
	 * cell.setCellValue(chatBotHisEntity.getTelechargement_ios()); cell =
	 * row.createCell(columnCount++);
	 * cell.setCellValue(chatBotHisEntity.getPremiere_connexion()); cell =
	 * row.createCell(columnCount++);
	 * cell.setCellValue(chatBotHisEntity.getData_action()); indexCount = indexCount
	 * + 1; }
	 * 
	 * }
	 */
    private void writeHeaderTransactionLine(XSSFSheet sheet) {
        XSSFRow headerRow = sheet.createRow(0);
        XSSFCell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("date_action");
        XSSFCell headerCel2 = headerRow.createCell(1);
        headerCel2.setCellValue("service");
        XSSFCell headerCell3 = headerRow.createCell(2);
        headerCell3.setCellValue("description");
        XSSFCell headerCel4 = headerRow.createCell(3);
        headerCel4.setCellValue("nbre_transaction");
        XSSFCell headerCel5 = headerRow.createCell(4);
        headerCel5.setCellValue("montant");
    }

    private void writeTransactionsLines(XSSFSheet sheet) throws SQLException {
        int rowCount = 1;
        List<TransactionsEntity> histGroupByCount = cbfile.listTransactions();
        indexCount = indexCount + 1;
        for (TransactionsEntity transEntity : histGroupByCount) {
            XSSFRow row = sheet.createRow(rowCount++);
            int columnCount = 0;
            XSSFCell cell = row.createCell(columnCount++);
            cell.setCellValue(transEntity.getDate_action());
            cell = row.createCell(columnCount++);
            cell.setCellValue(transEntity.getService());
            cell = row.createCell(columnCount++);
            cell.setCellValue(transEntity.getDescription());
            cell = row.createCell(columnCount++);
            cell.setCellValue(transEntity.getNbre_transaction());
            cell = row.createCell(columnCount++);
            cell.setCellValue(transEntity.getMontant());
            indexCount = indexCount + 1;
        }

    }

}
