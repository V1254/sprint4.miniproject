package springData;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Part1: Secure Connection
        http.requiresChannel().anyRequest().requiresSecure()
            .and()
                    .formLogin()
                                .loginPage("/user-login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/success-login",true)
                                .failureUrl("/error-login")
                                .permitAll()
            .and()
                    .logout()
                                .invalidateHttpSession(true)
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/user-login")
                                .permitAll()
            .and()
                    .authorizeRequests()
                                .antMatchers("/admin/**").hasRole("ADMIN")
                                .antMatchers("/create/**").hasRole("MANAGER")
                                .antMatchers("/delete/**").hasRole("MANAGER")
                                .antMatchers("/list/**").hasAnyRole("ASSISTANT","MANAGER")
            .and()
                    .exceptionHandling().accessDeniedPage("/access-denied");

    }
}
