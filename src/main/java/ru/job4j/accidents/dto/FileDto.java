package ru.job4j.accidents.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FileDto {

    private String name;

    private byte[] content;
}
