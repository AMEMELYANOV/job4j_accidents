package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Сервис по работе с инцидентами
 * @see ru.job4j.accidents.model.Accident
 * @author Alexander Emelyanov
 * @version 1.0
 */
public interface AccidentService {

    /**
     * Возвращает список всех инцидентов.
     *
     * @return список всех инцидентов
     */
    List<Accident> findAll();

    /**
     * Выполняет сохранение инцидента. При успешном сохранении возвращает
     * сохраненный инцидент, иначе выбрасывается исключение.
     *
     * @param accident сохраняемый инцидент
     * @return инцидент при успешном сохранении
     * @exception IllegalArgumentException если сохранение инцидента не произошло
     */
    Accident create(Accident accident);

    /**
     * Выполняет обновление инцидента.
     *
     * @param accident обновляемый инцидент
     * @return инцидент при успешном обновлении
     * @exception NoSuchElementException если инцидент не найден
     */
    Accident update(Accident accident);

    /**
     * Выполняет поиск инцидента по идентификатору. При успешном нахождении возвращает
     * инцидент, иначе выбрасывает исключение.
     *
     * @param id идентификатор инцидента
     * @return инцидент при успешном нахождении
     * @exception NoSuchElementException если инцидент не найден
     */
    Accident findById(int id);
}
