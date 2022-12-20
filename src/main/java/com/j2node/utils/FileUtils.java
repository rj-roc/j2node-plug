package com.j2node.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class FileUtils {

    public static String getTxtString(String path) {
        StringBuilder result = new StringBuilder();
        FileInputStream fin = null;
        InputStreamReader reader = null;
        BufferedReader buffReader = null;
        try {
            fin = new FileInputStream(path);
            reader = new InputStreamReader(fin);
            buffReader = new BufferedReader(reader);
            String strTmp;
            while ((strTmp = buffReader.readLine()) != null) {
                result.append(strTmp).append("\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (Objects.nonNull(buffReader)) {
                    buffReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {

                if (Objects.nonNull(buffReader)) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (Objects.nonNull(buffReader)) {
                    fin.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new String(result);
    }
}
