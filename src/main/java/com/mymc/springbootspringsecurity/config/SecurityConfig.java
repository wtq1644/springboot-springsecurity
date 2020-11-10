package com.mymc.springbootspringsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//AOP : 拦截器
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //链式编程
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //首页所有人可以访问，功能页只有对应有权限的人才能访问
        //请求授权的规则
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("vip1")
                .antMatchers("/level2/**").hasRole("vip2")
                .antMatchers("/level3/**").hasRole("vip3");

        //没有权限默认回到登录页面
        http.formLogin().loginPage("/tologin");
        //开启注销功能
        http.logout().logoutSuccessUrl("/");
    }

    //认证，sping 2.1.x可以直接使用
    //密码编码：PasswordEncoder
    //在spring secutiry 5.0+新增了很多的加密方法
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //inMemoryAuthentication 内存中认证


        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("wtq1644").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1", "vip2")
                .and()
                .withUser("root").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1", "vip2", "vip3");


        //jdbcAuthentication 数据库认证

//        @Autowired
//        private DataSource dataSource;
//
//        @Autowired
//        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//            // ensure the passwords are encoded properly
//            UserBuilder users = User.withDefaultPasswordEncoder();
//            auth
//                    .jdbcAuthentication()
//                    .dataSource(dataSource)
//                    .withDefaultSchema()
//                    .withUser(users.username("user").password("password").roles("USER"))
//                    .withUser(users.username("admin").password("password").roles("USER","ADMIN"));
//      }
    }
}
