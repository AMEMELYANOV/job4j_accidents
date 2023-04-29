package ru.job4j.accidents.service;

import ru.job4j.accidents.dto.FileDto;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Сервис по работе с инцидентами
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.accidents.model.Accident
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
     * сохраненный инцидент.
     *
     * @param accident сохраняемый инцидент
     * @param image    объект dto файла фотографии
     * @return инцидент при успешном сохранении
     */
    Accident create(Accident accident, FileDto image);

    /**
     * Выполняет обновление инцидента.
     *
     * @param accident обновляемый инцидент
     * @param image    объект dto файла фотографии
     * @return инцидент при успешном обновлении
     */
    Accident update(Accident accident, FileDto image);

    /**
     * Выполняет выбор методов класса для сохранения или обновления инцидента.
     *
     * @param accident сохраняемый инцидент
     * @param ids      массив идентификаторов статей
     * @param image    объект dto файла фотографии
     * @return инцидент при успешном сохранении или обновлении
     * @throws NoSuchElementException если тип инцидента не найден в репозитории
     */
    Accident createOrUpdateAccident(Accident accident, String[] ids, FileDto image);

    /**
     * Выполняет поиск инцидента по идентификатору. При успешном нахождении возвращает
     * инцидент, иначе выбрасывает исключение.
     *
     * @param id идентификатор инцидента
     * @return инцидент при успешном нахождении
     * @throws NoSuchElementException если инцидент не найден
     */
    Accident findById(int id);

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
     * Возвращает правило по идентификатору.
     *
     * @param id идентификатор правила
     * @return правило
     */
    Rule findRuleById(int id);

    /**
     * Возвращает список всех статей инцидента
     * по списку идентификаторов.
     *
     * @param ids список идентификаторов инцидента
     * @return список всех статей инцидента
     */
    Set<Rule> findRulesByIds(String[] ids);

    /**
     * Выполняет удаление инцидента по идентификатору.
     *
     * @param id идентификатор задачи
     */
    void deleteById(int id);

    /**
     * Выполняет обновление статуса инцидента.
     *
     * @param accident идентификатор задачи
     */
    void updateStatus(Accident accident);
}
