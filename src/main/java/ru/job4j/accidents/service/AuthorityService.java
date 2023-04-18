package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Authority;
import ru.job4j.accidents.repository.AuthorityRepository;

/**
 * Сервис по работе с ролями
 * @see ru.job4j.accidents.model.Accident
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class AuthorityService {

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
    public Authority findByAuthority(String authority) {
        return authorityRepository.findByAuthority(authority);
    }
}
