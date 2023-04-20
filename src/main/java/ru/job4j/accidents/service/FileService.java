package ru.job4j.accidents.service;

import ru.job4j.accidents.dto.FileDto;
import ru.job4j.accidents.model.File;

import java.util.Optional;

public interface FileService {

    File save(FileDto fileDto);

    Optional<FileDto> getFileById(int id);

    boolean deleteById(int id);

}