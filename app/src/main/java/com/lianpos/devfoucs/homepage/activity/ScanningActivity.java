package com.lianpos.devfoucs.homepage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.lianpos.activity.R;
import com.lianpos.devfoucs.contacts.ui.AddFriendActivity;
import com.lianpos.devfoucs.shoppingcart.activity.IncreaseCommodityActivity;
import com.lianpos.qrcode.QRCodeView;
import com.lianpos.qrcode.ZXingView;
import com.lianpos.zxing.android.CaptureActivity;

/**
 * 条形码二维码扫描
 * Created by wangshuai on 2017/11/18.
 */
public class ScanningActivity extends AppCompatActivity implements QRCodeView.Delegate {

    private ZXingView zXingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning);
        zXingView = (ZXingView) findViewById(R.id.zxingview);
        zXingView.setDelegate(this);//接收返回值
        zXingView.startSpotAndShowRect();//显示扫描框，并且延迟1.5秒后开始识别
    }

    @Override
    public void onScanQRCodeSuccess(String result) {//二维码返回的信息
        Log.i("zhh", "result:" + result);
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();

        Intent intent = getIntent();
        String tiaozhuan = intent.getStringExtra("commodity");
        String chaifen = intent.getStringExtra("increase");
        if (tiaozhuan != null){
            if (tiaozhuan.equals("shop")){
                intent.setClass(ScanningActivity.this,IncreaseCommodityActivity.class);
                intent.putExtra("codedContent", result);
                intent.putExtra("page","2");
                startActivity(intent);
            }
            finish();
        }else if (chaifen != null){
            if(chaifen.equals("chaifen")){
                intent.setClass(ScanningActivity.this,IncreaseCommodityActivity.class);
                intent.putExtra("shopTiao", result);
                intent.putExtra("zhuan","3");
                startActivity(intent);
            }
            finish();
        }else{
            intent.setClass(ScanningActivity.this,AddFriendActivity.class);
            intent.putExtra("codedContent", result);
            intent.putExtra("page","2");
            setResult(RESULT_OK, intent);
            startActivity(intent);
            finish();
        }
        vibrate();//兼容API23
        zXingView.startSpot();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e("zhh", "打开相机出错");
        Toast.makeText(this, "打开相机出错", Toast.LENGTH_SHORT).show();
        finish();
    }


    @Override
    protected void onStart() {
        super.onStart();
        zXingView.startCamera();
    }

    @Override
    protected void onStop() {
        zXingView.stopCamera();
        super.onStop();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    protected void onDestroy() {
        zXingView.onDestroy();
        super.onDestroy();
    }
}
