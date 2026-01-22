package Minesweeper;

import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame{

    public Menu(){
        JPanel menu = new JPanel();
        JPanel bts = new JPanel();
        menu.setPreferredSize(new Dimension(300, 200));
        bts.setPreferredSize(new Dimension(100, 50));
        setResizable(false);
        menu.setLayout(new FlowLayout());

        ImageIcon imagem = new ImageIcon(getClass().getResource("/imagens/png/titulo.png"));
        JLabel titulo = new JLabel(new ImageIcon(imagem.getImage().getScaledInstance(270, 112, Image.SCALE_SMOOTH)));
        titulo.setPreferredSize(new Dimension(270, 112));

        JButton start = new JButton();
        start.setText("Jogar!!!");

        menu.add(titulo);
        bts.add(start);

        start.addActionListener(e -> {
            setVisible(false);
            JogoMinado jogo = new JogoMinado(this);
            jogo.setTitle("Minesweeper");
            jogo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jogo.pack();
            jogo.setLocationRelativeTo(null);
            jogo.setVisible(true);
        });

        add(menu, BorderLayout.NORTH);
        add(bts);
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.setTitle("Minesweeper");
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu.pack();
        menu.setVisible(true);
        menu.setLocationRelativeTo(null);
    }
}
