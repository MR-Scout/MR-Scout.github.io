package com.mr.extractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;
import com.github.javaparser.resolution.declarations.ResolvedValueDeclaration;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserMethodDeclaration;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.mr.extractor.config.Config;
import com.mr.extractor.util.FileUtil;
import com.github.javaparser.resolution.UnsolvedSymbolException;

public class Parser {

    public static List<String> getAllMethodDeclaration(String filePath) throws FileNotFoundException {
        TypeSolver typeSolver = new ReflectionTypeSolver();
        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(typeSolver);
        StaticJavaParser
                .getConfiguration()
                .setSymbolResolver(symbolSolver);

        List<String> methodNames = new ArrayList();
        try {
            CompilationUnit cu = StaticJavaParser.parse(new FileInputStream(filePath));
            // VoidVisitor<Void> methodNameVisitor = new MethodNamePrinter();
            // methodNameVisitor.visit(cu, null);
            VoidVisitor<List<String>> methodNameCollector = new MethodNameCollector();
            methodNameCollector.visit(cu, methodNames);

            return methodNames;
        } catch (Exception e) {
            return methodNames;
        }
    }

    public static Map<String, List<String>> getAllMethodCall(String filePath) throws FileNotFoundException {
        TypeSolver reflectionTypeSolver = new ReflectionTypeSolver();
        // TypeSolver javaParserTypeSolver = new JavaParserTypeSolver(new File(SRC_PATH));
        // reflectionTypeSolver.setParent(reflectionTypeSolver);

        CombinedTypeSolver combinedSolver = new CombinedTypeSolver();
        combinedSolver.add(reflectionTypeSolver);
        // combinedSolver.add(javaParserTypeSolver);

        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(combinedSolver);
        StaticJavaParser
                .getConfiguration()
                .setSymbolResolver(symbolSolver);
        CompilationUnit cu = StaticJavaParser.parse(new FileInputStream(filePath));

        Map<String, List<String>> MethodCallMap = new HashMap<String, List<String>>();
        for (MethodDeclaration md: cu.findAll(MethodDeclaration.class)){

            MethodCallMap.put(filePath.split("/src/")[1] + ":" + md.getNameAsString(), new ArrayList<String>());

            List<MethodCallExpr> methodCalls = md.findAll(MethodCallExpr.class);
            Map<String, Integer> calledMethodCount = new HashMap<String, Integer>();

            for (MethodCallExpr mce: methodCalls){
                    String mceName = new String();
                    // try{
                    //     String mceQualifiedSignature = mce.asMethodCallExpr().resolve().getQualifiedSignature();
                    //     // System.out.println( mceQualifiedSignature );
                    //     mceName = mceQualifiedSignature.substring(0, mceQualifiedSignature.lastIndexOf('('));
                    // }catch(UnsolvedSymbolException e){
                    //     mceName = mce.getName().toString();
                    //     // System.out.println( "mce.getName(): "+mce.getName() );
                    // }
                    mceName = mce.getName().toString();
                    MethodCallMap.get(filePath.split("/src/")[1]  + ":" + md.getNameAsString()).add(mceName);
            }
        }
        return MethodCallMap;
    }

    public static Map<String, List<String>> getAllLocalMethodCall(String filePath) throws FileNotFoundException {
        TypeSolver reflectionTypeSolver = new ReflectionTypeSolver();
        // TypeSolver javaParserTypeSolver = new JavaParserTypeSolver(new File(SRC_PATH));
        // reflectionTypeSolver.setParent(reflectionTypeSolver);

        CombinedTypeSolver combinedSolver = new CombinedTypeSolver();
        combinedSolver.add(reflectionTypeSolver);
        // combinedSolver.add(javaParserTypeSolver);

        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(combinedSolver);
        StaticJavaParser
                .getConfiguration()
                .setSymbolResolver(symbolSolver);
        CompilationUnit cu = StaticJavaParser.parse(new FileInputStream(filePath));

        Map<String, List<String>> MethodCallMap = new HashMap<String, List<String>>();
        for (MethodDeclaration md: cu.findAll(MethodDeclaration.class)){

            MethodCallMap.put(md.getNameAsString(), new ArrayList<String>());

            List<MethodCallExpr> methodCalls = md.findAll(MethodCallExpr.class);
            Map<String, Integer> calledMethodCount = new HashMap<String, Integer>();

            for (MethodCallExpr mce: methodCalls){
                    String mceName = new String();
                    try{
                        String mceQualifiedSignature = mce.asMethodCallExpr().resolve().getQualifiedSignature();
                        // System.out.println( mceQualifiedSignature );
                        // mceName = mceQualifiedSignature.substring(0, mceQualifiedSignature.lastIndexOf('('));
                        mceName = mceQualifiedSignature.substring(mceQualifiedSignature.lastIndexOf('.'),mceQualifiedSignature.lastIndexOf(')')+1 );
                    }catch(Exception e){
                        // mceName = mce.getName().toString();
                        // System.out.println( "mce.getName(): "+mce.getName() );
                        // System.out.println(e.getMessage());
                    }
                    MethodCallMap.get(md.getNameAsString()).add(mceName);
            }
        }
        return MethodCallMap;
    }



