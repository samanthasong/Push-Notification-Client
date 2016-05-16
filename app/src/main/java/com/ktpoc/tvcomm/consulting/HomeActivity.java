package com.ktpoc.tvcomm.consulting;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.widget.Toast;

public class HomeActivity extends Activity {

    public WebView mWebView;
    private final String _TAG ="[HOME ACTIVITY]";

   // private final String _url = "https://amuzlab.iptime.org:3000/users/asdf";
    //private final String _url = "https://172.30.1.58:3000/consulting/amuzlab";
    private final String _url = "https://tvcomm.dlinkddns.com:3000/users/expert_category_22?roomId=rjw0asgrrg";

    ConsultingClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewManager.getInstance().addActivity(this);
        ViewManager.getInstance().printActivityListSofar();

        //String server_url = getResources().getString(R.string.consulting_server_url);
        setContentView(R.layout.activity_webview);
        mWebView = (WebView)findViewById(R.id.web_view);

    }

    @Override
    protected void onResume(){
        super.onResume();
//        Bundle extras = getIntent().getExtras();
//        if(extras!=null){
//
//
//        }
        Toast toast = Toast.makeText(this, _TAG, Toast.LENGTH_SHORT);
        toast.show();

        client = new ConsultingClient(_url);
        client.setWebviewSettings(mWebView);
        String currentUserInput = null;


        //client.bridgeUserEventToJS(currentUserInput, mWebView);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                this.finish();
                Log.d(_TAG, "BACK KEY PRESSED");

                break;
            default:
                break;
        }
        return false;
    }

    /*
   [USAGE -  Web Application에서의 Android's function call usage]
   - window.consultingAndroid.함수();
   - 이 떄 함수는 AndroidBridge 클래스 내 멤버메소드로용~
*/


    /* Android call the JS's function */

}
