package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Реализация хранилища инцидентов в памяти
 * с использованием ConcurrentHashMap
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.accidents.repository.AccidentRepository
 */
@Repository
public class MemoryAccidentRepository implements AccidentRepository {

    /**
     * Хранилище инцидентов
     */
    Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    /**
     * Счетчик инцидентов
     */
    AtomicInteger idx;

    /**
     * Список типов инцидентов
     */
    List<AccidentType> types = new ArrayList<>();

    /**
     * Конструктор, инициализирует начальное состояние хранилища
     */
    public MemoryAccidentRepository() {
        types.add(new AccidentType(1, "Две машины"));
        types.add(new AccidentType(2, "Машина и человек"));
        types.add(new AccidentType(3, "Машина и велосипед"));
        accidents.put(1, Accident.builder().id(1).name("name1").address("address1").text("text1")
                .type(types.get(0)).build());
        accidents.put(2, Accident.builder().id(2).name("name2").address("address2").text("text2")
                .type(types.get(1)).build());
        accidents.put(3, Accident.builder().id(3).name("name3").address("address3").text("text3")
                .type(types.get(2)).build());
        idx = new AtomicInteger(3);
    }

    /**
     * Возвращает список всех инцидентов.
     *
     * @return список всех задач
     */
    @Override
    public List<Accident> findAll() {
        return accidents.values().stream().toList();
    }

    /**
     * Выполняет добавление инцидента в хранилище.
     * Возвращает инцидент с проинициализированным идентификатором.
     *
     * @param accident инцидент
     * @return Optional.ofNullable() с сохраненным объектом accident
     */
    @Override
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
    @Override
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
    @Override
    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.get(id));
    }

    /**
     * Возвращает список всех типов инцидентов.
     *
     * @return список всех типов инцидентов
     */
    @Override
    public List<AccidentType> findAllAccidentTypes() {
        return types;
    }
}
