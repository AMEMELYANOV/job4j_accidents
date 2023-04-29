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
import ru.job4j.accidents.service.ImplAuthorityService;
import ru.job4j.accidents.service.ImplUserService;
import org.springframework.security.core.userdetails.*;

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
    private final ImplUserService userService;

    /**
     * Объект для доступа к методам AuthorityService
     */
    private final ImplAuthorityService authorityService;

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
     * Обрабатывает POST запрос, выполняется перенаправление на  входа.
     * При удачной валидации пользователя, пользователь обновляется в базе,
     * при неудачной валидации выполняется переадресация на страницу редактирования
     * пользователя с параметром password со значением true.
     *
     * @param user        пользователь сформированный из данных формы редактирования
     * @param oldPassword старый пароль пользователя
     * @return перенаправление на страницу входа
     */
    @PostMapping("/userEdit")
    public String userEdit(@ModelAttribute User user,
                           @RequestParam(value = "oldPassword") String oldPassword) {
        var currentUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        User userFromDB = userService.findByUsername(currentUser.getUsername());

        if (!userFromDB.getUsername().equals(user.getUsername())
                && userService.findByUsername(user.getUsername()) != null) {
            return "redirect:/userEdit?username=true";
        }
        if (oldPassword == null
                || (!encoder.matches(oldPassword, userFromDB.getPassword()))) {
            return "redirect:/userEdit?password=true";
        }
        userFromDB.setUsername(user.getUsername());
        userFromDB.setPassword(encoder.encode(user.getPassword()));
        userService.save(userFromDB);
        return "redirect:/login";
    }

    /**
     * Обрабатывает GET запрос, возвращает страницу со списков
     * пользователей, страница доступна пользователям с ролью
     * администратора.
     *
     * @param model модель
     * @return страница списка пользователей
     */
    @GetMapping("/getUsers")
    public String getUsers(Model model) {
        model.addAttribute("authorities", authorityService.findAllAuthorities());
        model.addAttribute("user", SecurityContextHolder
                .getContext().getAuthentication().getPrincipal());
        model.addAttribute("users", userService.findAllUsers());
        return "user/users";
    }

    /**
     * Обрабатывает POST запрос, сохраняет пользователя с измененной
     * ролью по переданному имени.
     *
     * @param user    пользователь
     * @param newRole новая роль пользователя
     * @param model   модель
     * @return страница списка пользователей
     */
    @PostMapping("/saveAuthority")
    public String saveAuthority(@ModelAttribute User user,
                                @RequestParam(value = "newRole") String newRole,
                                Model model) {
        user.setAuthority(authorityService.findByAuthority(newRole));
        userService.save(user);
        model.addAttribute("authorities", authorityService.findAllAuthorities());
        model.addAttribute("user", SecurityContextHolder
                .getContext().getAuthentication().getPrincipal());
        model.addAttribute("users", userService.findAllUsers());
        return "user/users";
    }

    /**
     * Обрабатывает POST запрос, удаляет пользователя
     * по переданному имени.
     *
     * @param username имя пользователя
     * @param model    модель
     * @return страница списка пользователей
     */
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam(value = "username") String username,
                             Model model) {
        userService.deleteByUsername(username);
        model.addAttribute("authorities", authorityService.findAllAuthorities());
        model.addAttribute("user", SecurityContextHolder
                .getContext().getAuthentication().getPrincipal());
        model.addAttribute("users", userService.findAllUsers());
        return "user/users";
    }
}
