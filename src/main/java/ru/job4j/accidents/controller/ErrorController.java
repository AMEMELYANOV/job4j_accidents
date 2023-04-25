package ru.job4j.accidents.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Контроллер для работы с исключениями
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
@ControllerAdvice
@Slf4j
public class ErrorController {

    /**
     * Возвращает страницу для вывода информации об исключении
     * или ошибке.
     *
     * @param throwable исключение или ошибка
     * @param model     модель
     * @return страница вывода исключения или ошибки
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(Throwable throwable, Model model) {
        log.error("Exception или Error во время выполнения приложения", throwable);
        String errorMessage = (throwable != null ? throwable.getMessage() : "Неизвестная ошибка");
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
}