    private static class MethodNamePrinter extends VoidVisitorAdapter<Void> {

        @Override
        public void visit(MethodDeclaration md, Void arg) {
            super.visit(md, arg);
            System.out.println("Method Name Printed: " + md.getName());
            System.out.println("Method body Printed: " + md.getBody());

        }
    }

    private static class MethodNameCollector extends VoidVisitorAdapter<List<String>> {

        @Override
        public void visit(MethodDeclaration md, List<String> collector) {
            super.visit(md, collector);
            collector.add(md.getNameAsString());
        }
    }
    

    /**
     * getPackagePathsFromPojDir: 认为package前的绝对路径，就是 package的source root
     *
     * @return true or false
     */
    public static List<String> getPackagePathsFromPojDir(String pojDir) throws FileNotFoundException {
        
        // 解析出所有文件的路径
        List<String> filePaths = new ArrayList<String>();
        FileUtil.findFileList(new File(pojDir),filePaths );

        // 解析出所有java文件的路径
        List<String> javaFilePaths = new ArrayList<String>();
        for (String filepath : filePaths) {
            if(filepath.endsWith(".java") && !filepath.contains("evosuite") && !filepath.endsWith("_AutoMR.java") ) { // !filepath.contains("evosuite")， 因为有些项目重复运行，生成了 /evosuite dir (或是_AutoMR.java文件)导致报错。。
                javaFilePaths.add(filepath);
            }
        }

        // 获取所有的package 申明
        Map<String, List<String>> packageDeclarations = new HashMap<>();
        for (String filepath : javaFilePaths) {
            // System.out.println( filepath );
            CompilationUnit cu;
            try {
                cu = StaticJavaParser.parse(new FileInputStream(filepath));
            } catch (com.github.javaparser.ParseProblemException e) {
                //TODO: handle exception //该文件没发parser
                continue;
            }
            String pdName = "";
            String pdDir = "";
            for (PackageDeclaration pd : cu.findAll(PackageDeclaration.class)) {
                // System.out.println( pd.getNameAsString()  + "   " + pd.toString());
                pdName = pd.getNameAsString();
                if (!packageDeclarations.keySet().contains(pdName)){
                    if (filepath.contains( '/'+ pdName.replace('.', '/') +'/' ) )
                        pdDir = filepath.split( '/'+ pdName.replace('.', '/') +'/' )[0] + '/';
                        
                        ArrayList pdDirs = new ArrayList<>();
                        pdDirs.add(pdDir);
                        packageDeclarations.put(pdName, pdDirs);
                        // System.out.println( filepath );
                        // System.out.println( pdName );
                        // System.out.println( pdDir );
                }
                else{
                    if (filepath.contains( '/'+ pdName.replace('.', '/') +'/' ) )
                        pdDir = filepath.split( '/'+ pdName.replace('.', '/') +'/' )[0] + '/';
                        packageDeclarations.get(pdName).add(pdDir);
                }

            }
        }

        List<String> pkDirs = new ArrayList<String>();
        for (String pdName : packageDeclarations.keySet()) {
            List<String> pdDirs = packageDeclarations.get(pdName);
            for (String pdDir : pdDirs) {
                if (!pkDirs.contains(pdDir))
                    pkDirs.add( pdDir );
            }
        }
        return pkDirs;
    }

/**
     * getTargerMethodsUnderTest: Get the test method name (TMN), and called method’s name (CMN), 
     * if TMN.lower() contains CMN.lower()  or CMN.lower() contains TMN.lower() without “test”
     * Matched methods are target methods
     *
     * 20220505 update
     * if 1. test method name (contains/ was contained by) invoked methods name, as what is already implemented.
     * else 2. invoked methods from targetObjectsUnderTest(targetClassUnderTest).
     * 
     * 20220528 update
     * 将以上两步，进行拆分。。。分为两个方法来做。。。。第一个方法，只做1。第二个方法，作为统一接口，默认做1+2
     * @return true or false
     */
    public static List<String> getTargetMethodsUnderTest(MethodDeclaration md, PackageDeclaration pd) throws FileNotFoundException {
        List<String> targetMethodsUnderTest = new ArrayList<>();
        
        /* valid test method */
        /* System.out.println( md.getDeclarationAsString()  );  // public void testSinX()
        System.out.println( md.getNameAsString()  );  // testSinX
        System.out.println( md.getTypeAsString()  );  // return type
        System.out.println( md.getAnnotations()  );  //  [@Test]
        System.out.println( md.getAccessSpecifier()); // PUBLIC
         */
        String testMethodName = md.getNameAsString();
        // List<AnnotationExpr> testMethodAnnotations = md.getAnnotations();
        // AccessSpecifier testMethodAccessSpecifier =  md.getAccessSpecifier();
        String testMethodAnnotations = md.getAnnotations().toString();
        String testMethodAccessSpecifier =  md.getAccessSpecifier().toString();
        if( !testMethodAccessSpecifier.equals("PUBLIC") || !testMethodAnnotations.contains("@Test") ) {
            return targetMethodsUnderTest;
        }

        /* Get the test method name (TMN), and called method’s name (CMN) */
        List<MethodCallExpr> methodCalls = md.findAll(MethodCallExpr.class);
        List<String> calledMethodNames = new ArrayList<>();

        for (MethodCallExpr mce: methodCalls){
            String mceName = new String();
            mceName = Parser.getFullQualifiedNameOfMethodCallExpr(mce, pd);
            // if(mceName.length() == 0)
            //     mceName = mce.getName().toString();
            // 防止是本地的test　ＡＰＩ
            if (mceName.length() > 0 && !mceName.contains("test") && !mceName.contains("Test") )
                calledMethodNames.add(mceName);
        }
        // System.out.println( methodCalls );
        // System.out.println( calledMethodNames );

        /* if TMN.lower() contains CMN.lower() */
        for (String mceName : calledMethodNames) {
            String shortmceName = mceName.substring(mceName.lastIndexOf('.')+1);
            if( testMethodName.toLowerCase().contains( shortmceName.toLowerCase() )){
                if(!targetMethodsUnderTest.contains(mceName))
                    targetMethodsUnderTest.add(mceName);
            }
             /* or CMN.lower() contains TMN.lower() without “test” */
            if( shortmceName.toLowerCase().contains( testMethodName.replaceFirst("test", "").toLowerCase() )){
                if(!targetMethodsUnderTest.contains(mceName))
                    targetMethodsUnderTest.add(mceName);
            }

        }

        // System.out.println("targetMethodsUnderTest: "+ targetMethodsUnderTest);

        /* the above code is for 1. test method name (contains/ was contained by) invoked methods name, as what is already implemented.
        * following code is for 2. invoked methods are from targetObjectsUnderTest(targetClassUnderTest).
        * 已拆到另一个方法中。
        */

        // if (targetMethodsUnderTest.size()==0){
        //     for (String mceName : calledMethodNames) {
        //         String shortmceName = mceName.substring(mceName.lastIndexOf('.')+1);
        //         String fullClassNameofCalledMethod = mceName.substring(0, mceName.lastIndexOf('.'));
                
        //         if( targetObjectsUnderTest.contains( fullClassNameofCalledMethod ) ){
        //             targetMethodsUnderTest.add( mceName );
        //             // // for test
        //             // System.out.println( "mceName: "+mceName  );
        //             // System.out.println( "fullClassNameofCalledMethod: "+fullClassNameofCalledMethod  );
        //         }

        //     }
        //     // // for test
        //     // System.out.println( "targetObjectsUnderTest: "+targetObjectsUnderTest  );
        //     // System.out.println( "new added methods under test: "+ targetMethodsUnderTest.toString()  );
        // }

        return targetMethodsUnderTest;
    }

