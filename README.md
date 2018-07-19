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
SQLiteDatabase db = getWritableDatabase();
db.execSQL("DELETE FROM " + CONTACTS_TABLE_NAME + " WHERE " + CONTACTS_COLUMN_ID + "=\"" + id + "\";");
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

I did delete method by swiping. How I did it?

1) Add this in **gradle** 

```
implementation 'com.baoyz.swipemenulistview:library:1.3.0'
```

2) Change in xml file from **ListView** to this

```xml
<com.baoyz.swipemenulistview.SwipeMenuListView
        android:id="@+id/TODO"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        android:lines="10"
        android:textSize="24dp"
        android:maxLines = "10"
        android:scrollbars = "vertical"
        android:scrollbarStyle="insideOverlay"
        android:fadeScrollbars="true"
        android:fadingEdge="vertical"/>
```

3) Add this code

```java
//swipe menu
SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(500);
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(500);
                // set item title
                deleteItem.setTitle("Delete");
                // set item title fontsize
                deleteItem.setTitleSize(18);
                // set item title font color
                deleteItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
};
```

4) Set menu

```java
//set menu
textView.setMenuCreator(creator);
```

5) Make a menu for our swipe

```java
//make swipe menu clickable
textView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        ...
                        break;
                    case 1:
                        ...
                        break;
                }
            }
}
```

6) The last I add my delete method in **case1:**.

```java
dbHelper.deleteContact(String.valueOf(position+1));
startActivity(new Intent(MainActivity.this, MainActivity.class));
```

And that's it. Soon I will add new features in this project. Thanks.
