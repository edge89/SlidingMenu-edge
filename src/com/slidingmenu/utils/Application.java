package com.slidingmenu.utils;

import android.os.Build;

public class Application {
	public static final boolean NEEDS_PROXY = Integer
			.valueOf(Build.VERSION.SDK).intValue() < Build.VERSION_CODES.HONEYCOMB;
}
