package expeditors.backend.jconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailService(){
        UserDetails admin = User
                .withUsername("admin")
                .password("{bcrypt}$2a$04$1BDg//200baRK2DIWLy9Oe1yR7lYdoJP31MFwGMmGorjbh3.Z/5YG")
                .roles("ADMIN", "USER")
                .build();

        UserDetails user = User.withUsername("user")
                .password("{bcrypt}$2a$04$7/EVpgT6spVEXFE6DZoveOYru20gizN40opqugzckjffKTOLuIBLO")
                .roles("USER")
                .build();

        var userDetailsService = new InMemoryUserDetailsManager(admin, user);
        return userDetailsService;
    }

    @Bean
    public SecurityFilterChain trackPricingChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
            auth.anyRequest().authenticated();
        });
        http.httpBasic(Customizer.withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
