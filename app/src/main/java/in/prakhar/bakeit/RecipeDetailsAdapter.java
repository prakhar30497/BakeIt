package in.prakhar.bakeit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecipeDetailsAdapter extends RecyclerView.Adapter<RecipeDetailsAdapter.ViewHolder>{

    private List<Step> mSteps;
    final private RecipeDetailsAdapter.RecipeDetailsItemClickHandler mOnClickListener;

    public interface RecipeDetailsItemClickHandler{
        public void onListItemClick(Step clickedStep);
    }

    public RecipeDetailsAdapter(List<Step> steps, RecipeDetailsAdapter.RecipeDetailsItemClickHandler listener) {
        mSteps = steps;
        mOnClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_details_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecipeDetailsAdapter.ViewHolder holder, int position) {
        Context context = holder.mView.getContext();
        Step step = mSteps.get(position);

        holder.step = step;
        holder.step_short_description.setText(step.getShortDescription());

        holder.mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mOnClickListener.onListItemClick(holder.step);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final View mView;
        public Step step;
        public TextView step_short_description;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            step_short_description = (TextView) itemView.findViewById(R.id.tv_step_short_description);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onListItemClick(step);
        }
    }

    public void setStepData(List<Step> steps){
        mSteps = steps;
        notifyDataSetChanged();
    }
}
