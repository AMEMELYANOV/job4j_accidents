package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.AccidentType;

/**
 * Хранилище типов инцидентов
 * @see ru.job4j.accidents.model.AccidentType
 * @author Alexander Emelyanov
 * @version 1.0
 */
public interface AccidentTypeRepository extends CrudRepository<AccidentType, Integer> {
}