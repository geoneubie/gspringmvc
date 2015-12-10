package csb.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity

import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler

@Configuration
@EnableWebMvcSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        SimpleUrlAuthenticationSuccessHandler authenticationSuccessHandler = new SimpleUrlAuthenticationSuccessHandler();
        authenticationSuccessHandler.setDefaultTargetUrl("/fileupload/index.html");
        authenticationSuccessHandler.setTargetUrlParameter("redirect");
        return authenticationSuccessHandler;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser( "dave" ).password( "dave" ).roles("USER").and()
                .withUser( "admin" ).password( "admin" ).roles("USER", "ADMIN")
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
                .disable()
            .authorizeRequests()
                .antMatchers( "/fileupload/**" )
                .authenticated()
                .anyRequest().permitAll()
            .and()
                .formLogin()
                .loginPage( "/login" )
                .successHandler(authenticationSuccessHandler())
                .failureUrl( "/login?error" )
                .permitAll()
            .and()
                .logout()
                .logoutUrl("/login?logout")
                .permitAll()
    }

}
