package ru.inno.task.model;

import lombok.Getter;
import lombok.Setter;
// Класс, описывающий структуру для работы с данными, считываемыми из файлов
// доступен для всех компонент для обмена данными
@Getter
@Setter
public class Model {
    private String fileName;
    //  Логин
    private String username;
    // ФИО
    private String fio;
    // Дата входа
    private String dateInput ;
    //Тип приложения
    private String applType;

    @Override
    public String toString() {
        return "Model= " +
                "filename= " + fileName +
                "username=" + username +
                ", fio=" + fio +
                ", dateInput=" + dateInput +
                ", applType=" + applType + "\n";
    }
}