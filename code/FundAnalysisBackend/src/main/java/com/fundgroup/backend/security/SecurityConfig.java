package com.fundgroup.backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fundgroup.backend.entity.UserAuthority;
import com.fundgroup.backend.service.UserAuthorityService;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  UserAuthorityService userAuthorityService;
  MyAccessDeniedHandler myAccessDeniedHandler;
  MyAuthenticationEntryPoint myAuthenticationEntryPoint;

  @Autowired
  void setUserAuthorityService(UserAuthorityService userAuthorityService) {
    this.userAuthorityService = userAuthorityService;
  }

  @Autowired
  void setMyAccessDeniedHandler(MyAccessDeniedHandler myAccessDeniedHandler) {
    this.myAccessDeniedHandler = myAccessDeniedHandler;
  }

  @Autowired
  void setMyAuthenticationEntryPoint(MyAuthenticationEntryPoint myAuthenticationEntryPoint) {
    this.myAuthenticationEntryPoint = myAuthenticationEntryPoint;
  }

  @Bean
  RoleHierarchy roleHierarchy() {
    RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
//    ATTENTION Please: ROLE_ is a fixed prefix
//    这是一个层级结构，参考偏序结构，必须写在一句内，是“覆盖性”的
    hierarchy.setHierarchy("ROLE_superAdmin > ROLE_admin > ROLE_superUser > ROLE_user");
    return hierarchy;
  }

  //  配置信息：spring security如何对密码进行编码？
  @Bean
  PasswordEncoder passwordEncoder() {
//    参数是加密迭代次数
    return new BCryptPasswordEncoder(10);
  }

  @Bean
  LoginFilter loginFilter() throws Exception {
//    登录认证过过滤器
    LoginFilter loginFilter = new LoginFilter();

//    如果认证成功执行第一个办法，否则执行第二个
    loginFilter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
      @Override
      public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
          Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
//        用servlet 来写JSON

        JSONObject mp = new JSONObject();
        mp.put("status", 0);
        mp.put("message", "登陆成功");

//        组装用户基本信息
        JSONObject userInfo = new JSONObject();
        UserAuthority userAuthority = (UserAuthority) authentication.getPrincipal();
        userInfo.put("username", authentication.getName());
        userInfo.put("userId", userAuthority.getUserId().toString());
        userInfo.put("userAuth", authentication.getAuthorities().toString());
        mp.put("data", userInfo.toString());

        out.print(mp);
        out.flush();
        out.close();
      }
    });

    loginFilter.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {
      @Override
      public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
          AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");

        PrintWriter out = response.getWriter();

        JSONObject mp = new JSONObject();
        mp.put("status", -1);
        mp.put("message", "用户名或密码错误");
        mp.put("data", new JSONObject());
        out.print(mp);
        out.flush();
        out.close();
      }
    });

    loginFilter.setAuthenticationManager(authenticationManagerBean());
    loginFilter.setFilterProcessesUrl("/doLogin");

    return loginFilter;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userAuthorityService);
  }

  //  建议只用于配置静态资源，相当于security框架放行，如果对url来就
//  可能导致安全风险，SS中所有格式都是Ant类
  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/js/**",
        "/css/**", "/images/**", "/avatar/**", "/app/**",
        "/favicon.ico");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
//        对权限进行配置，在userAuth里面对role我们的解释是EN name, 所以这里也应如是
//        资源放行：如果不需要登陆就能得到的资源，务必在这里放行！

        .antMatchers("/anyone/**").permitAll()
        .antMatchers("/register_auth").permitAll()
        .antMatchers("/anyonePermit/**").permitAll()
        .antMatchers("/register_auth").permitAll()

//        对每个url进行权限设置，高权限在低权限之前扫描
//        example: 如果先anyRequest那么后面的配置会全部失效
//        "superAdmin" 最高权限，系统管理员
//        "admin" 普通管理员权限
//        "superUser" 超级用户
//         "user" 用户
        .antMatchers("/superAdmin/**").hasRole("superAdmin")
        .antMatchers("/admin/**").hasRole("admin")
        .antMatchers("/superUser/**").hasRole("superUser")
        .antMatchers("/user/**").hasRole("user")
        .antMatchers("/anyUser/**").hasAnyRole("admin", "superAdmin","superUser","user")

//        默认所有的都要登录

        .anyRequest().authenticated()

//        .and()
//        .formLogin()
//        在这里指定前端的url，自定义登录界面，建议全局一个前端url api_url
//        .loginPage("/login.html")
//        可选，和表单的Action url保持一致即可
//        .loginProcessingUrl("/login")
//        .permitAll()

//        自动登录，根据场景决定
        .and()
        .rememberMe()

//        异常处理：当登录失效时或者没有登录时要求用户登录
//        当用户权限不足时，显式的告诉用户原因（在手机App中其实不应该出现）
//        当访问有问题，都*正常返回message，status=-1，message是对应的错误信息*
        .and()
        .exceptionHandling()

        .authenticationEntryPoint(myAuthenticationEntryPoint)
        .accessDeniedHandler(myAccessDeniedHandler)

//        配置注销，把注销后重定位到前端
        .and()
        .logout()
        .logoutUrl("/logout")
//        配置注销 handler 20210804
        .logoutSuccessHandler(new LogoutSuccessHandler() {
          @Override
          public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp,
              Authentication authentication) throws IOException, ServletException {
            resp.setContentType("application/json;charset=utf-8");
            PrintWriter out = resp.getWriter();
            Map<String, Object> map = new HashMap<>();
            map.put("status", 200);
            map.put("message", "注销登录成功!");
            out.write(new ObjectMapper().writeValueAsString(map));
            out.flush();
            out.close();
          }
        })

//        .logoutSuccessUrl("/login")
        .clearAuthentication(true)
        .invalidateHttpSession(true)

        .and()
        .cors()
        .configurationSource(corsConfigurationSource())

//        防止csrf攻击
        .and()
        .csrf().disable()

        .sessionManagement()
//        后面登陆的用户会踢掉前面的，这取决于业务逻辑
        .maximumSessions(2050);

    http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);
  }

  //  跨域配置
  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowCredentials(true);

//    允许跨域的请求源，最后改为前端即可
    configuration.setAllowedOriginPatterns(Arrays.asList("*"));
    configuration.setAllowedMethods(Arrays.asList("*"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(true);
    configuration.setMaxAge(Duration.ofHours(1));
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
