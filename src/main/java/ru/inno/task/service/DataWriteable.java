package ru.inno.task.service;

import ru.inno.task.model.Model;
import java.util.List;

public interface DataWriteable {
    void writeDb(List<Model> mods);
}