package com.mr.extractor;

import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JarTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.mr.extractor.MRdetector.methodInvocation;
import com.mr.extractor.MRdetector.relationAssertion;
import com.mr.extractor.config.Config;
import com.mr.extractor.util.*;

public class MTIdentifier {
    public static JavaSymbolSolver symbolSolver=null;
    public static String FILE_PATH=null;

    public static void main(String[] args) throws Exception {
        Config config = new Config();
        String FILE_PATH = config.DIR_DATA + "data/AutoMR/projects/quicktheories__split__QuickTheories/core/src/test/java/org/quicktheories/highlevel/LongsComponentTest.java";
        // String SRC_PATH = "src/main/java/";
        String DIR_PATH = config.DIR_DATA + "data/AutoMR/projects/quicktheories__split__QuickTheories/";

        FILE_PATH = config.DIR_DATA + "AutoMR/projects/alibaba__split__one-java-agent/one-java-agent-plugin/src/test/java/com/alibaba/oneagent/utils/FeatureCodecTest.java";
        // String SRC_PATH = "src/main/java/";
        DIR_PATH = config.DIR_DATA + "AutoMR/projects/alibaba__split__one-java-agent/";


        TypeSolver reflectionTypeSolver = new ReflectionTypeSolver(); //  part of the language, like java.lang.Object

        CombinedTypeSolver combinedSolver = new CombinedTypeSolver();
        combinedSolver.add(reflectionTypeSolver);
        combinedSolver.add(new JarTypeSolver( config.getPathJUNIT4_JAR() ));
        combinedSolver.add(new JarTypeSolver( config.getPathJUNIT5_JAR() ));
        // TypeSolver javaParserTypeSolver = new JavaParserTypeSolver(new File(SRC_PATH)); // r looks for classes defined in source files
        // combinedSolver.add(javaParserTypeSolver);

        // List<String> toDoPatterns = Arrays.asList("bp1","bp2","p1","p2","p3","p4");
        List<String> toDoPatterns = Arrays.asList("M&A"); // M&A: method invocations & assertion
        identifyMTPatterns(Parser.getPackagePathsFromPojDir(DIR_PATH), FILE_PATH, toDoPatterns);

    }

    /**
     * identifyMTPatterns: identifyMTPatterns
     *
     * @return 
     * @throws IOException
     */
    public static void identifyMTPatterns(List<String> SRC_PATHs, String FILE_PATH, List<String> Patterns) throws IOException {
        Config config = new Config();
        MTIdentifier.FILE_PATH = FILE_PATH;

        TypeSolver reflectionTypeSolver = new ReflectionTypeSolver();
        // TypeSolver javaParserTypeSolver = new JavaParserTypeSolver(new File(SRC_PATH));
        // reflectionTypeSolver.setParent(reflectionTypeSolver);

        CombinedTypeSolver combinedSolver = new CombinedTypeSolver();
        combinedSolver.add(reflectionTypeSolver);
        // combinedSolver.add(javaParserTypeSolver);

        for (String SRC_PATH : SRC_PATHs ) {
            TypeSolver javaParserTypeSolver = new JavaParserTypeSolver(new File(SRC_PATH)); // r looks for classes defined in source files
            combinedSolver.add(javaParserTypeSolver);
        }
        combinedSolver.add(new JarTypeSolver( config.getPathJUNIT4_JAR() ));
        combinedSolver.add(new JarTypeSolver( config.getPathJUNIT5_JAR() ));

        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(combinedSolver);
        MTIdentifier.symbolSolver = symbolSolver;
        StaticJavaParser
                .getConfiguration()
                .setSymbolResolver(symbolSolver);

        
            // System.out.println("!!!!!!!FILE_PATH: " + FILE_PATH);
            // CompilationUnit cu = StaticJavaParser.parse(new FileInputStream(FILE_PATH));
            CompilationUnit cu;
            try {
                cu = StaticJavaParser.parse(new FileInputStream(FILE_PATH));
            } catch (com.github.javaparser.ParseProblemException e) {
                //TODO: handle exception //该文件没法parser
                return;
            }
            // CompilationUnit cu = StaticJavaParser.parse(new File(FILE_PATH));
            if( cu.findAll(PackageDeclaration.class).size()<1) return; // CONDITION: 无package声明
            PackageDeclaration pd = cu.findAll(PackageDeclaration.class).get(0);

            // System.out.println("!!!!!!!"+ cu.findAll(MethodDeclaration.class));
            List<MethodDeclaration> mds = cu.findAll(MethodDeclaration.class);

            String testClassName = FILE_PATH.split("/")[ FILE_PATH.split("/").length -1 ].replace(".java","");

            for (MethodDeclaration md: mds){
                try {
                    // valid test methods
                    if(!Parser.isValidTestMethod(md)) continue;
                    System.out.println("TestFilePath: " + FILE_PATH);
                    System.out.println(  "++++++ " + "MethodDeclarationQualifiedSignature: " + md.asMethodDeclaration().resolve().getQualifiedSignature());
                    System.out.println( md);
                
                    if (Patterns.contains("bp1"))
                    System.out.println(  "------ " + "IsBasePattern1: " + IsBPattern1(md,pd) );
                    if (Patterns.contains("bp2"))
                    System.out.println(  "------ " + "IsBasePattern2: " + IsBPattern2(md,pd) );

                    if (Patterns.contains("p1") || Patterns.contains("p2") || Patterns.contains("p3") || Patterns.contains("p4") ){
                        // getTargetObject()
                        List<String> targetObjectsUnderTest = Parser.getTargetObjectsUnderTest(md, pd, testClassName);    
                        // getTargetMethods()
                        List<String> targetMethodsUnderTest = Parser.getTargetMethodsUnderTest(md, pd);
                        List<String> targetMethodsFromObjectsUnderTest = Parser.getTargetMethodsUnderTestFromObjects(md, pd, targetObjectsUnderTest);
                        
                        if (targetMethodsUnderTest.size()==0 && targetObjectsUnderTest.size()==0 && targetMethodsFromObjectsUnderTest.size()==0){
                            System.out.println( "---------- " +  "no target Methods or Objects" );
                            continue;
                        }
                        else{
                            System.out.println( "---------- " +  "with target Methods or Objects" );
                        }
                        // else if(targetMethodsUnderTest.size()>0 && targetObjectsUnderTest.size()>0){
                        //     System.out.println( "---------- " +  "with target Methods and Objects" );
                        // }

                        System.out.println( "---------- " +  "targetMethodsUnderTest: " + targetMethodsUnderTest);
                        System.out.println( "---------- " +  "targetObjectsUnderTest: " + targetObjectsUnderTest);
                        System.out.println( "---------- " +  "targetMethodsFromObjectsUnderTest: " + targetMethodsFromObjectsUnderTest);

                        if (targetMethodsUnderTest.size()==0){
                            targetMethodsUnderTest = targetMethodsFromObjectsUnderTest;
                            System.out.println( "---------- " +  "target Methods are from Class Under Test" );
                        }
                    
                        if (Patterns.contains("p1"))
                        System.out.println(  "------ " + "IsPattern1: " + IsPattern1(md,pd,targetMethodsUnderTest) );
                        if (Patterns.contains("p2"))
                        System.out.println(  "------ " + "IsPattern2: " + IsPattern2(md,pd,targetObjectsUnderTest) );
                        if (Patterns.contains("p3"))
                        System.out.println(  "------ " + "IsPattern3: " + IsPattern3(md,pd,targetMethodsUnderTest,targetObjectsUnderTest) );
                        if (Patterns.contains("p4"))
                        System.out.println(  "------ " + "IsPattern4: " + IsPattern4(md,pd,targetMethodsUnderTest,targetObjectsUnderTest) );
                    }
                    
                    if (Patterns.contains("M&A")){ 
                        System.out.println(  "------ " + "IsPatternMA: " + IsPatternMA(md,pd) );
                    }
                    System.out.println();
                    System.out.println();
                } 
                catch (Exception e) {
                    //TODO: handle exception
                    System.out.println("ERROR: " + e.getMessage() + e.toString());
                    // e.printStackTrace();
                }
            }
        return;
            
    }

    // for external call
    public static boolean MTIdentifierPattern_invoke(String FILE_PATH, String POJ_DIR_PATH, String Pattern, String mdName) throws FileNotFoundException, IOException, ClassNotFoundException, CloneNotSupportedException{
        Config config = new Config();
        MTIdentifier.FILE_PATH = FILE_PATH;
        boolean pattern_result = false;
        List<String> DIR_PATHs = Parser.getPackagePathsFromPojDir(POJ_DIR_PATH);

        TypeSolver reflectionTypeSolver = new ReflectionTypeSolver(); //  part of the language, like java.lang.Object
        CombinedTypeSolver combinedSolver = new CombinedTypeSolver();
        combinedSolver.add(reflectionTypeSolver);

        List<String> toDoPatterns = Arrays.asList(Pattern);
        for (String SRC_PATH : DIR_PATHs ) {
            TypeSolver javaParserTypeSolver = new JavaParserTypeSolver(new File(SRC_PATH)); // r looks for classes defined in source files
            combinedSolver.add(javaParserTypeSolver);
        }
        combinedSolver.add(new JarTypeSolver( config.getPathJUNIT4_JAR() ));
        combinedSolver.add(new JarTypeSolver( config.getPathJUNIT5_JAR() ));

        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(combinedSolver);
        StaticJavaParser
                .getConfiguration()
                .setSymbolResolver(symbolSolver);

        CompilationUnit cu = StaticJavaParser.parse(new FileInputStream(FILE_PATH));
        PackageDeclaration pd = cu.findAll(PackageDeclaration.class).get(0);
        List<MethodDeclaration> mds = cu.findAll(MethodDeclaration.class);
        for (MethodDeclaration md: mds){
            if(md.getNameAsString().equals(mdName)){
                if (Pattern.contains("M&A")){ 
                    pattern_result = MTIdentifier.IsPatternMA(md,pd) ;
                    break;
                }
            }
        }
        return pattern_result;
    }


    /**
     * BPattern1: Test cases invoke multiple times of the same method
     *
     * @return true or false
     */
    public static boolean IsBPattern1(MethodDeclaration md, PackageDeclaration pd ){
        // System.out.println(md.findAll(MethodCallExpr.class));
        List<MethodCallExpr> methodCalls = md.findAll(MethodCallExpr.class);
        Map<String, Integer> calledMethodCount = new HashMap<String, Integer>();
        String pdName = pd.getNameAsString();

        for (MethodCallExpr mce: methodCalls){
                String mceName = Parser.getFullQualifiedNameOfMethodCallExpr(mce, pd);
                /*try{
                    String mceQualifiedSignature = mce.asMethodCallExpr().resolve().getQualifiedSignature();
                    // System.out.println( mceQualifiedSignature );
                    if (mceQualifiedSignature.contains( pdName )) 
                        mceName = mceQualifiedSignature.substring(0, mceQualifiedSignature.lastIndexOf('('));
                }catch(UnsolvedSymbolException e){
                    if (mce.getName().toString().contains("assert")) continue;
                    System.out.println( "UnsolvedSymbolException: " + e.getMessage());
                    // mceName = mce.getName().toString();
                    System.out.println( "mce.getName(): "+mce.getName() );
                }*/
                
                if ( calledMethodCount.containsKey(mceName) ){
                    calledMethodCount.put(mceName, calledMethodCount.get(mceName)+1);
                } else if (mceName.length()>0) {
                    calledMethodCount.put(mceName, 1);
                }

        }
        boolean flagTrue = false;
        for(String key: calledMethodCount.keySet()){
            if(calledMethodCount.get(key)>1){
                System.out.printf("calledMethod: %s, calledTime: %d %n", key, calledMethodCount.get(key));
                flagTrue = true;
            }
        }
        return flagTrue;
    }

