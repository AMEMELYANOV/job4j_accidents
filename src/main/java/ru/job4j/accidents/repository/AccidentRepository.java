package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Accident;

/**
 * Хранилище инцидентов
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.accidents.model.Accident
 */
public interface AccidentRepository extends CrudRepository<Accident, Integer> {

    /**
     * Возвращает инцидент по идентификатору.
     *
     * @param id идентификатор
     * @return инцидент
     */
    Accident findAccidentById(int id);
}
