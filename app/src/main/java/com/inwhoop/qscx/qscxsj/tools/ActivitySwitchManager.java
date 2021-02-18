package com.inwhoop.qscx.qscxsj.tools;

import android.content.Context;
import android.content.Intent;

public class ActivitySwitchManager
{
	/**
	 * 模拟Home键 的效果
	 * @param context
	 */
	public static void simulationPressHomekey(Context context)
	{
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 注意
		intent.addCategory(Intent.CATEGORY_HOME);
		context.startActivity(intent);
	}
	
}
