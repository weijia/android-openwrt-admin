package com.devil.wrtcontrol.utils;

import com.devil.wrtcontrol.WRTControlActivity;

import android.content.Context;
import android.widget.Toast;

public class Utils extends WRTControlActivity{
	public static String[] notifications;
	
	public static Context Appcontext;
	
	public Utils( Context context )
	{
		Appcontext = context;
	}
	
	public void Popper( CharSequence text, int duration )
	{
		Toast toast = Toast.makeText(Appcontext, text, duration);
		toast.show();
	}
	
	public static void addNotification( String name )
	{
		/* I'll Lol if this works */
		try
		{
			notifications[notifications.length] = name;
		}
		catch( Exception e )
		{
			
		}
		
		
	}
	
}