    /* contains： 
     * if 1. test method name (contains/ was contained by) invoked methods name, as what is already implemented.
     * else 2. invoked methods from targetObjectsUnderTest(targetClassUnderTest).
     */
    public static List<String> getTargetMethodsUnderTest(MethodDeclaration md, PackageDeclaration pd, List<String> targetObjectsUnderTest) throws FileNotFoundException {
        List<String> targetMethodsUnderTest = new ArrayList<>();
        
        /* valid test method */
        /* System.out.println( md.getDeclarationAsString()  );  // public void testSinX()
        System.out.println( md.getNameAsString()  );  // testSinX
        System.out.println( md.getTypeAsString()  );  // return type
        System.out.println( md.getAnnotations()  );  //  [@Test]
        System.out.println( md.getAccessSpecifier()); // PUBLIC
         */
        String testMethodName = md.getNameAsString();
        // List<AnnotationExpr> testMethodAnnotations = md.getAnnotations();
        // AccessSpecifier testMethodAccessSpecifier =  md.getAccessSpecifier();
        String testMethodAnnotations = md.getAnnotations().toString();
        String testMethodAccessSpecifier =  md.getAccessSpecifier().toString();
        if( !testMethodAccessSpecifier.equals("PUBLIC") || !testMethodAnnotations.contains("@Test") ) {
            return targetMethodsUnderTest;
        }

        /* Get the test method name (TMN), and called method’s name (CMN) */
        List<MethodCallExpr> methodCalls = md.findAll(MethodCallExpr.class);
        List<String> calledMethodNames = new ArrayList<>();

        for (MethodCallExpr mce: methodCalls){
            String mceName = new String();
            mceName = Parser.getFullQualifiedNameOfMethodCallExpr(mce, pd);
            // if(mceName.length() == 0)
            //     mceName = mce.getName().toString();
            // 防止是本地的test　ＡＰＩ
            if (mceName.length() > 0 && !mceName.contains("test") && !mceName.contains("Test") )
                calledMethodNames.add(mceName);
        }
        // System.out.println( methodCalls );
        // System.out.println( calledMethodNames );


        /* if TMN.lower() contains CMN.lower() */
        for (String mceName : calledMethodNames) {
            String shortmceName = mceName.substring(mceName.lastIndexOf('.')+1);
            if( testMethodName.toLowerCase().contains( shortmceName.toLowerCase() )){
                if(!targetMethodsUnderTest.contains(mceName))
                    targetMethodsUnderTest.add(mceName);
            }
             /* or CMN.lower() contains TMN.lower() without “test” */
            if( shortmceName.toLowerCase().contains( testMethodName.replaceFirst("test", "").toLowerCase() )){
                if(!targetMethodsUnderTest.contains(mceName))
                    targetMethodsUnderTest.add(mceName);
            }

        }

        // System.out.println("targetMethodsUnderTest: "+ targetMethodsUnderTest);

        /* the above code is for 1. test method name (contains/ was contained by) invoked methods name, as what is already implemented.
        * following code is for 2. invoked methods are from targetObjectsUnderTest(targetClassUnderTest).
        */
        // System.out.println( "calledMethodNames: "+calledMethodNames  );
        if (targetMethodsUnderTest.size()==0){
            for (String mceName : calledMethodNames) {
                String shortmceName = mceName.substring(mceName.lastIndexOf('.')+1);
                String fullClassNameofCalledMethod = mceName.substring(0, mceName.lastIndexOf('.'));
                
                if( targetObjectsUnderTest.contains( fullClassNameofCalledMethod ) ){
                    targetMethodsUnderTest.add( mceName );
                    // // for test
                    // System.out.println( "mceName: "+mceName  );
                    // System.out.println( "fullClassNameofCalledMethod: "+fullClassNameofCalledMethod  );
                }

            }
            // // for test
            // System.out.println( "targetObjectsUnderTest: "+targetObjectsUnderTest  );
            // System.out.println( "new added methods under test: "+ targetMethodsUnderTest.toString()  );
        }

        return targetMethodsUnderTest;
    }


