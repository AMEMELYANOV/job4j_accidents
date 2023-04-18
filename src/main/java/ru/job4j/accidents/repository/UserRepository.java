package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.User;

import java.util.Optional;

/**
 * Хранилище пользователей
 * @see ru.job4j.accidents.model.User
 * @author Alexander Emelyanov
 * @version 1.0
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
