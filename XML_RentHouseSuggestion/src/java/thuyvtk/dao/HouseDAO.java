/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuyvtk.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.naming.NamingException;
import thuyvtk.jaxbObject.HouseItem;
import thuyvtk.utilities.DBConnection;
//import thuyvtk.jaxB.HouseItem;

/**
 *
 * @author ASUS
 */
public class HouseDAO implements Serializable{
    
    public boolean insertHouse(HouseItem dto) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBConnection.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO tblHouse(title,"
                        + "linkNew,"
                        + "timePost,"
                        + "img,"
                        + "rentAddress,"
                        + "size,"
                        + "toilet,"
                        + "people,"
                        + "electricPrice,"
                        + "waterPrice,"
                        + "bonus,"
                        + "rentPrice,"
                        + "detail,"
                        + "latitude,longitude) "
                        + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, dto.getTitle());
                stm.setString(2, dto.getLinkNew());
                stm.setString(3, "");
                stm.setString(4, dto.getImg());
                stm.setString(5, dto.getRentAddress());
                stm.setString(6, dto.getSize());
                stm.setString(7, dto.getToilet());
                stm.setString(8, dto.getPeople());
                stm.setString(9, dto.getElectricPrice());
                stm.setString(10, dto.getWaterPrice());
                stm.setString(11, dto.getBonus());
                stm.setString(12, "thuy");
                stm.setString(13, dto.getDetail());
                stm.setFloat(14, 123);
                stm.setFloat(15, 123);
                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }
    
}
