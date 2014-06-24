package com.kites.budget;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;


@SuppressWarnings("deprecation")
public class UserAnalyse extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_useranalyse);

		TabHost tabHost=getTabHost();
		TabSpec firstTab=tabHost.newTabSpec("Expense");
		TabSpec secondTab=tabHost.newTabSpec("Graph");
		
		Intent toFirstActivity=new Intent(this,ViewAsList.class);
		Intent toSecondActivity=new Intent(this,ViewAsGraph.class);
		
		firstTab.setIndicator("Expense",getResources().getDrawable(R.drawable.login));
		firstTab.setContent(toFirstActivity);
		secondTab.setIndicator("Graph",getResources().getDrawable(R.drawable.searchdialogue));
		secondTab.setContent(toSecondActivity);
		
		tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabPosition) {
				// TODO Auto-generated method stub
				
			}
		});
		
		tabHost.addTab(firstTab);
		tabHost.addTab(secondTab);
	}
	
}
