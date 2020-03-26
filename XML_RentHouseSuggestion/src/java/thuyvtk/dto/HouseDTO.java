/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuyvtk.dto;

import java.io.Serializable;
import thuyvtk.jaxbObject.HouseItem;

/**
 *
 * @author ASUS
 */
public class HouseDTO extends HouseItem implements Comparable<HouseDTO>, Serializable {

    private BonusDTO bonusDTO;
    private float distance;
    private float marketDistance;
    private float score;
    private int price;
    private int requestDistance;
    private BonusDTO requireBonus;
    private float maxSize;
    boolean checkSize;

    public BonusDTO getRequireBonus() {
        return requireBonus;
    }

    public void setRequireBonus(BonusDTO requireBonus) {
        this.requireBonus = requireBonus;
    }

    public float getScore() {
        float total = 0;
        total += initDistanceScore();
        total += initSizeScore(getSize());
        total += initBonusCore();
        total += initMarketScore();
        return this.score = total / (getPrice(this.requestDistance)/1000000);
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getPrice(int requireDistance) {
        return this.price = convertPrice(rentPrice);
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRequestDistance() {
        return requestDistance;
    }

    public void setRequestDistance(int requestDistance) {
        this.requestDistance = requestDistance;
    }

    public boolean isCheckSize() {
        return checkSize;
    }

    public void setCheckSize(boolean checkSize) {
        this.checkSize = checkSize;
    }

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
        return (float) Math.floor(marketDistance * 100) / 100;
    }

    public void setMarketDistance(float marketDistance) {
        this.marketDistance = marketDistance;
    }

    public float getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(float maxSize) {
        this.maxSize = maxSize;
    }
    
    

    private int convertPrice(String rentPrice) {
        String substring[] = null;
        int milion = 0;
        int thounsand = 0;
        int price = 0;
        try {
            if (rentPrice.contains("triệu")) {
                substring = rentPrice.split("triệu");
                milion = Integer.parseInt(substring[0].trim());
                price = milion * 1000000;
                if (rentPrice.contains("ngàn")) {
                    thounsand = Integer.parseInt(substring[1].trim().split("ngàn")[0].trim());
                    price += thounsand * 1000;
                }
                return price;
            } else if (rentPrice.contains("ngàn")) {
                thounsand = Integer.parseInt(rentPrice.split("ngàn")[0].trim());
                price = 1000 * thounsand;
                return price;
            }
            rentPrice = rentPrice.replace(".", "").replace(",", "").split("Đ")[0].trim();
            price = Integer.parseInt(rentPrice);
        } catch (Exception e) {
        }
        return price;
    }

    private float initDistanceScore() {
        return (float) (1 - (float) (this.distance / this.requestDistance));
    }

    private float initMarketScore() {
        return (float) (1 - (float) (this.getMarketDistance() / 5));
    }

    private float initSizeScore(String size) {
        size = size.replace("m2", "").replace("M2", "").replace("m", "").replace("M", "").trim();
        Float sizeParse = Float.parseFloat(size);
        if (checkSize) {
            return (float) sizeParse / this.maxSize;
        } else {
            return (float) ((sizeParse / this.maxSize)*0.3);
        }
    }

    private float initBonusCore() {
        float scoreBonus = 0;
        if (this.requireBonus.isFridge()) {
            if (this.bonusDTO.isFridge()) {
                scoreBonus += 5;
            }
        } else if (this.bonusDTO.isFridge()) {
            score += 2;
        }

        if (this.requireBonus.isWashing()) {
            if (this.bonusDTO.isWashing()) {
                scoreBonus += 5;
            }
        } else if (this.bonusDTO.isWashing()) {
            score += 2;
        }

        if (this.requireBonus.isAir_conditioner()) {
            if (this.bonusDTO.isAir_conditioner()) {
                scoreBonus += 5;
            }
        } else if (this.bonusDTO.isAir_conditioner()) {
            score += 2;
        }

        if (this.requireBonus.isHeater()) {
            if (this.bonusDTO.isHeater()) {
                scoreBonus += 5;
            }
        } else if (this.bonusDTO.isHeater()) {
            score += 2;
        }

        if (this.requireBonus.isParking()) {
            if (this.bonusDTO.isParking()) {
                scoreBonus += 5;
            }
        } else if (this.bonusDTO.isParking()) {
            score += 2;
        }
        return scoreBonus;
    }

    @Override
    public int compareTo(HouseDTO o) {
        return Float.compare(o.getScore(), this.getScore());
    }
}
