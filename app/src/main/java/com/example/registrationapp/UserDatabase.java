package com.example.registrationapp;
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

 // App database created.
@Database(entities = {User.class},version = 1)
public abstract class UserDatabase extends RoomDatabase {
    public static UserDatabase database;
    private static final String databaseName = "UserDb";
 
    // Method to get an instance of the database
    public synchronized static UserDatabase getInstance(Context context){
        if(database == null){
            database = Room.databaseBuilder(context.getApplicationContext(),// Building the database using Room database builder
                            UserDatabase.class,databaseName)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }
    // Abstract method to provide access to UserDao
    public abstract UserDao userDAO();
}
