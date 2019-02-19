

package com.example.android.bakingapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "recipe_table")
public class Recipe implements Parcelable {

  @PrimaryKey
  @SerializedName("id")
  @Expose
  private Integer id;
  @SerializedName("name")
  @Expose
  private String name;
  @Ignore
  @SerializedName("ingredients")
  @Expose
  private ArrayList<Ingredient> ingredients;
  @Ignore
  @SerializedName("steps")
  @Expose
  private ArrayList<RecipeStep> steps;
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
  public Recipe(Integer id, String name, ArrayList<Ingredient> ingredients,
      ArrayList<RecipeStep> steps, Integer servings, String image) {

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


  public ArrayList<Ingredient> getIngredients() {
    return ingredients;
  }


  public ArrayList<RecipeStep> getSteps() {
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