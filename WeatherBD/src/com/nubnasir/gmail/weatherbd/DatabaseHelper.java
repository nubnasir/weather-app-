package com.nubnasir.gmail.weatherbd;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static String PACKAGE_NAME;

	private static String DB_PATH;

	private static String DB_NAME = "weatherbd.db";

	private SQLiteDatabase myDataBase;

	private final Context myContext;

	public DatabaseHelper(Context context) {

		super(context, DB_NAME, null, 1);
		this.myContext = context;
		PACKAGE_NAME = myContext.getPackageName();
		DB_PATH = "/data/data/" + PACKAGE_NAME + "/databases/";
	}

	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();

		if (dbExist) {
			// do nothing - database already exist
		} else {

			// By calling this method and empty database will be created into
			// the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.
			this.getReadableDatabase();

			try {

				copyDataBase();

			} catch (IOException e) {

				throw new Error("Error copying database");

			}
		}

	}

	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READWRITE);

		} catch (SQLiteException e) {

			// database does't exist yet.

		}

		if (checkDB != null) {

			checkDB.close();

		}

		return checkDB != null ? true : false;
	}

	private void copyDataBase() throws IOException {

		// Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DB_NAME);

		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	public void openDataBase() throws SQLException {

		// Open the database
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE);

	}

	@Override
	public synchronized void close() {

		if (myDataBase != null)
			myDataBase.close();

		super.close();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public Cursor getResult(String sql) {

		Cursor c = myDataBase.rawQuery(sql, null);

		return c;
	}

	public void updateFavoriteList(String city_name, String country_name,
			int value) {
		myDataBase.execSQL("UPDATE city SET favorite = " + value
				+ " WHERE city_name='" + city_name + "' and country_name='"
				+ country_name + "';");
	}

	public void updateSettings(int mag, int range, int service, int all_eq) {
		myDataBase.execSQL("UPDATE settings SET value = " + mag
				+ " WHERE type='magnitude';");
		myDataBase.execSQL("UPDATE settings SET value = " + range
				+ " WHERE type='range';");
		myDataBase.execSQL("UPDATE settings SET value = " + service
				+ " WHERE type='service';");
		myDataBase.execSQL("UPDATE settings SET value = " + all_eq
				+ " WHERE type='all_eq';");
	}

	public int getSettings(String type) {

		Cursor c = myDataBase.rawQuery(
				"SELECT value FROM settings WHERE type='" + type + "';", null);

		c.moveToFirst();
		int value = c.getInt(c.getColumnIndex("value"));

		return value;
	}

	public String getDisaster(String type) {

		Cursor c = myDataBase.rawQuery(
				"SELECT value FROM disaster WHERE type='" + type + "';", null);

		c.moveToFirst();
		String value = c.getString(c.getColumnIndex("value"));

		return value;
	}

	public void updateDisaster(String type, String value) {
		myDataBase.execSQL("UPDATE disaster SET value='" + value
				+ "' WHERE type LIKE '" + type + "'");
	}

	public void addContact(String name, String phone) {
		try {
			myDataBase.execSQL("INSERT INTO contacts values('" + name + "','"
					+ phone + "') WHERE phone NOT LIKE '" + phone + "'");
			Toast.makeText(myContext, "Contact added!", Toast.LENGTH_LONG)
					.show();
		} catch (Exception ex) {
			Toast.makeText(myContext, "Contact already exits!",
					Toast.LENGTH_LONG).show();
		}
	}

	public void editContact(String lastPhone, String name, String phone) {
		try {
			myDataBase.execSQL("UPDATE contacts SET name='" + name
					+ "', phone='" + phone + "' WHERE phone LIKE '" + lastPhone
					+ "'");
			Toast.makeText(myContext, "Contact edited!", Toast.LENGTH_LONG)
					.show();
		} catch (Exception ex) {
			Toast.makeText(myContext, "ERROR:" + ex.getMessage(),
					Toast.LENGTH_LONG).show();
		}
	}
}
