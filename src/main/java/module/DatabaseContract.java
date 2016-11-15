package module;

class DatabaseContract {

    private DatabaseContract() {
    }

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
}
