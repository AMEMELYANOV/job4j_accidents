package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
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

import javax.servlet.http.HttpServletRequest;

/**
 * Контроллер пользователя
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
@AllArgsConstructor
@Slf4j
@Controller
public class UserController {

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
     * Обрабатывает GET запрос, возвращает страницу редактирования пользователя.
     * Если в параметре password передано true на страницу будет выведено
     * сообщение для пользователя о необходимости исправить вводимые данные.
     *
     * @param username параметр GET запроса, true, если есть ошибка валидации имени пользователя
     * @param password параметр GET запроса, true, если есть ошибка валидации пароля
     * @param model    модель
     * @return страница редактирования пользователя
     */
    @GetMapping("/userEdit")
    public String getUserEdit(@RequestParam(value = "username", required = false) String username,
                              @RequestParam(value = "password", required = false) String password,
                              Model model) {
        String errorMessage = null;
        if (username != null) {
            errorMessage = "Пользователь с таким именем уже существует";
        }
        if (password != null) {
            errorMessage = "Неверно введен старый пароль";
        }
        model.addAttribute("user", SecurityContextHolder
                .getContext().getAuthentication().getPrincipal());
        model.addAttribute("errorMessage", errorMessage);
        return "user/userEdit";
    }

    /**
     * Обрабатывает POST запрос, выполняется перенаправление на страницу списка инцидентов.
     * При удачной валидации пользователя, пользователь обновляется в базе,
     * при неудачной валидации выполняется переадресация на страницу редактирования
     * пользователя с параметром password со значением true.
     *
     * @param user        пользователь сформированный из данных формы редактирования
     * @param oldPassword старый пароль пользователя
     * @param request     запрос пользователя
     * @return перенаправление на страницу списка задач
     */
    @PostMapping("/userEdit")
    public String userEdit(@ModelAttribute User user,
                           @RequestParam(value = "oldPassword") String oldPassword,
                           HttpServletRequest request) {
        User userFromDB = userService.findByUsername(user.getUsername());
        if (oldPassword == null || !oldPassword.equals(userFromDB.getPassword())) {
            return "redirect:/userEdit?password=true";
        }
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(authorityService.findByAuthority("ROLE_USER"));
        userService.save(user);
        request.getSession().setAttribute("user", user);
        return "redirect:/tasks";
    }
}
