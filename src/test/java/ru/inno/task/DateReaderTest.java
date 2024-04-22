package ru.inno.task;

import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.inno.task.model.Model;
import ru.inno.task.service.DataCheckDate;
import ru.inno.task.service.DataCheckFio;
import ru.inno.task.service.DataCheckType;
import ru.inno.task.service.DataReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DateReaderTest {

    @BeforeEach
    void setup() {}

    @Test
    @Description("Тестирование перечня файлов в папках")
    void readFiles() throws IOException {
        System.out.println("Тестирование перечня файлов в папках");
        DataReader dataReader = new DataReader();
        List<Model> models = dataReader.readFiles();
        Assertions.assertEquals(7, models.size());  // ожидаемое число записей во всех файлах = 7
    }

    @Test
    @Description("Тестирование ФИО")
    void DataCheckFio() throws IOException {
        System.out.println("Тестирование ФИО");
        DataCheckFio dataCheck = new DataCheckFio();
        Assertions.assertEquals("Иванов Иван Иванович", dataCheck.checkFio("иванов    иван    иванович"));
    }

    @Test
    @Description("Тестирование типа")
    void DataCheckType() throws IOException {
        System.out.println("Тестирование типа");
        DataCheckType dataCheck = new DataCheckType();
        Assertions.assertEquals("mobile", dataCheck.checkType("mobile"));
        Assertions.assertEquals("web", dataCheck.checkType("web"));
        Assertions.assertEquals("other:something", dataCheck.checkType("something"));
        Assertions.assertEquals("other:", dataCheck.checkType(null));
    }

    @Test
    @Description("Тестирование преобразования даты")
    void DataCheckDate() throws IOException {
        System.out.println("Тестирование преобразования даты");
        DataCheckDate dataCheck = new DataCheckDate();
        LocalDateTime date = dataCheck.checkDate("07.04.2023 12:00:00");
        System.out.println("LocalDateTime date = " + date);
        Assertions.assertEquals(12, date.getHour());
        Assertions.assertEquals(0, date.getMinute());
        Assertions.assertEquals(0, date.getSecond());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        Assertions.assertEquals("07.04.2023", date.format(dateTimeFormatter));

        date = dataCheck.checkDate("07.04.23 12:00:00");
        Assertions.assertEquals(null, date);
    }

    @Test
    @Description("Тестирование проверки файлов")
    void checkFiles() throws IOException {
        DataReader dataReader = new DataReader();
        List<Model> models = dataReader.readFiles();
        DataCheckFio dataCheckFio = new DataCheckFio();
        DataCheckType dataCheckType = new DataCheckType();
        DataCheckDate dataCheckDate = new DataCheckDate();

        // Проверяем  компоненту проверки  ФИО
        models = dataCheckFio.checkFioL(models);
        for (Model md : models) {
            Assertions.assertEquals(md.getFio(), dataCheckFio.checkFio(md.getFio()));
        }

        // Проверяем  компоненту проверки  типа
        models = dataCheckType.checkTypeL(models);

        for (Model md : models) {
            Assertions.assertEquals(md.getApplType().replace("other:", "")
                    , dataCheckType.checkType(md.getApplType()).replace("other:", ""));
        }

        //  Проверяем  компоненту проверки  даты (с пустой записью не должно быть)
        models = dataCheckDate.checkDateL(models);
        Assertions.assertEquals(6, models.size());
    }
}