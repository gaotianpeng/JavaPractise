package com.practise.oracle;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.sql.Connection;
import java.util.Iterator;


public class ObjPrivsDDL {
    private static final char empty = ' ';
    private static final char semicolon = ';';
    private static final char dot = '.';


    private static final String on = "on";
    private static final String to = "to";

    static public String GenerateGrantSQL(String fromUser, String obj, HashSet<String> privs,
                                          String toUser) {
        /*
            grant SELECT,CREATE,UPDATE,DROP,INSERT,ALTER,TRUNCATE,DELETE,EXECUTOR,EXPORT,CREATE TABLE,CREATE VIEW on TEST_USER.* to TEST_USER;
         */
        StringBuilder sb = new StringBuilder();
        sb.append("grant");
        sb.append(empty);
        for (Iterator<String> iterator = privs.iterator(); iterator.hasNext(); ) {
            sb.append(iterator);
            sb.append(semicolon);
        }

        sb.deleteCharAt(sb.length() - 1);
        sb.append(empty);
        sb.append("on");
        sb.append(empty);
        sb.append(fromUser);
        sb.append(dot);
        sb.append(obj);
        sb.append(empty);
        sb.append(to);
        sb.append(toUser);
        sb.append(semicolon);

        return sb.toString();
    }

    static public String GenerateRevokeSQL(String fromUser, String obj, HashSet<String> privs,
                                          String toUser) {
        /*
            revoke SELECT,CREATE,UPDATE,DROP,INSERT,ALTER,TRUNCATE,DELETE,EXECUTOR,EXPORT,CREATE TABLE, CREATE VIEW on TEST_USER.* to TEST_USER;
         */
        StringBuilder sb = new StringBuilder();
        sb.append("grant");
        sb.append(empty);
        for (Iterator<String> iterator = privs.iterator(); iterator.hasNext(); ) {
            sb.append(iterator);
            sb.append(semicolon);
        }

        sb.deleteCharAt(sb.length() - 1);
        sb.append(empty);
        sb.append("on");
        sb.append(empty);
        sb.append(fromUser);
        sb.append(dot);
        sb.append(obj);
        sb.append(empty);
        sb.append(to);
        sb.append(toUser);
        sb.append(semicolon);

        return sb.toString();
    }


    public boolean GrantObjPrivs(Connection conn, String fromUser, String toUser, HashSet<String> privs,
                              String obj) {
        Statement stmt = null;
        try {
            String sql = GenerateGrantSQL(fromUser, obj, privs, toUser);
            stmt = conn.createStatement();
            stmt.execute(sql);
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

    public void RevokeObjPrivs(String fromUser, String toUser, HashSet<String> privs) {
    }
}
