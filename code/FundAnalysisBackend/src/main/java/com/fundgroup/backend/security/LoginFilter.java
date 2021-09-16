package com.fundgroup.backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
//    重写登录filter

//    step1: if frontend request type != POST，抛出异常
    if (!request.getMethod().equals("POST")) {
      throw new AuthenticationServiceException(
          "Authentication method not supported: " + request.getMethod());
    }

    if (request.getContentType() != null && (request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE) || request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)))
//    step2: request中组装map
    {
      Map loginData = new HashMap<>();
      try {
//      getInput stream to read paras of json
        loginData = new ObjectMapper().readValue(request.getInputStream(), Map.class);
      } catch (IOException e) {
        System.out.println("exception happened in Login Filter");
      }

      String username = (String) loginData.get(getUsernameParameter());
      String password = (String) loginData.get(getPasswordParameter());

//    never allow EMPTY name or password stored into database to confirm security
      if (username == null) {
        username = "";
      }
      if (password == null) {
        password = "";
      }

//    * 允许用户名前后的空格
      username = username.trim();

      UsernamePasswordAuthenticationToken authRequest = new
          UsernamePasswordAuthenticationToken(username, password);
      setDetails(request, authRequest);

      return this.getAuthenticationManager().authenticate(authRequest);
    } else {
//      如果不是KV型的登陆数据，那么交给父类来处理
      return attemptAuthenticationForForm(request, response);
    }
  }

  public Authentication attemptAuthenticationForForm(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {

      String username = this.obtainUsername(request);
      username = username != null ? username : "";
      username = username.trim();
      String password = this.obtainPassword(request);
      password = password != null ? password : "";
      UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
      this.setDetails(request, authRequest);
      return this.getAuthenticationManager().authenticate(authRequest);
    }
}
