package ru.inno.task.service;

import ru.inno.task.model.Model;
import java.io.IOException;
import java.util.List;

public interface DataCheckDateable {
    List<Model> checkDateL(List<Model> mods) throws IOException;
}