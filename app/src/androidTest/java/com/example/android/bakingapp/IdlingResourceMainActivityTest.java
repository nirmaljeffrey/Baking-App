package com.example.android.bakingapp;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;
import com.example.android.bakingapp.userInterface.activities.MainActivity;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class IdlingResourceMainActivityTest {

  @Rule
  public final ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(
      MainActivity.class);
  private IdlingResource idlingResource;

  @Before
  public void registerIdlingResource() {
    idlingResource = mainActivityActivityTestRule.getActivity().getIdlingResource();
    IdlingRegistry.getInstance().register(idlingResource);
  }

  @Test
  public void idlingResourceTest() {
    onView(withId(R.id.recipe_list_recycler_view))
        .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

    onView(allOf(instanceOf(TextView.class),
        withParent(withResourceName("action_bar"))))
        .check(matches(withText("Nutella Pie")));
  }

  @After
  public void unRegisterIdlingResource() {
    if (idlingResource != null) {
      IdlingRegistry.getInstance().unregister(idlingResource);
    }

  }
}
