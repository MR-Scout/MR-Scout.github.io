package com.mr.extractor.MRdetector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.mr.extractor.Parser;

public class relationAssertion implements java.io.Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    // about assertion relaton
    public static MethodCallExpr assertionInv ;
    public String assertionSTMT ;
    public methodInvocation lastMethodInvocation = null; // followUpMethodInvocation
    public String lastMethodInvocationOutputName = null; // followUpOutputName
    public HashMap<methodInvocation, List<String>> previousMethodInvocations_involvedInputOrOutput = new HashMap<methodInvocation, List<String>>(); //


    // about InputTransformation
    public int invocationNum=-1;
    public boolean withInputTransformation_Option1;
    public boolean withInputTransformation_Option2;


    // about profile
    public int lineNumberBegin;
    public int lineNumberEnd;
    public String FQN_testCaseUnderAnalysis;
    public String FQS_testCaseUnderAnalysis;
    public String FQN_targetClassUnderTest;
    public String Path_testClassUnderAnalysis = ""; 
    // public HashMap<String, methodInvocation> previousMethodInvocations; 
    public List<methodInvocation> previousMethodInvocations = new ArrayList<>(); 



    // public String assertionSignature = null;
    // involved varibles: input/output
    public static MethodDeclaration testCaseMethodDeclaration = null;

    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }

    /* 
     * invocations Num == 2
     * * for each element in the follow-up input
		* [option1]exists a statement which generates/updates this element using at least one source input/output.
		* [option2]exists a statement which generates/updates this element only using source input/output.
    */
    public void withInputTransformation(int option){ // opiont=1/2
        /* condition: invocations Num == 2 */
        this.invocationNum = this.previousMethodInvocations_involvedInputOrOutput.keySet().size()+1;
        if( this.invocationNum!=2 ){
            return;
        }
        /* condition: follow-up input should not be none */
        if( this.lastMethodInvocation.inputExpressions.size()<1) return;


        // 初始化
        // this.FQN_targetClassUnderTest = this.lastMethodInvocation.mceQualifiedSignature;
        MethodDeclaration testMethodDeclaration = this.testCaseMethodDeclaration;
        methodInvocation lastMethodInvocation = this.lastMethodInvocation;
        methodInvocation prevMethodInvocation =null;
        for (methodInvocation previousMethodInvocationElement : this.previousMethodInvocations_involvedInputOrOutput.keySet()) {
            // 其实，里面也只有一个MethodInvocation啦
            prevMethodInvocation = previousMethodInvocationElement;
            this.previousMethodInvocations.add(previousMethodInvocationElement);
        }
        List<SimpleName> allVaribleNamesInMDandFieldsOfClass = new ArrayList<>();
        List<String> allVaribleNamesInMDandFieldsOfClass_String = new ArrayList<>();
        List<VariableDeclarationExpr> VDEs = testMethodDeclaration.findAll(VariableDeclarationExpr.class);// for: byte[] bytes = OctetUtil.shortToBytes(in);
        List<AssignExpr> AEs = testMethodDeclaration.findAll(AssignExpr.class); // for: bytes = OctetUtil.shortToBytes(in);
        // allVaribleNamesInMd
        List<SimpleName> allVaribleNamesInMd = new ArrayList<>();
        for (VariableDeclarationExpr VDE : VDEs) {
            SimpleName declaredVariablName = VDE.getVariable(0).getName();
            allVaribleNamesInMd.add(declaredVariablName);
            allVaribleNamesInMDandFieldsOfClass.add(declaredVariablName);
            allVaribleNamesInMDandFieldsOfClass_String.add(declaredVariablName.toString());
        }
        // allFieldNamesInClassDeclaration
        List<SimpleName> allFieldNamesInClassDeclaration = new ArrayList<>(); // fields of a class
        ClassOrInterfaceDeclaration testClassDeclaration = Parser.getClassDeclarationFromMethodDeclaration(testMethodDeclaration);
        List<FieldDeclaration> fieldDeclarations= testClassDeclaration.getFields();
        for (FieldDeclaration fieldDeclaration : fieldDeclarations) {
            SimpleName declaredFieldlName = fieldDeclaration.getVariables().get(0).getName();
            allFieldNamesInClassDeclaration.add(declaredFieldlName);
            if(!allVaribleNamesInMDandFieldsOfClass.contains(declaredFieldlName))  {
                allVaribleNamesInMDandFieldsOfClass.add(declaredFieldlName);
                allVaribleNamesInMDandFieldsOfClass_String.add(declaredFieldlName.toString());
            }               
        }
        
        System.out.println("allVaribleNamesInMd: " + allVaribleNamesInMd);
        System.out.println("allVaribleNamesInMDandFieldsOfClass: " + allVaribleNamesInMDandFieldsOfClass);

        
        /* 参考代码：executableMRGenerator.java， 3. identify relation between source input and follow up input */
        //lastMethodInvocationInputExpressions_Strings == second_MUTCallExpr_Arguments_Strings
        //  =first_MUTCallExpr_Arguments_Strings
        // 判断下，follow-up input的def express中，是否有source iput就好？
        List<String> lastMethodInvocationInputExpressions_Strings = new ArrayList<>(lastMethodInvocation.inputExpressions);
        List<String> prevMethodInvocationInputOutputExpression_Strings = new ArrayList<>(prevMethodInvocation.inputExpressions);
        prevMethodInvocationInputOutputExpression_Strings.addAll(prevMethodInvocation.outputExpressions);
        int[] flag_followUpInputs_withTransformation = new int[ lastMethodInvocationInputExpressions_Strings.size() ]; // 默认都是0
        List<Expression> inputTransformation = new ArrayList<Expression>(); 
        // TODO, BUG: 要是 sourceInput和followUpInt一样，则“int i”，就会被认为是followUP的def 也是 sourceInput的use， 如：com.uber.h3core.TestVertex.cellToVertex()

        /* transformation1: in the VariableDeclarationExpr */ 
        for (VariableDeclarationExpr VDE : VDEs) {
            List<SimpleName> allVaribleNamesInExpr = VDE.findAll(SimpleName.class); allVaribleNamesInExpr.retainAll(allVaribleNamesInMDandFieldsOfClass);
            SimpleName declaredVariablName = VDE.getVariable(0).getName();
            // 该DeclarationExpr声明了followUp input,
            if ( lastMethodInvocationInputExpressions_Strings.contains( declaredVariablName.toString() ) ){
                int index; // 该Arguments在second_MUTCallExpr_Arguments中的坐标
                for (index = 0; index < lastMethodInvocationInputExpressions_Strings.size(); index++) {
                    if( lastMethodInvocationInputExpressions_Strings.get(index).equals(  declaredVariablName.toString()  ) ) break;
                }
                if(option==1){
                    // Option1: 该DeclarationExpr使用了source input
                    for (SimpleName simpleName : allVaribleNamesInExpr) {
                        if ( prevMethodInvocationInputOutputExpression_Strings.contains( simpleName.toString()   ) ){
                            flag_followUpInputs_withTransformation[index] = 1;
                            inputTransformation.add(VDE);
    
                            break;
                        }
                    }
                }
                else if(option==2){
                    // Option2: 该DeclarationExpr仅使用source input
                    boolean justSourceInput=true;
                    for (SimpleName simpleName : allVaribleNamesInExpr) {
                        if (! prevMethodInvocationInputOutputExpression_Strings.contains( simpleName.toString()   ) ){
                            justSourceInput=false; break;
                        }
                    }
                    if(justSourceInput){
                        flag_followUpInputs_withTransformation[index] = 1;
                        inputTransformation.add(VDE);
                    }
                }
            }
        }
        /* transformation2: in the AssignExpr */
        for (AssignExpr AE : AEs) {
            List<SimpleName> allVaribleNamesInExpr = AE.findAll(SimpleName.class); allVaribleNamesInExpr.retainAll(allVaribleNamesInMDandFieldsOfClass);
            SimpleName declaredVariablName = AE.findAll(SimpleName.class).get(0);
            // 该DeclarationExpr声明了followUp input,
            if ( lastMethodInvocationInputExpressions_Strings.contains( declaredVariablName.toString() ) ){
                int index; // 该Arguments在second_MUTCallExpr_Arguments中的坐标
                for (index = 0; index < lastMethodInvocationInputExpressions_Strings.size(); index++) {
                    if( lastMethodInvocationInputExpressions_Strings.get(index).equals(  declaredVariablName.toString()  ) ) break;
                }
                if(option==1){
                    // Option1: 该DeclarationExpr使用了source input
                    for (SimpleName simpleName : allVaribleNamesInExpr) {
                        if ( prevMethodInvocationInputOutputExpression_Strings.contains( simpleName.toString()   ) ){
                            flag_followUpInputs_withTransformation[index] = 1;
                            inputTransformation.add(AE);
    
                            break;
                        }
                    }
                }
                else if(option==2){
                    // Option2: 该DeclarationExpr仅使用source input
                    boolean justSourceInput=true;
                    for (SimpleName simpleName : allVaribleNamesInExpr) {
                        if (! prevMethodInvocationInputOutputExpression_Strings.contains( simpleName.toString()   ) ){
                            justSourceInput=false; break;
                        }
                    }
                    if(justSourceInput){
                        flag_followUpInputs_withTransformation[index] = 1;
                        inputTransformation.add(AE);
                    }
                }
            }
        }
        /* transformation3: follow-up == source, including: follow-up=object.method(), or source=object.method2() */
        for (int index = 0; index < lastMethodInvocationInputExpressions_Strings.size(); index++) {
            /* condition: follow-up inputs are the varible (in the VariableDeclarationExpr)  */
            String InputExpression = lastMethodInvocationInputExpressions_Strings.get(index);
            if(InputExpression.startsWith("\"") )continue; // 说明是字符串，肯定不符合condition
            else if(InputExpression.contains(".") || InputExpression.contains("(")){ // 说明不是字符串，是method call等骚操作；
                // 这里，if InputExpression contains Varible Names则认为是有declared variable 【简单处理, 可能会有误报啦，】
                boolean containVariableName = false;
                for (SimpleName VaribleName : allVaribleNamesInMDandFieldsOfClass) {
                    if (InputExpression.contains(VaribleName.toString())){
                        containVariableName = true;break;
                    }
                }
                if(!containVariableName) continue;
            }
            else if(!allVaribleNamesInMDandFieldsOfClass_String.contains(InputExpression) ) // 说明是简单variable or hardcoded value
                continue;
            
            

            // 该Arguments在second_MUTCallExpr_Arguments中的坐标
            if(flag_followUpInputs_withTransformation[index] ==1) continue;

            // 1. 相同
            if ( prevMethodInvocationInputOutputExpression_Strings.contains( InputExpression ) ){
                flag_followUpInputs_withTransformation[index] = 1; continue;
            }
            
            for (String prevMIIOExpression : prevMethodInvocationInputOutputExpression_Strings) {
                // 2. 一方为另一方的object (一方包含另一方)
                if(prevMIIOExpression.contains(InputExpression) || InputExpression.contains(prevMIIOExpression)){
                    flag_followUpInputs_withTransformation[index] = 1; break;
                }
                // 3. 两方为同一个object
                else{
                    String prevMIIOObject = prevMIIOExpression;
                    // 提取 object name
                    if(prevMIIOExpression.split(".").length>0) prevMIIOObject = prevMIIOExpression.split(".")[0];
                    String InputExpressionObject = InputExpression;
                    if(InputExpression.split(".").length>0) InputExpressionObject = InputExpression.split(".")[0];

                    if(prevMIIOObject.equals(InputExpressionObject)){
                        flag_followUpInputs_withTransformation[index] = 1; break;
                    }
                }
                
            }
        }

        // flag_followUpInputs, 都为1，表示都是由source input transform而来
        if( Arrays.stream(flag_followUpInputs_withTransformation).sum() == flag_followUpInputs_withTransformation.length){
            if(option==1)
                this.withInputTransformation_Option1 = true;
            if(option==2)
                this.withInputTransformation_Option2 = true;
        }
    }

}
