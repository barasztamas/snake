package snake;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import snake.persistence.HighScore;

public class HighScoreTableModel extends AbstractTableModel{
    private final ArrayList<HighScore> highScores;
    private final String[] colName = new String[]{ "Name", "Score" };

    public HighScoreTableModel(ArrayList<HighScore> highScores){
        this.highScores = highScores;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0: return String.class;
            case 1: return Integer.class;
            default: return super.getColumnClass(columnIndex);
        }
    }

    @Override
    public int getRowCount() { return highScores.size(); }

    @Override
    public int getColumnCount() { return 2; }

    @Override
    public Object getValueAt(int r, int c) {
        HighScore h = highScores.get(r);
        if      (c == 0) return h.name;
        else if (c == 1) return h.score;
        throw new IndexOutOfBoundsException();
    }

    @Override
    public String getColumnName(int i) { return colName[i]; }

}
