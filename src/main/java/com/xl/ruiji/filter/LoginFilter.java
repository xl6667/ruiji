package com.xl.ruiji.filter;

import com.alibaba.fastjson.JSONObject;
import com.xl.ruiji.pojo.R;
import com.xl.ruiji.until.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Slf4j
@Component
@WebFilter(urlPatterns = "/*")
public class LoginFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url = request.getRequestURI().toString();
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/upload",
                "/user/sendMsg",
                "/user/login"
        };
        if(check(url,urls)){
            filterChain.doFilter(request,response);
            return;
        }
        //pc端
        if (request.getSession().getAttribute("employee") !=null){

            BaseContext.setId(request.getSession().getAttribute("employee").toString());
            filterChain.doFilter(request,response);
            return;
        }
        //移动端
        if (request.getSession().getAttribute("user") !=null){
            BaseContext.setId(request.getSession().getAttribute("user").toString());
            filterChain.doFilter(request,response);
            return;
        }

        response.getWriter().write(JSONObject.toJSONString(R.error("NOTLOGIN")));
        log.info("未登录");
        return;
    }
    /*

    * */
    public static boolean check(String url,String [] urls){
        for (String s : urls) {
            if(PATH_MATCHER.match(s,url)){
                return true;
            }
        }
        return false;
    }
}
