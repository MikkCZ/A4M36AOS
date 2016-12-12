package cz.cvut.fel.aos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/destination/").hasRole("ADMIN").and().httpBasic();
        http.authorizeRequests()
                .antMatchers(HttpMethod.PUT, "/destination/**").hasRole("ADMIN").and().httpBasic();
        http.authorizeRequests()
                .antMatchers(HttpMethod.DELETE, "/destination/**").hasRole("ADMIN").and().httpBasic();

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/flight/").hasRole("ADMIN").and().httpBasic();
        http.authorizeRequests()
                .antMatchers(HttpMethod.PUT, "/flight/**").hasRole("ADMIN").and().httpBasic();
        http.authorizeRequests()
                .antMatchers(HttpMethod.DELETE, "/flight/**").hasRole("ADMIN").and().httpBasic();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("user").roles("USER"); // add user
        auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN"); // add admin
    }

}
