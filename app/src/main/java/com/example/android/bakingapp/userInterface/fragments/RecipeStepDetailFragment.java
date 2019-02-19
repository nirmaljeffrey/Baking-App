package com.example.android.bakingapp.userInterface.fragments;

import android.content.Context;


import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;


import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.Constants;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.RecipeStep;

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


    private RecipeStep mRecipeStep;
    private PlayerView playerView;
    private ImageView thumbnailImage;
    private TextView descriptionTextView;
    private SimpleExoPlayer simpleExoPlayer;
    private ArrayList<RecipeStep> recipeStepArrayList;
    private OnRecipeStepDetailFragmentItemClickListener clickListener;
    private boolean playWhenReady=true;
    private long playBackPosition=-1;
    private ImageView noVideoImageView;
    private Button nextButton;
    private Button previousButton;
    private FrameLayout frameLayout;
    private CardView cardView;




    public interface OnRecipeStepDetailFragmentItemClickListener{
        void previousButtonClicked(int previousStepId);
        void nextButtonClicked(int nextStepId);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            clickListener=(OnRecipeStepDetailFragmentItemClickListener) context;
        }catch (RuntimeException e){
            throw new RuntimeException("Implement the OnRecipeStepDetailFragmentClickListener");
        }
    }




  @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      if(savedInstanceState!=null){
      if(savedInstanceState.containsKey(Constants.RECIPE_STEP_OBJECT_KEY)&&
          savedInstanceState.containsKey(Constants.RECIPE_STEP_ARRAY_LIST_FRAGMENT_KEY))
        {
          mRecipeStep = savedInstanceState.getParcelable(Constants.RECIPE_STEP_OBJECT_KEY);
          recipeStepArrayList = savedInstanceState.getParcelableArrayList(Constants.RECIPE_STEP_ARRAY_LIST_FRAGMENT_KEY);
        }
        if(savedInstanceState.containsKey(Constants.PLAY_WHEN_READY_READY_KEY) &&
            savedInstanceState.containsKey(Constants.PLAY_BACK_POSITION_KEY) &&
            savedInstanceState.containsKey(Constants.PLAY_BACK_POSITION_KEY)){
            playWhenReady=savedInstanceState.getBoolean(Constants.PLAY_WHEN_READY_READY_KEY);
            playBackPosition=savedInstanceState.getLong(Constants.PLAY_BACK_POSITION_KEY);
        }
      }

        View view = inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);

          initiateViews(view);
          initiateButtons(view);

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

    private void initiateViews(View view){

      cardView=view.findViewById(R.id.card_view_for_exoplayer);
      frameLayout=view.findViewById(R.id.frame_layout_for_player);
      noVideoImageView = view.findViewById(R.id.no_video_image_view);
      playerView = view.findViewById(R.id.playerView);
       thumbnailImage = view.findViewById(R.id.thumbnail_imageView);
       descriptionTextView = view.findViewById(R.id.description_text_view);


    }

  @Override
  public void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    if(mRecipeStep!=null && recipeStepArrayList !=null) {
      outState.putParcelableArrayList(Constants.RECIPE_STEP_ARRAY_LIST_FRAGMENT_KEY,
          recipeStepArrayList);
      outState.putParcelable(Constants.RECIPE_STEP_OBJECT_KEY, mRecipeStep);
    }
    if(simpleExoPlayer!=null){
      outState.putLong(Constants.PLAY_BACK_POSITION_KEY,simpleExoPlayer.getCurrentPosition());
      outState.putInt(Constants.WINDOW_INDEX_KEY,simpleExoPlayer.getCurrentWindowIndex());
      outState.putBoolean(Constants.PLAY_WHEN_READY_READY_KEY,simpleExoPlayer.getPlayWhenReady());
    }
  }

  private void initiateButtons(View view){
         nextButton = view.findViewById(R.id.next_button);
         previousButton = view.findViewById(R.id.previous_button);


        if(mRecipeStep!=null) {
            if(mRecipeStep.getId()==0)   previousButton.setVisibility(View.INVISIBLE);;
            if(mRecipeStep.getId()==recipeStepArrayList.size()-1) nextButton.setVisibility(View.INVISIBLE);


            nextButton.setOnClickListener(view12 -> clickListener.nextButtonClicked(mRecipeStep.getId()));
            previousButton.setOnClickListener(view1 -> clickListener.previousButtonClicked(mRecipeStep.getId()));
        }
    }
    private void initializePlayer(Context context, Uri mediaUri){
        if(simpleExoPlayer==null) {
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
          simpleExoPlayer.setAudioAttributes(audioAttributes,true);
          String userAgent = Util.getUserAgent(context, context.getString(R.string.app_name));
          MediaSource mediaSource = new ExtractorMediaSource.Factory(new DefaultDataSourceFactory(context, userAgent)).createMediaSource(mediaUri);
          simpleExoPlayer.prepare(mediaSource);

        }




    }

  @Override
  public void onResume() {
    super.onResume();
    if(simpleExoPlayer!=null) {
      if (playBackPosition != -1)
        simpleExoPlayer.seekTo(playBackPosition);
    }
  }

  @Override
    public void onDestroyView() {
        super.onDestroyView();

          releasePlayer();


    }
    /**
  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);

    ActionBar actionBar = ((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar();;


    if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE) {

      if (actionBar != null) {
        actionBar.hide();
      }
      hideViewsForLandscapeMode();


      ConstraintLayout.LayoutParams playerParams = (ConstraintLayout.LayoutParams) frameLayout.getLayoutParams();
      playerParams.width = LayoutParams.MATCH_PARENT;
      playerParams.height = LayoutParams.MATCH_PARENT;
      frameLayout.setLayoutParams(playerParams);
    }
    else if(newConfig.orientation==Configuration.ORIENTATION_PORTRAIT){
      if(actionBar!=null) {
        actionBar.show();
      }
      showViewsForPortraitMode();

      ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) frameLayout.getLayoutParams();
      params.width=0;
      params.height=0;
      frameLayout.setLayoutParams(params);
    }
  }
  private void hideViewsForLandscapeMode(){
    previousButton.setVisibility(View.GONE);
    nextButton.setVisibility(View.GONE);
    cardView.setVisibility(View.GONE);

  }
  private void showViewsForPortraitMode(){
    previousButton.setVisibility(View.VISIBLE);
    nextButton.setVisibility(View.VISIBLE);
    cardView.setVisibility(View.VISIBLE);

  }**/



  private void releasePlayer(){
      if(simpleExoPlayer!=null) {
        simpleExoPlayer.stop();
        simpleExoPlayer.release();
        simpleExoPlayer = null;
      }
    }


    private void setThumbnailImage(String thumbnailUrlString,ImageView thumbnailImage){
        if(isImageFile(thumbnailUrlString)){

            Picasso.get().load(thumbnailUrlString).fit().into(thumbnailImage);
        }else {

            thumbnailImage.setImageResource(R.drawable.thumbnail);
        }

    }
    /**
     * Method to check whether the given thumbnail url is  a valid image
     *(https://stackoverflow.com/questions/17618118/check-if-a-file-is-an-image-or-a-video)
     */

    private boolean isImageFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("image");

    }
    public void setRecipeStep(RecipeStep recipeStep) {
        this.mRecipeStep = recipeStep;
    }
    public void setRecipeStepArrayList(ArrayList<RecipeStep> arrayList){
        this.recipeStepArrayList=arrayList;
    }






}
