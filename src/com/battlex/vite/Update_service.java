package com.battlex.vite;
/**
 * @author Saurabh
 *                     APP DOWNLOAD AND INSTALL SERVICE
 */
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
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.RemoteViews;

public class Update_service extends IntentService {
	private int progress = 10;
	ProgressBar progressBar;
	final Notification notification = new Notification(R.drawable.down, "Downloading...", System
            .currentTimeMillis());
	
	private Handler handler;  
	
	public Update_service() {
		super("Update_service");
	
	}
	
	@Override  
	public int onStartCommand(Intent intent, int flags, int startId) {
		handler = new Handler();  
		final PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
		
        notification.flags = notification.flags | Notification.FLAG_ONGOING_EVENT;
        notification.contentView = new RemoteViews(getApplicationContext().getPackageName(), R.layout.download_progress);
        notification.contentIntent = pendingIntent;
        notification.contentView.setImageViewResource(R.id.status_icon, R.drawable.down);
        notification.contentView.setTextViewText(R.id.status_text, "Downloading");
        notification.contentView.setProgressBar(R.id.status_progress, 100, progress, false);
		 return super.onStartCommand(intent, flags, startId);  
	}
	

	@Override
	protected void onHandleIntent(Intent intent) {
		
		getApplicationContext();
		final NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(
                Context.NOTIFICATION_SERVICE);
		 
	
		
		notificationManager.notify(42, notification);
            

            try {
            	URL url = new URL(intent.getStringExtra("url"));
            	URLConnection connection = url.openConnection();
                connection.connect();
                
                int fileLength = connection.getContentLength();

                
                InputStream input = new BufferedInputStream(url.openStream());
                String pathy =  Environment.getExternalStorageDirectory().toString().concat("/update.apk");
                
                OutputStream output = new FileOutputStream(pathy);

                byte data[] = new byte[1024];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    
                    progress = (int) (total * 100 / fileLength);
                    output.write(data, 0, count);
                    notification.contentView.setProgressBar(R.id.status_progress, 100, progress, false);
                    
                    notificationManager.notify(42, notification);
                }
                output.flush();
                output.close();
                input.close();
                notificationManager.cancel(42);
                openFile(pathy);                
            } catch (Exception e) {
				e.printStackTrace();
				notificationManager.cancel(42);
			}      
	}
	protected void openFile(String fileName) {
		handler.post(new Runnable() {  
			   @Override  
			   public void run() {  
				   Intent intent = new Intent(Intent.ACTION_VIEW);
				   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/update.apk")), "application/vnd.android.package-archive");
					startActivity(intent);   
			   }  
			}); }
	

}
