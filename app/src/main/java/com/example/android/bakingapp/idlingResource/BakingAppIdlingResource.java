package com.example.android.bakingapp.idlingResource;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;
import java.util.concurrent.atomic.AtomicBoolean;

public class BakingAppIdlingResource implements IdlingResource {

  @Nullable
  private volatile ResourceCallback callback;
  //Idleness is controlled with this boolean
  private final AtomicBoolean mIsIdleNow = new AtomicBoolean(true);

  @Override
  public String getName() {
    return this.getClass().getName();
  }

  @Override
  public boolean isIdleNow() {
    return mIsIdleNow.get();
  }

  @Override
  public void registerIdleTransitionCallback(ResourceCallback callback) {
    this.callback = callback;
  }

  /**
   * Sets the new idle state, if isIdleNow is true, it pings the {@link ResourceCallback}.
   *
   * @param isIdleNow false if there are pending operations, true if idle.
   */
  public void setIdleState(boolean isIdleNow) {
    mIsIdleNow.set(isIdleNow);
    if (isIdleNow && callback != null) {
      callback.onTransitionToIdle();
    }
  }
}
