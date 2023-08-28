package com.mr.extractor.MRgenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.mr.extractor.Parser;
import com.mr.extractor.MRdetector.relationAssertion;
import com.mr.extractor.config.Config;
import com.mr.extractor.util.FileUtil;
import com.mr.extractor.util.objectIO;;

public class MRMeta {
    public String FILE_PATH;
    public String DIR_PATH;
    public String[] TMD_QSs; //testMethodDeclarationQualifiedSignature;
    HashMap<String, List<relationAssertion>> TMQS_MRinstances = new HashMap<String, List<relationAssertion>>();
    public String POJ_Name;


    public MRMeta(String FILE_PATH, String DIR_PATH, String[] TMD_QSs ) throws ClassNotFoundException, IOException {
        this.FILE_PATH = FILE_PATH;
        this.DIR_PATH = DIR_PATH;
        this.TMD_QSs = TMD_QSs; 

        for (String token : FILE_PATH.split("/")) {
            if(token.contains(Config.SPLITE_STR)){ // FILE_PATH.splits -> substring with "__split__" is considered as poj_name
                this.POJ_Name = token; break;
            }
        }
        this.getMRinstances();
    }

    void getMRinstances() throws ClassNotFoundException, IOException{
        Config config = new Config();
        for (String TMD_QS : this.TMD_QSs) {
            List<relationAssertion> MRinstances = new ArrayList<>();
            String TMD_QN = Parser.methodSignatureToName(TMD_QS);
            String relationAssertionDir = config.AUTOMR_EXPERIMENTAL_POJS_MTIDENTIFIER_RELATIONASSRTION_OUTPUT_DIR + this.POJ_Name + "/";

            List<String> fileNames = FileUtil.findFileNameListLevel1(relationAssertionDir);
            for (String fileName : fileNames) {
                if(fileName.endsWith(".relationAssertion") && fileName.startsWith(TMD_QN)){
                    try {
                        relationAssertion relationAssertionObj = (relationAssertion) objectIO.readObjectFromFile(relationAssertionDir+fileName);
                        MRinstances.add( relationAssertionObj );
                    } catch (Exception InvalidClassException) {
                        // TODO: handle exception
                        System.out.println();
                        /*System.out.println( "delete out of date: " + relationAssertionDir + fileName);
                        System.out.println( "delete out of date: " + relationAssertionDir + fileName.replace(".relationAssertion", ".json"));
                        FileUtil.deleteFile(relationAssertionDir + fileName);
                        FileUtil.deleteFile(relationAssertionDir + fileName.replace(".relationAssertion", ".json"));
                        
                        String relationAssertionDir_localPoj = config.AUTOMR_EXPERIMENTAL_POJS_DIR + this.POJ_Name + "/"+ Config.MTidentifier_Fold_path_in_PUA;
                        System.out.println( "delete out of date: " + relationAssertionDir_localPoj + fileName);
                        System.out.println( "delete out of date: " + relationAssertionDir_localPoj + fileName.replace(".relationAssertion", ".json"));
                        FileUtil.deleteFile(relationAssertionDir_localPoj + fileName);
                        FileUtil.deleteFile(relationAssertionDir_localPoj + fileName.replace(".relationAssertion", ".json"));*/
                    }
                    
                }
            }
            this.TMQS_MRinstances.put(TMD_QS, MRinstances);
        }
    }

    HashMap<String, List<String>> MQS_targetObjectsUnderTest = new HashMap<String, List<String>>();
    HashMap<String, List<String>> MQS_targetMethodsUnderTest = new HashMap<String, List<String>>();
    HashMap<String, List<String>> MQS_targetMethodsFromObjectsUnderTest = new HashMap<String, List<String>>();

    public PackageDeclaration testPackageDeclaration;
    public CompilationUnit testFileCU;
    HashMap<String, MethodDeclaration> MQS_testMethodDeclarations = new HashMap<String, MethodDeclaration>();
    HashMap<String, String> MQS_testClassNames = new HashMap<String, String>();
    HashMap<String, String> MQS_testMethodNames = new HashMap<String, String>();
    HashMap<String, String> MQS_testMethodStrings = new HashMap<String, String>();
    public List<String> testMethodNames = new ArrayList<>();

    HashMap<String, List<MethodCallExpr>> MQS_MUTCallExprs = new HashMap<String, List<MethodCallExpr>>();

    public boolean successfulAutoMR = false;
    public CompilationUnit generatedMRFileCU;
    public String generatedMRFilePath;
    HashMap<String, MethodDeclaration> MQS_generatedMRMethodDeclaration = new HashMap<String, MethodDeclaration>();
    HashMap<String, String> MQS_generatedExecutableMRMethodString = new HashMap<String, String>();

    public CompilationUnit generatedonlyOriginalMRFileCU;
    public String generatedonlyOriginalMRFilePath;

    // for 后续流程好解析
    String generatedMRClassFQN; // fully quified name
    // List<String> successfulGeneratedMR_MQS = new ArrayList<>();
    List<String> successfulExtractedOriginal_MQS = new ArrayList<>();
    List<String> successfulGeneratedMR_methodNames = new ArrayList<>(); //generated by getNameAsString()

    public String orignalTestClassFQN;
    public String orignalMTC_FQS;
    public String generatedMRMethodSignature;

    public void updateMUTandCUT( List<String> targetObjectsUnderTest, List<String> targetMethodsUnderTest, List<String> targetMethodsFromObjectsUnderTest, String TMD_QS) {
        this.MQS_targetObjectsUnderTest.put(TMD_QS, targetObjectsUnderTest);
        this.MQS_targetMethodsUnderTest.put(TMD_QS, targetMethodsUnderTest);
        this.MQS_targetMethodsFromObjectsUnderTest.put(TMD_QS, targetMethodsFromObjectsUnderTest); 
    }




    // for new version: // 貌似并没有使用？？ 221120
    public String TMD_QS;
    
    public MRMeta(String FILE_PATH, String DIR_PATH, String TMD_QS ) {
        this.FILE_PATH = FILE_PATH;
        this.DIR_PATH = DIR_PATH;
        this.TMD_QS = TMD_QS; 
    }
}
