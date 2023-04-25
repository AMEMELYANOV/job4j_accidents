package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.accidents.dto.FileDto;
import ru.job4j.accidents.service.FileService;

import java.util.Optional;

/**
 * Контроллер для работы с файлами
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.accidents.model.Accident
 */
@RestController
@RequestMapping("/files")
@AllArgsConstructor
public class FileController {

    /**
     * Объект для доступа к методам FileService
     */
    private final FileService fileService;

    /**
     * Обрабатывает GET запрос, возвращает файл с фотографией инцидента.
     *
     * @param id идентификатор файла
     * @return фотография инцидента
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        Optional<FileDto> contentOptional = fileService.getFileById(id);
        if (contentOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(contentOptional.get().getContent());
    }
}
