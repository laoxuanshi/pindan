package com.example.test.PinFragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import com.example.test.ImageGridAdapter.TextCallback;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class ImageGridActivity extends Activity
{
	public static final String EXTRA_IMAGE_LIST = "imagelist";


	List<ImageItem> dataList;
	GridView gridView;
	ImageGridAdapter adapter;
	AlbumHelper helper;
	Button bt;

	Handler mHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case 0:
				Toast.makeText(ImageGridActivity.this, "Õº∆¨œÍ«È", 400).show();
				break;

			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_image_grid);

		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());

		dataList = (List<ImageItem>) getIntent().getSerializableExtra(
				EXTRA_IMAGE_LIST);

		initView();
		bt = (Button) findViewById(R.id.bt);
		bt.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v)
			{
				ArrayList<String> list = new ArrayList<String>();
				Collection<String> c = adapter.map.values();
				Iterator<String> it = c.iterator();
				for (; it.hasNext();)
				{
					list.add(it.next());
				}

				if (Bimp.act_bool)
				{
					Intent intent = new Intent(ImageGridActivity.this,
							xianxia1.class);
					startActivity(intent);
					Bimp.act_bool = false;
				}
				for (int i = 0; i < list.size(); i++)
				{
					if (Bimp.drr.size() < 9)
					{
						Bimp.drr.add(list.get(i));
					}
				}
				finish();
			}

		});
	}

	private void initView()
	{
		gridView = (GridView) findViewById(R.id.gridview);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new ImageGridAdapter(ImageGridActivity.this, dataList,
				mHandler);
		gridView.setAdapter(adapter);
		adapter.setTextCallback(new TextCallback()
		{
			public void onListen(int count)
			{
				bt.setText("π≤" + "(" + count + ")");
			}
		});

		gridView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				adapter.notifyDataSetChanged();
			}

		});

	}
}
