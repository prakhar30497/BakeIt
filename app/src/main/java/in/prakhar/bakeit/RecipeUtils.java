package in.prakhar.bakeit;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class RecipeUtils {
    private static final String LOG_TAG = RecipeUtils.class.getSimpleName();

    private RecipeUtils(){
    }

    private static List<Recipe> extractFeatureFromJson(String recipeJson){
        List<Recipe> recipes = new ArrayList<>();

        try{
            JSONArray results = new JSONArray(recipeJson);

            for(int i=0; i<results.length(); i++){

                List<Ingredient> ingredientList = new ArrayList<>();
                List<Step> stepList = new ArrayList<>();

                JSONObject currentRecipe = results.getJSONObject(i);
                int id = currentRecipe.getInt("id");
                String name = currentRecipe.getString("name");

                JSONArray ingredients = currentRecipe.getJSONArray("ingredients");
                for(int j=0; j<ingredients.length(); j++){

                    JSONObject currentIngredient = ingredients.getJSONObject(j);
                    float quantity = (float) currentIngredient.getDouble("quantity");
                    String measure = currentIngredient.getString("measure");
                    String ingredient = currentIngredient.getString("ingredient");

                    Ingredient ingredient1 = new Ingredient(quantity, measure, ingredient);
                    ingredientList.add(ingredient1);
                }
                JSONArray steps = currentRecipe.getJSONArray("steps");
                for(int j=0; j<steps.length(); j++){

                    JSONObject currentStep = steps.getJSONObject(j);
                    int stepId = currentStep.getInt("id");
                    String shortDescription = currentStep.getString("shortDescription");
                    String description = currentStep.getString("description");
                    String videoUrl = currentStep.getString("videoURL");

                    Step step = new Step(stepId, shortDescription, description, videoUrl);
                    stepList.add(step);
                }

                int servings = currentRecipe.getInt("servings");
                int image = currentRecipe.getInt("image");

                Recipe recipe = new Recipe(id, name, ingredientList, stepList, servings, image);
                recipes.add(recipe);
            }
        }catch (JSONException e){

        }
        return recipes;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if(url==null)
            return jsonResponse;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            if(urlConnection.getResponseCode()==200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else{
                Log.e(LOG_TAG, "Error Response Code: " + urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retreiving the movie json results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static List<Recipe> fetchRecipeData(String requestUrl){
        try{
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<Recipe> recipes = extractFeatureFromJson(jsonResponse);
        return recipes  ;
    }
}
