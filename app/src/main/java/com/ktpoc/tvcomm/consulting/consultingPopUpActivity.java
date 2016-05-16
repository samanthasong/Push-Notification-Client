package com.ktpoc.tvcomm.consulting;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ConsultingPopUpActivity extends Activity implements View.OnClickListener{

    private Button mConfirmBtn, mCancelBtn;
    private TextView mTextView;
    private String mMessage;

    private final String _TAG = "[SONG-POPUP ACTIVITY]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulting_pop_up);

        ViewManager.getInstance().addActivity(this);
        ViewManager.getInstance().printActivityListSofar();

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            mMessage = extra.getString("message");
        }
        setPopUpContent();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void setPopUpContent(){
        mConfirmBtn = (Button)findViewById(R.id.confirm_button);
        mCancelBtn = (Button)findViewById(R.id.cancel_button);
        mTextView = (TextView)findViewById(R.id.text_view);

        mTextView.setText(mMessage);

        mConfirmBtn.setOnClickListener(this);
        mCancelBtn.setOnClickListener(this);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.confirm_button:

                //TODO: url passing

                Toast toast = Toast.makeText(this, "URL RECEIVED --> " + mMessage, Toast.LENGTH_SHORT);
                toast.show();

                //moveTaskToBack(true);
                this.finish();
                break;
            case R.id.cancel_button:
                Log.d(_TAG, "cancel button pressed");
                //moveTaskToBack(true);
                this.finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onPause(){
        //ViewManager.getInstance().removeActivity(this);
        Log.d(_TAG, "onPause called");
        super.onPause();

    }

    @Override
    protected void onStop(){
        //ViewManager.getInstance().removeActivity(this);
        Log.d(_TAG, "onStop called");
        super.onStop();

    }

}
