package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.accidents.dto.FileDto;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.ImplAccidentJpaService;
import ru.job4j.accidents.service.ImplFileService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;
import java.util.NoSuchElementException;

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
        model.addAttribute("accident", new Accident());
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
     * @param file     файл фотографии
     * @param accident инцидент
     * @param request  запрос пользователя
     * @return редирект на страницу списка инцидентов
     * @throws IOException если возникнут ошибки при работе с потоками ввода вывода
     */
    @PostMapping("/saveAccident")
    public String save(
            @RequestParam MultipartFile file, @ModelAttribute Accident accident,
            HttpServletRequest request) throws IOException {
        String[] ids = request.getParameterValues("rIds");
        accidentService.createOrUpdateAccident(accident, ids,
                new FileDto(file.getOriginalFilename(), file.getBytes()));
        return "redirect:/index";
    }

    /**
     * Обрабатывает GET запрос, возвращает страницу с подробной
     * информацией об инциденте.
     *
     * @param accidentId идентификатор инцидента
     * @param model      модель
     * @return страница с подробной информацией об инциденте
     */
    @GetMapping("/accidentDetails")
    public String getAccidentDetails(@RequestParam(value = "accidentId") int accidentId,
                                     Model model) {
        model.addAttribute("user", SecurityContextHolder
                .getContext().getAuthentication().getPrincipal());
        model.addAttribute("accident", accidentService.findAccidentById(accidentId));
        return "accident/accidentDetails";
    }

    /**
     * Обрабатывает GET запрос, возвращает страницу со списком инцидентов.
     *
     * @param model модель
     * @return страница с подробной информацией об инциденте
     */
    @GetMapping("/accidentsAdmin")
    public String getAccidentsAdmin(Model model) {
        model.addAttribute("user", SecurityContextHolder
                .getContext().getAuthentication().getPrincipal());
        model.addAttribute("accidents", accidentService.findAll());
        return "accident/accidentsAdmin";
    }

    /**
     * Удаляет инцидент по идентификатору.
     *
     * @param accidentId идентификатор инцидента
     * @return редирект на страницу списка инцидентов
     */
    @GetMapping("/deleteAccident")
    public String deleteAccident(@RequestParam("accidentId") int accidentId) {
        accidentService.deleteById(accidentId);
        return "redirect:/index";
    }

    /**
     * Обрабатывает действия инспектора по присвоению
     * статуса инциденту и комментированию выбранного статуса.
     *
     * @param accident инцидент
     * @return редирект на страницу списка инцидентов
     */
    @PostMapping("/inspectorAction")
    public String inspectorAction(@ModelAttribute Accident accident) {
        accidentService.updateStatus(accident);
        return "redirect:/accidentDetails?accidentId=" + accident.getId();
    }

    /**
     * Обрабатывает POST запрос, возвращает страницу со списком инцидентов,
     * для администратора.
     *
     * @param accidentId идентификатор инцидента
     * @return страница с подробной информацией об инциденте
     */
    @PostMapping("/deleteAccident")
    public String getAccidentsAdmin(@RequestParam(value = "accidentId") int accidentId) {
        accidentService.deleteById(accidentId);
        return "redirect:/accidentsAdmin";
    }
}