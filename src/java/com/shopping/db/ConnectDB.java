/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shopping.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ConnectDB {
    
    Connection conn = null;
    Statement stmt = null;
    Statement stmt1 = null;
    PreparedStatement pstmt = null;
    PreparedStatement pstmt1 = null;
    public String dbConnect() throws ClassNotFoundException, SQLException {
        String retVal;
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/XE", "scott", "pa$$word");
            retVal="OK";
        }catch (ClassNotFoundException | SQLException e) {
            retVal="error" + e.toString();       
        }
        return retVal;
    }
    
    public void createStatement() throws SQLException {
        this.stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
    }
    public Statement getStmt() {
        return stmt;
    }
    
    public void createStatement1() throws SQLException {
        this.stmt1 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);;
    }
    public Statement getStmt1() {
        return stmt1;
    }

    public PreparedStatement getPrepStmt(String sSql) throws SQLException {
        pstmt = conn.prepareStatement(sSql);
        return pstmt;
    }
    public PreparedStatement getPrepStmt1(String sSql) throws SQLException {
        pstmt1 = conn.prepareStatement(sSql);
        return pstmt1;
    }
    public void setAutoCommit(boolean b) throws SQLException {
        conn.setAutoCommit(b);
    }
    public void commit() throws SQLException {
        conn.commit();
    }

    public void rollback() throws SQLException {
        conn.rollback();
    }

    public int connectionClose() throws SQLException {
        if (conn != null) {
            conn.close();
        }
        if (stmt != null) {
            stmt.close();
        }
        if (pstmt != null) {
            pstmt.close();
        }
        return 1;
    }
}
