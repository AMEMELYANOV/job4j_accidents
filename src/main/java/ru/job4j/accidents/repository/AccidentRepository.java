package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Хранилище задач
 * @see ru.job4j.accidents.model.Accident
 * @author Alexander Emelyanov
 * @version 1.0
 */
public interface AccidentRepository {

    /**
     * Возвращает список всех инцидентов.
     *
     * @return список всех задач
     */
    List<Accident> findAll();

    /**
     * Выполняет добавление инцидента в хранилище.
     * Возвращает инцидент с проинициализированным идентификатором.
     *
     * @param accident инцидент
     * @return Optional.ofNullable() с сохраненным объектом accident
     */
    Optional<Accident> create(Accident accident);

    /**
     * Выполняет обновление инцидента.
     *
     * @param accident инцидент
     * @return Optional.ofNullable() с обновленным объектом accident
     */
    Optional<Accident> update(Accident accident);

    /**
     * Выполняет поиск инцидента по идентификатору.
     * Возвращает Optional, может содержать null,
     * если инцидент не найден.
     *
     * @param id идентификатор инцидента
     * @return Optional.ofNullable() с объектом accident
     */
    Optional<Accident> findById(int id);

    /**
     * Возвращает список всех типов инцидентов.
     *
     * @return список всех типов инцидентов
     */
    List<AccidentType> findAllAccidentTypes();

    /**
     * Возвращает список всех статей инцидентов.
     *
     * @return список всех статей инцидентов
     */
    List<Rule> findAllAccidentRules();

    /**
     * Возвращает тип инцидента по идентификатору.
     * Возвращает Optional, может содержать null,
     * если тип не найден.
     *
     * @param id идентификатор типа инцидента
     * @return  @return Optional.ofNullable() с объектом accidentType
     */
    Optional<AccidentType> findTypeById(int id);

    /**
     * Возвращает множество статей инцидента по идентификаторам.
     *
     * @param ids строковый массив идентификаторов статей
     * @return множество статей
     */
    Set<Rule> findRulesByIds(String[] ids);
}
