package in.prakhar.bakeit;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RecipeDetailsFragment extends Fragment implements RecipeDetailsAdapter.RecipeDetailsItemClickHandler {

    @BindView(R.id.tv_ingredients)
    TextView mIngredients;

    private RecyclerView mRecyclerView;
    private RecipeDetailsAdapter mAdapter;
    private List<Step> mSteps;

    public RecipeDetailsFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        mRecyclerView = (RecyclerView)  view.findViewById(R.id.rv_recipe_steps);

        mSteps = new ArrayList<>();
        mAdapter = new RecipeDetailsAdapter(mSteps, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onListItemClick(Step clickedStep) {

    }
}
