package com.pdfcorr.service;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfEncryptor;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.*;

/**
 * Created by joeymadich on 2/3/15.
 */
public class PdfProtectionService {

    private String errorMessage;

    public void protectPdfDocument(String encryptedPassword, File documentToProtect) {
        try {
            String decryptedPassword = encryptedPassword.trim();
            InputStream is = new FileInputStream(documentToProtect);
            PdfReader documentToEncryptReader = new PdfReader(is);
            is.close();

            OutputStream os = new FileOutputStream(documentToProtect.getPath());
            try {
                PdfEncryptor.encrypt(documentToEncryptReader, os, true, decryptedPassword, decryptedPassword,
                        (PdfWriter.ALLOW_COPY | PdfWriter.ALLOW_PRINTING | PdfWriter.ALLOW_SCREENREADERS | PdfWriter.ALLOW_FILL_IN) | PdfWriter.ALLOW_ASSEMBLY | PdfWriter.ALLOW_MODIFY_CONTENTS | PdfWriter.ALLOW_MODIFY_ANNOTATIONS);
            }
            catch(DocumentException de) {
                this.setErrorMessage("There was something wrong with the file used. Try another file.");
                de.printStackTrace();
            }

            os.close();
        }
        catch(IOException e) {
            this.setErrorMessage("There was something wrong with the file used. Try another file.");
            e.printStackTrace();
        }

    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