    /**
     * BPattern2: Test cases invoke multiple methods of the same class
     *
     * @return true or false
     */
    public static boolean IsBPattern2(MethodDeclaration md, PackageDeclaration pd){
        // System.out.println(md.findAll(MethodCallExpr.class));
        List<MethodCallExpr> methodCalls = md.findAll(MethodCallExpr.class);
        Map<String, List<String>> classCalledMethodsMap = new HashMap<String, List<String>>();
        String pdName = pd.getNameAsString();
        String shortPdName = Parser.shortenPDName(pdName);

        for (MethodCallExpr mce: methodCalls){
                // System.out.println("md: " + md.asMethodDeclaration().resolve().getQualifiedSignature());
                try{
                    String mceQualifiedSignature = mce.asMethodCallExpr().resolve().getQualifiedSignature();
                    // System.out.println( mceQualifiedSignature );
                    if (!mceQualifiedSignature.contains( shortPdName )) 
                        continue;

                    String mceName = mceQualifiedSignature.substring(0, mceQualifiedSignature.lastIndexOf('('));
                    String mceClassQualifiedSignature = mceName.substring(0, mceName.lastIndexOf('.'));
                    // System.out.println( mceClassQualifiedSignature );
                    if(classCalledMethodsMap.containsKey(mceClassQualifiedSignature)){
                        if(classCalledMethodsMap.get(mceClassQualifiedSignature).contains(mceQualifiedSignature)){
                            continue;
                        }else classCalledMethodsMap.get(mceClassQualifiedSignature).add(mceQualifiedSignature);
                    }
                    else{
                        classCalledMethodsMap.put(mceClassQualifiedSignature,  new ArrayList<String>());
                        classCalledMethodsMap.get(mceClassQualifiedSignature).add(mceQualifiedSignature);
                    }

                }catch(UnsolvedSymbolException e){
                    if (mce.getName().toString().contains("assert")) continue;
                    System.out.println( "UnsolvedSymbolException: " + e.getMessage());
                    // String mceName = mce.getName().toString();
                    System.out.println( "mce.getName(): "+mce.getName() );
                }

        }
        boolean flagTrue = false;
        for (String mceClassQualifiedSignature : classCalledMethodsMap.keySet()) {
            if( classCalledMethodsMap.get(mceClassQualifiedSignature).size() > 1 ){
                flagTrue = true;
                System.out.println( "InvocakedMethods"+ ": " + classCalledMethodsMap.get(mceClassQualifiedSignature));
            }
        }
        return flagTrue;
    }


    /**
     * Pattern1: method multiple invocations of the target method
     *
     * @return true or false
     */
    public static boolean IsPattern1(MethodDeclaration md, PackageDeclaration pd, List<String> targetMethodsUnderTest ){
        // System.out.println(md.findAll(MethodCallExpr.class));
        List<MethodCallExpr> methodCalls = md.findAll(MethodCallExpr.class);
        Map<String, Integer> calledMethodCount = new HashMap<String, Integer>();
        String pdName = pd.getNameAsString();
        String shortPdName = Parser.shortenPDName(pdName);

        for (MethodCallExpr mce: methodCalls){
                String mceName = Parser.getFullQualifiedNameOfMethodCallExpr(mce, pd);
                /*try{
                    String mceQualifiedSignature = mce.asMethodCallExpr().resolve().getQualifiedSignature();
                    // System.out.println( mceQualifiedSignature );
                    if (mceQualifiedSignature.contains( pdName )) 
                        mceName = mceQualifiedSignature.substring(0, mceQualifiedSignature.lastIndexOf('('));
                }catch(UnsolvedSymbolException e){
                    if (mce.getName().toString().contains("assert")) continue;
                    System.out.println( "UnsolvedSymbolException: " + e.getMessage());
                    // mceName = mce.getName().toString();
                    System.out.println( "mce.getName(): "+mce.getName() );
                }*/
                
                if ( calledMethodCount.containsKey(mceName) ){
                    calledMethodCount.put(mceName, calledMethodCount.get(mceName)+1);
                } else if (mceName.length()>0 && targetMethodsUnderTest.contains(mceName)) {
                    calledMethodCount.put(mceName, 1);
                }

        }
        boolean flagTrue = false;
        for(String key: calledMethodCount.keySet()){
            if(calledMethodCount.get(key)>1){
                System.out.printf("calledMethod: %s, calledTime: %d %n", key, calledMethodCount.get(key));
                flagTrue = true;
            }
        }
        return flagTrue;
    }

    /**
     * Pattern2: invocations of multiple methods from the target object
     *
     * @return true or false
     */
    public static boolean IsPattern2(MethodDeclaration md, PackageDeclaration pd, List<String> targetObjectsUnderTest ){
        // System.out.println(md.findAll(MethodCallExpr.class));
        List<MethodCallExpr> methodCalls = md.findAll(MethodCallExpr.class);
        Map<String, List<String>> classCalledMethodsMap = new HashMap<String, List<String>>();
        String pdName = pd.getNameAsString();
        String shortPdName = Parser.shortenPDName(pdName);

        for (MethodCallExpr mce: methodCalls){
                // System.out.println("md: " + md.asMethodDeclaration().resolve().getQualifiedSignature());
                try{
                    String mceQualifiedSignature = mce.asMethodCallExpr().resolve().getQualifiedSignature();
                    // System.out.println( mceQualifiedSignature );
                    if (!mceQualifiedSignature.contains( shortPdName )) 
                        continue;

                    String mceName = mceQualifiedSignature.substring(0, mceQualifiedSignature.lastIndexOf('('));
                    String mceClassQualifiedSignature = mceName.substring(0, mceName.lastIndexOf('.'));
                    // System.out.println( mceClassQualifiedSignature );
                    if(classCalledMethodsMap.containsKey(mceClassQualifiedSignature)){
                        if(classCalledMethodsMap.get(mceClassQualifiedSignature).contains(mceQualifiedSignature)){
                            continue;
                        }else classCalledMethodsMap.get(mceClassQualifiedSignature).add(mceQualifiedSignature);
                    }
                    else if(targetObjectsUnderTest.contains(mceClassQualifiedSignature)){
                        classCalledMethodsMap.put(mceClassQualifiedSignature,  new ArrayList<String>());
                        classCalledMethodsMap.get(mceClassQualifiedSignature).add(mceQualifiedSignature);
                    }


                }catch(UnsolvedSymbolException e){
                    if (mce.getName().toString().contains("assert")) continue;
                    System.out.println( "UnsolvedSymbolException: " + e.getMessage());
                    // String mceName = mce.getName().toString();
                    System.out.println( "mce.getName(): "+mce.getName() );
                }

        }
        boolean flagTrue = false;
        for (String mceClassQualifiedSignature : classCalledMethodsMap.keySet()) {
            if( classCalledMethodsMap.get(mceClassQualifiedSignature).size() > 1 ){
                flagTrue = true;
                System.out.println( "InvocakedMethods"+ ": " + classCalledMethodsMap.get(mceClassQualifiedSignature));
            }
        }
        return flagTrue;
    }


    /**
     * Pattern3: Assertion involves multipe same method invocations, 不一定是分别作为assertion的参数，可能是一个表达式。 
     * combine all assertions in one test method
     * @return true or false
     */
    public static boolean IsPattern3_combineAssertions(MethodDeclaration md, PackageDeclaration pd, List<String> targetMethodsUnderTest, List<String> targetObjectsUnderTest){
        // System.out.println(md.findAll(MethodCallExpr.class));
        List<MethodCallExpr> methodCalls = md.findAll(MethodCallExpr.class); 
        String pdName = pd.getNameAsString();

        // find assertion invocation
        // assertStmt.class 不太好用
        List<MethodCallExpr> assertionInvs = new ArrayList<MethodCallExpr>(); 
        for (MethodCallExpr mce: methodCalls){
                String mceName = mce.getName().toString();
                if ( mceName.toLowerCase().contains("assert") ){
                    assertionInvs.add(mce);
                }
        }
        // System.out.println("assertionInvs: "+ assertionInvs);

        // judge predict on involveing multipe same method invocations
        boolean flag1True = false;
        boolean flag2True = false;
        Map<String, Integer> calledMethodCount = new HashMap<String, Integer>(); // 移到外边，等于汇总所有assertions
        Map<String, List> calledClassAndMethods = new HashMap<String, List>(); // 移到外边，等于汇总所有assertions

        for (MethodCallExpr assertionInv : assertionInvs) { // 循环单个assertions
            // System.out.println("assertionInv: " + assertionInv);
            List<MethodCallExpr> methodCallsInAsserion = assertionInv.findAll(MethodCallExpr.class); 
            for (MethodCallExpr mce: methodCallsInAsserion){
                String mceName = Parser.getFullQualifiedNameOfMethodCallExpr(mce, pd);
                if (mceName.length()>0){
                    String mceClassName = mceName.substring(0, mceName.lastIndexOf("."));
                    // targetMethodsUnderTest
                    if (calledMethodCount.containsKey(mceName))
                        calledMethodCount.put(mceName, calledMethodCount.get(mceName) + 1 );
                    else if( targetMethodsUnderTest.contains(mceName))
                        calledMethodCount.put(mceName, 1 );
                    
                    // targetObjectsUnderTest
                    if (targetObjectsUnderTest.contains(mceClassName)){
                        if (calledClassAndMethods.containsKey(mceClassName)){
                            if (!calledClassAndMethods.get(mceClassName).contains(mceName))
                                calledClassAndMethods.get(mceClassName).add(mceName) ;
                        }
                        else{
                            calledClassAndMethods.put(mceClassName, new ArrayList<String>() );
                            calledClassAndMethods.get(mceClassName).add(mceName);
                        }    
                    }
                    // System.out.println(assertionInv);
                }

            }
        }

        // targetMethodsUnderTest
        for (String mceName : calledMethodCount.keySet()) {
            if( calledMethodCount.get(mceName)>1 ){
                flag1True = true;
                System.out.println("calledMethodCount: " + mceName+ ": " + calledMethodCount.get(mceName));
            }
        }
        if(flag1True) 
                System.out.println("------ " + "IsBasePattern3-1: " + true );
        // targetObjectsUnderTest
        for (String mceClassName : calledClassAndMethods.keySet()) {
            if( calledClassAndMethods.get(mceClassName).size()>1 ){
                flag2True = true;
                System.out.println("calledObject: " + mceClassName+ ": " + calledClassAndMethods.get(mceClassName));
            }
        }
        if(flag2True) 
                System.out.println("------ " + "IsBasePattern3-2: " + true );
        return flag1True || flag2True;
    }


    /**
     * Pattern3: Assertion involves multipe same method invocations, 不一定是分别作为assertion的参数，可能是一个表达式。 
     * 
     * @return true or false
     */
    public static boolean IsPattern3(MethodDeclaration md, PackageDeclaration pd, List<String> targetMethodsUnderTest, List<String> targetObjectsUnderTest){
        // System.out.println(md.findAll(MethodCallExpr.class));
        List<MethodCallExpr> methodCalls = md.findAll(MethodCallExpr.class); 
        String pdName = pd.getNameAsString();

        // find assertion invocation
        // assertStmt.class 不太好用
        List<MethodCallExpr> assertionInvs = new ArrayList<MethodCallExpr>(); 
        for (MethodCallExpr mce: methodCalls){
                String mceName = mce.getName().toString();
                if ( mceName.toLowerCase().contains("assert") ){
                    assertionInvs.add(mce);
                }
        }
        // System.out.println("assertionInvs: "+ assertionInvs);

        // judge predict on involveing multipe same method invocations
        boolean flag1True = false;
        boolean flag2True = false;
        
        for (MethodCallExpr assertionInv : assertionInvs) { // 循环单个assertions
            Map<String, Integer> calledMethodCount = new HashMap<String, Integer>(); // 若是移到for外边，等于汇总所有assertions
            Map<String, List> calledClassAndMethods = new HashMap<String, List>(); // 若是移到for外边，等于汇总所有assertions

            List<MethodCallExpr> methodCallsInAsserion = assertionInv.findAll(MethodCallExpr.class);
            // System.out.println("assertionInv: " + assertionInv);
            // System.out.println("methodCallsInAsserion: " + methodCallsInAsserion); 
            for (MethodCallExpr mce: methodCallsInAsserion){
                
                String mceName = Parser.getFullQualifiedNameOfMethodCallExpr(mce, pd);
                // System.out.println("mce: " + mce); 
                // System.out.println("mceName: " + mceName); 
                if (mceName.length()>0){
                    String mceClassName = mceName.substring(0, mceName.lastIndexOf("."));
                    // targetMethodsUnderTest
                    if (calledMethodCount.containsKey(mceName))
                        calledMethodCount.put(mceName, calledMethodCount.get(mceName) + 1 );
                    else if( targetMethodsUnderTest.contains(mceName))
                        calledMethodCount.put(mceName, 1 );
                    
                    // targetObjectsUnderTest
                    if (targetObjectsUnderTest.contains(mceClassName) && targetMethodsUnderTest.contains(mceName)){
                        if (calledClassAndMethods.containsKey(mceClassName)){
                            if (!calledClassAndMethods.get(mceClassName).contains(mceName))
                                calledClassAndMethods.get(mceClassName).add(mceName) ;
                        }
                        else{
                            calledClassAndMethods.put(mceClassName, new ArrayList<String>() );
                            calledClassAndMethods.get(mceClassName).add(mceName);
                        }    
                    }
                    // System.out.println(assertionInv);
                }

            }

            // targetMethodsUnderTest
            for (String mceName : calledMethodCount.keySet()) {
                if( calledMethodCount.get(mceName)>1 ){
                    flag1True = true;
                    System.out.println("calledMethodCount: " + mceName+ ": " + calledMethodCount.get(mceName));
                }
            }
            // targetObjectsUnderTest
            for (String mceClassName : calledClassAndMethods.keySet()) {
                if( calledClassAndMethods.get(mceClassName).size()>1 ){
                    flag2True = true;
                    System.out.println("calledClass(P3-2): " + mceClassName+ ": " + calledClassAndMethods.get(mceClassName));
                }
            }
        }
        if(flag1True) 
            System.out.println("------ " + "IsPattern3-1: " + true );
        if(flag2True) 
            System.out.println("------ " + "IsPattern3-2: " + true );
        return flag1True || flag2True;
    }


