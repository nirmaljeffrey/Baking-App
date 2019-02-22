

package com.example.android.bakingapp.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import com.example.android.bakingapp.database.IngredientListTypeConverter;
import com.example.android.bakingapp.database.StepListTypeConverter;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@Entity(tableName = "recipe_table")
@TypeConverters({IngredientListTypeConverter.class,StepListTypeConverter.class})
public class Recipe implements Parcelable {

  @PrimaryKey
  @SerializedName("id")
  @Expose
  private Integer id;
  @SerializedName("name")
  @Expose
  private String name;

  @SerializedName("ingredients")
  @Expose
  private List<Ingredient> ingredients;

  @SerializedName("steps")
  @Expose
  private List<RecipeStep> steps;
  @SerializedName("servings")
  @Expose
  private Integer servings;
  @SerializedName("image")
  @Expose
  private String image;


  /**
   *
   * @param ingredients
   * @param id
   * @param servings
   * @param name
   * @param image
   * @param steps
   */
  public Recipe(Integer id, String name, List<Ingredient> ingredients,
      List<RecipeStep> steps, Integer servings, String image) {

    this.id = id;
    this.name = name;
    this.ingredients = ingredients;
    this.steps = steps;
    this.servings = servings;
    this.image = image;
  }


  protected Recipe(Parcel in) {
    if (in.readByte() == 0) {
      id = null;
    } else {
      id = in.readInt();
    }
    name = in.readString();
    ingredients = in.createTypedArrayList(Ingredient.CREATOR);
    steps = in.createTypedArrayList(RecipeStep.CREATOR);
    if (in.readByte() == 0) {
      servings = null;
    } else {
      servings = in.readInt();
    }
    image = in.readString();
  }

  public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
    @Override
    public Recipe createFromParcel(Parcel in) {
      return new Recipe(in);
    }

    @Override
    public Recipe[] newArray(int size) {
      return new Recipe[size];
    }
  };

  public Integer getId() {
    return id;
  }


  public String getName() {
    return name;
  }


  public List<Ingredient> getIngredients() {
    return ingredients;
  }


  public List<RecipeStep> getSteps() {
    return steps;
  }


  public Integer getServings() {
    return servings;
  }


  public String getImage() {
    return image;
  }


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    if (id == null) {
      parcel.writeByte((byte) 0);
    } else {
      parcel.writeByte((byte) 1);
      parcel.writeInt(id);
    }
    parcel.writeString(name);
    parcel.writeTypedList(ingredients);
    parcel.writeTypedList(steps);
    if (servings == null) {
      parcel.writeByte((byte) 0);
    } else {
      parcel.writeByte((byte) 1);
      parcel.writeInt(servings);
    }
    parcel.writeString(image);
  }
}