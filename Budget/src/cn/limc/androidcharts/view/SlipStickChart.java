/*
 * SlipStickChart.java
 * Android-Charts
 *
 * Created by limc on 2014.
 *
 * Copyright 2011 limc.cn All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.limc.androidcharts.view;

import cn.limc.androidcharts.common.ISlipable;
import cn.limc.androidcharts.entity.IMeasurable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * <p>
 * en
 * </p>
 * <p>
 * jp
 * </p>
 * <p>
 * cn
 * </p>
 * 
 * @author limc
 * @version v1.0 2014-1-20 下午2:03:08
 * 
 */
public class SlipStickChart extends StickChart implements ISlipable {

	public static final int DEFAULT_DISPLAY_FROM = 0;
	public static final int DEFAULT_DISPLAY_NUMBER = 50;
	public static final int DEFAULT_MIN_DISPLAY_NUMBER = 20;
	public static final int DEFAULT_ZOOM_BASE_LINE = ZOOM_BASE_LINE_CENTER;

	protected int displayFrom = DEFAULT_DISPLAY_FROM;
	protected int displayNumber = DEFAULT_DISPLAY_NUMBER;
	protected int minDisplayNumber = DEFAULT_MIN_DISPLAY_NUMBER;
	protected int zoomBaseLine = DEFAULT_ZOOM_BASE_LINE;

	protected OnSlipGestureListener onSlipGestureListener;

	/**
	 * <p>
	 * Constructor of SlipStickChart
	 * </p>
	 * <p>
	 * SlipStickChart类对象的构造函数
	 * </p>
	 * <p>
	 * SlipStickChartのコンストラクター
	 * </p>
	 * 
	 * @param context
	 */
	public SlipStickChart(Context context) {
		super(context);
	}