    /**
     * @Todo Pattern4: Assertion involves multipe variables which are respectively involved in same method invocations 
     * find assertion invocation
     * find objects or variables in assertion invocation
     * find statments which last involves objects or variables (use or def)
     * judge these statments whether invoking same methods
     * @return true or false
     */
    public static boolean IsPattern4(MethodDeclaration md, PackageDeclaration pd, List<String> targetMethodsUnderTest, List<String> targetObjectsUnderTest){
        // System.out.println(md.findAll(MethodCallExpr.class));
        List<MethodCallExpr> methodCalls = md.findAll(MethodCallExpr.class); 
        List<AssignExpr> assignExprs = md.findAll(AssignExpr.class); 
        String pdName = pd.getNameAsString();
        boolean flag1True = false;
        boolean flag2True = false;
    


        List<VariableDeclarationExpr> variableDeclarations = md.findAll(VariableDeclarationExpr.class); 
        // System.out.println(variableDeclarations);

        /* collect variables declarations in this method*/
        // collect variables are outputs of method invocations: 
        // ? 不知道object在这里算不算是 variables? 算的！在里面
        List<String> variableNames = new ArrayList<>();
        NodeList<Node>  variables = new NodeList<Node>();
        for (VariableDeclarationExpr vd: variableDeclarations){
            variableNames.add( vd.getVariables().get(0).getNameAsString() ); 
        }

        System.out.println("variableNames in md: "+variableNames);
    

        /* find assertion invocation */
        List<MethodCallExpr> assertionInvs = new ArrayList<MethodCallExpr>(); 
        for (MethodCallExpr mce: methodCalls){
                String mceName = mce.getName().toString();
                if ( mceName.toLowerCase().contains("assert") ){
                    assertionInvs.add(mce);
                }
        }

        /* collect varibales/objects names involved in assertion */
        for (MethodCallExpr assertionInv : assertionInvs) {
            // System.out.println("assertionInv: " +assertionInv);
            List<String> varibalesInAssertion = new ArrayList<>(); // 每一个assertion都重新记录一下
            Map<String, List<String>> variableInvlovedMethodCallMap = new HashMap(); // result: variable 及其相关的MethodCall的Map
            Map<String, List<String>> variableInvlovedMethodCall_stmtMap = new HashMap(); // key: variable+mce, value: stmt,  确保assertion中不同variable不是在同一statement中

            Map<String, List<String>> variableInvlovedObjectCallMap = new HashMap(); // result: variable 及其相关的ObjectCall的Map
            Map<String, List<String>> variableInvlovedObjectCall_methodMap = new HashMap(); // result: variable 及其相关ObjectCall中的method的Map
            Map<String, List<String>> variableInvlovedObjectCall_stmtMap = new HashMap(); // key: variable+mce, value: stmt,  确保assertion中不同variable不是在同一statement中
            
            List<NameExpr> nameExprsInAsserion = assertionInv.findAll(NameExpr.class); 
            for (NameExpr ne: nameExprsInAsserion){
                // System.out.println( ne.getNameAsString());
                if ( variableNames.contains(ne.getNameAsString()) ){ // 说明是variables
                    String variableName = ne.getNameAsString();
                    if (!varibalesInAssertion.contains(variableName))
                        varibalesInAssertion.add(variableName);                    
                }
            }
            if ( varibalesInAssertion.size()<2 ) continue; // 说明variables数量少于1
            // System.out.println( "varibalesInAssertion: " + varibalesInAssertion);
            List<Statement> statementsInMd = md.findAll(Statement.class);
            List<Statement> nonBlockStatementsInMd = new ArrayList<>();
            /* find statments which last involves objects or variables (use or def) */
            for (Statement stmt :  statementsInMd) {
                // 大语句块，直接pass了。因为会造成误报。。。TODO
                if (stmt.isBlockStmt()||stmt.isForStmt()|| stmt.isForEachStmt() || stmt.isWhileStmt()) continue; // 非单独的语句
                if (stmt.isIfStmt()) continue; // if语句的condition，不会作为statement。。。暂时只能也先pass了，感觉这样会有遗漏的。。

                // System.out.println( stmt.findAll(NameExpr.class) );
                if ( stmt.toString().contains("assert") ) continue;
                // 没有 method invocation的话，也pass
                List<MethodCallExpr> methodCalls_Instmt = stmt.findAll(MethodCallExpr.class);
                if (methodCalls_Instmt.size()==0) continue;
                // System.out.println("stmt: " +stmt);

                List<NameExpr> nameExprsInStmt = stmt.findAll(NameExpr.class);
                List<String> nameExprsInStmtAndAssert =new ArrayList<>();
                for (NameExpr ne : nameExprsInStmt) {
                    // System.out.println(ne.getNameAsString());
                    if( varibalesInAssertion.contains( ne.getNameAsString() ) ){ //该 var在assertion中
                        String variableName = ne.getNameAsString();
                        nameExprsInStmtAndAssert.add(variableName);
                        // methodCalls invloveing this variable
                    }
                }
                
                // debug+, double y2 = Math.abs(App.SinX( -X ));这种情况y2不会被当成 name
                if (stmt.findAll(VariableDeclarationExpr.class).size()>0 ){
                    // System.out.println( stmt.findAll(VariableDeclarationExpr.class).get(0).getVariables().get(0) );// findAll(MethodCallExpr.class) );
                    String declaredName = stmt.findAll(VariableDeclarationExpr.class).get(0).getVariables().get(0).getNameAsString();
                    // nameExprsInStmt.add(declaredName);
                    if( varibalesInAssertion.contains( declaredName ) )
                        nameExprsInStmtAndAssert.add(declaredName);
                }   

                // 没有变量的话，也pass，这个暂时不用。因为下面的for循环，自己会停止。
                // if (stmt.findAll(NameExpr.class).size()==0 ) continue;

                // System.out.println( "nameExprsInStmtAndAssert: " + nameExprsInStmtAndAssert);
                for (String variableName : nameExprsInStmtAndAssert) {
                        // methodCalls invloveing this variable
                        for (MethodCallExpr mce: methodCalls_Instmt){
                                String mceName = Parser.getFullQualifiedNameOfMethodCallExpr(mce, pd);
                                /*try{
                                    String mceQualifiedSignature = mce.asMethodCallExpr().resolve().getQualifiedSignature();
                                    // System.out.println( mceQualifiedSignature );
                                    if (mceQualifiedSignature.contains( pdName )) 
                                        mceName = mceQualifiedSignature.substring(0, mceQualifiedSignature.lastIndexOf('('));
                                }catch(UnsolvedSymbolException e){
                                    if (mce.getName().toString().contains("assert")) continue;
                                    System.out.println( "UnsolvedSymbolException: " + e.getMessage());
                                    // mceName = mce.getName().toString();
                                    System.out.println( "mce.getName(): "+mce.getName() );
                                }*/
                                if (mceName.length()>0){
                                    // if (variableInvlovedMethodCallMap.keySet().contains(variableName) && !variableInvlovedMethodCallMap.get(variableName).contains(mceName))
                                    
                                    String mceClassName = mceName.substring(0, mceName.lastIndexOf("."));
                                    
                                    /* targetMethodsUnderTest */
                                    if( targetMethodsUnderTest.contains(mceName)){
                                        if (variableInvlovedMethodCallMap.keySet().contains(variableName)){
                                            if (!variableInvlovedMethodCallMap.get(variableName).contains(mceName)){
                                                variableInvlovedMethodCallMap.get(variableName).add(mceName);
                                            }else{
                                                if (!variableInvlovedMethodCall_stmtMap.get(variableName+"__"+mceName).contains(stmt.toString()))
                                                variableInvlovedMethodCall_stmtMap.get(variableName+"__"+mceName).add(stmt.toString()) ;
                                            }
                                        }
                                        else{
                                            variableInvlovedMethodCallMap.put(variableName, new ArrayList( Arrays.asList(mceName) ) );
                                            variableInvlovedMethodCall_stmtMap.put(variableName+"__"+mceName, new ArrayList( Arrays.asList(stmt.toString()) ) );
                                        }
                                    }
                                    /* targetObjectsUnderTest */
                                    // targetMethodsUnderTest与targetObjectsUnderTest基本不可能同时存在的，
                                    // 所以，直接用variableInvlovedMethodCallMap和variableInvlovedMethodCall_stmtMap（代替：variableInvlovedObjectCallMap），不再新建变量存储了
                                    // 改用。。variableInvlovedObjectCallMap,variableInvlovedObjectCall_stmtMap
                                    if( targetObjectsUnderTest.contains(mceClassName) && targetMethodsUnderTest.contains(mceName)){
                                        if (variableInvlovedObjectCallMap.keySet().contains(variableName)){
                                            if (!variableInvlovedObjectCallMap.get(variableName).contains(mceClassName)){
                                                variableInvlovedObjectCallMap.get(variableName).add(mceClassName);
                                                variableInvlovedObjectCall_methodMap.get(variableName+"__"+mceClassName).add(mceName);
                                            }else{
                                                if (!variableInvlovedObjectCall_stmtMap.get(variableName+"__"+mceClassName).contains(stmt.toString()))
                                                variableInvlovedObjectCall_stmtMap.get(variableName+"__"+mceClassName).add(stmt.toString()) ;
                                            }
                                        }
                                        else{
                                            variableInvlovedObjectCallMap.put(variableName, new ArrayList( Arrays.asList(mceClassName) ) );
                                            variableInvlovedObjectCall_methodMap.put(variableName+"__"+mceClassName, new ArrayList( Arrays.asList(mceName) ) );
                                            variableInvlovedObjectCall_stmtMap.put(variableName+"__"+mceClassName, new ArrayList( Arrays.asList(stmt.toString()) ) );
                                        }
                                    }  
                                }
                        }
                        // System.out.println("variableInvlovedMethodCallMap--: "+ variableInvlovedMethodCallMap);
                        // System.out.println("stmt--: "+ stmt);
                }
            }
            

            /* 比对结果, 不同variable之间有相同的method call, 但不是在同一个stmt中。 */
            if(variableInvlovedMethodCallMap.keySet().size()>0){
                System.out.println("variableInvlovedMethodCallMap.keySet()" + ":" + variableInvlovedMethodCallMap.keySet());
                // System.out.println("variableInvlovedMethodCallMap" + ":" + variableInvlovedMethodCallMap);
                List<String> keyList = new ArrayList<>( variableInvlovedMethodCallMap.keySet() );
                for (int i = 0; i < keyList.size(); i++) {
                    String variableName1 = keyList.get(i);
                    for (int j = i+1; j < keyList.size(); j++) {
                        String variableName2 = keyList.get(j);
                        if(variableName1.equals(variableName2)) continue;
                        List<String> variableName1Value = new ArrayList<String>( variableInvlovedMethodCallMap.get(variableName1) );
                        List<String> variableName2Value = new ArrayList<String>( variableInvlovedMethodCallMap.get(variableName2) );
                        List<String> tem_Value = new ArrayList<String>( variableName1Value );
                        // System.out.println(variableName1 + "  variableName1Value: " + variableName1Value);
                        // System.out.println(variableName2 + "  variableName2Value: " + variableName2Value);
                        // System.out.println("variableName1Value.retainAll(variableName2Value): " +  variableName1Value.retainAll(variableName2Value));
                        // System.out.println("variableName1Value: " + variableName1Value);
                        // System.out.println("variableInvlovedMethodCallMap.get(variableName1): " + variableInvlovedMethodCallMap.get(variableName1));
                        tem_Value.retainAll(variableName2Value); /* 判断是否有共同的mce调用 */
                        // if (variableName1Value.size()>0){
                        //     flagTrue = true;
                        //     // System.out.println(variableName1 + "  variableName1Value: " + variableInvlovedMethodCallMap.get(variableName1));
                        //     // System.out.println(variableName2 + "  variableName2Value: " + variableInvlovedMethodCallMap.get(variableName2));
                        //     System.out.println("assertionInv: "+assertionInv);
                        //     System.out.println(variableName1 + " && " +variableName2 + ":" + variableName1Value);
                        // }
                        /* 判断共同的mce调用，是否在同一个stmt中 */
                        for (String mceName : tem_Value) {
                            List<String> variableName1_mceStmt = new ArrayList<String>( variableInvlovedMethodCall_stmtMap.get(variableName1+"__"+mceName) );
                            List<String> variableName2_mceStmt = new ArrayList<String>( variableInvlovedMethodCall_stmtMap.get(variableName2+"__"+mceName) );
                            List<String> tem_mceStmt = new ArrayList<>(variableName1_mceStmt);
                            tem_mceStmt.retainAll(variableName2_mceStmt);
                            if (tem_mceStmt.size()!=variableName1_mceStmt.size() && tem_mceStmt.size()!=variableName2_mceStmt.size()){ // 说明不是包含关系。。
                                flag1True = true;
                                System.out.println("assertionInv: "+assertionInv);
                                System.out.println(variableName1 + " && " +variableName2 + ":" + variableName1Value);
                            }
                        }
                    }
                }
            }
            // variableInvlovedObjectCallMap,variableInvlovedObjectCall_stmtMap
            if(variableInvlovedObjectCallMap.keySet().size()>0){
                System.out.println("variableInvlovedObjectCallMap.keySet()" + ":" + variableInvlovedObjectCallMap.keySet());
                // System.out.println("variableInvlovedObjectCallMap" + ":" + variableInvlovedObjectCallMap);
                List<String> keyList = new ArrayList<>( variableInvlovedObjectCallMap.keySet() );
                for (int i = 0; i < keyList.size(); i++) {
                    String variableName1 = keyList.get(i);
                    for (int j = i+1; j < keyList.size(); j++) {
                        String variableName2 = keyList.get(j);
                        if(variableName1.equals(variableName2)) continue;
                        List<String> variableName1Value = new ArrayList<String>( variableInvlovedObjectCallMap.get(variableName1) );
                        List<String> variableName2Value = new ArrayList<String>( variableInvlovedObjectCallMap.get(variableName2) );
                        List<String> tem_Value = new ArrayList<String>( variableName1Value );

                        tem_Value.retainAll(variableName2Value); /* 判断是否有共同的mce调用 */

                        /* 判断共同的mce调用，是否在同一个stmt中 */
                        for (String mceName : tem_Value) {
                            List<String> variableName1_mceStmt = new ArrayList<String>( variableInvlovedObjectCall_stmtMap.get(variableName1+"__"+mceName) );
                            List<String> variableName2_mceStmt = new ArrayList<String>( variableInvlovedObjectCall_stmtMap.get(variableName2+"__"+mceName) );
                            List<String> tem_mceStmt = new ArrayList<>(variableName1_mceStmt);
                            tem_mceStmt.retainAll(variableName2_mceStmt);
                            if (tem_mceStmt.size()!=variableName1_mceStmt.size() && tem_mceStmt.size()!=variableName2_mceStmt.size()){ // 说明不是包含关系。。
                                flag2True = true;
                                System.out.println("assertionInv: "+assertionInv);
                                System.out.println(variableName1 + " && " +variableName2 + ":" + variableName1Value);

                                String  mceClassName = variableName1Value.get(0); // 简单点，单纯认为有一个mceClassName，就取第一个吧。。
                                List<String> variableName1InvlovedObjectCall_method = variableInvlovedObjectCall_methodMap.get(variableName1+"__"+mceClassName);
                                List<String> variableName2InvlovedObjectCall_method = variableInvlovedObjectCall_methodMap.get(variableName2+"__"+mceClassName);
                                variableName1InvlovedObjectCall_method.addAll(variableName2InvlovedObjectCall_method);
                                System.out.println("calledClass(P4-2):" + mceClassName + ": " + variableName1InvlovedObjectCall_method);

                            }
                        }
                    }
                }
            }
        }
        if(flag1True) 
            System.out.println("------ " + "IsPattern4-1: " + true );
        if(flag2True) 
            System.out.println("------ " + "IsPattern4-2: " + true );
        return flag1True || flag2True;
    }

