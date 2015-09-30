/**
 * This code sample was written by Bruno Lowagie in answer to this question:
 * http://stackoverflow.com/questions/29331838/add-multiple-images-into-a-single-pdf-file-with-itext-using-java
 */
package com.itextpdf.samples.sandbox.images;

import com.itextpdf.basics.geom.PageSize;
import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Image;
import com.itextpdf.samples.GenericTest;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileOutputStream;

@Category(SampleTest.class)
public class MultipleImages extends GenericTest {
    public static final String[] IMAGES = {
            "./src/test/resources/sandbox/images/berlin2013.jpg",
            "./src/test/resources/sandbox/images/javaone2013.jpg",
            "./src/test/resources/sandbox/images/map_cic.png"
    };
    public static final String DEST = "./target/test/resources/sandbox/images/multiple_images.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new MultipleImages().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        Image image = new Image(ImageFactory.getImage(IMAGES[0]));
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, new PageSize(image.getImageWidth(), image.getImageHeight()));

        // TODO why not to add mechanically on the right page
        for (int i = 0; i < IMAGES.length; i++) {
            image = new Image(ImageFactory.getImage(IMAGES[i]));
            pdfDoc.addNewPage(new PageSize(image.getImageWidth(), image.getImageHeight()));
            image.setFixedPosition(i + 1, 0, 0);
            doc.add(image);
        }
        doc.close();
    }
}