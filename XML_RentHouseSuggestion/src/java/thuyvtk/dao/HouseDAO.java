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
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import thuyvtk.dto.BonusDTO;
import thuyvtk.dto.HouseDTO;
import thuyvtk.jaxbObject.HouseItem;
import thuyvtk.utilities.DBConnection;
import thuyvtk.utilities.Utilites;
//import thuyvtk.jaxB.HouseItem;

/**
 *
 * @author ASUS
 */
public class HouseDAO implements Serializable {

    public List<HouseDTO> listHouses;

    public List<HouseDTO> getListHouses() {
        return listHouses;
    }
    
    Utilites helper = new Utilites();

    public int insertHouse(HouseItem dto) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO tblHouse(title,"
                        + "linkNew,"
                        + "timePost,"
                        + "img,"
                        + "rentAddress,"
                        + "size,"
                        + "electricPrice,"
                        + "waterPrice,"
                        + "rentPrice,"
                        + "detail,"
                        + "latitude,"
                        + "longitude,"
                        + "webId) "
                        + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?) "
                        + "SELECT SCOPE_IDENTITY() as ID";
                stm = con.prepareStatement(sql);
                stm.setString(1, dto.getTitle());
                stm.setString(2, dto.getLinkNew());
                stm.setDate(3, Date.valueOf(dto.getTimePost()));
                stm.setString(4, dto.getImg());
                stm.setString(5, dto.getRentAddress());
                stm.setString(6, dto.getSize());
                stm.setString(7, dto.getElectricPrice());
                stm.setString(8, dto.getWaterPrice());
                stm.setString(9, dto.getRentPrice() + "");
                stm.setString(10, dto.getDetail());
                stm.setFloat(11, Float.valueOf(dto.getLatitude()));
                stm.setFloat(12, Float.valueOf(dto.getLongitude()));
                stm.setString(13, dto.getWebsite());
                int row = stm.executeUpdate();
                if (row > 0) {
                    rs = stm.getGeneratedKeys();
                    if (rs.next()) {
                        System.out.println(rs.getInt(1));
                        return rs.getInt(1);
                    }
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
        return 0;
    }

    public HouseItem isHomeExisted(String linkNew) throws ClassNotFoundException, SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.makeConnection();
            if (con != null) {
                String sql = "Select id,title,timePost,img,rentAddress,size,"
                        + "electricPrice,waterPrice,rentPrice,detail,latitude,longitude,webId"
                        + " from tblHouse where linkNew = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, linkNew);
                rs = stm.executeQuery();
                if (rs.next()) {
                    HouseItem houseItem = new HouseItem();
                    houseItem.setId(BigInteger.valueOf(rs.getInt("id")));
                    houseItem.setTitle(rs.getString("title"));
                    houseItem.setLinkNew(linkNew);
                    houseItem.setTimePost(rs.getDate("timePost") + "");
                    houseItem.setImg(rs.getString("img"));
                    houseItem.setRentAddress(rs.getString("rentAddress"));
                    houseItem.setSize(rs.getString("size"));
                    houseItem.setElectricPrice(rs.getString("electricPrice"));
                    houseItem.setWaterPrice(rs.getString("waterPrice"));
                    houseItem.setRentPrice(rs.getString("rentPrice"));
                    houseItem.setDetail(rs.getString("detail"));
                    houseItem.setLatitude(rs.getFloat("latitude") + "");
                    houseItem.setLongitude(rs.getFloat("longitude") + "");
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
                stm.setString(7, dto.getWaterPrice());
                stm.setString(8, dto.getRentPrice());
                stm.setString(9, dto.getDetail());
                stm.setFloat(10, Float.valueOf(dto.getLatitude()));
                stm.setFloat(11, Float.valueOf(dto.getLongitude()));
                stm.setInt(12, Integer.valueOf(dto.getId() + ""));
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

    public void findHouses(String latitude, String longitude, float distance) throws ClassNotFoundException, SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.makeConnection();
            if (con != null) {
                String sql = "SELECT * FROM (select id,title,linkNew,timePost,img,rentAddress,size,electricPrice,waterPrice"
                        + ",rentPrice,detail,latitude,longitude,webId,"
                        + "( 6371 * acos( cos( radians(?) ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians(?) ) + sin( radians(?) ) * sin( radians( latitude ) ) ) ) "
                        + "as 'distance' FROM tblHouse "
                        + "WHERE ( 6371 * acos( cos( radians(?) ) * cos( radians( latitude ) ) * cos( radians( longitude ) - radians(?) ) + sin( radians(?) ) * sin( radians( latitude ) ) ) ) < ?) H "
                        + "JOIN tblHouse_Bonus B ON H.id  = B.houseId order by distance";
                stm = con.prepareStatement(sql);
                stm.setFloat(1, Float.parseFloat(latitude));
                stm.setFloat(2, Float.parseFloat(longitude));
                stm.setFloat(3, Float.parseFloat(latitude));
                stm.setFloat(4, Float.parseFloat(latitude));
                stm.setFloat(5, Float.parseFloat(longitude));
                stm.setFloat(6, Float.parseFloat(latitude));
                stm.setFloat(7, distance);
                rs = stm.executeQuery();
                List<Integer> checkList = new ArrayList<>();
                while (rs.next()) {
                    HouseDTO houseItem = new HouseDTO();
                    int id = rs.getInt("id");

                    if (listHouses == null) {
                        listHouses = new ArrayList<>();
                    }
                    if (!checkList.contains(id)) {
                        houseItem.setId(BigInteger.valueOf(id));
                        houseItem.setTitle(rs.getString("title"));
                        houseItem.setLinkNew(rs.getString("linkNew"));
                        houseItem.setTimePost(rs.getDate("timePost") + "");
                        houseItem.setImg(rs.getString("img"));
                        houseItem.setRentAddress(rs.getString("rentAddress"));
                        houseItem.setSize(rs.getString("size"));
                        houseItem.setElectricPrice(rs.getString("electricPrice"));
                        houseItem.setWaterPrice(rs.getString("waterPrice"));
                        houseItem.setRentPrice(rs.getString("rentPrice"));
                        houseItem.setDetail(rs.getString("detail"));
                        houseItem.setLatitude(rs.getFloat("latitude") + "");
                        houseItem.setLongitude(rs.getFloat("longitude") + "");
                        houseItem.setWebsite(rs.getString("webId"));
                        houseItem.setDistance(rs.getFloat("distance"));
                        
                        BonusDTO bonus = new BonusDTO();
                        bonus = helper.setBonus(bonus, rs.getInt("bonusId"));
                        houseItem.setBonusDTO(bonus);
                        
                        checkList.add(id);
                        listHouses.add(houseItem);
                        
                    } else {
                        houseItem = listHouses.get(checkList.indexOf(id));
                        BonusDTO bonus = houseItem.getBonusDTO();
                        bonus = helper.setBonus(bonus, rs.getInt("bonusId"));
                        houseItem.setBonusDTO(bonus);
                        listHouses.set(checkList.indexOf(id), houseItem);
//                        listHouses.get(listHouses.size()-1).setBonusDTO(bonus);
                    }
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
    }

    public boolean insertBonus() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBConnection.makeConnection();
            if (con != null) {
                String sql = "insert into tblBonus(name) values(?) SELECT SCOPE_IDENTITY() as ID";
                stm = con.prepareStatement(sql);
                stm.setString(1, "chuc");
                int row = stm.executeUpdate();
                if (row > 0) {
                    rs = stm.getGeneratedKeys();
                    if (rs.next()) {
                        System.out.println(rs.getInt(1));
                    }
                    return true;
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
        return false;
    }

}
