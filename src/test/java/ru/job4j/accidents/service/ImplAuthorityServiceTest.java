package ru.job4j.accidents.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.job4j.accidents.Application;
import ru.job4j.accidents.model.Authority;
import ru.job4j.accidents.repository.AuthorityRepository;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@SpringBootTest(classes = Application.class)
class ImplAuthorityServiceTest {

    /**
     * Объект заглушка для UserRepository
     */
    @MockBean
    private AuthorityRepository authorityRepository;

    /**
     * Объект для доступа к методам UserService
     */
    private AuthorityService authorityService;

    /**
     * Роль
     */
    private Authority authority;

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
        authorityService = new ImplAuthorityService(authorityRepository);
    }

    /**
     * Выполняется проверка возвращения пользователя, при возврате
     * от userRepository Optional.of(user), т.е. если пользователь найден по username.
     */
    @Test
    void whenFindAuthorityByAuthorityThenReturnAuthority() {
        doReturn(Optional.of(authority)).when(authorityRepository).findByAuthority(authority.getAuthority());
        Authority authorityFromDB = authorityService.findByAuthority(authority.getAuthority());

        assertThat(authorityFromDB).isEqualTo(authority);
    }

    /**
     * Выполняется проверка выброса исключения, при возврате от
     * userRepository Optional.empty(), если задача не найдена по id.
     */
    @Test
    void whenFindAuthorityByAuthorityThenThrowsException() {
        doReturn(Optional.empty()).when(authorityRepository).findByAuthority(authority.getAuthority());

        assertThrows(NoSuchElementException.class,
                () -> authorityService.findByAuthority(authority.getAuthority()));
    }

    /**
     * Выполняется проверка возвращения списка ролей пользователей,
     * если в списке есть элементы.
     */
    @Test
    void whenFindAllAuthoritiesThenReturnList() {
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authority);
        doReturn(authorities).when(authorityRepository).findAll();
        List<Authority> authorityList = authorityService.findAllAuthorities();

        assertThat(authorityList).isNotNull();
        assertThat(authorityList.size()).isEqualTo(1);
    }

    /**
     * Выполняется проверка возвращения списка ролей пользователей,
     * если список пустой.
     */
    @Test
    void whenFindAllAuthoritiesThenReturnEmptyList() {
        doReturn(Collections.emptyList()).when(authorityRepository).findAll();
        List<Authority> authorityList = authorityService.findAllAuthorities();

        assertThat(authorityList).isEmpty();
    }
}