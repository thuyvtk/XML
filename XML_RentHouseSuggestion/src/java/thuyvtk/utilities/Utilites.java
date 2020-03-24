/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuyvtk.utilities;

import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import thuyvtk.dto.BonusDTO;
import thuyvtk.jaxbObject.ListHouse;

/**
 *
 * @author ASUS
 */
public class Utilites {

    public BonusDTO setBonus(BonusDTO bonus, int bonusId) {
        switch (bonusId) {
            case 1:
                bonus.setFridge(true);
                break;
            case 2:
                bonus.setWashing(true);
                break;
            case 3:
                bonus.setAir_conditioner(true);
                break;
            case 4:
                bonus.setParking(true);
                break;
            case 5:
                bonus.setHeater(true);
                break;
        }
        return bonus;
    }
 public static String marshallToString(ListHouse houses) {
        try {
            JAXBContext context = JAXBContext.newInstance(ListHouse.class);
            Marshaller mar = context.createMarshaller();
//            mar.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            StringWriter sw = new StringWriter();
            mar.marshal(houses, sw);

            return sw.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
