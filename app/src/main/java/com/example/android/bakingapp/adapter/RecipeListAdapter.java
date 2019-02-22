package com.example.android.bakingapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder> {

    private ArrayList<Recipe> recipeList = new ArrayList<>();
    private ListItemClickListener listener;
    public RecipeListAdapter(ListItemClickListener listItemClickListener) {
        this.listener=listItemClickListener;
    }

    public interface ListItemClickListener{
        void onListItemClick(Recipe recipe);
    }

    @NonNull
    @Override
    public RecipeListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recipe_list_item,viewGroup,false);
        return new RecipeListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeListViewHolder recipeListViewHolder, int i) {

        Recipe recipe = recipeList.get(i);
        recipeListViewHolder.recipeName.setText(recipe.getName());
        // Double value for quantity variable is converted into string to pass as argument for TextView.setText() method.
        recipeListViewHolder.recipeServings.setText(recipe.getServings().toString());
        // If there is no image url the image view will be loaded with sample image from the drawable folder
        if(recipe.getImage()!=null && recipe.getImage().isEmpty()){
            recipeListViewHolder.recipeImage.setImageResource(R.drawable.cake);
        }else {
            Picasso.get().load(recipe.getImage()).fit().into(recipeListViewHolder.recipeImage);
        }


    }

    @Override
    public int getItemCount() {

        return recipeList.size();
    }
    public void setRecipeListData(ArrayList<Recipe> recipeList){
        this.recipeList=recipeList;
        notifyDataSetChanged();
    }

    public  class RecipeListViewHolder extends RecyclerView.ViewHolder  {
        private ImageView recipeImage;
        private TextView  recipeName;
        private  TextView recipeServings;

        public RecipeListViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeImage=itemView.findViewById(R.id.recipe_image);
            recipeName=itemView.findViewById(R.id.recipe_name);
            recipeServings=itemView.findViewById(R.id.recipe_servings);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if(listener!=null && position !=RecyclerView.NO_POSITION) {
                    listener.onListItemClick(recipeList.get(position));

                }
            });
        }


    }
}
