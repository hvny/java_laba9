package org.example;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TableCreator {

    private static final String URL = "jdbc:postgresql://localhost:5432/java_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "8631";

    public static void createTable(Class<?> clazz) {
        Table table = clazz.getAnnotation(Table.class);
        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS ");           //создание sql-запроса
        sql.append(table.title()).append(" (");

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                sql.append(column.name()).append(" ").append(column.type());

                if (column.unique()) {
                    sql.append(" UNIQUE");
                }
                sql.append(", ");
            }
        }

        sql.setLength(sql.length() - 2);
        sql.append(");");

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql.toString());
            System.out.println("Таблица " + table.title() + " успешно создана или уже существует.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}