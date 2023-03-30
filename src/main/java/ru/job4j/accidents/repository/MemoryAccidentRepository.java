package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Реализация хранилища инцидентов в памяти
 * с использованием ConcurrentHashMap
 * @see ru.job4j.accidents.repository.AccidentRepository
 * @author Alexander Emelyanov
 * @version 1.0
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
     * Конструктор, инициализирует начальное состояние хранилища
     */
    public MemoryAccidentRepository() {
        accidents.put(1, Accident.builder().id(1).name("name1").address("address1").text("text1").build());
        accidents.put(2, Accident.builder().id(2).name("name2").address("address2").text("text2").build());
        accidents.put(3, Accident.builder().id(3).name("name3").address("address3").text("text3").build());
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
}
