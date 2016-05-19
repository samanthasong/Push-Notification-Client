package com.ktpoc.tvcomm.consulting;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.widget.Toast;

import java.util.Properties;

public class HomeActivity extends Activity {

    public WebView mWebView;
    private final String _TAG ="[HOME ACTIVITY]";
    private boolean isDlgShownBefore = false;

    //private final String _url = "https://amuzlab.iptime.org:3000/users/asdf";
    //private final String _url = "https://172.30.1.58:3000/consulting/amuzlab";
    //private final String _url = "https://tvcomm.dlinkddns.com:3000/users/expert_category_22?roomId=rjw0asgrrg";
    private final String _url = "https://amuzlab.iptime.org:3000/users/expert_category22?roomId=gaolf7rhe8";

    private String server_url;
    private ConsultingClient client;
    private String deviceAuthResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewManager.getInstance().addActivity(this);
        ViewManager.getInstance().printActivityListSofar();

        //String server_url = getResources().getString(R.string.consulting_server_url);
        setContentView(R.layout.activity_webview);

        client = new ConsultingClient(_url);
        mWebView = (WebView)findViewById(R.id.web_view);
        client.setWebviewSettings(mWebView);
    }

    @Override
    protected void onResume(){
        super.onResume();

        //다이얼로그 종료되면 다시 온리쥼오나ㅎㅎㅎ
        Toast toast = Toast.makeText(this, _TAG, Toast.LENGTH_SHORT);
        toast.show();

        String currentUserInput = null;

        if(isDlgShownBefore == false){
            deviceAuthResult = checkRegistration();
            switch (deviceAuthResult) {
                case "true": // Customer already registered once before
                    Log.d(_TAG, "deviceAuthSelect result is -->" + deviceAuthResult);
                    break;

                case "false": // Customer never registered
                    Log.d(_TAG, "deviceAuthSelect result is -->" + deviceAuthResult);
                    registerDialog();
                    isDlgShownBefore = true;
                    break;

                case "error":
                    Log.d(_TAG, "deviceAuthSelect result is -->" + deviceAuthResult);
                    break;

                default:
                    break;
            }
        }else { //stay

        }
        //client.bridgeUserEventToJS(currentUserInput, mWebView);
    }

    private String checkRegistration(){
        String result;

        server_url  = getResources().getString(R.string.consulting_server_url);
        AndroidHttp httpsClient = new AndroidHttp(server_url);

        Properties prop = new Properties();
        final String registrationId = ((TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        prop.setProperty("registerid", registrationId);

        result = httpsClient.postCMS("deviceAuthSelect?", prop, true);
        return result;
    }

    private void registerDialog(){
        final AlertDialog.Builder dlgBuilder = new AlertDialog.Builder(this);
        dlgBuilder.setMessage("GiGA Genie 컨설팅 서비스에 가입하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(HomeActivity.this, UserRegisterActivity.class);
                        startActivity(intent);
                    }
                }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = dlgBuilder.create();
        alertDialog.setTitle("GiGA Genie 컨설팅 서비스");
        alertDialog.show();
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
