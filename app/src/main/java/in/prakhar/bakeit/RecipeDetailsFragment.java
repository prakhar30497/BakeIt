package in.prakhar.bakeit;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RecipeDetailsFragment extends Fragment implements RecipeDetailsAdapter.RecipeDetailsItemClickHandler {

    private TextView ingredientsTextView;

    private RecyclerView mRecyclerView;
    private RecipeDetailsAdapter mAdapter;
    private List<Step> mSteps;
    private Recipe mRecipe;
    private List<Ingredient> mIngredients;

    public RecipeDetailsFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        RecipeDetailsActivity activity = (RecipeDetailsActivity) getActivity();
        mRecipe = activity.getRecipe();

        ingredientsTextView = (TextView) view.findViewById(R.id.tv_ingredients);

        mIngredients = mRecipe.getIngredients();
        mSteps = mRecipe.getSteps();

        ingredientsTextView.append("-  " + mIngredients.get(0).getIngredient()+
                " (" + mIngredients.get(0).getQuantity()+
                " " + mIngredients.get(0).getMeasure()+
                ")");
        for(int i=1; i<mIngredients.size(); i++){
            ingredientsTextView.append("\n");
            ingredientsTextView.append("-  " + mIngredients.get(i).getIngredient()+
                    " (" + mIngredients.get(i).getQuantity()+
                    " " + mIngredients.get(i).getMeasure()+
                    ")");
        }

        mRecyclerView = (RecyclerView)  view.findViewById(R.id.rv_recipe_steps);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new RecipeDetailsAdapter(mSteps, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mAdapter.setStepData(mSteps);

        return view;
    }

    @Override
    public void onListItemClick(Step clickedStep) {
        Intent intent = new Intent(getActivity(), RecipeStepActivity.class);
        intent.putExtra("Step", clickedStep);
        startActivity(intent);
    }
}
