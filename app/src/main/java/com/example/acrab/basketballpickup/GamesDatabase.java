package com.example.acrab.basketballpickup;


import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;



/**
 * Created by Dougie Fresh on 4/9/2017.
 */

public class GamesDatabase extends Application {
    private SQLiteDatabase database;
    private GamesDatabaseHandler dbHelper;
    private String[] allColumns = { GamesDatabaseHandler.COLUMN_ID,
            GamesDatabaseHandler.COLUMN_COMMENT};
    public GamesDatabase(Context context){
        dbHelper = new GamesDatabaseHandler(context);
    }
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }
    public void createComment(String comment) {
        ContentValues values = new ContentValues();
        values.put(GamesDatabaseHandler.COLUMN_COMMENT, comment);
        long insertId = database.insert(GamesDatabaseHandler.TABLE_COMMENTS, null,
                values);
    }
    public void deleteComment(Comment comment) {
        long id = comment.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(GamesDatabaseHandler.COLUMN_COMMENT, GamesDatabaseHandler.COLUMN_ID
                + " = " + id, null);
    }
    public List<Comment> getAllComments() {
        List<Comment> comments = new ArrayList<Comment>();

        Cursor cursor = database.query(GamesDatabaseHandler.TABLE_COMMENTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Comment comment = cursorToComment(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }


    private Comment cursorToComment(Cursor cursor) {
        Comment comment = new Comment();
        comment.setId(cursor.getLong(0));
        comment.setComment(cursor.getString(1));
        return comment;
    }




}
