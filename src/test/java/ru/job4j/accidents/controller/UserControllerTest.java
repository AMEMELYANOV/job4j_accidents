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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Тест класс реализации контроллеров
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.accidents.controller.UserController
 */
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
class UserControllerTest {

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
     * Выполняется проверка возвращения страницы редактирования профиля пользователя.
     */
    @Test
    @WithMockUser
    public void whenGetUserEditThanReturnUserEditView() throws Exception {
        this.mockMvc.perform(get("/userEdit"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/userEdit"));
    }

    /**
     * Выполняется проверка возвращения страницы списка пользователей.
     */
    @Test
    @WithMockUser
    public void whenGetUsersThanReturnGetUsersView() throws Exception {
        this.mockMvc.perform(get("/getUsers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/users"));
    }


    /**
     * Выполняется проверка возвращения страницы списка пользователей
     * после обновления роли пользователя.
     */
    @Test
    @WithMockUser
    public void whenPostSaveAuthorityThanReturnUsersView() throws Exception {
        this.mockMvc.perform(post("/saveAuthority")
                        .param("newRole", "ROLE_ADMIN"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/users"));

        ArgumentCaptor<User> user = ArgumentCaptor.forClass(User.class);
        verify(userService).save(user.capture());
        assertThat(user.getValue().getAuthority().getAuthority())
                .isEqualTo("ROLE_ADMIN");
    }

    /**
     * Выполняется проверка возвращения страницы списка пользователей
     * после удаления пользователя.
     */
    @Test
    @WithMockUser
    public void whenPostDeleteUserThanReturnUsersView() throws Exception {
        this.mockMvc.perform(post("/deleteUser")
                        .param("username", "user"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/users"));

        ArgumentCaptor<String> username = ArgumentCaptor.forClass(String.class);
        verify(userService).deleteByUsername(username.capture());
        assertThat(username.getValue())
                .isEqualTo("user");
    }
}