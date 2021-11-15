//package com.example.SpringMongoDB.Driver.pkg.GeneratePDF;
//
//import com.example.SpringMongoDB.Driver.pkg.model.User;
//import com.lowagie.text.Font;
//import com.lowagie.text.*;
//import com.lowagie.text.pdf.PdfPCell;
//import com.lowagie.text.pdf.PdfPTable;
//import com.lowagie.text.pdf.PdfWriter;
//
//import javax.servlet.http.HttpServletResponse;
//import java.awt.*;
//import java.io.IOException;
//import java.util.List;
//
//public class UserPDFExporter {
//
//    private List<User> listUsers;
//
//    public UserPDFExporter(List<User> listUsers) {
//        this.listUsers = listUsers;
//    }
//
//
//    private void writeTableHeader(PdfPTable table) {
//        PdfPCell cell = new PdfPCell();
//        cell.setBackgroundColor(Color.BLUE);
//        cell.setPadding(5);
//
//        Font font = FontFactory.getFont(FontFactory.HELVETICA);
//        font.setColor(Color.WHITE);
//
//        cell.setPhrase(new Phrase("User ID", font));
//
//        table.addCell(cell);
//
//        cell.setPhrase(new Phrase("User-Names", font));
//        table.addCell(cell);
//
////        cell.setPhrase(new Phrase("E-mail", font));
////        table.addCell(cell);
//
//        cell.setPhrase(new Phrase("Full Name", font));
//        table.addCell(cell);
//
//        cell.setPhrase(new Phrase("Country", font));
//        table.addCell(cell);
//
////        cell.setPhrase(new Phrase("Address", font));
////        table.addCell(cell);
//    }
//
//
//    private void writeTableData(PdfPTable table) {
//        for (User user : listUsers) {
//            table.addCell(String.valueOf(user.getId()));
//            table.addCell(user.getUsername());
//            table.addCell(user.getName());
//            table.addCell(user.getCountry());
//            // table.addCell(String.valueOf(user.isEnabled()));
//        }
//    }
//
//
//
//    public void export(HttpServletResponse response) throws DocumentException, IOException {
//        Document document = new Document(PageSize.A4);
//        PdfWriter.getInstance(document, response.getOutputStream());
//
//        document.open();
//        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
//        font.setSize(18);
//        font.setColor(Color.BLUE);
//
//        Paragraph p = new Paragraph("List of Users", font);
//        p.setAlignment(Paragraph.ALIGN_CENTER);
//
//        document.add(p);
//
//        PdfPTable table = new PdfPTable(5);
//        table.setWidthPercentage(100f);
//        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
//        table.setSpacingBefore(10);
//
//        writeTableHeader(table);
//        writeTableData(table);
//
//        document.add(table);
//
//        document.close();
//
//    }
//}
