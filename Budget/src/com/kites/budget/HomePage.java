package com.kites.budget;


import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

import com.kites.dbsettings.DBconnection;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TabActivity;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;
import android.os.Build;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.database.Cursor;
import android.view.View.OnClickListener;


public class HomePage extends TabActivity {

	private ListView mDrawerList;
	private DrawerLayout mDrawer;
	private CustomActionBarDrawerToggle mDrawerToggle;
	private String[] menuItems;
	
	
	private int year;
	private int month;
	private int day;
	
	private int hour;
	private int minute;
	private int second;
	
	TimePicker TimePicker;
	TimePickerDialog timePickerDialog;
	final static int RQS_1 = 1;
	
	Calendar calSet;
	
	public static final int flagDate=2,flagTime=3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 //Remove notification bar
 	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_home_page);

		TabHost tabHost=getTabHost();
		TabSpec firstTab=tabHost.newTabSpec("Updates");
		TabSpec secondTab=tabHost.newTabSpec("Analyse");
		
		Intent toFirstActivity=new Intent(this,UserUpdates.class);
		Intent toSecondActivity=new Intent(this,UserAnalyse.class);
		
		firstTab.setIndicator("Updates",getResources().getDrawable(R.drawable.login));
		firstTab.setContent(toFirstActivity);
		secondTab.setIndicator("Analyse",getResources().getDrawable(R.drawable.searchdialogue));
		secondTab.setContent(toSecondActivity);
		
		tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabPosition) {
				// TODO Auto-generated method stub
				
			}
		});
		
		tabHost.addTab(firstTab);
		tabHost.addTab(secondTab);
				
		// enable ActionBar app icon to behave as action to toggle nav drawer
				getActionBar().setDisplayHomeAsUpEnabled(true);
				getActionBar().setHomeButtonEnabled(true);

				mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

				// set a custom shadow that overlays the main content when the drawer
				// opens
				mDrawer.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

				_initMenu();
				mDrawerToggle = new CustomActionBarDrawerToggle(this, mDrawer);
				mDrawer.setDrawerListener(mDrawerToggle);
	}
	
	
	private void _initMenu() {
		NavigationDrawerAdapter mAdapter = new NavigationDrawerAdapter(this);

		// Add Header
		mAdapter.addHeader(R.string.ns_menu_main_header);

		// Add first block

		menuItems = getResources().getStringArray(
				R.array.ns_menu_items);
		String[] menuItemsIcon = getResources().getStringArray(
				R.array.ns_menu_items_icon);

		int res = 0;
		for (String item : menuItems) {

			int id_title = getResources().getIdentifier(item, "string",
					this.getPackageName());
			int id_icon = getResources().getIdentifier(menuItemsIcon[res],
					"drawable", this.getPackageName());

			NavigationDrawerItemModel mItem = new NavigationDrawerItemModel(id_title, id_icon);
			if (res==1) mItem.counter=12; //it is just an example...
			if (res==3) mItem.counter=3; //it is just an example...
			mAdapter.addItem(mItem);
			res++;
		}

		mDrawerList = (ListView) findViewById(R.id.drawer);
		if (mDrawerList != null)
			mDrawerList.setAdapter(mAdapter);
		 
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_page, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawer.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_save).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/*
		 * The action bar home/up should open or close the drawer.
		 * ActionBarDrawerToggle will take care of this.
		 */
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		// Handle your other action bar items...
		return super.onOptionsItemSelected(item);
	}

	private class CustomActionBarDrawerToggle extends ActionBarDrawerToggle {

		public CustomActionBarDrawerToggle(Activity mActivity,DrawerLayout mDrawerLayout){
			super(
			    mActivity,
			    mDrawerLayout, 
			    R.drawable.ic_drawer,
			    R.string.ns_menu_open, 
			    R.string.ns_menu_close);
		}

		@Override
		public void onDrawerClosed(View view) {
			getActionBar().setTitle(getString(R.string.ns_menu_close));
			invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
		}

		@Override
		public void onDrawerOpened(View drawerView) {
			getActionBar().setTitle(getString(R.string.ns_menu_open));
			invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
		}
	}
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// Highlight the selected item, update the title, and close the drawer
			// update selected item and title, then close the drawer
	        mDrawerList.setItemChecked(position, true);

	        if(position==3)
	        {	        
	        	showAlarm();	        	
	        }
	        else if(position==5)
	        {
	        	addEnvelope();	
	        }
	        else if(position==1)
	        {
	        	try {
	        		
					CSVReadWrite.readCSV();
					
					} catch (Exception e) {}
	        	
	        		showAlertMessage("CSV","Data imported Successfully");
	
	        }
	        else if(position==2)
	        {
	        	try {
	        		
	        		String sql="select *from daily";
	        		DBconnection db=new DBconnection(getApplicationContext());
	        		Cursor cu=db.getData(sql);
	        		
	        		String data="";
	        		
	        		while (cu.moveToNext()) {
	        			
	        		data=data+cu.getString(0)+"#"+cu.getString(1)+"#"+cu.getString(2)+"#"+cu.getString(3)+"#"+cu.getString(4);
	        		File f=new File(Environment.getExternalStorageDirectory()+"/+output.csv");
	        		FileOutputStream fout=new FileOutputStream(f);
	        		fout.write("\n".getBytes());
	        		
	        		CSVReadWrite.writeCSV(data);	
					}
	        		
					
					
	        		} catch (Exception e) {}
	        	
	        	showAlertMessage("CSV","Data exported Successfully");
	
	        }
	        
	        //You should reset item counter 
	        mDrawer.closeDrawer(mDrawerList);
			
		}
		
	}

