package api;

import api.db.users.Users;
import api.db.users.UsersManager;
import com.amazonaws.services.organizations.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * User authentication configuration
 * @author mayank
 */
@Configuration
public class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {
 /* private @Autowired UsersManager users;

  @Bean
  public DaoAuthenticationProvider authProvider() {
    final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(getUserDetailsService());
    authProvider.setPasswordEncoder(getPasswordEncoder());
    return authProvider;
  }

  @Bean
  public UserDetailsService getUserDetailsService() {
    return username -> users.stream()
        .filter(Users.USERNAME.equal(username))
        .findAny()
        .orElseThrow(() -> new UsernameNotFoundException(
            "Could not find the user '" + username + "'"
        ));
  }

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public void init(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(getUserDetailsService())
        .passwordEncoder(getPasswordEncoder());
  }*/
}
