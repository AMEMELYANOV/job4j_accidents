package ru.job4j.accidents.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO модели файл
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
@AllArgsConstructor
@Data
public class FileDto {

    /**
     * Имя файла
     */
    private String name;

    /**
     * Байтовый массив содержимого файла
     */
    private byte[] content;
}
