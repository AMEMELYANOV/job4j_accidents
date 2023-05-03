package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Rule;

/**
 * Хранилище правил инцидентов
 *
 * @see ru.job4j.accidents.model.Rule
 * @author Alexander Emelyanov
 * @version 1.0
 */
public interface RuleRepository extends CrudRepository<Rule, Integer> {
}
