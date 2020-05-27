package snake.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {
    private final String tableName = "highscore";
    private final Connection conn;
    private final HashMap<String, Integer> highScores;

    public Database(){
        Connection c = null;
        try {
            c = ConnectionFactory.getConnection();
        } catch (Exception ex) { System.out.println("No connection");}
        this.conn = c;
        highScores = new HashMap<>();
        loadHighScores();
    }

    public boolean storeHighScore(String name, int newScore){
        return mergeHighScores(name, newScore, newScore > 0);
    }

    public ArrayList<HighScore> getHighScores(){
        ArrayList<HighScore> scores = new ArrayList<>();
        for (String name : highScores.keySet()){
            HighScore h = new HighScore(name, highScores.get(name));
            scores.add(h);
            System.out.println(h);
        }
        return scores;
    }

    private void loadHighScores(){
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);
            while (rs.next()){
                String name = rs.getString("Name");
                int score = rs.getInt("Score");
                mergeHighScores(name, score, false);
            }
        } catch (Exception e){ System.out.println("loadHighScores error");}
    }

    private boolean mergeHighScores(String name, int score, boolean store){
        System.out.println("Merge: " + name + ":" + score + "(" + store + ")");
        boolean doUpdate = true;
        if (highScores.containsKey(name)){
            int oldScore = highScores.get(name);
            doUpdate = ((score < oldScore && score != 0) || oldScore == 0);
        }
        if (doUpdate){
            highScores.remove(name);
            highScores.put(name, score);
            if (store) return storeToDatabase(name, score) > 0;
            return true;
        }
        return false;
    }

    private int storeToDatabase(String name, int score){
        try (Statement stmt = conn.createStatement()){
            String s = "INSERT INTO " + tableName +
                    " (Difficulty, GameLevel, Score) " +
                    "VALUES('" + name + "'," +
                    "," + score +
                    ") ON DUPLICATE KEY UPDATE Score=" + score;
            return stmt.executeUpdate(s);
        } catch (Exception e){
            System.out.println("storeToDatabase error");
        }
        return 0;
    }

}
