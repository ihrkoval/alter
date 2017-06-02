package com.alter.automation.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by jlab13 on 16.05.2017.
 */
public class ZipFiles {

    public String zipFiles(List<String> filePaths) {
        String path = "";
        String zipFileName = "debits.zip";
        try {
            File firstFile = new File(filePaths.get(0));

            path = firstFile.getPath().substring(0, firstFile.getPath().lastIndexOf("\\") );
            File zipFile = new File(path+"\\"+zipFileName);
            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(fos, Charset.forName("cp866"));
           for (String aFile : filePaths) {
               System.out.println(aFile+ " FILE");
               String fileName = new File(aFile).getName();
               fileName = fileName.replace("і", "и");
                zos.putNextEntry(new ZipEntry(fileName));
                byte[] bytes = Files.readAllBytes(Paths.get(aFile));
                zos.write(bytes, 0, bytes.length);
                zos.closeEntry();
            }

            zos.close();

        } catch (FileNotFoundException ex) {
            System.err.println("A file does not exist: " + ex);
        } catch (IOException ex) {
            System.err.println("I/O error: " + ex);
        }
    return path+"\\"+zipFileName;
    }

}