package ru.job4j.accidents.service;

import ru.job4j.accidents.dto.FileDto;
import ru.job4j.accidents.model.File;

import java.util.Optional;

/**
 * Сервис по работе с файлами
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.accidents.model.File
 */
public interface FileService {

    /**
     * Выполняет сохранение файла на основании пути dto объекта,
     * так же сохраняет объект File в репозитории файлов.
     *
     * @param fileDto dto объект файла
     * @return сохраненный файл
     */
    File save(FileDto fileDto);

    /**
     * Выполняет поиск файла в репозитории по идентификатору,
     * если файл в репозитории не найден, то возвращается Optional.empty(),
     * иначе возвращает объект dto обернутый в Optional и содержащий байтовое
     * представление файла.
     *
     * @param id идентификатор
     * @return объект dto файла обернутый в Optional
     */
    Optional<FileDto> getFileById(int id);

    /**
     * Выполняет удаление файла в репозитории по идентификатору,
     * если файл в репозитории не найден, то возвращается false,
     * иначе выполняется удаление файла из репозитория и из директории
     * хранения файлов, после этого методом возвращается true.
     *
     * @param id идентификатор
     * @return true или false в зависимости от результата работы
     */
    boolean deleteById(int id);
}