package com.mr.extractor.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.checkerframework.common.returnsreceiver.qual.This;

public class Config {
    // 由于内部许多代码是静态引用, 所以，用之前需要 new config();执行一下

    public Config() throws UnknownHostException{
        InetAddress ia = InetAddress.getLocalHost();
        String HOST_NAME = ia.getHostName();//获取计算机主机名 
        // String IP= ia.getHostAddress();//获取计算机IP 

        if(HOST_NAME.equals("sccpu4.cse.ust.hk")){
            DIR_DATA = "/ssddata1/cxubl/data/"; // todo
            DIR_SOFTWARE = "/ssddata1/cxubl/software/";
            DIR_WORKPROJECTS = "/ssddata1/cxubl/workProjects/";
            PATH_JAVADEMO = "/tmp/cp_9ggftvcuasvgz762z1fw42u04.jar"; // to check,
            DIR_HOME = "/data/cxubl/";
        }
        else if(HOST_NAME.equals("sccpu2.cse.ust.hk")){
            DIR_DATA = "/home1/cxubl/data/"; 
            DIR_SOFTWARE = "/home1/cxubl/software/";
            DIR_WORKPROJECTS = "/home1/cxubl/workProjects/";
            PATH_JAVADEMO = "/tmp/cp_9ggftvcuasvgz762z1fw42u04.jar"; 
            DIR_HOME = "/home1/cxubl/";
        }

        // dir
        AUTOMR_EXPERIMENTAL_POJS_DIR = DIR_DATA + "AutoMR/projects/" ;
        AUTOMR_EXPERIMENTAL_POJS_MTIDENTIFIER_RELATIONASSRTION_OUTPUT_DIR = DIR_DATA + "AutoMR/projects_MTidentifier_relationAssertion_outputs/";        
    }

    public static String DIR_DATA;
    public static String DIR_SOFTWARE;
    public static String DIR_WORKPROJECTS;
    public static String PATH_JAVADEMO;
    public static String DIR_HOME;

    // dir
    public static String AUTOMR_EXPERIMENTAL_POJS_DIR ;
    public static String AUTOMR_EXPERIMENTAL_POJS_MTIDENTIFIER_RELATIONASSRTION_OUTPUT_DIR;


    public static String PATH_AUTOMR_PROFILE_JSON = "AutoMR/AutoMRProfile.json";
    

    // name 
    public static String TOOL_NAME = "AutoMR";
    public static String OriginalMRcases = "OriginalMRcases";
    public static String FILENAMR_AUTOMR_PROFILE_JSON = "AutoMRProfile.json";

    // str
    public static String SPLITE_STR = "__split__";
    public static String MTidentifier_Fold_path_in_PUA = "AutoMR/MTidentifier/"; // PUA=poj under analysis


    // Jar
    public static String getPathJUNIT4_JAR() {
        String JUNIT4_JAR = DIR_SOFTWARE + "junit/junit-4.13.2.jar";
        return JUNIT4_JAR;
    }
    public static String getPathJUNIT5_JAR() {
        String JUNIT5_JAR = DIR_SOFTWARE + "junit/junit-jupiter-api-5.8.2.jar";
        return JUNIT5_JAR;
    }
    
    
    public static void main(String[] args) throws UnknownHostException {
        // InetAddress addr = InetAddress.getLocalHost();
        // System.out.println("Local HostAddress: "+addr.getHostAddress());
        // String hostname = addr.getHostName();
        // System.out.println("Local host name: "+hostname);

        System.out.println( Config.AUTOMR_EXPERIMENTAL_POJS_DIR );
        new Config();
        System.out.println( Config.AUTOMR_EXPERIMENTAL_POJS_DIR );
    }
}
