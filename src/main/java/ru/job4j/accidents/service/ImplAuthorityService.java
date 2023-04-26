package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Authority;
import ru.job4j.accidents.repository.AuthorityRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис по работе с ролями
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.accidents.model.Accident
 */
@Service
@AllArgsConstructor
public class ImplAuthorityService implements AuthorityService {

    /**
     * Объект для доступа к методам AuthorityRepository
     */
    private final AuthorityRepository authorityRepository;

    /**
     * Выполняет поиск роли по наименованию.
     *
     * @param authority наименование роли
     * @return роль при успешном нахождении
     */
    @Override
    public Authority findByAuthority(String authority) {
        return authorityRepository.findByAuthority(authority);
    }

    /**
     * Возвращает список ролей пользователей.
     *
     * @return список ролей пользователей пользователь
     */
    @Override
    public List<Authority> findAllAuthorities() {
        List<Authority> authorities = new ArrayList<>();
        authorityRepository.findAll().forEach(authorities::add);
        return authorities;
    }
}
