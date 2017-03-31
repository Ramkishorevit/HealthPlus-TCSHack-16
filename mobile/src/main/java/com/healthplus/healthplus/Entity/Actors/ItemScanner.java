package com.healthplus.healthplus.Entity.Actors;

import java.util.List;

/**
 * Created by ramkishorevs on 03/03/17.
 */

public class ItemScanner {

    public String code;
    public List<Items> items;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    public class Items
    {
        String title;
        String description;
        String brand;
        double lowest_recorded_price;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public double getLowest_recorded_price() {
            return lowest_recorded_price;
        }

        public void setLowest_recorded_price(double lowest_recorded_price) {
            this.lowest_recorded_price = lowest_recorded_price;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        String model;
        List<String> images;
        String color;
    }
}
