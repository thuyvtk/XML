/* 
 * To change this license header, choose License Headers in Project Properties. 
 * To change this template file, choose Tools | Templates 
 * and open the template in the editor. 
 */
package thuyvtk.utilities;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.json.JSONException;
import thuyvtk.common.Constraint;
import thuyvtk.dto.Coordinate;
import thuyvtk.jaxbObject.HouseItem;
import thuyvtk.jaxbObject.ListHouse;
import thuyvtk.parser.JsonParser;

/**
 *
 * @author ASUS
 */
public class XMLHelper {

    public void JAXBUnmarshallingHouse(String xmlFile) {
        try {
            JAXBContext context = JAXBContext.newInstance(ListHouse.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

//            unmarshaller.setEventHandler(new JAXBValidationHandler());
            File file = new File(xmlFile);
            ListHouse houses = (ListHouse) unmarshaller.unmarshal(file);
            JsonParser jsonParser = new JsonParser();
            for (int i = 0; i < houses.getHouse().size(); i++) {
                try {
                    HouseItem item = houses.getHouse().get(i);
                    String url = initParamAddress(item.getRentAddress());
                    Coordinate coordinate = jsonParser.parseJSON(url);
                    System.out.println(item.getRentAddress() + ": " + coordinate.getLatitude() + " - " + coordinate.getLongitude());
//                item.setLatitude(BigDecimal.valueOf(coordinate.getLatitude()));
//                item.setLongitude(BigDecimal.valueOf(coordinate.getLongitude()));
//                HouseDAO houseDAO = new HouseDAO();
//                System.out.println(houseDAO.insertHouse(item, i+1));
                } catch (Exception e) {
                    // neu co loi
                    e.printStackTrace();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(XMLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String initParamAddress(String address) {
        address = address.trim().replace(" ", "+").replace("/", ",");
        try {
            address = URLEncoder.encode(address, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return Constraint.GEOCODE_API.concat(address + "?json=1");
    }
}
