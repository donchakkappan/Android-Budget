package com.kites.budget;

import java.util.ArrayList;

import com.kites.dbsettings.DBconnection;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.os.Build;

public class ViewAsList extends Activity {

	
	ListView list;
	public static ArrayList<ListItemModel> arrayOfItems=new ArrayList<ListItemModel>();
	public static ListViewAdapter myAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_as_list);
		arrayOfItems.clear();
		list=(ListView)findViewById(R.id.listView1);

		String sql="select *from daily";
		DBconnection db=new DBconnection(getApplicationContext());
		Cursor cu=db.getData(sql);
		
		while (cu.moveToNext()) {
			
			String envelopeName=cu.getString(0);
			String purpose=cu.getString(2);
			String amount=cu.getString(1);
			String date=cu.getString(4);
			
			    ListItemModel listItemModel=new ListItemModel();
			    listItemModel.setEnvelopename(envelopeName);
			    listItemModel.setPurpose(purpose);
			    listItemModel.setAmount(amount);
			    listItemModel.setDate(date);
			    if(cu.getString(3).equals("up"))
			    {
			    	listItemModel.setUporDownLabel("Earned");
				    listItemModel.setImage("up");	
			    }
			    else
			    {
			    	listItemModel.setUporDownLabel("Deducted");
				    listItemModel.setImage("down");
			    }
			    
			    arrayOfItems.add(listItemModel);	
		}
		
//			ListItemModel listItemModel=new ListItemModel();
//		    listItemModel.setEnvelopename("Shopping");
//		    listItemModel.setPurpose("Books And Bags");
//		    listItemModel.setUporDownLabel("Deducted");
//		    listItemModel.setImage("down");	
//		    arrayOfItems.add(listItemModel);
//		    
//		    ListItemModel listItemModel1=new ListItemModel();
//		    listItemModel1.setEnvelopename("Office");
//		    listItemModel1.setPurpose("Salary");
//		    listItemModel1.setUporDownLabel("Earned");
//		    listItemModel1.setImage("up");	
//		    arrayOfItems.add(listItemModel1);
		
		myAdapter=new ListViewAdapter(this,arrayOfItems,getResources());
		
		list.setAdapter(myAdapter);
	
	}

}
