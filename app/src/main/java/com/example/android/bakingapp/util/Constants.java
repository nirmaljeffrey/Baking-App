package com.example.android.bakingapp.util;

public final class Constants {

  //----- Intent extra Constants -------//

  //Intent extra constant used in main_activity and detail_activity
  public static final String RECIPE_INTENT_EXTRA_CONSTANT = "recipe_item";
  //Intent extra constant used in detail_activity and ingredient_activity
  public static final String INGREDIENT_LIST_INTENT_EXTRA_CONSTANT = "ingredient_list";
  //Intent extra constant used in detail_Activity and recipe_step_master_activity
  public static final String RECIPE_STEP_LIST_INTENT_EXTRA_CONSTANT = "recipe_step_list";
  //Intent extra constant used in recipe_Step_master_activity and recipe_step_detail_activity
  public static final String RECIPE_STEP_MASTER_TO_DETAIL_LIST_INTENT = "recipe_step_master_to_detail_LIST";
  //Intent extra constant used in recipe_Step_master_activity and recipe_step_detail_activity
  public static final String RECIPE_STEP_MASTER_TO_DETAIL_OBJECT_INTENT = "recipe_step_master_to_detail_object";


  //--- Constant used in Recipe_step_detail_activity ---//
  //Constant for saving the fragment's state during configuration changes
  public static final String RECIPE_STEP_DETAIL_FRAGMENT = "my_fragment";


  //--- Constants used in Recipe_step_detail_fragment ---//
  //Constants for saving exoPlayer states to survive configuration changes.
  public static final String PLAY_WHEN_READY_READY_KEY = "play_when_ready";
  public static final String PLAY_BACK_POSITION_KEY = "play_back_position";
  public static final String WINDOW_INDEX_KEY = "window_index";
  //Constants for saving the data to survive configuration changes.
  public static final String RECIPE_STEP_OBJECT_KEY = "recipe_step";
  public static final String RECIPE_STEP_ARRAY_LIST_FRAGMENT_KEY = "recipe_step_array_list_fragment";

}
