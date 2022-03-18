/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sendreporting.conec;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Caitson Canpintam <caitson.canpintam@orange-sonatel.com>
 */
public class Conect {

    static String url = "jdbc:mysql://10.210.102.139:3306/barkafon?verifyServerCertificate=false&useSSL=false&requireSSL=false";
    static String usuario = "user_barkafon";
    static String senha = "BarkafonWs2021$$";

    //10.210.102.139:3306
//    pring.datasource.url=jdbc:mysql://10.210.102.139:3306/orangemobile
//spring.datasource.jdbc-url=jdbc:mysql://10.210.102.139:3306/orangemobile
//spring.datasource.username=omy_tango
//spring.datasource.password=TangoWs1010$$
    static Connection con = null;

    public static Connection getConexao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e);
        }
        return con;
    }

}
