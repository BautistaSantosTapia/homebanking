package com.mindhub.homebanking.configurations;

import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.modals.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration //sobreescribe la configuracion de spring security antes de iniciar la aplicacion
public class WebAuthentication extends GlobalAuthenticationConfigurerAdapter {  // GACA nos indica como va a buscar los detalles del usuario

   /*@Autowired
   ClientRepository repository;*/
    @Autowired
    ClientService clientService;

   @Override  // indica que lo vamos a sobreescribir (no es necesario)
   public void init(AuthenticationManagerBuilder auth) throws Exception {  // es un metodo que viene en GACA, el cual nosotros queremos modificar/personalizar

       auth.userDetailsService(inputName-> {

           Client client = clientService.getClientMail(inputName);

           if (client != null) {
               if (client.getMail().contains("melba@lorenzo.com")){
                   return new User(client.getMail(), client.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("CLIENT, ADMIN")); //AuthorityUtils.createAuthorityList("ADMIN")
               }else {
                   return new User(client.getMail(), client.getPassword(), AuthorityUtils.createAuthorityList("CLIENT"));
               }
           } else {
               throw new UsernameNotFoundException("Unknown client: " + inputName);
           }
       });  // crea la sesionId del usuario que se esta logeando, no solo se compara el mail sino la contrasena tmb
   }


   @Bean
   public PasswordEncoder passwordEncoder() {
       return PasswordEncoderFactories.createDelegatingPasswordEncoder();
   }
    // nos devuelve un passwordEncoder que lo que hace es con la ayuda del metodo .encode() encriptar las contrasenas
}
