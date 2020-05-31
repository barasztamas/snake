package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuItem extends JMenuItem implements ActionListener {
    private Runnable onClick;
    public MenuItem(String s, int mnemonic, Runnable onClick) {
        super(s);
        setMnemonic(mnemonic);
        this.onClick = onClick;
        setPreferredSize(new Dimension(10, getHeight()));
        addActionListener(this);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1, 1);
    }

    @Override
    public void actionPerformed(ActionEvent e) { onClick.run(); }
}
