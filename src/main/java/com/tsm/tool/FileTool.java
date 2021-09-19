package com.tsm.tool;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.*;

/**
 * @Description: 文件工具
 * @Author: raining19
 * @Date: 2021/07/19/17:12
 * @Version: 1.0
 */
public class FileTool {

    private static Logger log = LoggerFactory.getLogger(FileTool.class);

    public static void main(String[] args) {

        String dirPath = "D:\\WMP\\20 学习\\2021java.ZIP";
        String file1Path = "D:\\WMP\\20 学习\\03 JAVA\\Java-华山版.pdf";
        String file2Path = "D:\\WMP\\20 学习\\03 JAVA\\Java 生态知识体系.pdf";
        List<String> list = new ArrayList<>(2);
        list.add(file1Path);
        list.add(file2Path);
        zipFiles(dirPath, list);
        //scanDir(dirPath);
        //findFileOfName(dirPath, "java");

        /*File file = new File(dirPath);
        System.out.println(file.getName());
        System.out.println(file.length());*/
    }

    /**
     * 扫描文件夹，获取所有文件路径
     *
     * @param dirPath 文件夹目录
     */
    public static List<String> scanDir(String dirPath){
        List<String> fileList = new ArrayList<>();
        File dir = new File(dirPath);
        if (dir.exists()) {
            File[] files = dir.listFiles();
            if (null == files || files.length == 0) {
                log.info("文件夹是空的!");
                return fileList;
            }

            for (File file : files) {
                if (file.isDirectory()) {
                    scanDir(file.getAbsolutePath());
                } else {
                    fileList.add(file.getAbsolutePath());
                    log.info(file.getAbsolutePath());
                }
            }
        } else {
            log.info("文件不存在!");
        }
        return fileList;
    }

    /**
     * 扫描文件夹里特定类型文件
     * @param dirPath 文件夹目录
     * @param fileType 文件类型
     */
    public static List<String> findFileOfType(String dirPath, String fileType){
        List<String> fileList = new ArrayList<>();
        File dir = new File(dirPath);
        if (dir.exists()) {
            File[] files = dir.listFiles();
            if (null == files || files.length == 0) {
                log.info("文件夹是空的!");
                return fileList;
            }

            for (File file : files) {
                if (file.isDirectory()) {
                    findFileOfType(file.getAbsolutePath(), fileType);
                } else {
                    String fileName = file.getName();
                    if(fileType.equals(StringTool.getFileType(fileName))){
                        fileList.add(file.getAbsolutePath());
                        log.info(file.getAbsolutePath());
                    }
                }
            }
        } else {
            log.info("文件不存在!");
        }
        return fileList;
    }

    /**
     * 扫描文件夹里包含指定文件名目录
     * @param dirPath 文件夹目录
     * @param name 文件名
     */
    public static List<String> findFileOfName(String dirPath, String name){
        List<String> fileList = new ArrayList<>();
        File dir = new File(dirPath);
        if (dir.exists()) {
            File[] files = dir.listFiles();
            if (null == files || files.length == 0) {
                log.info("文件夹是空的!");
                return fileList;
            }

            for (File file : files) {
                if (file.isDirectory()) {
                    findFileOfName(file.getAbsolutePath(), name);
                } else {
                    String fileName = file.getName();
                    if(fileName.contains(name)){
                        fileList.add(file.getAbsolutePath());
                        log.info(file.getAbsolutePath());
                    }
                }
            }
        } else {
            log.info("文件不存在!");
        }
        return fileList;
    }

    /**
     * 解压zip文件
     *
     * @param zipFileName zip文件名
     * @param tarPath 目标路径
     */
    public static boolean unZipFile(String zipFileName, String tarPath) {
        boolean unzipSuccess = false;
        InputStream is = null;
        ZipFile zf = null;
        try {
            zf = new ZipFile(new File(zipFileName));
            Enumeration en = zf.entries();
            while (en.hasMoreElements()) {
                ZipEntry zn = (ZipEntry) en.nextElement();
                if (!zn.isDirectory()) {
                    is = zf.getInputStream(zn);
                    String filePath = tarPath + zn.getName();
                    //避免攻击者恶意修改任意文件
                    if (StringUtils.contains(filePath, "..")) {
                        throw new Exception("文件路径有被恶意攻击风险");
                    }
                    File f = new File(filePath);
                    File file = f.getParentFile();
                    file.mkdirs();

                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(filePath);
                        int len = 0;
                        byte[] bufer = new byte[4096];
                        while (-1 != (len = is.read(bufer))) {
                            fos.write(bufer, 0, len);
                        }
                    } finally {
                        if (fos != null) {
                            try {
                                fos.close();
                            } catch (Exception ex) {
                                log.error("写入文件异常", ex);
                            }
                        }
                    }
                }
            }

            unzipSuccess = true;

        } catch (ZipException ze) {
            log.error("zip解压异常！", ze);
        } catch (Exception e) {
            log.error("zip解压IO异常！", e);
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    log.error("文件流关闭异常！", e);
                }
            }
            if (zf != null) {
                try {
                    zf.close();
                } catch (Exception e) {
                    log.error("文件流关闭异常！", e);
                }
            }
        }
        return unzipSuccess;
    }

    /**
     * 单压缩文件
     *
     * @param zipFileName ZIP文件名
     * @param filename    源文件名
     */
    public static void zipFile(String zipFileName, String filename) {
        List<String> fileList = new ArrayList<>();
        fileList.add(filename);
        zipFiles(zipFileName, fileList);
    }

    /**
     * 多文件压缩
     *
     * @param zipFileName ZIP文件名
     * @param fileList 待压缩文件路径
     */
    public static void zipFiles(String zipFileName, List<String> fileList) {
        FileOutputStream output = null;
        ZipOutputStream zipOutput = null;
        FileInputStream input = null;
        try {
            output = new FileOutputStream(zipFileName);
            zipOutput = new ZipOutputStream(output);
            ;

            for (String filePath : fileList) {
                String entryName = filePath;
                int idx = filePath.lastIndexOf("\\");
                if (idx == -1) {
                    idx = filePath.lastIndexOf("/");
                }
                if (idx != -1) {
                    entryName = filePath.substring(idx + 1);
                }

                ZipEntry entry = new ZipEntry(entryName);
                File ff = new File(filePath);

                entry.setSize(ff.length());
                entry.setTime(ff.lastModified());

                entry.setCrc(0);
                CRC32 crc = new CRC32();
                crc.reset();

                zipOutput.putNextEntry(entry);

                input = new FileInputStream(filePath);

                int len = 0;
                byte[] buffer = new byte[4096];
                int bufferLen = 4096;

                while ((len = input.read(buffer, 0, bufferLen)) != -1) {
                    zipOutput.write(buffer, 0, len);
                    crc.update(buffer, 0, len);
                }
                entry.setCrc(crc.getValue());

                zipOutput.closeEntry();
            }

        } catch (IOException ioe) {
            log.error("zip文件压缩异常！", ioe);
            throw new RuntimeException("文件压缩失败");
        } finally {
            if (zipOutput != null) {
                try {
                    zipOutput.close();
                } catch (IOException e) {
                    log.error("zip流关闭异常！");
                }
            }
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    log.error("输入流关闭异常！");
                }
            }
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    log.error("输出流关闭异常！");
                }
            }
        }
    }
}
