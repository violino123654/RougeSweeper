package Minesweeper;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

public class Ganhar extends JFrame{
    boolean poderClicar = true;
    public Ganhar(){

        JPanel fundo = new JPanel();
        JPanel texto = new JPanel();
        JPanel bts = new JPanel();
        texto.setPreferredSize(new Dimension(300, 200));
        bts.setPreferredSize(new Dimension(100, 50));
        setResizable(false);
        texto.setLayout(new FlowLayout());

        ImageIcon fundoGif = new ImageIcon(getClass().getResource("/gifs/gif/Fundos/FundoGanhar.gif"));
        JLabel background = new JLabel(fundoGif);

        ImageIcon imagem = new ImageIcon(getClass().getResource("/imagens/png/GanharTitulo.png"));
        JLabel titulo = new JLabel(new ImageIcon(imagem.getImage().getScaledInstance(270, 112, Image.SCALE_SMOOTH)));
        titulo.setPreferredSize(new Dimension(270, 112));

        JButton voltar = new JButton();
        voltar.setText("Voltar ao Menu");


        fundo.add(background);
        texto.add(titulo);
        bts.add(voltar);

        texto.setOpaque(false);
        bts.setOpaque(false);

        JLayeredPane camadas = new JLayeredPane();
        camadas.setPreferredSize(new Dimension(300, 200));
        camadas.setLayout(null);
        fundo.setBounds(0, 0, 300, 200);
        camadas.add(fundo, Integer.valueOf(0));
        texto.setBounds(0, 20, 300, 120);
        camadas.add(texto, Integer.valueOf(1));
        bts.setBounds(0, 150, 300, 50);
        camadas.add(bts, Integer.valueOf(2));

        setContentPane(camadas);

        voltar.addActionListener(e -> {
            if(poderClicar){
                poderClicar = false;
                FileWriter writer = null;
                try {
                    writer = new FileWriter("src/dadosJogador/dados.txt");
                    writer.write(String.valueOf(0));
                    writer.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    writer = new FileWriter("src/dadosJogador/itens.txt");
                    for(int i=0;i<5;i++){
                        writer.write(String.valueOf(0+"\n"));
                    }
                    writer.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                setVisible(false);
                Menu menu = new Menu();
                menu.setTitle("Roguesweeper");
                menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                menu.pack();
                menu.setLocationRelativeTo(null);
                menu.setVisible(true);
            }
        });
    }
}
