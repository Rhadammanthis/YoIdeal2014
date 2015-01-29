package com.example.miyoideal;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.miyoideal.extra.PhotoManager;
import com.example.miyoideal.extra.ShareContentManager;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.OpenGraphAction;
import com.facebook.model.OpenGraphObject;
import com.facebook.scrumptious.ScrumptiousApplication;
import com.facebook.widget.FacebookDialog;

//import com.facebook.scrumptious.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;

public class ShareActivity extends Activity implements View.OnClickListener {
	PhotoManager photoManager;
	
    private static final int CAMERA = 0;
    private static final int GALLERY = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private File photoFile;
    
    //Facebook callback variable
    private UiLifecycleHelper uiHelper;
    
    private Button facebookButton;
    private Uri tempUri;
    
    @Override
    public void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_share);
    	uiHelper = new UiLifecycleHelper(this, null);
        uiHelper.onCreate(savedInstanceState);
		facebookButton = (Button) findViewById(R.id.shareFacebook);
		facebookButton.setOnClickListener(this);
		
    	//Uri uri = (Uri) bundle.get("URI");
    }
    
    @Override
	public void onClick(View v) {
    	AlertDialog.Builder builder = new AlertDialog.Builder(ShareActivity.this);
        CharSequence camera = getResources().getString(R.string.action_photo_camera);
        CharSequence gallery = getResources().getString(R.string.action_photo_gallery);
        builder.setCancelable(true).
                setItems(new CharSequence[] {camera, gallery}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == CAMERA) {
                            try {
								startCamera();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                        } else if (i == GALLERY) {
                        	startGallery();
                        } 
                    }
                });
        builder.show();	
	}
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//Add the menu layout to the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.base_action_bar, menu);
		return super.onCreateOptionsMenu(menu);
	}
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//If the Logo clicked
		Intent intent = new Intent(this, HomeActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		return super.onOptionsItemSelected(item);
	}
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if(requestCode == 1){
	    	if(data != null){
	    		uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
	    	        @Override
	    	        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
	    	            Log.e("Activity", String.format("Error: %s", error.toString()));
	    	        }
	    	        @Override
	    	        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
	    	            Log.i("Activity", "Success!");
	    	        }
	    	    });
	    		ShareContentManager shareContentManager = new ShareContentManager(data.getData());
	    		createFacebookDialog(shareContentManager);
	    	}
	    	else{
	    		Toast.makeText(this, "Se produjo un error al cargar la imagen", Toast.LENGTH_LONG).show();
	    	}
    	}
    	else if(requestCode == 2){
    		if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
	    		Log.d("OnActivityResult", photoManager.getUri().toString());
	    		 /*Bitmap bmp = (Bitmap)data.getExtras().get("data");
	    	        ByteArrayOutputStream stream = new ByteArrayOutputStream();
	    	 
	    	         bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
	    	         byte[] byteArray = stream.toByteArray(); // convert camera photo to byte array
	    	 
	    	         // save it in your external storage. 
	    	        FileOutputStream fo;
					try {
						fo = new FileOutputStream(photoFile);
						fo.write(byteArray);
		    	        fo.flush();
		    	        fo.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
	    		Toast.makeText(this, "Foto Almacenada en la galeria", Toast.LENGTH_SHORT).show();
			} 
			else { 
				Toast.makeText(this, "No se logro almacenar la imagen", Toast.LENGTH_SHORT).show(); 
			}
			Log.d("HomeActivity", "Termino");
    	}
	}
    
    public void createFacebookDialog(ShareContentManager shareContentManager){
    	OpenGraphObject diet = shareContentManager.loadContent(); 
		OpenGraphAction action = GraphObject.Factory.create(OpenGraphAction.class);
		action.setProperty("diet", diet);
		FacebookDialog shareDialog = new FacebookDialog.OpenGraphActionDialogBuilder(this, action, "yo-ideal:diet", "diet").build();
		uiHelper.trackPendingDialogCall(shareDialog.present());
    }
    
    @Override
    protected void onResume() {
        super.onResume();
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }
    
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    
    //Set the extras for ShareActivity and start the camera
  	public void startCamera() throws IOException{
  		photoManager = new PhotoManager();
  		File photoFile = photoManager.createFile();
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    if(takePictureIntent.resolveActivity(getPackageManager()) != null){
			//photoFile = createImageFile();
			takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoManager.getUri());
	    	//Save the image uri for later use
	  		startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
	    }	
  	}
  	
    //Set the extras for ShareActivity and start the gallery
  	public void startGallery(){
  		tempUri = null;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        String selectPicture = getResources().getString(R.string.select_picture);
        startActivityForResult(Intent.createChooser(intent, selectPicture), 1);
  	}	
}
