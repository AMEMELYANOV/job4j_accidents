package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.service.AccidentService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        List<AccidentType> types = accidentService.findAllAccidentTypes();
        model.addAttribute("types", types);
        return "accident/addAccident";
    }

    /**
     * Получает с формы создания инцидента объект и передает
     * для дальнейшего сохранения в хранилище данных методу
     * сервисного слоя {@link AccidentService#create(Accident)}.
     *
     * @param accident инцидент
     * @return редирект на страницу списка инцидентов
     */
    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident) {
        accidentService.create(accident);
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
        return "accident/editAccident";
    }

    /**
     * Получает с формы редактирования инцидента объект и передает
     * для дальнейшего обновления в хранилище данных методу
     * сервисного слоя {@link AccidentService#update(Accident)}.
     *
     * @param accident инцидент
     * @return редирект на страницу списка инцидентов
     */
    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident) {
        accidentService.update(accident);
        return "redirect:/accidents";
    }
}