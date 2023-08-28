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

import com.alibaba.fastjson.JSONObject;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
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
import com.mr.extractor.config.Config;
import com.mr.extractor.util.FileUtil;
import com.mr.extractor.util.JsonUtil;
import com.mr.extractor.util.LogUtil;


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
public class executableMRGenerator {
    

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        String FILE_PATH;
        String DIR_PATH;
        String testMethodDeclarationQualifiedSignatures;
        Config config = new Config();


        if ( args.length>0){
            FILE_PATH = args[0];
            DIR_PATH = args[1];
            testMethodDeclarationQualifiedSignatures = args[2];
        }
        else{
            FILE_PATH = config.DIR_DATA + "data/AutoMR/projects/opentelecoms-org__split__jsmpp/jsmpp/src/test/java/org/jsmpp/util/OctetUtilTest.java";
            DIR_PATH = config.DIR_DATA + "data/AutoMR/projects/opentelecoms-org__split__jsmpp/"; 
            // String testMethodDeclarationQualifiedSignature = "org.jsmpp.util.OctetUtilTest.shortConversion()";
            testMethodDeclarationQualifiedSignatures = "org.jsmpp.util.OctetUtilTest.intConversion()__split__org.jsmpp.util.OctetUtilTest.testIntConversions()__split__org.jsmpp.util.OctetUtilTest.shortConversion()__split__org.jsmpp.util.OctetUtilTest.testShortConversions()__split__org.jsmpp.util.OctetUtilTest.testAnotherShortConversion()__split__org.jsmpp.util.OctetUtilTest.testShortEncode()";
            // testMethodDeclarationQualifiedSignatures = "org.jsmpp.util.OctetUtilTest.testIntConversions()";
            // testMethodDeclarationQualifiedSignatures = "org.jsmpp.util.OctetUtilTest.intConversion()";

            FILE_PATH = config.DIR_DATA + "data/AutoMR/projects/matsim-org__split__matsim-libs/matsim/src/test/java/org/matsim/utils/objectattributes/attributeconverters/StringCollectionConverterTest.java";
            DIR_PATH = config.DIR_DATA + "data/AutoMR/projects/matsim-org__split__matsim-libs/"; 
            // String testMethodDeclarationQualifiedSignature = "org.jsmpp.util.OctetUtilTest.shortConversion()";
            testMethodDeclarationQualifiedSignatures = "org.matsim.utils.objectattributes.attributeconverters.StringCollectionConverterTest.test()";
        }
        
        if(!withJunit(FILE_PATH)) {
            System.out.println("Not withJunit(FILE_PATH): " + FILE_PATH );
            // return;
        }
        String[] TMD_QSs = testMethodDeclarationQualifiedSignatures.split(Config.SPLITE_STR);

        MRMeta mrMeta = new MRMeta(FILE_PATH, DIR_PATH, TMD_QSs);
        generateExecutableMRMethod( mrMeta);

