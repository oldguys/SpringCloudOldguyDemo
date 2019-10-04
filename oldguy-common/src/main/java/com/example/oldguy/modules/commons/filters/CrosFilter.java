package com.example.oldguy.modules.commons.filters;

import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: CrosFilter
 * @Author: ren
 * @Description:
 * @CreateTIme: 2019/9/29 0029 下午 4:30
 **/
public class CrosFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        /**
         *  允许指定请求头跨域
         */
//        res.addHeader("Access-Control-Allow-Origin","http://localhost:8083");
//        res.addHeader("Access-Control-Allow-Methods","GET");

        /**
         *  允许简单请求：
         *  方法为：GET,HEAD,POST
         *  请求header里面
         *      无自定义头
         *      Content-Type 为以下几种：
         *          text/plain
         *          multipart/form-data
         *          application/x-www-form-urlencoded
         *   ‘ * ’不支持cookie
         */
//        res.addHeader("Access-Control-Allow-Origin","*");
//        res.addHeader("Access-Control-Allow-Methods","*");

        /**
         *  非简单请求
         *      put，delete方法的ajax请求
         *      发送json格式的ajax请求
         *      带自定义头的ajax请求
         *      满足带Cookie请求
         */
        String origin = req.getHeader("Origin");
        if (!StringUtils.isEmpty(origin)) {
            res.addHeader("Access-Control-Allow-Origin", origin);
        }
        res.addHeader("Access-Control-Allow-Methods", "*");

        // Post 解析
        String headers = req.getHeader("Access-Control-Request-Headers");
        if (!StringUtils.isEmpty(headers)) {
            res.addHeader("Access-Control-Request-Headers", headers);
        }

        // 预解命令缓存
//        res.addHeader("Access-Control-Max-Age", "3600");

        // enable cookie
        res.addHeader("Access-Control-Allow-Credentials", "true");

        filterChain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }
}
