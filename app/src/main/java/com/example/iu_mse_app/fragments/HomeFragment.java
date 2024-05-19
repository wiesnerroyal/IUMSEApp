package com.example.iu_mse_app.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.iu_mse_app.R;
import com.example.iu_mse_app.activities.MainActivity;

public class HomeFragment extends Fragment {


    public static boolean isActive = false;

    private CardView card1;
    private CardView card2;
    private CardView card3;
    private CardView card4;
    private CardView card5;
    private Activity parentActivity;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        parentActivity = (Activity) context;
    }

    @Override
    public void onStart() {
        super.onStart();

        isActive = true;

        card1 = requireView().findViewById(R.id.card1);
        card1.setOnClickListener(v -> card1onClick());

        card2 = requireView().findViewById(R.id.card2);
        card2.setOnClickListener(v -> card2onClick());

        card3 = requireView().findViewById(R.id.card3);
        card3.setOnClickListener(v -> card3nClick());

        card4 = requireView().findViewById(R.id.card4);
        card4.setOnClickListener(v -> card4nClick());

        card5 = requireView().findViewById(R.id.card5);
        card5.setOnClickListener(v -> card5nClick());

    }

    @Override
    public void onStop(){
        super.onStop();
        isActive = false;
    }

    private void card1onClick(){
        MainActivity.activeFragment = 1;
        animateButtonAndChangeFragment(card1, new VoteFragment());
    }

    private void card2onClick(){
        MainActivity.activeFragment = 2;
        animateButtonAndChangeFragment(card2, new NewsFragment());
    }

    private void card3nClick(){
        MainActivity.activeFragment = 3;
        animateButtonAndChangeFragment(card3, new ChatFragment());
    }

    private void card4nClick(){
        MainActivity.activeFragment = 4;
        animateButtonAndChangeFragment(card4, new HistoryFragment());
    }

    private void card5nClick(){
        MainActivity.activeFragment = 5;
        animateButtonAndChangeFragment(card5, new InfoFragment());
    }

    private void animateButtonAndChangeFragment(CardView card, Fragment fragment){
        Animation animation = AnimationUtils.loadAnimation(
                getActivity(),
                R.anim.card_click_animation
        );
        card.startAnimation(animation);

        card.postDelayed(() -> {
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.mainFragmentContainer, fragment);
            fragmentTransaction.commit();
            TextView toolbarTextView = parentActivity.findViewById(R.id.mainToolbarTextView);
            toolbarTextView.setText(getString(R.string.tb_home));
        }, 200);
    }
}