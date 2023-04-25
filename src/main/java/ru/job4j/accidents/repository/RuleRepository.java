package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Rule;

/**
 * Хранилище правил инцидентов
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.accidents.model.Rule
 */
public interface RuleRepository extends CrudRepository<Rule, Integer> {
}
