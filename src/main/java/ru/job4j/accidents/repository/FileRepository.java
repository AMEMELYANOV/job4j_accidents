package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.File;

/**
 * Хранилище файлов фотографий
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.accidents.model.Authority
 */
@Repository
public interface FileRepository extends CrudRepository<File, Integer> {
}
