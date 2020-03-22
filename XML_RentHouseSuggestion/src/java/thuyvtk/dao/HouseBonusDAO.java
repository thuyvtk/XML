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
import thuyvtk.utilities.DBConnection;

/**
 *
 * @author ASUS
 */
public class HouseBonusDAO implements Serializable{
    public boolean insertHouseBonus(int houseId, int bonus) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBConnection.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO tblHouse_Bonus(houseId,bonusId) VALUES(?,?)";
                stm = con.prepareStatement(sql);
                stm.setInt(1, houseId);
                stm.setInt(2, bonus);
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
