package com.example.android.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.RecipeStep;
import java.util.ArrayList;

public class RecipeStepMasterAdapter extends
    RecyclerView.Adapter<RecipeStepMasterAdapter.MasterStepViewHolder> {

  private ArrayList<RecipeStep> recipeStepArrayList;
  private final RecipeStepClickListener clickListener;
  private final Context context;

  public RecipeStepMasterAdapter(RecipeStepClickListener recipeStepClickListener, Context context) {
    this.clickListener = recipeStepClickListener;
    this.context = context;
  }

  @NonNull
  @Override
  public MasterStepViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.recipe_step_list_item, viewGroup, false);
    return new MasterStepViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull MasterStepViewHolder masterStepViewHolder, int i) {
    RecipeStep recipeStep = recipeStepArrayList.get(i);

    String shortDescription = recipeStep.getShortDescription();
    String id = String.valueOf(recipeStep.getId());

    masterStepViewHolder.shortDescription.setText(String
        .format(context.getString(R.string.recipe_step_master_short_description), id,
            shortDescription));

  }

  @Override
  public int getItemCount() {
    if (recipeStepArrayList == null) {
      return 0;
    }
    return recipeStepArrayList.size();
  }

  public void setRecipeStepArrayList(ArrayList<RecipeStep> arrayList) {
    this.recipeStepArrayList = arrayList;
  }

  public interface RecipeStepClickListener {

    void onItemClick(RecipeStep recipeStep);
  }

  public class MasterStepViewHolder extends RecyclerView.ViewHolder {

    private final TextView shortDescription;

    MasterStepViewHolder(@NonNull View itemView) {
      super(itemView);
      shortDescription = itemView.findViewById(R.id.text_view_recipe_step_list);
      itemView.setOnClickListener(view -> {
        int position = getAdapterPosition();
        if (clickListener != null && position != RecyclerView.NO_POSITION) {
          clickListener.onItemClick(recipeStepArrayList.get(position));
        }
      });
    }
  }
}
