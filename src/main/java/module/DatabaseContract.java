package module;

class DatabaseContract {

    protected static abstract class Problems {
        protected static final String TABLE_NAME = "problems";
        protected static final String COLUMN_ID = "problemid";
        protected static final String COLUMN_PROBLEM_TYPE = "type";
        protected static final String COLUMN_QUESTION = "question";
        protected static final String COLUMN_DATA = "data";

        protected static final String COLUMN_ANSWER = "answer";
        //Types of questions
        protected static final int TYPE_QUADRATIC = 0;
        protected static final int TYPE_TRIG = 1;
        protected static final int TYPE_TEXT = 2;

    }

    protected static abstract class Tests {
        protected static final String TABLE_NAME = "test";
        protected static final String COLUMN_ID = "testid";
        //Question IDs are separated by commas
        protected static final String COLUMN_NAME = "name";
        protected static final String COLUMN_DATE = "date";
        protected static final String COLUMN_PROBLEM_IDS = "problemids";
    }
}
