package com.example.recipevingeroefening;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //STARTSTATE COUNTER
    int counter = 2;

    //INGREDIENT CLASS
    class Ingredient {
        String name;
        double baseAmount;
        String unit;
        String singularUnit;
        String amount;
        String singular;
        Boolean isSingular;

        Ingredient(String n, double ba, String u, String sU, String a, String s, Boolean iS) {
            name = n;
            baseAmount = ba;
            unit = u;
            singularUnit = sU;
            amount = a;
            singular = s;
            isSingular = iS;
        }
        //MAAKT HOEVEELHEID INGREDIENT AANGEPAST AAN PERSOON
        //PAST INGREDIENTEN AAN IN EEN ARRAY
        void calculateAmount() {
           double amount = this.baseAmount * counter;
           DecimalFormat format = new DecimalFormat("0.##");
           this.amount = format.format(amount);
           if (amount < 2) {
               isSingular = true;
           } else if (amount >= 2){
               isSingular = false;
           }
        }
        //GEBRUIKT GEÜPDATE AMOUNT OM DE INGREDIENTENLIJST AAN TE PASSEN
        //MAAKT VAN OBJECT IN CLASS EEN BRUIKBARE STRING
        //CHECKT OF INGREDIENT IN ENKELVOUD OF MEERVOUW MOET STAAN
        String toText() {
            if (isSingular) {
                return String.format("%s %s %s", this.amount, this.singularUnit, this.singular);
            }

            return String.format("%s %s %s", this.amount, this.unit, this.name);

        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ELEMENTS
        setContentView(R.layout.activity_main);
        TextView counterText = findViewById(R.id.title);
        TextView ingredientList = findViewById(R.id.recipe);
        Button buttonPlus = findViewById(R.id.plus);
        Button buttonMin = findViewById(R.id.min);

        //STARTSTATE
        updateTitle(counterText, counter);
            //MAAKT ARRAY VAN BENODIGDE INGREDIENTEN
            //INGREDIENTEN CLASS = naam, basishoeveelheid, unit, berekende hoeveelheid, enkelvoud van naam, boolean of ingreident enkelvoud moet zijn
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient("zelfrijzende bloem", 75, "g", "g", "","zelfrijzende bloem", false));
        ingredients.add(new Ingredient("eieren", 0.5, "st", "st", "","ei", true ));
        ingredients.add(new Ingredient("zout", 0.3, "snuifjes", "snuifje", "","zout", false));
        ingredients.add(new Ingredient("boter", 25, "g", "g", "", "boter", false));
        ingredients.add(new Ingredient("suiker", 18.8, "g", "g", "", "suiker", false));
        ingredients.add(new Ingredient("melk", 62.5, "ml", "ml", "", "melk", false));

            //BEGINSTATE LIJST METEEN AANPASSEN AAN BEGIINSTATE VAN COUNTER
        for(Ingredient in: ingredients) {
            in.calculateAmount();
        }
        update(counterText, counter, ingredients, ingredientList);

        //PLUS
        //BIJ ELKE KLIK GAAT HET AANTAL PERSONEN MET 1 OMHOOG
        //BIJ ELKE KLIK WORDT VOOR ELK INGREDIENT IN DE ARRAY DE NIEUWE HOEVEELHEID BEREKENT
        buttonPlus.setOnClickListener(v -> {
            buttonMin.setEnabled(true);
            counter += 1;
            for(Ingredient in: ingredients) {
                in.calculateAmount();
            }
            update(counterText, counter, ingredients, ingredientList);
        });

        //MIN
        //ALS DE COUNTER OP 1 STAAT WERKT DE KNOP NIET MEER
        //BIJ ELKE KLIK GAAT HET AANTAL PERSONEN MET 1 OMLAAG
        //BIJ ELKE KLIK WORDT VOOR ELK INGREDIENT IN DE ARRAY DE NIEUWE HOEVEELHEID BEREKENT
        buttonMin.setOnClickListener(v -> {
            if (counter > 1) {
                counter -= 1;
                for(Ingredient in: ingredients) {
                    in.calculateAmount();
                }
                update(counterText, counter, ingredients, ingredientList);
                if (counter == 1) {
                    buttonMin.setEnabled(false);
                }
            }
        });
    }
    //BIJ BUTTON KLIK WORDT DE TITEL GEÜPDATE (MEERVOUD/ENKELVOUD BIJ PERSONEN)
    //LOOPT OVER INGREDIENTEN ARRAY OM ZE IN EEN LIJST TE TONEN
    private void update(TextView counterText, int counter, ArrayList<Ingredient> ingredients, TextView ingredientList) {
        updateTitle(counterText, counter);
        ingredientList.setText("");
        for(Ingredient in: ingredients) {
            String ingredientString = in.toText();
            ingredientList.append(ingredientString);
            ingredientList.append("\n");
        }
    }

    private void updateTitle (TextView counterText, int counter) {
        if (counter == 1) {
            counterText.setText(String.format("ZACHTE WAFELS \n voor %d persoon", counter));
        } else {
            counterText.setText(String.format("ZACHTE WAFELS \n voor %d personen", counter));
        }
    }

}