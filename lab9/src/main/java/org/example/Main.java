package org.example;

public class Main {
    public static void main(String[] args) {
        TableCreator.createTable(Cat.class);

        Gender male = Gender.MALE;
        Gender female = Gender.FEMALE;

        Cat[] cats = {
                new Cat("CAT1", 2, male),
                new Cat("CAT2", 1, male),
                new Cat("CAT3", 3, female),

        };
        
        // Добавление объектов в таблицу
        for (Cat cat : cats) {
            DataInserter.insertObject(cat);
        }
    }
}