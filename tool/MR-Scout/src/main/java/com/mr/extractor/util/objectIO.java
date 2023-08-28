package com.mr.extractor.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

// https://mkyong.com/java/java-serialization-examples/#serialize-object-to-file
public class objectIO {
    

// Serialization
  // Save object into a file.
  public static void writeObjectToFile(Object obj, String filePath) throws IOException {
    File file = new File(filePath);
      try (FileOutputStream fos = new FileOutputStream(file);
           ObjectOutputStream oos = new ObjectOutputStream(fos)) {
          oos.writeObject(obj);
          oos.flush();
      }
  }

  // Deserialization
  // Get object from a file.
  public static Object readObjectFromFile(String filePath) throws IOException, ClassNotFoundException {
      Object result = null;
      File file = new File(filePath);
      try (FileInputStream fis = new FileInputStream(file);
           ObjectInputStream ois = new ObjectInputStream(fis)) {
          result = ois.readObject();
      }
      return result;
  }
}
