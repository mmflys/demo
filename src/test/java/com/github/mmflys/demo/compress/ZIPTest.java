package com.github.mmflys.demo.compress;

import java.io.*;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZIPTest {

    private static ZipEntry createZipEntry(String fileName, File file) throws IOException {
        if (file.isDirectory()) {
            return zipEntry(fileName);
        } else {
            return zipEntry(fileName, file);
        }
    }

    private static ZipEntry zipEntry(String fileName, File file) throws IOException {
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipEntry.setMethod(ZipEntry.STORED);
        zipEntry.setCompressedSize(file.length());
        zipEntry.setSize(file.length());
        CRC32 crc = new CRC32();

        InputStream inputStream = new FileInputStream(file);
        byte[] data = new byte[inputStream.available()];
        inputStream.read(data);
        crc.update(data);

        zipEntry.setCrc(crc.getValue());
        return zipEntry;
    }

    private static ZipEntry zipEntry(String fileName) {
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipEntry.setMethod(ZipEntry.STORED);
        zipEntry.setCompressedSize(0);
        zipEntry.setSize(0);
        CRC32 crc = new CRC32();
        zipEntry.setCrc(crc.getValue());
        return zipEntry;
    }

    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(createZipEntry(fileName, fileToZip));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(createZipEntry(fileName + "/", fileToZip));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        System.out.println("Going to pack file " + fileToZip.getPath());
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = createZipEntry(fileName, fileToZip);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        zipOut.closeEntry();
        fis.close();
        System.out.println("Finished to pack file " + fileToZip.getPath());
    }

    public static void main(String[] args) throws IOException {
        String sourceFile = "logs";
        FileOutputStream fos = new FileOutputStream("logs.zip");
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        File fileToZip = new File(sourceFile);

        zipFile(fileToZip, fileToZip.getName(), zipOut);
        zipOut.close();
        fos.close();
    }
}
