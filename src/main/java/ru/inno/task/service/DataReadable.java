package ru.inno.task.service;

import ru.inno.task.model.Model;
import java.io.IOException;
import java.util.List;

public interface DataReadable {
    //  Получить данные для записи в БД
    List<Model> readFromFiles(String strPath) throws IOException;
}