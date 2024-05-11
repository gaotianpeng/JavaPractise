package com.practise.oracle;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBDDL {

    public boolean createDatabase(Connection conn, String dbName) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.execute("CREATE DATABASE " + dbName);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return true;
    }

    public boolean dropDatabase(Connection conn, String dbName) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.execute("DROP DATABASE " + dbName);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return true;
    }
}
