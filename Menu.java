package Minesweeper;

import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame{

    public Menu(){
        JPanel menu = new JPanel();
        menu.setPreferredSize(new Dimension(200, 100));
        setResizable(false);
        menu.setLayout(new FlowLayout());
        setLocationRelativeTo(null);

        JButton start = new JButton();

        menu.add(start);

        start.addActionListener(e -> {
            setVisible(false);
            JogoMinado jogo = new JogoMinado(this);
            jogo.setTitle("Minesweeper");
            jogo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jogo.pack();
            jogo.setLocationRelativeTo(null);
            jogo.setVisible(true);
        });

        add(menu);
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.setTitle("Minesweeper");
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu.pack();
        menu.setVisible(true);
    }
}
