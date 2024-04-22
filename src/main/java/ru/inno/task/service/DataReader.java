package ru.inno.task.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.inno.task.model.Model;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class DataReader implements DataReadable {

    @Value("${file.path}")
    private String path;

    public String getPath() {
        if (path == null)
            return new File("").getAbsolutePath();
        return path;
    }

    // Метод с указанием пути
    @LogTransformation("LogComponentReader.log")
    @Override
    public List<Model> readFromFiles(String strPath) throws IOException {
        //System.out.println("Зашли в readFromFiles");

        // Данные прочитанные из файла
        List<Model> modelList = new ArrayList<>();

        // Разделитель
        String delimeter = ";";

        // Найдём все текстовые файлы
        List<Path> filesPath = Files.find(Path.of(strPath)
                , Integer.MAX_VALUE
                , (path, attr) -> path.toString().endsWith(".txt")).toList();

        // Читаем все найденные файлы
        for (Path pathCur : filesPath)
            try (Scanner scanFile = new Scanner(pathCur, StandardCharsets.UTF_8)) {
                while (scanFile.hasNextLine()) {
                    // полученную строку разбиваем в массив
                    String[] strLineArr = scanFile.nextLine().split(delimeter);
                    Model model = new Model();
                    model.setFileName(pathCur.getFileName().toString());
                    for (int i = 0; i < strLineArr.length; i++) {
                        // Сравнение  добавлено, если строка окажется короче(не из 4 позиций)
                        // можно было без цикла вытаскивать из массива
                        if (i == 0) model.setUsername(strLineArr[i]);
                        if (i == 1) model.setFio(strLineArr[i]);
                        if (i == 2) model.setDateInput(strLineArr[i]);
                        if (i == 3) model.setApplType(strLineArr[i]);
                    }
                    modelList.add(model);
                }
            }
        return modelList;
    }

    public List<Model> readFiles() throws IOException {
        String pathRead = getPath();
        return readFromFiles(pathRead);
    }
}