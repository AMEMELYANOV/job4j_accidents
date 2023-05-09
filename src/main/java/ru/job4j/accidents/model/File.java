package ru.job4j.accidents.model;

import lombok.*;

import javax.persistence.*;

/**
 * Модель данных файл
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "files")
public class File {

    /**
     * Идентификатор файла
     */
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Имя файла
     */
    private String name;

    /**
     * Путь к файлу
     */
    private String path;

    /**
     * Конструктор
     *
     * @param name имя файла
     * @param path путь к файлу
     */
    public File(String name, String path) {
        this.name = name;
        this.path = path;
    }
}