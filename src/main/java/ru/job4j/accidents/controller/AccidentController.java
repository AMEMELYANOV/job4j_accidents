package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * Контроллер для стартовой страницы приложения
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Controller
@AllArgsConstructor
public class AccidentController {

    /**
     * Объект для доступа к методам AccidentService
     */
    AccidentService accidentService;

    /**
     * Возвращает страницу списка инцидентов.
     *
     * @return страница списка инцидентов
     */
    @GetMapping({"/", "accidents"})
    public String index(Model model) {
        Collection<Accident> accidents = accidentService.findAll();
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
        model.addAttribute("types", accidentService.findAllAccidentTypes());
        model.addAttribute("rules", accidentService.findAllAccidentRules());
        return "accident/addAccident";
    }

    /**
     * Получает с формы создания инцидента объект и передает
     * для дальнейшего сохранения в хранилище данных методу
     * сервисного слоя {@link AccidentService#createOrUpdateAccident(Accident, String[])}.
     *
     * @param accident инцидент
     * @param request запрос пользователя
     * @return редирект на страницу списка инцидентов
     */
    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, HttpServletRequest request) {
        String[] ids = request.getParameterValues("rIds");
        accidentService.createOrUpdateAccident(accident, ids);
        return "redirect:/accidents";
    }

    /**
     * Возвращает страницу редактирования инцидента.
     *
     * @return страница редактирования инцидента
     */
    @GetMapping("/editAccident")
    public String viewEditAccident(@RequestParam("accidentId") int accidentId, Model model) {
        model.addAttribute("accident", accidentService.findById(accidentId));
        model.addAttribute("types", accidentService.findAllAccidentTypes());
        model.addAttribute("rules", accidentService.findAllAccidentRules());
        return "accident/editAccident";
    }
}