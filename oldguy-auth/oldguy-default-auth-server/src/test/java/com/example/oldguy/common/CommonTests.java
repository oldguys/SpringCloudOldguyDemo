package com.example.oldguy.common;

import org.junit.Test;

/**
 * @ClassName: CommonTests
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/7/9 0009 下午 2:12
 **/
public class CommonTests {

    @Test
    public void test(){

        String str2 = "save*";
        System.out.println(str2.matches("saveBatch"));
        System.out.println("saveBatch".matches("save.*"));

    }
}
