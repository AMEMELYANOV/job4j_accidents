package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Реализация сервиса по работе с инцидентами
 * @see ru.job4j.accidents.service.AccidentService
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class ImplAccidentService implements AccidentService {

    /**
     * Объект для доступа к методам AccidentRepository
     */
    AccidentRepository accidentRepository;

    /**
     * Возвращает список всех инцидентов.
     *
     * @return список всех инцидентов
     */
    @Override
    public List<Accident> findAll() {
        return accidentRepository.findAll();
    }

    /**
     * Выполняет сохранение инцидента. При успешном сохранении возвращает
     * сохраненный инцидент, иначе выбрасывается исключение.
     *
     * @param accident сохраняемый инцидент
     * @return инцидент при успешном сохранении
     * @exception IllegalArgumentException если сохранение инцидента не произошло
     */
    @Override
    public Accident create(Accident accident) {
        Optional<Accident> accident1 = accidentRepository.create(accident);
        return accident1.orElseThrow(
                () -> new IllegalArgumentException(
                        String.format("Ошибка в сохранении инцидента с наименованием - %s",
                                accident.getName()))
        );
    }

    /**
     * Выполняет обновление инцидента.
     *
     * @param accident обновляемый инцидент
     * @return инцидент при успешном обновлении
     * @exception NoSuchElementException если инцидент не найден
     */
    @Override
    public Accident update(Accident accident) {
        return accidentRepository.update(accident).orElseThrow(
                () -> new IllegalArgumentException(
                        String.format("Ошибка в обновлении инцидента с id = %d",
                                accident.getId()))
        );
    }

    /**
     * Выполняет поиск инцидента по идентификатору. При успешном нахождении возвращает
     * инцидент, иначе выбрасывает исключение.
     *
     * @param id идентификатор инцидента
     * @return инцидент при успешном нахождении
     * @exception NoSuchElementException если инцидент не найден
     */
    @Override
    public Accident findById(int id) {
        return accidentRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException(
                        String.format("Инцидент c id = %d не найден", id))
        );
    }
}
