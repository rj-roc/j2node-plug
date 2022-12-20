package com.j2node.utils;

import com.j2node.exception.NodeException;
import com.j2nodejs.NodeJS;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@Slf4j
public class ResourceLoadUtils {

    public static String loadResourceFilePath(String sourcePath) {
        InputStream in = NodeJS.class.getResourceAsStream(sourcePath);
        String fileName = sourcePath.substring(sourcePath.lastIndexOf("/")+1);
        System.out.println(fileName);
        byte[] buffer = new byte[1024];
        File temp = new File(fileName);
        try {
            FileOutputStream fos = new FileOutputStream(temp);
            int read = -1;
            while ((read = in.read(buffer)) != -1) {
                fos.write(buffer, 0, read);
            }
            fos.close();
            in.close();
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new NodeException("加载资源失败");
        }
        return temp.getAbsolutePath();
    }

    public static void loadResourceFileLib(String path){
        String sourcePath = loadResourceFilePath(path);
        System.load(sourcePath);
    }
}
