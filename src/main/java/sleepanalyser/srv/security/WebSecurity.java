package sleepanalyser.srv.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sleepanalyser.srv.filter.AuthenticationFilter;
import sleepanalyser.srv.filter.AuthorizationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.*;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManagerBean());
        authenticationFilter.setFilterProcessesUrl("/api/login");
        http.csrf().disable();
        log.info(authenticationFilter.getUsernameParameter());
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/api/login/**", "/api/role/refresh-token").permitAll();
        http.authorizeRequests().antMatchers(GET, "/user/**").hasAnyAuthority("USER");
        http.authorizeRequests().antMatchers(GET, "/sleep-data/**").hasAnyAuthority("USER");
        http.authorizeRequests().antMatchers(DELETE, "/user/**").hasAnyAuthority("ADMIN","DOCTOR");
        http.authorizeRequests().antMatchers(POST, "/user/**").hasAnyAuthority("DOCTOR", "ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(authenticationFilter);
        http.addFilterBefore(new AuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
