package ru.job4j.accidents.model;

import lombok.*;

import java.util.Set;

/**
 * Модель данных инцидент
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Accident {

    /**
     * Идентификатор инцидента
     */
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Наименование инцидента
     */
    private String name;

    /**
     * Описание инцидента
     */
    private String text;

    /**
     * Адрес инцидента
     */
    private String address;

    /**
     * Тип инцидента
     */
    private AccidentType type;

    /**
     * Множество статей
     */
    private Set<Rule> rules;
}