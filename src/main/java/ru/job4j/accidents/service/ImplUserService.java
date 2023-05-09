package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Реализация сервиса по работе с пользователями
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.accidents.service.UserService
 */
@Service
@AllArgsConstructor
public class ImplUserService implements UserService {

    /**
     * Объект для доступа к методам UserRepository
     */
    private final UserRepository userRepository;

    /**
     * Выполняет поиск пользователя по имени.
     *
     * @param username имя пользователя
     * @return пользователя при успешном нахождении
     */
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Сохраняет пользователя в базе данных, если пользователь существует,
     * будет выполнено его обновление.
     *
     * @param user пользователь
     * @return сохраненный пользователь
     */
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Возвращает список пользователей.
     *
     * @return список пользователей пользователь
     */
    @Override
    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    /**
     * Удаляет пользователя по имени.
     *
     * @param username имя пользователя
     */
    @Transactional
    @Override
    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }
}
