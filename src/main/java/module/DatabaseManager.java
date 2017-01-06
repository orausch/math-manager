package module;

import factory.QuadraticProblemFactory;
import factory.RightAngleTrigonometricProblemFactory;
import model.*;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class DatabaseManager {
    private final String DB_NAME = "database";
    private Connection conn;
    private Statement stat;

    public DatabaseManager() {
        try {
            File file = new File(this.DB_NAME + ".mv.db");

            boolean createTables = !file.exists() && !file.isDirectory();
            conn = DriverManager.getConnection("jdbc:h2:./" + this.DB_NAME + ";TRACE_LEVEL_FILE=0");
            stat = conn.createStatement();


            if (createTables) {
                initDatabase();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initDatabase() {
        //Creating the Tables for the Database
        System.out.println("STARTING TABLE CREATION");
        System.out.println("=======================");

        final String CREATE_PROBLEMS_TABLE = "CREATE TABLE "
                + DatabaseContract.Problems.TABLE_NAME + " ("
                + DatabaseContract.Problems.COLUMN_ID + " INT PRIMARY KEY AUTO_INCREMENT,"
                + DatabaseContract.Problems.COLUMN_PROBLEM_TYPE + " INT,"
                + DatabaseContract.Problems.COLUMN_QUESTION + " TEXT, "
                + DatabaseContract.Problems.COLUMN_DATA + " TEXT,"
                + DatabaseContract.Problems.COLUMN_ANSWER + " TEXT)";

        final String CREATE_TESTS_TABLE = "CREATE TABLE "
                + DatabaseContract.Tests.TABLE_NAME + " ("
                + DatabaseContract.Tests.COLUMN_ID + " INT PRIMARY KEY AUTO_INCREMENT,"
                + DatabaseContract.Tests.COLUMN_NAME + " TEXT,"
                + DatabaseContract.Tests.COLUMN_PROBLEM_IDS + " TEXT) ";

        try {
            stat.execute(CREATE_PROBLEMS_TABLE);
            stat.execute(CREATE_TESTS_TABLE);

            //insert data for debugging
            Quadratic quadratic = QuadraticProblemFactory.generateSolveQuadraticQuestion(1, 5, 10);
            insertProblem(quadratic);
            insertProblem(QuadraticProblemFactory.generateSolveQuadraticQuestion(1, 5, 10));
            insertProblem(QuadraticProblemFactory.generateSolveQuadraticQuestion(1, 5, 10));
            insertProblem(QuadraticProblemFactory.generateSolveQuadraticQuestion(1, 5, 10));
            insertProblem(QuadraticProblemFactory.generateSolveQuadraticQuestion(1, 5, 10));

            insertProblem(RightAngleTrigonometricProblemFactory.generateRightAngleTrigonometricProblem(0));
            insertProblem(RightAngleTrigonometricProblemFactory.generateRightAngleTrigonometricProblem(1));
            insertProblem(RightAngleTrigonometricProblemFactory.generateRightAngleTrigonometricProblem(2));
            insertProblem(RightAngleTrigonometricProblemFactory.generateRightAngleTrigonometricProblem(3));

            LinkedList<Problem> problems = new LinkedList<>();
            problems.add(getProblem(1));
            insertTest(new Test(problems, "test"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Problem[] getProblemsArray() {
        try {
            ResultSet rs = stat.executeQuery("SELECT * FROM " + DatabaseContract.Problems.TABLE_NAME);
            ArrayList<Problem> problems = new ArrayList<>();
            while (rs.next()) {
                if (rs.getInt(DatabaseContract.Problems.COLUMN_PROBLEM_TYPE) == DatabaseContract.Problems.TYPE_QUADRATIC) {
                    Quadratic quadratic = new Quadratic(rs.getString(DatabaseContract.Problems.COLUMN_DATA),
                            rs.getString(DatabaseContract.Problems.COLUMN_ANSWER),
                            rs.getString(DatabaseContract.Problems.COLUMN_QUESTION));
                    quadratic.setId(rs.getInt(DatabaseContract.Problems.COLUMN_ID));
                    problems.add(quadratic);
                } else if (rs.getInt(DatabaseContract.Problems.COLUMN_PROBLEM_TYPE) == DatabaseContract.Problems.TYPE_TRIG) {
                    try {
                        RightAngleTrigonometric trigonometric = (RightAngleTrigonometric) RightAngleTrigonometric.parseDatabaseForm(rs.getString(DatabaseContract.Problems.COLUMN_DATA));
                        trigonometric.setId(rs.getInt(DatabaseContract.Problems.COLUMN_ID));
                        problems.add(trigonometric);
                    } catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }
                } else if (rs.getInt(DatabaseContract.Problems.COLUMN_PROBLEM_TYPE) == DatabaseContract.Problems.TYPE_TEXT) {
                    Text text = new Text(rs.getString(DatabaseContract.Problems.COLUMN_QUESTION), rs.getString(DatabaseContract.Problems.COLUMN_ANSWER));
                    text.setId(rs.getInt(DatabaseContract.Problems.COLUMN_ID));
                    problems.add(text);
                }
            }
            Problem[] problemsArray = new Problem[problems.size()];
            for (int i = 0; i < problemsArray.length; i++) {
                problemsArray[i] = problems.get(i);
            }
            return problemsArray;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Problem getProblem(int id) {
        try {
            ResultSet rs = stat.executeQuery("SELECT * FROM " + DatabaseContract.Problems.TABLE_NAME + " WHERE " + DatabaseContract.Problems.COLUMN_ID + "=" + 2);
            rs.next();
            if (rs.getInt(DatabaseContract.Problems.COLUMN_PROBLEM_TYPE) == DatabaseContract.Problems.TYPE_QUADRATIC) {
                Quadratic quadratic = new Quadratic(rs.getString(DatabaseContract.Problems.COLUMN_DATA),
                        rs.getString(DatabaseContract.Problems.COLUMN_ANSWER),
                        rs.getString(DatabaseContract.Problems.COLUMN_QUESTION));
                quadratic.setId(rs.getInt(DatabaseContract.Problems.COLUMN_ID));
                return quadratic;
            } else if (rs.getInt(DatabaseContract.Problems.COLUMN_PROBLEM_TYPE) == DatabaseContract.Problems.TYPE_TRIG) {
                try {
                    RightAngleTrigonometric trigonometric = (RightAngleTrigonometric) RightAngleTrigonometric.parseDatabaseForm(rs.getString(DatabaseContract.Problems.COLUMN_DATA));
                    trigonometric.setId(rs.getInt(DatabaseContract.Problems.COLUMN_ID));
                    return trigonometric;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (rs.getInt(DatabaseContract.Problems.COLUMN_PROBLEM_TYPE) == DatabaseContract.Problems.TYPE_TEXT) {
                Text text = new Text(rs.getString(DatabaseContract.Problems.COLUMN_QUESTION), rs.getString(DatabaseContract.Problems.COLUMN_ANSWER));
                text.setId(rs.getInt(DatabaseContract.Problems.COLUMN_ID));
                return text;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Text("Error", "Error");
    }

    public void insertProblem(Problem problem) {
        String statement = "INSERT INTO "
                + DatabaseContract.Problems.TABLE_NAME
                + "("
                + DatabaseContract.Problems.COLUMN_PROBLEM_TYPE + ", "
                + DatabaseContract.Problems.COLUMN_QUESTION + ", "
                + DatabaseContract.Problems.COLUMN_DATA + ", "
                + DatabaseContract.Problems.COLUMN_ANSWER + ")"
                + " VALUES(";
        if (problem instanceof Quadratic) {
            Quadratic quadratic = (Quadratic) problem;
            statement = statement + String.valueOf(DatabaseContract.Problems.TYPE_QUADRATIC) + ", "
                    + "\'" + quadratic.getQuestion() + "\', "
                    + "\'" + quadratic.toDatabaseForm() + "\', "
                    + "\'" + quadratic.getAnswer() + "\')";
        } else if (problem instanceof RightAngleTrigonometric) {
            RightAngleTrigonometric trigonometric = (RightAngleTrigonometric) problem;
            try {
                statement = statement + String.valueOf(DatabaseContract.Problems.TYPE_TRIG) + ","
                        + "\'" + trigonometric.getQuestion() + "\', "
                        + "\'" + trigonometric.toDatabaseForm() + "\', "
                        + "\'" + trigonometric.getAnswer() + "\')";
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        } else if (problem instanceof Text) {
            Text text = (Text) problem;
            statement = statement + String.valueOf(DatabaseContract.Problems.TYPE_TEXT) + ","
                    + "\'" + text.getQuestion() + "\', "
                    + "\' N/A \', "
                    + "\'" + text.getAnswer() + "\')";
        }
        try {
            stat.execute(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProblem(Problem problem) {
        String statement = "DELETE FROM "
                + DatabaseContract.Problems.TABLE_NAME
                + " WHERE " + DatabaseContract.Problems.COLUMN_ID
                + " = \'" + String.valueOf(problem.getId())
                + "\';";
        try {
            stat.execute(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertTest(Test test) {
        String ids = String.valueOf(test.getQuestions().getFirst().getId());

        for (int i = 1; i < test.getQuestions().size(); i++) {
            ids += "," + test.getQuestions().get(i).getId();
        }

        String statement = "INSERT INTO "
                + DatabaseContract.Tests.TABLE_NAME
                + "("
                + DatabaseContract.Tests.COLUMN_NAME + ", "
                + DatabaseContract.Tests.COLUMN_PROBLEM_IDS + ")"
                + " VALUES(" + "\'" + test.getName() + "\'" + ","
                + "\'" + ids + "\')";

        try {
            stat.execute(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Test[] getTestArray() {
        try {
            ArrayList<Test> tests = new ArrayList<>();
            //A new statement is required because JDBC auto closes the old one during the loop
            Statement stat2 = conn.createStatement();
            ResultSet rs = stat2.executeQuery("SELECT * FROM " + DatabaseContract.Tests.TABLE_NAME);
            while (rs.next()) {
                LinkedList<Problem> problems = new LinkedList<>();
                String[] string = rs.getString(DatabaseContract.Tests.COLUMN_PROBLEM_IDS).split(",");
                String testName = rs.getString(DatabaseContract.Tests.COLUMN_NAME);
                int id = rs.getInt(DatabaseContract.Tests.COLUMN_ID);
                //When it gets to this loop it closes the resultset
                for (int i = 0; i < string.length; i++) {
                    problems.add(getProblem(Integer.parseInt(string[i])));
                }
                Test test = new Test(problems, testName);
                test.setId(id);
                tests.add(test);
            }
            Test[] testsArray = new Test[tests.size()];
            for (int i = 0; i < testsArray.length; i++) {
                testsArray[i] = tests.get(i);
            }
            return testsArray;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void close() {
        try {
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


