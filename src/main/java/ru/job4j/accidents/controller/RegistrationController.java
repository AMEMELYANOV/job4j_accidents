package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.service.AuthorityService;
import ru.job4j.accidents.service.UserService;

/**
 * Контроллер страницы регистрации пользователя
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Controller
@AllArgsConstructor
public class RegistrationController {

    /**
     * Шифратор пароля
     */
    private final PasswordEncoder encoder;

    /**
     * Объект для доступа к методам UserService
     */
    private final UserService userService;

    /**
     * Объект для доступа к методам AuthorityService
     */
    private final AuthorityService authorityService;

    /**
     * Обрабатывает GET запрос, возвращает страницу регистрации пользователя.
     * В зависимости от параметров password и account на страницу будут выведены
     * сообщения для пользователя о необходимости исправить вводимые данные.
     *
     * @param error параметр GET запроса, true, если есть ошибка сохранения пользователя
     * @param model модель
     * @return страница регистрации пользователя
     */
    @GetMapping("/registration")
    public String regPage(@RequestParam(value = "error", required = false) String error, Model model) {
        String errorMessage = null;
        if (error != null) {
            errorMessage = "Пользователь с таким именем уже существует!";
        }
        model.addAttribute("user", new User());
        model.addAttribute("errorMessage", errorMessage);
        return "user/registration";
    }

    /**
     * Обрабатывает POST запрос. При удачном сохранении пользователя в базе данных,
     * выполняется перенаправление на страницу входа, при неудачной регистрации с
     * параметром ошибки error=true.
     *
     * @param user пользователь сформированный из данных формы регистрации
     * @return страница входа пользователя
     */
    @PostMapping("/registration")
    public String regSave(@ModelAttribute User user) {
        if (userService.findByUsername(user.getUsername()) != null) {
            return "redirect:/registration?error=true";
        }
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(authorityService.findByAuthority("ROLE_USER"));
        userService.save(user);
        return "redirect:/login";
    }
}