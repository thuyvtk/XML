/* 
 * To change this license header, choose License Headers in Project Properties. 
 * To change this template file, choose Tools | Templates 
 * and open the template in the editor. 
 */
package thuyvtk.utilities;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.json.JSONException;
import thuyvtk.common.Constraint;
import thuyvtk.dao.HouseDAO;
import thuyvtk.dto.Coordinate;
import thuyvtk.jaxbObject.HouseItem;
import thuyvtk.jaxbObject.ListHouse;
import thuyvtk.parser.JsonParser;

/**
 *
 * @author ASUS
 */
public class XMLHelper {

    public void JAXBUnmarshallingHouse(String xmlFile) throws NamingException, SQLException {
        try {
            JAXBContext context = JAXBContext.newInstance(ListHouse.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

//            unmarshaller.setEventHandler(new JAXBValidationHandler());
            File file = new File(xmlFile);
            ListHouse houses = (ListHouse) unmarshaller.unmarshal(file);
            JsonParser jsonParser = new JsonParser();
            for (int i = 0; i < houses.getHouse().size(); i++) {
                HouseItem item = houses.getHouse().get(i);
                HouseDAO houseDAO = new HouseDAO();
                HouseItem dto = houseDAO.isHomeExisted(item.getLinkNew());

                if (dto == null) {
                    String timePost = convertDate(item.getTimePost());
                    item.setTimePost(timePost);
                    if (item.getLongitude().isEmpty() || item.getLongitude() == null) {
                        String url = initParamAddress(item.getRentAddress().trim());
                        Coordinate coordinate = jsonParser.parseJSON(url);
                        if (coordinate == null) {
                            coordinate = new Coordinate(0, 0);
                        }
                        item.setLongitude(coordinate.getLongitude() + "");
                        item.setLatitude(coordinate.getLongitude() + "");
                    }
                    houseDAO.insertHouse(item);
                } //else if (!item.equals(dto)) {
                //houseDAO.updateHouse(dto);
//                }

            }
        } catch (InterruptedException ex) {
            Logger.getLogger(XMLHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(XMLHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException ex) {
            Logger.getLogger(XMLHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(XMLHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(XMLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String initParamAddress(String address) throws UnsupportedEncodingException {
        address = address.trim().replace(" ", "+").replace("/", ",");
        address = URLEncoder.encode(address, "UTF-8");
        return Constraint.GEOCODE_API.concat(address + "?json=1");
    }

    public String convertDate(String timePost) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        if (timePost.contains("ngày trước")) {
            int time = Integer.valueOf(timePost.split(" ")[0]);
            calendar.add(Calendar.DATE, -time);
        } else if (timePost.contains("tuần trước")) {
            int time = Integer.valueOf(timePost.split(" ")[0]);
            calendar.add(Calendar.DATE, -time * 7);
        } else if (timePost.contains("tháng trước")) {
            int time = Integer.valueOf(timePost.split(" ")[0]);
            calendar.add(Calendar.MONTH, -time);
        } else if (timePost.contains("năm trước")) {
            int time = Integer.valueOf(timePost.split(" ")[0]);
            calendar.add(Calendar.YEAR, -time);
        }

        return dateFormat.format(calendar.getTime());
    }
}
