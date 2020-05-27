package snake;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class MenuItem extends JMenu implements MenuListener {
    private Runnable onClick;
    public MenuItem(String s, int mnemonic, Runnable onClick) {
        super(s);
        setMnemonic(mnemonic);
        this.onClick = onClick;
        addMenuListener(this);
    }

    @Override
    public void menuSelected(MenuEvent e) { onClick.run(); }

    @Override
    public void menuDeselected(MenuEvent e) { }//onClick.run(); }

    @Override
    public void menuCanceled(MenuEvent e) { }//onClick.run(); }
}
