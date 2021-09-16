package com.fundgroup.backend.security;

import com.fundgroup.backend.entity.UserAuthority;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class MyAccessDeniedHandler  implements AccessDeniedHandler {
  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
    response.setContentType("application/json;charset=utf-8");
    PrintWriter out = response.getWriter();
    System.out.println("Spring security 刚刚阻止了一个非权限访问");
//        用servlet 来写JSON

    JSONObject object = new JSONObject();
    object.put("status", -1);
    object.put("message", "您没有权限访问您的目标页面，请联系管理员。");

    out.print(object);
    out.flush();
    out.close();
  }
}
