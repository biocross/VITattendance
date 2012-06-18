package com.battlex.vite;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.ProgressBar;
import android.widget.RemoteViews;

public class Update_service extends IntentService {
	private int progress = 10;
	ProgressBar progressBar;
	final Notification notification = new Notification(R.drawable.ic_launcher, "Downloading...", System
            .currentTimeMillis());
	
	
	public Update_service() {
		super("Update_service");
		// TODO Auto-generated constructor stub
	}
	
	@Override  
	public int onStartCommand(Intent intent, int flags, int startId) {
		final PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
		
        notification.flags = notification.flags | Notification.FLAG_ONGOING_EVENT;
        notification.contentView = new RemoteViews(getApplicationContext().getPackageName(), R.layout.download_progress);
        notification.contentIntent = pendingIntent;
        notification.contentView.setImageViewResource(R.id.status_icon, R.drawable.ic_launcher);
        notification.contentView.setTextViewText(R.id.status_text, "Downloading");
        notification.contentView.setProgressBar(R.id.status_progress, 100, progress, false);
		 return super.onStartCommand(intent, flags, startId);  
	}
	

	@Override
	protected void onHandleIntent(Intent intent) {
		
		getApplicationContext();
		final NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(
                Context.NOTIFICATION_SERVICE);
		 
		// TODO Auto-generated method stub
		
		notificationManager.notify(42, notification);
            

            try {
            	URL url = new URL(intent.getStringExtra("url"));
            	URLConnection connection = url.openConnection();
                connection.connect();
                // this will be useful so that you can show a typical 0-100% progress bar
                int fileLength = connection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream());
                String pathy =  Environment.getExternalStorageDirectory().toString().concat("/update.apk");
                
                OutputStream output = new FileOutputStream(pathy);

                byte data[] = new byte[1024];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    progress = (int) (total * 100 / fileLength);
                    output.write(data, 0, count);
                    notification.contentView.setProgressBar(R.id.status_progress, 100, progress, false);
                    // inform the progress bar of updates in progress
                    notificationManager.notify(42, notification);
                }
                

                output.flush();
                output.close();
                input.close();
                
                notificationManager.cancel(42);
                openFile(pathy);
                
            } catch (Exception e) {
				e.printStackTrace();
			}
        

        // remove the notification (we're done)
        
	}
	protected void openFile(String fileName) {
	    Intent install = new Intent(Intent.ACTION_VIEW);
	    install.setDataAndType(Uri.fromFile(new File(fileName)),
	            "MIME-TYPE");
	    startActivity(install);
	}

}
