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
////ʹ������΢��SNS��¼��������Activity��
//public class AuthActivity extends Activity {
//
// // onCreate�г�ʼ�������ҵ�¼
// public void onCreate(Bundle savedInstanceState) 
// {
//	 super.onCreate(savedInstanceState);
//     // callback ����
//     final SNSCallback myCallback = new SNSCallback() {
//         @Override
//         public void done(SNSBase object, SNSException e) {
//             if (e == null) {
//                 Loger.i("�ɹ���¼ " + SNSType.AVOSCloudSNSSinaWeibo );
//             }
//         }
//     };
//
//     // ����
//     try {
//		SNS.setupPlatform(SNSType.AVOSCloudSNSSinaWeibo, "weiboping", "123", "https://leancloud.cn/1.1/sns/callback/fi6uhskbvx9xrzk1");
//	} catch (AVException e1) {
//		// TODO Auto-generated catch block
//		e1.printStackTrace();
//	}
//     SNS.loginWithCallback(this, SNSType.AVOSCloudSNSSinaWeibo, myCallback);
// }
//
// // ����¼��ɺ������SNS.onActivityResult(requestCode, resultCode, data, type);
// // �������Ļص��ý��ᱻ���õ�
// @Override
// protected void onActivityResult(int requestCode, int resultCode, Intent data) 
// {
//     super.onActivityResult(requestCode, resultCode, data);
//     SNS.onActivityResult(requestCode, resultCode, data, SNSType.AVOSCloudSNSSinaWeibo);
// }
//}