package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.AccidentType;

/**
 * Хранилище типов инцидентов
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.accidents.model.AccidentType
 */
public interface AccidentTypeRepository extends CrudRepository<AccidentType, Integer> {
}