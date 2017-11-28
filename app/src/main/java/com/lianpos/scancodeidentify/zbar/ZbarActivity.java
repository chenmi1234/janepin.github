package com.lianpos.scancodeidentify.zbar;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.lianpos.activity.R;
import com.lianpos.devfoucs.contacts.ui.AddFriendActivity;
import com.lianpos.devfoucs.homepage.activity.IWantBillingActivity;
import com.lianpos.devfoucs.listviewlinkage.View.AddCommodityDialog;
import com.lianpos.devfoucs.shoppingcart.MerchantActivity;
import com.lianpos.devfoucs.shoppingcart.activity.IncreaseCommodityActivity;
import com.lianpos.entity.JanePinBean;
import com.lianpos.qrcode.QRCodeDecoder;
import com.lianpos.qrcode.QRCodeView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;


/**
 * Zbar二维码扫描+闪光灯+本地二维码识别
 */
public class ZbarActivity extends AppCompatActivity implements QRCodeView.Delegate {

    @Bind(R.id.zbarview)
    ZBarView mQRCodeView;

    @Bind(R.id.scancode_lamplight)
    ToggleButton toggleButton;

    // 两个按钮的dialog
    private AddCommodityDialog addCommodityDialog;
    private Realm realm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zbartest_scan_layout);
        ButterKnife.bind(this);
        initLayout();
    }

    private void initLayout() {
        mQRCodeView = (ZBarView) findViewById(R.id.zbarview);
        toggleButton = (ToggleButton) findViewById(R.id.scancode_lamplight);
        mQRCodeView.setDelegate(this);
        mQRCodeView.startSpotAndShowRect();//显示扫描框，并且延迟1.5秒后开始识别
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mQRCodeView.openFlashlight();
                } else {
                    mQRCodeView.closeFlashlight();
                }
            }
        });
    }

    @OnClick({R.id.line_back, R.id.scancode_localimg})
    protected void onClickBtn(View view) {
        switch (view.getId()) {
            case R.id.line_back:
                finish();
                break;
            case R.id.scancode_localimg:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 0x11);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 0x11) {
            Uri uri = data.getData();
            String path = null;
            if (!TextUtils.isEmpty(uri.getAuthority())) {
                Cursor cursor = getContentResolver().query(uri,
                        new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                if (null == cursor) {
                    Toast.makeText(ZbarActivity.this, "图片没找到", Toast.LENGTH_SHORT).show();
                    return;
                }
                cursor.moveToFirst();
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                cursor.close();
            } else {
                path = uri.getPath();
            }
            if (null != path) {
                codeDiscriminate(path);
            } else {
                Toast.makeText(ZbarActivity.this, "图片路径为空", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    @Override
    protected void onRestart() {
        mQRCodeView.startCamera();
        super.onRestart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mQRCodeView.startSpotAndShowRect();//显示扫描框，并且延迟1.5秒后开始识别
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
//        mQRCodeView.closeFlashlight();
        super.onStop();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    private void codeDiscriminate(final String path) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Looper.prepare();
                String result = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    result = QRCodeDecoder.syncDecodeQRCode(path);
                } else {
                    result = QRCodeDecoder.syncDecodeQRCode2(path);
                }
                Log.i("zbar_result", Build.VERSION.SDK_INT + "--->" + result);
                Message msg = mHandler.obtainMessage();
                //封装消息id
                msg.what = 1;//作为标示，便于接收消息
                msg.obj = result;
                mHandler.sendMessage(msg);//发送消息
            }
        }).start();
    }


    //创建一个Hander局部类对象，通过handleMessage()钩子方法来更新UI控件
    Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            //得到封装消息的id进行匹配
            if (1 == msg.what) {
                if (null != msg.obj)
                    onScanQRCodeSuccess(msg.obj.toString());
            }
        }

    };


    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        ButterKnife.unbind(this);
        super.onDestroy();

    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Log.i("zbar_result", "result:" + result);
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        Intent intent = getIntent();
        String tiaozhuan = intent.getStringExtra("commodity");
        String chaifen = intent.getStringExtra("increase");
        String tianjiashop = intent.getStringExtra("addshop");

        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<JanePinBean> guests = realm.where(JanePinBean.class).equalTo("id", 0).findAll();
        realm.commitTransaction();
        String billingInventory = "";
        for (JanePinBean guest : guests) {
            billingInventory = guest.NewlyAddedDistinguish;
        }

        if (billingInventory.equals("1")){
            realm.beginTransaction();
            JanePinBean janePinBean = realm.createObject(JanePinBean.class); // Create a new object
            janePinBean.NewlyAddedBarCode = result;
            realm.commitTransaction();
            finish();
        }else if (billingInventory.equals("2")){
            realm.beginTransaction();
            JanePinBean janePinBean = realm.createObject(JanePinBean.class); // Create a new object
            janePinBean.NewlyAddedAssembleBarCode = result;
            realm.commitTransaction();
            finish();
        }else if (billingInventory.equals("3")){
            realm.beginTransaction();
            JanePinBean janePinBean = realm.createObject(JanePinBean.class); // Create a new object
            janePinBean.DialogEjectCode = "1";
            realm.commitTransaction();
            finish();
        }

        if (tiaozhuan != null){
            if (tiaozhuan.equals("shop")){
                intent.setClass(ZbarActivity.this,IncreaseCommodityActivity.class);
                intent.putExtra("codedContent", result);
                intent.putExtra("page","2");
                startActivity(intent);
            }
            finish();
            finish();
        }else if (chaifen != null){
            if(chaifen.equals("chaifen")){
                intent.setClass(ZbarActivity.this,IncreaseCommodityActivity.class);
                intent.putExtra("shopTiao", result);
                intent.putExtra("zhuan","3");
                startActivity(intent);
            }
            finish();
        }else if (tianjiashop != null){
            if(tianjiashop.equals("addshop")){
                ButterKnife.bind(this);
                realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                JanePinBean janePinBean = realm.createObject(JanePinBean.class); // Create a new object
                janePinBean.AddShopDialogTitle = result;
                janePinBean.DialogEjectCode = "1";
                realm.commitTransaction();
            }
            finish();
        }
        vibrate();
        mQRCodeView.startSpot();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e("zbar_result", "打开相机出错");
        Toast.makeText(this, "打开相机出错", Toast.LENGTH_SHORT).show();
    }

}
