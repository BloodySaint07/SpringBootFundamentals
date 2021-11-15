package com.example.SpringMongoDB.Driver.pkg.GeneratePDF;

import com.example.SpringMongoDB.Driver.pkg.Constants.IErrorConstants;
import com.example.SpringMongoDB.Driver.pkg.model.CustomException;
import com.example.SpringMongoDB.Driver.pkg.model.Order;
import com.example.SpringMongoDB.Driver.pkg.service.ICustomExceptionService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class PDFGenerator {

    /** Logger */
    static Logger LOGGER = LogManager.getLogger(PDFGenerator.class);
    @Autowired
    private static ICustomExceptionService customExceptionService;


    public static ByteArrayInputStream userPDFReport(List<Order> orders) {

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();


        try {

            PdfWriter.getInstance(document, out);
            document.addAuthor("Soumyajit Mandal");
            document.addCreationDate();
            document.addSubject("Generating PDF from Spring Boot Application");
            document.open();

            // Add Text to PDF file ->
            Font font = FontFactory.getFont(FontFactory.COURIER_BOLD, 18, BaseColor.BLACK);
            Paragraph para = new Paragraph("Soumyajit's Order History", font);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);

            File logo = new File("D:\\Eclipse_Workspace\\SpringMySQLDB\\src\\main\\resources\\img\\logo.png");
            if (!logo.exists()) {
                LOGGER.warn("File " + logo.getName() + " not exists");
            }

            //Image img = Image.getInstance(ClassLoader.getSystemResource("logo.png"));
            Image img = Image.getInstance("D:\\Eclipse_Workspace\\SpringMySQLDB\\src\\main\\resources\\img\\logo.png");
            img.scaleToFit(PageSize.A4.getWidth() / 4, PageSize.A4.getHeight() / 4);
            float x = (PageSize.A4.getWidth() - img.getScaledWidth()) / 16;
            float y = (PageSize.A4.getHeight() - img.getScaledHeight()) / 16;
            img.setAbsolutePosition(x, y);
            document.add(img);

            PdfPTable table = new PdfPTable(3);
            // Add PDF Table Header ->
            Stream.of("Order Id", "Order ItemName", "Order Time").forEach(headerTitle -> {
                PdfPCell header = new PdfPCell();
                Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                header.setBackgroundColor(BaseColor.ORANGE);
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setBorderWidth(2);
                header.setPaddingBottom(4);
                header.setPhrase(new Phrase(headerTitle, headFont));
                table.addCell(header);
            });

            for (Order order : orders) {
                PdfPCell idCell = new PdfPCell(new Phrase(order.getId().toString()));
                idCell.setPaddingLeft(4);
                idCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                idCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                idCell.setPaddingBottom(4);
                table.addCell(idCell);

                PdfPCell name = new PdfPCell(new Phrase(order.getOrderName()));
                name.setPaddingLeft(5);
                name.setPaddingRight(5);
                name.setVerticalAlignment(Element.ALIGN_MIDDLE);
                name.setHorizontalAlignment(Element.ALIGN_MIDDLE);
                name.setPaddingBottom(4);
                table.addCell(name);


                PdfPCell orderTime = new PdfPCell(new Phrase(order.getOrderTime()));
                orderTime.setVerticalAlignment(Element.ALIGN_CENTER);
                orderTime.setHorizontalAlignment(Element.ALIGN_CENTER);
                orderTime.setPaddingRight(4);
                orderTime.setPaddingLeft(4);
                orderTime.setPaddingBottom(4);
                table.addCell(orderTime);
            }
            document.add(table);

            Paragraph con = new Paragraph("Call : +91-8617292962 to Place Order");
            document.add(new Paragraph("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"));
            con.setAlignment(Element.ALIGN_CENTER);
            document.add(con);
            document.close();
        } catch (DocumentException | IOException e) {

            LOGGER.info("*** FAILED TO GENERATE PDF *** ");
            CustomException customException = new CustomException(IErrorConstants.FAILEDPDFGEN + " " + e.getMessage().toString().trim());
            customExceptionService.saveException(customException);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }


    }

