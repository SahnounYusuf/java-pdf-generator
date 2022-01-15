/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdfgenerator;

import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.*;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BarcodeDatamatrix;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author trator979
 */
public class PDFgenerator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, DocumentException, ClassNotFoundException, IOException {
        // TODO code application logic here

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            String dbURL = "jdbc:oracle:thin:@SERV-MSI01:1521:DBMSIC";
            String username = "GPCOELEC";
            String password = "OCTAL";
            Connection db = DriverManager.getConnection(dbURL, username, password);

            Document pdfDocument = new Document();
            java.io.File pdfFile = new java.io.File("C:/hallo.pdf");
            com.itextpdf.text.pdf.PdfWriter.getInstance(pdfDocument, new java.io.FileOutputStream(pdfFile));
            PdfWriter writer = PdfWriter.getInstance(pdfDocument, new FileOutputStream("C:/hallo.pdf"));
            pdfDocument.open();

            Statement stmt = db.createStatement();
            ResultSet rs = stmt.executeQuery("select BLCL_CLNT_CODE, CLNT_NOM, lblc_blcl_numero,  to_char(BLCL_DT_SAISIE,'DD/MM/YYYY') data_ddt,  lblc_ligne, lblc_cdcl_numero, "
                    + "lblc_lccl_ligne,LBLC_QTE_A_LIVRER_UV,\n"
                    + "LBLC_ARTI_CODE_LIVRE, LBLC_DESIGNATION  ,\n"
                    + "lblc_blcl_numero||'%'||BLCL_DT_SAISIE||'%'||lblc_ligne||'%'||lblc_cdcl_numero||'%'||lblc_lccl_ligne||'%'||LBLC_QTE_A_LIVRER_UV||'%'||LBLC_ARTI_CODE_LIVRE DATAMATRIX\n"
                    + "  from ligne_bl_client, bl_client, client\n"
                    + "  where lblc_blcl_numero = blcl_numero\n"
                    + "  and BLCL_CLNT_CODE = clnt_code\n"
                    + "  and blcl_numero = 'BL2000023'");