    /**
     * pattern3+4: one execution directly in assertion, another execution is in the form of variable in assertion
     * @return true or false
     */
    public static boolean IsPattern35(MethodDeclaration md, PackageDeclaration pd, List<String> targetMethodsUnderTest, List<String> targetObjectsUnderTest){
        List<MethodCallExpr> methodCalls = md.findAll(MethodCallExpr.class); 
        String pdName = pd.getNameAsString();

        /* SETUP: find assertion invocation */
        // assertStmt.class 不太好用
        List<MethodCallExpr> assertionInvs = new ArrayList<MethodCallExpr>(); 
        for (MethodCallExpr mce: methodCalls){
                String mceName = mce.getName().toString();
                if ( mceName.toLowerCase().contains("assert") ){
                    assertionInvs.add(mce);
                }
        }
        // System.out.println("assertionInvs: "+ assertionInvs);

        List<VariableDeclarationExpr> variableDeclarations = md.findAll(VariableDeclarationExpr.class); 
        /* SETUP: collect variables declarations in this method*/
        List<String> variableNames = new ArrayList<>();
        NodeList<Node>  variables = new NodeList<Node>();
        for (VariableDeclarationExpr vd: variableDeclarations){
            variableNames.add( vd.getVariables().get(0).getNameAsString() ); 
        }


        // judge predict on involveing multipe same method invocations
        boolean flag1True = false;
        boolean flag2True = false;
        
        for (MethodCallExpr assertionInv : assertionInvs) { // 循环单个assertions
            /* P3: one execution directly in assertion */
            Map<String, Integer> calledMethodCount = new HashMap<String, Integer>(); // 若是移到for外边，等于汇总所有assertions
            Map<String, List> calledClassAndMethods = new HashMap<String, List>(); // 若是移到for外边，等于汇总所有assertions
            List<MethodCallExpr> methodCallsInAsserion = assertionInv.findAll(MethodCallExpr.class);
            // System.out.println("assertionInv: " + assertionInv);
            // System.out.println("methodCallsInAsserion: " + methodCallsInAsserion); 
            for (MethodCallExpr mce: methodCallsInAsserion){
                
                String mceName = Parser.getFullQualifiedNameOfMethodCallExpr(mce, pd);
                // System.out.println("mce: " + mce); 
                // System.out.println("mceName: " + mceName); 
                if (mceName.length()>0){
                    String mceClassName = mceName.substring(0, mceName.lastIndexOf("."));
                    // targetMethodsUnderTest
                    if (calledMethodCount.containsKey(mceName))
                        calledMethodCount.put(mceName, calledMethodCount.get(mceName) + 1 );
                    else if( targetMethodsUnderTest.contains(mceName))
                        calledMethodCount.put(mceName, 1 );
                    
                    // targetObjectsUnderTest
                    if (targetObjectsUnderTest.contains(mceClassName) && targetMethodsUnderTest.contains(mceName)){
                        if (calledClassAndMethods.containsKey(mceClassName)){
                            if (!calledClassAndMethods.get(mceClassName).contains(mceName))
                                calledClassAndMethods.get(mceClassName).add(mceName) ;
                        }
                        else{
                            calledClassAndMethods.put(mceClassName, new ArrayList<String>() );
                            calledClassAndMethods.get(mceClassName).add(mceName);
                        }    
                    }
                    // System.out.println(assertionInv);
                }

            }


            /* P4: another execution is in the form of variable in assertion */
            // 参考Pattern4的实现 
            List<String> varibalesInAssertion = new ArrayList<>(); // 每一个assertion都重新记录一下
            Map<String, List<String>> variableInvlovedMethodCallMap = new HashMap(); // result: variable 及其相关的MethodCall的Map
            Map<String, List<String>> variableInvlovedMethodCall_stmtMap = new HashMap(); // key: variable+mce, value: stmt,  确保assertion中不同variable不是在同一statement中
            Map<String, List<String>> variableInvlovedObjectCallMap = new HashMap(); // result: variable 及其相关的ObjectCall的Map
            Map<String, List<String>> variableInvlovedObjectCall_methodMap = new HashMap(); // result: variable 及其相关ObjectCall中的method的Map
            Map<String, List<String>> variableInvlovedObjectCall_stmtMap = new HashMap(); // key: variable+mce, value: stmt,  确保assertion中不同variable不是在同一statement中
            List<NameExpr> nameExprsInAsserion = assertionInv.findAll(NameExpr.class); 
            for (NameExpr ne: nameExprsInAsserion){
                // System.out.println( ne.getNameAsString());
                if ( variableNames.contains(ne.getNameAsString()) ){ // 说明是variables
                    String variableName = ne.getNameAsString();
                    if (!varibalesInAssertion.contains(variableName))
                        varibalesInAssertion.add(variableName);                    
                }
            }
            List<Statement> statementsInMd = md.findAll(Statement.class);
            List<Statement> nonBlockStatementsInMd = new ArrayList<>();
            /* find statments which last involves objects or variables (use or def) */
            for (Statement stmt :  statementsInMd) {
                // 大语句块，直接pass了。因为会造成误报。。。TODO
                if (stmt.isBlockStmt()||stmt.isForStmt()|| stmt.isForEachStmt() || stmt.isWhileStmt()) continue; // 非单独的语句
                if (stmt.isIfStmt()) continue; // if语句的condition，不会作为statement。。。暂时只能也先pass了，感觉这样会有遗漏的。。
                if ( stmt.toString().contains("assert") ) continue;
                // 没有 method invocation的话，也pass
                List<MethodCallExpr> methodCalls_Instmt = stmt.findAll(MethodCallExpr.class);
                if (methodCalls_Instmt.size()==0) continue;

                List<NameExpr> nameExprsInStmt = stmt.findAll(NameExpr.class);
                List<String> nameExprsInStmtAndAssert =new ArrayList<>();
                for (NameExpr ne : nameExprsInStmt) {
                    if( varibalesInAssertion.contains( ne.getNameAsString() ) ){ //该 var在assertion中
                        String variableName = ne.getNameAsString();
                        nameExprsInStmtAndAssert.add(variableName);
                        // methodCalls invloveing this variable
                    }
                }
                
                // debug+, double y2 = Math.abs(App.SinX( -X ));这种情况y2不会被当成 name
                if (stmt.findAll(VariableDeclarationExpr.class).size()>0 ){
                    String declaredName = stmt.findAll(VariableDeclarationExpr.class).get(0).getVariables().get(0).getNameAsString();
                    if( varibalesInAssertion.contains( declaredName ) )
                        nameExprsInStmtAndAssert.add(declaredName);
                }   

                // System.out.println( "nameExprsInStmtAndAssert: " + nameExprsInStmtAndAssert);
                for (String variableName : nameExprsInStmtAndAssert) {
                        // methodCalls invloveing this variable
                        for (MethodCallExpr mce: methodCalls_Instmt){
                                String mceName = Parser.getFullQualifiedNameOfMethodCallExpr(mce, pd);
                                if (mceName.length()>0){ 
                                    String mceClassName = mceName.substring(0, mceName.lastIndexOf("."));
                                    
                                    /* targetMethodsUnderTest */
                                    if( targetMethodsUnderTest.contains(mceName)){
                                        if (variableInvlovedMethodCallMap.keySet().contains(variableName)){
                                            if (!variableInvlovedMethodCallMap.get(variableName).contains(mceName)){
                                                variableInvlovedMethodCallMap.get(variableName).add(mceName);
                                            }else{
                                                if (!variableInvlovedMethodCall_stmtMap.get(variableName+"__"+mceName).contains(stmt.toString()))
                                                variableInvlovedMethodCall_stmtMap.get(variableName+"__"+mceName).add(stmt.toString()) ;
                                            }
                                        }
                                        else{
                                            variableInvlovedMethodCallMap.put(variableName, new ArrayList( Arrays.asList(mceName) ) );
                                            variableInvlovedMethodCall_stmtMap.put(variableName+"__"+mceName, new ArrayList( Arrays.asList(stmt.toString()) ) );
                                        }
                                    }
                                    /* targetObjectsUnderTest */
                                    if( targetObjectsUnderTest.contains(mceClassName) && targetMethodsUnderTest.contains(mceName)){
                                        if (variableInvlovedObjectCallMap.keySet().contains(variableName)){
                                            if (!variableInvlovedObjectCallMap.get(variableName).contains(mceClassName)){
                                                variableInvlovedObjectCallMap.get(variableName).add(mceClassName);
                                                variableInvlovedObjectCall_methodMap.get(variableName+"__"+mceClassName).add(mceName);
                                            }else{
                                                if (!variableInvlovedObjectCall_stmtMap.get(variableName+"__"+mceClassName).contains(stmt.toString()))
                                                variableInvlovedObjectCall_stmtMap.get(variableName+"__"+mceClassName).add(stmt.toString()) ;
                                            }
                                        }
                                        else{
                                            variableInvlovedObjectCallMap.put(variableName, new ArrayList( Arrays.asList(mceClassName) ) );
                                            variableInvlovedObjectCall_methodMap.put(variableName+"__"+mceClassName, new ArrayList( Arrays.asList(mceName) ) );
                                            variableInvlovedObjectCall_stmtMap.put(variableName+"__"+mceClassName, new ArrayList( Arrays.asList(stmt.toString()) ) );
                                        }
                                    }  
                                }
                        }
                }
            }




            // targetMethodsUnderTest
            for (String mceName : calledMethodCount.keySet()) {// if P3's mce in P4's involved method?
                // 判断在不在，以及是哪个variable及mce
                for ( String variableName__mceName : variableInvlovedMethodCall_stmtMap.keySet()) {
                    String VmceName = variableName__mceName.split("__")[1];
                    if(VmceName.contains(mceName)){
                        System.out.println("(35-1) assertion: " +assertionInv.toString());
                        System.out.println("(35-1) variableName__invokedMethod: " + variableName__mceName);
                        flag1True = true;
                    }
                }
            }
            // targetObjectsUnderTest
            for (String mceClassName : calledClassAndMethods.keySet()) {
                // 判断在不在，以及是哪个variable及mce
                for ( String variableName__mceClassName : variableInvlovedObjectCall_stmtMap.keySet()) {
                    String VmceClassName = variableName__mceClassName.split("__")[1];
                    if(VmceClassName.contains(mceClassName)){
                        System.out.println("(35-2) assertion: " +assertionInv.toString());
                        System.out.println("(35-2) variableName__mceClassName: " + variableName__mceClassName);
                        System.out.println("variable invoked method stmt: " + variableInvlovedObjectCall_stmtMap.get(variableName__mceClassName));
                        System.out.println("directly invoked method : " + calledClassAndMethods.get(mceClassName));
                        flag2True = true;
                    }
                }
            }
        }
        if(flag1True) 
            System.out.println("------ " + "IsPattern35-1: " + true );
        if(flag2True) 
            System.out.println("------ " + "IsPattern35-2: " + true );
        return flag1True || flag2True;
    }