        deleteIrrelevantTestMethodsAndCreateFile(mrMeta);
        updateAutoMRProfile(mrMeta);
        // System.out.println("mrMeta.testFileCU: " + mrMeta.testFileCU.toString());
    }

    public static void generateExecutableMRMethod(MRMeta mrMeta) throws FileNotFoundException {
        String FILE_PATH = mrMeta.FILE_PATH; String DIR_PATH = mrMeta.DIR_PATH; String[] TMD_QSs= mrMeta.TMD_QSs;
        
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
        
        // 从这往下是单个 method TMD_QS
        for (String TMD_QS : TMD_QSs) {
            String TMD_QN = TMD_QS.substring( 0, TMD_QS.indexOf("(") );
            String testMethodName = TMD_QN.substring( TMD_QN.lastIndexOf(".") +1);
            // System.out.println(testMethodName);

            List<String> targetMethodsUnderTest = new ArrayList<String>();
            List<MethodCallExpr> MUTCallExpr =  new ArrayList<MethodCallExpr>();
            MethodDeclaration testMethodDeclaration = new MethodDeclaration();
            List<String> targetObjectsUnderTest = new ArrayList<String>() ;
            List<String> targetMethodsFromObjectsUnderTest = new ArrayList<String>() ;
            // System.out.println("!!!!!!!"+ cu.findAll(MethodDeclaration.class));
            for (MethodDeclaration md:MethodDeclarationsInTestFile ){
                    if (md.getNameAsString().equals(testMethodName)){
                        // getTargetObject()
                        targetObjectsUnderTest = Parser.getTargetObjectsUnderTest(md, pd, testClassName);    
                        // getTargetMethods()
                        targetMethodsUnderTest = Parser.getTargetMethodsUnderTest(md, pd);
                        targetMethodsFromObjectsUnderTest = Parser.getTargetMethodsUnderTestFromObjects(md, pd, targetObjectsUnderTest);
                        // targetMethodsUnderTest.addAll( Parser.getTargetMethodsUnderTestFromObjects(md, pd, targetObjectsUnderTest) );
                        // 用signature不好，因为无法灵活处理多态Overloading。 
                        // List<String> Signature = Parser.getFullQualifiedSignatureOfMethod(md, pd, targetMethodsUnderTest);
                        testMethodDeclaration = md;

                        if(targetObjectsUnderTest.size() == 0){ // 若targetMethodsUnderTest 不为空，则以method‘s class为准
                            for (String MUT : targetMethodsUnderTest) {
                                String CUT = MUT.substring(0, MUT.lastIndexOf('.'));
                                if(!targetObjectsUnderTest.contains(CUT))
                                    targetObjectsUnderTest.add(CUT);
                            }
                        }
                            
                    }
            }

            /* 1. locate invocation of targetMethodsUnderTests */
            MUTCallExpr = Parser.getMethodCallExprOfMethod(testMethodDeclaration, pd, Parser.getTargetMethodsUnderTest(testMethodDeclaration, pd, targetObjectsUnderTest));

            mrMeta.testMethodNames.add( testMethodName ); 
            mrMeta.MQS_testMethodNames.put( TMD_QS,testMethodName ); 
            mrMeta.MQS_testClassNames.put( TMD_QS,testClassName );
            mrMeta.MQS_testMethodStrings.put( TMD_QS,testMethodDeclaration.toString() );
            mrMeta.MQS_testMethodDeclarations.put( TMD_QS,testMethodDeclaration); 
            mrMeta.updateMUTandCUT(targetObjectsUnderTest, targetMethodsUnderTest, targetMethodsFromObjectsUnderTest, TMD_QS);
            mrMeta.MQS_MUTCallExprs.put(TMD_QS, MUTCallExpr);
            
        }
        mrMeta.testFileCU = cu.clone();  // 后边会改动内容，所以，在起初先 clone一个origial的版本  
        mrMeta.testPackageDeclaration = pd; 
        
        for (String TMD_QS : TMD_QSs) {
            List<MethodCallExpr> MUTCallExpr =  mrMeta.MQS_MUTCallExprs.get(TMD_QS);
            List<String> targetMethodsUnderTest = mrMeta.MQS_targetMethodsUnderTest.get(TMD_QS);
            System.out.println( "MUTCallExpr: "+ MUTCallExpr );
            System.out.println( "targetMethodsUnderTest: "+ targetMethodsUnderTest );
            extractComponentsOfMR( mrMeta, TMD_QS ); // 加个参数，TMD_QS，指明就好？ 原来的内容就整个dict形式吧。
        }
    }


    /* components try to identify:
    * sourceInput, input Transformation/(input relation?) ,followupInput
    * outputOfSourceInput, outputOfFollowupInput, test oracle
    *
    * components must identify:
    * sourceInput, followupInput, input relation between them 
    * 
    * 1. locate targetMethodsUnderTests
    * 2. extract inputs and caller objects of MUT
    * 3. identify relation between source input and follow up input
    * 4. change the source input to parameters of method, delete the def of source input, generate the code
    */
    public static void extractComponentsOfMR(MRMeta mrMeta, String TMD_QS) {
        System.out.println( "TMD_QS Under test: "+ TMD_QS );
        
        MethodDeclaration testMethodDeclaration = mrMeta.MQS_testMethodDeclarations.get(TMD_QS);
        List<String> targetMethodsUnderTest = mrMeta.MQS_targetMethodsUnderTest.get(TMD_QS);
        List<MethodCallExpr> MUTCallExpr = mrMeta.MQS_MUTCallExprs.get(TMD_QS);

        BlockStmt testMethodBodyBlockStmt =  testMethodDeclaration.getBody().get();
        NodeList<Statement> testMethodStatements = testMethodBodyBlockStmt.getStatements();
        boolean flag_successfulAutoMR = false;
        // String TMD_QS_MethodName = testMethodDeclaration.getNameAsString();
        

        // System.out.println(  testMethodDeclaration.findAll(AssignExpr.class)  );
        // System.out.println(  testMethodDeclaration);
        // 直接在AST上进行定位和修改，便于生成 code。
        // testMethodDeclaration.find

        MethodDeclaration executableMR = testMethodDeclaration.clone();
        // executableMR.remove();
        List<VariableDeclarationExpr> VDEs = testMethodDeclaration.findAll(VariableDeclarationExpr.class);// for: byte[] bytes = OctetUtil.shortToBytes(in);
        System.out.println( "VDEs: " + VDEs); 

        List<AssignExpr> AEs = testMethodDeclaration.findAll(AssignExpr.class); // for: bytes = OctetUtil.shortToBytes(in);
        System.out.println( "AEs: " + AEs);
        boolean withInputTransformation = false;

        // System.out.println(  MUTCallExpr.get(0).getArguments().get(0).getClass()  );
        // System.out.println(  MUTCallExpr.get(0).getArguments().get(0).appearsInInvocationContext() );

        // 找到input的def啊！ 
        // 感觉，需要数据流分析了直接？ [taint analysis]

        /* 2. extract inputs and caller objects of MUT*/
        // 目前，先只处理两次 method invocations的case
        if ( MUTCallExpr.size() <2 ){
            LogUtil.logger.info("MUTCallExpr.size() <2: "+ MUTCallExpr + "  " + TMD_QS);
            return;
        } 
        // first invocation 
        MethodCallExpr first_MUTCallExpr = MUTCallExpr.get(0);
        NodeList<Expression> first_MUTCallExpr_Arguments = first_MUTCallExpr.getArguments(); // arguments of source inputs (to consider caller objects)
        Node first_MUTCallExpr_caller = null;
        if ( isDeclaredVariable(first_MUTCallExpr.getChildNodes().get(0), VDEs) )
            first_MUTCallExpr_caller = first_MUTCallExpr.getChildNodes().get(0);
        System.out.println( "first_MUTCallExpr_caller: " + first_MUTCallExpr_caller );
        Node first_MUTCallExpr_output;

        // second invocation 
        MethodCallExpr second_MUTCallExpr = MUTCallExpr.get(1);
        NodeList<Expression> second_MUTCallExpr_Arguments = second_MUTCallExpr.getArguments(); // arguments of followUp inputs (to consider caller objects)
        Node second_MUTCallExpr_caller = null;
        if ( isDeclaredVariable(second_MUTCallExpr.getChildNodes().get(0), VDEs) )
        second_MUTCallExpr_caller = second_MUTCallExpr.getChildNodes().get(0);
        System.out.println( "second_MUTCallExpr_caller: " + second_MUTCallExpr_caller );

        
        Node second_MUTCallExpr_output;
        //TODO: MUTCallExpr.size() > 2
        if ( MUTCallExpr.size() >1 ){ // 这个IF语句很废物！
            // System.out.println(first_MUTCallExpr_Arguments);
            // System.out.println(first_MUTCallExpr.getArguments().get(0).getClass());
            // System.out.println(first_MUTCallExpr.getParentNode());

            /* get output */
            // bytes = OctetUtil.shortToBytes(in); class com.github.javaparser.ast.expr.AssignExpr
            // byte[] bytes = OctetUtil.shortToBytes(in); class com.github.javaparser.ast.body.VariableDeclarator
            Node first_MUTCallExpr_parentNode = first_MUTCallExpr.getParentNode().get(); // first_MUTCallExpr_output
            if ( first_MUTCallExpr_parentNode instanceof AssignExpr ||  first_MUTCallExpr_parentNode instanceof VariableDeclarator ){
                // System.out.println( "sss " +  first_MUTCallExpr.getParentNode().get().getClass());
                // System.out.println(  first_MUTCallExpr.getParentNode().get());
                for (Node iterable_element : first_MUTCallExpr.getParentNode().get().getChildNodes() ) {
                    if (iterable_element instanceof SimpleName){ // toadd, variable在语句中是 SimpleName类的对象
                        // System.out.println(iterable_element + " is SimpleName");
                        first_MUTCallExpr_output = iterable_element;
                    }
                }

            }
            Node second_MUTCallExpr_parentNode = second_MUTCallExpr.getParentNode().get(); // first_MUTCallExpr_output
            if ( second_MUTCallExpr_parentNode instanceof AssignExpr ||  second_MUTCallExpr_parentNode instanceof VariableDeclarator ){
                for (Node iterable_element : second_MUTCallExpr.getParentNode().get().getChildNodes() ) {
                    if (iterable_element instanceof SimpleName){ // toadd, variable在语句中可能是SimpleName类的对象
                        // System.out.println(iterable_element + " is SimpleName");
                        second_MUTCallExpr_output = iterable_element;
                    }
                }

            }
        }

        System.out.println( "first_MUTCallExpr_Arguments: "  + first_MUTCallExpr_Arguments);
        System.out.println( "second_MUTCallExpr_Arguments: "  + second_MUTCallExpr_Arguments);
        List<String> first_MUTCallExpr_Arguments_Strings = new ArrayList<>();
        if (first_MUTCallExpr_caller!=null) first_MUTCallExpr_Arguments_Strings.add(first_MUTCallExpr_caller.toString());
        for (Expression Arguments : first_MUTCallExpr_Arguments) {
            first_MUTCallExpr_Arguments_Strings.add( Arguments.toString() );
        }
        List<String> second_MUTCallExpr_Arguments_Strings = new ArrayList<>();
        if (second_MUTCallExpr_caller!=null) second_MUTCallExpr_Arguments_Strings.add(second_MUTCallExpr_caller.toString());
        for (Expression Arguments : second_MUTCallExpr_Arguments) {
            second_MUTCallExpr_Arguments_Strings.add( Arguments.toString() );
        }

        /* 3. identify relation between source input and follow up input*/
        // 判断下，follow-up input的def express中，是否有source iput就好？
        int[] flag_followUpInputs_withTransformation = new int[ second_MUTCallExpr_Arguments_Strings.size() ]; // 默认都是0
        List<SimpleName> transformatedSourceInputs_toRefactor = new ArrayList<>() ; // 即：将会被delete，change to parameters of executableMR method
        List<Expression> inputTransformation = new ArrayList<Expression>(); 
        // TODO, BUG: 要是 sourceInput和followUpInt一样，则“int i”，就会被认为是followUP的def 也是 sourceInput的use， 如：com.uber.h3core.TestVertex.cellToVertex()
        for (VariableDeclarationExpr VDE : VDEs) {
            SimpleName declaredVariablName = VDE.getVariable(0).getName();
            // 该DeclarationExpr声明了followUp input,
            if ( second_MUTCallExpr_Arguments_Strings.contains( declaredVariablName.toString() ) ){
                int index; // 该Arguments在second_MUTCallExpr_Arguments中的坐标
                for (index = 0; index < second_MUTCallExpr_Arguments_Strings.size(); index++) {
                    if( second_MUTCallExpr_Arguments_Strings.get(index).equals(  declaredVariablName.toString()  ) ) break;
                }
                // 该DeclarationExpr使用了source input
                for (SimpleName simpleName : VDE.findAll(SimpleName.class)) {
                    if ( first_MUTCallExpr_Arguments_Strings.contains( simpleName.toString()   ) ){
                        flag_followUpInputs_withTransformation[index] = 1;
                        inputTransformation.add(VDE);

                        transformatedSourceInputs_toRefactor.add(simpleName);
                        break;
                    }
                }
                
            }
        }
        for (AssignExpr AE : AEs) {
            // System.out.println(  AE.findAll(SimpleName.class)  );
            SimpleName declaredVariablName = AE.findAll(SimpleName.class).get(0);
            // 该DeclarationExpr声明了followUp input,
            if ( second_MUTCallExpr_Arguments_Strings.contains( declaredVariablName.toString()   ) ){
                int index; // 该Arguments在second_MUTCallExpr_Arguments中的坐标
                for (index = 0; index < second_MUTCallExpr_Arguments_Strings.size(); index++) {
                    if( second_MUTCallExpr_Arguments_Strings.get(index).equals(  declaredVariablName.toString()  ) ) break;
                }
                // 该DeclarationExpr使用了source input
                for (SimpleName simpleName : AE.findAll(SimpleName.class)) {
                    if ( first_MUTCallExpr_Arguments_Strings.contains( simpleName.toString()   ) ){
                        flag_followUpInputs_withTransformation[index] = 1;
                        inputTransformation.add(AE);

                        transformatedSourceInputs_toRefactor.add(simpleName);
                        break;
                    }
                    
                }
            }
        }
        System.out.println("------- flag_followUpInputs: " + flag_followUpInputs_withTransformation.toString());

        // flag_followUpInputs, 都为1，表示都是由source input transform而来
        // if( Arrays.stream(flag_followUpInputs).sum() == flag_followUpInputs.length){
        if( Arrays.stream(flag_followUpInputs_withTransformation).sum() >0){
            withInputTransformation = true;
        }
        LogUtil.logger.info("withInputTransformation: "+withInputTransformation + "  " + TMD_QS);

        /* 4. change the source input to parameters of method, delete the def of source input, generate the code */
        /* 仅refactor有transformation的input，能少变则少变？*/
        if(withInputTransformation){
            boolean successfuldeleteSourceInput = false;
            // for each input，删除第一次use前的声明及初始化 
            for (Expression sourceInput : first_MUTCallExpr_Arguments) {
                /*testMethodDeclaration.remove(node);
                for (Statement statement : testMethodStatements) {
                    System.out.println( statement + "   "  + statement.getChildNodes().size()  + "   "  +  statement.getChildNodes().get(0).getClass()   );
                    statement.findAll(  )
                }*/
            }
            // for each input，删除第一次use前的声明及初始化 （有点难，先实现个粗暴版;仅删除声明。）
            for (VariableDeclarationExpr VDE : VDEs) {
                // System.out.println("--------- " + VDE + "  " + VDE.getChildNodes() + "  " + VDE.getVariable(0).getTypeAsString());
                // System.out.println("--------- " + VDE + "  " + VDE.getChildNodes() + "  " + VDE.getVariable(0).getType());
                SimpleName declaredVariablName = VDE.getVariable(0).getName();
                Type declaredVariablType = VDE.getVariable(0).getType();
                // 该DeclarationExpr声明了某个source input, 
                // 且 该source input需要被refactoration。 该 transformatedSourceInputs_toRefactor
                // if ( first_MUTCallExpr_Arguments_Strings.contains( declaredVariablName.toString())  && transformatedSourceInputs_toRefactor.contains(declaredVariablName)  ){
                // 2022.06.09 目前认为所有 inputs和caller object都要refactor
                if ( first_MUTCallExpr_Arguments_Strings.contains( declaredVariablName.toString())){
                    /* delete DEF segment */
                    // testMethodDeclaration.remove(VDE);
                    // System.out.println( "------ debug: " + VDE.getParentNodeForChildren().getClass() ); // class com.github.javaparser.ast.expr.VariableDeclarationExpr
                    // System.out.println( "------ debug: " +  VDE.getParentNode().get().getClass() ); // class com.github.javaparser.ast.stmt.ExpressionStmt
                    Node expressToDelete = VDE.getParentNodeForChildren();
                    Node stmtToDelete = VDE.getParentNode().get();
                    Boolean delete_result=false;
                    // if ( stmtToDelete instanceof ForEachStmt ||  stmtToDelete instanceof ForStmt ) continue;
                    if ( stmtToDelete instanceof ForEachStmt ||  stmtToDelete instanceof ForStmt ){
                        // 说明变量声明是在for condition中，则仅保留 执行代码块
                        int childSize =  stmtToDelete.getChildNodes().size();
                        delete_result = testMethodBodyBlockStmt.replace(stmtToDelete, stmtToDelete.getChildNodes().get( childSize-1 ) ); //执行代码块应该是在最后一个。。。。
                        // 参考测试用例：testGenerateExecutableMRMethod3()
                    }
                    else{
                        // latest version
                        delete_result = stmtToDelete.getParentNode().get().remove(stmtToDelete);
                        // fixed version
                        // Boolean delete_result = testMethodBodyBlockStmt.remove(  stmtToDelete   ); // 牛
                        // if (!delete_result){
                        //     // 可能是因为：stmtToDelete不是testMethodBodyBlockStmt的直接childnode，就得从stmtToDelete的parent node入手。。。
                        //     // 例子：testGenerateExecutableMRMethod6()
                        //     System.out.println( stmtToDelete.getParentNode().get().remove(stmtToDelete) );
                        // }
                        // buggy version
                        // System.out.println( "remove: " + VDE.getParentNode().get() + "   " + testMethodDeclaration.remove(VDE.getParentNode().get()) ); // 这个就不行，应该是 操作的类型有别，后期细细研究  
                    }
                        
                    /* add addParameter */
                    if (delete_result){
                        testMethodDeclaration.addParameter(declaredVariablType, declaredVariablName.toString());
                        successfuldeleteSourceInput = true;
                    }
                    else
                        LogUtil.logger.info("delete_result: "+delete_result + "  " + stmtToDelete);
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
                // System.out.println( "AnnotationExprs: " + testMethodDeclaration.getAnnotationByName("Test"));


                /* set method Name */
                testMethodDeclaration.setName( testMethodDeclaration.getNameAsString() + "_" + Config.TOOL_NAME );
                flag_successfulAutoMR = true;
            }
        }

        if (flag_successfulAutoMR){
            mrMeta.successfulAutoMR = flag_successfulAutoMR;
            mrMeta.MQS_generatedMRMethodDeclaration.put(TMD_QS, testMethodDeclaration);
            mrMeta.MQS_generatedExecutableMRMethodString.put(TMD_QS, testMethodDeclaration.toString());

            mrMeta.successfulExtractedOriginal_MQS.add(TMD_QS);
            mrMeta.successfulGeneratedMR_methodNames.add(testMethodDeclaration.getNameAsString());
        }
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
        /* delete irrelevant methods */  /* 删掉构造器*/
        /* add generated MR method */
        /* delete irralevant classes and add import */
        for (ClassOrInterfaceDeclaration cd : generatedMRFileCU.findAll(ClassOrInterfaceDeclaration.class)) {
            List<String> toaddMDlistInThisCD = new ArrayList<>();
            String testClassName = cd.getNameAsString(); 
            boolean flag_thisclass_contains_AutoMR = false; // 判断这个class是否改名_autoMR

            for (MethodDeclaration md: cd.findAll(MethodDeclaration.class)){
                String testMethodAnnotations = md.getAnnotations().toString();
                String testMethodAccessSpecifier =  md.getAccessSpecifier().toString();
                if( testMethodAccessSpecifier.equals("PUBLIC") && testMethodAnnotations.contains("@Test") ) { 
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
            String originalclassFullyQualifiedName = cd.asClassOrInterfaceDeclaration().resolve().getQualifiedName(); 
            originalclassFullyQualifiedName=originalclassFullyQualifiedName.replace("_"+Config.TOOL_NAME, ""); // class名可能已加上"_AutoMR"
            originalclassFullyQualifiedName=originalclassFullyQualifiedName.replace("_"+Config.OriginalMRcases, ""); // class名可能已加上"_AutoMR"
            if (flag_thisclass_contains_AutoMR){
                cd.setName(testClassName+"_"+Config.TOOL_NAME);
                mrMeta.generatedMRClassFQN = mrMeta.testPackageDeclaration.getNameAsString() + "." + testClassName+"_"+Config.TOOL_NAME;//只会记录最后一个class。。。。一般也只有一个。。。
                
                /* 删掉构造器*/
                for (ConstructorDeclaration ConstructorD : cd.findAll(ConstructorDeclaration.class)) {
                    if( ConstructorD.getNameAsString().equals( cd.getNameAsString() ) )
                        ConstructorD.remove();
                }
            }
            else{// 不改名的话，
                // if private：pass （subclass未private不影响编译，不会冲突）。 else: 则删除。。并引入 import
                if( "PRIVATE".equals( cd.getAccessSpecifier().toString() ) ){
                    // pass, 不操作
                }
                else{
                    cd.remove();
                    generatedMRFileCU.addImport(originalclassFullyQualifiedName);
                    // System.out.println("after: "+generatedMRFileCU);
                    // System.out.println("");
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
                    generatedMRFileCU.addImport(new ImportDeclaration(CUT, false, true)); // add import
                    // generatedMRFileCU.addImport(new ImportDeclaration(CUT, false, false)); // 这样无法成功addimport，不知为啥。。 
                }  
            }  
        }
        
        /* generate file */
        String filePath = mrMeta.FILE_PATH.replace(".java", "_" + Config.TOOL_NAME + ".java");
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
                if( testMethodAccessSpecifier.equals("PUBLIC") && testMethodAnnotations.contains("@Test") ) { 
                    // 说明是test methods，判断下，是否保留（relevant）
                    if (!successfulGneratedMRMethodNames.contains( md.getNameAsString() ) )
                        cd.remove(md);
                    else
                        flag_thisclass_contains_OriginalMRcases = true;

                }
            }

            String originalclassFullyQualifiedName = cd.asClassOrInterfaceDeclaration().resolve().getQualifiedName(); 
            originalclassFullyQualifiedName=originalclassFullyQualifiedName.replace("_"+Config.TOOL_NAME, ""); // class名可能已加上"_AutoMR"，先给复原了
            originalclassFullyQualifiedName=originalclassFullyQualifiedName.replace("_"+Config.OriginalMRcases, ""); // class名可能已加上"_OriginalMRcases"，先给复原了
            if (flag_thisclass_contains_OriginalMRcases){ // 该class改名的话
                cd.setName(testClassName+"_"+Config.OriginalMRcases);
                /* 删掉构造器*/
                for (ConstructorDeclaration ConstructorD : cd.findAll(ConstructorDeclaration.class)) {
                    if( ConstructorD.getNameAsString().equals( cd.getNameAsString() ) )
                        ConstructorD.remove();
                }
            }
            else{// 该class不改名的话
                // if private：pass （subclass为private不影响编译，不会冲突）。 else: 则删除。。并将该class 作为 import
                if( "PRIVATE".equals( cd.getAccessSpecifier().toString() ) ){
                    // pass, 不操作
                }
                else{
                    cd.remove();
                    onlyOriginalMRFileCU.addImport(originalclassFullyQualifiedName); // 该class应该是子类class了
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
                    onlyOriginalMRFileCU.addImport(new ImportDeclaration(CUT, false, true)); // add import
                    // onlyOriginalMRFileCU.addImport(new ImportDeclaration(CUT, false, false)); // 这样无法成功addimport，不知为啥。。
                }  
            }  
        }

        /* generate file */
        String OriginalMRcasesfilePath = mrMeta.FILE_PATH.replace(".java", "_" + Config.OriginalMRcases + ".java");
        String OriginalMRcasescontent = onlyOriginalMRFileCU.toString();
        FileUtil.writeTextFile(OriginalMRcasesfilePath, OriginalMRcasescontent);
    }

    public static void updateAutoMRProfile(MRMeta mrMeta) {
        String AutoMRProfileJSONPath = mrMeta.DIR_PATH + "/" + Config.FILENAMR_AUTOMR_PROFILE_JSON;
        AutoMRProfileJSONPath.replace("//", "/");

        HashMap<String, HashMap> AutoMRProfile = new HashMap<>();
        if (FileUtil.fileExisting(AutoMRProfileJSONPath)){
            // try {
            //     String  AutoMRProfileJSONString = JsonUtil.readJsonFileAsString(AutoMRProfileJSONPath);
            //     AutoMRProfile = JSONObject.parseObject(AutoMRProfileJSONString, HashMap.class);
            // } catch (Exception e) {
            //     //TODO: handle exception
            // }
            String  AutoMRProfileJSONString = JsonUtil.readJsonFileAsString(AutoMRProfileJSONPath);
            AutoMRProfile = JSONObject.parseObject(AutoMRProfileJSONString, HashMap.class);
        }
        if(mrMeta.generatedMRFilePath!=null) {
            HashMap<String, String> MRFile_ele = new HashMap<>();
            MRFile_ele.put("generatedMRClassFQN", mrMeta.generatedMRClassFQN);

            // 基于MQS，解析出 CQS
            // TODO: 明确 targetObjectsUnderTest 信息！！！！
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
            FQN_classesUnderTest = FQN_classesUnderTest.substring(0, FQN_classesUnderTest.lastIndexOf(",")); // 去掉某位的 ”， “
            MRFile_ele.put("ClassessUnderTest", FQN_classesUnderTest);

            /* 增加targetMethodsUnderTest的信息 */
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
            allTargetMethodsUnderTestSet_str = allTargetMethodsUnderTestSet_str.substring(1); //去掉首位的',''
            MRFile_ele.put("MethodsUnderTest", allTargetMethodsUnderTestSet_str);
            /* 增加Methodsfrom CUT的信息 */


            
            AutoMRProfile.put(mrMeta.generatedMRFilePath, MRFile_ele);    
        }   
        JsonUtil.writeJson(AutoMRProfileJSONPath, AutoMRProfile, true); 
    }

    public static void generateExecutableMR() {
        
    }

    public static void generateSourceInputForExecutableMR() {
        
    }

    public static boolean withJunit(String FILE_PATH) throws IOException {
        String content = FileUtil.readTextFile(FILE_PATH);
        if( content.toLowerCase().contains(".junit") ){
            return true;
        }
        return false;
    }

}
