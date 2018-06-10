package in.prakhar.bakeit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListFragment extends Fragment implements RecipeAdapter.RecipeItemClickHandler{

    private static final String ALL_RECIPES = "all recipes";

    private RecyclerView mRecyclerView;

    private RecipeAdapter mAdapter;
    private List<Recipe> mRecipes;

    public RecipeListFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_recipes);

        mRecipes = new ArrayList<>();
        mAdapter = new RecipeAdapter(mRecipes, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        APIInterface mInterface = RetrofitBuilder.Retrieve();
        Call<List<Recipe>> recipes = mInterface.getRecipes();

        recipes.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

                List<Recipe> mRecipes = response.body();
                mAdapter.setRecipeData(mRecipes);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });



        return rootView;
    }

    @Override
    public void onListItemClick(Recipe clickedRecipe) {

    }
}
