package code.configuration;

import code.model.Account;
import code.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccountService accountService;


    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        for (Account a : accountService.findAll()) {
            auth.inMemoryAuthentication().withUser(a.getUsername()).password(a.getPassword()).roles(a.getRole());
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);

        http.csrf().disable();

        // permit all
        http.authorizeRequests().antMatchers("/home", "/products-brand/**", "/products-category/**", "/detail/**", "/search-sort/**", "/search", "/price-range/**");

        // Only user or admin
        http.authorizeRequests().antMatchers("/contact").hasAnyRole("USER", "ADMIN");

        // Access denied
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/denied");

        // Form login
        http.authorizeRequests().and().formLogin()
                .loginProcessingUrl("/login-processing") // Submit URL
                .loginPage("/login")                    // login page url
                .defaultSuccessUrl("/home")             // login successful
                .failureUrl("/login?error=true")        // login failed
                .usernameParameter("username")
                .passwordParameter("password")
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }
}
