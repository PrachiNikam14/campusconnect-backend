package campusconnect.backend.utils;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InvoiceGenerator {

    public static byte[] generateInvoice(
            String studentName,
            String eventName,
            String collegeName,
            double amount,
            String transactionId
    ) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();

            Font titleFont = new Font(Font.HELVETICA, 22, Font.BOLD);
            Font headingFont = new Font(Font.HELVETICA, 14, Font.BOLD);
            Font textFont = new Font(Font.HELVETICA, 12);

            Paragraph title = new Paragraph("CampusConnect Invoice", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph(" "));
            document.add(new Paragraph("Date: " +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy"))));

            document.add(new Paragraph("Invoice ID: INV-" + System.currentTimeMillis()));

            document.add(new Paragraph(" "));

            document.add(new Paragraph("Student Details", headingFont));
            document.add(new Paragraph("Name: " + studentName, textFont));

            document.add(new Paragraph(" "));

            document.add(new Paragraph("Event Details", headingFont));
            document.add(new Paragraph("Event: " + eventName, textFont));
            document.add(new Paragraph("College: " + collegeName, textFont));

            document.add(new Paragraph(" "));

            document.add(new Paragraph("Payment Details", headingFont));
            document.add(new Paragraph("Amount Paid: ₹" + amount, textFont));
            document.add(new Paragraph("Transaction ID: " + transactionId, textFont));

            document.add(new Paragraph(" "));

            Paragraph thanks = new Paragraph(
                    "Thank you for using CampusConnect.",
                    new Font(Font.HELVETICA, 12, Font.ITALIC)
            );

            thanks.setAlignment(Element.ALIGN_CENTER);

            document.add(thanks);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }
}