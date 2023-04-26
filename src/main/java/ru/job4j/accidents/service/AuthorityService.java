package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Authority;

import java.util.List;

public interface AuthorityService {

    /**
     * Выполняет поиск роли по наименованию.
     *
     * @param authority наименование роли
     * @return роль при успешном нахождении
     */
    Authority findByAuthority(String authority);

    /**
     * Возвращает список ролей пользователей.
     *
     * @return список ролей пользователей пользователь
     */
    List<Authority> findAllAuthorities();
}
