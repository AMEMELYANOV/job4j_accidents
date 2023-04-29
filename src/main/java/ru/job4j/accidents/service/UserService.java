package ru.job4j.accidents.service;

import ru.job4j.accidents.model.User;

import java.util.List;

/**
 * Сервис по работе с пользователями
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.accidents.model.User
 */
public interface UserService {

    /**
     * Выполняет поиск пользователя по имени.
     *
     * @param username имя пользователя
     * @return пользователя при успешном нахождении
     */
    User findByUsername(String username);

    /**
     * Сохраняет пользователя в базе данных, если пользователь существует,
     * будет выполнено его обновление.
     *
     * @param user пользователь
     * @return сохраненный пользователь
     */
    User save(User user);

    /**
     * Возвращает список пользователей.
     *
     * @return список пользователей пользователь
     */
    List<User> findAllUsers();

    /**
     * Удаляет пользователя по имени.
     *
     * @param username имя пользователя
     */
    void deleteByUsername(String username);
}
