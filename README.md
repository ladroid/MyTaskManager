# MyTaskManager
This is Task Manager

Hello everybody. Decided to make own **Task Manager**

And what I did?

At first I chose in Android Studio Basic Activity.
The next step is make FloatingActionButton clickable.

Like here 
```java
fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ...
            }
}
```
After this I added **AlertDialog** for making todo task. And this is a code.
```java
final EditText input = new EditText(MainActivity.this);

//Setting Dialog Message, and Dialog Title, also add edittext in alertDialog and show everything
AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
alertDialog.setTitle("Add new Task").setMessage("What do you want to do?").setView(input)
        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        String task = String.valueOf(input.getText());
        Log.d("Adding Task", "Task to add: " + task);
    }
}).setNegativeButton("Cancel", null).create().show();
```

And the full code
```java
FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "New Task", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                final EditText input = new EditText(MainActivity.this);

                //Setting Dialog Message, and Dialog Title, also add edittext in alertDialog and show everything
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Add new Task").setMessage("What do you want to do?").setView(input)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String task = String.valueOf(input.getText());
                        Log.d("Adding Task", "Task to add: " + task);
                    }
                }).setNegativeButton("Cancel", null).create().show();

            }
        });
```

I added DataBase for saving tasks. So how I did it? Let's see.

At first I made constants values for name my db, version and columns. Don't forget to extend **SQLiteOpenHelper** for your class.

```java
public static final String DATABASE_NAME = "Task.db";
public static final int VERSION = 1;
public static final String CONTACTS_TABLE_NAME = "tasks";
public static final String CONTACTS_COLUMN_ID = "id";
public static final String CONTACTS_COLUMN_INFO = "info";
```

When you extended SQLiteOpenHelper you should **override onUpgrade(...), onCreate(...) and add constructor from a class**.

Then in this will looks like this

```java
public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, VERSION);
}

@Override
public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("CREATE TABLE " + CONTACTS_TABLE_NAME + " ( " + CONTACTS_COLUMN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + CONTACTS_COLUMN_INFO + " TEXT NOT NULL);");
}

@Override
public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CONTACTS_TABLE_NAME);
            onCreate(sqLiteDatabase);
}
```

After this add some methods like insert, update, delete and get everything.

Let's see insert method. This method and all others besides get will be boolean.

```java
SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
ContentValues contentValues = new ContentValues();

contentValues.put("info", info);
sqLiteDatabase.insert(CONTACTS_TABLE_NAME, null, contentValues);
return true;
```

What does it mean the first line? The first line make our db writable, it means that we can add datas in columns. **ContentValues** I used to put my data in column. And then I insert it(if you have more columns it doesn't matter).

Let's see update

```java
SQLiteDatabase db = this.getWritableDatabase();

ContentValues values = new ContentValues();
values.put("info", CONTACTS_COLUMN_INFO);

// updating row
db.update(CONTACTS_TABLE_NAME, values, CONTACTS_COLUMN_ID + " = ?",
    new String[]{String.valueOf(CONTACTS_COLUMN_ID)});
return true;
```

By this method I update my db. I did the same things below except update, where I'm looking if I add new task by id.

Now delete

```java
SQLiteDatabase db = this.getWritableDatabase();
return db.delete(CONTACTS_TABLE_NAME,"id = ? ", new String[] { Integer.toString(id) });
```

I delete data by id.

And the last is getting all

```java
ArrayList<String> array_list = new ArrayList<String>();

SQLiteDatabase db = this.getReadableDatabase();
Cursor res =  db.rawQuery( "SELECT * FROM " + CONTACTS_TABLE_NAME, null );
res.moveToFirst();

while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_INFO)));
            res.moveToNext();
}
return array_list;
```

Let's see it in detail. The first part of line I added arraylist value for output all datas from column. The next part of line I did my db readable. Why? Just because I read my db and don't add anything. 

The next is this *Cursor res =  db.rawQuery( "SELECT * FROM " + CONTACTS_TABLE_NAME, null );*  make a request for all data from the table. 

Next line means that we position the cursor on the first row of the sample. 

*while(res.isAfterLast() == false)* - means that if we don't have the last data the we should to add it. And I did this by this line of code *array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_INFO)));* and then go to the next line by this part of code *res.moveToNext();*.

And after I return my arraylist.

**In my project I don't add delete. It doesn't means that this method doesn't work) I just want make it by swapping:)**

And that's it. Soon I will add new features in this project. Thanks.
