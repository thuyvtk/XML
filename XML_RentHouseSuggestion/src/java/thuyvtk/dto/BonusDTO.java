/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuyvtk.dto;

import java.io.Serializable;

/**
 *
 * @author ASUS
 */
public class BonusDTO implements Serializable{
    private boolean fridge;
    private boolean washing;
    private boolean air_conditioner;
    private boolean parking;
    private boolean heater;

    public BonusDTO() {
    }

    public BonusDTO(boolean fridge, boolean washing, boolean air_conditioner, boolean parking, boolean heater) {
        this.fridge = fridge;
        this.washing = washing;
        this.air_conditioner = air_conditioner;
        this.parking = parking;
        this.heater = heater;
    }

    public boolean isFridge() {
        return fridge;
    }

    public void setFridge(boolean fridge) {
        this.fridge = fridge;
    }

    public boolean isWashing() {
        return washing;
    }

    public void setWashing(boolean washing) {
        this.washing = washing;
    }

    public boolean isAir_conditioner() {
        return air_conditioner;
    }

    public void setAir_conditioner(boolean air_conditioner) {
        this.air_conditioner = air_conditioner;
    }

    public boolean isParking() {
        return parking;
    }

    public void setParking(boolean parking) {
        this.parking = parking;
    }

    public boolean isHeater() {
        return heater;
    }

    public void setHeater(boolean heater) {
        this.heater = heater;
    }
    
}
