package in.prakhar.bakeit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RecipeListFragment extends Fragment implements RecipeAdapter.RecipeItemClickHandler{

    private static final String ALL_RECIPES = "all recipes";
    private static final String LOG_TAG = "RecipeListFragment";

    private RecyclerView mRecyclerView;

    private RecipeAdapter mAdapter;
    private List<Recipe> mRecipes;

    private RequestQueue mRequestQueue;

    public RecipeListFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_recipes);

        mRecipes = new ArrayList<>();

        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        mRequestQueue = Volley.newRequestQueue(getContext());
        parseJSON();

        return rootView;
    }

    private void parseJSON() {
        String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for(int i=0; i<response.length(); i++){
                                List<Ingredient> ingredientList = new ArrayList<>();
                                List<Step> stepList = new ArrayList<>();

                                JSONObject currentRecipe = response.getJSONObject(i);
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
                                int image = (R.drawable.nutella_pie);

                                if(id == 1){
                                    image = (R.drawable.np);
                                }
                                else if(id == 2){
                                    image = (R.drawable.brownies);
                                }
                                else if(id == 3){
                                    image = (R.drawable.yellow_cake);
                                }
                                else if(id == 4){
                                    image = (R.drawable.cheesecake);
                                }
                                Log.d(LOG_TAG, "image-"+image);
                                mRecipes.add(new Recipe(id, name, ingredientList, stepList, servings, image));
                            }
                            mAdapter = new RecipeAdapter(mRecipes, RecipeListFragment.this);
                            mRecyclerView.setAdapter(mAdapter);
                            mAdapter.setRecipeData(mRecipes);
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mRequestQueue.add(request);
    }

    @Override
    public void onListItemClick(Recipe clickedRecipe) {
        Intent intent = new Intent(getActivity(), RecipeDetailsActivity.class);
        intent.putExtra("Recipe", clickedRecipe);
        startActivity(intent);
    }
}
