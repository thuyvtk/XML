/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuyvtk.dto;

import thuyvtk.jaxbObject.HouseItem;

/**
 *
 * @author ASUS
 */
public class HouseDTO extends HouseItem{
    private BonusDTO bonusDTO;
    private float distance;
    private float marketDistance;

    public HouseDTO() {
    }

    public HouseDTO(BonusDTO bonusDTO, float distance, float marketDistance) {
        this.bonusDTO = bonusDTO;
        this.distance = distance;
        this.marketDistance = marketDistance;
    }

    public BonusDTO getBonusDTO() {
        return bonusDTO;
    }

    public void setBonusDTO(BonusDTO bonusDTO) {
        this.bonusDTO = bonusDTO;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getMarketDistance() {
        return (float) Math.floor(marketDistance*100)/100;
    }

    public void setMarketDistance(float marketDistance) {
        this.marketDistance = marketDistance;
    }
    
    
            
    
}
