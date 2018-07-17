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

And that's it. Soon I will add new features in this project. Thanks.

**Now output task in Logcat and works without SQL. Soon it will be in app(normal UI) and will add SQL for saving tasks;)**
