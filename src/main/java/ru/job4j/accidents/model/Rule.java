package ru.job4j.accidents.model;

import lombok.*;

/**
 * Модель данных статья
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Rule {

    /**
     * Идентификатор статьи
     */
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Наименование статьи
     */
    private String name;
}