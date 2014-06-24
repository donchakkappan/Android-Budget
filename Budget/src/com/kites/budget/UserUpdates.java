package com.kites.budget;

import java.util.ArrayList;
import java.util.Calendar;

import com.kites.dbsettings.DBconnection;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.os.Build;

public class UserUpdates extends Activity {

	TextView currentDay,currentTime;
	
	private int year;
	private int month;
	private int day;
	
	private int hour;
	private int minute;
	
	public static final int flagDate=2,flagTime=3;
	
	
	GridView majorEnvelopes;
	public static ArrayList<GridViewItemModel> arrayOfItems=new ArrayList<GridViewItemModel>();
	public static GridViewAdapter myAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_updates);
		arrayOfItems.clear();

		majorEnvelopes=(GridView)findViewById(R.id.gridView1);
		
		String sql="select *from envelopes";
		DBconnection db=new DBconnection(getApplicationContext());
		Cursor cu=db.getData(sql);
		
		while (cu.moveToNext()) {
			
			String envelopeName=cu.getString(0);
			GridViewItemModel listItemModel1=new GridViewItemModel();
		    listItemModel1.setName(envelopeName);
		    
		    if(envelopeName.equals("Shopping"))
		    {
			    listItemModel1.setImage("shopping");
		    }
		    else if(envelopeName.equals("Clothes"))
		    {
			    listItemModel1.setImage("cloths");
		    }
		    else if(envelopeName.equals("Food"))
		    {
			    listItemModel1.setImage("food");
		    }
		    else if(envelopeName.equals("Cinema"))
		    {
			    listItemModel1.setImage("cinema");
		    }
		    else
		    {
		    	listItemModel1.setImage("recent");
		    }
		
		    arrayOfItems.add(listItemModel1);
		}
				
		myAdapter=new GridViewAdapter(this,arrayOfItems,getResources());		
		majorEnvelopes.setAdapter(myAdapter);
		
		majorEnvelopes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,long arg3) {
				// TODO Auto-generated method stub
				String selected = ((TextView) view.findViewById(R.id.label)).getText().toString();
				//Toast.makeText(getApplicationContext(),selected,3000).show();
				updateBalance(selected);
			}
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_updates, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
    //--------------------------------------------------------------------------------------------------------------------------------------------------------    

		void updateBalance(final String envelopeName) {
			
			LayoutInflater inflater = this.getLayoutInflater();

			AlertDialog.Builder alert = new AlertDialog.Builder(this);

			alert.setTitle(envelopeName);
			
			alert.setIcon(R.drawable.login);
			
		    View view=inflater.inflate(R.layout.updatebalance, null);
			alert.setView(view);
			
			 final EditText amount=(EditText)view.findViewById(R.id.editText1);
			 final EditText purpose=(EditText)view.findViewById(R.id.EditText01);
			
			currentDay=(TextView)view.findViewById(R.id.envelopename);
			currentTime=(TextView)view.findViewById(R.id.TextView01);
			
			
			final Calendar c = Calendar.getInstance();
			hour = c.get(Calendar.HOUR_OF_DAY);
			minute = c.get(Calendar.MINUTE);
			
			year  = c.get(Calendar.YEAR);
			month = c.get(Calendar.MONTH);
			day   = c.get(Calendar.DAY_OF_MONTH);
			
			currentDay.setText(day+"--"+(month+1)+"--"+year);
			currentTime.setText(hour+"--"+minute);
			
			Button date=(Button)view.findViewById(R.id.button1);		
			Button time=(Button)view.findViewById(R.id.button2);
			
			date.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					showDialog(flagDate);
				}
			});
			
			time.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					showDialog(flagTime);
				}
			});
		   
			final ImageButton up=(ImageButton)view.findViewById(R.id.upordown);		
			final ImageButton down=(ImageButton)view.findViewById(R.id.imageButton2);
			up.setBackgroundColor(Color.TRANSPARENT);
			down.setBackgroundColor(Color.TRANSPARENT);
			
		
			final String day=currentDay.getText().toString()+": : : :"+currentTime.getText().toString();
			
			
			up.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					//Toast.makeText(getApplicationContext(),"UP",3000).show();
					String amountData=amount.getText().toString();
					String purposeOf=purpose.getText().toString();
					String sql="insert into daily values('"+envelopeName+"','"+amountData+"','"+purposeOf+"','up','"+day+"')";
					DBconnection db=new DBconnection(getApplicationContext());
					int i=db.putData(sql);
					if(i==1)
					{
					showAlertMessage("Earnings","Successfully Added");	
					
					ListItemModel listItemModel=new ListItemModel();
					listItemModel.setEnvelopename(envelopeName);
					listItemModel.setPurpose(purposeOf);
					listItemModel.setUporDownLabel("Earned");
					listItemModel.setAmount(amountData);
					listItemModel.setDate(day);
					listItemModel.setImage("up");	
					    
					ViewAsList.arrayOfItems.add(listItemModel);
					ViewAsList.myAdapter.notifyDataSetChanged();
															 
					}
				}
			});
			
			down.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					//Toast.makeText(getApplicationContext(),"DOWN",3000).show();
					String amountData=amount.getText().toString();
					String purposeOf=purpose.getText().toString();
					String sql="insert into daily values('"+envelopeName+"','"+amountData+"','"+purposeOf+"','down','"+day+"')";
					DBconnection db=new DBconnection(getApplicationContext());
					int i=db.putData(sql);
					if(i==1)
					{
					showAlertMessage("Looses","Successfully Added");					

					ListItemModel listItemModel=new ListItemModel();
					listItemModel.setEnvelopename(envelopeName);
					listItemModel.setPurpose(purposeOf);
					listItemModel.setUporDownLabel("Deducted");
					listItemModel.setAmount(amountData);
					listItemModel.setDate(day);
					listItemModel.setImage("down");
					    
					ViewAsList.arrayOfItems.add(listItemModel);
					ViewAsList.myAdapter.notifyDataSetChanged();
					}
					
				}
			});
			

		    alert.show();

		}
		
		