//-------------------------------------------------------------------------------------------------------------------------------------


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

		void addEnvelope() {
			
			LayoutInflater inflater = this.getLayoutInflater();

			AlertDialog.Builder alert = new AlertDialog.Builder(this);

			alert.setTitle("Envelope Name");
			
			alert.setIcon(R.drawable.login);
			
			final EditText envelopeName=new EditText(this);
			alert.setView(envelopeName);
			
			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				
				String envelope=envelopeName.getText().toString();
				//Toast.makeText(getApplicationContext(),"Ambo ..",3000).show();
			    GridViewItemModel listItemModel=new GridViewItemModel();
			    listItemModel.setImage("recent");
			    listItemModel.setName(envelopeName.getText().toString());	    
			    UserUpdates.arrayOfItems.add(listItemModel);	
			    
			    String sql="insert into envelopes values('"+envelope+"','now()')";
				DBconnection db=new DBconnection(getApplicationContext());
				int i=db.putData(sql);
				if(i==1)
				{
				showAlertMessage("Message","Successfully Added");	
				}
				
				
				
			    UserUpdates.myAdapter.notifyDataSetChanged();
			 }
			});

			alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			 public void onClick(DialogInterface dialog, int whichButton) {
				
			}
			});

		    alert.show();

		}
		
		
//--------------------------------------------------------------------------------------------------------------------------------------------------------    
//--------------------------------------------------------------------------------------------------------------------------------------------------------    

			void showAlarm() {
				
				LayoutInflater inflater = this.getLayoutInflater();

				AlertDialog.Builder alert = new AlertDialog.Builder(this);

				alert.setTitle("Provide the Details");
				
				alert.setIcon(R.drawable.alarm);
				
				final View view=inflater.inflate(R.layout.setalarm, null);
				alert.setView(view);
								
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
						openTimePickerDialog(false);
					}
				});
				
				
				final EditText message=(EditText)view.findViewById(R.id.editText1);
				
				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					
					Toast.makeText(getApplicationContext(), "Alarm set in " + calSet.getTime() + " seconds",Toast.LENGTH_LONG).show();
					AlarmBroadcastReceiver.message=message.getText().toString();
					setAlarm(calSet);
												
				 }
				});

				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				 public void onClick(DialogInterface dialog, int whichButton) {
					
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
								
				   }
			    };
			
//--------------------------------------------------------------------------------------------------------------------------------------------------------    
			    private void openTimePickerDialog(boolean is24r) {
			        Calendar calendar = Calendar.getInstance();
			        timePickerDialog = new TimePickerDialog(HomePage.this, onTimeSetListener,
			                calendar.get(Calendar.HOUR_OF_DAY),
			                calendar.get(Calendar.MINUTE), is24r);
			        timePickerDialog.setTitle("Set Alarm");

			        timePickerDialog.show();

			    }
			    
//--------------------------------------------------------------------------------------------------------------------------------------------------------    
//-----------------------------------------------------------------------------------------------------------------------------------------------	
	
			    
			    OnTimeSetListener onTimeSetListener = new OnTimeSetListener() {
			        @Override
			        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

			            Calendar calNow = Calendar.getInstance();
			            calSet = (Calendar) calNow.clone();

			            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
			            calSet.set(Calendar.MINUTE, minute);
			            calSet.set(Calendar.SECOND, 0);
			            calSet.set(Calendar.MILLISECOND, 0);

			            if (calSet.compareTo(calNow) <= 0) {

			                calSet.add(Calendar.DATE, 1);
			            }			            
			        }
			    };
			    
//--------------------------------------------------------------------------------------------------------------------------------------------------------    
//-----------------------------------------------------------------------------------------------------------------------------------------------	
		    
			    private void setAlarm(Calendar targetCal) {
			    	
			        	int i = Integer.parseInt("10");
			    		Intent intent = new Intent(getApplicationContext(),AlarmBroadcastReceiver.class);
			    		PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 234324243, intent, 0);
			    		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
			    		alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);			    		

			    }
			    

//--------------------------------------------------------------------------------------------------------------------------------------------------------    
//-----------------------------------------------------------------------------------------------------------------------------------------------	
				
				protected Dialog onCreateDialog(int id) {
					
				    switch (id) {
				    case flagDate:		    	 
						  return new DatePickerDialog(this, pickerListener, year, month,day);						  						  
				    default:
				        return null;
				    }	
				}		

}