    /**
     * PropertyOne, method invocation: The test case has at least two invocations to methods belonging to the same class
     * PropertyTwo, Assertions: The test case has at least one assertion that predicates on the relation of invocations of methods belonging to the same class
     * procedure: 
     *  1. find multiple method invocations of the same class
     *  for each target class from setp1
     *      2. analyze the inputs and outputs of method invocation 
     *      - input: object receiver (to analyze whether it is accessed), input parameters 
     *      - output: returned value, object receiver (updated?), input object references (updated?) 
     *      3. check the assertion predicating on relation (with relation templates) 
     * @return true or false
     * @throws IOException
     */
    public static boolean IsPatternMA(MethodDeclaration md, PackageDeclaration pd) throws IOException, ClassNotFoundException, CloneNotSupportedException{
        List<MethodCallExpr> methodCalls = md.findAll(MethodCallExpr.class);
        Map<String, List<String>> classCalledMethodsMap = new HashMap<String, List<String>>(); 
        List<String> satisfiedMethodsOnPropertyOne = new ArrayList<>(); List<String> satisfiedClassesOnPropertyOne = new ArrayList<>();
        String pdName = pd.getNameAsString();
        String shortPdName = Parser.shortenPDName(pdName);
        HashMap<MethodCallExpr, String> mce_mceQualifiedSignatureMap = new HashMap<MethodCallExpr, String>();
        HashMap<String, MethodCallExpr> mceQualifiedSignature_mceMap = new HashMap<String, MethodCallExpr>();

        HashMap<String, List<methodInvocation>> targetClass_methodInvocationObjectsMap = new HashMap<String, List<methodInvocation>>();// PropertyOne's outputs, PropertyTwo's inputs

        List<String> getterMethodsFQS = new ArrayList<>();
        List<String> setterMethodsFQS = new ArrayList<>();

        /* 1. find multiple method invocations of the same class  */
        boolean flagMethodInvocation = false;// flag of PropertyOne's outputs
        for (MethodCallExpr mce: methodCalls){
                try{
                    String mceQualifiedSignature = mce.asMethodCallExpr().resolve().getQualifiedSignature();
                    if (!mceQualifiedSignature.contains( shortPdName )) continue; // CONDITION: invoked method under the package?
                    if (mceQualifiedSignature.toLowerCase().contains( "assert" )) continue; // CONDITION: invoked method should not be assert
                    /* discount setters and getters*/
                    if (Parser.isGetter(mce)){getterMethodsFQS.add(mceQualifiedSignature);continue;}
                    if (Parser.isSetter(mce)){setterMethodsFQS.add(mceQualifiedSignature);continue;}
                    
                    // classCalledMethodsMap
                    String mceName = mceQualifiedSignature.substring(0, mceQualifiedSignature.lastIndexOf('(')); // method fullname
                    String mceClassQualifiedSignature = mceName.substring(0, mceName.lastIndexOf('.')); // class fullname

                    // filter: Test class 不考虑。例如： InvokedClass: org.nem.core.node.NisNodeInfoTest
                    if(mceClassQualifiedSignature.contains(".test") || mceClassQualifiedSignature.contains(".test")|| mceClassQualifiedSignature.endsWith("Test") ) 
                        continue;
                
                    if(classCalledMethodsMap.containsKey(mceClassQualifiedSignature)){
                        classCalledMethodsMap.get(mceClassQualifiedSignature).add(mceQualifiedSignature);
                    }
                    else{
                        classCalledMethodsMap.put(mceClassQualifiedSignature,  new ArrayList<String>());
                        classCalledMethodsMap.get(mceClassQualifiedSignature).add(mceQualifiedSignature);
                    }
                    // mce_mceQualifiedSignatureMap, mceQualifiedSignature_mceMap
                    // mce_mceQualifiedSignatureMap.put(mce, mceQualifiedSignature);
                    // mceQualifiedSignature_mceMap.put(mceQualifiedSignature, mce);
                }catch(UnsolvedSymbolException e){
                    if (mce.getName().toString().contains("assert")) continue;
                    System.out.println( "UnsolvedSymbolException: " + e.getMessage());
                    System.out.println( "mce.getName(): "+mce.getName() );
                }
        }
        for (String mceClassQualifiedSignature : classCalledMethodsMap.keySet()) {
            if( classCalledMethodsMap.get(mceClassQualifiedSignature).size() > 1 ){
                // sysout result
                flagMethodInvocation = true;
                System.out.println( "InvokedClass"+ ": " + mceClassQualifiedSignature + "\nInvokedMethods"+ ": " + classCalledMethodsMap.get(mceClassQualifiedSignature));
                // data preparation for PropertyTwo 
                if(!satisfiedClassesOnPropertyOne.contains(mceClassQualifiedSignature)) satisfiedClassesOnPropertyOne.add(mceClassQualifiedSignature);
                for (String satisfiedMethod : classCalledMethodsMap.get(mceClassQualifiedSignature)) {
                    // satisfiedMethod是mceQualifiedSignature
                    if(!satisfiedMethodsOnPropertyOne.contains(satisfiedMethod)){
                        String satisfiedMethodName = satisfiedMethod.substring(0, satisfiedMethod.lastIndexOf('(')); // signature -> name
                        satisfiedMethodsOnPropertyOne.add(satisfiedMethodName);
                    } 
                } 
            }
        }
        /* 2. analyze the inputs and outputs of method invocation */
        /* generate targetClass_methodInvocationObjectsMap, based on satisfiedClassesOnPropertyOne */
        for (MethodCallExpr mce: methodCalls){
            try{
                String mceQualifiedSignature = mce.asMethodCallExpr().resolve().getQualifiedSignature();
                if (!mceQualifiedSignature.contains( shortPdName )) continue; // CONDITION: invoked method under the package?
                if (mceQualifiedSignature.toLowerCase().contains( "assert" )) continue; // CONDITION: invoked method should not be assert/* discount setters and getters*/
                if (getterMethodsFQS.contains(mceQualifiedSignature) || setterMethodsFQS.contains(mceQualifiedSignature)){continue;}
   
                String mceName = mceQualifiedSignature.substring(0, mceQualifiedSignature.lastIndexOf('(')); // method fullname
                String mceClassQualifiedSignature = mceName.substring(0, mceName.lastIndexOf('.')); // class fullname
                if(satisfiedClassesOnPropertyOne.contains(mceClassQualifiedSignature)){
                    if(!targetClass_methodInvocationObjectsMap.containsKey(mceClassQualifiedSignature)){
                        targetClass_methodInvocationObjectsMap.put(mceClassQualifiedSignature, new ArrayList<methodInvocation>());
                    }
                    targetClass_methodInvocationObjectsMap.get(mceClassQualifiedSignature).add(new methodInvocation(mce, mceQualifiedSignature));
                }
            }catch(UnsolvedSymbolException e){
            }
        }
        
        System.out.println("============ PropertyOne, Method Invocations: " + flagMethodInvocation);
        if(!flagMethodInvocation) return flagMethodInvocation;
        

        /* 3. check the assertion predicating on relation (with relation templates)  */
        boolean flagAssertion = false; // flag of PropertyTwo's outputs
        for (String mceClassQualifiedSignature : targetClass_methodInvocationObjectsMap.keySet()) {
            List<methodInvocation> methodInvocations = targetClass_methodInvocationObjectsMap.get(mceClassQualifiedSignature);
            flagAssertion = checkOutputRelation(md,pd,mceClassQualifiedSignature, methodInvocations);
            if(flagAssertion){
                System.out.println("============ PropertyTwo, Assertion: " + flagAssertion);
            }
        }

        // 2022.09.10: 之前的版本，已抛弃
        /* 
        // 策略: 调用原P3和P4的内容。satisfiedMethodsOnPropertyOne, satisfiedClassesOnPropertyOne 作为targetMethodsUnderTest, targetObjectsUnderTest参数传入。
        boolean IsPattern3 = IsPattern3(md,pd,satisfiedMethodsOnPropertyOne, satisfiedClassesOnPropertyOne);
        boolean IsPattern4 = IsPattern4(md,pd,satisfiedMethodsOnPropertyOne, satisfiedClassesOnPropertyOne);
        flagAssertion = IsPattern3||IsPattern4;
        // boolean IsPattern35 = IsPattern35(md,pd,satisfiedMethodsOnPropertyOne, satisfiedClassesOnPropertyOne);
        // flagAssertion = IsPattern3||IsPattern4||IsPattern35;

        if (flagAssertion){
            System.out.println("============ PropertyTwo, Assertion: " + flagAssertion);
            if (IsPattern3) System.out.println("Method Invocations directly in assertion.");
            if (IsPattern4) System.out.println("Method Invocations' outputs (variables) in assertion.");
            // if (IsPattern35) System.out.println("Method Invocations' outputs (stmt&variable) in assertion.");
        }
        */
        return flagAssertion;
    }
    
    static void relationAssertionOutputDirInitialize(MethodDeclaration md) throws UnknownHostException{
        Config config = new Config();
        String FQN_testCaseUnderAnalysis = md.resolve().getQualifiedName();
        String pojName = FileUtil.findPojName( MTIdentifier.FILE_PATH, Config.SPLITE_STR);
        String pojDir = FileUtil.findPojDir( MTIdentifier.FILE_PATH, Config.SPLITE_STR);

        // 创建output文件夹
        FileUtil.folderExistingIfNotCreate(pojDir + Config.MTidentifier_Fold_path_in_PUA.split("/")[0]);
        FileUtil.folderExistingIfNotCreate(pojDir + Config.MTidentifier_Fold_path_in_PUA); 
        String relationAssertionOutputInPojDir = pojDir + Config.MTidentifier_Fold_path_in_PUA;// 1
        String dirRelationAssertion; 
        dirRelationAssertion =  config.AUTOMR_EXPERIMENTAL_POJS_MTIDENTIFIER_RELATIONASSRTION_OUTPUT_DIR + pojName +"/";
        FileUtil.folderExistingIfNotCreate(config.AUTOMR_EXPERIMENTAL_POJS_MTIDENTIFIER_RELATIONASSRTION_OUTPUT_DIR);
        FileUtil.folderExistingIfNotCreate(dirRelationAssertion); 
        String relationAssertionOutputInDataDir = dirRelationAssertion;// 2

        // 清空历史遗留 relationAssertions 文件
        List<String> fileNames = new ArrayList<>();
        FileUtil.findFileList(new File(relationAssertionOutputInPojDir), fileNames);
        FileUtil.findFileList(new File(relationAssertionOutputInDataDir), fileNames);
        for (String filePath : fileNames) {
            if(filePath.contains("/"+FQN_testCaseUnderAnalysis))
                FileUtil.deleteFile(filePath);
        }

    }