//--------------------------------------------------------------------------------------------------------------------------------------------------------    

//--------------------------------------------------------------------------------------------------------------------------------------------------------    
		  
	  	
		public void showAlertMessage(String title,String message) {
			final AlertDialog alert = new AlertDialog.Builder(this).create();
			alert.setTitle(title);
			alert.setMessage(message);
			alert.setIcon(R.drawable.alert);
			alert.setButton(-1, "OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			 
			       alert.dismiss();					 
				}
			});
			alert.show();
		}
	
    
 //--------------------------------------------------------------------------------------------------------------------------------------------------------    

//--------------------------------------------------------------------------------------------------------------------------------------------------------    
		
			private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

				// when dialog box is closed, below method will be called.
				@Override
				public void onDateSet(DatePicker view, int selectedYear,int selectedMonth, int selectedDay) {
					
					year  = selectedYear;
					month = selectedMonth;
					day   = selectedDay;
					currentDay.setText(day+"--"+(month+1)+"--"+year);
			
				   }
			    };
			
//--------------------------------------------------------------------------------------------------------------------------------------------------------    
			
			  private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
					public void onTimeSet(TimePicker view, int selectedHour,int selectedMinute) {
						
						hour = selectedHour;
						minute = selectedMinute;
						currentTime.setText(hour+"--"+minute);

					}
				};
			

//--------------------------------------------------------------------------------------------------------------------------------------------------------    
//-----------------------------------------------------------------------------------------------------------------------------------------------	
				
				protected Dialog onCreateDialog(int id) {
					
				    switch (id) {
				    case flagDate:		    	 

						  return new DatePickerDialog(this, pickerListener, year, month,day);
						  
				    case flagTime:		    	 

				    	return new TimePickerDialog(this, timePickerListener, hour, minute,false);
						  
				    default:
				        return null;
				    }	
				}

}
