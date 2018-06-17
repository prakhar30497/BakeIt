package in.prakhar.bakeit;

import android.os.Bundle;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class RecipeDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();

        recipeDetailsFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fc_recipe_details, recipeDetailsFragment)
                .commit();
    }
}
