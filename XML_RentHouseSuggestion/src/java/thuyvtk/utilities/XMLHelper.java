/* 
 * To change this license header, choose License Headers in Project Properties. 
 * To change this template file, choose Tools | Templates 
 * and open the template in the editor. 
 */
package thuyvtk.utilities;

import com.sun.codemodel.JCodeModel;
import com.sun.tools.xjc.api.ErrorListener;
import com.sun.tools.xjc.api.S2JJAXBModel;
import com.sun.tools.xjc.api.SchemaCompiler;
import com.sun.tools.xjc.api.XJC;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;
import thuyvtk.common.Constraint;
import thuyvtk.dao.HouseDAO;
import thuyvtk.jaxbObject.HouseItem;
import thuyvtk.jaxbObject.ListHouse;

/**
 *
 * @author ASUS
 */
public class XMLHelper {


    public static void JAXBUnmarshallingHouse(String xmlFile) {
        try {
            JAXBContext context = JAXBContext.newInstance(ListHouse.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            
//            unmarshaller.setEventHandler(new JAXBValidationHandler());

            File file = new File(xmlFile);
            ListHouse houses = (ListHouse) unmarshaller.unmarshal(file);
            for (int i = 0; i < houses.getHouse().size(); i++) {
                HouseItem item = houses.getHouse().get(i);
                HouseDAO houseDAO = new HouseDAO();
                System.out.println(houseDAO.insertHouse(item));
            }
        } catch (JAXBException ex) {
            Logger.getLogger(XMLHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(XMLHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(XMLHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        } 
}
