package com.licence.services;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.licence.dto.LicenceData;
import com.licence.models.LicenceIdentity;
import com.licence.models.Module;

public class PdfService {
    
    public static ByteArrayOutputStream getByteArrayOutputStreamPdf(LicenceData licenceData){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdf = new PdfDocument(pdfWriter);
        Document document = new Document(pdf);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Couleur pour l'état actif et inactif
        Color green = new DeviceRgb(0, 255, 0);
        Color red = new DeviceRgb(255, 0, 0);

        document.add(new Paragraph("Licence details")
            .setBold()
            .setFontSize(18)
            .setMarginBottom(20));

        // Section "Date de début, Date de fin et Nombre d'activation"
        document.add(new Paragraph("Date début de validité : "+simpleDateFormat.format(licenceData.getLicence().getStartDate())));
        document.add(new Paragraph("Date fin de validité : "+simpleDateFormat.format(licenceData.getLicence().getEndDate())));
        document.add(new Paragraph("Nombre d'activation : "+licenceData.getLicence().getNumberActivation()));
        document.add(new Paragraph("\n")); // Saut de ligne

        // Section "Details Client"
        document.add(new Paragraph("Details Client")
                .setBold()
                .setFontSize(14)
                .setMarginBottom(10));
        String nomComplet = licenceData.getLicence().getUsers().getFirstname()+" "+licenceData.getLicence().getUsers().getSurname();
        document.add(new Paragraph("Nom : "+nomComplet));
        document.add(new Paragraph("Email : "+licenceData.getLicence().getUsers().getEmail()));
        document.add(new Paragraph("Numéro téléphone : "+licenceData.getLicence().getUsers().getContact()));
        document.add(new Paragraph("\n")); // Saut de ligne

        // Section "Identifiants unique licence"
        document.add(new Paragraph("Identifiants unique licence")
                .setBold()
                .setFontSize(14)
                .setMarginBottom(10));

        // Tableau pour Identifiants
        float[] columnWidths = {100f, 120f, 100f};
        Table table = new Table(columnWidths);

        // Ajouter l'en-tête
        table.addHeaderCell(new Cell().add(new Paragraph("Identifiant")));
        table.addHeaderCell(new Cell().add(new Paragraph("Mode d'activation")));
        // table.addHeaderCell(new Cell().add(new Paragraph("Date d'activation")));
        table.addHeaderCell(new Cell().add(new Paragraph("État")));

        List<LicenceIdentity> licenceIdentities = licenceData.getLicenceIdentities();
        for (LicenceIdentity licenceIdentity : licenceIdentities) {
            String mode_activation = "";
            if (licenceIdentity.getModeActivation() != 0) {
                if (licenceIdentity.getModeActivation() ==1) {
                    mode_activation = "Manuel";
                }
                else{
                    mode_activation ="Automatique";
                }
            }
            table.addCell(new Cell().add(new Paragraph(licenceIdentity.getIdPc())));
            table.addCell(new Cell().add(new Paragraph(mode_activation)));
            // table.addCell(new Cell().add(new Paragraph("12/03/2024")));
            if (licenceIdentity.getState() != 0) {
                table.addCell(new Cell().add(new Paragraph("Actif").setFontColor(green)));
            }
            else{
                table.addCell(new Cell().add(new Paragraph("Inactif").setFontColor(red)));
            }
        }

        document.add(table);
        document.add(new Paragraph("\n")); // Saut de ligne

        // Section "Software"
        document.add(new Paragraph("Software")
                .setBold()
                .setFontSize(14)
                .setMarginBottom(10));

        // Tableau pour Software
        float[] softwareColumnWidths = {200f, 150f};
        Table softwareTable = new Table(softwareColumnWidths);
        softwareTable.addHeaderCell(new Cell().add(new Paragraph("Name")));
        softwareTable.addHeaderCell(new Cell().add(new Paragraph("Date d'activation")));

        softwareTable.addCell(new Cell().add(new Paragraph(licenceData.getLicence().getSoftware().getName())));
        softwareTable.addCell(new Cell().add(new Paragraph(simpleDateFormat.format(licenceData.getLicence().getSoftware().getDateCreation()))));

        document.add(softwareTable);
        document.add(new Paragraph("\n")); // Saut de ligne

        // Section "List of module"
        document.add(new Paragraph("List of module")
                .setBold()
                .setFontSize(14)
                .setMarginBottom(10));

        // Tableau pour les modules
        Table moduleTable = new Table(softwareColumnWidths);
        moduleTable.addHeaderCell(new Cell().add(new Paragraph("Name")));
        moduleTable.addHeaderCell(new Cell().add(new Paragraph("Created Date")));

        List<Module> modules = licenceData.getModules();
        for (Module module : modules) {
            moduleTable.addCell(new Cell().add(new Paragraph(module.getName())));
            moduleTable.addCell(new Cell().add(new Paragraph(simpleDateFormat.format(licenceData.getLicence().getSoftware().getDateCreation()))));
        }

        document.add(moduleTable);

        // Fermer le document
        document.close();
        System.out.println("PDF Crée");
        return byteArrayOutputStream;
    }

}
