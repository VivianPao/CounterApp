package com.example.counter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText count_text;
    private ImageButton incr_btn;
    private ImageButton decr_btn;
    private int count;

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
        count_text = (EditText) findViewById(R.id.count_text);
        count_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId){
                    case EditorInfo.IME_ACTION_DONE:
                        if (v.getText().length() > 0) {
                            count = Integer.parseInt(v.getText().toString());
                            updateCountText();
                            return true;
                        }
                }
                return false;
            }
        });

        incr_btn = (ImageButton) findViewById(R.id.incr_btn);
        incr_btn.setOnClickListener(clickListener);
        decr_btn = (ImageButton) findViewById(R.id.decr_btn);
        decr_btn.setOnClickListener(clickListener);

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

    private void updateCountText(){
        count_text.setText(String.valueOf(count));
    }
}