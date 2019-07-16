package com.example.oldguy;

import com.example.oldguy.utils.PropertiesUtils;
import org.junit.Test;
import java.util.Properties;

/**
 * @ClassName: CommonTests
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/14 0014 下午 4:07
 **/
public class CommonTests {

    @Test
    public void test() {

//        File file = ResourceUtils.getFile();
//
//        Properties properties = new Properties();
//        FileInputStream fileInputStream = new FileInputStream(file);
//        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
//        properties.load(new InputStreamReader(bufferedInputStream, "UTF-8"));
//        fileInputStream.close();
//
        Properties properties = PropertiesUtils.getProperties("classpath:auth-client.properties");

        for (Object key : properties.keySet()) {
            System.out.println(key + "\t: value:" + properties.get(key));
        }
    }
}
