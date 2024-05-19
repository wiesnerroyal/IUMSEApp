// Generated by view binder compiler. Do not edit!
package com.example.iu_mse_app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.iu_mse_app.R;
import com.google.android.material.button.MaterialButton;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentVoteBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final GridLayout voteGrid;

  @NonNull
  public final ProgressBar voteProgressLoader;

  @NonNull
  public final Spinner voteSpinner;

  @NonNull
  public final MaterialButton voteSubmitButton;

  @NonNull
  public final TextView voteTextView;

  private FragmentVoteBinding(@NonNull FrameLayout rootView, @NonNull GridLayout voteGrid,
      @NonNull ProgressBar voteProgressLoader, @NonNull Spinner voteSpinner,
      @NonNull MaterialButton voteSubmitButton, @NonNull TextView voteTextView) {
    this.rootView = rootView;
    this.voteGrid = voteGrid;
    this.voteProgressLoader = voteProgressLoader;
    this.voteSpinner = voteSpinner;
    this.voteSubmitButton = voteSubmitButton;
    this.voteTextView = voteTextView;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentVoteBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentVoteBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_vote, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentVoteBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.voteGrid;
      GridLayout voteGrid = ViewBindings.findChildViewById(rootView, id);
      if (voteGrid == null) {
        break missingId;
      }

      id = R.id.voteProgressLoader;
      ProgressBar voteProgressLoader = ViewBindings.findChildViewById(rootView, id);
      if (voteProgressLoader == null) {
        break missingId;
      }

      id = R.id.voteSpinner;
      Spinner voteSpinner = ViewBindings.findChildViewById(rootView, id);
      if (voteSpinner == null) {
        break missingId;
      }

      id = R.id.voteSubmitButton;
      MaterialButton voteSubmitButton = ViewBindings.findChildViewById(rootView, id);
      if (voteSubmitButton == null) {
        break missingId;
      }

      id = R.id.voteTextView;
      TextView voteTextView = ViewBindings.findChildViewById(rootView, id);
      if (voteTextView == null) {
        break missingId;
      }

      return new FragmentVoteBinding((FrameLayout) rootView, voteGrid, voteProgressLoader,
          voteSpinner, voteSubmitButton, voteTextView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}