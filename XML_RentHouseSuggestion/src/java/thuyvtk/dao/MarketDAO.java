/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuyvtk.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import thuyvtk.jaxbObject.MarketItem;
import thuyvtk.utilities.DBConnection;

/**
 *
 * @author ASUS
 */
public class MarketDAO implements Serializable{
    public boolean insertHouse(MarketItem dto) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBConnection.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO tblMarket(latitude,longitude) VALUES(?,?)";
                stm = con.prepareStatement(sql);
                stm.setFloat(1, Float.valueOf(dto.getLatitude()));
                stm.setFloat(2, Float.valueOf(dto.getLongitude()));
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
    
    public MarketItem isMarketExisted(MarketItem dto) throws ClassNotFoundException, SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.makeConnection();
            if (con != null) {
                String sql = "Select latitude,longitude from tblMarket where latitude = ? and longitude = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, dto.getLatitude());
                stm.setString(2, dto.getLongitude());
                rs = stm.executeQuery();
                if (rs.next()) {
                    MarketItem marketItem = new MarketItem();
                    marketItem.setLatitude(rs.getFloat("latitude")+"");
                    marketItem.setLongitude(rs.getFloat("longitude")+"");
                    return marketItem;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }
    
}
