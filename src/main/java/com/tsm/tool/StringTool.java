package com.tsm.tool;

import com.tsm.constant.StringPool;

import java.io.File;

/**
 * @Description: 字符串工具箱
 * @Author: raining19
 * @Date: 2021/07/19/17:09
 * @Version: 1.0
 */
public class StringTool {

    public static void main(String[] args) {
        String s = "w\\w\\w3.txt";
        System.out.println(getFileName("w/w/w3.txt"));
        System.out.println(getFileType("w/w/w3txt"));
        System.out.println(getFileType("w/w/w3.txt"));
        System.out.println(getFileType("w/w/w3.txt.zip"));
    }

    /**
     * 根据输入路径，获取文件名
     * @param filePath 路径
     * @return 文件名
     */
    public static String getFileName(String filePath){
        if(isEmpty(filePath)){
            return null;
        }
        String fileName;
        if(filePath.contains(StringPool.SLASH)){
            fileName = filePath.substring(filePath.lastIndexOf(StringPool.SLASH) + 1);
        }else if(filePath.contains(StringPool.BACK_SLASH)){
            fileName = filePath.substring(filePath.lastIndexOf(StringPool.BACK_SLASH) + 1);
        }else {
            fileName = filePath;
        }
        return fileName;
    }

    /**
     * 根据输入路径，获取文件类型
     * @param filePath 路径
     * @return 文件类型
     */
    public static String getFileType(String filePath){
        if(isEmpty(filePath)){
            return null;
        }
        String fileType;
        if(filePath.contains(StringPool.DOT)){
            fileType = filePath.substring(filePath.lastIndexOf(StringPool.DOT) + 1);
        }else {
            fileType = filePath;
        }
        return fileType;
    }


    /**
     * 判断字符串是否为null，和空串
     * @param str 字符串
     * @return 不为空返回true
     */
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }

    /**
     * 判断字符串是否为null，和空串
     * @param str 字符串
     * @return 为空返回true
     */
    public static boolean isEmpty(String str){
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }

}