    public static List<String> getTargetMethodsUnderTestFromObjects(MethodDeclaration md, PackageDeclaration pd, List<String> targetObjectsUnderTest) throws FileNotFoundException {
        List<String> targetMethodsUnderTest = new ArrayList<>();
        
        /* valid test method */
        /* System.out.println( md.getDeclarationAsString()  );  // public void testSinX()
        System.out.println( md.getNameAsString()  );  // testSinX
        System.out.println( md.getTypeAsString()  );  // return type
        System.out.println( md.getAnnotations()  );  //  [@Test]
        System.out.println( md.getAccessSpecifier()); // PUBLIC
         */
        String testMethodName = md.getNameAsString();
        // List<AnnotationExpr> testMethodAnnotations = md.getAnnotations();
        // AccessSpecifier testMethodAccessSpecifier =  md.getAccessSpecifier();
        String testMethodAnnotations = md.getAnnotations().toString();
        String testMethodAccessSpecifier =  md.getAccessSpecifier().toString();
        if( !testMethodAccessSpecifier.equals("PUBLIC") || !testMethodAnnotations.contains("@Test") ) {
            return targetMethodsUnderTest;
        }

        /* Get the test method name (TMN), and called method’s name (CMN) */
        List<MethodCallExpr> methodCalls = md.findAll(MethodCallExpr.class);
        List<String> calledMethodNames = new ArrayList<>();

        for (MethodCallExpr mce: methodCalls){
            String mceName = new String();
            mceName = Parser.getFullQualifiedNameOfMethodCallExpr(mce, pd);
            // if(mceName.length() == 0)
            //     mceName = mce.getName().toString();
            // 防止是本地的test　ＡＰＩ
            if (mceName.length() > 0 && !mceName.contains("test") && !mceName.contains("Test") )
                calledMethodNames.add(mceName);
        }
        // System.out.println( methodCalls );
        // System.out.println( calledMethodNames );


        // System.out.println("targetMethodsUnderTest: "+ targetMethodsUnderTest);

        /* 
        * following code is for 2. invoked methods are from targetObjectsUnderTest(targetClassUnderTest).
        */
        // System.out.println( "calledMethodNames: "+calledMethodNames  );
        if (targetMethodsUnderTest.size()==0){
            for (String mceName : calledMethodNames) {
                String shortmceName = mceName.substring(mceName.lastIndexOf('.')+1);
                String fullClassNameofCalledMethod = mceName.substring(0, mceName.lastIndexOf('.'));
                
                if( targetObjectsUnderTest.contains( fullClassNameofCalledMethod ) ){
                    targetMethodsUnderTest.add( mceName );
                    // // for test
                    // System.out.println( "mceName: "+mceName  );
                    // System.out.println( "fullClassNameofCalledMethod: "+fullClassNameofCalledMethod  );
                }

            }
            // // for test
            // System.out.println( "targetObjectsUnderTest: "+targetObjectsUnderTest  );
            // System.out.println( "new added methods under test: "+ targetMethodsUnderTest.toString()  );
        }

        return targetMethodsUnderTest;
    }



    
/**
     * getTargerMethodsUnderTest: Get the test method name (TMN), and called method’s class name (CCN) from fully qualified names (CFQN), 
     * if TMN.lower() contains CCN.lower()  or CCN.lower() contains TMN.lower() without “test”
     * Matched objects are target objects
     *
     * 20220505 update
     * if 1. test method name (contains/ was contained by) invoked methods' class name, as what is already implemented.
     * else 2. test class name (contains/ was contained by)  invoked methods' class name, which is added newly.
     * @return true or false
     */
    public static List<String> getTargetObjectsUnderTest(MethodDeclaration md, PackageDeclaration pd, String testClassName) throws FileNotFoundException {
        List<String> targetObjectsUnderTest = new ArrayList<>();
        
        /* valid test method */
        String testMethodName = md.getNameAsString();
        // List<AnnotationExpr> testMethodAnnotations = md.getAnnotations();
        // AccessSpecifier testMethodAccessSpecifier =  md.getAccessSpecifier();
        String testMethodAnnotations = md.getAnnotations().toString();
        String testMethodAccessSpecifier =  md.getAccessSpecifier().toString();
        if( !testMethodAccessSpecifier.equals("PUBLIC") || !testMethodAnnotations.contains("@Test") ) {
            return targetObjectsUnderTest;
        }

        /* Get the test method name (TMN), and called method’s class name (CCN) */
        List<MethodCallExpr> methodCalls = md.findAll(MethodCallExpr.class);
        List<String> calledMethodClassNames = new ArrayList<>();
        // System.out.println("!!!"+testMethodName);
        for (MethodCallExpr mce: methodCalls){
            String mceName = new String();
            mceName = Parser.getFullQualifiedNameOfMethodCallExpr(mce, pd);
            if(mceName.length() > 0 && !mceName.contains("test") && !mceName.contains("Test") ){  // 防止是本地的test　ＡＰＩ
                // System.out.println( "!!!!!!"+mceName );
                String objectFullQualifiedName = mceName.substring(0,mceName.lastIndexOf('.'));
                // System.out.println( "!!!!!!"+objectFullQualifiedName );
                calledMethodClassNames.add(objectFullQualifiedName);
                String objectName = objectFullQualifiedName.substring(objectFullQualifiedName.lastIndexOf('.')+1);
                // System.out.println( "!!!!!!"+objectName );
                // calledMethodClassNames.add(objectName);
            }

        }
        
        /* if TMN.lower() contains CCN.lower() */
        for (String mceName : calledMethodClassNames) { // mceName means className
            String shortmceName = mceName.substring(mceName.lastIndexOf('.')+1);
            if( testMethodName.toLowerCase().contains( shortmceName.toLowerCase() )){
                if(!targetObjectsUnderTest.contains(mceName))
                    targetObjectsUnderTest.add(mceName);
            }
            /* CCN.lower() contains TMN.lower() without “test” */
            if( shortmceName.toLowerCase().contains( testMethodName.toLowerCase().replaceFirst("test", "") )){
                if(!targetObjectsUnderTest.contains(mceName))
                    targetObjectsUnderTest.add(mceName);
            }
        }
        // System.out.println("targetObjectsUnderTest: " + targetObjectsUnderTest);

        /* the above code is for 1. test method name (contains/ was contained by) invoked methods' class name, as what is already implemented.
        * following code is for 2. test class name (contains/ was contained by)  invoked methods' class name, which is added newly.
        */
        if (targetObjectsUnderTest.size()==0){
            //  // for test
            //  System.out.println( "targetObjectsUnderTest: "+ targetObjectsUnderTest.toString()  );
             
            /* test class name (TCN), and called method’s class name (CCN) */
            /* if TCN.lower() contains CCN.lower() */
            for (String mceName : calledMethodClassNames) { // mceName means className
                String shortmceName = mceName.substring(mceName.lastIndexOf('.')+1);
                if( testClassName.toLowerCase().contains( shortmceName.toLowerCase() )){
                    if(!targetObjectsUnderTest.contains(mceName))
                        targetObjectsUnderTest.add(mceName);
                    // // for test
                    // System.out.println( "testClassName: "+ testClassName.toString() + ", shortmceName: " +shortmceName );
                }
                /* CCN.lower() contains TCN.lower() without “test” */
                if( shortmceName.toLowerCase().contains( testClassName.toLowerCase().replaceFirst("test", "") )){
                    if(!targetObjectsUnderTest.contains(mceName))
                        targetObjectsUnderTest.add(mceName);
                    // // for test
                    // System.out.println( "testClassName: "+ testClassName.toString() + ", shortmceName: " +shortmceName );
                }
            }

            // // for test
            // System.out.println( "new added classes under test: "+ targetObjectsUnderTest.toString()  );
        }

        return targetObjectsUnderTest;
    }


