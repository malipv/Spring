package ru.inno.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.inno.task.model.Model;

import java.io.IOException;
import java.util.List;

@Component
public class DataMaker {
   // Строки прочитанные
   private List<Model> models;
   // Компонента чтения данных
   private final DataReader dataReader;

   private final DataWriter dataWriter;

   //Компоненты проверки данных
   private final DataCheckFio dataCheckFio;
   private final DataCheckType dataCheckType;
   private final DataCheckDate dataCheckDate;

    @Autowired
   public DataMaker( DataReader     dataReader
                    ,DataCheckFio   dataCheckFio
                    ,DataCheckType  dataCheckType
                    ,DataCheckDate  dataCheckDate
                    ,DataWriter     dateWriter
                    )
    {
        this.dataReader = dataReader;
        this.dataWriter = dateWriter;
        this.dataCheckFio = dataCheckFio;
        this.dataCheckType = dataCheckType;
        this.dataCheckDate = dataCheckDate;
    }

    public  void make() throws IOException {
        // Получим путь(по условию задачи) для загрузки файлов(его можно разными способами задавать)
        String strPath = dataReader.getPath();
        //System.out.println("strPath = " + strPath);

        // Читаем данные из файлов
        models = dataReader.readFromFiles(strPath);

        // Выполняем проверку данных
        // Проверяем ФИО(меняем на первые заглавные буквы)
        models = dataCheckFio.checkFioL(models);

        // Проверяем тип меняем на "other: ", если не web или mobile
        models = dataCheckType.checkTypeL(models);

        // Проверяем дату Если пустая, записываем в файл
        models = dataCheckDate.checkDateL(models);

        System.out.println("=========Запись в БД =========================");
        dataWriter.writeDb(models);
   }
}