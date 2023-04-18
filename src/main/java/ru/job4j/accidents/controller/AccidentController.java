package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.ImplAccidentJpaService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

/**
 * Контроллер для работы с инцидентами
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.accidents.model.Accident
 */
@Controller
@AllArgsConstructor
public class AccidentController {

    /**
     * Объект для доступа к методам AccidentService
     */
    private final ImplAccidentJpaService accidentService;

    /**
     * Возвращает страницу списка инцидентов.
     *
     * @param model модель
     * @return страница списка инцидентов
     */
    @GetMapping("index")
    public String index(Model model) {
        Collection<Accident> accidents = accidentService.findAll();
        model.addAttribute("user", SecurityContextHolder
                .getContext().getAuthentication().getPrincipal());
        model.addAttribute("accidents", accidents);
        return "index";
    }

    /**
     * Возвращает страницу добавления инцидента.
     *
     * @param model модель
     * @return страница добавления инцидента
     */
    @GetMapping("/addAccident")
    public String viewAddAccident(Model model) {
        model.addAttribute("user", SecurityContextHolder
                .getContext().getAuthentication().getPrincipal());
        model.addAttribute("types", accidentService.findAllAccidentTypes());
        model.addAttribute("rules", accidentService.findAllAccidentRules());
        return "accident/addAccident";
    }

    /**
     * Возвращает страницу редактирования инцидента.
     *
     * @param accidentId идентификатор инцидента
     * @param model      модель
     * @return страница редактирования инцидента
     */
    @GetMapping("/editAccident")
    public String viewEditAccident(@RequestParam("accidentId") int accidentId, Model model) {
        model.addAttribute("user", SecurityContextHolder
                .getContext().getAuthentication().getPrincipal());
        model.addAttribute("accident", accidentService.findById(accidentId));
        model.addAttribute("types", accidentService.findAllAccidentTypes());
        model.addAttribute("rules", accidentService.findAllAccidentRules());
        return "accident/editAccident";
    }

    /**
     * Получает от форм создания или редактирования объект инцидента и передает
     * для дальнейшего сохранения или обновления в хранилище данных.
     *
     * @param accident инцидент
     * @param request  запрос пользователя
     * @return редирект на страницу списка инцидентов
     */
    @PostMapping("/saveAccident")
    public String save(@RequestParam("dateTime") String dateTime,
            @ModelAttribute Accident accident, HttpServletRequest request) {
        accident.setCreated(LocalDateTime.parse(dateTime));
        String[] ids = request.getParameterValues("rIds");
        accidentService.createOrUpdateAccident(accident, ids);
        return "redirect:/index";
    }

    /**
     * Обрабатывает GET запрос, возвращает страницу с подробной
     * информацией об инциденте.
     *
     * @param accidentId идентификатор инцидента
     * @param model      модель
     * @param request    запрос пользователя
     * @return страница с подробной информацией об инциденте
     */
    @GetMapping("/accidentDetails{accidentId}")
    public String getAccidentDetails(@RequestParam(value = "accidentId") int accidentId,
                                     Model model, HttpServletRequest request) {
        model.addAttribute("user", SecurityContextHolder
                .getContext().getAuthentication().getPrincipal());
        model.addAttribute("accident", accidentService.findAccidentById(accidentId));
        return "accident/accidentDetails";
    }
}