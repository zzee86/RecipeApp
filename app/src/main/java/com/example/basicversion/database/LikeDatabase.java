package com.example.basicversion.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.basicversion.CreatedRecipes;

    public class LikeDatabase extends SQLiteOpenHelper {

        private static LikeDatabase likeDatabase;

        private static final int DB_VERSION = 18;
        private static final String DATABASE_NAME = "news";
        private static final String TABLE_NAME = "recipeTable";
        public static String KEY_ID = "id";
        public static String ITEM_TITLE = "itemTitle";
        public static String INSTRUCTIONS = "instructions";
        public static String INGREDIENTS = "ingredients";
        public static String LIKE_STATUS = "lStatus";
        public static String COOKINGTIME = "cookingTime";
        public static String YIELD = "yield";


        private static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " TEXT," + ITEM_TITLE + " TEXT," +INSTRUCTIONS + " TEXT," + INGREDIENTS + " TEXT," + LIKE_STATUS + " TEXT," + COOKINGTIME + " TEXT," + YIELD + " TEXT)";


        private static final String DATABASE_ALTER_TABLE = "ALTER TABLE " + TABLE_NAME;

        public LikeDatabase(Context context) {
            super(context, DATABASE_NAME, null, DB_VERSION);
        }

        public static LikeDatabase instanceOfDatabase(Context context) {

            if (likeDatabase == null) likeDatabase = new LikeDatabase(context);

            return likeDatabase;

        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i("testing", "created");
                     db.execSQL(CREATE_TABLE);

//            StringBuilder sql;
//            sql = new StringBuilder().append("CREATE TABLE ").append(TABLE_NAME).append(" (").append(KEY_ID).append(" INT, ").append(ITEM_TITLE).append(" TEXT, ").append(INSTRUCTIONS).append(" TEXT, ").append(INGREDIENTS).append(" TEXT, ").append(LIKE_STATUS).append(" TEXT, ").append(COOKINGTIME).append(" TEXT, ").append(YIELD).append(" TEXT)");
//
//            db.execSQL(sql.toString());
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            if (newVersion > oldVersion) {
                db.execSQL(DATABASE_ALTER_TABLE);
            }
        }

        public void addRecipe(CreatedRecipes recipes) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(KEY_ID, recipes.getId());
            cv.put(ITEM_TITLE, recipes.getTitle());
            cv.put(INSTRUCTIONS, recipes.getInstructions());
            cv.put(INGREDIENTS, recipes.getIngredients());
            cv.put(LIKE_STATUS, recipes.getLikeStatus());
            cv.put(COOKINGTIME, recipes.getCookingTime());
            cv.put(YIELD, recipes.getYield());
            db.insert(TABLE_NAME, null, cv);
        }

        public void populateRecipeArray() {
            SQLiteDatabase db = this.getReadableDatabase();
            CreatedRecipes.createdRecipes.clear();

            try (Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null)) {
                //      try (Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " +LIKE_STATUS + "="+ '0', null)) {

                if (cursor.getCount() != 0) {
                    while (cursor.moveToNext()) {
                        int id = cursor.getInt(0);
                        String title = cursor.getString(1);
                        String instruction = cursor.getString(2);
                        String ingredients = cursor.getString(3);
                        String like = cursor.getString(4);
                        String cooking = cursor.getString(5);
                        String yield = cursor.getString(6);


                        if (like == null) {
                            Log.i("populate", "recipe like is null: " + cursor.getString(2) + " like: " + cursor.getString(5));

                        } else {
                            Log.i("populate", "recipe like is not null: " + title + " like: " + like);

                        }
                        //   @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(ITEM_TITLE));
                        CreatedRecipes recipes = new CreatedRecipes(id, title, instruction, ingredients, like, cooking, yield);
                        CreatedRecipes.createdRecipes.add(recipes);
                    }
                }
            }
        }

        public void insertEmpty() {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            for (int x = 1; x < 200; x++) {
                cv.put(KEY_ID, x);
                db.insert(TABLE_NAME, null, cv);
            }
        }

        public void insertIntoDatabase(String id, String item_title, String instructions, String ingredients, String like_status, String cookingTime, String yield) {
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(ITEM_TITLE, item_title);
            cv.put(KEY_ID, id);
            cv.put(INSTRUCTIONS, instructions);
            cv.put(INGREDIENTS, ingredients);
            cv.put(LIKE_STATUS, like_status);
            cv.put(COOKINGTIME, cookingTime);
            cv.put(YIELD, yield);
            Long result = db.insert(TABLE_NAME, null, cv);

            if (result == -1) {
                Log.i("likedb status", "insertIntoDatabase Failed ");
            } else {
                Log.i("likedb status", "insertIntoDatabase Success");

            }
        }

        public Cursor read_all_data() {
            SQLiteDatabase db = this.getReadableDatabase();
            //   String sql = "select from * from " + TABLE_NAME + " where " + KEY_ID + "=" + id + "";


            String sql = "select * from " + TABLE_NAME;

            return db.rawQuery(sql, null, null);

        }

        public Cursor read_all_liked_data() {
            SQLiteDatabase db = this.getReadableDatabase();
            String sql = "select * from " + TABLE_NAME + " WHERE " + LIKE_STATUS + "=" + '1';
            return db.rawQuery(sql, null, null);

        }


        public void remove_like(String id) {
            SQLiteDatabase db = this.getReadableDatabase();
            //  String sql = "UPDATE " + TABLE_NAME + " SET " + LIKE_STATUS + " ='0' WHERE " + KEY_ID+"="+id+"";
            db.execSQL(" DELETE FROM " + TABLE_NAME + " WHERE " + KEY_ID + "=" + id + "");
            //     db.execSQL(" DELETE FROM " + TABLE_NAME );
        }

        public void updateRecipe(CreatedRecipes recipes) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(KEY_ID, recipes.getId());
            cv.put(ITEM_TITLE, recipes.getTitle());
            cv.put(INSTRUCTIONS, recipes.getInstructions());
            cv.put(INGREDIENTS, recipes.getIngredients());
            cv.put(LIKE_STATUS, recipes.getLikeStatus());
            cv.put(COOKINGTIME, recipes.getCookingTime());
            cv.put(YIELD, recipes.getYield());
            db.update(TABLE_NAME, cv, KEY_ID + " =? ", new String[]{String.valueOf(recipes.getId())});
        }


    }