            while (rs.next()) {
                {
                    String BLCL_CLNT_CODE = rs.getString(1);
                    String CLNT_NOM = rs.getString(2);
                    String lblc_blcl_numero = rs.getString(3);
                    String BLCL_DT_SAISIE = rs.getString(4);
                    String lblc_ligne = rs.getString(5);
                    String lblc_cdcl_numero = rs.getString(6);
                    String lblc_lccl_ligne = rs.getString(7);
                    String LBLC_QTE_A_LIVRER_UV = rs.getString(8);
                    String LBLC_ARTI_CODE_LIVRE = rs.getString(9);
                    String LBLC_DESIGNATION = rs.getString(10);
                    String DATAMATRIX = rs.getString(11);

                    System.out.println(LBLC_DESIGNATION);

                    pdfDocument.newPage();
                    PdfPTable qrCodeTable = new PdfPTable(3);
                    PdfPTable ndTab = new PdfPTable(6);
                    PdfPTable rdTab = new PdfPTable(5);
                    BaseFont bf = BaseFont.createFont(
                            BaseFont.TIMES_ROMAN,
                            BaseFont.CP1252,
                            BaseFont.EMBEDDED);
                    Font font = new Font(bf, 6);

//                  
//                    Barcode128 code128 = new Barcode128();
//                    code128.setFont(null);
//                    code128.setCode(BLCL_CLNT_CODE);
//                    code128.setCodeType(Barcode128.CODE128);
//                    PdfContentByte cb = writer.getDirectContent();
//                    com.itextpdf.text.Image code128Image = code128.createImageWithBarcode(cb, null, null);
//                    PdfPCell cell = new PdfPCell();
//                    cell.addElement(new Phrase("PO #: " + BLCL_CLNT_CODE));
//                    cell.addElement(code128Image);
//                    qrCodeTable.addCell(cell);
                    PdfPCell cellLogo = new PdfPCell();
                    cellLogo.addElement(new Phrase("LOGO HERE",font));
                    cellLogo.setBackgroundColor(BaseColor.WHITE);
                    cellLogo.setBorder(Rectangle.NO_BORDER);
                    qrCodeTable.addCell(cellLogo);

//                    Barcode128 code1281 = new Barcode128();
//                    code1281.setFont(null);
//                    code1281.setCode(CLNT_NOM);
//                    code1281.setCodeType(Barcode128.CODE128);
//                    PdfContentByte cb1 = writer.getDirectContent();
//                    com.itextpdf.text.Image code128Image1 = code1281.createImageWithBarcode(cb1, null, null);
//                    PdfPCell cell1 = new PdfPCell();
//                    cell1.addElement(new Phrase("PO #: " + CLNT_NOM));
//                    cell1.addElement(code128Image1);
//                    qrCodeTable.addCell(cell1);
                    Barcode128 code1283 = new Barcode128();
                    code1283.setFont(null);
                    code1283.setBaseline(-1);
                    code1283.setSize(12);
                    code1283.setCode("*"+LBLC_ARTI_CODE_LIVRE+"*");
                    code1283.setCodeType(Barcode128.CODE128);

                    PdfContentByte cb3 = writer.getDirectContent();
                    com.itextpdf.text.Image code128Image3 = code1283.createImageWithBarcode(cb3, null, null);
                    PdfPCell cell3 = new PdfPCell();
                    cell3.addElement(new Phrase(LBLC_ARTI_CODE_LIVRE+"\n",font));
                    cell3.addElement(code128Image3);
                    cell3.setBorder(Rectangle.NO_BORDER);
                    cell3.setPadding(5.0f);
                    cell3.setFixedHeight(40f);
                    qrCodeTable.addCell(cell3);

//                    Barcode128 code1282 = new Barcode128();
//                    code1282.setFont(null);
//                    code1282.setCode(lblc_blcl_numero);
//                    code1282.setCodeType(Barcode128.CODE128);
//                    PdfContentByte cb2 = writer.getDirectContent();
//                    com.itextpdf.text.Image code128Image2 = code1282.createImageWithBarcode(cb2, null, null);
//                    PdfPCell cell2 = new PdfPCell();
//                    cell2.addElement(new Phrase("PO #: " + lblc_blcl_numero));
//                    cell2.addElement(code128Image2);
//                    qrCodeTable.addCell(cell2);
                    PdfPCell cellFige = new PdfPCell();
                    cellFige.addElement(new Phrase("FIGé HERE",font));
                    cellFige.setBorder(Rectangle.NO_BORDER);
                    qrCodeTable.addCell(cellFige);

//                    Barcode128 code1284 = new Barcode128();
//                    code1284.setFont(null);
//                    code1284.setCode(lblc_ligne);
//                    code1284.setCodeType(Barcode128.CODE128);
//                    PdfContentByte cb4 = writer.getDirectContent();
//                    com.itextpdf.text.Image code128Image4 = code1284.createImageWithBarcode(cb4, null, null);
//                    PdfPCell cell4 = new PdfPCell();
//                    cell4.addElement(new Phrase("PO #: " + lblc_ligne));
//                    cell4.addElement(code128Image4);
//                    qrCodeTable.addCell(cell4);
//                    Barcode128 code1285 = new Barcode128();
//                    code1285.setFont(null);
//                    code1285.setCode(lblc_cdcl_numero);
//                    code1285.setCodeType(Barcode128.CODE128);
//                    PdfContentByte cb5 = writer.getDirectContent();
//                    com.itextpdf.text.Image code128Image5 = code1281.createImageWithBarcode(cb5, null, null);
//                    PdfPCell cell5 = new PdfPCell();
//                    cell5.addElement(new Phrase("PO #: " + lblc_cdcl_numero));
//                    cell5.addElement(code128Image5);
//                    qrCodeTable.addCell(cell5);
                    PdfPCell cellHar = new PdfPCell();
                    cellHar.addElement(new Phrase(LBLC_DESIGNATION,font));
                    cellHar.setBorder(Rectangle.NO_BORDER);
                    qrCodeTable.addCell(cellHar);

                    PdfPCell cellEmpty = new PdfPCell();
                    cellEmpty.addElement(new Phrase("        "));
                    cellEmpty.setBorder(Rectangle.NO_BORDER);
                    qrCodeTable.addCell(cellEmpty);

                    PdfPCell cellTab = new PdfPCell();
                    cellTab.addElement(new Phrase("Revisine:\nDATA REV:\nQTA'BOX:      "+LBLC_QTE_A_LIVRER_UV,font));
                    cellTab.setPadding(5.0f);
                    cellTab.setFixedHeight(40f);
                    qrCodeTable.addCell(cellTab);

//=============================================END OF TABLE 1======================================================
                    PdfPCell cellFornitore = new PdfPCell();
                    cellFornitore.addElement(new Phrase("FORNITORE:\n BarCode HERE",font));
                    cellFornitore.setBorder(Rectangle.NO_BORDER);
                    ndTab.addCell(cellFornitore);

                    PdfPCell cellOrdine = new PdfPCell();
                    cellOrdine.addElement(new Phrase("N. ORDINE:\n BarCode HERE",font));
                    cellOrdine.setBorder(Rectangle.NO_BORDER);
                    ndTab.addCell(cellOrdine);

                    PdfPCell cellQtaSped = new PdfPCell();
                    cellQtaSped.addElement(new Phrase("FORNITORE:\n BarCode HERE",font));
                    cellQtaSped.setBorder(Rectangle.NO_BORDER);
                    ndTab.addCell(cellQtaSped);

                    PdfPCell cellLotto = new PdfPCell();
                    cellLotto.addElement(new Phrase("N LOTTO:\n BarCode HERE",font));
                    cellLotto.setBorder(Rectangle.NO_BORDER);
                    ndTab.addCell(cellLotto);

                    PdfPCell cellQCK = new PdfPCell();
                    cellQCK.addElement(new Phrase("QCK ID:",font));
                    cellQCK.setPadding(5.0f);
                    cellQCK.setFixedHeight(40f);
                    ndTab.addCell(cellQCK);

                    PdfPCell cellVCK = new PdfPCell();
                    cellVCK.addElement(new Phrase("VCK ID:",font));
                    cellVCK.setPadding(5.0f);
                    cellVCK.setFixedHeight(40f);
                    ndTab.addCell(cellVCK);

//=============================================END OF 2ND TABLE====================================================
                    Barcode128 code1285 = new Barcode128();
                    code1285.setFont(null);
                    code1285.setBaseline(-1);
                    code1285.setSize(12);
                    code1285.setCode(BLCL_DT_SAISIE);
                    code1285.setCodeType(Barcode128.CODE128);
                    PdfContentByte cb5 = writer.getDirectContent();
                    com.itextpdf.text.Image code128Image5 = code1285.createImageWithBarcode(cb5, null, null);
                    PdfPCell cell5 = new PdfPCell();
                    cell5.addElement(new Phrase("DATA DDT: " + BLCL_DT_SAISIE+"\n",font));
                    cell5.addElement(code128Image5);
                    cell5.setBorder(Rectangle.NO_BORDER);
                    cell5.setPadding(5.0f);
                    cell5.setFixedHeight(40f);
                    rdTab.addCell(cell5);

                    Barcode128 CodeNum = new Barcode128();
                    CodeNum.setFont(null);
                    CodeNum.setCode(lblc_blcl_numero);
                    CodeNum.setCodeType(Barcode128.CODE128);
                    PdfContentByte cbNum = writer.getDirectContent();
                    com.itextpdf.text.Image code128ImageNum = CodeNum.createImageWithBarcode(cbNum, null, null);
                    PdfPCell cellNum = new PdfPCell();
                    cellNum.addElement(new Phrase("DDT. N: " + lblc_blcl_numero+"\n",font));
                    cellNum.addElement(code128ImageNum);
                    cellNum.setBorder(Rectangle.NO_BORDER);
                    cellNum.setPadding(5.0f);
                    cellNum.setFixedHeight(30f);
                    rdTab.addCell(cellNum);

                    PdfPCell cellQta = new PdfPCell();
                    cellQta.addElement(new Phrase("N.BOX: \nCode HERE",font));
                    cellQta.setBorder(Rectangle.NO_BORDER);
                    cellQta.setPadding(10f);
//                    cellQta.setPadding(5.0f);
                    cellQta.setFixedHeight(40f);
                    rdTab.addCell(cellQta);

                    PdfPCell cellNote = new PdfPCell();
                    cellNote.addElement(new Phrase("NOTE: ",font));
                    cellNote.setPadding(5.0f);
                    cellNote.setFixedHeight(35f);
                    rdTab.addCell(cellNote);

                    BarcodeDatamatrix dm = new BarcodeDatamatrix();
                    dm.generate(DATAMATRIX);
                    Image img = dm.createImage();

                    PdfPCell cellMat = new PdfPCell(img, true);
                    cellMat.setFixedHeight(50f);
                    cellMat.setPadding(1f);
                    cellMat.setBorder(Rectangle.NO_BORDER);
                    cellMat.setHorizontalAlignment(Element.ALIGN_CENTER);
                    rdTab.addCell(cellMat);

//=======================================END OF 3RD TABLE=====================================================================
                    pdfDocument.add(qrCodeTable);
                    pdfDocument.add(new Phrase("\n----------------------------------"
                            + "-----------------------------------------------------"
                            + "-----------------------------------------------------"
                            + "-----------------------------------------------------"
                            + "------------------------------------------------",font));
                    pdfDocument.add(ndTab);
                    pdfDocument.add(rdTab);

                }
            }

            pdfDocument.close();

        } catch (SQLException e) {
            System.err.println(e);
        }

    }

}
