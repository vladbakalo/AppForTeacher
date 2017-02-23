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
 */
public class StudentContent {

    /**
     * An array of Student items.
     */
    public final List<DummyItem> ITEMS = new ArrayList<DummyItem>();
    public static final int COUNT_TO_UPLOAD = 20;
    private Cursor itemCursor;

    public StudentContent(Cursor cursor) {
        itemCursor = cursor;
        itemCursor.moveToFirst();
        try {
            updateArrayByStudents();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public void closeCursor()
    {
        itemCursor.close();
    }
    private void addItem(DummyItem item) {
        ITEMS.add(item);
    }

    public void updateArrayByStudents() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat dateFormatFrom = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DummyItem studentItem;

        for (int i = 0; i < COUNT_TO_UPLOAD; i++) {
            if (itemCursor.isLast())
            {
                return;
            }
            Date date = dateFormatFrom.parse(itemCursor.getString(3));

            studentItem = new DummyItem(itemCursor.getString(0), itemCursor.getString(1),
                    itemCursor.getString(2), dateFormat.format(date));
            addItem(studentItem);
            //
            itemCursor.moveToNext();
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
