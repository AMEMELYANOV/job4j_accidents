package ru.job4j.accidents.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.job4j.accidents.Application;
import ru.job4j.accidents.model.Authority;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

/**
 * Тест класс реализации сервисного слоя
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.accidents.service.ImplUserService
 */
@SpringBootTest(classes = Application.class)
class ImplUserServiceTest {

    /**
     * Объект заглушка для UserRepository
     */
    @MockBean
    private UserRepository userRepository;

    /**
     * Объект для доступа к методам UserService
     */
    private UserService userService;

    /**
     * Роль
     */
    private Authority authority;

    /**
     * Пользователь
     */
    private User user;

    /**
     * Создает необходимые для выполнения тестов общие объекты.
     * Создание выполняется перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        authority = Authority.builder()
                .id(1)
                .authority("auth")
                .build();
        user = User.builder()
                .id(1)
                .username("username")
                .password("pass")
                .enabled(true)
                .authority(authority)
                .build();
        userService = new ImplUserService(userRepository);
    }

    /**
     * Выполняется проверка возвращения пользователя, при возврате
     * от userRepository, т.е. если пользователь найден по username.
     */
    @Test
    void whenFindUserByUsernameThenReturnUser() {
        doReturn(user).when(userRepository).findByUsername(user.getUsername());
        User userFromDB = userService.findByUsername(user.getUsername());

        assertThat(userFromDB).isEqualTo(user);
    }

    /**
     * Выполняется проверка возвращения значения null, при возврате от
     * userRepository, если пользователь не найден по username.
     */
    @Test
    void whenFindUserByUsernameThenThrowsException() {
        doReturn(null).when(userRepository).findByUsername(user.getUsername());
        User userFromDB = userService.findByUsername(user.getUsername());

        assertThat(userFromDB).isNull();
    }

    /**
     * Выполняется проверка возврата пользователь, при возврате от
     * userRepository, если пользователь был сохранен.
     */
    @Test
    void whenAddUserThenReturnUser() {
        doReturn(user).when(userRepository).save(user);
        User userFromDB = userService.save(user);
        assertThat(userFromDB).isEqualTo(user);
        assertThat(userFromDB).isNotNull();
    }

    /**
     * Выполняется проверка возвращения списка пользователей,
     * если в списке есть элементы.
     */
    @Test
    void whenFindAllUsersThenReturnList() {
        List<User> users = new ArrayList<>();
        users.add(user);
        doReturn(users).when(userRepository).findAll();
        List<User> userList = userService.findAllUsers();

        assertThat(userList).isNotNull();
        assertThat(userList.size()).isEqualTo(1);
    }

    /**
     * Выполняется проверка возвращения списка пользователей,
     * если список пустой.
     */
    @Test
    void whenFindAllUsersThenReturnEmptyList() {
        doReturn(Collections.emptyList()).when(userRepository).findAll();
        List<User> userList = userService.findAllUsers();

        assertThat(userList).isEmpty();
    }

}