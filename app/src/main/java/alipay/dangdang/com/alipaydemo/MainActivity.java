package alipay.dangdang.com.alipaydemo;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.alipay.sdk.app.PayTask;
import com.pay.alipay.OrderInfoUtil2_0;
import com.pay.alipay.PayResult;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button btn1;
    private Button btn2;
    private MyListener listener;
    public static final String TAG = "com.dangdang=====";
    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "11111";
    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    public static final String PID = "";
    /**
     * 支付宝账户登录授权业务：入参target_id值
     */
    public static final String TARGET_ID = "";
    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /**
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA2_PRIVATE = "22222";
    public static final String RSA_PRIVATE = "33333";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        listener = new MyListener();
        btn1.setOnClickListener(listener);
        btn2.setOnClickListener(listener);
    }

    class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn1:
                    MyTask task = new MyTask();
                    String orderInfo="partner=\"2088101568358171\"&seller_id=\"xxx@alipay.com\"&out_trade_no=\"0819145412-6177\"&subject=\"测试\"&body=\"测试测试\"&total_fee=\"0.01\"&notify_url=\"http://notify.msp.hk/notify.htm\"&service=\"mobile.securitypay.pay\"&payment_type=\"1\"&_input_charset=\"utf-8\"&it_b_pay=\"30m\"&sign=\"lBBK%2F0w5LOajrMrji7DUgEqNjIhQbidR13GovA5r3TgIbNqv231yC1NksLdw%2Ba3JnfHXoXuet6XNNHtn7VE%2BeCoRO1O%2BR1KugLrQEZMtG5jmJIe2pbjm%2F3kb%2FuGkpG%2BwYQYI51%2BhA3YBbvZHVQBYveBqK%2Bh8mUyb7GM1HxWs9k4%3D\"&sign_type=\"RSA\"";
                    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, orderInfo);
                case R.id.btn2:
            }
        }
    }

    class MyTask extends AsyncTask<String, Integer, Map<String, String>> {


        @Override
        protected Map<String, String> doInBackground(String... params) {
            boolean rsa2 = (RSA2_PRIVATE.length() > 0);
//            Map<String, String> paramsMap = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
//            String orderParam = OrderInfoUtil2_0.buildOrderParam(paramsMap);
//            String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
//            String sign = OrderInfoUtil2_0.getSign(paramsMap, privateKey, rsa2);
//            final String orderInfo = orderParam + "&" + sign;

            PayTask alipay = new PayTask(MainActivity.this);
            return alipay.payV2(params[0], true);
        }

        @Override
        protected void onPostExecute(Map<String, String> stringStringMap) {
            PayResult result = new PayResult(stringStringMap);
            String resultInfo = result.getResult();
            String resultStatus = result.getResultStatus();
            if (TextUtils.equals(resultStatus, "9000")) {
                ;
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("交易成功")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).show();
            } else {
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("交易失败")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).show();
            }
        }
    }
}
