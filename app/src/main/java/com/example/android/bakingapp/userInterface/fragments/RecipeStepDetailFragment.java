package com.example.android.bakingapp.userInterface.fragments;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.android.bakingapp.util.Constants;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.RecipeStep;
import com.example.android.bakingapp.userInterface.activities.RecipeStepDetailActivity;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;
import java.net.URLConnection;
import java.util.ArrayList;

public class RecipeStepDetailFragment extends Fragment {

  private static final String BUTTON_VISIBILITY = "visibility";
  private static final String ARRAY_LIST_BUNDLE = "recipe_step_array_list_bundle";
  private static final String RECIPE_STEP_BUNDLE = "recipe_Step_bundle";
  private RecipeStep mRecipeStep;
  private PlayerView playerView;
  private ImageView thumbnailImage;
  private TextView descriptionTextView;
  private SimpleExoPlayer simpleExoPlayer;
  private ArrayList<RecipeStep> recipeStepArrayList;
  private OnRecipeStepDetailFragmentItemClickListener clickListener;
  private boolean playWhenReady = true;
  private long playBackPosition = -1;
  private ImageView noVideoImageView;
  private boolean buttonsInvisible;
  private Button nextButton;
  private Button previousButton;

  /**
   * Method used for creating instances of RecipeStepDetailFragment
   *
   * @param recipeStepArrayList arguments for fragments
   * @param recipeStep arguments for fragments
   * @param setButtonsInvisible boolean used for checking whether to display next and previous
   * buttons in fragment or not.
   * @return fragment instance
   */
  public static RecipeStepDetailFragment getDetailFragmentInstance(
      ArrayList<RecipeStep> recipeStepArrayList, RecipeStep recipeStep,
      boolean setButtonsInvisible) {
    RecipeStepDetailFragment detailFragment = new RecipeStepDetailFragment();
    Bundle bundle = new Bundle();
    bundle.putParcelableArrayList(ARRAY_LIST_BUNDLE, recipeStepArrayList);
    bundle.putParcelable(RECIPE_STEP_BUNDLE, recipeStep);
    bundle.putBoolean(BUTTON_VISIBILITY, setButtonsInvisible);
    detailFragment.setArguments(bundle);

    return detailFragment;
  }


  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (getActivity() instanceof RecipeStepDetailActivity) {
      try {
        clickListener = (OnRecipeStepDetailFragmentItemClickListener) context;
      } catch (RuntimeException e) {
        throw new RuntimeException("Implement the OnRecipeStepDetailFragmentClickListener");
      }
    }
  }


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    if (getArguments() != null) {
      mRecipeStep = getArguments().getParcelable(RECIPE_STEP_BUNDLE);
      recipeStepArrayList = getArguments().getParcelableArrayList(ARRAY_LIST_BUNDLE);
      buttonsInvisible = getArguments().getBoolean(BUTTON_VISIBILITY);

    }
    if (savedInstanceState != null) {
      if (savedInstanceState.containsKey(Constants.RECIPE_STEP_OBJECT_KEY) &&
          savedInstanceState.containsKey(Constants.RECIPE_STEP_ARRAY_LIST_FRAGMENT_KEY)) {
        mRecipeStep = savedInstanceState.getParcelable(Constants.RECIPE_STEP_OBJECT_KEY);
        recipeStepArrayList = savedInstanceState
            .getParcelableArrayList(Constants.RECIPE_STEP_ARRAY_LIST_FRAGMENT_KEY);
      }
      if (savedInstanceState.containsKey(Constants.PLAY_WHEN_READY_READY_KEY) &&
          savedInstanceState.containsKey(Constants.PLAY_BACK_POSITION_KEY) &&
          savedInstanceState.containsKey(Constants.PLAY_BACK_POSITION_KEY)) {
        playWhenReady = savedInstanceState.getBoolean(Constants.PLAY_WHEN_READY_READY_KEY);
        playBackPosition = savedInstanceState.getLong(Constants.PLAY_BACK_POSITION_KEY);
      }
    }

    View view = inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);

    initiateViews(view);
    if (!buttonsInvisible) {
      initiateButtons();
    } else {
      setButtonsInvisible();
    }

    descriptionTextView.setText(mRecipeStep.getDescription());
    String videoUrlString = mRecipeStep.getVideoURL();
    String thumbnailUrlString = mRecipeStep.getThumbnailURL();
    setThumbnailImage(thumbnailUrlString, thumbnailImage);
    if (videoUrlString.isEmpty()) {
      playerView.setVisibility(View.GONE);
      noVideoImageView.setVisibility(View.VISIBLE);
    } else {
      noVideoImageView.setVisibility(View.GONE);
      Uri mediaUri = Uri.parse(videoUrlString);
      initializePlayer(getActivity(), mediaUri);
    }

    return view;
  }

  /**
   * Method for initiating views in the fragment
   *
   * @param view used for finding views from the layout file
   */
  private void initiateViews(View view) {
    nextButton = view.findViewById(R.id.next_button);
    previousButton = view.findViewById(R.id.previous_button);
    noVideoImageView = view.findViewById(R.id.no_video_image_view);
    playerView = view.findViewById(R.id.playerView);
    thumbnailImage = view.findViewById(R.id.thumbnail_imageView);
    descriptionTextView = view.findViewById(R.id.description_text_view);


  }

  @Override
  public void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    if (mRecipeStep != null && recipeStepArrayList != null) {
      outState.putParcelableArrayList(Constants.RECIPE_STEP_ARRAY_LIST_FRAGMENT_KEY,
          recipeStepArrayList);
      outState.putParcelable(Constants.RECIPE_STEP_OBJECT_KEY, mRecipeStep);
    }
    if (simpleExoPlayer != null) {
      outState.putLong(Constants.PLAY_BACK_POSITION_KEY, simpleExoPlayer.getCurrentPosition());
      outState.putInt(Constants.WINDOW_INDEX_KEY, simpleExoPlayer.getCurrentWindowIndex());
      outState.putBoolean(Constants.PLAY_WHEN_READY_READY_KEY, simpleExoPlayer.getPlayWhenReady());
    }
  }

  /**
   * Method used for initiating next and previous buttons in fragment
   */
  private void initiateButtons() {

    if (mRecipeStep != null) {
      if (mRecipeStep.getId() == 0) {
        previousButton.setVisibility(View.INVISIBLE);
      }

      if (mRecipeStep.getId() == recipeStepArrayList.size() - 1) {
        nextButton.setVisibility(View.INVISIBLE);
      }

      nextButton.setOnClickListener(view12 -> clickListener.nextButtonClicked(mRecipeStep.getId()));
      previousButton
          .setOnClickListener(view1 -> clickListener.previousButtonClicked(mRecipeStep.getId()));
    }
  }

  /**
   * Method used for initiating the exoPlayer inside the fragment
   *
   * @param context input parameter used in creating exoPlayer
   * @param mediaUri URL link for the video to display
   */
  private void initializePlayer(Context context, Uri mediaUri) {
    if (simpleExoPlayer == null) {
      simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context);
      playerView.setPlayer(simpleExoPlayer);
      //To show progress bar when the video buffers
      playerView.setShowBuffering(PlayerView.SHOW_BUFFERING_WHEN_PLAYING);
      simpleExoPlayer.setPlayWhenReady(playWhenReady);

      // Requesting audio focus
      AudioAttributes audioAttributes = new AudioAttributes.Builder()
          .setUsage(C.USAGE_MEDIA)
          //for short videos, the notifications from other apps are stopped
          .setContentType(C.CONTENT_TYPE_SPEECH)
          .build();
      simpleExoPlayer.setAudioAttributes(audioAttributes, true);
      String userAgent = Util.getUserAgent(context, context.getString(R.string.app_name));
      MediaSource mediaSource = new ExtractorMediaSource.Factory(
          new DefaultDataSourceFactory(context, userAgent)).createMediaSource(mediaUri);
      simpleExoPlayer.prepare(mediaSource);

    }


  }

  @Override
  public void onResume() {
    super.onResume();
    if (simpleExoPlayer != null) {
      if (playBackPosition != -1) {
        simpleExoPlayer.seekTo(playBackPosition);
      }
    }
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();

    releasePlayer();


  }

  private void releasePlayer() {
    if (simpleExoPlayer != null) {
      simpleExoPlayer.stop();
      simpleExoPlayer.release();
      simpleExoPlayer = null;
    }
  }

  /**
   * Method to check if the image url is present in jsonResponse else it displays default image
   *
   * @param thumbnailUrlString URL link for the image file
   * @param thumbnailImage ImageView to display thumbnails
   */
  private void setThumbnailImage(String thumbnailUrlString, ImageView thumbnailImage) {
    if (isImageFile(thumbnailUrlString)) {

      Picasso.get().load(thumbnailUrlString).fit().into(thumbnailImage);
    } else {

      thumbnailImage.setImageResource(R.drawable.thumbnail);
    }

  }

  /**
   * Method to check whether the given thumbnail url is  a valid image
   * (https://stackoverflow.com/questions/17618118/check-if-a-file-is-an-image-or-a-video)
   */

  private boolean isImageFile(String path) {
    String mimeType = URLConnection.guessContentTypeFromName(path);
    return mimeType != null && mimeType.startsWith("image");

  }

  /**
   * Method used for hiding the next and previous buttons.
   */
  private void setButtonsInvisible() {
    nextButton.setVisibility(View.GONE);
    previousButton.setVisibility(View.GONE);
  }


  // Interface to communicate with activity
  public interface OnRecipeStepDetailFragmentItemClickListener {

    void previousButtonClicked(int previousStepId);

    void nextButtonClicked(int nextStepId);
  }


}
