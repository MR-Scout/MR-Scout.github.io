package com.mr.extractor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mr.extractor.config.Config;
import com.mr.extractor.util.FileUtil;
import com.mr.extractor.util.SystemUtil;


/* 
* java，通过命令行传参数： https://blog.csdn.net/liuhenghui5201/article/details/18553765
* 
*/
public class Main {

    public static void main(String[] args) throws IOException {
        Map<String,String> PojsAndDir = new HashMap<>();
        String PID = ""+SystemUtil.getProcessID();
        System.out.println( "PID_tool_main_start:" + PID + ":PID_end" );
        Config config = new Config();

        if (args.length>0 && args.length==2){
            for (String string : args) {
                System.out.println(string);
            }
            PojsAndDir.put(args[0], args[1]);
        }
        else{
            // PojsAndDir.put("CoreNLP", config.AUTOMR_EXPERIMENTAL_POJS_DIR + "stanfordnlp__split__CoreNLP");
            // PojsAndDir.put("Mahout", config.AUTOMR_EXPERIMENTAL_POJS_DIR + "apache__split__mahout");
            // PojsAndDir.put("Cornutum__split__tcases", config.AUTOMR_EXPERIMENTAL_POJS_DIR + "Cornutum__split__tcases");   
            System.out.println("ERROR: No args received. Please check your input :(.");

        }
        // TODO: run之前，清空历史遗留 output
        for (String pojName : PojsAndDir.keySet()) {
            // store program in MRidentifier.storeRelationAssertion
            // two path: 1. dir output/  2. at the root dir of poj
            // 1. dir output/ 
            String dirRelationAssertion =  config.AUTOMR_EXPERIMENTAL_POJS_MTIDENTIFIER_RELATIONASSRTION_OUTPUT_DIR + pojName +"/";
            FileUtil.deleteFilesInFolder(dirRelationAssertion);

            //2. at the root dir of poj
            // String dirRelationAssertion; // pojDir/AutoMR/MTidentifier/FQN_TMD_index.relationAssertion
            // String pojDir = Config.AUTOMR_EXPERIMENTAL_POJS_DIR + pojName + "/";
            String pojDir = PojsAndDir.get(pojName) ;
            dirRelationAssertion =  pojDir + Config.MTidentifier_Fold_path_in_PUA;
            FileUtil.deleteFilesInFolder(dirRelationAssertion);

            // for release package
            //3. create folder
            if(!FileUtil.folderExisting(dirRelationAssertion)){
                FileUtil.createFolder(pojDir + Config.MTidentifier_Fold_path_in_PUA.split("/")[0]);
                FileUtil.createFolder(dirRelationAssertion);
            }
            
        }
        


        // List<String> toDoPatterns = Arrays.asList("bp1","bp2","p1","p2","p3","p4");
        List<String> toDoPatterns = Arrays.asList("M&A"); // M&A: method invocations & assertion
        runPojs(PojsAndDir, toDoPatterns);

        System.out.println( "PID_tool_main_end:" + PID + ":PID_end" );
    }

    public static void runPojs(Map<String,String> PojsAndDir,List<String> toDoPatterns) throws IOException {
        for (String pojName : PojsAndDir.keySet()) {
            // for debug
            // pojName = "Guava";

            System.out.println("pojName: " + pojName);
            String pojDir = PojsAndDir.get(pojName);
            System.out.println("pojDir: " + pojDir);
            // 解析出所有test class的路径
            List<String> filePaths = new ArrayList<String>();
            FileUtil.findFileList(new File(pojDir),filePaths );
            List<String> TestFilePaths = new ArrayList<String>();
            getTestFilePaths(filePaths, TestFilePaths);
            System.out.println("TestFilePaths.size: " + TestFilePaths.size());
            // 解析出所有package source root的路径
            List<String> src_dirs  = Parser.getPackagePathsFromPojDir(pojDir);
            System.out.println("src_dirs: " + src_dirs);

            // for debug
            // TestFilePaths = Arrays.asList("/home/hadoop/dfs/data/Workspace/CongyingXU/data/AutoMR/projects/guava/guava-tests/test/com/google/common/math/LinearTransformationTest.java");
            // TestFilePath: /home/hadoop/dfs/data/Workspace/CongyingXU/data/AutoMR/projects/CoreNLP-main/test/src/edu/stanford/nlp/trees/tregex/tsurgeon/ProcessTsurgeonRequestTest.java

            // test class路径作为参数传入，并运行
            for (String TestFilePath : TestFilePaths) {
                System.out.println("+++ TestFilePath: " + TestFilePath);
                MTIdentifier.identifyMTPatterns(src_dirs,TestFilePath,toDoPatterns);
            }

            // for debug
            // break;
        }
    }

    public static void getTestFilePaths(List<String> filePaths, List<String> TestFilePaths) {
        // List<String> TestFilePaths = new ArrayList<String>();
        for (String filepath : filePaths) {
            if((filepath.contains("/test/") || filepath.contains("/androidTest/")) && filepath.endsWith(".java") && !filepath.toLowerCase().contains("evosuite") && !filepath.toLowerCase().contains("_automr")  && !filepath.contains("_OriginalMR") ) { //
                TestFilePaths.add(filepath);
            }
        }
        
    }    
}
