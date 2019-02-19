package com.example.android.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Ingredient;

import java.util.ArrayList;

public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.IngredientItemViewHolder> {
    private static final int TYPE_HEADER=0;
    private static final int TYPE_LIST=1;


    private ArrayList<Ingredient> ingredientArrayList=new ArrayList<>();
    private Context context;
    public IngredientListAdapter(Context context){
        this.context=context;
    }
    @NonNull
    @Override
    public IngredientItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
         int layoutResourceId;
         switch (viewType){
             case TYPE_HEADER:
                 layoutResourceId=R.layout.ingredient_list_header;
                 break;
             case TYPE_LIST:
                 layoutResourceId=R.layout.ingredient_list_item;
                 break;
                 default:
                     throw new IllegalArgumentException("Invalid view type of id "+viewType);
         }

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(layoutResourceId,viewGroup,false);
            return new IngredientItemViewHolder(view,viewType);



    }

    @Override
    public void onBindViewHolder(@NonNull IngredientItemViewHolder ingredientItemViewHolder, int position) {

            if(ingredientItemViewHolder.view_type==TYPE_HEADER)
            {
                ingredientItemViewHolder.linearLayout.setBackgroundColor(ContextCompat.getColor(context,R.color.colorTableHeader));
            }
            else if(ingredientItemViewHolder.view_type==TYPE_LIST)
            {
                Ingredient ingredient = ingredientArrayList.get(position - 1);
                String ingredientName = ingredient.getIngredient();
                String ingredientMeasure = ingredient.getMeasure();
                String quantity = ingredient.getQuantity().toString();
                // Handling worst cases when there is no text in ingredients text view.
                if (!ingredientName.isEmpty()) {
                    ingredientItemViewHolder.ingredientName.setText(ingredientName);
                } else {
                    ingredientItemViewHolder.ingredientName.setText("-");
                }
                if (!quantity.isEmpty()) {
                    ingredientItemViewHolder.ingredientQuantity.setText(quantity);
                } else {
                    ingredientItemViewHolder.ingredientQuantity.setText("-");
                }
                if (!ingredientMeasure.isEmpty()) {
                    ingredientItemViewHolder.ingredientMeasure.setText(ingredientMeasure);
                } else {
                    ingredientItemViewHolder.ingredientMeasure.setText("-");
                }

            }

    }


    @Override
    public int getItemViewType(int position) {
        if(position==0) return TYPE_HEADER;
        return TYPE_LIST;
    }

    @Override
    public int getItemCount() {
        if(ingredientArrayList==null)return 0;

        return ingredientArrayList.size()+1 ;
    }
    public void setIngredientArrayList(ArrayList<Ingredient> ingredients){
        this.ingredientArrayList=ingredients;
        notifyDataSetChanged();
    }


    public static class IngredientItemViewHolder extends RecyclerView.ViewHolder{
        private int view_type;
        private TextView ingredientName;
        private TextView ingredientQuantity;
        private TextView ingredientMeasure;
        private LinearLayout linearLayout;
        public IngredientItemViewHolder(@NonNull View itemView,int viewType) {
            super(itemView);
            if(viewType==TYPE_HEADER){
                linearLayout = itemView.findViewById(R.id.ingredient_header_linear_layout);
                view_type=0;
            }else if (viewType==TYPE_LIST){
                ingredientMeasure = itemView.findViewById(R.id.ingredient_measure_text_view);
                ingredientName = itemView.findViewById(R.id.ingredient_name_text_view);
                ingredientQuantity = itemView.findViewById(R.id.ingredient_quantity_text_view);
                view_type=1;
            }

        }
    }



}
