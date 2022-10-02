package com.mindhub.homebanking.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity // conecta la aplicacion a la segutidad de spring
@Configuration //sobreescribe la configuracion de spring security antes de iniciar la aplicacion
public class WebAuthorization extends WebSecurityConfigurerAdapter { // WSCA es la que configura la autorizacion de la aplicacion


    @Override
    protected void configure(HttpSecurity http) throws Exception {  // es un metodo que viene en WSCA, el cual nosotros queremos modificar/personalizar

        http.authorizeRequests()
                .antMatchers("/Web/index.html","/Web/index.js","/Web/index.css","/Web/img/desktop-wallpaper.webp","/Web/img/40d21e1d7f8e6b1ed55caaeffe104c80.jpg","/Web/img/39cf90aaa8744eaf8d50f25da63ad21a.png","/Web/img/52e06e07244a3590366669665ea540e3-icone-do-circulo-do-banco-3.png","/Web/img/instagram-new--v2.png","/Web/img/facebook-new.png","/Web/img/twitter-circled.png","/Web/img/whatsapp.png")
                .permitAll()

                .antMatchers(HttpMethod.POST,"/api/clients")
                .permitAll()

                .antMatchers(HttpMethod.POST, "/api/clients/current/accounts","/api/clients/current/cards","/api/transactions","/api/loans")
                .hasAuthority("CLIENT")

                .antMatchers(HttpMethod.PATCH,"/api/clients/current/cards/{id}","/api/clients/current/accounts/{id}")
                .hasAuthority("CLIENT")


                .antMatchers("/Web/accounts.html","/Web/accounts.js","/Web/style.css","/Web/account.html","/Web/account.js","/Web/style2.css","/Web/cards.html","/Web/cards.js","/Web/style3.css","/Web/create-cards.html","/Web/create-cards.css","/Web/create-cards.js","/Web/transfers.html","/Web/transfers.css","/Web/transfers.js","/Web/loan-application.html","/Web/loan-application.css","/Web/loan-application.js","/Web/contact.html","/Web/contact.css","/Web/contact.js","/Web/create-account.html","/Web/create-account.css","/Web/create-account.js","/Web/img/**","/api/**")
                .hasAuthority("CLIENT")
                // .hasAuthority("CLIENT").permitAll()

                

                .antMatchers("/h2-console/**","/rest/**","/manager.html","/manager.js","/manager.css","/web/**")
                .hasAuthority("ADMIN")
                //.permitAll().hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST,"/api/clientLoans")
                .hasAuthority("ADMIN");


        http.formLogin()
                .usernameParameter("mail")
                .passwordParameter("password")
                .loginPage("/api/login")
                /*.loginPage("/Web/index.html")
                .loginProcessingUrl("/api/login")
                .defaultSuccessUrl("/Web/accounts.html",true)
                .failureUrl("/Web/index.html?error=true")*/
                /*.failureHandler((request, response, exception) -> exception.getMessage().contains())*/
                ;
        //.loginPage("/api/login") es donde se la pagina va a autenticar el cliente (es el controlador de login)    "index.html" es donde se pide el acceso al sitio. la pagina


        http.logout()
                .logoutUrl("/api/logout")
                /*.logoutSuccessUrl("/Web/index.html")*/;

        http.sessionManagement()
                .invalidSessionUrl("/Web/index.html")
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
                .expiredUrl("/Web/index.html");//no anda


     // turn off checking for CSRF tokens
     http.csrf().disable();

     //disabling frameOptions so h2-console can be accessed
     http.headers().frameOptions().disable();

     // if user is not authenticated, just send an authentication failure response
     http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

     // if login is successful, just clear the flags asking for authentication
     http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

     // if login fails, just send an authentication failure response
     http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

     // if logout is successful, just send a success response
     http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }

    }// es un metodo que borra las banderas para que no pida t0d0 el tiempo la autenticacion al cliente despues de logearse

}
