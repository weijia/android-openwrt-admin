package com.devil.wrtcontrol;

import android.R.integer;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.devil.wrtcontrol.utils.*;
import com.devil.wrtcontrol.wrtrpc.API;
import com.devil.wrtcontrol.*;

public class WRTControlActivity extends Activity {
	
	public static final String CFG_NAME = "WRTTool";
	public static String ERR = "";
	public static boolean DEBUG = true;
	
	/** Static vars for settings */
	public static String USR = "";
	public static String PASS = "";
	public static String PORT = "";
	public static String HOST = "";
	public static String Token = "";
	public static int[] NOT_IDS;
	
	public Utils utils = null;
	/*private void notification_ids()
	{
		NOT_IDS['MAIN'] = 0; 
	}*/
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        final Utils utils = new Utils( getApplicationContext() );
        
        setContentView(R.layout.main);
        /* Loads Settings */
        if( LoadSettings() )
        {
        	utils.Popper( "Settings Loaded", 3);
        }
        else
        {
        	utils.Popper( "Failed to Load Settings", 3);
        }
        
        final Button save = (Button)findViewById(R.id.savebtn);
        save.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if( SaveSettings() )
				{
					utils.Popper( "Success", 3);
				}
				else
				{
					utils.Popper("An Error has occured while saving the config", 3);
				}
			}
		});
        
        final Button login = (Button)findViewById(R.id.loginbtn);
        login.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				API auth = new API();
				String token = auth.Auth();
				Log.v(CFG_NAME, token);
				utils.Popper(auth.Auth(), 10);
			}
		});
        
        final EditText hosttxt = (EditText)findViewById(R.id.hosttxt);
        hosttxt.setOnKeyListener(new View.OnKeyListener() {
			
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				HOST = hosttxt.getText().toString();
				return true;
			}
		});
        
        final EditText passtxt = (EditText)findViewById(R.id.passtxt);
        passtxt.setOnKeyListener(new View.OnKeyListener() {
			
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				HOST = passtxt.getText().toString();
				return true;
			}
		});
        
        this.notification_create();
    }
    
    /** Creates the main inflated menu containing the other menus to navigate etc*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_screens, menu);
        return true;        
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.lan:
            setContentView(R.layout.lan_view);
            break;
        case R.id.wan:
            setContentView(R.layout.main);
            break;
        default:
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
    
    /** Called then the activity goes to hell */
    @Override
    public void onStop()
    {
    	super.onStop();    	
    	SaveSettings();
    	utils.Popper("Bubyez", 2);
    }
    
    /**
     * Handles setting save
     * 
     * @return boolean true on save settings, false on error
     */
    public boolean SaveSettings()
    {
    	try
		{
			SharedPreferences settings = this.getSharedPreferences(CFG_NAME, 0);
	    	SharedPreferences.Editor editor = settings.edit();
	    	
	    	EditText hosttxt = (EditText)findViewById(R.id.hosttxt);
	        
	        EditText porttxt = (EditText)findViewById(R.id.porttxt);
	        
	        EditText usertxt = (EditText)findViewById(R.id.usertxt);
	        
	        EditText passtxt = (EditText)findViewById(R.id.passtxt);
	        
	    	editor.putString( "Hostname", hosttxt.getText().toString());
	    	editor.putString( "Port", porttxt.getText().toString());
	    	editor.putString( "Username", usertxt.getText().toString());
	    	editor.putString( "Pass", passtxt.getText().toString());
	    	editor.commit();
	    	LoadSettings();
		}catch( Exception e )
		{
			return false;
		}
		return true;
    }
    
    /**
     * Handles Setting Load
     * 
     * @return boolean true on load success
     */
    public boolean LoadSettings()
    {
    	try
    	{
    		SharedPreferences settings = getSharedPreferences(CFG_NAME, 0);
            String hostname = settings.getString( "Hostname", "192.168.1.1");
            String port = settings.getString( "Port", "80");
            String user = settings.getString( "Username", "root");
            String pass = settings.getString( "Pass", "");
            
            /* get Hostname saved pref */
            EditText hosttxt = (EditText)findViewById(R.id.hosttxt);
            HOST = hostname;
            hosttxt.setText( hostname );
            
            /* get Port number saved pref*/
            EditText porttxt = (EditText)findViewById(R.id.porttxt);
            PORT = port;
            porttxt.setText( port );
            
            /* get Username saved pref */
            EditText usertxt = (EditText)findViewById(R.id.usertxt);
            USR = user;
            usertxt.setText( user);
            
            /* get Password saved pref */
            EditText passtxt = (EditText)findViewById(R.id.passtxt);
            PASS = pass;
            passtxt.setText( pass );
            
    	}catch( Exception e )
    	{
    		return false;
    	}
    	return true;
    	
    }
    
    
    public void notification_create() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        int icon = R.drawable.icon;
        CharSequence text = "APS a correr";
        CharSequence contentTitle = "APS";
        CharSequence contentText = "APS encontra-se a correr";
        long when = System.currentTimeMillis();

        Intent intent = new Intent(this, Notification.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                intent, 0);

        //ISTO É Q TORNA A NOTIFICAÇÃO PERNAMENTE

        Notification notification = new Notification(icon, text, when);

        //FIM :D

        notification.flags = Notification.FLAG_ONGOING_EVENT;
        notification.setLatestEventInfo(this, contentTitle, contentText,
                contentIntent);

        notificationManager.notify(0, notification);
    }
}