package com.znjtgs.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by lenovo on 2017/7/7.
 */

public class FileTools {
    public static String openFileToString(String path) {
        StringBuilder stringBuilder = new StringBuilder();
        File file = new File(path);
        if (!file.exists()) {
            return stringBuilder.toString();
        }
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader = null;
        try {
            fileInputStream = new FileInputStream(file);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

        } catch (IOException e) {

        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return stringBuilder.toString();
    }


    public static void saveStringToFile(String path, String data) {
        File file = new File(path);
        if (!file.exists()) {
//            int index = file.getAbsolutePath().lastIndexOf(File.separator);
//            String parentPath = file.getAbsolutePath().substring(0, index);
//            File parentPathFile = new File(parentPath);
//            if (!parentPathFile.exists()) {
//                parentPathFile.mkdirs();
//            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        FileOutputStream fileOutputStream = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            bufferedWriter.write(data);
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
