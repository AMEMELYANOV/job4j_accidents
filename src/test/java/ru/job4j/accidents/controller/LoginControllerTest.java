package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Application;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Тест класс реализации контроллеров
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.accidents.controller.LoginController
 */
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
class LoginControllerTest {

    /**
     * Объект заглушка направления запросов
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Выполняется проверка возвращения страницы логина.
     */
    @Test
    @WithMockUser
    public void whenGetLoginThanReturnLoginView() throws Exception {
        this.mockMvc.perform(get("/login")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/login"));
    }

    /**
     * Выполняется проверка редиректа на страницу логина с соответствующим
     * параметром при выходе пользователя.
     */
    @Test
    @WithMockUser
    public void whenGetLogoutThanRedirectToLoginView() throws Exception {
        this.mockMvc.perform(get("/logout")
                        .param("error", "true")
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?logout=true"));
    }
}