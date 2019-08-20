package com.example.oldguy.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.Properties;

/**
 * @ClassName: PropertiesUtils
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/14 0014 下午 4:13
 **/
public class PropertiesUtils {

    public static Properties getProperties(String path) {
        Properties properties = new Properties();
        try {
//            File file = ResourceUtils.getFile(path);
//
//            FileInputStream fileInputStream = new FileInputStream(file);

            ClassPathResource resource = new ClassPathResource(path);
            InputStream inputStream = resource.getInputStream();

            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            properties.load(new InputStreamReader(bufferedInputStream, "UTF-8"));
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }
}
