//package com.example.test;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//
//import com.avos.avoscloud.AVCloud;
//import com.avos.avoscloud.AVException;
//import com.avos.avoscloud.AVObject;
//import com.avos.avoscloud.AVQuery;
//import com.avos.avoscloud.AVUser;
//import com.avos.avoscloud.FindCallback;
//import com.avos.avoscloud.FunctionCallback;
//import com.avos.sns.*;
//import com.example.test.util.Loger;
//
////使用新浪微博SNS登录，在您的Activity中
//public class AuthActivity extends Activity {
//
// // onCreate中初始化，并且登录
// public void onCreate(Bundle savedInstanceState) 
// {
//	 super.onCreate(savedInstanceState);
//     // callback 函数
//     final SNSCallback myCallback = new SNSCallback() {
//         @Override
//         public void done(SNSBase object, SNSException e) {
//             if (e == null) {
//                 Loger.i("成功登录 " + SNSType.AVOSCloudSNSSinaWeibo );
//             }
//         }
//     };
//
//     // 关联
//     try {
//		SNS.setupPlatform(SNSType.AVOSCloudSNSSinaWeibo, "weiboping", "123", "https://leancloud.cn/1.1/sns/callback/fi6uhskbvx9xrzk1");
//	} catch (AVException e1) {
//		// TODO Auto-generated catch block
//		e1.printStackTrace();
//	}
//     SNS.loginWithCallback(this, SNSType.AVOSCloudSNSSinaWeibo, myCallback);
// }
//
// // 当登录完成后，请调用SNS.onActivityResult(requestCode, resultCode, data, type);
// // 这样您的回调用将会被调用到
// @Override
// protected void onActivityResult(int requestCode, int resultCode, Intent data) 
// {
//     super.onActivityResult(requestCode, resultCode, data);
//     SNS.onActivityResult(requestCode, resultCode, data, SNSType.AVOSCloudSNSSinaWeibo);
// }
//}