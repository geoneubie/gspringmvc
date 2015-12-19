package csb.config

import csb.service.UserService
import csb.service.security.CurrentUserDetailsService
import csb.service.security.CurrentUserService
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableWebMvcSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public CurrentUserDetailsService userDetailsService(){

        CurrentUserDetailsService userDetailsService = new CurrentUserDetailsService()
        return userDetailsService

    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        PasswordEncoder encoder = new BCryptPasswordEncoder()
        return encoder

    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {

        SimpleUrlAuthenticationSuccessHandler authenticationSuccessHandler = new SimpleUrlAuthenticationSuccessHandler()
        authenticationSuccessHandler.setDefaultTargetUrl( "/fileupload/index.html" )
        authenticationSuccessHandler.setTargetUrlParameter( "redirect" )
        return authenticationSuccessHandler

    }

    @Override
    public void configure( AuthenticationManagerBuilder auth ) throws Exception {

        auth
                .userDetailsService( userDetailsService() )
                .passwordEncoder( passwordEncoder() )

    }

    @Override
    protected void configure( HttpSecurity http ) throws Exception {

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
