package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Реализация хранилища инцидентов в памяти
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.accidents.model.Accident
 */
@Repository
public class MemoryAccidentRepository {

    /**
     * Хранилище инцидентов
     */
    Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    /**
     * Счетчик инцидентов
     */
    AtomicInteger idx;

    /**
     * Хранилище типов инцидентов
     */
    List<AccidentType> types = new ArrayList<>();

    /**
     * Хранилище статей инцидентов
     */
    List<Rule> rules = new ArrayList<>();

    /**
     * Конструктор, инициализирует начальное состояние хранилища
     */
    public MemoryAccidentRepository() {
        Rule rule1 = Rule.builder().id(1).name("Правило. 1").build();
        Rule rule2 = Rule.builder().id(2).name("Правило. 2").build();
        Rule rule3 = Rule.builder().id(3).name("Правило. 3").build();
        rules.add(rule1);
        rules.add(rule2);
        rules.add(rule3);
        types.add(new AccidentType(1, "Несколько автомобилей"));
        types.add(new AccidentType(2, "Автомобиль и человек"));
        types.add(new AccidentType(3, "Автомобиль и велосипед"));
        accidents.put(1, Accident.builder().id(1).name("name1").address("address1").description("description1")
                .type(types.get(0)).rules(Set.of(rule1, rule2)).build());
        accidents.put(2, Accident.builder().id(2).name("name2").address("address2").description("description2")
                .type(types.get(1)).rules(Set.of(rule2, rule3)).build());
        accidents.put(3, Accident.builder().id(3).name("name3").address("address3").description("description3")
                .type(types.get(2)).rules(Set.of(rule1, rule3)).build());
        idx = new AtomicInteger(3);
    }

    /**
     * Возвращает список всех инцидентов.
     *
     * @return список всех задач
     */
    public List<Accident> findAll() {
        return accidents.values().stream().toList();
    }

    /**
     * Выполняет добавление или обновление инцидента в хранилище.
     * Возвращает инцидент с проинициализированным идентификатором.
     *
     * @param accident инцидент
     * @return Optional.ofNullable() с сохраненным объектом accident
     */
    public Optional<Accident> create(Accident accident) {
        int id = idx.incrementAndGet();
        accident.setId(id);
        accidents.put(id, accident);
        return Optional.ofNullable(accidents.get(id));
    }

    /**
     * Выполняет обновление инцидента.
     *
     * @param accident инцидент
     * @return Optional.ofNullable() с обновленным объектом accident
     */
    public Optional<Accident> update(Accident accident) {
        accidents.put(accident.getId(), accident);
        return Optional.ofNullable(accidents.get(accident.getId()));
    }

    /**
     * Выполняет поиск инцидента по идентификатору.
     * Возвращает Optional, может содержать null,
     * если инцидент не найден.
     *
     * @param id идентификатор инцидента
     * @return Optional.ofNullable() с объектом accident
     */
    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.get(id));
    }

    /**
     * Возвращает список всех типов инцидентов.
     *
     * @return список всех типов инцидентов
     */
    public List<AccidentType> findAllAccidentTypes() {
        return types;
    }

    /**
     * Возвращает список всех статей инцидентов.
     *
     * @return список всех статей инцидентов
     */
    public List<Rule> findAllAccidentRules() {
        return rules;
    }

    /**
     * Возвращает тип инцидента по идентификатору.
     * Возвращает Optional, может содержать null,
     * если тип не найден.
     *
     * @param id идентификатор типа инцидента
     * @return  Optional.ofNullable() с объектом accidentType
     */
    public Optional<AccidentType> findTypeById(int id) {
        return types.stream().filter(type -> type.getId() == id).findFirst();
    }

    /**
     * Возвращает правило по идентификатору.
     *
     * @param id идентификатор правила
     * @return правило
     */
    public Optional<Rule> findRuleById(int id) {
       return rules.stream().filter(type -> type.getId() == id).findFirst();
    }
}
