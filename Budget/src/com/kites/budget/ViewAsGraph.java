package com.kites.budget;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.kites.dbsettings.DBconnection;

import cn.limc.androidcharts.common.IZoomable;
import cn.limc.androidcharts.entity.ColoredStickEntity;
import cn.limc.androidcharts.entity.DateValueEntity;
import cn.limc.androidcharts.entity.IStickEntity;
import cn.limc.androidcharts.entity.ListChartData;
import cn.limc.androidcharts.view.ColoredSlipStickChart;
import cn.limc.androidcharts.view.GridChart;
import cn.limc.androidcharts.view.MASlipStickChart;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;


public class ViewAsGraph extends Activity{

	List<IStickEntity> ohlc;
	List<IStickEntity> vol;
	List<IStickEntity> volc;
	List<DateValueEntity> dv1;
	List<DateValueEntity> dv2;
	List<IStickEntity> macd;
	
	MASlipStickChart maslipstickchart;
	
	ColoredSlipStickChart coloredslipstickchart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_barchart);
	
		//initVOLC();
		initGraph();
		
		initColoredSlipStickChart();


}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//initVOLC();
				initGraph();
				
				initColoredSlipStickChart();
	}


	private void initColoredSlipStickChart() {
		this.coloredslipstickchart = (ColoredSlipStickChart) findViewById(R.id.coloredslipstickchart);

		coloredslipstickchart.setAxisXColor(Color.LTGRAY);
		coloredslipstickchart.setAxisYColor(Color.LTGRAY);
		coloredslipstickchart.setLatitudeColor(Color.GRAY);
		coloredslipstickchart.setLongitudeColor(Color.GRAY);
		coloredslipstickchart.setBorderColor(Color.LTGRAY);
		coloredslipstickchart.setLongitudeFontColor(Color.BLACK);
		coloredslipstickchart.setLatitudeFontColor(Color.BLACK);

		// 最大纬线数
		coloredslipstickchart.setLatitudeNum(2);
		// 最大经线数
		coloredslipstickchart.setLongitudeNum(3);
		// 最大价格
		coloredslipstickchart.setMaxValue(100000);
		// 最小价格
		coloredslipstickchart.setMinValue(100);

		coloredslipstickchart.setDisplayFrom(0);     // NB

		coloredslipstickchart.setDisplayNumber(7);    //NB

		coloredslipstickchart.setMinDisplayNumber(7);

		coloredslipstickchart.setZoomBaseLine(IZoomable.ZOOM_BASE_LINE_CENTER);

		coloredslipstickchart.setDisplayLongitudeTitle(true);
		coloredslipstickchart.setDisplayLatitudeTitle(true);
		coloredslipstickchart.setDisplayLatitude(true);
		coloredslipstickchart.setDisplayLongitude(true);
		coloredslipstickchart.setBackgroundColor(Color.WHITE);

		coloredslipstickchart.setDataQuadrantPaddingTop(10);
		coloredslipstickchart.setDataQuadrantPaddingBottom(10);
		coloredslipstickchart.setDataQuadrantPaddingLeft(10);
		coloredslipstickchart.setDataQuadrantPaddingRight(10);
		
		coloredslipstickchart.setAxisYTitleQuadrantWidth(15);
		coloredslipstickchart.setAxisXTitleQuadrantHeight(10);
		coloredslipstickchart.setAxisXPosition(GridChart.AXIS_X_POSITION_BOTTOM);
		coloredslipstickchart.setAxisYPosition(GridChart.AXIS_Y_POSITION_RIGHT);

		// 为chart1增加均线
		coloredslipstickchart
				.setStickData(new ListChartData<IStickEntity>(volc));
	}
	
	private void initGraph() {
	
		List<IStickEntity> stick = new ArrayList<IStickEntity>();
		this.volc = new ArrayList<IStickEntity>();
		
		String sql="select *from daily";
		DBconnection db=new DBconnection(getApplicationContext());
		Cursor cu=db.getData(sql);
		
		final Calendar c = Calendar.getInstance();

		int year  = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day   = c.get(Calendar.DAY_OF_MONTH);
		int count=0;
		String dump="";
		
			while (cu.moveToNext()) {
				//System.out.println("----------------");
				
				String date=cu.getString(4);
				String array[]=date.split("-");
				String dayOfMonth=array[0];
				
				dump=dump+":"+day;
				
				String temp=day+""+month+year;
				int yAxisDate=Integer.parseInt(temp);
				//System.out.println(array[0]);
				//System.out.println(day);
				
				if(dump.contains(array[0]))
				{
					//System.out.println("////////////////////////////////////"+cu.getString(1));
					stick.add(new ColoredStickEntity(Integer.parseInt(cu.getString(1)), 0,yAxisDate, Color.RED));
					count++;
				}
				day=day-1;
				

			}
			
			
			for(int i=0;i<7-count;i++)
			{
				//System.out.println("##########################################");
				
				String temp=day+""+month+year;
				int yAxisDate=Integer.parseInt(temp);
				stick.add(new ColoredStickEntity(0, 0,yAxisDate, Color.RED));
				day=day-1;
			}

			for (int i = stick.size(); i > 0; i--) {
				this.volc.add(stick.get(i - 1));
			}
		
	
	}
	
	private void initVOLC() {
		List<IStickEntity> stick = new ArrayList<IStickEntity>();
		this.volc = new ArrayList<IStickEntity>();
		
		stick.add(new ColoredStickEntity(406000, 0,20110824, Color.RED));
		stick.add(new ColoredStickEntity(232000, 0, 20110824, Color.RED));
		stick.add(new ColoredStickEntity(355000, 0, 20110823, Color.RED));
		stick.add(new ColoredStickEntity(437000, 0, 20110822, Color.RED));
		stick.add(new ColoredStickEntity(460000, 0, 20110819, Color.RED));
		stick.add(new ColoredStickEntity(422000, 0, 20110818, Color.RED));
		stick.add(new ColoredStickEntity(502000, 0, 20110817, Color.RED));




		for (int i = stick.size(); i > 0; i--) {
			this.volc.add(stick.get(i - 1));
		}

		// this.volc.addAll(stick);
	}

}