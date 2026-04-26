package Minesweeper;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

public class Menu extends JFrame{
    boolean poderClicar = true;
    public Menu(){
        JPanel fundo = new JPanel();
        JPanel menu = new JPanel();
        JPanel bts = new JPanel();
        menu.setPreferredSize(new Dimension(300, 200));
        bts.setPreferredSize(new Dimension(100, 50));
        setResizable(false);
        menu.setLayout(new FlowLayout());

        ImageIcon fundoGif = new ImageIcon(getClass().getResource("/gifs/gif/Fundos/FundoMenu.gif"));
        JLabel background = new JLabel(fundoGif);

        ImageIcon imagem = new ImageIcon(getClass().getResource("/imagens/png/titulo.png"));
        JLabel titulo = new JLabel(new ImageIcon(imagem.getImage().getScaledInstance(270, 112, Image.SCALE_SMOOTH)));
        titulo.setPreferredSize(new Dimension(270, 112));

        JButton start = new JButton(), abrirManual = new JButton();
        start.setText("Jogar!!!");
        abrirManual.setText("Como Jogar");


        fundo.add(background);
        menu.add(titulo);
        bts.add(start);
        bts.add(abrirManual);

        menu.setOpaque(false);
        bts.setOpaque(false);

        JLayeredPane camadas = new JLayeredPane();
        camadas.setPreferredSize(new Dimension(300, 200));
        camadas.setLayout(null);
        fundo.setBounds(0, 0, 300, 200);
        camadas.add(fundo, Integer.valueOf(0));
        menu.setBounds(0, 20, 300, 120);
        camadas.add(menu, Integer.valueOf(1));
        bts.setBounds(0, 150, 300, 50);
        camadas.add(bts, Integer.valueOf(2));

        setContentPane(camadas);

        start.addActionListener(e -> {
            if(poderClicar){
                poderClicar = false;
                FileWriter writer = null;
                try {
                    writer = new FileWriter("src/dadosJogador/dados.txt");
                    writer.write(String.valueOf(3+"\n"+100+"\n"+1+"\n"+1+"\n"+5));
                    writer.close();
                }
                catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    writer = new FileWriter("src/dadosJogador/itens.txt");
                    for(int i=0;i<5;i++){
                        writer.write(String.valueOf(0+"\n"));
                    }
                    writer.close();
                }
                catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                dispose();
                Jogo jogo = new Jogo();
                jogo.setTitle("Roguesweeper");
                jogo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                jogo.pack();
                jogo.setLocationRelativeTo(null);
                jogo.setVisible(true);
            }
        });
        abrirManual.addActionListener(e -> {
            if(poderClicar){
                poderClicar = false;
                dispose();
                Manual manual = new Manual();
                manual.setTitle("Roguesweeper");
                manual.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                manual.pack();
                manual.setLocationRelativeTo(null);
                manual.setSize(600, 600);
                manual.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.setTitle("Roguesweeper");
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu.pack();
        menu.setVisible(true);
        menu.setLocationRelativeTo(null);
    }
}