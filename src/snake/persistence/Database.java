package snake.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

public class Database {
    private final String tableName = "highscore";
    private final Connection conn;
    private final HashMap<String, Integer> highScores;
    private final int capacity = 10;

    public Database(){
        Connection c = null;
        try {
            c = ConnectionFactory.getConnection();
        } catch (Exception ex) { System.out.println("No connection");}
        this.conn = c;
        highScores = new HashMap<>();
        loadHighScores();
        removeMinScore(false);
    }

    public boolean storeHighScore(String name, int newScore){
        return mergeHighScores(name, newScore, newScore > 0);
    }

    public int minScore() {
        if (highScores.size() < capacity) {
            return 0;
        }
        return Collections.min(highScores.values());
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
            Integer oldScore = highScores.get(name);
            doUpdate = (score != 0 && (oldScore == null || score > oldScore));
        }
        if (doUpdate){
            highScores.put(name, score);
            return (!store || (storeToDatabase(name, score) > 0 && !name.equals(removeMinScore(true))));
        }
        return false;
    }

    private String removeMinScore(boolean firstOnly) {
        while (highScores.size() > capacity) {
            for (String name : highScores.keySet().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList())) {
                if (highScores.get(name) == minScore()) {
                    highScores.remove(name);
                    removeFromDatabase(name);
                    if (firstOnly) { return name; }
                    break;
                }
            }
        }
        return null;
    }

    private int removeFromDatabase(String name) {
        try (Statement stmt = conn.createStatement()){
            String s = "DELETE FROM " + tableName +
                    " WHERE Name = '" + name + "'";
            return stmt.executeUpdate(s);
        } catch (Exception e){
            System.out.println("storeToDatabase error");
        }
        return 0;
    }

    private int storeToDatabase(String name, int score){
        try (Statement stmt = conn.createStatement()){
            String s = "INSERT INTO " + tableName +
                    " (Name, Score) " +
                    "VALUES('" + name + "'" +
                    "," + score +
                    ") ON DUPLICATE KEY UPDATE Score=" + score;
            System.out.println(s);
            return stmt.executeUpdate(s);
        } catch (Exception e){
            System.out.println("storeToDatabase error");
        }
        return 0;
    }

}