    public static Boolean isValidTestMethod(MethodDeclaration md) {
        Boolean ValidTestMethod = true;

        /* valid test method */
        String testMethodName = md.getNameAsString();
        // List<AnnotationExpr> testMethodAnnotations = md.getAnnotations();
        // AccessSpecifier testMethodAccessSpecifier =  md.getAccessSpecifier();
        List<String> testMethodAnnotations = new ArrayList<>();
        for (AnnotationExpr Annotation : md.getAnnotations() ) {
            testMethodAnnotations.add(Annotation.toString());
        }
        // System.out.println("testMethodAnnotations: "  + testMethodAnnotations);
        String testMethodAccessSpecifier =  md.getAccessSpecifier().toString();
        // if( !(testMethodAccessSpecifier.equals("PUBLIC") || testMethodAccessSpecifier.equals("PACKAGE_PRIVATE")) ) {//AccessSpecifier: 必须为PUBLIC 或 PACKAGE_PRIVATE
        //     ValidTestMethod = false;
        // }
        if ( !(testMethodAnnotations.contains("@Test") || testMethodAnnotations.toString().contains("@Test(") ) ){ // Annotation: 必须为@Test 或 包含"@Test(:, 因为 @Test(expected = IllegalArgumentException.class)
            ValidTestMethod = false;
        }
        return ValidTestMethod;
    }

