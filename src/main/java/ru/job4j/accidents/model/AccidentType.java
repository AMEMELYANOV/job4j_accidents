package ru.job4j.accidents.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Модель данных тип инцидента
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AccidentType {

    /**
     * Идентификатор типа инцидента
     */
    @EqualsAndHashCode.Include
    private int id;

    /**
     * Наименование типа инцидента
     */
    private String name;
}