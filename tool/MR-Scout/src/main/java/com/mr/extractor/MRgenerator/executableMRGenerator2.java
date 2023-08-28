package com.mr.extractor.MRgenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import com.alibaba.fastjson.JSONObject;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.mr.extractor.Parser;
import com.mr.extractor.MRdetector.methodInvocation;
import com.mr.extractor.MRdetector.relationAssertion;
import com.mr.extractor.config.Config;
import com.mr.extractor.util.FileUtil;
import com.mr.extractor.util.JsonUtil;
import com.mr.extractor.util.LogUtil;
import com.github.javaparser.ast.comments.LineComment;


/* 宗旨，先完成，再完美。敏捷、迭代
思路：
1. locate invocation of targetMethodsUnderTests
2. extract group of inputs： 
    - 2.1 extract parameters and caller objects of MUT
        - TODO：目前，parameters已提取，caller objects还没有
    - 2.2 如何区分source input以及followUp input? // 多看看数据找idea
        - 目前，只能处理有两次invocation的case；先出现的是source，后出现的是followUp。
3. identify relation between source input and follow up input
    - 目前，follow-up input的def express中，是否都有source iput就好 // 看看比例，先解决简单的和代表性case；说不定这种就能解决大部分了
    - 理想，follow-up input的def express中，只有source iput就好
4. refactor and code generation
    - 4.1 add the source input to parameters of method, 
    - 4.2 delete the def segment of source input, generate the code
        - 目前，for each input，删除声明、初始化 （简单粗暴。。。） // 看看比例，先解决简单的和代表性case；说不定这种就能解决大部分了
        - 理想，for each input，删除第一次use前的声明、初始化和后期赋值  （有点难实现）
5. test the naturalness of generated code
    - 用原来程序中的input，作为输入；看看能否和原代码一样正常运行。
*/
public class executableMRGenerator2 {
    

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        String FILE_PATH;
        String DIR_PATH;
        String testMethodDeclarationQualifiedSignatures;
        Config config = new Config();


        if ( args.length>0){
            FILE_PATH = args[0];
            DIR_PATH = args[1];
            testMethodDeclarationQualifiedSignatures = args[2]; // all MDs in the class, they cannnot be finished in one turn
        }
        else{   
            FILE_PATH = config.DIR_DATA + "AutoMR/projects/JetBrains__split__Arend/src/test/java/org/arend/term/expr/visitor/BuiltinNormalizationTest.java";
            DIR_PATH = config.DIR_DATA + "AutoMR/projects/JetBrains__split__Arend/";
            testMethodDeclarationQualifiedSignatures = "org.arend.term.expr.visitor.BuiltinNormalizationTest.testPlusSuc()";


            FILE_PATH = config.DIR_DATA + "AutoMR/projects/hashgraph__split__hedera-services/hapi-utils/src/test/java/com/hedera/services/throttles/DeterministicThrottleTest.java";
            DIR_PATH = config.DIR_DATA + "AutoMR/projects/hashgraph__split__hedera-services/";
            testMethodDeclarationQualifiedSignatures = "com.hedera.services.throttles.DeterministicThrottleTest.usesZeroElapsedNanosOnFirstDecision()";


            FILE_PATH = config.DIR_DATA + "AutoMR/projects/apolloconfig__split__apollo/apollo-configservice/src/test/java/com/ctrip/framework/apollo/configservice/integration/NotificationControllerV2IntegrationTest.java";
            DIR_PATH = config.DIR_DATA + "AutoMR/projects/apolloconfig__split__apollo/";
            testMethodDeclarationQualifiedSignatures = "com.ctrip.framework.apollo.configservice.integration.NotificationControllerV2IntegrationTest.testPollNotificationWithMultipleNamespacesAndNotificationIdsOutDated()";


            FILE_PATH = config.DIR_DATA + "AutoMR/projects/Cornutum__split__tcases/tcases-openapi-test/src/test/java/org/cornutum/tcases/openapi/test/MediaRangeTest.java";
            DIR_PATH = config.DIR_DATA + "AutoMR/projects/Cornutum__split__tcases/";
            testMethodDeclarationQualifiedSignatures = "org.cornutum.tcases.openapi.test.Of_5()";

            FILE_PATH = config.DIR_DATA + "AutoMR/projects/Cornutum__split__tcases/tcases-openapi-test/src/test/java/org/cornutum/tcases/openapi/test/MediaRangeTest.java";
            DIR_PATH = config.DIR_DATA + "AutoMR/projects/Cornutum__split__tcases/";
            testMethodDeclarationQualifiedSignatures = "org.cornutum.tcases.openapi.test.Of_5()";


            FILE_PATH = "/ssddata1/cxubl/workProjects/dapr__split__java-sdk/sdk/src/test/java/io/dapr/utils/DurationUtilsTest.java";
            DIR_PATH = "/ssddata1/cxubl/workProjects/dapr__split__java-sdk/";
            testMethodDeclarationQualifiedSignatures = "io.dapr.utils.DurationUtilsTest.convertTimeBothWays()";
        }
        
        if(!withJunit(FILE_PATH)) {
            System.out.println("Not withJunit(FILE_PATH): " + FILE_PATH );
            // return;
        }
        String[] TMD_QSs = testMethodDeclarationQualifiedSignatures.split(Config.SPLITE_STR);
        List<relationAssertion> successRefactorSourceInputMRinstance;
        
