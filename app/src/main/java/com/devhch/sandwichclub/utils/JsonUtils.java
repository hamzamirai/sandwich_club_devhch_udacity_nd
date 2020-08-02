package com.devhch.sandwichclub.utils;

/**
 * Created By Mirai Dev.
 * Hamza Chaouki
 * Hamza Shawki
 */
import android.util.Log;

import com.devhch.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static final String KEY_NAME = "name";
    public static final String KEY_MAIN_NAME = "mainName";
    public static final String KEY_ALSO_KNOW_AS = "alsoKnownAs";
    public static final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_INGREDIENTS = "ingredients";

    /**
     * Return an {@link Sandwich} object by parsing out information
     * about the first sandwich from the input sandwichJSON string.
     */
    public static Sandwich parseSandwichJson(String sandwichJSON) {

        // Create an empty Object that we can start adding sandwich to
        Sandwich mSandwich = new Sandwich();

        // Try to parse the sandwichJSON. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // Create an empty List that we can start adding alsoKnownAs to
            List<String> alsoKnownAs = new ArrayList<>();

            // Create an empty List that we can start adding ingredients to
            List<String> ingredients = new ArrayList<>();

            // Create a JSONObject from the sandwichJSON String
            JSONObject jsonResponse = new JSONObject(sandwichJSON);

            // Extract the JSONObject associated with the key called "name",
            JSONObject name = jsonResponse.getJSONObject(KEY_NAME);

            // Extract the value for the key called "mainName"
            String mainName = name.getString(KEY_MAIN_NAME);

            // Extract the JSONArray associated with the key called "alsoKnownAs",
            // which represents a list of alsoKnownAs).
            JSONArray alsoKnownAsArray = name.getJSONArray(KEY_ALSO_KNOW_AS);
            // If there are results in the alsoKnownAs array
            if (alsoKnownAsArray.length() > 0) {
                // For each alsoKnownAs in the alsoKnownAsArray.
                for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                    // Get a single alsoKnownAs at position i within the list.
                    String currentAlsoKnownAs = alsoKnownAsArray.getString(i);
                    alsoKnownAs.add(currentAlsoKnownAs);
                }
            }

            // Extract the value for the key called "placeOfOrigin"
            String placeOfOrigin = jsonResponse.getString(KEY_PLACE_OF_ORIGIN);

            // Extract the value for the key called "description"
            String description = jsonResponse.getString(KEY_DESCRIPTION);

            // Extract the value for the key called "image"
            String image = jsonResponse.getString(KEY_IMAGE);

            // For a given sandwich, extract the JSONObject associated with the
            // key called "ingredients", which represents a list of all ingredients
            // for that sandwich.
            JSONArray ingredientsArray = jsonResponse.getJSONArray(KEY_INGREDIENTS);
            // If there are results in the ingredients array
            if (alsoKnownAsArray.length() > 0) {
                // For each ingredients in the ingredientsArray.
                for (int i = 0; i < ingredientsArray.length(); i++) {
                    // Get a single ingredient at position i within the list of ingredients
                    String currentIngredients = ingredientsArray.getString(i);
                    ingredients.add(currentIngredients);
                }
            }

            // Create a new {@link Sandwich} object with the mainName, alsoKnownAs, placeOfOrigin,
            // description, image and ingredients from the JSON response.
            mSandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin,
                    description, image, ingredients);

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("Sandwich", "Problem parsing the Sandwich JSON results", e);
        }
        return mSandwich;
    }
}

