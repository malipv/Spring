package ru.inno.task.service;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class BeanPostProcessorLog implements BeanPostProcessor {
    private Map<String, Object> beans  = new HashMap<>();
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (Arrays.stream(bean.getClass().getDeclaredMethods()).anyMatch(m->m.isAnnotationPresent(LogTransformation.class)))
            beans.put(beanName, bean);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(beans.containsKey(beanName)) {
            beans.remove(beanName);
            ProxyFactory proxyFactory = new ProxyFactory(bean);
            proxyFactory.setProxyTargetClass(true);
            proxyFactory.addAdvice(new LoggingInterceptor());
            return proxyFactory.getProxy();
        }
        return bean;
    }

    private  class LoggingInterceptor implements MethodInterceptor {
        private <T> T getObjectByType(Object obj, Class <T> cl){
            return (T) obj;
        }

        @Override
        public Object invoke(MethodInvocation methodInvocation) throws Throwable {
            Method meth = methodInvocation.getMethod();
            boolean isAnnotationPresent = meth.isAnnotationPresent(LogTransformation.class);
            List<String> outStr = new ArrayList<>();
            if (isAnnotationPresent) {
                // Определим аргументы перехватываемого метода для логирования
                String nameMeth = methodInvocation.getMethod().getName();
                outStr.add("<<Метод " + nameMeth + " >> : " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
                outStr.add("Аргументы:");
                outStr.add(Arrays.stream(methodInvocation.getArguments()).toList().toString());
            }
            // Вызов метода
            Object returnValue = methodInvocation.proceed();

            // Начитываем результат и сохраняем в файл
            // Попробовала сохранить через логирование @Slf4j с указанием  пути в fpplication.properties
            // Не стала  так делать, т.к. много лищней инфы сохранялось
            if (meth.isAnnotationPresent(LogTransformation.class)){
                // Из аннотации имя файла
                String fileName = meth.getDeclaredAnnotation(LogTransformation.class).value();
                //System.out.println("fileName = " + fileName);

                // Строки логирования
                outStr.add("Результат выполнения:");
                assert getObjectByType(returnValue, meth.getReturnType()) != null;
                outStr.add(getObjectByType(returnValue, meth.getReturnType()).toString());
                Path textFile = Paths.get(fileName);
                //System.out.println("textFile = " + textFile);
                Files.write(textFile, outStr, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                System.out.println("outStr = " + outStr);
            }
            return returnValue;
        }
    }
}