package com.devil.wrtcontrol.wrtrpc;

import org.alexd.jsonrpc.JSONRPCClient;

import android.util.Log;
import com.devil.wrtcontrol.WRTControlActivity;
import org.alexd.jsonrpc.JSONRPCParams;

public class API
{
	public String Auth()
	{

		try
		{
			JSONRPCClient client = JSONRPCClient.create("http://" + WRTControlActivity.HOST +
                    "/cgi-bin/luci/rpc/auth", JSONRPCParams.Versions.VERSION_2);
			client.setConnectionTimeout( 2000 );
			client.setSoTimeout( 2000 );
			String token = client.callString("login", WRTControlActivity.USR, WRTControlActivity.PASS );
			WRTControlActivity.Token = token;
			Log.v(WRTControlActivity.CFG_NAME, token );
			TestAuth();
			return token;
		}catch( Exception e )
		{
			Log.wtf(WRTControlActivity.CFG_NAME, e);
		}
		return "Fucking Fail";
	}
	
	public String TestAuth()
	{
		try
		{
			JSONRPCClient client = JSONRPCClient.create("http://" + WRTControlActivity.HOST +
                    "/cgi-bin/luci/rpc/uci?auth="+WRTControlActivity.Token, JSONRPCParams.Versions.VERSION_2);
			client.setConnectionTimeout( 2000 );
			client.setSoTimeout( 2000 );
			String hostname = client.callString("get", "wireless.radio0.hwmode" );
			Log.v(WRTControlActivity.CFG_NAME, hostname );
			return hostname;
		}catch( Exception e )
		{
			Log.wtf(WRTControlActivity.CFG_NAME, e);
		}
		return "Fucking Fail";
	}
}
