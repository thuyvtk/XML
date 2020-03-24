/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuyvtk.cart;

import java.util.HashMap;
import thuyvtk.dto.HouseDTO;

/**
 *
 * @author ASUS
 */
public class LoveListBean extends HashMap {

    public LoveListBean() {
        super();
    }

    public boolean addRentNew(HouseDTO rentNew) {
        String key = rentNew.getId().toString();
        if (this.containsKey(key)) {
            return true;
        } else {
            this.put(rentNew.getId().toString(), rentNew);
            return false;
        }

    }

}
