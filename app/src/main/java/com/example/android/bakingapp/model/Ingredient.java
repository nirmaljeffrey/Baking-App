



        package com.example.android.bakingapp.model;

        import android.arch.persistence.room.ColumnInfo;
        import android.arch.persistence.room.Entity;
        import android.arch.persistence.room.PrimaryKey;
        import android.os.Parcel;
        import android.os.Parcelable;

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;
@Entity
public class Ingredient implements Parcelable
{



  @PrimaryKey(autoGenerate = true)
  private int roomIngredientId;
    @SerializedName("quantity")
    @Expose
    private Double quantity;
    @SerializedName("measure")
    @Expose
    private String measure;
    @SerializedName("ingredient")
    @Expose
    private String ingredient;
    private int recipeId;

  public int getRecipeId() {
    return recipeId;
  }

  public void setRecipeId(int recipeId) {
    this.recipeId = recipeId;
  }

  /**
     *
     * @param measure
     * @param ingredient
     * @param quantity
     */
    public Ingredient(Double quantity, String measure, String ingredient) {

        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    protected Ingredient(Parcel in) {
        if (in.readByte() == 0) {
            quantity = null;
        } else {
            quantity = in.readDouble();
        }
        measure = in.readString();
        ingredient = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public Double getQuantity() {
        return quantity;
    }



    public String getMeasure() {
        return measure;
    }



    public String getIngredient() {
        return ingredient;
    }

  public void setRoomIngredientId(int roomIngredientId) {
    this.roomIngredientId = roomIngredientId;
  }

  public int getRoomIngredientId() {

    return roomIngredientId;
  }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (quantity == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(quantity);
        }
        parcel.writeString(measure);
        parcel.writeString(ingredient);
    }
}
