package ru.inno.task.service;

import org.springframework.stereotype.Component;
import ru.inno.task.model.Model;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataCheckFio implements DataCheckFioable{
    // Проверяем ФИО
    public String checkFio(String fio) {
        // В ФИО может быть несколько пробелов
        String[] strArr = fio.split(" ");
        // Итоговая строка
        StringBuilder strRes = new StringBuilder();
        for (String s : strArr) {
            String strAdd = s.trim();
            // Делаем первую букву заглавной
            if (!strAdd.isEmpty())
                strRes.append(" ").append(strAdd.substring(0, 1).toUpperCase()).append(strAdd.substring(1).toLowerCase());
        }
        // удаляем добавленный первый пробел
        return strRes.toString().trim();
    }

    // Проверяем ФИО  (компонента логируется )
    @LogTransformation("LogComponentReader.log")
    @Override
    public List<Model> checkFioL(List<Model> mods) {
        //System.out.println("Зашли в checkFioL");
        mods.stream().peek(x-> x.setFio(checkFio(x.getFio()))).collect(Collectors.toList());
        return mods;
    }
}