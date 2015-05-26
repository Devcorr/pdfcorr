package com.pdfcorr;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.exceptions.BadPasswordException;
import com.itextpdf.text.pdf.PdfReader;
import com.pdfcorr.service.PdfProtectionService;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

/**
 * Created by joeymadich on 2/3/15.
 */
public class PdfProtectionServiceTest {

    private File testDocumentToProtect;

    private PdfProtectionService testProtectionService;

    private File testProtectedDocument;

    private static final String protectedDocumentPathLocation = "/tmp/test.pdf";

    @Before
    public void setup() {
        //copy resource to file system in /tmp/ directory
        //set the document to use to a file object that is
        //the newly copied resource on the filesystem.
        this.setTestProtectionService(new PdfProtectionService());

        //since we expect the file that is requested for protection will
        //stay in the same location after protection has completed.
        this.setTestDocumentToProtect(new File(protectedDocumentPathLocation));
        this.setTestProtectedDocument(new File(protectedDocumentPathLocation));
        try {
            ExportResource("/test.pdf");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void createPdfProtectionService() {
        assertNotNull(this.getTestDocumentToProtect());
    }

    @Test(expected = BadPasswordException.class)
    public void protectPdfDocumentTest() throws IOException, DocumentException {
        String testPassword = "test1234";
        this.getTestProtectionService().protectPdfDocument(testPassword, this.getTestDocumentToProtect());
        PdfReader reader = new PdfReader(this.getTestProtectedDocument().getPath());
    }

    @Test
    public void openProtectPdfDocumentWithPassword() throws IOException, DocumentException{
        String testPassword = "test1234";
        this.getTestProtectionService().protectPdfDocument(testPassword, this.getTestDocumentToProtect());
        PdfReader reader = new PdfReader(this.getTestProtectedDocument().getPath(), testPassword.getBytes());
        assertNotNull(reader);
    }

    /**
     * This method takes a resource file from the classpath
     * and puts it in a specified location on the file system of
     * the executing machine.
     * @param resourceName
     */
    private static String ExportResource(String resourceName) throws Exception {
        InputStream stream = null;
        OutputStream resStreamOut = null;
        final String destFolder = "/tmp/";
        try {
            stream = PdfProtectionServiceTest.class.getResourceAsStream(resourceName);
            if(stream == null) {
                throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
            }

            int readBytes;
            byte[] buffer = new byte[4096];
            resStreamOut = new FileOutputStream(destFolder + resourceName);
            while ((readBytes = stream.read(buffer)) > 0) {
                resStreamOut.write(buffer, 0, readBytes);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            stream.close();
            resStreamOut.close();
        }

        return destFolder + resourceName;
    }

    public File getTestDocumentToProtect() {
        return testDocumentToProtect;
    }

    public void setTestDocumentToProtect(File testDocumentToProtect) {
        this.testDocumentToProtect = testDocumentToProtect;
    }

    public PdfProtectionService getTestProtectionService() {
        return testProtectionService;
    }

    public void setTestProtectionService(PdfProtectionService testProtectionService) {
        this.testProtectionService = testProtectionService;
    }

    public File getTestProtectedDocument() {
        return testProtectedDocument;
    }

    public void setTestProtectedDocument(File mockProtectedDocument) {
        this.testProtectedDocument = mockProtectedDocument;
    }
}
