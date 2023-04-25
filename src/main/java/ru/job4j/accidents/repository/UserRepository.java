package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.User;

/**
 * Хранилище пользователей
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.accidents.model.User
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    /**
     * Возвращает пользователя по имени.
     *
     * @param username имя пользователя
     * @return пользователь
     */
    User findByUsername(String username);
}
