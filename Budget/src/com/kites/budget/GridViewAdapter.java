package com.kites.budget;

import java.util.ArrayList;

import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GridViewAdapter extends BaseAdapter{
	
	Activity callingActivity;
	ArrayList datas;
	Resources imagesEtc;

	public GridViewAdapter(Activity callingActivity,ArrayList datas,Resources imagesEtc) {
		// TODO Auto-generated constructor stub
		this.callingActivity=callingActivity;
		this.datas=datas;
		this.imagesEtc=imagesEtc;
   
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(datas.size()<=0)
    		return 1;
        return datas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public static class ItemsInEachRow
	{
		ImageView imageName;
		TextView userName;
		Button okButton,cancelButton;
		
	}
	
	
	@Override
	public View getView(final int position, View eachRowsView, ViewGroup parentView) {
		// To Inflate Single Item of ListView
		
		View eachRow =eachRowsView;
		ItemsInEachRow itemsInEachRow;
		if(eachRowsView==null)
		{
		itemsInEachRow=new ItemsInEachRow();
		
		eachRow=callingActivity.getLayoutInflater().inflate(R.layout.envelopeitemmodel, null);	
		
		itemsInEachRow.imageName=(ImageView)eachRow.findViewById(R.id.imageView1);
		itemsInEachRow.userName=(TextView)eachRow.findViewById(R.id.label);
//		itemsInEachRow.okButton=(Button)eachRow.findViewById(R.id.button1);
//		itemsInEachRow.cancelButton=(Button)eachRow.findViewById(R.id.button2);
//
//		
//		itemsInEachRow.okButton.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				GridViewItemModel temporary=(GridViewItemModel)datas.get(position);
//				Toast.makeText(arg0.getContext(),"Ambo .."+temporary.getName(),3000).show();
//			}
//		});
//		
		eachRow.setTag(itemsInEachRow);
	
		}
		else
		{
			itemsInEachRow=(ItemsInEachRow)eachRow.getTag();
		}
//-------------------------------------------------------------------------------------------------------------------
		
		// To set Data in Each Single Item of ListView
		
		if(datas.size()<=0)
		{
			itemsInEachRow.userName.setText("No Envelopes Added Yet");	
		}
		else
		{
			GridViewItemModel temporary=(GridViewItemModel)datas.get(position);

			itemsInEachRow.imageName.setImageResource(imagesEtc.getIdentifier("com.kites.budget:drawable/"+temporary.getImage(),null,null));
			itemsInEachRow.userName.setText(temporary.getName());

		}
		
		return eachRow;
	}

//-------------------------------------------------------------------------------------------------------------------
	
}
