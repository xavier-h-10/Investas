package com.fundgroup.backend.security;

import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.Column;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class MyAuthenticationEntryPoint  implements AuthenticationEntryPoint {
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
    response.setContentType("application/json;charset=utf-8");
    PrintWriter out = response.getWriter();
    System.out.println("Spring security 刚刚阻止了一个未登录访问");
//        用servlet 来写JSON

    JSONObject object = new JSONObject();
    object.put("status", -1);
    object.put("message", "您需要登录以访问目标资源");

    out.print(object);
    out.flush();
    out.close();
  }
}
