package ru.job4j.accidents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Основной класс для запуска приложения
 * @author Alexander Emelyanov
 * @version 1.0
 */
@SpringBootApplication
public class Application {

    /**
     * Выполняет запуск приложения
     * @param args аргументы командной строки
     */
     public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("Go to http://localhost:8080");

         BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
         String pwd = encoder.encode("111");
         System.out.println(pwd);
    }
}
