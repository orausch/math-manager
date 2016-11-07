package module;

import factory.RightAngleTrigonometricProblemFactory;
import model.Problem;
import model.Quadratic;
import model.RightAngleTrigonometric;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

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
        System.out.println("-- STARTING TABLE CREATION --");
        final String CREATE_PROBLEMS_TABLE = "CREATE TABLE "
                + DatabaseContract.Problems.TABLE_NAME + " ("
                + DatabaseContract.Problems.COLUMN_ID + " INT PRIMARY KEY AUTO_INCREMENT,"
                + DatabaseContract.Problems.COLUMN_PROBLEM_TYPE + " INT,"
                + DatabaseContract.Problems.COLUMN_QUESTION + " TEXT, "
                + DatabaseContract.Problems.COLUMN_DATA + " TEXT,"
                + DatabaseContract.Problems.COLUMN_ANSWER + " TEXT)";


        try {
            stat.execute(CREATE_PROBLEMS_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 5; i++) {
            insertProblem(RightAngleTrigonometricProblemFactory.generateRightAngleTrigonometricProblem(3));
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

    private void insertProblem(Problem problem) {
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

    public void close() {
        try {
            stat.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


