/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuyvtk.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Date;
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
    
    public boolean insertHouse(HouseItem dto) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBConnection.makeConnection();
            if (con != null) {
//                latitude = dto.getLatitude().floatValue();
//                longitude = dto.getLongitude().floatValue();
                String sql = "INSERT INTO tblHouse(title,"
                        + "linkNew,"
                        + "timePost,"
                        + "img,"
                        + "rentAddress,"
                        + "size,"
                        + "electricPrice,"
                        + "waterPrice,"
                        + "bonus,"
                        + "rentPrice,"
                        + "detail,"
                        + "latitude,"
                        + "longitude,"
                        + "webId) "
                        + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, dto.getTitle());
                stm.setString(2, dto.getLinkNew());
                stm.setDate(3, Date.valueOf(dto.getTimePost()));
                stm.setString(4, dto.getImg());
                stm.setString(5, dto.getRentAddress());
                stm.setString(6, dto.getSize());
                stm.setString(7, dto.getElectricPrice());
                stm.setString(8, dto.getWaterPrice());
                stm.setString(9, dto.getBonus());
                stm.setString(10, dto.getRentPrice()+"");
                stm.setString(11, dto.getDetail());
                stm.setFloat(12, Float.valueOf(dto.getLatitude()));
                stm.setFloat(13, Float.valueOf(dto.getLongitude()));
                stm.setString(14, dto.getWebsite());
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
                String sql = "Select id,title,timePost,img,rentAddress,size,"
                        + "electricPrice,waterPrice,bonus,rentPrice,detail,latitude,longitude,webId"
                        + " from tblHouse where linkNew = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, linkNew);
                rs = stm.executeQuery();
                if (rs.next()) {
                    HouseItem houseItem = new HouseItem();
                    houseItem.setId(BigInteger.valueOf(rs.getInt("id")));
                    houseItem.setTitle(rs.getString("title"));
                    houseItem.setLinkNew(linkNew);
                    houseItem.setTimePost(rs.getDate("timePost")+"");
                    houseItem.setImg(rs.getString("img"));
                    houseItem.setRentAddress(rs.getString("rentAddress"));
                    houseItem.setSize(rs.getString("size"));
                    houseItem.setElectricPrice(rs.getString("electricPrice"));
                    houseItem.setWaterPrice(rs.getString("waterPrice"));
                    houseItem.setBonus(rs.getString("bonus"));
                    houseItem.setRentPrice(rs.getString("rentPrice"));
                    houseItem.setDetail(rs.getString("detail"));
                    houseItem.setLatitude(rs.getFloat("latitude")+"");
                    houseItem.setLongitude(rs.getFloat("longitude")+"");
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
                String sql = "UPDATE tblHouse SET title = ?,"
                        + "timePost = ?,"
                        + "img = ?,"
                        + "rentAddress = ?,"
                        + "size =?,"
                        + "electricPrice = ?,"
                        + "waterPrice = ?,"
                        + "bonus = ?,"
                        + "rentPrice = ?,"
                        + "detail = ?,"
                        + "latitude = ?,"
                        + "longitude = ? "
                        + "WHERE id = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, dto.getTitle());
                stm.setDate(2, Date.valueOf(dto.getTimePost()));
                stm.setString(3, dto.getImg());
                stm.setString(4, dto.getRentAddress());
                stm.setString(5, dto.getSize());
                stm.setString(6, dto.getElectricPrice());
                stm.setString(7 , dto.getWaterPrice());
                stm.setString(8, dto.getBonus());
                stm.setString(9, dto.getRentPrice());
                stm.setString(10, dto.getDetail());
                stm.setFloat(11, Float.valueOf(dto.getLatitude()));
                stm.setFloat(12, Float.valueOf(dto.getLongitude()));
                stm.setInt(13, Integer.valueOf(dto.getId()+""));
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
