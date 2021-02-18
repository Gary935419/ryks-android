package com.inwhoop.qscx.qscxsj.tools;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

public class TopBarColorManager
{
	/**
	 * Apply KitKat specific translucency.
	 */
	public static void applyKitKatTranslucency(Activity activity)
	{

		// KitKat translucent navigation/status bar.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
		{
//			setTranslucentStatus(activity, true);
			SystemBarTintManager mTintManager = new SystemBarTintManager(activity);
			mTintManager.setStatusBarTintEnabled(true);
			mTintManager.setNavigationBarTintEnabled(true);
//			mTintManager.setTintColor(0xff527ed7);
			mTintManager.setTintColor(Color.parseColor("#000000"));
			// mTintManager.setTintDrawable(UIElementsHelper
			// .getGeneralActionBarBackground(this));

		}

	}

	@TargetApi(19)
	public static void setTranslucentStatus(Activity activity, boolean on)
	{
		Window win = activity.getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on)
		{
			winParams.flags |= bits;
		} else
		{
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}
}