    public static ResolvedMethodDeclaration getResolvedMethodCallExpr(MethodCallExpr mce){
        String mceName = new String();
        String mceQualifiedSignature = null;
        ResolvedMethodDeclaration resolvedMethodCallExpr = null;
        try{
            // System.out.println( "mce.asMethodCallExpr().resolve().getQualifiedName(): " + mce.asMethodCallExpr().resolve().getQualifiedName()  );
            
            resolvedMethodCallExpr = mce.asMethodCallExpr().resolve();
        }catch(Exception e){
            // mceName = mce.getName().toString();
            System.out.println( "mce.getName(): "+mce.getName() );
            System.out.println( "ERROR: " + e.getMessage());
            // e.printStackTrace();
        }
        // System.out.println("getFullQualifiedName: "+mceName);
        return resolvedMethodCallExpr;
    }

    public static String getFullQualifiedNameOfMethodCallExpr(MethodCallExpr mce, PackageDeclaration pd){
        String pdName = pd.getNameAsString();
        String mceName = new String();

        try{
            // System.out.println( "mce.asMethodCallExpr().resolve().getQualifiedName(): " + mce.asMethodCallExpr().resolve().getQualifiedName()  );
            
            String mceQualifiedSignature = mce.asMethodCallExpr().resolve().getQualifiedSignature();
            if (mceQualifiedSignature.contains( pdName )) 
                mceName = mceQualifiedSignature.substring(0, mceQualifiedSignature.lastIndexOf('('));
        }catch(Exception e){
            if (mce.getName().toString().contains("assert")) 
                return mceName;
            
            // mceName = mce.getName().toString();
            System.out.println( "mce.getName(): "+mce.getName() );
            System.out.println( "ERROR: " + e.getMessage());
            // e.printStackTrace();
        }
        // System.out.println("getFullQualifiedName: "+mceName);
        return mceName;
    }

    /* 与输入MethodsName顺序一致。 */
    public static List<String> getFullQualifiedSignatureOfMethod(MethodDeclaration md, PackageDeclaration pd, List<String> MethodsName){
        String testMethodName = md.getNameAsString();
        // List<AnnotationExpr> testMethodAnnotations = md.getAnnotations();
        // AccessSpecifier testMethodAccessSpecifier =  md.getAccessSpecifier();
        String testMethodAnnotations = md.getAnnotations().toString();
        String testMethodAccessSpecifier =  md.getAccessSpecifier().toString();

        /* Get the test method name (TMN), and called method’s name (CMN) */
        List<MethodCallExpr> methodCalls = md.findAll(MethodCallExpr.class);
        List<String> calledMethodNames = new ArrayList<>();


        List<String> methodsFullQualifiedSignatures = new ArrayList<String>( Collections.nCopies(MethodsName.size(), " ") );
        for (MethodCallExpr mce: methodCalls){
            String mceName = new String();
            mceName = Parser.getFullQualifiedNameOfMethodCallExpr(mce, pd);

            if( MethodsName.contains(mceName)){
                int index = MethodsName.indexOf(mceName);
                String mceQualifiedSignature = mce.asMethodCallExpr().resolve().getQualifiedSignature();
                methodsFullQualifiedSignatures.set(index, mceQualifiedSignature);
            }
        }

        return methodsFullQualifiedSignatures;
    }

