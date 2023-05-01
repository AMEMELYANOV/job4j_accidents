package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Authority;

import java.util.Optional;

/**
 * Хранилище ролей
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.accidents.model.Authority
 */
@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Integer> {

    /**
     * Возвращает роль пользователя по наименованию.
     *
     * @param authority наименование роли
     * @return роль пользователя
     */
    Optional<Authority> findByAuthority(String authority);
}
