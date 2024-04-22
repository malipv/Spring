package ru.inno.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.inno.task.service.DataMaker;

import java.io.IOException;

@SpringBootApplication(scanBasePackages = "ru.inno.task")
public class Main {
    public static void main(String[] args) throws IOException {
        ApplicationContext ctx = SpringApplication.run(Main.class);
        DataMaker mk = ctx.getBean(DataMaker.class);
        mk.make();
    }
}