package com.example.miyoideal;

import java.util.HashMap;

import com.example.DB.*;
import com.example.miyoideal.extra.DialyNotificationReceiver;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity implements SQLiteFactory{
	
	//layout components
	private Button button;
	
	//global variables;
	private int width;
	private int height;
	
	//database
	private SQLiteUserDB userDB;
	private ContentValues values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Diagnostico");
        
        //Calculates Screen size
    	Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		height = size.y;	
        
        button = (Button) findViewById(R.id.button1);
        
        userDB = new SQLiteUserDB(this);
		
		userDB.getReadableDatabase();
		String query = "Select * FROM " + "usuario" + " WHERE " + "id_usuario" + " =  \"" + "1" + "\"";
		values = new ContentValues();
		Cursor cursor = userDB.getReadableDatabase().rawQuery(query, null);
		
		if(cursor.moveToFirst())
		{
			Log.d("main", "entre");
			
			Intent intent = new Intent(MainActivity.this, HomeActivity.class);
			startActivity(intent);
		}
        
        button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				initComponenteDB();
				initDietaDB();
				initControlDB();
				initProgramaDB();
				
				userDB.getWritableDatabase();
				values.put("id_usuario", "1");
				values.put("nombre", "Hugo");
				values.put("peso", "85 kg");
				userDB.getWritableDatabase().insert("usuario", null, values);
				userDB.close();
				Intent intent = new Intent(MainActivity.this, HomeActivity.class);
				startActivity(intent);
			}
		});	
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
    	// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	public void initDietaDB() {
		// TODO Auto-generated method stub
		SQLiteDietaDB db = new SQLiteDietaDB(this);
		db.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("id_dieta", "1");
		values.put("nombre", "Dieta 1");
		values.put("tipo", "Perdida de Peso");
		values.put("duracion", "7");
		db.getWritableDatabase().insert("dieta", null, values);
		
		values = new ContentValues();
		values.put("id_dieta", "2");
		values.put("nombre", "Dieta 2");
		values.put("tipo", "Perdida de Peso");
		values.put("duracion", "3");
		db.getWritableDatabase().insert("dieta", null, values);
		db.close();
		
	}

	@Override
	public void initComponenteDB() {
		// TODO Auto-generated method stub
		SQLiteComponenteDB db = new SQLiteComponenteDB(this);
		db.getWritableDatabase();
		
		//1
		ContentValues values = new ContentValues();
		values.put("id_componente", "1");
		values.put("id_dieta", "1");
		values.put("dia", "1");
		values.put("alimento", "Desayuno");
		values.put("activo", "no");
		values.put("descripcion", "Desayuno DIETA UNO D�a 1 Alimento 1");
		db.getWritableDatabase().insert("componente", null, values);
		
		//2
		values = new ContentValues();
		values.put("id_componente", "2");
		values.put("id_dieta", "1");
		values.put("dia", "1");
		values.put("alimento", "Desayuno");
		values.put("activo", "no");
		values.put("descripcion", "Desayuno DIETA UNO D�a 1 Alimento 2");
		db.getWritableDatabase().insert("componente", null, values);
		
		//3
		values = new ContentValues();
		values.put("id_componente", "3");
		values.put("id_dieta", "1");
		values.put("dia", "1");
		values.put("alimento", "Desayuno");
		values.put("activo", "no");
		values.put("descripcion", "Desayuno DIETA UNO D�a 1 Alimento 3");
		db.getWritableDatabase().insert("componente", null, values);
		
		//4
		values = new ContentValues();
		values.put("id_componente", "4");
		values.put("id_dieta", "1");
		values.put("dia", "1");
		values.put("alimento", "Colacion");
		values.put("activo", "no");
		values.put("descripcion", "Colacion DIETA UNO D�a 1 Alimento 1");
		db.getWritableDatabase().insert("componente", null, values);
		
		//5
		values = new ContentValues();
		values.put("id_componente", "5");
		values.put("id_dieta", "1");
		values.put("dia", "1");
		values.put("alimento", "Colacion");
		values.put("activo", "no");
		values.put("descripcion", "Colacion DIETA UNO D�a 1 Alimento 2");
		db.getWritableDatabase().insert("componente", null, values);
		
		//6
		values = new ContentValues();
		values.put("id_componente", "6");
		values.put("id_dieta", "1");
		values.put("dia", "1");
		values.put("alimento", "Colacion");
		values.put("activo", "no");
		values.put("descripcion", "Colacion DIETA UNO D�a 1 Alimento 3");
		db.getWritableDatabase().insert("componente", null, values);
		
		///////////////////////////////////////////////////////////////////////////////////////////////
		//segundo dia
		
		//7
		values = new ContentValues();
		values.put("id_componente", "7");
		values.put("id_dieta", "1");
		values.put("dia", "2");
		values.put("alimento", "Desayuno");
		values.put("activo", "no");
		values.put("descripcion", "Desayuno DIETA UNO D�a 2 Alimento 3");
		db.getWritableDatabase().insert("componente", null, values);
		
		//88
		values = new ContentValues();
		values.put("id_componente", "8");
		values.put("id_dieta", "1");
		values.put("dia", "2");
		values.put("alimento", "Colacion");
		values.put("activo", "no");
		values.put("descripcion", "Colacion DIETA UNO D�a 2 Alimento 1");
		db.getWritableDatabase().insert("componente", null, values);
		
		//9
		values = new ContentValues();
		values.put("id_componente", "9");
		values.put("id_dieta", "1");
		values.put("dia", "2");
		values.put("alimento", "Colacion");
		values.put("activo", "no");
		values.put("descripcion", "Colacion DIETA UNO D�a 2 Alimento 2");
		db.getWritableDatabase().insert("componente", null, values);
		
		//10
		values = new ContentValues();
		values.put("id_componente", "10");
		values.put("id_dieta", "1");
		values.put("dia", "2");
		values.put("alimento", "Colacion");
		values.put("activo", "no");
		values.put("descripcion", "Colacion DIETA UNO D�a 2 Alimento 3");
		db.getWritableDatabase().insert("componente", null, values);
		
		db.close();
	}


	@Override
	public void initControlDB() {
		// TODO Auto-generated method stub
		SQLiteControl db = new SQLiteControl(this);
		db.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("id_control", "1");
		values.put("id_dieta", "0");
		values.put("dia", "0");
		db.getWritableDatabase().insert("control", null, values);

		//Set the notification receiver 
		DialyNotificationReceiver dialyNotification = new DialyNotificationReceiver();
		dialyNotification.setAlarm(this);
		
		db.close();
	}


	@Override
	public void initProgramaDB() {
		// TODO Auto-generated method stub
		SQLiteProgramaDB db = new SQLiteProgramaDB(this);
		db.getWritableDatabase();
		
		//bajo
		ContentValues values = new ContentValues();
		values.put("id_programa", "1");
		values.put("nivel", "bajo");
		values.put("titulo", "Lagartijas");
		values.put("descripcion", "Hacer 1 series de 8 repeticiones");
		values.put("hora", "10 am");
		db.getWritableDatabase().insert("programa", null, values);
		
		//medio
		values = new ContentValues();
		values.put("id_programa", "2");
		values.put("nivel", "medio");
		values.put("titulo", "Abdominales");
		values.put("descripcion", "Hacer 3 series de 8 repeticiones");
		values.put("hora", "3 pm");
		db.getWritableDatabase().insert("programa", null, values);
		
		//alto
		new ContentValues();
		values.put("id_programa", "3");
		values.put("nivel", "alto");
		values.put("titulo", "Carrera");
		values.put("descripcion", "Correr 5 km a un ritmo medio");
		values.put("hora", "7 pm");
		db.getWritableDatabase().insert("programa", null, values);				
	
		
	}		
	
}
