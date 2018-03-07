package com.abdallah.sandwichclub.utils;

import com.abdallah.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        try {
            /* Parse the entire json */
            JSONObject jsonObject = new JSONObject(json);

            // get our sandwich fields
            /* 1. get mainName field */
            JSONObject nameJsonObject = jsonObject.getJSONObject("name");
            String mainName = nameJsonObject.getString("mainName");

            /* 2. get alsoKnownAs field */
            JSONArray alsoKnownAsJSONArray = nameJsonObject.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = new ArrayList<>();
            int akaJsonArraylength = alsoKnownAsJSONArray.length();
            for (int i = 0; i< akaJsonArraylength; i++) {
                alsoKnownAs.add(alsoKnownAsJSONArray.getString(i));
            }

            /* 3. get placeOfOrigin field */
            String placeOfOrigin = jsonObject.getString("placeOfOrigin");

            /* 4. get description field */
            String description = jsonObject.getString("description");

            /* 5. get image field */
            String image = jsonObject.getString("image");

            /* 6. get ingredients field */
            JSONArray ingredientsJASONArray = jsonObject.getJSONArray("ingredients");
            List<String> ingredients = new ArrayList<>();
            int ingredientsJSONArrayLength = ingredientsJASONArray.length();
            for (int i = 0; i<ingredientsJSONArrayLength; i++) {
                ingredients.add(ingredientsJASONArray.getString(i));
            }

            // create the sandwich object
            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }
}
