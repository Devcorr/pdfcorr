package com.pdfcorr.service;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfEncryptor;
import com.itextpdf.text.pdf.PdfException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.*;

/**
 * Created by joeymadich on 2/3/15.
 */
public class PdfProtectionService {

    public void protectPdfDocument(String encryptedPassword, File documentToProtect) throws IOException, DocumentException {
        String decryptedPassword = encryptedPassword.trim();
        InputStream is = new FileInputStream(documentToProtect);
        PdfReader documentToEncryptReader = new PdfReader(is);
        is.close();

        OutputStream os = new FileOutputStream(documentToProtect.getPath());
        PdfEncryptor.encrypt(documentToEncryptReader, os, true, decryptedPassword, decryptedPassword,
                (PdfWriter.ALLOW_COPY | PdfWriter.ALLOW_PRINTING | PdfWriter.ALLOW_SCREENREADERS | PdfWriter.ALLOW_FILL_IN) | PdfWriter.ALLOW_ASSEMBLY | PdfWriter.ALLOW_MODIFY_CONTENTS | PdfWriter.ALLOW_MODIFY_ANNOTATIONS);

        os.close();
    }
}
