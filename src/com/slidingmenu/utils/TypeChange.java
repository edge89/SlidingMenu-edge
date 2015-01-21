package com.slidingmenu.utils;

import android.content.Context;
import android.renderscript.Type;
import android.util.TypedValue;

public class TypeChange {
	/**
	 * 将DP转换为px
	 * 
	 * @param dp
	 * @param context
	 * @return int
	 */
	public static int parsePxfromDp(int dp, Context context) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context
				.getResources().getDisplayMetrics());
	}

	public static int parsePxfromSp(int sp, Context context) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 50, context
				.getResources().getDisplayMetrics());
	}
}
