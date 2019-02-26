

package com.example.android.bakingapp.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@Entity(tableName = "recipe_table")
public class Recipe implements Parcelable {

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
  @PrimaryKey
  @SerializedName("id")
  @Expose
  private Integer id;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("ingredients")
  @Expose
  @Ignore
  private List<Ingredient> ingredients;
  @SerializedName("steps")
  @Expose
  @Ignore
  private List<RecipeStep> steps;
  @SerializedName("servings")
  @Expose
  private Integer servings;
  @SerializedName("image")
  @Expose
  private String image;

  public Recipe() {

  }

  @Ignore
  public Recipe(String name,
      List<Ingredient> ingredients,
      List<RecipeStep> steps, Integer servings, String image) {
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

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Ingredient> getIngredients() {
    return ingredients;
  }

  public void setIngredients(List<Ingredient> ingredients) {
    this.ingredients = ingredients;
  }

  public List<RecipeStep> getSteps() {
    return steps;
  }

  public void setSteps(List<RecipeStep> steps) {
    this.steps = steps;
  }

  public Integer getServings() {
    return servings;
  }

  public void setServings(Integer servings) {
    this.servings = servings;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
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