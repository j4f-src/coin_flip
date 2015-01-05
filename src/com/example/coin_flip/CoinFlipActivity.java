package com.example.coin_flip;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class CoinFlipActivity extends Activity implements FragmentManager.OnBackStackChangedListener {
    private boolean mShowingBack;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        getFragmentManager()
            .beginTransaction()
            .add(R.id.container, new CardFrontFragment())
            .commit();

        FrameLayout mainLayout = (FrameLayout)findViewById(R.id.container);
        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipCard();
            }
        });

        getFragmentManager().addOnBackStackChangedListener(this);
    }

    private void flipCard() {
        if (mShowingBack) {
            getFragmentManager().popBackStack();
            return;
        }

        getFragmentManager()
            .beginTransaction()
            .setCustomAnimations(
                R.animator.card_flip_left_in, R.animator.card_flip_left_out,
                R.animator.card_flip_left_in, R.animator.card_flip_left_out
            )
            .replace(R.id.container, new CardBackFragment())
            .addToBackStack(null)
            .commit();
    }

    @Override
    public void onBackStackChanged() {
        mShowingBack = getFragmentManager().getBackStackEntryCount() > 0;
    }

    /**
     * 表側のフラグメントクラス
     */
    public static class CardFrontFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_card_front, container, false);
        }
    }

    /**
     * 裏側のフラグメントクラス
     */
    public static class CardBackFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_card_back, container, false);
        }
    }
}
