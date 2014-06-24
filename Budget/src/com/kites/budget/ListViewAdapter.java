package com.kites.budget;

import java.util.ArrayList;

import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ListViewAdapter extends BaseAdapter{
	
	Activity callingActivity;
	ArrayList datas;
	Resources imagesEtc;

	public ListViewAdapter(Activity callingActivity,ArrayList datas,Resources imagesEtc) {
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
		ImageView upordownimage;
		TextView  envelopenameLabel;
		TextView purposeLabel;
		TextView upordownLabel;
		TextView amount;
		TextView date;
	
	}
	
	
	@Override
	public View getView(final int position, View eachRowsView, ViewGroup parentView) {
		// To Inflate Single Item of ListView
		
		View eachRow =eachRowsView;
		ItemsInEachRow itemsInEachRow;
		if(eachRowsView==null)
		{
		itemsInEachRow=new ItemsInEachRow();
		
		eachRow=callingActivity.getLayoutInflater().inflate(R.layout.lisviewitemmodel, null);	
		
		itemsInEachRow.upordownimage=(ImageView)eachRow.findViewById(R.id.imageView1);
		itemsInEachRow.envelopenameLabel=(TextView)eachRow.findViewById(R.id.envelopename);
		itemsInEachRow.purposeLabel=(TextView)eachRow.findViewById(R.id.purpose);
		itemsInEachRow.upordownLabel=(TextView)eachRow.findViewById(R.id.upordownlabel);
		itemsInEachRow.amount=(TextView)eachRow.findViewById(R.id.amount);
		itemsInEachRow.date=(TextView)eachRow.findViewById(R.id.date);
		
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
			itemsInEachRow.envelopenameLabel.setText("No Transaction");	
			itemsInEachRow.purposeLabel.setText("NIL");	
			itemsInEachRow.upordownLabel.setText("NIL");	
			itemsInEachRow.amount.setText("NIL");	
			itemsInEachRow.date.setText("NIL");
		}
		else
		{
			ListItemModel temporary=(ListItemModel)datas.get(position);

			itemsInEachRow.upordownimage.setImageResource(imagesEtc.getIdentifier("com.kites.budget:drawable/"+temporary.getupordownImage(),null,null));
			itemsInEachRow.envelopenameLabel.setText(temporary.getEnvelopeName());
			itemsInEachRow.purposeLabel.setText(temporary.getPurpose());
			itemsInEachRow.upordownLabel.setText(temporary.getUporDownLabel());
			itemsInEachRow.amount.setText(temporary.getAmount());
			itemsInEachRow.date.setText(temporary.getDate());

		}
		
		return eachRow;
	}

//-------------------------------------------------------------------------------------------------------------------
	
}
