package com.ktpoc.tvcomm.consulting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConsultingPopUpActivity extends Activity implements View.OnClickListener{

    private Button mConfirmBtn, mCancelBtn;
    private TextView mTextView;
    private String mMessage;

    private final String _TAG = "[SONG-POPUP ACTIVITY]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulting_pop_up);

        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            setPopUpContent(extras);
            Log.d(_TAG, "extras are not null, setContent start");
        }else {
            Log.d(_TAG, "No extras received to pop up activity");
        }
    }

    private void setPopUpContent(Bundle extras){
        mConfirmBtn = (Button)findViewById(R.id.confirm_button);
        mCancelBtn = (Button)findViewById(R.id.cancel_button);
        mTextView = (TextView)findViewById(R.id.text_view);

        mMessage = extras.getString("receivedMsg");
        mTextView.setVisibility(View.VISIBLE);
        mTextView.setText(mMessage);

        mConfirmBtn.setOnClickListener(this);
        mCancelBtn.setOnClickListener(this);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.confirm_button:
                Intent i = new Intent(ConsultingPopUpActivity.this, com.ktpoc.tvcomm.consulting.MainActivity.class);
                //i.putExtra("url", "https://amuzlab.iptime.org:3000/users/amuzlab");
                i.putExtra("url", "http://www.google.co.kr");
                i.setFlags(getIntent().FLAG_ACTIVITY_NEW_TASK);
                Log.d(_TAG, "URL sent to Main Activity");
                this.finish();
                startActivity(i);
               // this.finish();
                break;
            case R.id.cancel_button:
                Log.d(_TAG, "cancel button pressed");
                this.finish();
                break;
            default:
                break;
        }
    }
}