    /* 
     * checkOutputRelation according to templates:
     * ∃Vi∈⟨P(x1), · · · , P(xi), · · · , P(xn)⟩  ∃Vj∈⟨x1, · · · , xj , · · · , xn, P(x1), · · · , P(xj), · · · , P(xn)⟩  Λ  i≠j
     * Template1:
     *  - ASSERT( STMT1, STMT2 ): Statements STMT1 and STMT2 contain Vi and Vj respectively.
     * Template2:
     *  - ASSERT( BoolSTMT ): BoolSTMT indicates a boolean statement, where Vi and Vj  are operated by 
     *    i) numerical operators (i.e., +,-, *, / , %, ==, <, >, <= , >=, !=), 
     *    ii) boolean operators (i.e., AND, OR, XOR, EXOR), or 
     *    ii) user-defined methods which return boolean value.
     *     or
     *    BoolSTMT contain Vi and Vj. [220926: 目前是这个implementation]
    */
    static boolean checkOutputRelation(MethodDeclaration md, PackageDeclaration pd, String mceClassQualifiedSignature, List<methodInvocation> methodInvocations) throws IOException, ClassNotFoundException, CloneNotSupportedException{
        boolean validOutputRelation = false;
        // find assertion invocation
        // assertStmt.class 不太好用
        List<MethodCallExpr> assertionInvs = new ArrayList<MethodCallExpr>(); 
        for (MethodCallExpr mce: md.findAll(MethodCallExpr.class)){
                String mceName = mce.getName().toString();
                if ( mceName.toLowerCase().contains("assert") ){
                    assertionInvs.add(mce);
                }
        }

        relationAssertionOutputDirInitialize(md);// 创建文件夹， 清空历史。。。
        /* 循环单个assertion */ 
        for (MethodCallExpr assertionInv : assertionInvs) { 
            String assertSTMT = assertionInv.toString();
            // 过滤无效assert
            // assertSTMT 包含两个以上assert { assert(); asert(); asert(); ....}
            if(assertSTMT.split("assert").length >3) continue;


            // assert type
            if(assertSTMT.contains("assertEqual") || assertSTMT.contains("assertNotEqual") || assertSTMT.contains("failNotEqual") || assertSTMT.contains("assertSame") || assertSTMT.contains("assertNotSame") || assertSTMT.contains("failNotSame") || assertSTMT.contains("assertArrayEquals") || assertSTMT.contains("assertIterableEquals") || assertSTMT.contains("assertLinesMatch") || assertSTMT.contains("assertThat")){ //
                // there is no fix order of "msg", "actual", "expect" in these assertions. so we check all the arguments, and find at least two arguemetns contain outputs.
                NodeList argumentsOfassert = assertionInv.getArguments();
                // HashMap<Node, List<methodInvocation>> argument_containedInputOrOutput;
                HashMap<Node, List<String>> argument_containedInputOrOutput = new HashMap<Node, List<String>>();
                HashMap<methodInvocation, List<Node>> mtdInvocation_involvedByArguments = new HashMap<methodInvocation, List<Node>>();
                // filter message parameter
                // 因为不确定 message是第一还是倒一
                // solution： 取第一、倒一；如果是string，且和中间值不一样，则删
                if( argumentsOfassert.size()>2 ){
                    ResolvedMethodDeclaration resolvedAssertionInv = Parser.getResolvedMethodCallExpr(assertionInv);
                    if(resolvedAssertionInv!=null){
                        int numOfParameter = resolvedAssertionInv.getNumberOfParams();
                        // 因为不确定 message是第一还是倒一
                        // solution： 取第一、倒一；如果是string，且和中间值不一样，则删
                        String firstParaType = resolvedAssertionInv.getParam(0).getType().describe();
                        String middleParaType = resolvedAssertionInv.getParam(1).getType().describe();
                        String lastParaType = resolvedAssertionInv.getLastParam().getType().describe();
                        if(firstParaType.equals("java.lang.String") && !middleParaType.equals("java.lang.String")){
                            System.out.println("argumentsOfassert.remove: " + argumentsOfassert.get(0));
                            argumentsOfassert.remove(0);
                        }
                        else if(lastParaType.equals("java.lang.String") && !middleParaType.equals("java.lang.String")){
                            System.out.println("argumentsOfassert.remove: " + argumentsOfassert.get(argumentsOfassert.size()-1));
                            argumentsOfassert.remove(argumentsOfassert.size()-1);
                        }

                    }
                    
                }
                
                // get 
                List< Node> exprsOfOperator = new ArrayList<>();
                for (int i = 0; i < argumentsOfassert.size(); i++) {
                    Node argument = argumentsOfassert.get(i); 
                    if(argument.findAll(SimpleName.class).size()<1) continue;
                    exprsOfOperator.add(argument);
                }

                // judge
                HashMap<Node, HashMap<String, List<String>>> expsInvolvedInputOrOutputs =  analyzeInvolvedInputOrOutputs(exprsOfOperator, methodInvocations, assertionInv);
                Boolean containOutputRelation = satisfyOutputRelation(expsInvolvedInputOrOutputs, methodInvocations, md, assertionInv);
                if(containOutputRelation){
                    System.out.println("LOG: positive assertion! for assertEqual category: " + assertSTMT);
                    System.out.println();
                    validOutputRelation = true; // break;
                }

                /* old version before 2022 10 04
                // 循环assertion中的单个arguments
                for (int i = 0; i < argumentsOfassert.size(); i++) {
                    Node argument = argumentsOfassert.get(i); 
                    if(argument.findAll(SimpleName.class).size()<1) continue; // SimpleName.class, find: variable + invoked method's name ;  //说明这个arguement压根没variable/ method's name，不用浪费时间啦
                    
                    
                    argument_containedInputOrOutput.put(argument, new ArrayList<>());
                    String argumentStr = argument.toString();
                    List<String> varibleNamesinArgument = new ArrayList<>();
                    List<String> mceStringInArgument = new ArrayList<>();

                    List<NameExpr> variblesinArgument  = argument.findAll(NameExpr.class); // SimpleName.class, find: variable + invoked method's name ;      NameExpr.class, find: variable's name
                    for (NameExpr varibles : variblesinArgument) {
                        varibleNamesinArgument.add(varibles.toString());
                    }

                    List<MethodCallExpr> mcesinArgument  = argument.findAll(MethodCallExpr.class); 
                    for (MethodCallExpr mce : mcesinArgument) {
                        mceStringInArgument.add(mce.toString());
                    }
                    
                    // methodInvocation.outputExpressions中，会有: 正常的variables + mceString (which is invoked in assert)  
                    // - 正常variables (Str中无 '.' 无'(' ), 判断是否在 varibleOrMethods list
                    // - mceString (Str中有 '.' 有'(' ), 判断是否 contained by  argumentStr
                    // 
                    for (int j = 0; j < methodInvocations.size(); j++) {
                        methodInvocation mtdInvocation = methodInvocations.get(j);
                        if(!mtdInvocation_involvedByArguments.keySet().contains(mtdInvocation)) mtdInvocation_involvedByArguments.put(mtdInvocation, new ArrayList<Node>());
                        // mtdInvocation.outputExpressions;
                        // mtdInvocation.inputExpressions;
                        
                        // 交集
                        // https://juejin.cn/post/6844903833726894093
                        List<String> intersectionInputExpressions = mtdInvocation.inputExpressions.stream().filter(item -> varibleNamesinArgument.contains(item)).collect(toList()); 
                        List<String> intersectionOutputExpressions = mtdInvocation.outputExpressions.stream().filter(item -> varibleNamesinArgument.contains(item)).collect(toList()); 
                        List<String> intersectionOutputExpressions2 = mtdInvocation.outputExpressions.stream().filter(item -> mceStringInArgument.contains(item)).collect(toList()); 
                        
                        // note bug: softly.assertThat(mailQueueViewConfiguration.getBucketCount()), argument_containedInputOrOutput: mailQueueViewConfiguration.getBucketCount(): [INPUT: mailQueueViewConfiguration, OUTPUT: mailQueueViewConfiguration.getBucketCount(), INPUT: mailQueueViewConfiguration, INPUT: mailQueueViewConfiguration]
                        if (intersectionInputExpressions.size()>0)
                            for (String intersectionInput : intersectionInputExpressions)
                                argument_containedInputOrOutput.get(argument).add("INPUT: " +intersectionInput);
                                // if(!argument_containedInputOrOutput.get(argument).toString().contains(intersectionInput.toString()) && argument_containedInputOrOutput.get(argument).toString().length() + intersectionInput.toString().length() < argument.toString().length() + "[]".length() + " OUTPUT: ".length()*argument_containedInputOrOutput.get(argument).size() ) // 说明，该项element 不在另一个element中，且 one element + another element < len( argument )
                                    // argument_containedInputOrOutput.get(argument).add("INPUT: " +intersectionInput);
                        if (intersectionOutputExpressions.size()>0) 
                            for (String intersectionOutput : intersectionOutputExpressions)
                                argument_containedInputOrOutput.get(argument).add("OUTPUT: " +intersectionOutput);
                        if (intersectionOutputExpressions2.size()>0) 
                            for (String OutputExpressions : intersectionOutputExpressions2)
                                argument_containedInputOrOutput.get(argument).add("OUTPUT: " +OutputExpressions);
                        
                        if ( intersectionInputExpressions.size() + intersectionOutputExpressions.size() + intersectionOutputExpressions2.size()>0 )
                        mtdInvocation_involvedByArguments.get(mtdInvocation).add(argument);
                    }
                }

                // argument_containedInputOrOutput: 至少有两个arguments，不为空 -》 说明involved ouputs/inputs 在两边
                // argument_containedInputOrOutput: 至少有一个arguments中有 "OUTPUT__" -》 说明involved ouputs/inputs, 至少有一个是output
                // mtdInvocation_involvedByArguments： 至少有两个mtdInvocation，不为空 -》 说明 有多个method invocation 被涉及.
                int count_argument = 0; int count_mtdInvocation = 0; int count_involvedOutput = 0;
                for (Node argument : argument_containedInputOrOutput.keySet())
                    if(argument_containedInputOrOutput.get(argument).size()>0){
                        count_argument++;
                        if( argument_containedInputOrOutput.get(argument).toString().contains( "OUTPUT: " ) )
                            count_involvedOutput++;
                    } 
                for (methodInvocation mtdInvocation : mtdInvocation_involvedByArguments.keySet())
                    if(mtdInvocation_involvedByArguments.get(mtdInvocation).size()>0) count_mtdInvocation++;
                if( count_argument>1 &&  count_mtdInvocation>1 && count_involvedOutput>0){
                    System.out.println("assertSTMT: " + assertSTMT);
                    for (Node argument : argument_containedInputOrOutput.keySet()){
                        if(argument_containedInputOrOutput.get(argument).size()>0){
                            System.out.println( "argument_containedInputOrOutput: " + argument + ": " + argument_containedInputOrOutput.get(argument) );
                        }
                    }
                    for (methodInvocation mtdInvocation : mtdInvocation_involvedByArguments.keySet()){
                        if(mtdInvocation_involvedByArguments.get(mtdInvocation).size()>0){
                            System.out.println( "mtdInvocation_involvedByArguments: " + mtdInvocation.mce.toString() + ": " + mtdInvocation_involvedByArguments.get(mtdInvocation) );
                        }
                    }
                    System.out.println("positive assertion! for assertEqual");
                    validOutputRelation = true; break;
                }
    
                // System.out.println("assertionInv: " + assertionInv);
                // System.out.println("assertionInv getArguments: " + assertionInv.getArguments());
                // System.out.println("assertionInv getChildNodes: " + assertionInv.getChildNodes());
                */
            }
            else if(assertSTMT.contains("assertTrue") || assertSTMT.contains("assertFalse")){
                // there is no fix order of "msg", "actual", "expect" in these assertions. so we check all the arguments, and find at least two arguemetns contain outputs.
                NodeList argumentsOfassert = assertionInv.getArguments();
                // HashMap<Node, List<methodInvocation>> argument_containedInputOrOutput;
                HashMap<Node, List<String>> argument_containedInputOrOutput = new HashMap<Node, List<String>>();
                HashMap<methodInvocation, List<Node>> mtdInvocation_involvedByArguments = new HashMap<methodInvocation, List<Node>>();
                // filter message parameter
                // 因为不确定 message是第一还是倒一
                // solution： 取第一、倒一；如果是string，则删
                if( argumentsOfassert.size()>1 ){
                    ResolvedMethodDeclaration resolvedAssertionInv = Parser.getResolvedMethodCallExpr(assertionInv);
                    if(resolvedAssertionInv!=null){
                        int numOfParameter = resolvedAssertionInv.getNumberOfParams();
                        // 因为不确定 message是第一还是倒一
                        // solution： 取第一、倒一；如果是string，则删
                        String firstParaType = resolvedAssertionInv.getParam(0).getType().describe();
                        String lastParaType = resolvedAssertionInv.getLastParam().getType().describe();
                        if(firstParaType.equals("java.lang.String")){
                            System.out.println("argumentsOfassert.remove: " + argumentsOfassert.get(0));
                            argumentsOfassert.remove(0);
                        }
                        else if(lastParaType.equals("java.lang.String")){
                            System.out.println("argumentsOfassert.remove: " + argumentsOfassert.get(argumentsOfassert.size()-1));
                            argumentsOfassert.remove(argumentsOfassert.size()-1);
                        }

                    }
                    
                }
                
                /* old version before 2022 10 04
                // 循环assertion中的当个arguments
                for (int i = 0; i < argumentsOfassert.size(); i++) {
                    Node argument = argumentsOfassert.get(i); 
                    if(argument.findAll(SimpleName.class).size()<1) continue; // SimpleName.class, find: variable + invoked method's name ;  //说明这个arguement压根没variable/ method's name，不用浪费时间啦
                    argument_containedInputOrOutput.put(argument, new ArrayList<>());
                    String argumentStr = argument.toString();
                    List<String> varibleNamesinArgument = new ArrayList<>();
                    List<String> mceStringInArgument = new ArrayList<>();

                    List<NameExpr> variblesinArgument  = argument.findAll(NameExpr.class); // SimpleName.class, find: variable + invoked method's name ;      NameExpr.class, find: variable's name
                    for (NameExpr varibles : variblesinArgument) {
                        varibleNamesinArgument.add(varibles.toString());
                    }

                    List<MethodCallExpr> mcesinArgument  = argument.findAll(MethodCallExpr.class); 
                    for (MethodCallExpr mce : mcesinArgument) {// to judge: return boolean value?
                        mceStringInArgument.add(mce.toString());
                    }

                    System.out.println( "argument: "  + argument);
                    System.out.println( "argument--BinaryExpr.class: "  + argument.findAll(BinaryExpr.class));
                    for (BinaryExpr binaryExpr : argument.findAll(BinaryExpr.class) ) {

                        System.out.println( "binaryExpr: " + binaryExpr );
                        System.out.println( "binaryExpr.getOperator(): " + binaryExpr.getOperator()  + "  ;;;  "+ binaryExpr.getChildNodes() );
                        // System.out.println( "binaryExpr.getOperator(): " + ( binaryExpr.getOperator() instanceof BinaryOperator.LEFT_SHIFT ) );
                    }
                    // BinaryExpr.Operator
                    for (MethodCallExpr mce_ : argument.findAll(MethodCallExpr.class) ) {

                        System.out.println( "mce_: " + mce_ );
                        System.out.println( "mce_.getChildNodes(): " + "  ;;;  "+ mce_.getChildNodes() );
                        // System.out.println( "binaryExpr.getOperator(): " + ( binaryExpr.getOperator() instanceof BinaryOperator.LEFT_SHIFT ) );
                        System.out.println( mce_.getParentNode().get().getChildNodes().get(1).getChildNodes() );
                        System.out.println( mce_.getParentNodeForChildren().getChildNodes().get(1) );
                    }

                    
                    // methodInvocation.outputExpressions中，会有: 正常的variables + mceString (which is invoked in assert)  
                    // - 正常variables (Str中无 '.' 无'(' ), 判断是否在 varibleOrMethods list
                    // - mceString (Str中有 '.' 有'(' ), 判断是否 contained by  argumentStr
                    // 
                    for (int j = 0; j < methodInvocations.size(); j++) {
                        methodInvocation mtdInvocation = methodInvocations.get(j);
                        if(!mtdInvocation_involvedByArguments.keySet().contains(mtdInvocation)) mtdInvocation_involvedByArguments.put(mtdInvocation, new ArrayList<Node>());
                        // mtdInvocation.outputExpressions;
                        // mtdInvocation.inputExpressions;
                        
                        // 交集 TODO: Check
                        // https://juejin.cn/post/6844903833726894093
                        List<String> intersectionInputExpressions = mtdInvocation.inputExpressions.stream().filter(item -> varibleNamesinArgument.contains(item)).collect(toList()); 
                        List<String> intersectionOutputExpressions = mtdInvocation.outputExpressions.stream().filter(item -> varibleNamesinArgument.contains(item)).collect(toList()); 
                        List<String> intersectionOutputExpressions2 = mtdInvocation.outputExpressions.stream().filter(item -> mceStringInArgument.contains(item)).collect(toList()); 
                        
                        if (intersectionInputExpressions.size()>0)
                            for (String intersectionInput : intersectionInputExpressions)
                                argument_containedInputOrOutput.get(argument).add("INPUT: " +intersectionInput);
                        if (intersectionOutputExpressions.size()>0) 
                            for (String intersectionOutput : intersectionOutputExpressions)
                                argument_containedInputOrOutput.get(argument).add("OUTPUT: " +intersectionOutput);
                        if (intersectionOutputExpressions2.size()>0) 
                            for (String OutputExpressions : intersectionOutputExpressions2)
                                argument_containedInputOrOutput.get(argument).add("OUTPUT: " +OutputExpressions);
                        
                        if ( intersectionInputExpressions.size() + intersectionOutputExpressions.size() + intersectionOutputExpressions2.size()>0 )
                        mtdInvocation_involvedByArguments.get(mtdInvocation).add(argument);
                    }

                }

                // argument_containedInputOrOutput: 至少有1个arguments，不为空 -》 说明有involved ouputs/inputs 在两边
                // argument_containedInputOrOutput: 至少有一个arguments中有 "OUTPUT__" -》 说明involved ouputs/inputs, 至少有一个是output
                // mtdInvocation_involvedByArguments： 至少有两个mtdInvocation，不为空 -》 说明 有多个method invocation 被涉及.
                int count_argument = 0; int count_mtdInvocation = 0; int count_involvedOutput = 0;
                for (Node argument : argument_containedInputOrOutput.keySet())
                    if(argument_containedInputOrOutput.get(argument).size()>0){
                        count_argument++;
                        if( argument_containedInputOrOutput.get(argument).toString().contains( "OUTPUT: " ) )
                            count_involvedOutput++;
                    }
                for (methodInvocation mtdInvocation : mtdInvocation_involvedByArguments.keySet())
                    if(mtdInvocation_involvedByArguments.get(mtdInvocation).size()>0) count_mtdInvocation++;
                if( count_argument>0 &&  count_mtdInvocation>1 && count_involvedOutput>0){
                    System.out.println("assertSTMT: " + assertSTMT);
                    for (Node argument : argument_containedInputOrOutput.keySet()){
                        if(argument_containedInputOrOutput.get(argument).size()>0){
                            System.out.println( "argument_containedInputOrOutput: " + argument + ": " + argument_containedInputOrOutput.get(argument) );
                        }
                    }
                    for (methodInvocation mtdInvocation : mtdInvocation_involvedByArguments.keySet()){
                        if(mtdInvocation_involvedByArguments.get(mtdInvocation).size()>0){
                            System.out.println( "mtdInvocation_involvedByArguments: " + mtdInvocation.mce.toString() + ": " + mtdInvocation_involvedByArguments.get(mtdInvocation) );
                        }
                    }
                    System.out.println("positive assertion! for assertTrue");
                    validOutputRelation = true; break;
                }*/

                List<String> validBinaryOperators = new ArrayList<>( Arrays.asList("AND", "BINARY_AND", "BINARY_OR", "OR", "XOR", "GREATER_EQUALS", "GREATER", "EQUALS", "LESS", "LESS_EQUALS", "NOT_EQUALS") );
                /* 循环assertion中的当个arguments */ 
                for (int i = 0; i < argumentsOfassert.size(); i++) {
                    Node argument = argumentsOfassert.get(i); 
                    if(argument.findAll(SimpleName.class).size()<1) continue; // SimpleName.class, find: variable + invoked method's name ;  //说明这个arguement压根没variable/ method's name，不用浪费时间啦

                    /* find all binaryOperators: */
                    for (BinaryExpr binaryExpr : argument.findAll(BinaryExpr.class) ) {
                        // System.out.println( "binaryExpr: " + binaryExpr );
                        // System.out.println( "binaryExpr.getOperator(): " + binaryExpr.getOperator()  + "  ;;;  "+ binaryExpr.getChildNodes() );
                        // System.out.println( "binaryExpr.getOperator(): " + ( binaryExpr.getOperator() 
                        String binaryOperator = binaryExpr.getOperator().toString();
                        // if binaryOperators belongs to ( ==, <, >, <= , >=, !=, AND, OR )
                        if(validBinaryOperators.contains(binaryOperator)){
                            // get 
                            Node oneside = binaryExpr.getChildNodes().get(0);
                            Node anotherside = binaryExpr.getChildNodes().get(1);
                            List< Node> exprsOfOperator = new ArrayList<>(Arrays.asList(oneside, anotherside) );
                            // judge
                            HashMap<Node, HashMap<String, List<String>>> expsInvolvedInputOrOutputs =  analyzeInvolvedInputOrOutputs(exprsOfOperator, methodInvocations, assertionInv);
                            Boolean containOutputRelation = satisfyOutputRelation(expsInvolvedInputOrOutputs, methodInvocations, md, assertionInv);
                            if(containOutputRelation){
                                System.out.println("LOG: positive assertion! for assertTrue category: " + assertSTMT);
                                System.out.println();
                                validOutputRelation = true; //break;
                            }
                        }
                        else{
                            System.out.println("not validBinaryOperators.contains(binaryOperator): " + binaryOperator);
                        }
                    }
                    if(!validOutputRelation){
                        /* find all mce (methodcallexpr) */
                        for (MethodCallExpr mce : argument.findAll(MethodCallExpr.class) ) {
                            if(Parser.getMethodReturnType(mce)==null){
                                System.out.println("Parser.getMethodReturnType(mce)==null, " + mce); continue;
                            }
                            if(Parser.getMethodReturnType(mce).equals("boolean")){ // method return boolean value
                                // get
                                List< Node> exprsOfOperator = new ArrayList<>();
                                for (Node node : mce.getChildNodes()) 
                                    if(!node.toString().equals( mce.getName()))
                                        exprsOfOperator.add(node);
                                // judge
                                HashMap<Node, HashMap<String, List<String>>> expsInvolvedInputOrOutputs = analyzeInvolvedInputOrOutputs(exprsOfOperator, methodInvocations, assertionInv);
                                Boolean containOutputRelation = satisfyOutputRelation(expsInvolvedInputOrOutputs, methodInvocations, md, assertionInv);
                                if(containOutputRelation){
                                    System.out.println("LOG: positive assertion! for assertTrue category: " + assertSTMT);
                                    System.out.println();
                                    validOutputRelation = true; //break;
                                }
                            }
                        }
                    }
                }
            }
            else{
                System.out.println("MISSED Assertion type: " + assertSTMT);
            }
        }
        return validOutputRelation;
    }


