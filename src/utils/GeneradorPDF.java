package utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import models.DetalleVenta;

public class GeneradorPDF {

    public static void crearFactura(List<DetalleVenta> listaVentas) {
        Document documento = new Document();
        try {
            String fechaHora = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String rutaArchivo = "Factura_" + fechaHora + ".pdf";
            
            PdfWriter.getInstance(documento, new FileOutputStream(rutaArchivo));
            documento.open();
            
            Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, BaseColor.BLACK);
            Font fuenteSubtitulo = FontFactory.getFont(FontFactory.HELVETICA, 11, BaseColor.DARK_GRAY);
            Font fuenteTabla = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, BaseColor.WHITE);
            Font fuenteTotal = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);

            Paragraph titulo = new Paragraph("TECNISKATE", fuenteTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);
            
            Paragraph infoTienda = new Paragraph("Soporte y Venta de Hardware / Skate\nCiudad Bolívar, Bogotá\nAtendido por: Federico\n\n", fuenteSubtitulo);
            infoTienda.setAlignment(Element.ALIGN_CENTER);
            documento.add(infoTienda);
            
            documento.add(new Paragraph("Fecha de emisión: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()) + "\n\n"));

            PdfPTable tabla = new PdfPTable(5); 
            tabla.setWidthPercentage(100);
            
            String[] encabezados = {"Artículo", "Marca", "Precio Unit.", "Cant.", "Subtotal"};
            for (String enc : encabezados) {
                PdfPCell celda = new PdfPCell(new Phrase(enc, fuenteTabla));
                celda.setBackgroundColor(BaseColor.BLACK);
                celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabla.addCell(celda);
            }
            
            double granTotal = 0;
            for (DetalleVenta detalle : listaVentas) {
                tabla.addCell(detalle.getSkate().getArticulo());
                tabla.addCell(detalle.getSkate().getMarca());
                tabla.addCell("$ " + detalle.getSkate().getPrecio());
                tabla.addCell(String.valueOf(detalle.getCantidad()));
                tabla.addCell("$ " + detalle.getSubtotal());
                granTotal += detalle.getSubtotal();
            }
            
            PdfPCell celdaTotalTexto = new PdfPCell(new Phrase("TOTAL A PAGAR:", fuenteTotal));
            celdaTotalTexto.setColspan(4);
            celdaTotalTexto.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tabla.addCell(celdaTotalTexto);
            
            PdfPCell celdaTotalValor = new PdfPCell(new Phrase("$ " + granTotal, fuenteTotal));
            tabla.addCell(celdaTotalValor);
            
            documento.add(tabla);
            
            Paragraph despedida = new Paragraph("\n¡Gracias por tu compra! Patina duro.", fuenteSubtitulo);
            despedida.setAlignment(Element.ALIGN_CENTER);
            documento.add(despedida);
            
            documento.close();
        } catch (Exception e) {
            System.out.println("Error en PDF: " + e.getMessage());
        }
    }
}