

        package com.example.android.bakingapp.model;

        import android.arch.persistence.room.ColumnInfo;
        import android.arch.persistence.room.Entity;
        import android.arch.persistence.room.PrimaryKey;
        import android.os.Parcel;
        import android.os.Parcelable;
        import android.os.Parcelable.Creator;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;
@Entity
public class RecipeStep implements Parcelable
{
    @PrimaryKey(autoGenerate = true)
    private int roomStepId;
    @SerializedName("id")
    @Expose
    private Integer id;
  @ColumnInfo(name = "short_description")
    @SerializedName("shortDescription")
    @Expose
    private String shortDescription;
    @SerializedName("description")
    @Expose
    private String description;
  @ColumnInfo(name = "video_url")
    @SerializedName("videoURL")
    @Expose
    private String videoURL;
  @ColumnInfo(name = "thumbnail_url")
    @SerializedName("thumbnailURL")
    @Expose
    private String thumbnailURL;
  private int recipeId;




    /**
     *
     * @param id
     * @param shortDescription
     * @param description
     * @param videoURL
     * @param thumbnailURL
     */
    public RecipeStep(Integer id, String shortDescription, String description, String videoURL, String thumbnailURL) {

        this.id = id;
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
    public int getRoomRecipeStepId() {
        return roomStepId;
    }

    public void setRoomRecipeStepId(int roomRecipeStepId) {
        this.roomStepId = roomRecipeStepId;
    }


    public Integer getId() {
        return id;
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


    public String getDescription() {
        return description;
    }



    public String getVideoURL() {
        return videoURL;
    }



    public String getThumbnailURL() {
        return thumbnailURL;
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