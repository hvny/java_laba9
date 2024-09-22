package org.example;

import org.example.annotations.Column;
import org.example.annotations.Table;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DataInserter {

    public static void insertObject(Object obj) {
        Class<?> clazz = obj.getClass();
        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = clazz.getAnnotation(Table.class);
            StringBuilder insertQuery = new StringBuilder("INSERT INTO " + table.title() + " (");

            StringBuilder valuesPlaceholder = new StringBuilder("VALUES (");

            Field[] fields = clazz.getDeclaredFields();
            boolean idAutoIncrement = false;

            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class)) {
                    Column column = field.getAnnotation(Column.class);

                    // Пропускаем поле id, если оно AUTOINCREMENT
                    if (column.type().contains("AUTOINCREMENT")) {
                        idAutoIncrement = true;
                        continue;
                    }

                    insertQuery.append(column.name()).append(", ");
                    valuesPlaceholder.append("?, ");
                }
            }

            // Удаляем последнюю запятую и пробел
            insertQuery.setLength(insertQuery.length() - 2);
            valuesPlaceholder.setLength(valuesPlaceholder.length() - 2);

            insertQuery.append(") ").append(valuesPlaceholder).append(");");

            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:lab9/db/animals.db");
                 PreparedStatement pstmt = conn.prepareStatement(insertQuery.toString())) {

                int parameterIndex = 1;
                for (Field field : fields) {
                    if (field.isAnnotationPresent(Column.class)) {
                        Column column = field.getAnnotation(Column.class);

                        // Пропускаем поле id, если оно AUTOINCREMENT
                        if (column.type().contains("AUTOINCREMENT")) {
                            continue;
                        }

                        field.setAccessible(true);
                        pstmt.setObject(parameterIndex++, field.get(obj));
                    }
                }

                pstmt.executeUpdate();
                System.out.println("Объект " + obj.toString() + " добавлен в таблицу: " + table.title());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
