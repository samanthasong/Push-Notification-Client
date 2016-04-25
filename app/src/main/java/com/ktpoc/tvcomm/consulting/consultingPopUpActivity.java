package com.ktpoc.tvcomm.consulting;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class consultingPopUpActivity extends Activity implements View.OnClickListener{

    private Button mConfirmBtn, mCancelBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulting_pop_up);

        setPopUpContent();

    }

    private void setPopUpContent(){
        mConfirmBtn = (Button)findViewById(R.id.confirm_button);
        mCancelBtn = (Button)findViewById(R.id.cancel_button);

        mConfirmBtn.setOnClickListener(this);
        mCancelBtn.setOnClickListener(this);
    }

    public void onClick(View v){
        //
    }
}
