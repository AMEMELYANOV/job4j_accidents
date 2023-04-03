package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentRepository;
import ru.job4j.accidents.repository.AccidentTypeRepository;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Реализация сервиса по работе с инцидентами
 * @author Alexander Emelyanov
 * @version 1.0
 */
@AllArgsConstructor
@Service
public class ImplAccidentJpaService implements AccidentService {

    /**
     * Объект для доступа к методам AccidentRepository
     */
    private final AccidentRepository accidentRepository;

    /**
     * Объект для доступа к методам AccidentTypeRepository
     */
    private final AccidentTypeRepository accidentTypeRepository;

    /**
     * Объект для доступа к методам RuleRepository
     */
    private final RuleRepository ruleRepository;

    /**
     * Возвращает список всех инцидентов.
     *
     * @return список всех инцидентов
     */
    @Override
    public List<Accident> findAll() {
        List<Accident> result = new ArrayList<>();
        accidentRepository.findAll().forEach(result::add);
        return result.stream()
                .sorted(Comparator.comparing(Accident::getId))
                .collect(Collectors.toList());
    }

    /**
     * Выполняет сохранение инцидента. При успешном сохранении возвращает
     * сохраненный инцидент, иначе выбрасывается исключение.
     *
     * @param accident сохраняемый инцидент
     * @return инцидент при успешном сохранении
     */
    @Override
    public Accident create(Accident accident) {
        return accidentRepository.save(accident);
    }

    /**
     * Выполняет обновление инцидента.
     *
     * @param accident обновляемый инцидент
     * @return инцидент при успешном обновлении
     */
    @Override
    public Accident update(Accident accident) {
        return accidentRepository.save(accident);
    }

    /**
     * Выполняет выбор методов класса для сохранения или обновления инцидента.
     *
     * @param accident сохраняемый инцидент
     * @param ids массив идентификаторов статей
     * @return инцидент при успешном сохранении или обновлении
     */
    @Override
    public Accident createOrUpdateAccident(Accident accident, String[] ids) {
        int typeId = accident.getType().getId();
        AccidentType type = accidentTypeRepository.findById(typeId).orElseThrow(
                () -> new NoSuchElementException(String.format(
                        "Тип нарушения с id = %d не найдена", typeId))
        );
        Set<Rule> rules = findRulesByIds(ids);
        accident.setType(type);
        accident.setRules(rules);
        if (accident.getId() == 0) {
            accident = create(accident);
        } else {
            accident = update(accident);
        }
        return accident;
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

    /**
     * Возвращает список всех типов инцидентов.
     *
     * @return список всех типов инцидентов
     */
    @Override
    public List<AccidentType> findAllAccidentTypes() {
        List<AccidentType> result = new ArrayList<>();
        accidentTypeRepository.findAll().forEach(result::add);
        return result;
    }

    /**
     * Возвращает список всех статей инцидентов.
     *
     * @return список всех статей инцидентов
     */
    @Override
    public List<Rule> findAllAccidentRules() {
        List<Rule> result = new ArrayList<>();
        ruleRepository.findAll().forEach(result::add);
        return result;
    }

    /**
     * Возвращает правило по идентификатору.
     *
     * @param id идентификатор правила
     * @return правило
     */
    @Override
    public Rule findRuleById(int id) {
        return ruleRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException(
                        String.format("Правило c id = %d не найдена", id))
        );
    }

    /**
     * Возвращает список всех правил инцидента
     * по списку идентификаторов.
     *
     * @param ids список идентификаторов инцидента
     * @return список всех статей инцидента
     */
    @Override
    public Set<Rule> findRulesByIds(String[] ids) {
        return Arrays.stream(ids).map(Integer::parseInt)
                .map(this::findRuleById).collect(Collectors.toSet());
    }
}
