package ru.job4j.accidents.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.dto.FileDto;
import ru.job4j.accidents.model.File;
import ru.job4j.accidents.repository.FileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

/**
 * Реализация сервиса по работе с инцидентами
 *
 * @see ru.job4j.accidents.service.FileService
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Service
public class ImplFileService implements FileService {

    /**
     * Объект для доступа к методам FileRepository
     */
    private final FileRepository fileRepository;

    /**
     * Директория сохранения файлов
     */
    private final String storageDirectory;

    /**
     * Конструктор
     * @param fileRepository репозиторий файлов
     * @param storageDirectory директория для хранения файлов
     */
    public ImplFileService(FileRepository fileRepository,
                           @Value("${file.directory}") String storageDirectory) {
        this.fileRepository = fileRepository;
        this.storageDirectory = storageDirectory;
        createStorageDirectory(storageDirectory);
    }

    /**
     * Выполняет создание директории для сохранения файлов
     * в соответствии с полученным путем.
     *
     * @param path путь
     */
    private void createStorageDirectory(String path) {
        try {
            Files.createDirectories(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Выполняет сохранение файла на основании пути dto объекта,
     * так же сохраняет объект File в репозитории файлов.
     *
     * @param fileDto dto объект файла
     * @return сохраненный файл
     */
    @Override
    public File save(FileDto fileDto) {
        String path = getNewFilePath(fileDto.getName());
        writeFileBytes(path, fileDto.getContent());
        return fileRepository.save(new File(fileDto.getName(), path));
    }

    /**
     * Создает уникальное имя файла с учетом директории сохранения файлов.
     *
     * @param sourceName имя файла
     * @return уникальное имя файла
     */
    private String getNewFilePath(String sourceName) {
        return storageDirectory + java.io.File.separator + UUID.randomUUID() + sourceName;
    }

    /**
     * Записывает байтовое представление в директорию сохранения файлов.
     *
     * @param path путь файла для записи
     * @param content байтовый массив данных файла
     */
    private void writeFileBytes(String path, byte[] content) {
        try {
            Files.write(Path.of(path), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Выполняет поиск файла в репозитории по идентификатору,
     * если файл в репозитории не найден, то возвращается Optional.empty(),
     * иначе возвращает объект dto обернутый в Optional и содержащий байтовое
     * представление файла.
     *
     * @param id идентификатор
     * @return объект dto файла обернутый в Optional
     */
    @Override
    public Optional<FileDto> getFileById(int id) {
        Optional<File> fileOptional = fileRepository.findById(id);
        if (fileOptional.isEmpty()) {
            return Optional.empty();
        }
        byte[] content = readFileAsBytes(fileOptional.get().getPath());
        return Optional.of(new FileDto(fileOptional.get().getName(), content));
    }

    /**
     * Читает файл с диска по переданному пути к файлу.
     *
     * @param path идентификатор
     * @return массив байт с данными файла
     */
    private byte[] readFileAsBytes(String path) {
        try {
            return Files.readAllBytes(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Выполняет удаление файла в репозитории по идентификатору,
     * если файл в репозитории не найден, то возвращается false,
     * иначе выполняется удаление файла из репозитория и из директории
     * хранения файлов, после этого методом возвращается true.
     *
     * @param id идентификатор
     * @return true или false в зависимости от результата работы
     */
    @Override
    public boolean deleteById(int id) {
        Optional<File> fileOptional = fileRepository.findById(id);
        if (fileOptional.isEmpty()) {
            return false;
        }
        deleteFile(fileOptional.get().getPath());
        fileRepository.deleteById(id);
        return true;
    }

    /**
     * Выполняет удаление файла по указанному пути.
     *
     * @param path путь к файлу
     */
    private void deleteFile(String path) {
        try {
            Files.deleteIfExists(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
