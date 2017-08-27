package com.example.test.PinFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.avos.avoscloud.*;
import com.example.test.ImageDownLoader.onImageLoaderListener;
import com.example.test.activity.MainActivity;
import com.example.test.util.Loger;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

public class bigimage extends Activity 
{
	String BigUrlString;
	Bitmap picSeeBitmap;
	ImageView pic_seeImageView;
	private ImageDownLoader mImageDownLoader;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bigimage);
		ActionBar actionBar = this.getActionBar();
		actionBar.hide();
		Bundle bundle = this.getIntent().getExtras();
		BigUrlString=bundle.getString("bigimageurl");
		Loger.i("BigUrlString"+BigUrlString);
		pic_seeImageView = (ImageView) findViewById(R.id.bigimage);
		mImageDownLoader = new ImageDownLoader(this);
		getPicUrl(BigUrlString);
	}

	public void getPicUrl(String urlstring) 
	{
		picSeeBitmap = mImageDownLoader.downloadImage(
				urlstring, new onImageLoaderListener() {
					@Override
					public void onImageLoader(Bitmap bitmap,
							String url) {
						Loger.l();
						if (pic_seeImageView != null
								&& bitmap != null) {
							Loger.l();
							pic_seeImageView.setImageBitmap(bitmap);
						}
					}
				});
		if (picSeeBitmap != null) {
			Loger.l();
			pic_seeImageView.setImageBitmap(picSeeBitmap);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		bigimage.this.finish();
		return super.onTouchEvent(event);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void progressDialogDismiss() {
		if (progressDialog != null)
			progressDialog.dismiss();
	}

	private void progressDialogShow() {
		progressDialog = ProgressDialog
				.show(bigimage.this,
						bigimage.this.getResources().getText(
								R.string.dialog_title_remind),
						bigimage.this.getResources().getText(
								R.string.dialog_text_wait), true, false);
	}
}
