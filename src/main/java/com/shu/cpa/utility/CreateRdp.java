package com.shu.cpa.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

public class CreateRdp {
	public static  void rdpc(HttpServletRequest  request,String ip,String inIp) {
		String path = request.getSession().getServletContext().getRealPath("/upload");  
		
  		String te ="screen mode id:i:2"+
		"use multimon:i:0\n"+
		"desktopwidth:i:1366\n"+
		"desktopheight:i:768\n"+
		"session bpp:i:32\n"+
		"winposstr:s:0,1,0,0,1366,728\n"+
		"compression:i:1\n"+
		"keyboardhook:i:2\n"+
		"audiocapturemode:i:0\n"+
		"videoplaybackmode:i:1\n"+
		"connection type:i:2\n"+
		"displayconnectionbar:i:1\n"+
		"disable wallpaper:i:1\n"+
		"allow font smoothing:i:0\n"+
		"allow desktop composition:i:0\n"+
		"disable full window drag:i:1\n"+
		"disable menu anims:i:1\n"+
		"disable themes:i:0\n"+
		"disable cursor setting:i:0\n"+
		"bitmapcachepersistenable:i:1\n"+
		"full address:s:"+ip+"\n"+
		"audiomode:i:0\n"+
		"redirectprinters:i:1\n"+
		"redirectcomports:i:1\n"+
		"redirectsmartcards:i:1\n"+
		"redirectclipboard:i:1\n"+
		"redirectposdevices:i:0\n"+
		"redirectdirectx:i:1\n"+
		"autoreconnection enabled:i:1\n"+
		"authentication level:i:2\n"+
		"prompt for credentials:i:0\n"+
		"negotiate security layer:i:1\n"+
		"remoteapplicationmode:i:0\n"+
		"alternate shell:s:\n"+
		"shell working directory:s:\n"+
		"gatewayhostname:s:\n"+
		"gatewayusagemethod:i:4\n"+
		"gatewaycredentialssource:i:4\n"+
		"gatewayprofileusagemethod:i:0\n"+
		"promptcredentialonce:i:1\n"+
		"use redirection server name:i:0\n"+
		"devicestoredirect:s:*\n"+
		"drivestoredirect:s:*\n"+
		"username:s:Administrator\n";
      		System.err.println(te);
      byte a[]=te.getBytes();
      File file = new File(path, inIp+".rdp");
      System.err.println(path);
      if(file.exists()) {
    	  return ;
      }
      else {
    	  
    
      //输出的目的地
      try{  
         OutputStream out=new FileOutputStream(file,true);      //指向目的地的输出流
         out.write(a);                                    //向目的地写数据
         out.close();
         
         out=new FileOutputStream(file,true);             //准备向文件尾加内容

         out.close();
      }
      catch(IOException e) {
          System.out.println("Error "+e);
      }
	}
	  }
}