    public static HashMap<Node, HashMap<String, List<String>>> analyzeInvolvedInputOrOutputs(List<Node> exprsOfOperator, List<methodInvocation> methodInvocations, MethodCallExpr assertionInv) {
        // { exprsOfOperator: { "methodInvocation_j": ["Input: xxxx" , "Input: xxxx"] } }
        HashMap<Node, HashMap<String,List<String>>> result = new HashMap<Node, HashMap<String,List<String>>>();
        int assertLineNumberEND = assertionInv.getEnd().get().line;


        for (Node oneExprOfOperator : exprsOfOperator) {
            result.put(oneExprOfOperator, new HashMap<String,List<String>>());
            List<NameExpr> variblesinOneExprOfOperator = oneExprOfOperator.findAll(NameExpr.class);
            List<String> variblesStrInOneExprOfOperator = new ArrayList<>() ;
            for (NameExpr nameExpr : variblesinOneExprOfOperator) {
                variblesStrInOneExprOfOperator.add(nameExpr.getNameAsString());
            }

            List<MethodCallExpr> mcesinExpr  = oneExprOfOperator.findAll(MethodCallExpr.class); 
            List<String> mceStringInExpr = new ArrayList<>();
            for (MethodCallExpr mce : mcesinExpr) {// to judge: return boolean value?
                mceStringInExpr.add(mce.toString());
            }

            for (int j = 0; j < methodInvocations.size(); j++) {
                methodInvocation mtdInvocation = methodInvocations.get(j);
                int methodInvocationlineNumberBegin = mtdInvocation.lineNumberBegin;
                if(methodInvocationlineNumberBegin>assertLineNumberEND) // 说明在assert之后
                    continue;
                String methodInvocation_index = "methodInvocation_" +  j;

                // 交集
                // https://juejin.cn/post/6844903833726894093
                List<String> intersectionInputExpressions = mtdInvocation.inputExpressions.stream().filter(item -> variblesStrInOneExprOfOperator.contains(item)).collect(toList()); 
                List<String> intersectionOutputExpressions = mtdInvocation.outputExpressions.stream().filter(item -> variblesStrInOneExprOfOperator.contains(item)).collect(toList()); 
                List<String> intersectionOutputExpressions2 = mtdInvocation.outputExpressions.stream().filter(item -> mceStringInExpr.contains(item)).collect(toList()); 
                
                if ( intersectionInputExpressions.size() + intersectionOutputExpressions.size() + intersectionOutputExpressions2.size()>0 )
                    result.get(oneExprOfOperator).put(methodInvocation_index, new ArrayList<>());
                if (intersectionInputExpressions.size()>0)
                    for (String intersectionInput : intersectionInputExpressions)
                        result.get(oneExprOfOperator).get(methodInvocation_index).add("INPUT: " +intersectionInput);
                if (intersectionOutputExpressions.size()>0) 
                    for (String intersectionOutput : intersectionOutputExpressions)
                        result.get(oneExprOfOperator).get(methodInvocation_index).add("OUTPUT: " +intersectionOutput);
                if (intersectionOutputExpressions2.size()>0) 
                    for (String OutputExpressions : intersectionOutputExpressions2)
                        result.get(oneExprOfOperator).get(methodInvocation_index).add("OUTPUT: " +OutputExpressions);
            }
        }
        return result;
    }

