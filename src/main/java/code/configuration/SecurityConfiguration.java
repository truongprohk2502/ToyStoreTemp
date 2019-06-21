package code.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authProvider;
    }
//    use for c3
//    @Autowired
//    DataSource dataSource;

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {

//   C1:     auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
        auth.authenticationProvider(authenticationProvider());
//   C2:     auth.inMemoryAuthentication().withUser("bill").password("abc123").roles("USER");
//           auth.inMemoryAuthentication().withUser("admin").password("root123").roles("ADMIN");
//   C3:           auth.jdbcAuthentication().usersByUsernameQuery("select username,password, enabled from users where username=?")
//                .authoritiesByUsernameQuery("select username, role from user_roles where username=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers( "/buy-something").access("hasRole('USER')")
                .antMatchers("/my-account").access("hasRole('USER')")
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/home")
                .usernameParameter("username").passwordParameter("password")
                .and().csrf()

                // chua co phan quyen admin seller
//                .and().exceptionHandling().accessDeniedPage("/Access_Denied")

                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }
}
