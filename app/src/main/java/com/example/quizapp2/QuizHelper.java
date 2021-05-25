package com.example.quizapp2;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


class QuizHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DB_NAME = "JavaQuiz.db";

    //If you want to add more questions or wanna update table values
    //or any kind of modification in db just increment version no.
    private static final int DB_VERSION = 7;
    //Table name
    private static final String TABLE_NAME = "TQ";
    //Id of question
    private static  String UID = "_UID";
    //Question
    private static final String QUESTION = "QUESTION";
    //Option A
    private static final String OPTA = "OPTA";
    //Option B
    private static final String OPTB = "OPTB";
    //Option C
    private static final String OPTC = "OPTC";
    //Option D
    private static final String OPTD = "OPTD";
    //Answer
    private static final String ANSWER = "ANSWER";
    //So basically we are now creating table with first column-id , sec column-question , third column -option A, fourth column -option B , Fifth column -option C , sixth column -option D , seventh column - answer(i.e ans of  question)
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + UID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + QUESTION + " VARCHAR(255), " + OPTA + " VARCHAR(255), " + OPTB + " VARCHAR(255), " + OPTC + " VARCHAR(255), " + OPTD + " VARCHAR(255), " + ANSWER + " VARCHAR(255));";
    //Drop table query
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    QuizHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //OnCreate is called only once
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //OnUpgrade is called when ever we upgrade or increment our database version no
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    void allQuestion() {
        ArrayList<QuestionActivity> arraylist = new ArrayList<>();

        arraylist.add(new QuestionActivity("JVM Stand for", "JAVA Runtime Environment ?", "Java Virtual machine", "Java Voice Machine", "Virtual Java Machine ", "Java Virtual machine"));

        arraylist.add(new QuestionActivity("Which method is the starting point for all Java programs ?", "public", "void", "main", "class", "main"));

        arraylist.add(new QuestionActivity("Which method print text in java ?", "out.writeText()", "System.out.println()", "print()", "print.out()", "System.out.println()"));

        arraylist.add(new QuestionActivity("Which symbol is used for Single Line Comment?", "** at the beginning of the line", "// at the end of the line", "*/ at the beginning of the line", "// at the beginning of the line", "// at the beginning of the line"));

        arraylist.add(new QuestionActivity("Which symbol is used for Java doc style comment?", "// at the beginning of the line", "/** and */to wrap a comment", "/* and */ to wrap comment", "// and */ to wrap a comment", "/** and */to wrap a comment"));

        arraylist.add(new QuestionActivity(" Which of the following is not a Java features?","Dynamic","Architecture Neutral","Use of pointers","Object-oriented","Use of pointers"));

        arraylist.add(new QuestionActivity( "The \'u0021 article referred to as a", "Unicode escape sequence","Octal escape","Hexadecimal","Line feed","Unicode escape sequence"));

        arraylist.add(new QuestionActivity("Evaluate the following Java expression, if x=3, y=5, and z=10:","24","23","20","25","24"));

        arraylist.add(new QuestionActivity("Which of the following tool is used to generate API documentation in HTML format from doc comments in source code?","javap tool","javaw command","Javadoc tool","javah command","Javadoc tool"));

        arraylist.add(new QuestionActivity("In which process, a local variable has the same name as one of the instance variables?","Serialization","Variable Shadowing","Abstraction","Multi-threading","Variable Shadowing"));

        arraylist.add(new QuestionActivity("An interface with no fields or methods is known as a ______.","Runnable Interface","Marker Interface","Abstract Interface","CharSequence Interface","Marker Interface"));

        arraylist.add(new QuestionActivity("Which of the following is an immediate subclass of the Panel class?\n","Applet class","Window class","Frame class","Dialog class","Applet class"));



        this.addAllQuestions(arraylist);

    }


    private void addAllQuestions(ArrayList<QuestionActivity> allQuestions) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (QuestionActivity question : allQuestions) {
                values.put(QUESTION, question.getQuestion());
                values.put(OPTA, question.getOptA());
                values.put(OPTB, question.getOptB());
                values.put(OPTC, question.getOptC());
                values.put(OPTD, question.getOptD());
                values.put(ANSWER, question.getAnswer());
                db.insert(TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }


    List<QuestionActivity> getAllOfTheQuestions() {

        List<QuestionActivity> questionsList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        String coloumn[] = {UID, QUESTION, OPTA, OPTB, OPTC, OPTD, ANSWER};
        Cursor cursor = db.query(TABLE_NAME, coloumn, null, null, null, null, null);


        while (cursor.moveToNext()) {
            QuestionActivity question = new QuestionActivity();
            question.setId(cursor.getInt(0));
            question.setQuestion(cursor.getString(1));
            question.setOptA(cursor.getString(2));
            question.setOptB(cursor.getString(3));
            question.setOptC(cursor.getString(4));
            question.setOptD(cursor.getString(5));
            question.setAnswer(cursor.getString(6));
            questionsList.add(question);
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        cursor.close();
        db.close();
        return questionsList;
    }

}
