package ru.inno.task.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.inno.task.model.Logins;
import ru.inno.task.model.Model;
import ru.inno.task.model.Users;
import ru.inno.task.repo.LoginsRep;
import ru.inno.task.repo.UsersRep;
//import ru.inno.task.service.DataWriteable;
import java.util.List;

@Component
public class DataWriter implements DataWriteable {
    private final UsersRep usersRep;
    private final LoginsRep loginsRep;

    @Autowired
    public DataWriter(UsersRep usersRep, LoginsRep loginsRep) {
        this.usersRep = usersRep;
        this.loginsRep = loginsRep;
    }

    @Override
    @Transactional
    public void writeDb(List<Model> mods) {
        System.out.println("Зашли в writeDb");

        // Отсортируем, чтобы можно было проще обработать при проходе
        mods.sort((o1, o2) -> (o1.getUsername() + o2.getFio()).compareTo(o2.getUsername() + o2.getFio()));
        String keyCmp = ""; //  для сравнения в цикле
        Users usr = new Users();

        // Пройдёмся по отсортированному множеству данных
        for (Model m : mods) {
            if (!keyCmp.equals(m.getUsername() + m.getFio())) {
                keyCmp = m.getUsername() + m.getFio();
                System.out.println(" Записываем данные user");
                usr = new Users();
                usr.setUsername(m.getUsername());
                usr.setFio(m.getFio());
                usersRep.save(usr);
                System.out.println("  -> " + m.getUsername() + " " + m.getFio());
            }
            System.out.println(" Записываем данные login");
            Logins log = new Logins();
            log.setApplication(m.getApplType());
            log.setAccessDate(m.getDateInput());
            log.setUsers(usr);
            loginsRep.save(log);
            System.out.println("  -> " + m.getApplType() + m.getDateInput());
        }
    }
}