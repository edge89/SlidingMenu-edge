package com.slidingmenu.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.nineoldandroids.view.ViewHelper;
import com.slidingmenu.R;
import com.slidingmenu.utils.Application;
import com.slidingmenu.utils.TypeChange;

public class SlidingMenu extends HorizontalScrollView {

	private LinearLayout mWapper;
	private ViewGroup mMenu;// 不管是什么都是ViewGroup子类
	private ViewGroup mContent;

	private int mMenuRightPadding;

	private int mScreenWidth;// 屏幕寬度

	private int mMenuWidth;

	private boolean once = false;

	private boolean isOpen = false;

	private Message message = new Message() {

		@Override
		public void onMessage() {
		}
	};
	private boolean isScrollable;// scroll能否滑动

	/**
	 * 未使用自定义属性时使用
	 * 
	 * @param context
	 * @param attrs
	 */
	public SlidingMenu(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	/**
	 * 當使用了自定義的屬性時 會調用此構造方法
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public SlidingMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// 获取自定义的属性
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.SlidingMenu, defStyle, 0);
		int n = a.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.SlidingMenu_rightPadding:
				mMenuRightPadding = a.getDimensionPixelSize(attr,
						TypeChange.parsePxfromDp(50, context));
				break;
			}
		}

		a.recycle();
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);//
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);

		mScreenWidth = outMetrics.widthPixels;
		// mMenuRightPadding = TypeChange.parsePxfromDp(mMenuRightPadding,
		// context);
	}

	public SlidingMenu(Context context) {
		this(context, null);
	}

	/**
	 * 决定内部View的宽和高 以及自己的宽和高
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (!once) {

			mWapper = (LinearLayout) getChildAt(0);
			mMenu = (ViewGroup) mWapper.getChildAt(0);
			mContent = (ViewGroup) mWapper.getChildAt(1);
			mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth
					- mMenuRightPadding;
			mContent.getLayoutParams().width = mScreenWidth;
			once = true;
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (this.isOpen && ev.getX() <= mMenuWidth) {
			setScrollable(false);
		} else {
			setScrollable(true);
		}
		System.out.println(this.isOpen + "%" + isOpen);
		System.out.println(ev.getAction() + "%" + "SlidingMenu"
				+ super.onInterceptTouchEvent(ev));
		return super.onInterceptTouchEvent(ev);
	}

	public void setScrollable(boolean scrollable) {
		isScrollable = scrollable;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return super.dispatchTouchEvent(ev);
	}

	/**
	 * 决定子View放置的位置
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (!changed) {
			this.scrollTo(mMenuWidth, 0);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		if (isScrollable) {
			switch (action) {
			case MotionEvent.ACTION_UP:
				int scrollX = getScrollX();
				if (scrollX > mMenuWidth / 2) {
					this.smoothScrollTo(mMenuWidth, 0);
					isOpen = false;
				} else {
					this.smoothScrollTo(0, 0);
					isOpen = true;
				}
				return true;
			}
			return super.onTouchEvent(ev);
		} else {
			return false;
		}

	}

	/**
	 * 打开菜单
	 */
	public void openMenu() {
		if (isOpen)
			return;
		this.smoothScrollTo(0, 0);
		isOpen = true;

	}

	/**
	 * 关闭菜单
	 */
	public void closeMenu() {
		if (!isOpen)
			return;
		this.smoothScrollTo(mMenuWidth, 0);
		isOpen = false;
	}

	/**
	 * 切换菜单
	 */
	public void toggle() {
		if (isOpen) {
			closeMenu();
		} else {
			openMenu();
		}
	}

	// @Override
	// public boolean onInterceptTouchEvent(MotionEvent ev) {
	// // if (isOpen && ev.getX() < mScreenWidth - mMenuRightPadding) {
	// // return true;
	// // }
	// return super.onInterceptTouchEvent(ev);
	// }

	// @Override
	// public boolean dispatchKeyEvent(KeyEvent event) {
	// // TODO Auto-generated method stub
	// return super.dispatchKeyEvent(event);
	// }

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (Application.NEEDS_PROXY) {
			setAnimationGingerbread(l);
		} else {
			setAnimationKitKat(l);
		}

		// setAnimationKitKat(l);

	}

	private void setAnimationKitKat(int l) {
		float scale = 1.0f * l / mMenuWidth; // 从1到0
		mMenu.setAlpha(1 - 0.6f * scale);// 从0.4到1;
		mMenu.setTranslationX(mMenuWidth * scale * 0.6f);
		mContent.setPivotX(getScrollX());
		mContent.setPivotY(getHeight() / 2);
		mContent.setScaleX(scale + 0.8f * (1 - scale));//
		mContent.setScaleY(scale + 0.8f * (1 - scale));
	}

	/**
	 * @param l
	 */
	private void setAnimationGingerbread(int l) {
		float scale = 1.0f * l / mMenuWidth; // 从1到0
		ViewHelper.setAlpha(mMenu, 1 - 0.6f * scale);
		ViewHelper.setAlpha(mMenu, 1 - 0.6f * scale);// 从0.4到1;
		ViewHelper.setTranslationX(mMenu, mMenuWidth * scale * 0.6f);
		ViewHelper.setPivotX(mContent, getScrollX());
		ViewHelper.setPivotY(mContent, getHeight() / 2);
		ViewHelper.setScaleX(mContent, scale + 0.8f * (1 - scale));//
		ViewHelper.setScaleY(mContent, scale + 0.8f * (1 - scale));
	}

	public interface Message {
		public void onMessage();
	};
}
