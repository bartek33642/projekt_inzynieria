package com.example.maxsatApi.data;

import com.example.maxsatApi.extensions.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.sql.*;

public class DatabaseGenerator {
    String DB_URL = "jdbc:sqlite:" + System.getProperty("user.dir") + "\\data.db";
    String SQL_SCRIPT_PATH = System.getProperty("user.dir") + "\\create_tables.sql";
    String ZONE_TABLE_NAME = "Zones";
    String PARKING_LOT_TABLE_NAME = "ParkingLot";
    String ZONE_ID_COLUMN_NAME = "zoneId";
    String CITY_COLUMN_NAME = "city";
    String CORD_X_COLUMN_NAME = "cordX";
    String CORD_Y_COLUMN_NAME = "cordY";
    String FREE_LOTS_FACTOR_COLUMN_NAME = "freeLotsFactor";
    String ATTRACTIVENESS_FACTOR_COLUMN_NAME = "attractivenessFactor";
    String VARCHAR_TYPE_NAME = "varchar(255)";
    String INTEGER_TYPE_NAME = "varchar(255)";
    String DOUBLE_TYPE_NAME = "varchar(255)";
    public void createDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void createTables() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                ScriptRunner runner = new ScriptRunner(conn, true, true);
                runner.runScript(new BufferedReader(new FileReader(SQL_SCRIPT_PATH)));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