    /* 先出现先放，与输入MethodsName顺序不一定一致。 */
    public static List<MethodCallExpr> getMethodCallExprOfMethod(MethodDeclaration md, PackageDeclaration pd, List<String> MethodsName){
        String testMethodName = md.getNameAsString();
        // List<AnnotationExpr> testMethodAnnotations = md.getAnnotations();
        // AccessSpecifier testMethodAccessSpecifier =  md.getAccessSpecifier();
        String testMethodAnnotations = md.getAnnotations().toString();
        String testMethodAccessSpecifier =  md.getAccessSpecifier().toString();

        /* Get the test method name (TMN), and called method’s name (CMN) */
        List<MethodCallExpr> methodCalls = md.findAll(MethodCallExpr.class);
        List<String> calledMethodNames = new ArrayList<>();

        // List<MethodCallExpr> methodsCallExpr = Stream.generate(MethodCallExpr::new).limit( MethodsName.size() ).collect(Collectors.toList());
        List<MethodCallExpr> methodsCallExpr = new ArrayList<MethodCallExpr>();
        for (MethodCallExpr mce: methodCalls){
            String mceName = new String();
            mceName = Parser.getFullQualifiedNameOfMethodCallExpr(mce, pd);

            if( MethodsName.contains(mceName)){
                // int index = MethodsName.indexOf(mceName);
                // methodsCallExpr.set(index, mce);
                methodsCallExpr.add(mce); // 先出现先放，与输入MethodsName顺序不一定一致。
            }
        }

        return methodsCallExpr;
    }

    /* 以'.'分割，取前三块字符串；不足三块时，取全部 */
    public static String shortenPDName(String pdName){
        String shortPDName;
        String[] shorten_list = pdName.split("\\."); //需要用转义字符来表示 "."
        if(shorten_list.length>3){
            shortPDName = shorten_list[0] +"."+ shorten_list[1] +"."+ shorten_list[2];
        }
        else
            shortPDName = pdName;

        return shortPDName;
    }



    public static void main(String[] args) throws Exception {
        Config config = new Config();
        // String pojDir = "/home/hadoop/dfs/data/Workspace/CongyingXU/data/AutoMR/projects/CoreNLP-main";
        // String pojDir = config.DIR_DATA + "data/AutoMR/projects/fizzed__split__rocker/";
        String pojDir = config.DIR_DATA + "data/AutoMR/projects/matsim-org__split__matsim-libs/";
        System.out.println(  getPackagePathsFromPojDir(pojDir) );
        // System.out.println(  getPackagePathsFromPojDir(pojDir).size() );
    }

    public static MethodDeclaration getMethodDeclarationFromMethodCallExpr(MethodCallExpr mce) {
        // StaticJavaParser.setConfiguration(new ParserConfiguration().setSymbolResolver(MTIdentifier.symbolSolver));
        ResolvedMethodDeclaration rmd=null;
        try {
            rmd = mce.resolve();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(""+ mce + " cannot mce.resolve().");
            return null;
        }
                    
        if (rmd instanceof JavaParserMethodDeclaration) {
            JavaParserMethodDeclaration jpmd = (JavaParserMethodDeclaration) rmd;
            MethodDeclaration mdec = jpmd.getWrappedNode();
            return mdec;
        }
        return null;
    }

    public static ClassOrInterfaceDeclaration getClassDeclarationFromMethodDeclaration(MethodDeclaration md) {
        // StaticJavaParser.setConfiguration(new ParserConfiguration().setSymbolResolver(MTIdentifier.symbolSolver));
        ClassOrInterfaceDeclaration CD = null;
        try {
            CD = (ClassOrInterfaceDeclaration) md.getParentNode().get(); //.getClass()
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("getClassDeclarationFromMethodDeclaration: "+ md + " .getParentNode().get()");
            return null;
        }
        return CD;
    }

    public static Node getTheSTMT(Node expression) {
        // Node methodInvocation = mce.getParentNodeForChildren(); // method invocation
        Node expressionSTMT = expression.getParentNode().get(); // statement? No, just parent node.
        // when expressionSTMT or expressionSTMT.parent do not have ";"。 expressionSTMT.parent contains(";")停
        while(!expressionSTMT.toString().contains(";") && !expressionSTMT.getParentNode().get().toString().contains(";") )
            expressionSTMT = expressionSTMT.getParentNode().get();
        
        return expressionSTMT;
    }

