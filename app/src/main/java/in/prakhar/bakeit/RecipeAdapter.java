package in.prakhar.bakeit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder>{

    private List<Recipe> mRecipes;
    final private RecipeItemClickHandler mOnClickListener;

    public interface RecipeItemClickHandler{
        public void onListItemClick(Recipe clickedRecipe);
    }

    public RecipeAdapter(List<Recipe> recipes, RecipeItemClickHandler listener) {
        mRecipes = recipes;
        mOnClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Context context = holder.mView.getContext();
        Recipe recipe = mRecipes.get(position);
        holder.mRecipe = recipe;
        holder.mName.setText(recipe.getName());
        holder.mServings.setText(recipe.getServings()+" servings");

        Picasso.with(context)
                .load(recipe.getImage())
                .into(holder.mImage);

        holder.mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mOnClickListener.onListItemClick(holder.mRecipe);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final View mView;
        public Recipe mRecipe;
        public ImageView mImage;
        public TextView mName;
        public TextView mServings;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mImage = (ImageView) itemView.findViewById(R.id.iv_recipe);
            mName = (TextView) itemView.findViewById(R.id.tv_recipe_name);
            mServings = (TextView) itemView.findViewById(R.id.tv_recipe_servings);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onListItemClick(mRecipe);
        }
    }

    public void setRecipeData(List<Recipe> recipes){
        mRecipes = recipes;
        notifyDataSetChanged();
    }
}
