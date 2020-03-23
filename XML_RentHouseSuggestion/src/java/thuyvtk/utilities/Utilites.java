/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuyvtk.utilities;

import thuyvtk.dto.BonusDTO;

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

}
