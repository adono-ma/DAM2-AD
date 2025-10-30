/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IO.DAO.JDBC;

import java.sql.Connection;

/**
 *
 * @author ixchel
 */
public class DAOJDBCCircuito {
    private Connection con = null;
    
    public DAOJDBCCircuito(Connection con) {
        this.con = con;
    }
    
}