        for (String TMD_QS : TMD_QSs) {
            String[] TMD_QS_only_one = {TMD_QS};
            MRMeta mrMeta = new MRMeta(FILE_PATH, DIR_PATH, TMD_QS_only_one);fileInit(mrMeta);
            List<relationAssertion> MRintances = mrMeta.TMQS_MRinstances.get(TMD_QS);
            successRefactorSourceInputMRinstance = new ArrayList<>();
            for (relationAssertion MRintance: MRintances) {
                boolean refactorInputResult = generateExecutableMRMethod( mrMeta, MRintance, TMD_QS); //针对MRintance进行generate
                System.out.println( "mrMeta.successfulAutoMR:" + mrMeta.successfulAutoMR );
                if (refactorInputResult){
                    // 记录 成功的relation assertion
                    successRefactorSourceInputMRinstance.add(MRintance);
                }
            }
            if(successRefactorSourceInputMRinstance.size()>0)
                // 基于成功的relation assertion， comment掉其他的assertions
                executableMRGenerator2.commentNoisyAssertions(mrMeta, successRefactorSourceInputMRinstance, TMD_QS);
            deleteIrrelevantTestMethodsAndCreateFile(mrMeta);
            updateAutoMRProfile(mrMeta);
        }
        // System.out.println("mrMeta.testFileCU: " + mrMeta.testFileCU.toString());
    }
    public static void fileInit(MRMeta mrMeta) throws FileNotFoundException {
        String FILE_PATH = mrMeta.FILE_PATH; String DIR_PATH = mrMeta.DIR_PATH; String[] TMD_QSs= mrMeta.TMD_QSs;
        
        /* init */
        TypeSolver reflectionTypeSolver = new ReflectionTypeSolver();
        CombinedTypeSolver combinedSolver = new CombinedTypeSolver();
        combinedSolver.add(reflectionTypeSolver);
        // Parser.getPackagePathsFromPojDir(DIR_PATH);
        for (String SRC_PATH : Parser.getPackagePathsFromPojDir(DIR_PATH) ) {
            TypeSolver javaParserTypeSolver = new JavaParserTypeSolver(new File(SRC_PATH)); // r looks for classes defined in source files
            combinedSolver.add(javaParserTypeSolver);
        }
        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(combinedSolver);
        StaticJavaParser
                .getConfiguration()
                .setSymbolResolver(symbolSolver);
        
        CompilationUnit cu = StaticJavaParser.parse(new FileInputStream(FILE_PATH));
        PackageDeclaration pd = cu.findAll(PackageDeclaration.class).get(0);
        String testClassName = FILE_PATH.split("/")[ FILE_PATH.split("/").length -1 ].replace(".java","");
        List<MethodDeclaration> MethodDeclarationsInTestFile = cu.findAll(MethodDeclaration.class);
        

        /* main-init */
        for (String TMD_QS : TMD_QSs) {
            String TMD_QN = TMD_QS.substring( 0, TMD_QS.indexOf("(") );
            String testMethodName = TMD_QN.substring( TMD_QN.lastIndexOf(".") +1);
            // System.out.println(testMethodName);

            List<MethodCallExpr> MUTCallExpr =  new ArrayList<MethodCallExpr>();
            MethodDeclaration testMethodDeclaration = new MethodDeclaration();
            // System.out.println("!!!!!!!"+ cu.findAll(MethodDeclaration.class));
            for (MethodDeclaration md:MethodDeclarationsInTestFile ){
                    if (md.getNameAsString().equals(testMethodName))
                        testMethodDeclaration = md;
                mrMeta.testMethodNames.add( testMethodName ); 
                mrMeta.MQS_testMethodNames.put( TMD_QS,testMethodName ); 
                mrMeta.MQS_testClassNames.put( TMD_QS,testClassName );
                mrMeta.MQS_testMethodStrings.put( TMD_QS,testMethodDeclaration.toString() );
                mrMeta.MQS_testMethodDeclarations.put( TMD_QS,testMethodDeclaration);   
            }
        }
        mrMeta.testFileCU = cu.clone();  // 后边会改动内容，所以，在起初先 clone一个origial的版本  
        mrMeta.testPackageDeclaration = pd; 
    }


    /* 针对MRintance进行generate */
    public static boolean generateExecutableMRMethod(MRMeta mrMeta, relationAssertion MRintance, String TMD_QS) throws FileNotFoundException {
        MethodDeclaration testMethodDeclaration = mrMeta.MQS_testMethodDeclarations.get(TMD_QS);
        LogUtil.logger.info("generateExecutableMRMethod: "+ TMD_QS);
        /* main*/
        boolean withInputTransformation = MRintance.withInputTransformation_Option2;
        // VariableDeclaration in method body.  for: byte[] bytes = OctetUtil.shortToBytes(in);
        List<VariableDeclarationExpr> VDEs = testMethodDeclaration.findAll(VariableDeclarationExpr.class);
        // VariableDeclaration as class field. // get varibles as the class field
        ClassOrInterfaceDeclaration testClassDeclaration = Parser.getClassDeclarationFromMethodDeclaration(testMethodDeclaration);
        List<SimpleName> allFieldNamesInClassDeclaration = new ArrayList<>();; 
        List<FieldDeclaration> fieldDeclarations= testClassDeclaration.getFields();
    
        BlockStmt testMethodBodyBlockStmt =  testMethodDeclaration.getBody().get();
        methodInvocation prevMethodInvocation=null;
        for (methodInvocation previousMethodInvocationElement : MRintance.previousMethodInvocations_involvedInputOrOutput.keySet()) {
            // 其实，里面也只有一个MethodInvocation啦
            prevMethodInvocation = previousMethodInvocationElement;            
        }
        List<String> prevMethodInvocationInputExpressions_Strings = new ArrayList<>(prevMethodInvocation.inputExpressions);
        List<String> first_MUTCallExpr_Arguments_Strings = prevMethodInvocationInputExpressions_Strings; // 因为assume 只有两个invocation
        
        LogUtil.logger.info("withInputTransformation: "+ withInputTransformation);
        boolean flag_successfulMRgeneration=false;
        if(withInputTransformation){
            boolean successfuldeleteSourceInput = false;
            List<String> addedParameters = new ArrayList<>(); // 防止parameters重复了
            List<String> newDeclaredSourceInputNames = new ArrayList<>(); // 用于hardcoded 情况
            for (Parameter parameter : testMethodDeclaration.getParameters()) {
                addedParameters.add(parameter.getType().toString() +"_"+parameter.getName().toString());
            }
            // for each input，删除第一次use前的声明及初始化 （有点难，先实现个粗暴版;仅删除声明。）
            /* [potential scenario1] VDEs: varibles declared in the MD body */
            for (VariableDeclarationExpr VDE : VDEs) {
                SimpleName declaredVariablName = VDE.getVariable(0).getName();
                Type declaredVariablType = VDE.getVariable(0).getType();
                // 2022.06.09 目前认为所有 inputs和caller object都要refactor
                if ( first_MUTCallExpr_Arguments_Strings.contains( declaredVariablName.toString())){
                    /* delete DEF segment */
                    Node expressToDelete = VDE.getParentNodeForChildren();
                    Node stmtToDelete = VDE.getParentNode().get();
                    Boolean delete_result=false;
                    if ( stmtToDelete instanceof ForEachStmt ||  stmtToDelete instanceof ForStmt ){
                        // 说明变量声明是在for condition中，则仅保留 执行代码块
                        int childSize =  stmtToDelete.getChildNodes().size();
                        delete_result = testMethodBodyBlockStmt.replace(stmtToDelete, stmtToDelete.getChildNodes().get( childSize-1 ) ); //执行代码块应该是在最后一个。。。。
                        // 参考测试用例：testGenerateExecutableMRMethod3()
                    }
                    else{
                        delete_result = stmtToDelete.getParentNode().get().remove(stmtToDelete);
                    }
                        
                    /* add addParameter */
                    if (delete_result){
                        if(!addedParameters.contains(declaredVariablType.toString() +"_"+declaredVariablName.toString())){ // 防止parameters重复了
                            if(declaredVariablType.isVarType()){ // var type
                                // try type inference
                                LogUtil.logger.info("With Var type varible: "+declaredVariablType + "  " + declaredVariablName);
                                String inferredVariableClassName = infer_type(declaredVariablName, prevMethodInvocation, testMethodDeclaration);
                                System.out.println("inferredVariableClassName: " + inferredVariableClassName);
                                if(!addedParameters.contains(inferredVariableClassName.toString() +"_"+declaredVariablName.toString())){
                                    testMethodDeclaration.addParameter(inferredVariableClassName, declaredVariablName.toString());
                                    addedParameters.add(inferredVariableClassName.toString() +"_"+declaredVariablName.toString());
                                }
                            }
                            else{
                                testMethodDeclaration.addParameter(declaredVariablType, declaredVariablName.toString());
                                addedParameters.add(declaredVariablType.toString() +"_"+declaredVariablName.toString());
                            }
                        }
                        successfuldeleteSourceInput = true;
                    }
                    else
                        LogUtil.logger.info("delete_result: "+delete_result + "  " + stmtToDelete);
                }
            }
            /* [potential scenario2]  VDEs: varibles declared as the class field */
            if(!successfuldeleteSourceInput){
                for (FieldDeclaration fieldDeclaration : fieldDeclarations) {
                    SimpleName declaredFieldlName = fieldDeclaration.getVariables().get(0).getName();
                    allFieldNamesInClassDeclaration.add(declaredFieldlName);              
                }

                // match the input string to the field, and added as the parameter.
                for (FieldDeclaration fieldDeclaration : fieldDeclarations) {
                    SimpleName declaredVariablName = fieldDeclaration.getVariable(0).getName();
                    Type declaredVariablType = fieldDeclaration.getVariable(0).getType();
                    // 2022.06.09 目前认为所有 inputs和caller object都要refactor
                    if ( first_MUTCallExpr_Arguments_Strings.contains( declaredVariablName.toString())){
                        /* skip: delete DEF segment */
                        /* add addParameter */
                        if(!addedParameters.contains(declaredVariablType.toString() +"_"+declaredVariablName.toString())){ // 防止parameters重复了
                            if(declaredVariablType.isVarType()){ // var type
                                // try type inference
                                LogUtil.logger.info("With Var type varible: "+declaredVariablType + "  " + declaredVariablName);
                                String inferredVariableClassName = infer_type(declaredVariablName, prevMethodInvocation, testMethodDeclaration);
                                if(!addedParameters.contains(inferredVariableClassName.toString() +"_"+declaredVariablName.toString())){
                                    testMethodDeclaration.addParameter(inferredVariableClassName, declaredVariablName.toString());
                                    addedParameters.add(inferredVariableClassName.toString() +"_"+declaredVariablName.toString());
                                }
                            }
                            else{
                                testMethodDeclaration.addParameter(declaredVariablType, declaredVariablName.toString());
                                addedParameters.add(declaredVariablType.toString() +"_"+declaredVariablName.toString());
                            }
                        }
                        successfuldeleteSourceInput = true;
                    }
                }
            }

            /* [potential scenario3]  source input is not a declared variable in the MD body/class field. The source input is totally hardcoded or method invocaion like "testColor.r" */
            if(!successfuldeleteSourceInput){
                /* method invocaion like "testColor.r" */
                // varibles declared in the MD body
                for (VariableDeclarationExpr VDE : VDEs) {
                    SimpleName declaredVariablName = VDE.getVariable(0).getName();
                    Type declaredVariablType = VDE.getVariable(0).getType();
                    // 2022.06.09 目前认为所有 inputs和caller object都要refactor
                    if ( first_MUTCallExpr_Arguments_Strings.toString().contains( declaredVariablName.toString())){
                        /* delete DEF segment */
                    Node expressToDelete = VDE.getParentNodeForChildren();
                    Node stmtToDelete = VDE.getParentNode().get();
                    Boolean delete_result=false;
                    if ( stmtToDelete instanceof ForEachStmt ||  stmtToDelete instanceof ForStmt ){
                        // 说明变量声明是在for condition中，则仅保留 执行代码块
                        int childSize =  stmtToDelete.getChildNodes().size();
                        delete_result = testMethodBodyBlockStmt.replace(stmtToDelete, stmtToDelete.getChildNodes().get( childSize-1 ) ); //执行代码块应该是在最后一个。。。。
                        // 参考测试用例：testGenerateExecutableMRMethod3()
                    }
                    else{
                        delete_result = stmtToDelete.getParentNode().get().remove(stmtToDelete);
                    }
                    /* add addParameter */
                    if (delete_result){
                        if(!addedParameters.contains(declaredVariablType.toString() +"_"+declaredVariablName.toString())){ // 防止parameters重复了
                            if(declaredVariablType.isVarType()){ // var type
                                // try type inference
                                LogUtil.logger.info("With Var type varible: "+declaredVariablType + "  " + declaredVariablName);
                                String inferredVariableClassName = infer_type(declaredVariablName, prevMethodInvocation, testMethodDeclaration);
                                System.out.println("inferredVariableClassName: " + inferredVariableClassName);
                                if(!addedParameters.contains(inferredVariableClassName.toString() +"_"+declaredVariablName.toString())){
                                    testMethodDeclaration.addParameter(inferredVariableClassName, declaredVariablName.toString());
                                    addedParameters.add(inferredVariableClassName.toString() +"_"+declaredVariablName.toString());
                                }
                            }
                            else{
                                testMethodDeclaration.addParameter(declaredVariablType, declaredVariablName.toString());
                                addedParameters.add(declaredVariablType.toString() +"_"+declaredVariablName.toString());
                            }
                        }
                        successfuldeleteSourceInput = true;
                    }
                    else
                        LogUtil.logger.info("delete_result: "+delete_result + "  " + stmtToDelete);
                    }
                }
                // varibles declared as the class field
                if(!successfuldeleteSourceInput){
                    for (FieldDeclaration fieldDeclaration : fieldDeclarations) {
                        SimpleName declaredVariablName = fieldDeclaration.getVariable(0).getName();
                        Type declaredVariablType = fieldDeclaration.getVariable(0).getType();
                        // 2022.06.09 目前认为所有 inputs和caller object都要refactor
                        if ( first_MUTCallExpr_Arguments_Strings.toString().contains( declaredVariablName.toString())){
                            /* skip: delete DEF segment */
                            /* add addParameter */
                            if(!addedParameters.contains(declaredVariablType.toString() +"_"+declaredVariablName.toString())){ // 防止parameters重复了
                                if(declaredVariablType.isVarType()){ // var type
                                    // try type inference
                                    LogUtil.logger.info("With Var type varible: "+declaredVariablType + "  " + declaredVariablName);
                                    String inferredVariableClassName = infer_type(declaredVariablName, prevMethodInvocation, testMethodDeclaration);
                                    if(!addedParameters.contains(inferredVariableClassName.toString() +"_"+declaredVariablName.toString())){
                                        testMethodDeclaration.addParameter(inferredVariableClassName, declaredVariablName.toString());
                                        addedParameters.add(inferredVariableClassName.toString() +"_"+declaredVariablName.toString());
                                    }
                                }
                                else{
                                    testMethodDeclaration.addParameter(declaredVariablType, declaredVariablName.toString());
                                    addedParameters.add(declaredVariablType.toString() +"_"+declaredVariablName.toString());
                                }
                            }
                            successfuldeleteSourceInput = true;
                        }
                    }
                    
                }
                
                /* totally hardcoded */
                if(!successfuldeleteSourceInput){
                    for (String Argument : first_MUTCallExpr_Arguments_Strings) {
                        // 说明 Argument-contained varibale 已经作为了parameter; 只是被删了没找到
                        boolean flag_alreadyrefactored = false;
                        for (String addedParameterTypeandName : addedParameters) {
                            if(Argument.contains( addedParameterTypeandName.split("_")[1] )) // 说明 Argument-contained varibale 已经作为了parameter
                                flag_alreadyrefactored = true;
                            if(flag_alreadyrefactored) break;
                        }
                        if(flag_alreadyrefactored) break;

                        // try type inference
                        LogUtil.logger.info("hardcoded: "+ Argument);
                        String inferredVariableClassName = infer_type(Argument, prevMethodInvocation, testMethodDeclaration);
                        String newDeclaredSourceInputName = "input" + newDeclaredSourceInputNames.size();
                        if(!addedParameters.contains(inferredVariableClassName.toString() +"_"+newDeclaredSourceInputName.toString())){ // 防止parameters重复了
                            newDeclaredSourceInputNames.add(newDeclaredSourceInputName);
                            testMethodDeclaration.addParameter(inferredVariableClassName, newDeclaredSourceInputName);
                            addedParameters.add(inferredVariableClassName.toString() +"_"+newDeclaredSourceInputName);
                        }
                        
                        
                        // change hardcoded value to varible: method("12345") -> method(input0)
                        for (MethodCallExpr mce : testMethodDeclaration.findAll(MethodCallExpr.class) ) {
                            String mceQualifiedSignature = "";
                            try {
                                mceQualifiedSignature = mce.asMethodCallExpr().resolve().getQualifiedSignature(); 
                            } catch (Exception UnsolvedSymbolException) {}

                            // locate the mce
                            if( mceQualifiedSignature.equals( prevMethodInvocation.mceQualifiedSignature) ||
                            (mceQualifiedSignature.equals("") && prevMethodInvocation.mceQualifiedSignature.contains( "."+mce.getNameAsString()+".(" )) ){ // is this mce
                                NodeList inputArguementsNodes = mce.getArguments();
                                int index = 0;
                                for (Object inputParametersNode : inputArguementsNodes) {
                                    String pureInputParametersNode = inputParametersNode.toString().replaceAll("\"", ""); // 2019-01-01 -> 2019-01-01
                                    String pureArgument = Argument.toString().replaceAll("\"", ""); // "2019-01-01" -> 2019-01-01, 否则 2019-01-01 != "2019-01-01" 造成误报
                                    if(pureInputParametersNode.equals(pureArgument) ){ // is this argument
                                        mce.setArgument(index, new NameExpr(newDeclaredSourceInputName)); // set this argument
                                        successfuldeleteSourceInput = true;
                                    }
                                    index += 1;
                                }
                            }
                        }
                    }
                }

            }

            if (successfuldeleteSourceInput){
                /* delete @Test annotation */ // 暂时还没有实现，不过，这个不太重要啦。
                NodeList<AnnotationExpr> AnnotationExprs = testMethodDeclaration.getAnnotations();
                // System.out.println( "AnnotationExprs: " + AnnotationExprs.get(0).remove());
                for (int i = 0; i < AnnotationExprs.size(); i++) {
                    AnnotationExpr anno = AnnotationExprs.get(i);
                    if (anno.getNameAsString().equals("Test") ){
                        anno.remove();
                    }  
                }
                /* set method Name */
                if(!testMethodDeclaration.getNameAsString().contains( Config.TOOL_NAME) ) // 因为是循环构造，可能已经被加上了
                    testMethodDeclaration.setName( testMethodDeclaration.getNameAsString() + "_" + Config.TOOL_NAME );
                flag_successfulMRgeneration = true;
            }
        }

        if(withInputTransformation && flag_successfulMRgeneration){
            String orignalMTC_FQS = MRintance.FQS_testCaseUnderAnalysis;
            String orignalTestClassFQN = orignalMTC_FQS.substring(0, orignalMTC_FQS.lastIndexOf("."));
            mrMeta.orignalMTC_FQS = orignalMTC_FQS;
            mrMeta.orignalTestClassFQN = orignalTestClassFQN;
            mrMeta.generatedMRMethodSignature = testMethodDeclaration.getSignature().toString();

            mrMeta.successfulAutoMR = flag_successfulMRgeneration;
            mrMeta.MQS_generatedMRMethodDeclaration.put(TMD_QS, testMethodDeclaration);
            mrMeta.MQS_generatedExecutableMRMethodString.put(TMD_QS, testMethodDeclaration.toString());
            mrMeta.successfulExtractedOriginal_MQS.add(TMD_QS);
            mrMeta.successfulGeneratedMR_methodNames.add(testMethodDeclaration.getNameAsString());

            String targetObjectsUnderTest = MRintance.FQN_targetClassUnderTest;

            if(mrMeta.successfulExtractedOriginal_MQS.contains(TMD_QS)) 
                mrMeta.successfulExtractedOriginal_MQS.add(TMD_QS);
            if(mrMeta.successfulGeneratedMR_methodNames.contains(testMethodDeclaration.getNameAsString())) 
                mrMeta.successfulGeneratedMR_methodNames.add(testMethodDeclaration.getNameAsString());
            if(!mrMeta.MQS_targetObjectsUnderTest.keySet().contains(TMD_QS)){
                mrMeta.MQS_targetObjectsUnderTest.put(TMD_QS, new ArrayList<>());
            }
            mrMeta.MQS_targetObjectsUnderTest.get(TMD_QS).add(targetObjectsUnderTest);
        }
        LogUtil.logger.info("flag_successfulMRgeneration: "+ flag_successfulMRgeneration);
        return withInputTransformation && flag_successfulMRgeneration;
    }

    /* 
    * VDEs: [CoordIJ ij1 = new CoordIJ(0, 0), CoordIJ ij2 = new CoordIJ(1, 10), CoordIJ ij3 = new CoordIJ(0, 0)]
    * node: ij1
    */
    public static boolean isDeclaredVariable(Node node, List<VariableDeclarationExpr> VDEs) {
        for (VariableDeclarationExpr VDE : VDEs) {
            SimpleName declaredVariablName = VDE.getVariable(0).getName();
            if ( declaredVariablName.toString().equals( node.toString() ) )
                return true;
        }
        return false;
    }

    /* 
     * change test class's name -> ""_toolName
     * delete irrelevant methods
     * add generated MR method
     * generate file // 与原test文件放在同一目录？ 可以在项目最外层写个json，记录这些信息。
    */
    public static void deleteIrrelevantTestMethodsAndCreateFile(MRMeta mrMeta) {
        if (!mrMeta.successfulAutoMR) return;
        CompilationUnit generatedMRFileCU = mrMeta.testFileCU.clone();
        List<String> testMethodNames = mrMeta.testMethodNames;
        List<String> successfulGneratedMRMethodNames = new ArrayList<>();

        /* change test class's name */
        /* delete irrelevant methods */  /* rename构造器*/
        /* add generated MR method */
        /* delete irralevant classes and add import */
        for (ClassOrInterfaceDeclaration cd : generatedMRFileCU.findAll(ClassOrInterfaceDeclaration.class)) {
            List<String> toaddMDlistInThisCD = new ArrayList<>();
            String testClassName = cd.getNameAsString(); 
            boolean flag_thisclass_contains_AutoMR = false; // 判断这个class是否改名_autoMR

            for (MethodDeclaration md: cd.findAll(MethodDeclaration.class)){
                String testMethodAnnotations = md.getAnnotations().toString();
                String testMethodAccessSpecifier =  md.getAccessSpecifier().toString();
                // if( testMethodAccessSpecifier.equals("PUBLIC") && testMethodAnnotations.contains("@Test") ) { 
                if( testMethodAnnotations.contains("@Test") ) { 
                    // 说明是test methods，删。。。。。
                    cd.remove(md);
                    if ( testMethodNames.contains( md.getNameAsString() ) )
                        toaddMDlistInThisCD.add( md.getNameAsString() );
                }

            }
            if(toaddMDlistInThisCD.size()>0){
                for (String testMethodName : toaddMDlistInThisCD) {
                    for (String TMD_QS : mrMeta.MQS_generatedMRMethodDeclaration.keySet()) {
                        String MethodName = mrMeta.MQS_testMethodNames.get(TMD_QS);
                        if (MethodName.equals(testMethodName)){
                            MethodDeclaration generatedMRMethodDeclaration = mrMeta.MQS_generatedMRMethodDeclaration.get(TMD_QS);
                            cd.addMember(generatedMRMethodDeclaration);
                            successfulGneratedMRMethodNames.add(MethodName);

                            flag_thisclass_contains_AutoMR = true;
                        }  
                    }
                }
            }
            // for (String TMD_QS : mrMeta.TMD_QSs) {
            //     MethodDeclaration generatedMRMethodDeclaration = mrMeta.MQS_generatedMRMethodDeclaration.get(TMD_QS);
            //     cd.addMember(generatedMRMethodDeclaration); 
            // }
            
            // String originalclassFullyQualifiedName = cd.asClassOrInterfaceDeclaration().resolve().getQualifiedName(); 放这里会有// Exception in thread "main" java.lang.IllegalStateException: error，所以放到else里面了
            String generatedMRMethodName = null;
            if (flag_thisclass_contains_AutoMR){
                generatedMRMethodName = successfulGneratedMRMethodNames.get(0);
                cd.setName(testClassName+"_"+ generatedMRMethodName +"_"+ Config.TOOL_NAME);
                mrMeta.generatedMRClassFQN = mrMeta.testPackageDeclaration.getNameAsString() + "." + testClassName+"_"+Config.TOOL_NAME;//只会记录最后一个class。。。。一般也只有一个。。。
                if(cd.getExtendedTypes().size()==0) cd.addExtendedType(testClassName);

                // /* 删掉构造器*/
                // for (ConstructorDeclaration ConstructorD : cd.findAll(ConstructorDeclaration.class)) {
                //     if( ConstructorD.getNameAsString().equals( cd.getNameAsString() ) ||  ConstructorD.getNameAsString().equals( cd.getNameAsString().replace("_"+ generatedMRMethodName +"_"+ Config.TOOL_NAME, "") ) || ConstructorD.getNameAsString().equals( cd.getNameAsString().replace("_"+ generatedMRMethodName +"_"+ Config.OriginalMRcases, ""))) // class名可能已加上"_AutoMR"/"_OriginalMRcases",因为是循环操作
                //         ConstructorD.remove();
                // }
                /* rename构造器*/
                for (ConstructorDeclaration ConstructorD : cd.findAll(ConstructorDeclaration.class)) {
                    if( ConstructorD.getNameAsString().equals( cd.getNameAsString() ) ||  ConstructorD.getNameAsString().equals( cd.getNameAsString().replace("_"+ generatedMRMethodName +"_"+ Config.TOOL_NAME, "") ) || ConstructorD.getNameAsString().equals( cd.getNameAsString().replace("_"+ generatedMRMethodName +"_"+ Config.OriginalMRcases, ""))) // class名可能已加上"_AutoMR"/"_OriginalMRcases",因为是循环操作
                        ConstructorD.setName(testClassName+"_"+ generatedMRMethodName +"_"+ Config.TOOL_NAME);
                }
            }
            else{// 不改名的话，
                // if private：pass （subclass未private不影响编译，不会冲突）。 else: 则删除。。并引入 import
                if( "PRIVATE".equals( cd.getAccessSpecifier().toString() ) ){
                    // pass, 不操作
                }
                else{
                    try {
                        String originalclassFullyQualifiedName = cd.asClassOrInterfaceDeclaration().resolve().getQualifiedName();
                        cd.remove();
                    
                        if( successfulGneratedMRMethodNames.size()>0 ){ // 如果没有successfulGneratedMRMethodName的话，应该也不会有 class名已加上"_AutoMR"的问题
                            generatedMRMethodName = successfulGneratedMRMethodNames.get(0);
                            originalclassFullyQualifiedName=originalclassFullyQualifiedName.replace("_"+generatedMRMethodName+"_"+Config.TOOL_NAME, ""); // class名可能已加上"_AutoMR",因为是循环操作,先给复原了
                            originalclassFullyQualifiedName=originalclassFullyQualifiedName.replace("_"+generatedMRMethodName+"_"+Config.OriginalMRcases, ""); // class名可能已加上"_OriginalMRcases",因为是循环操作,先给复原了
                        }
                        generatedMRFileCU.addImport(originalclassFullyQualifiedName);
                        // System.out.println("after: "+generatedMRFileCU);
                        // System.out.println("");  
                    } catch (Exception e) {
                        // Exception in thread "main" java.lang.IllegalStateException: The node is not inserted in a CompilationUnit
                    }
    
                }
            }
        }  
        /* import CUT */
        List<String> CUTs_QN = new ArrayList<>();
        for (String TMD_QS : mrMeta.MQS_generatedMRMethodDeclaration.keySet()) {
            List<String> CUTS = mrMeta.MQS_targetObjectsUnderTest.get(TMD_QS);

            for (String CUT : CUTS) {
                if(!CUTs_QN.contains(CUT)){
                    CUTs_QN.add(CUT);
                    generatedMRFileCU.addImport(CUT); // 无效，并不显示。。。
                    generatedMRFileCU.addImport(new ImportDeclaration(CUT, true, true)); // add import static CUT.*;
                    // generatedMRFileCU.addImport(new ImportDeclaration(CUT, false, false)); // 这样无法成功addimport，不知为啥。。 
                }  
            }  
        }
        
        /* generate file */
        // String filePath = mrMeta.FILE_PATH.replace(".java", "_" + Config.TOOL_NAME + ".java"); // 多个MR methods 合成于1个AutoMR file
        if( successfulGneratedMRMethodNames.size()>1) System.out.println( "successfulGneratedMRMethodNames.size()>1 not right, should be only one");
        String generatedMRMethodName = successfulGneratedMRMethodNames.get(0);
        mrMeta.generatedMRClassFQN = mrMeta.generatedMRClassFQN.replace(Config.TOOL_NAME, generatedMRMethodName +"_"+ Config.TOOL_NAME);
        String filePath = mrMeta.FILE_PATH.replace(".java", "_" + generatedMRMethodName +"_"+ Config.TOOL_NAME + ".java"); // 单个MR methods 合成于单个AutoMR file
        String content = generatedMRFileCU.toString();
        FileUtil.writeTextFile(filePath, content);

        mrMeta.generatedMRFileCU = generatedMRFileCU; // mrMeta.generatedMRFilePath
        mrMeta.generatedMRFilePath = filePath;
        




        /* for comparsion, baseline: Original MRs-embedded cases VS. generated MRs cases */ 
        CompilationUnit onlyOriginalMRFileCU = mrMeta.testFileCU.clone();    
        /* change test class's name */
        /* delete irrelevant methods */
        for (ClassOrInterfaceDeclaration cd : onlyOriginalMRFileCU.findAll(ClassOrInterfaceDeclaration.class)) {
            List<String> toaddMDlistInThisCD = new ArrayList<>();
            String testClassName = cd.getNameAsString(); 
            boolean flag_thisclass_contains_OriginalMRcases = false; // 判断这个class是否要改名为_OriginalMRcases
            
            /* delete irrelevant methods */
            for (MethodDeclaration md: cd.findAll(MethodDeclaration.class)){
                String testMethodAnnotations = md.getAnnotations().toString();
                String testMethodAccessSpecifier =  md.getAccessSpecifier().toString();
                if( testMethodAnnotations.contains("@Test") ) { 
                    // 说明是test methods，判断下，是否保留（relevant）
                    if (!successfulGneratedMRMethodNames.contains( md.getNameAsString() ) )
                        cd.remove(md);
                    else
                        flag_thisclass_contains_OriginalMRcases = true;

                }
            }

            // String originalclassFullyQualifiedName = cd.asClassOrInterfaceDeclaration().resolve().getQualifiedName();
            if (flag_thisclass_contains_OriginalMRcases){ // 该class改名的话
                generatedMRMethodName = successfulGneratedMRMethodNames.get(0);
                cd.setName(testClassName+"_"+ generatedMRMethodName +"_"+ Config.OriginalMRcases);
                if(cd.getExtendedTypes().size()==0) cd.addExtendedType(testClassName);

                // /* 删掉构造器*/
                // for (ConstructorDeclaration ConstructorD : cd.findAll(ConstructorDeclaration.class)) {
                //     if( ConstructorD.getNameAsString().equals( cd.getNameAsString() ) ||  ConstructorD.getNameAsString().equals( cd.getNameAsString().replace("_"+ generatedMRMethodName +"_"+ Config.TOOL_NAME, "") ) || ConstructorD.getNameAsString().equals( cd.getNameAsString().replace("_"+ generatedMRMethodName +"_"+ Config.OriginalMRcases, ""))) // class名可能已加上"_AutoMR"/"_OriginalMRcases",因为是循环操作
                //         ConstructorD.remove();
                // }
                /* rename构造器*/
                for (ConstructorDeclaration ConstructorD : cd.findAll(ConstructorDeclaration.class)) {
                    if( ConstructorD.getNameAsString().equals( cd.getNameAsString() ) ||  ConstructorD.getNameAsString().equals( cd.getNameAsString().replace("_"+ generatedMRMethodName +"_"+ Config.TOOL_NAME, "") ) || ConstructorD.getNameAsString().equals( cd.getNameAsString().replace("_"+ generatedMRMethodName +"_"+ Config.OriginalMRcases, ""))) // class名可能已加上"_AutoMR"/"_OriginalMRcases",因为是循环操作
                        ConstructorD.setName(testClassName+"_"+ generatedMRMethodName +"_"+ Config.OriginalMRcases);
                }
            }
            else{// 该class不改名的话
                // if private：pass （subclass为private不影响编译，不会冲突）。 else: 则删除。。并将该class 作为 import
                if( "PRIVATE".equals( cd.getAccessSpecifier().toString() ) ){
                    // pass, 不操作
                }
                else{
                    try {
                        String originalclassFullyQualifiedName = cd.asClassOrInterfaceDeclaration().resolve().getQualifiedName();
                        cd.remove();

                        if( successfulGneratedMRMethodNames.size()>0 ){ // 如果没有successfulGneratedMRMethodName的话，应该也不会有 class名已加上"_AutoMR"的问题
                            generatedMRMethodName = successfulGneratedMRMethodNames.get(0);
                            originalclassFullyQualifiedName=originalclassFullyQualifiedName.replace("_"+generatedMRMethodName+"_"+Config.TOOL_NAME, ""); // class名可能已加上"_AutoMR",因为是循环操作,先给复原了
                            originalclassFullyQualifiedName=originalclassFullyQualifiedName.replace("_"+generatedMRMethodName+"_"+Config.OriginalMRcases, ""); // class名可能已加上"_OriginalMRcases",因为是循环操作,先给复原了
                        }
                        onlyOriginalMRFileCU.addImport(originalclassFullyQualifiedName); // 该class应该是子类class了
                    } catch (Exception e) {
                        // Exception in thread "main" java.lang.IllegalStateException: The node is not inserted in a CompilationUnit
                    }
                    
                }
            }
        }

        /* import CUT */
        CUTs_QN = new ArrayList<>();
        for (String TMD_QS : mrMeta.MQS_generatedMRMethodDeclaration.keySet()) {
            List<String> CUTS = mrMeta.MQS_targetObjectsUnderTest.get(TMD_QS);
            for (String CUT : CUTS) {
                if(!CUTs_QN.contains(CUT)){
                    CUTs_QN.add(CUT);
                    onlyOriginalMRFileCU.addImport(CUT); // 无效，并不显示。。。
                    onlyOriginalMRFileCU.addImport(new ImportDeclaration(CUT, true, true)); // add import static CUT.*;
                    // onlyOriginalMRFileCU.addImport(new ImportDeclaration(CUT, false, false)); // 这样无法成功addimport，不知为啥。。
                    
                }  
            }  
        }

        /* generate file */
        String OriginalMRcasesfilePath = mrMeta.FILE_PATH.replace(".java", "_" + generatedMRMethodName +"_"+ Config.OriginalMRcases + ".java");
        String OriginalMRcasescontent = onlyOriginalMRFileCU.toString();
        FileUtil.writeTextFile(OriginalMRcasesfilePath, OriginalMRcasescontent);

        mrMeta.generatedonlyOriginalMRFileCU = onlyOriginalMRFileCU; // mrMeta.generatedonlyOriginalMRFilePath
        mrMeta.generatedonlyOriginalMRFilePath = OriginalMRcasesfilePath;
    }

    public static void updateAutoMRProfile(MRMeta mrMeta) {
        String AutoMRProfileJSONPath = mrMeta.DIR_PATH + "/" + Config.PATH_AUTOMR_PROFILE_JSON;
        AutoMRProfileJSONPath = AutoMRProfileJSONPath.replace("//", "/");

        HashMap<String, HashMap> AutoMRProfile = new HashMap<>();
        if (FileUtil.fileExisting(AutoMRProfileJSONPath)){
            String  AutoMRProfileJSONString = JsonUtil.readJsonFileAsString(AutoMRProfileJSONPath);
            AutoMRProfile = JSONObject.parseObject(AutoMRProfileJSONString, HashMap.class);
        }
        if(mrMeta.generatedMRFilePath!=null) {
            HashMap<String, String> MRFile_ele = new HashMap<>();
            MRFile_ele.put("generatedMRClassFQN", mrMeta.generatedMRClassFQN);
            MRFile_ele.put("generatedOrgianlMTClassFQN", mrMeta.generatedMRClassFQN.replace(Config.TOOL_NAME,Config.OriginalMRcases));

            /* 增加ClassessUnderTest的信息 */
            String FQN_classesUnderTest = "";
            HashSet<String> FQN_classesUnderTest_set = new HashSet<>();
            for (String MQS : mrMeta.successfulExtractedOriginal_MQS) {
                for (String classquifiedName : mrMeta.MQS_targetObjectsUnderTest.get(MQS)) {
                    if(!FQN_classesUnderTest_set.contains(classquifiedName)){
                        FQN_classesUnderTest += classquifiedName + ","; // splited with ”， “
                        FQN_classesUnderTest_set.add(classquifiedName);
                    }
                }
            }
            if (FQN_classesUnderTest.lastIndexOf(",")>-1)
                FQN_classesUnderTest = FQN_classesUnderTest.substring(0, FQN_classesUnderTest.lastIndexOf(",")); // 去掉某位的 ”， “
            MRFile_ele.put("ClassessUnderTest", FQN_classesUnderTest);

            /* 增加targetMethodsUnderTest的信息 */
            // TODO 2022.1120
            HashSet<String> allTargetMethodsUnderTestSet = new HashSet<>();
            for (String TMD_QS : mrMeta.MQS_targetMethodsUnderTest.keySet()) {
                for (String MQS_MUT : mrMeta.MQS_targetMethodsUnderTest.get(TMD_QS)) {
                    allTargetMethodsUnderTestSet.add(MQS_MUT);
                }
                for (String MQS_MUT : mrMeta.MQS_targetMethodsFromObjectsUnderTest.get(TMD_QS)) {
                    allTargetMethodsUnderTestSet.add(MQS_MUT);
                }
            }
            // 合成字符串
            String allTargetMethodsUnderTestSet_str = "";
            for (String MQS_MUT: allTargetMethodsUnderTestSet) {
                allTargetMethodsUnderTestSet_str += "," + MQS_MUT;
            }
            if (allTargetMethodsUnderTestSet_str.lastIndexOf(",")>-1)
                allTargetMethodsUnderTestSet_str = allTargetMethodsUnderTestSet_str.substring(1); //去掉首位的',''
            MRFile_ele.put("MethodsUnderTest", allTargetMethodsUnderTestSet_str);
            /* 增加Methodsfrom CUT的信息 */

            // 增加：
            // -   original class FQN
            // -   original MTC FQN
            // -  fully qualified name of generated AutoMR method.
            MRFile_ele.put("orignalMTC_FQS", mrMeta.orignalMTC_FQS);
            MRFile_ele.put("orignalTestClassFQN", mrMeta.orignalTestClassFQN);
            MRFile_ele.put("generatedMRMethodSignature", mrMeta.generatedMRMethodSignature);
            
            
            AutoMRProfile.put(mrMeta.generatedMRFilePath, MRFile_ele);    
        }   
        JsonUtil.writeJson(AutoMRProfileJSONPath, AutoMRProfile, true); 
    }


    public static boolean withJunit(String FILE_PATH) throws IOException {
        String content = FileUtil.readTextFile(FILE_PATH);
        if( content.toLowerCase().contains(".junit") ){
            return true;
        }
        return false;
    }

    /* infer the type of VAR varible, based on the invoked method 
     * 1. identify the input parameter's types of the invoked method
     * 2. identify the object receiver's types of the invoked method
     * 
     * 3. identify the role of VAR varible, input parameter or object receiver?
     * 4. return corresponding type 
    */
    public static String infer_type(SimpleName VariablName, methodInvocation MethodInvocation, MethodDeclaration testMethodDeclaration){
        return infer_type(VariablName.toString(), MethodInvocation, testMethodDeclaration);
    }

    public static String infer_type(String VariablName, methodInvocation MethodInvocation, MethodDeclaration testMethodDeclaration){
        if(testMethodDeclaration==null)
            return infer_type(VariablName.toString(), MethodInvocation);
        
        String inferredVariableClassName = null;

        // TODO, 完全用mce重改
        MethodCallExpr mce = null;
        for (MethodCallExpr mcePotentail : testMethodDeclaration.findAll(MethodCallExpr.class) ) {
            String mceQualifiedSignaturePotentail = "";
            try {
                mceQualifiedSignaturePotentail = mcePotentail.asMethodCallExpr().resolve().getQualifiedSignature(); 
            } catch (Exception UnsolvedSymbolException) {}

            // locate the mce
            if( mceQualifiedSignaturePotentail.equals( MethodInvocation.mceQualifiedSignature) ||
            (mceQualifiedSignaturePotentail.equals("") && MethodInvocation.mceQualifiedSignature.contains( "."+mcePotentail.getNameAsString()+".(" )) ){ // is this mce
                mce = mcePotentail;
                break;
            }
        }
        if(Parser.getMethodDeclarationFromMethodCallExpr(mce)==null) // 降维处理
            return infer_type(VariablName.toString(), MethodInvocation);

        /* 1. identify the input parameter's types of the invoked method */
        String mceQualifiedSignature = MethodInvocation.mceQualifiedSignature; //  "com.hedera.services.throttles.DeterministicThrottle.allow(int, java.time.Instant)"
        System.out.println(  "mceQualifiedSignature: "  + mceQualifiedSignature);
        String mceMethodFullNameString = mceQualifiedSignature.split("\\(")[0] ; // com.hedera.services.throttles.DeterministicThrottle.allow(
        String mceparameterTypesString = mceQualifiedSignature.split("\\(")[1].split("\\)")[0]; // int, java.time.Instant
        List<String> parameterTypesNamesArray = new ArrayList<>();
        System.out.println(  "mceparameterTypesString: "  + mceparameterTypesString);
        MethodDeclaration declarationOfInovkedMce = Parser.getMethodDeclarationFromMethodCallExpr(mce);
        Parameter parameter=null;
        NodeList parametersOfInovkedMceDeclaration = declarationOfInovkedMce.getParameters();
        for (int index = 0; index < parametersOfInovkedMceDeclaration.size(); index++) {
            parameter = (Parameter) parametersOfInovkedMceDeclaration.get(index);
            parameterTypesNamesArray.add( parameter.getTypeAsString() );
            // System.out.println(  "parameter.getTypeAsString(): " + parameter.getTypeAsString() );
        }

        /* 2. identify the object receiver's types of the invoked method */
        int len = mceMethodFullNameString.split("\\.").length;
        String objectReceiverTypesName;
        if(len==0) objectReceiverTypesName = mceMethodFullNameString;
        else objectReceiverTypesName = mceMethodFullNameString.split("\\.")[len-2]; //最后一个：invoked method name，倒数第二是：invoked class name
        System.out.println(  "objectReceiverTypesName: "  + objectReceiverTypesName);

        /* 3. identify the role of VAR varible, input parameter or object receiver */
        // if( MethodInvocation.objectReceiverName.equals( VariablName ) )
        //     inferredVariableClassName = objectReceiverTypesName;
        // elif( MethodInvocation.inputArgumentsNames.contains( VariablName ) ){
        //     int index = MethodInvocation.inputArgumentsNames.find( VariablName );
        //     System.out.println( index );
        // }

        System.out.println( "MethodInvocation.inputExpressions: " + MethodInvocation.inputExpressions );
        if( MethodInvocation.inputExpressions.contains( VariablName ) ){
            int index = MethodInvocation.inputExpressions.indexOf( VariablName );
            if(MethodInvocation.inputExpressions.size()== parameterTypesNamesArray.size())// 说明 object receiver 不是input
                inferredVariableClassName = parameterTypesNamesArray.get(index);
            else if(index ==  MethodInvocation.inputExpressions.size()-1 )// 当object receiver是input时， 最后一个 input expression，是object receiver
                inferredVariableClassName = objectReceiverTypesName;
            else // ndex == parameterTypesNamesArray.size() objectReceiverTypesName
                inferredVariableClassName = parameterTypesNamesArray.get(index);
            // parameterTypesNamesArray 识别可能是不准的，比如：setCryptoAllowances(final SortedMap<EntityNum, Long> cryptoAllowances) 就会识别为：[EntityNum, Long>]
                
        }

        
        System.out.println( VariablName + " . " + inferredVariableClassName  );
        return inferredVariableClassName;
    }

    public static String infer_type(String VariablName, methodInvocation MethodInvocation){
        String inferredVariableClassName = null;
        // mce 不支持序列化。。。。所以，无法直接从MethodInvocation.mce.getArguments() 获取
        /* 1. identify the input parameter's types of the invoked method */
        String mceQualifiedSignature = MethodInvocation.mceQualifiedSignature; //  "com.hedera.services.throttles.DeterministicThrottle.allow(int, java.time.Instant)"
        System.out.println(  "mceQualifiedSignature: "  + mceQualifiedSignature);
        String mceMethodFullNameString = mceQualifiedSignature.split("\\(")[0] ; // com.hedera.services.throttles.DeterministicThrottle.allow(
        String mceparameterTypesString = mceQualifiedSignature.split("\\(")[1].split("\\)")[0]; // int, java.time.Instant
        List<String> parameterTypesNamesArray = new ArrayList<>();
        System.out.println(  "mceparameterTypesString: "  + mceparameterTypesString);
        for (String parameterType : mceparameterTypesString.split(",")) {
            int len = parameterType.split("\\.").length;
            if(len==0) parameterTypesNamesArray.add( parameterType );
            else parameterTypesNamesArray.add( parameterType.split("\\.")[len-1] ) ; // 取最后一个
        }
        System.out.println(  "parameterTypesNames: "  + parameterTypesNamesArray);
        // parameterTypesNamesArray 识别可能是不准的，比如：setCryptoAllowances(final SortedMap<EntityNum, Long> cryptoAllowances) 就会识别为：[EntityNum, Long>]

        /* 2. identify the object receiver's types of the invoked method */
        int len = mceMethodFullNameString.split("\\.").length;
        String objectReceiverTypesName;
        if(len==0) objectReceiverTypesName = mceMethodFullNameString;
        else objectReceiverTypesName = mceMethodFullNameString.split("\\.")[len-2]; //最后一个：invoked method name，倒数第二是：invoked class name
        System.out.println(  "objectReceiverTypesName: "  + objectReceiverTypesName);


        /* 3. identify the role of VAR varible, input parameter or object receiver */
        // if( MethodInvocation.objectReceiverName.equals( VariablName ) )
        //     inferredVariableClassName = objectReceiverTypesName;
        // elif( MethodInvocation.inputArgumentsNames.contains( VariablName ) ){
        //     int index = MethodInvocation.inputArgumentsNames.find( VariablName );
        //     System.out.println( index );
        // }

        System.out.println( "MethodInvocation.inputExpressions: " + MethodInvocation.inputExpressions );
        if( MethodInvocation.inputExpressions.contains( VariablName ) ){
            int index = MethodInvocation.inputExpressions.indexOf( VariablName );
            if(MethodInvocation.inputExpressions.size()== parameterTypesNamesArray.size())// 说明 object receiver 不是input
                inferredVariableClassName = parameterTypesNamesArray.get(index);
            else if(index ==  MethodInvocation.inputExpressions.size()-1 )// 当object receiver是input时， 最后一个 input expression，是object receiver
                inferredVariableClassName = objectReceiverTypesName;
            else // ndex == parameterTypesNamesArray.size() objectReceiverTypesName
                inferredVariableClassName = parameterTypesNamesArray.get(index);
            // parameterTypesNamesArray 识别可能是不准的，比如：setCryptoAllowances(final SortedMap<EntityNum, Long> cryptoAllowances) 就会识别为：[EntityNum, Long>]
                
        }
        
        System.out.println( VariablName + " . " + inferredVariableClassName  );
        return inferredVariableClassName;
    }



    /* comment other noisy assertions */
    public static void commentNoisyAssertions(MRMeta mrMeta, List<relationAssertion> successRefactorSourceInputMRinstance, String TMD_QS) {
        // // 2023.03.18 不足：block会被当作一个statement；noisy assertion在block中时，会被忽略。。。
        // mrMeta.MQS_generatedMRMethodDeclaration.put(TMD_QS, testMethodDeclaration);
        
        // to update: 
        // mrMeta.MQS_generatedMRMethodDeclaration.put(TMD_QS, testMethodDeclaration);
        // mrMeta.MQS_generatedExecutableMRMethodString.put(TMD_QS, testMethodDeclaration.toString());
        // mrMeta.successfulExtractedOriginal_MQS.add(TMD_QS);
        // mrMeta.successfulGeneratedMR_methodNames.add(testMethodDeclaration.getNameAsString());

        MethodDeclaration testMethodDeclaration = mrMeta.MQS_generatedMRMethodDeclaration.get(TMD_QS);
        List<Statement> statements = testMethodDeclaration.getBody().get().getStatements();
        // System.out.println("testMethodDeclaration: " + testMethodDeclaration.toString());

        for (int i = 0; i < statements.size(); i++) {
            Statement statement = statements.get(i);
            System.out.println("statement: " + statement);
            if (isAssertionStatement(statement)) {
                boolean isNoisy = true;

                for (relationAssertion successAssertion : successRefactorSourceInputMRinstance) {
                    String assertionStmt = successAssertion.assertionSTMT;
                    if (statement.toString().trim().contains(assertionStmt.toString()) || assertionStmt.toString().contains(statement.toString().trim())) { // contains, rether than "equals". because statement may have ",", but assertionStmt may not.
                        isNoisy = false;
                        break;
                    }
                }

                if (isNoisy) {
                    System.out.println("remove: " + statement);
                    statement.remove();
                    i--; //因为删了一个statements后，下一个statement为当前index数值；用一个i-- 抵消 i++
                    // System.out.println("statements.size(): "+ statements.size());
                    // addSingleLineCommentToStatement(statement, statement.toString().trim());
                    // replaceStatementWithComment(statements, i, statement.toString().trim());
                }
            }
        }

         // to update: 
        mrMeta.MQS_generatedMRMethodDeclaration.put(TMD_QS, testMethodDeclaration);
        mrMeta.MQS_generatedExecutableMRMethodString.put(TMD_QS, testMethodDeclaration.toString());
        // mrMeta.successfulExtractedOriginal_MQS.add(TMD_QS);
        // mrMeta.successfulGeneratedMR_methodNames.add(testMethodDeclaration.getNameAsString());


        

    }

    public static boolean isAssertionStatement(Statement statement) {
        return statement.toString().trim().startsWith("Assert.") || statement.toString().trim().startsWith("assert");
    }
    
    // public static void addSingleLineCommentToStatement(Statement statement, String commentText) {
    //     LineComment comment = new LineComment(commentText);
    //     statement.setComment(comment);
    // }
    // public static void replaceStatementWithComment(List<Statement> statements, int index, String commentText) {
    //     LineComment comment = new LineComment(commentText);
    //     BlockStmt block = new BlockStmt();
    //     block.setComment(comment);
    //     statements.set(index, block);
    //     block.remove();
    // }
    

}

