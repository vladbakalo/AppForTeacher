package com.vladik_bakalo.appforteacher.dummy;

import android.database.Cursor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class StudentContent {

    /**
     * An array of sample (Student) items.
     */
    public final List<DummyItem> ITEMS = new ArrayList<DummyItem>();


    private void addItem(DummyItem item) {
        ITEMS.add(item);
    }

    public void fillArrayByStudents(Cursor cursorOfStudents) throws ParseException {
        Cursor cursor = cursorOfStudents;
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat dateFormatFrom = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (cursor.moveToFirst()) {
            DummyItem studentItem;
            do {
                Date date = dateFormatFrom.parse(cursor.getString(3));

                studentItem = new DummyItem(cursor.getString(0), cursor.getString(1),
                        cursor.getString(2), dateFormat.format(date));
                addItem(studentItem);
            } while (cursor.moveToNext());
        }
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final String firstName;
        public final String lastName;
        public final String birthDay;

        public DummyItem(String id, String firstName, String lastName, String birthDay) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.birthDay = birthDay;
        }

        @Override
        public String toString() {
            return firstName + " " + lastName;
        }
    }
}
