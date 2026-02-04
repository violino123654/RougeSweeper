package Minesweeper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Loja extends JFrame {
    public  Loja() {
        JPanel loja = new JPanel();
        JButton botao= new JButton("ir para ecra 2");

        botao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        loja.add(botao);
        loja.setVisible(true);
    }

    public static void main(String[] args){
        Loja loja = new Loja();
        loja.setTitle("Minesweeper");
        loja.pack();
        loja.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loja.setVisible(true);
        loja.setLocationRelativeTo(null);
    }
}