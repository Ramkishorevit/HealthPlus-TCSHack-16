package com.healthplus.healthplus.Entity.Actors;

import java.util.List;

/**
 * Created by VSRK on 8/23/2016.
 */
public class Food {

    public boolean status;
    List<Data> data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data
    {
        public String name;

        public String getServing() {
            return serving;
        }

        public void setServing(String serving) {
            this.serving = serving;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Nutrition getNutrition() {
            return nutrition;
        }

        public void setNutrition(Nutrition nutrition) {
            this.nutrition = nutrition;
        }

        public String serving;
        Nutrition nutrition;

        public class Nutrition {

            public String calories;
            public String carbs;
            public String protein;
            public String cholesterol;

            public String getCalories() {
                return calories;
            }

            public void setCalories(String calories) {
                this.calories = calories;
            }

            public String getCarbs() {
                return carbs;
            }

            public void setCarbs(String carbs) {
                this.carbs = carbs;
            }

            public String getProtein() {
                return protein;
            }

            public void setProtein(String protein) {
                this.protein = protein;
            }

            public String getCholesterol() {
                return cholesterol;
            }

            public void setCholesterol(String cholesterol) {
                this.cholesterol = cholesterol;
            }
        }
    }
}
