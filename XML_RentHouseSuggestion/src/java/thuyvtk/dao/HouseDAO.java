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
import thuyvtk.jaxbObject.HouseItem;
import thuyvtk.utilities.DBConnection;
//import thuyvtk.jaxB.HouseItem;

/**
 *
 * @author ASUS
 */
public class HouseDAO implements Serializable{
    
    public boolean insertHouse(HouseItem dto, int id) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBConnection.makeConnection();
            if (con != null) {
                float latitude = 0;
                float longitude = 0;
                latitude = dto.getLatitude().floatValue();
                longitude = dto.getLongitude().floatValue();
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
                stm.setString(3, dto.getTimePost());
                stm.setString(4, dto.getImg());
                stm.setString(5, dto.getRentAddress());
                stm.setString(6, dto.getSize());
                stm.setString(7, dto.getToilet());
                stm.setString(8, dto.getPeople());
                stm.setString(9, dto.getElectricPrice());
                stm.setString(10, dto.getWaterPrice());
                stm.setString(11, dto.getBonus());
                stm.setString(12, dto.getRentPrice()+"");
                stm.setString(13, dto.getDetail());
                stm.setFloat(14, latitude);
                stm.setFloat(15, longitude);
//                stm.setInt(16, id);
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
    
    public HouseItem isHomeExisted(String linkNew) throws ClassNotFoundException, SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.makeConnection();
            if (con != null) {
                String sql = "Select title,timePost,img,rentAddress,size,toilet,people,"
                        + "electricPrice,waterPrice,bonus,rentPrice,detail,latitude,longitude"
                        + " from tblHouse where linkNew = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, linkNew);
                rs = stm.executeQuery();
                if (rs.next()) {
                    HouseItem houseItem = new HouseItem();
                    houseItem.setTitle(rs.getString("title"));
                    houseItem.setLinkNew(linkNew);
                    houseItem.setTimePost(rs.getString("timePost"));
                    houseItem.setImg(rs.getString("title"));
                    houseItem.setRentAddress(rs.getString("rentAddress"));
                    houseItem.setSize(rs.getString("size"));
                    houseItem.setToilet(rs.getString("toilet"));
                    houseItem.setPeople(rs.getString("people"));
                    houseItem.setElectricPrice(rs.getString("electricPrice"));
                    houseItem.setWaterPrice(rs.getString("waterPrice"));
                    houseItem.setBonus(rs.getString("bonus"));
//                    houseItem.setRentPrice(rs.getString("rentPrice").);
                    houseItem.setElectricPrice(rs.getString("electricPrice"));
                    houseItem.setElectricPrice(rs.getString("electricPrice"));
                    houseItem.setElectricPrice(rs.getString("electricPrice"));
                    return houseItem;
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
    
    public boolean updateHouse(HouseItem dto) throws ClassNotFoundException, SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBConnection.makeConnection();
            if (con != null) {
                String sql = "UPDATE tblHouse SET title = ?"
                        + "timePost = ?,"
                        + "img = ?,"
                        + "rentAddress = ?,"
                        + "size =?,"
                        + "toilet = ?,"
                        + "people = ?,"
                        + "electricPrice = ?,"
                        + "waterPrice = ?,"
                        + "bonus = ?,"
                        + "rentPrice = ?,"
                        + "detail = ?,"
//                        + "latitude = ?,"
//                        + "longitude = ? "
                        + "WHERE id = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, dto.getTitle());
                stm.setString(1, dto.getTimePost());
                stm.setString(1, dto.getImg());
                stm.setString(1, dto.getRentAddress());
                stm.setString(1, dto.getSize());
                stm.setString(1, dto.getToilet());
                stm.setString(1, dto.getPeople());
                stm.setString(1, dto.getElectricPrice());
                stm.setString(1, dto.getWaterPrice());
                stm.setString(1, dto.getBonus());
                stm.setString(1, dto.getRentPrice()+"");
                stm.setString(1, dto.getTitle());
                stm.setString(1, dto.getTitle());
                stm.setString(1, dto.getTitle());
                stm.setString(1, dto.getTitle());
                stm.setString(1, dto.getTitle());
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
