package ru.andreycherenkov.digitasignature.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Document {

    private String data;
    private String signature;

}
