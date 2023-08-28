package com.mr.extractor.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonUtil {

    public static void main(String[] args) {
        // HashMap<String,String> testDict=new HashMap<>() ;
        // // testDict.put("aa", "11");
        // writeJson("test.json", testDict, true);

        // String jsontest = readJsonFileAsString("test.json");
        // // jsontest.parseObject(is, charset, type, features);
        // testDict = JSONObject.parseObject(jsontest, HashMap.class);
		// System.out.println( testDict );

        // testDict.put("bb", "22");
        // writeJson("test.json", testDict, true);

		try {
            JsonUtil.readJsonFileAsString("asd.json");
            // FileUtil.readTextFile("asd.json");
        } catch (Exception e) {
            //TODO: handle exception
			System.out.println("No such file or directory??");
        }
		JsonUtil.readJsonFileAsString("asd.json");
		System.out.println("No such file or directory---------");

    }
	
	public static void writeJson(String savePath, Object obj, boolean isFormatted) {
        try {
            FileWriter f = new FileWriter(savePath);
            if(isFormatted)
            	f.write(JSON.toJSONString(obj,SerializerFeature.PrettyFormat,SerializerFeature.WriteMapNullValue));// 格式化写入
            else
            	f.write(obj.toString());// 直接写入
            f.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void writeJsonString(String savePath, String obj) {
        try {
            FileWriter f = new FileWriter(savePath);
            f.write(obj);// 写入
            f.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static String readJsonFileAsString(String path) {
		String s = readJsonFile(path);
		return s;
		
	}
	
	public static JSONObject readJsonFileAsObject(String path) {
		String s = readJsonFile(path);
		JSONObject jo = JSONObject.parseObject(s);
		return jo;
		
	}
	
	public static JSONArray readJsonFileAsArray(String path) {
		String s = readJsonFile(path);
		JSONArray ja = JSONArray.parseArray(s);
		//todo JSONArray.parseObject()
		return ja;	
	}


    private static String readJsonFile(String path) {
		String jsonStr = "";
		try {
			File jsonFile = new File(path);
			FileReader fileReader = new FileReader(jsonFile);
			Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
			int ch = 0;
			char[] cache = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((ch = reader.read(cache)) != -1) {
				sb.append(cache,0,ch);
//				sb.append(cache);

			}
			fileReader.close();
			reader.close();
			jsonStr = sb.toString();
			return jsonStr;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}