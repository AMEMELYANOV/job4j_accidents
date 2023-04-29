package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Application;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.service.ImplUserService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Тест класс реализации контроллеров
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.accidents.controller.RegistrationController
 */
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
class RegistrationControllerTest {

    /**
     * Объект заглушка направления запросов
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Объект заглушка для ImplUserService
     */
    @MockBean
    private ImplUserService userService;

    /**
     * Выполняется проверка возвращения страницы регистрации.
     */
    @Test
    @WithMockUser
    public void whenGetRegistrationThanReturnRegistrationView() throws Exception {
        this.mockMvc.perform(get("/registration"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/registration"));
    }

    /**
     * Выполняется проверка редиректа на страницу логина после регистрации пользователя.
     */
    @Test
    @WithMockUser
    public void whenPostRegistrationThanRedirectLoginView() throws Exception {
        this.mockMvc.perform(post("/registration")
                        .param("username", "user")
                        .param("password", "password"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        ArgumentCaptor<User> user = ArgumentCaptor.forClass(User.class);
        verify(userService).save(user.capture());
        assertThat(user.getValue().getUsername()).isEqualTo("user");
    }

}