    public static boolean satisfyOutputRelation(HashMap<Node, HashMap<String, List<String>>> expsInvolvedInputOrOutputs, List<methodInvocation> methodInvocations, MethodDeclaration md, MethodCallExpr assertionInv) throws IOException, ClassNotFoundException, CloneNotSupportedException {
        // { exprsOfOperator: { "methodInvocation_j": ["Input: xxxx" , "Input: xxxx"] } }
        /* 
         * at lest two exprsOfOperators with "methodInvocation_j" 
         * involved method invocation>2
         * follow-up output are involved, and another invocation not the same index
        */
        boolean result = false;
        // at lest two exprsOfOperators with "methodInvocation_j" 
        int count_exprsOfOperators_with_methodInvocation = 0;
        // involved method invocation>2
        List<Integer> involvedMethodInvocationIndex = new ArrayList<>();
        List<String> involvedMethodInvocationIndexStr = new ArrayList<>();
        // follow-up output: invocation index and oneExprOfOperator
        int indexOffollowUpOutputInvocation = -1;
        methodInvocation methodInvocationOfFollowUp = null;
        String followUpOutputName = "";
        Node ExprOfOperatorInvolveFollowUpOutput = null;
        String ExprOfOperatorInvolveFollowUpOutput_Str = null;
        for (Node oneExprOfOperator : expsInvolvedInputOrOutputs.keySet()) {
            if( expsInvolvedInputOrOutputs.get(oneExprOfOperator).size()>0 ){
                count_exprsOfOperators_with_methodInvocation ++;
                for (String methodInvocationIndexStr : expsInvolvedInputOrOutputs.get(oneExprOfOperator).keySet()) {
                    int methodInvocationIndex = Integer.parseInt(methodInvocationIndexStr.split("_")[1]);
                    involvedMethodInvocationIndex.add(methodInvocationIndex);
                    involvedMethodInvocationIndexStr.add(methodInvocationIndexStr);

                    // locate the follow up output
                    for (String inputOrOutput : expsInvolvedInputOrOutputs.get(oneExprOfOperator).get(methodInvocationIndexStr)) {
                        if(inputOrOutput.startsWith("OUTPUT: ") && methodInvocationIndex>indexOffollowUpOutputInvocation){
                            followUpOutputName = inputOrOutput.split("OUTPUT: ")[1];
                            indexOffollowUpOutputInvocation = methodInvocationIndex;
                            ExprOfOperatorInvolveFollowUpOutput = oneExprOfOperator;
                            ExprOfOperatorInvolveFollowUpOutput_Str = oneExprOfOperator.toString();
                            methodInvocationOfFollowUp = methodInvocations.get(indexOffollowUpOutputInvocation);
                        }
                    }
                }
            }
        }
        
        // for debug:
        // System.out.println("ExprOfOperatorInvolveFollowUpOutput_Str: " + ExprOfOperatorInvolveFollowUpOutput_Str);//
        // System.out.println("indexOffollowUpOutputInvocation: " + indexOffollowUpOutputInvocation);
        // System.out.println("count_exprsOfOperators_with_methodInvocation: " + count_exprsOfOperators_with_methodInvocation);
        // System.out.println("involvedMethodInvocationIndex.size(): " + involvedMethodInvocationIndex.size());
        
        relationAssertion relationAssertionObj = new relationAssertion();
        List<methodInvocation> allInvolvedPreMethodInvocations = new ArrayList<>();
        HashMap<methodInvocation, List<String>> previousMethodInvocations_involvedInputOrOutput = new HashMap<methodInvocation, List<String>>();
        // follow-up output are involved, and another invocation not the same index
        if( count_exprsOfOperators_with_methodInvocation >1 && involvedMethodInvocationIndex.size() >1 && indexOffollowUpOutputInvocation>0){ // ndexOffollowUpOutputInvocation>0 -> invocation not the first invocation
            for (Node oneExprOfOperator : expsInvolvedInputOrOutputs.keySet()) {
                if(expsInvolvedInputOrOutputs.get(oneExprOfOperator).size()<1 ) continue;
                if(!oneExprOfOperator.toString().equals(ExprOfOperatorInvolveFollowUpOutput_Str)){ // 与followup Output非同一个ExprOfOperator -> 在两边
                    for (String methodInvocationIndexStr : expsInvolvedInputOrOutputs.get(oneExprOfOperator).keySet()) {
                        int methodInvocationIndex = Integer.parseInt(methodInvocationIndexStr.split("_")[1]);
                        if(methodInvocationIndex < indexOffollowUpOutputInvocation){ // 在follow-up output所在invocation之前调用
                            result = true;
                            // printRelationDetails, for log 
                            // relationAssertionObj
                            methodInvocation methodInvocationOfPrevious = methodInvocations.get(methodInvocationIndex);
                            allInvolvedPreMethodInvocations.add(methodInvocationOfPrevious);
                            List<String> anotherInvolvedInputOrOutput = expsInvolvedInputOrOutputs.get(oneExprOfOperator).get(methodInvocationIndexStr);
                            previousMethodInvocations_involvedInputOrOutput.put(methodInvocationOfPrevious, anotherInvolvedInputOrOutput);

                            // printRelationDetails(expsInvolvedInputOrOutputs, followUpOutputName, ExprOfOperatorInvolveFollowUpOutput_Str,  anotherInvolvedInputOrOutput,  oneExprOfOperator);
                            // return result;
                        }
                    }
                }
            }
        }
        
        if(result){
            String FQN_targetClassUnderTest = Parser.GetClassFNameFromMethodFSig(methodInvocationOfFollowUp.mceQualifiedSignature) ;
            relationAssertionObj.lineNumberBegin = assertionInv.getBegin().get().line;
            relationAssertionObj.lineNumberEnd = assertionInv.getEnd().get().line;
            relationAssertionObj.Path_testClassUnderAnalysis = MTIdentifier.FILE_PATH;
            relationAssertionObj.lastMethodInvocationOutputName = followUpOutputName;
            relationAssertionObj.lastMethodInvocation = methodInvocationOfFollowUp;
            relationAssertionObj.previousMethodInvocations_involvedInputOrOutput = previousMethodInvocations_involvedInputOrOutput;
            relationAssertionObj.testCaseMethodDeclaration = md;
            relationAssertionObj.FQN_testCaseUnderAnalysis = md.resolve().getQualifiedName();
            relationAssertionObj.FQS_testCaseUnderAnalysis = md.resolve().getQualifiedSignature();
            relationAssertionObj.FQN_targetClassUnderTest = FQN_targetClassUnderTest;
            relationAssertionObj.assertionInv = assertionInv;
            relationAssertionObj.assertionSTMT = assertionInv.toString();
            relationAssertionObj.withInputTransformation(1);
            relationAssertionObj.withInputTransformation(2);


            printRelationAssertion(relationAssertionObj);
            storeRelationAssertion(relationAssertionObj, md);   
        }
        return result;
    }



    public static void printRelationDetails(HashMap<Node, HashMap<String, List<String>>> expsInvolvedInputOrOutputs, String followUpOutputName, String ExprOfOperatorInvolveFollowUpOutput_Str, List<String> anotherInvolvedInputOrOutput, Node oneExprOfOperator) {
        // System.out.println("LOG: expsInvolvedInputOrOutputs: "+expsInvolvedInputOrOutputs);
        System.out.println("LOG: followUpOutputName: "+followUpOutputName);
        System.out.println("LOG: ExprOfOperatorInvolveFollowUpOutput_Str: "+ExprOfOperatorInvolveFollowUpOutput_Str);
        System.out.println("LOG: anotherExprOfOperator: "+oneExprOfOperator);
        System.out.println("LOG: anotherInvolvedInputOrOutput: "+anotherInvolvedInputOrOutput);
    }

    public static void printRelationAssertion(relationAssertion relationAssertionObj) throws IOException, ClassNotFoundException {
        System.out.println("LOG: followUpOutputName(lastMethodInvocationOutputName): "+relationAssertionObj.lastMethodInvocationOutputName);
        System.out.println("LOG: followUpMethodInvocation(lastMethodInvocation): "+relationAssertionObj.lastMethodInvocation.mce);
        int count=1;
        for (methodInvocation previousMethodInvocation : relationAssertionObj.previousMethodInvocations_involvedInputOrOutput.keySet()) {
            System.out.println("LOG: previousMethodInvocations" + count++);
            System.out.println("LOG: previousMethodInvocation: "+ previousMethodInvocation.mce );
            System.out.println("LOG: previousMethodInvocation_involvedInputOrOutput: "+relationAssertionObj.previousMethodInvocations_involvedInputOrOutput.get(previousMethodInvocation));
        }
    }

    public static void storeRelationAssertion(relationAssertion relationAssertionObj, MethodDeclaration md) throws ClassNotFoundException, IOException, CloneNotSupportedException{
        // System.out.println( "md.resolve().getQualifiedName(): " +  md.resolve().getQualifiedName() );
        // System.out.println( "md.resolve().getQualifiedSignature(): " +  md.resolve().getQualifiedSignature() );
        String FQN_testCaseUnderAnalysis = md.resolve().getQualifiedName();
        String pojName = FileUtil.findPojName( MTIdentifier.FILE_PATH, Config.SPLITE_STR);
        String pojDir = FileUtil.findPojDir( MTIdentifier.FILE_PATH, Config.SPLITE_STR);

        // two path: 1. dir output/  2. at the root dir of poj
        String dirRelationAssertion; 
        // 1. dir output/ 
        Config config = new Config();
        dirRelationAssertion =  config.AUTOMR_EXPERIMENTAL_POJS_MTIDENTIFIER_RELATIONASSRTION_OUTPUT_DIR + pojName +"/";

        int index=0;
        String pathRelationAssertion = dirRelationAssertion + FQN_testCaseUnderAnalysis + Config.SPLITE_STR +(index++) +".relationAssertion";
        while(FileUtil.fileExisting(pathRelationAssertion)){
            pathRelationAssertion = dirRelationAssertion + FQN_testCaseUnderAnalysis + Config.SPLITE_STR + (index++) +".relationAssertion";
        }
        storeRelationAssertion(relationAssertionObj, pathRelationAssertion, pathRelationAssertion.replace(".relationAssertion", ".json"));

        //2. at the root dir of poj
        // String dirRelationAssertion; // pojDir/AutoMR/MTidentifier/FQN_TMD_index.relationAssertion
        dirRelationAssertion =  pojDir + Config.MTidentifier_Fold_path_in_PUA;
        // System.out.println("storeRelationAssertion: dirRelationAssertion: " + dirRelationAssertion);

        index=0;
        pathRelationAssertion = dirRelationAssertion + FQN_testCaseUnderAnalysis + Config.SPLITE_STR +(index++) +".relationAssertion";
        while(FileUtil.fileExisting(pathRelationAssertion)){
            pathRelationAssertion = dirRelationAssertion + FQN_testCaseUnderAnalysis + Config.SPLITE_STR + (index++) +".relationAssertion";
        }
        storeRelationAssertion(relationAssertionObj, pathRelationAssertion, pathRelationAssertion.replace(".relationAssertion", ".json"));
    }
    
    public static void storeRelationAssertion(relationAssertion relationAssertionObj, String filePathObject, String filePathJson) throws IOException, ClassNotFoundException, CloneNotSupportedException{
        String filePath = filePathObject;
        objectIO.writeObjectToFile(relationAssertionObj, filePath);

        relationAssertion relationAssertionObj_json = (relationAssertion) relationAssertionObj.clone();//
        // relationAssertion relationAssertionObj_json = relationAssertionObj;// 最好是能想办法copy下
        relationAssertionObj_json.previousMethodInvocations_involvedInputOrOutput = null; // 因为object为key时，在JSON语法中不适用 
        JsonUtil.writeJson(filePathJson, relationAssertionObj_json, true);
    }



}
