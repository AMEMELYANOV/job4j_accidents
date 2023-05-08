package ru.job4j.accidents.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

/**
 * Конфигурация системы безопасности
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig {

    /**
     * Источник данных
     */
    private final DataSource dataSource;

    /**
     * Создание бина PasswordEncoder для
     * шифрования паролей пользователей.
     *
     * @return объект BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Организует взаимодействие между системой безопасности и хранилища
     * пользователей для выполнения аутентификации.
     * Реализация использует менеджер аутентификации через JDBC.
     *
     * @return объект для работы с пользователями
     */
    @Bean
    public UserDetailsManager authenticateUsers() {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.setUsersByUsernameQuery("SELECT username, password, enabled "
                + "FROM users WHERE username = ?");
        users.setAuthoritiesByUsernameQuery(
                "SELECT u.username, a.authority "
                        + "FROM authorities as a, users as u "
                        + "WHERE u.username = ? AND u.authority_id = a.id"
        );
        return users;
    }

    /**
     * Создает конфигурацию авторизации пользователей при работе с приложением.
     *
     * @param http объект HttpSecurity для которого выполняется настройка авторизации
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login", "/registration")
                .permitAll()
                .antMatchers("/**")
                .hasAnyRole("ADMIN", "USER", "INSPECTOR")
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/index")
                .failureUrl("/login?error=true")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .permitAll()
                .and()
                .csrf()
                .disable();
        return http.build();
    }
}