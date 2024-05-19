// Generated by view binder compiler. Do not edit!
package com.example.iu_mse_app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.iu_mse_app.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentHistoryBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ProgressBar historyProgressLoader;

  @NonNull
  public final TextView historyTextView;

  private FragmentHistoryBinding(@NonNull ConstraintLayout rootView,
      @NonNull ProgressBar historyProgressLoader, @NonNull TextView historyTextView) {
    this.rootView = rootView;
    this.historyProgressLoader = historyProgressLoader;
    this.historyTextView = historyTextView;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentHistoryBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentHistoryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_history, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentHistoryBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.historyProgressLoader;
      ProgressBar historyProgressLoader = ViewBindings.findChildViewById(rootView, id);
      if (historyProgressLoader == null) {
        break missingId;
      }

      id = R.id.historyTextView;
      TextView historyTextView = ViewBindings.findChildViewById(rootView, id);
      if (historyTextView == null) {
        break missingId;
      }

      return new FragmentHistoryBinding((ConstraintLayout) rootView, historyProgressLoader,
          historyTextView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}