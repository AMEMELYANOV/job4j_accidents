package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Authority;

import java.util.List;

/**
 * Сервис по работе с ролями
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.accidents.model.Authority
 */
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
