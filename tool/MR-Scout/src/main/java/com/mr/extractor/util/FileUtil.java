package com.mr.extractor.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
 
    public static void main(String[] args) throws Exception {
        try {
            // FileUtil.readTextFile("asd.json");
            // FileUtil.readTextFile("asd.json");
            int a=3-3;
            System.out.println(5/a);
        } catch (Exception e) {
            //TODO: handle exception
			System.out.println("No such file or directory??");
        }
		// FileUtil.readTextFile("asd.json");
        int a=3-3;
        System.out.println(5/a);
		System.out.println("No such file or directory -------");
    }
    
    
    /**
     * 读取目录下的所有文件
     * 
     * @param dir
     *            目录
     * @param fileNames
     *            保存文件名的集合
     * @return
     */
    public static void findFileList(File dir, List<String> filePaths) {
        if (!dir.exists() || !dir.isDirectory()) {// 判断是否存在目录
            return;
        }
        String[] files = dir.list();// 读取目录下的所有目录文件信息
        for (int i = 0; i < files.length; i++) {// 循环，添加文件名或回调自身
            File file = new File(dir, files[i]);
            if (file.isFile()) {// 如果文件
                filePaths.add(dir + "/" + file.getName());// 添加文件全路径名
            } else {// 如果是目录
                findFileList(file, filePaths);// 回调自身继续查询
            }
        }
    }

    public static List<String> findFileNameListLevel1(String Dir) {
        List<String> fileNames = new ArrayList<>();
        File dir = new File(Dir);
        if (!dir.exists() || !dir.isDirectory()) {// 判断是否存在目录
            return fileNames;
        }
        String[] files = dir.list();// 读取目录下的所有目录文件信息
        for (int i = 0; i < files.length; i++) {// 循环，添加文件名或回调自身
            File file = new File(dir, files[i]);
            if (file.isFile()) {// 如果文件
                fileNames.add(file.getName());// 添加文件全路径名
            } 
        }
        return fileNames;
    }

    public static void writeTextFile(String FilePath, String content)  {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(FilePath));
            out.write(content);
            out.close();
            // System.out.println("文件创建成功！");
        } catch (IOException e) {
        }
    }

    public static String readTextFile(String FilePath) throws IOException{
        String content = new String(Files.readAllBytes(Paths.get(FilePath)));
        return content;
    }

    public static boolean folderExisting(String folderPath)  {
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            return true;
        }
        else{
            return false;
        }
    }

    public static void folderExistingIfNotCreate(String folderPath)  {
        File folder = new File(folderPath);
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
        }
    }

    public static boolean fileExisting(String filePath)  {
        File file = new File(filePath);
        if (file.exists()) {
            return true;
        }
        else{
            return false;
        }
    }

    public static String findPojDir(String filePath, String splitString){
        String pojName = findPojName(filePath, splitString);
        String pojDir = filePath.split(pojName)[0] + pojName + "/";
        return pojDir;
    }

    public static String findPojName(String filePath, String splitString){
        String pojName=null;
        for (String element : filePath.split("/")) {
            if( element.contains(splitString) ){
                pojName = element;
                return element;
            }
        }
        return pojName;  
    }

    public static void deleteFile(String filePath){
        try{
            File file = new File(filePath);
            if(file.delete()){
                ;
            }else{
                System.out.println("deleteFile failed: " + filePath);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void deleteFilesInFolder(String folderPath){
        try{
            // specify the folder to delete files from
            File folder = new File(folderPath);

            if (folder.exists()) {
                // get all the files in the folder
                File[] files = folder.listFiles();
                // delete all files in the folder
                for (File file : files) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
                System.out.println("All files deleted from folder: " + folderPath);
            } else {
                System.out.println("Folder does not exist: " + folderPath);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void createFolder(String folderPath){
        // Specify the folder path

        // Create a File object representing the folder
        File folder = new File(folderPath);

        // Check if the folder does not already exist
        if (!folder.exists()) {
            // Attempt to create the folder
            boolean isCreated = folder.mkdir();
            if (isCreated) {
                System.out.println("Folder successfully created at: " + folder.getAbsolutePath());
            } else {
                System.out.println("Failed to create the folder: " + folder.getAbsolutePath());
            }
        } else {
            System.out.println("Folder already exists at: " + folder.getAbsolutePath());
        }
    }





}