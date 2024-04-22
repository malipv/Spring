package ru.inno.task.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

// Соотвествует таблице users в базе postgres
@Data
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String fio;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private List<Logins> logins = new ArrayList<>();
    public void addLogins(Logins login) {
        this.logins.add(login);
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", username='" + username +
                ", fio='" + fio +
                //", " + logins +
                "}";
    }
}