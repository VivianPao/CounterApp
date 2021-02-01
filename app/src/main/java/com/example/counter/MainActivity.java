package com.example.counter;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class MainActivity extends AppCompatActivity {

    private EditText count_text;
    private ImageButton incr_btn;
    private ImageButton decr_btn;
    private LinearLayout base;
    private int count;
    private int colorIndex;
    private int[] bgColors;

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.incr_btn:
                    incrCount();
                    break;
                case R.id.decr_btn:
                    decrCount();
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bgColors = getResources().getIntArray(R.array.bgColors);    // Getting color array from XML
        colorIndex = 0; // Default value

        count_text = (EditText) findViewById(R.id.count_text);
        count_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_DONE:
                        // If there is typed text that isn't just a single negative sign, save the number as the count
                        if (v.getText().length() > 0 && (!v.getText().toString().equals("-"))) {
                            count = Integer.parseInt(v.getText().toString());
                        } else {
                            count = 0;
                        }
                        updateCountText();

                        // Hide the soft numerical keyboard
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);

                        count_text.clearFocus();

                        return true;
                }
                return false;
            }
        });

        // Getting all important features by ID so we can make changes
        incr_btn = (ImageButton) findViewById(R.id.incr_btn);
        incr_btn.setOnClickListener(clickListener);
        decr_btn = (ImageButton) findViewById(R.id.decr_btn);
        decr_btn.setOnClickListener(clickListener);
        base = (LinearLayout) findViewById(R.id.base);

        base.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            public void onSwipeRight() {
                colorIndex++;
                updateColors();
            }
            public void onSwipeLeft() {
                colorIndex--;
                updateColors();
            }
        });


        initCount();
    }

    private void initCount() {
        count = 0;
        updateCountText();
    }

    private void incrCount() {
        count++;
        updateCountText();
    }

    private void decrCount() {
        count--;
        updateCountText();
    }

    private void updateCountText() {
        count_text.setText(String.valueOf(count));
    }

    private void updateColors() {
        // Ensuring the color index is always positive using multiples of 3 to retain directional color switching.
        while (colorIndex < 0) {
            colorIndex = colorIndex + bgColors.length;
        }
        colorIndex = Math.abs(colorIndex % bgColors.length);  // Modulus to loop to beginning

        int currColor = bgColors[colorIndex];   // Selecting and setting the color for all features on-screen.
        incr_btn.setBackgroundColor(currColor);
        decr_btn.setBackgroundColor(currColor);
        base.setBackgroundColor(currColor);
    }

}


//public class OnSwipeTouchListener implements OnTouchListener {
//
//    private final GestureDetector gestureDetector;
//
//    public OnSwipeTouchListener(Context context) {
//        gestureDetector = new GestureDetector(context, new GestureListener());
//    }
//
//    public void onSwipeLeft() {
//    }
//
//    public void onSwipeRight() {
//    }
//
//    public boolean onTouch(View v, MotionEvent event) {
//        return gestureDetector.onTouchEvent(event);
//    }
//
//    private final class GestureListener extends SimpleOnGestureListener {
//
//        private static final int SWIPE_DISTANCE_THRESHOLD = 100;
//        private static final int SWIPE_VELOCITY_THRESHOLD = 100;
//
//        @Override
//        public boolean onDown(MotionEvent e) {
//            return true;
//        }
//
//        @Override
//        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            float distanceX = e2.getX() - e1.getX();
//            float distanceY = e2.getY() - e1.getY();
//            if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
//                if (distanceX > 0)
//                    onSwipeRight();
//                else
//                    onSwipeLeft();
//                return true;
//            }
//            return false;
//        }
//    }
//}