	/**
	 * <p>
	 * Constructor of SlipStickChart
	 * </p>
	 * <p>
	 * SlipStickChart类对象的构造函数
	 * </p>
	 * <p>
	 * SlipStickChartのコンストラクター
	 * </p>
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public SlipStickChart(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	/**
	 * <p>
	 * Constructor of SlipStickChart
	 * </p>
	 * <p>
	 * SlipStickChart类对象的构造函数
	 * </p>
	 * <p>
	 * SlipStickChartのコンストラクター
	 * </p>
	 * 
	 * @param context
	 * @param attrs
	 */
	public SlipStickChart(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * <p>Called when is going to draw this chart<p> <p>チャートを書く前、メソッドを呼ぶ<p>
	 * <p>绘制图表时调用<p>
	 * 
	 * @param canvas
	 * 
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

	@Override
	protected void drawSticks(Canvas canvas) {
		if (null == stickData) {
			return;
		}
		if (stickData.size() == 0) {
			return;
		}

		Paint mPaintStick = new Paint();
		mPaintStick.setColor(stickFillColor);

		float stickWidth = getDataQuadrantPaddingWidth() / getDisplayNumber()
				- stickSpacing;
		float stickX = getDataQuadrantPaddingStartX();

		for (int i = getDisplayFrom(); i < getDisplayTo(); i++) {
			IMeasurable stick = stickData.get(i);
			float highY = (float) ((1f - (stick.getHigh() - minValue)
					/ (maxValue - minValue))
					* (getDataQuadrantPaddingHeight()) + getDataQuadrantPaddingStartY());
			float lowY = (float) ((1f - (stick.getLow() - minValue)
					/ (maxValue - minValue))
					* (getDataQuadrantPaddingHeight()) + getDataQuadrantPaddingStartY());

			// stick or line?
			if (stickWidth >= 2f) {
				canvas.drawRect(stickX, highY, stickX + stickWidth, lowY,
						mPaintStick);
			} else {
				canvas.drawLine(stickX, highY, stickX, lowY, mPaintStick);
			}

			// next x
			stickX = stickX + stickSpacing + stickWidth;
		}
	}

	protected float olddistance = 0f;
	protected float newdistance = 0f;

	protected PointF startPointA;
	protected PointF startPointB;


	/*
	 * (non-Javadoc)
	 * 
	 * @param event
	 * @return 
	 * @see cn.limc.androidcharts.view.StickChart#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// valid
		if (!isValidTouchPoint(event.getX(), event.getY())) {
			return false;
		}

		if (null == stickData || stickData.size() == 0) {
			return false;
		}

		final float MIN_LENGTH = (super.getWidth() / 40) < 5 ? 5 : (super
				.getWidth() / 50);

		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		// 设置拖拉模式
		case MotionEvent.ACTION_DOWN:
			touchMode = TOUCH_MODE_SINGLE;
			if (event.getPointerCount() == 1) {
				touchPoint = calcTouchedPoint(event.getX(), event.getY());
				if (onTouchGestureListener != null) {
					onTouchGestureListener.onTouchDown(touchPoint,
							getSelectedIndex());
				}
				super.postInvalidate();
				// Notifier
				super.notifyEventAll(this);
			}
			break;
		case MotionEvent.ACTION_UP:
			touchMode = TOUCH_MODE_NONE;
			startPointA = null;
			startPointB = null;
			if (event.getPointerCount() == 1) {
				touchPoint = calcTouchedPoint(event.getX(), event.getY());
				if (onTouchGestureListener != null) {
					onTouchGestureListener.onTouchUp(touchPoint,
							getSelectedIndex());
				}
				super.postInvalidate();
				// Notifier
				super.notifyEventAll(this);
			}
			break;
		case MotionEvent.ACTION_POINTER_UP:
			touchMode = TOUCH_MODE_NONE;
			startPointA = null;
			startPointB = null;
			return super.onTouchEvent(event);
			// 设置多点触摸模式
		case MotionEvent.ACTION_POINTER_DOWN:
			olddistance = calcDistance(event);
			if (olddistance > MIN_LENGTH) {
				touchMode = TOUCH_MODE_MULTI;
				startPointA = new PointF(event.getX(0), event.getY(0));
				startPointB = new PointF(event.getX(1), event.getY(1));
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (touchMode == TOUCH_MODE_MULTI) {
				newdistance = calcDistance(event);
				if (newdistance > MIN_LENGTH) {
					if (startPointA.x >= event.getX(0)
							&& startPointB.x >= event.getX(1)) {
						moveRight();
					} else if (startPointA.x <= event.getX(0)
							&& startPointB.x <= event.getX(1)) {
						moveLeft();
					} else {
						if (Math.abs(newdistance - olddistance) > MIN_LENGTH) {
							if (newdistance > olddistance) {
								zoomIn();
							} else {
								zoomOut();
							}
							// 重置距离
							olddistance = newdistance;
						}
					}
					startPointA = new PointF(event.getX(0), event.getY(0));
					startPointB = new PointF(event.getX(1), event.getY(1));

					super.postInvalidate();
					super.notifyEventAll(this);
				}
			} else {
				// 单点拖动效果
				if (event.getPointerCount() == 1) {
					float moveXdistance = Math.abs(event.getX() - touchPoint.x);
					float moveYdistance = Math.abs(event.getY() - touchPoint.y);

					if (moveXdistance > TOUCH_MOVE_MIN_DISTANCE
							|| moveYdistance > TOUCH_MOVE_MIN_DISTANCE) {
						touchPoint = calcTouchedPoint(event.getX(),
								event.getY());

						// call back to listener
						if (onTouchGestureListener != null) {
							onTouchGestureListener.onTouchMoved(touchPoint,
									getSelectedIndex());
						}
					}

					super.postInvalidate();
					super.notifyEventAll(this);
				}
			}
			break;
		}
		return true;
	}

	/* (non-Javadoc)
	 *  
	 * @see cn.limc.androidcharts.common.ISlipable#moveRight() 
	 */
	public void moveRight() {
		int dataSize = stickData.size();
		if (getDisplayTo() < dataSize - SLIP_STEP) {
			setDisplayFrom(getDisplayFrom() + SLIP_STEP);
		} else {
			setDisplayFrom(dataSize - getDisplayNumber());
		}

		// 处理displayFrom越界
		if (getDisplayTo() >= dataSize) {
			setDisplayFrom(dataSize - getDisplayNumber());
		}

		// Listener
		if (onSlipGestureListener != null) {
			onSlipGestureListener.onSlip(SLIP_DIRECTION_RIGHT,
					getDisplayFrom(), getDisplayNumber());
		}
	}

	/* (non-Javadoc)
	 *  
	 * @see cn.limc.androidcharts.common.ISlipable#moveLeft() 
	 */
	public void moveLeft() {
		int dataSize = stickData.size();

		if (getDisplayFrom() <= SLIP_STEP) {
			setDisplayFrom(0);
		} else if (getDisplayFrom() > SLIP_STEP) {
			setDisplayFrom(getDisplayFrom() - SLIP_STEP);
		} else {

		}

		// 处理displayFrom越界
		if (getDisplayTo() >= dataSize) {
			setDisplayFrom(dataSize - getDisplayNumber());
		}

		// Listener
		if (onSlipGestureListener != null) {
			onSlipGestureListener.onSlip(SLIP_DIRECTION_LEFT, getDisplayFrom(),
					getDisplayNumber());
		}
	}
	
	/*
	 * (non-Javadoc)
	 *  
	 * @see cn.limc.androidcharts.view.StickChart#zoomIn()
	 */
	@Override
	public void zoomIn() {
		if (getDisplayNumber() > getMinDisplayNumber()) {
			// 区分缩放方向
			if (zoomBaseLine == ZOOM_BASE_LINE_CENTER) {
				setDisplayNumber(getDisplayNumber() - ZOOM_STEP);
				setDisplayFrom(getDisplayFrom() + ZOOM_STEP / 2);
			} else if (zoomBaseLine == ZOOM_BASE_LINE_LEFT) {
				setDisplayNumber(getDisplayNumber() - ZOOM_STEP);
			} else if (zoomBaseLine == ZOOM_BASE_LINE_RIGHT) {
				setDisplayNumber(getDisplayNumber() - ZOOM_STEP);
				setDisplayFrom(getDisplayFrom() + ZOOM_STEP);
			}

			// 处理displayNumber越界
			if (getDisplayNumber() < getMinDisplayNumber()) {
				setDisplayNumber(getMinDisplayNumber());
			}

			// 处理displayFrom越界
			if (getDisplayTo() >= stickData.size()) {
				setDisplayFrom(stickData.size() - getDisplayNumber());
			}

			// Listener
			if (onZoomGestureListener != null) {
				onZoomGestureListener.onZoom(ZOOM_IN, getDisplayFrom(),
						getDisplayNumber());
			}
		}
	}


	/*
	 * (non-Javadoc)
	 *  
	 * @see cn.limc.androidcharts.view.StickChart#zoomOut()
	 */
	@Override
	public void zoomOut() {
		if (getDisplayNumber() < stickData.size() - 1) {
			if (getDisplayNumber() + ZOOM_STEP > stickData.size() - 1) {
				setDisplayNumber(stickData.size() - 1);
				setDisplayFrom(0);
			} else {
				// 区分缩放方向
				if (zoomBaseLine == ZOOM_BASE_LINE_CENTER) {
					setDisplayNumber(getDisplayNumber() + ZOOM_STEP);
					if (getDisplayFrom() > 1) {
						setDisplayFrom(getDisplayFrom() - ZOOM_STEP / 2);
					} else {
						setDisplayFrom(0);
					}
				} else if (zoomBaseLine == ZOOM_BASE_LINE_LEFT) {
					setDisplayNumber(getDisplayNumber() + ZOOM_STEP);
				} else if (zoomBaseLine == ZOOM_BASE_LINE_RIGHT) {
					setDisplayNumber(getDisplayNumber() + ZOOM_STEP);
					if (getDisplayFrom() > ZOOM_STEP) {
						setDisplayFrom(getDisplayFrom() - ZOOM_STEP);
					} else {
						setDisplayFrom(0);
					}
				}
			}

			if (getDisplayTo() >= stickData.size()) {
				setDisplayNumber(stickData.size() - getDisplayFrom());
			}

			// Listener
			if (onZoomGestureListener != null) {
				onZoomGestureListener.onZoom(ZOOM_OUT, getDisplayFrom(),
						getDisplayNumber());
			}
		}
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @return 
	 * @see cn.limc.androidcharts.view.StickChart#getDisplayFrom()
	 */
	@Override
	public int getDisplayFrom() {
		return displayFrom;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @param displayFrom 
	 * @see cn.limc.androidcharts.view.StickChart#setDisplayFrom(int)
	 */
	@Override
	public void setDisplayFrom(int displayFrom) {
		this.displayFrom = displayFrom;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @return 
	 * @see cn.limc.androidcharts.view.StickChart#getDisplayTo()
	 */
	@Override
	public int getDisplayTo() {
		return displayFrom + displayNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @param displayTo 
	 * @see cn.limc.androidcharts.view.StickChart#setDisplayTo(int)
	 */
	@Override
	public void setDisplayTo(int displayTo) {
		// TODO
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @return 
	 * @see cn.limc.androidcharts.view.StickChart#getDisplayNumber()
	 */
	@Override
	public int getDisplayNumber() {
		return displayNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @param displayNumber 
	 * @see cn.limc.androidcharts.view.StickChart#setDisplayNumber(int)
	 */
	@Override
	public void setDisplayNumber(int displayNumber) {
		this.displayNumber = displayNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @return 
	 * @see cn.limc.androidcharts.view.StickChart#getMinDisplayNumber()
	 */
	@Override
	public int getMinDisplayNumber() {
		return minDisplayNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @param minDisplayNumber 
	 * @see cn.limc.androidcharts.view.StickChart#setMinDisplayNumber(int)
	 */
	@Override
	public void setMinDisplayNumber(int minDisplayNumber) {
		this.minDisplayNumber = minDisplayNumber;
	}

	
	/**
	 * @return the zoomBaseLine
	 */
	public int getZoomBaseLine() {
		return zoomBaseLine;
	}

	/**
	 * @param zoomBaseLine
	 *            the zoomBaseLine to set
	 */
	public void setZoomBaseLine(int zoomBaseLine) {
		this.zoomBaseLine = zoomBaseLine;
	}

	/**
	 * @param listener
	 *            the OnSlipGestureListener to set
	 */
	public void setOnSlipGestureListener(OnSlipGestureListener listener) {
		this.onSlipGestureListener = listener;
	}
}