    public static String getMethodReturnType(MethodCallExpr mce){
        MethodDeclaration mdec = getMethodDeclarationFromMethodCallExpr(mce);
        String name = null;
        if(mdec!=null)
            name= mdec.getType().toString();
        return name;
    }
    /* 
     * if methodName not startwith("get") -> return false;
     * 1. find all fields in CUT
     * 2. find all ReturnStmt, 
     * if a ReturnStmt consists of fields in CUT -> return getter;
     */
    public static boolean isGetter(MethodCallExpr mce){
        // if methodName not startwith("get") -> return false;
        try{
            String mceName = mce.asMethodCallExpr().resolve().getName();
            if(!mceName.startsWith("get")) return false;
        }catch(UnsolvedSymbolException e){
            return false;
        }

        boolean result = false;
        MethodDeclaration mdec = getMethodDeclarationFromMethodCallExpr(mce);
        Node classNode =  mdec.findRootNode();
        
        /* 1. find all fields in CUT */
        List<String> fieldNames = new ArrayList<>();
        List<FieldDeclaration> fieldDeclarations = classNode.findAll(FieldDeclaration.class);
        for (FieldDeclaration fieldDeclaration : fieldDeclarations) {
            // System.out.println(fieldDeclaration.getVariables()  );
            for (VariableDeclarator variableDeclarator : fieldDeclaration.getVariables()) 
                fieldNames.add( variableDeclarator.getNameAsString() );
        }
        
        // if(!declarationOfInovkedMce.getBody().isPresent()) return accessed_or_updated; // no body
        // BlockStmt MDBody = declarationOfInovkedMce.getBody().get();
        /* 2. find all ReturnStmt */
        List<ReturnStmt> returnStmts =  mdec.findAll(ReturnStmt.class);
        for (ReturnStmt returnStmt : returnStmts) {
            // System.out.println( "returnStmt.getChildNodes(): " +returnStmt.getChildNodes() );
            // System.out.println( "returnStmt.findAll(FieldAccessExpr): " +returnStmt.findAll( FieldAccessExpr.class ) );
            // System.out.println( "returnStmt.findAll(Simple): " +returnStmt.findAll( NameExpr.class ) );
            boolean thisReturnStmtResult = true;
            /* if a ReturnStmt consists of fields in CUT */
            for (Node child : returnStmt.getChildNodes()) {
                if( (child instanceof FieldAccessExpr) && child.toString().startsWith("this.") ){
                    ; //  return this.fieldname;   this.fieldname is a FieldAccessExpr
                }
                else if( (child instanceof NameExpr) && fieldNames.contains(child.toString()) ){
                    ; // return fieldName; fieldName is a NameExpr
                }
                else{
                    thisReturnStmtResult = false; 
                }
            }
            if(thisReturnStmtResult){
                result = thisReturnStmtResult;
                System.out.println( "Is getter: " + mce.toString());
                return result;
            }
                
        }
        return result;
    }

    /* 
     * if methodName not startwith("set") -> return false;
     * 1. find all fields in CUT
     * 2. find all statements, 
     * for each statement except return 
     *    if not (statement with "=", and fields of CUT in the left) -> return not setter
     * -> return setter
     */
    public static boolean isSetter(MethodCallExpr mce){
        // if methodName not startwith("set") -> return false;
        try{
            String mceName = mce.asMethodCallExpr().resolve().getName();
            if(!mceName.startsWith("set")) return false;
        }catch(UnsolvedSymbolException e){
            return false;
        }

        boolean result = true;
        MethodDeclaration mdec = getMethodDeclarationFromMethodCallExpr(mce);
        Node classNode =  mdec.findRootNode();
        
        /* 1. find all fields in CUT */
        List<String> fieldNames = new ArrayList<>();
        List<FieldDeclaration> fieldDeclarations = classNode.findAll(FieldDeclaration.class);
        for (FieldDeclaration fieldDeclaration : fieldDeclarations) {
            // System.out.println(fieldDeclaration.getVariables()  );
            for (VariableDeclarator variableDeclarator : fieldDeclaration.getVariables()) 
                fieldNames.add( variableDeclarator.getNameAsString() );
        }

        /* 2. find all statements, */
        List<Statement> statements =  mdec.getBody().get().getStatements();
        for (Statement statement : statements) {
            statement.findAll(AssignExpr.class);
            if(statement.toString().contains("=") && statement.toString().split("=")[0].contains("this.") ){
                ;// statement with "=", and (this.fields of CUT in the left )
            }
            else if(statement.toString().contains("=") && statement.findAll(NameExpr.class).size()>0 && fieldNames.contains((statement.findAll(NameExpr.class).get(0)))){
                ;// statement with "=", and (the first variale is field )
            }
            else{
                result = false; break;
            }
        }
        if(result)
            System.out.println( "Is setter: " + mce.toString());
        return result;
    }



    public static String methodSignatureToName(String Signature) {
        String Name="";
        int indexLeftBracket = Signature.indexOf("(");
        Name = Signature.substring(0, indexLeftBracket);
        return Name;
    }

    public static String GetClassFNameFromMethodFSig(String MethodFullSignature) {
        String ClassFName="";
        String methodFName = methodSignatureToName(MethodFullSignature);
        int indexLastDot = methodFName.lastIndexOf(".");
        ClassFName = methodFName.substring(0, indexLastDot);
        return ClassFName;
    }

}
