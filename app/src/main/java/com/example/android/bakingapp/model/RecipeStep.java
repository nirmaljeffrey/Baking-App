

package com.example.android.bakingapp.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class RecipeStep implements Parcelable {

  public static final Creator<RecipeStep> CREATOR = new Creator<RecipeStep>() {
    @Override
    public RecipeStep createFromParcel(Parcel in) {
      return new RecipeStep(in);
    }

    @Override
    public RecipeStep[] newArray(int size) {
      return new RecipeStep[size];
    }
  };

  @PrimaryKey(autoGenerate = true)
  private int roomId;
  @SerializedName("id")
  @Expose
  private Integer id;
  private int recipeId;
  @SerializedName("shortDescription")
  @Expose
  private String shortDescription;
  @SerializedName("description")
  @Expose
  private String description;
  @SerializedName("videoURL")
  @Expose
  private String videoURL;
  @SerializedName("thumbnailURL")
  @Expose
  private String thumbnailURL;

  public RecipeStep() {

  }

  @Ignore
  public RecipeStep(Integer id, int recipeId, String shortDescription, String description,
      String videoURL, String thumbnailURL) {
    this.id = id;
    this.recipeId = recipeId;
    this.shortDescription = shortDescription;
    this.description = description;
    this.videoURL = videoURL;
    this.thumbnailURL = thumbnailURL;
  }

  protected RecipeStep(Parcel in) {
    if (in.readByte() == 0) {
      id = null;
    } else {
      id = in.readInt();
    }
    shortDescription = in.readString();
    description = in.readString();
    videoURL = in.readString();
    thumbnailURL = in.readString();
  }

  public int getRoomId() {
    return roomId;
  }

  public void setRoomId(int roomId) {
    this.roomId = roomId;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public int getRecipeId() {
    return recipeId;
  }

  public void setRecipeId(int recipeId) {
    this.recipeId = recipeId;
  }

  public String getShortDescription() {
    return shortDescription;
  }

  public void setShortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getVideoURL() {
    return videoURL;
  }

  public void setVideoURL(String videoURL) {
    this.videoURL = videoURL;
  }

  public String getThumbnailURL() {
    return thumbnailURL;
  }

  public void setThumbnailURL(String thumbnailURL) {
    this.thumbnailURL = thumbnailURL;
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
    parcel.writeString(shortDescription);
    parcel.writeString(description);
    parcel.writeString(videoURL);
    parcel.writeString(thumbnailURL);
  }
}