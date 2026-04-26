package Minesweeper;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

public class Manual extends JFrame {

    private JTextArea caixa1;
    private JTextArea caixa2;
    private JTextArea caixa3;
    private JTextArea caixa4;
    private JTextArea caixa5;
    private JTextArea caixa6;
    private JTextArea caixa7;
    private JTextArea caixa8;
    private JTextArea caixa9;

    boolean poderClicar = true;

    public Manual() {
        setTitle("Roguesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        JButton voltar = new JButton("             Voltar             ");
        voltar.addActionListener(e -> {
            if(poderClicar){
                poderClicar = false;
                dispose();
                Menu menu = new Menu();
                menu.setTitle("Roguesweeper");
                menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                menu.pack();
                menu.setLocationRelativeTo(null);
                menu.setVisible(true);
            }
        });

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));

        painel.add(voltar);

        getContentPane().setBackground(Color.DARK_GRAY);
        painel.setBackground(Color.DARK_GRAY);

        caixa1 = new JTextArea("Primeiro clique:\n" + "Ao clicar numa casa, ela revela um número.\n" + "O número que estiver indicado na casa significa a quantidade de minas\n" + "que vão estar à volta desse número, nas 8 casas à volta do número.");
        caixa1.setFont(new Font("Arial", Font.BOLD, 16));
        caixa1.setEditable(false);
        caixa1.setLineWrap(true);
        caixa1.setWrapStyleWord(true);
        caixa1.setBackground(Color.GRAY);
        caixa1.setForeground(Color.WHITE);
        JScrollPane scrollCaixa1 = new JScrollPane(caixa1);
        scrollCaixa1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        painel.add(scrollCaixa1);
        painel.add(Box.createRigidArea(new Dimension(0, 5)));

        caixa2 = new JTextArea("Casas vazias:\n" + "se não houver minas na redondeza, aparece automaticamente as casas livres ligadas.");
        caixa2.setFont(new Font("Arial", Font.BOLD, 16));
        caixa2.setEditable(false);
        caixa2.setLineWrap(true);
        caixa2.setWrapStyleWord(true);
        caixa2.setBackground(Color.GRAY);
        caixa2.setForeground(Color.WHITE);
        JScrollPane scrollCaixa2 = new JScrollPane(caixa2);
        scrollCaixa2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        painel.add(scrollCaixa2);
        painel.add(Box.createRigidArea(new Dimension(0, 5)));

        caixa3 = new JTextArea("Significado dos números:\n" + "1- apenas uma bomba nas casas adjacentes.\n" + "2- duas bombas nas casas adjacentes.\n" + "3- três bombas nas casas adjacentes.\n" + "4- quatro bombas nas casas adjacentes.\netc...");
        caixa3.setFont(new Font("Arial", Font.BOLD, 16));
        caixa3.setEditable(false);
        caixa3.setLineWrap(true);
        caixa3.setWrapStyleWord(true);
        caixa3.setBackground(Color.GRAY);
        caixa3.setForeground(Color.WHITE);
        JScrollPane scrollCaixa3 = new JScrollPane(caixa3);
        scrollCaixa3.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        painel.add(scrollCaixa3);
        painel.add(Box.createRigidArea(new Dimension(0, 5)));

        caixa4 = new JTextArea("Bandeiras:\n" + "Com o botão direito do mouse, é possível posicionar uma bandeira\nA bandeira serve para marcar que naquela casa é uma mina.");
        caixa4.setFont(new Font("Arial", Font.BOLD, 16));
        caixa4.setEditable(false);
        caixa4.setLineWrap(true);
        caixa4.setWrapStyleWord(true);
        caixa4.setBackground(Color.GRAY);
        caixa4.setForeground(Color.WHITE);
        JScrollPane scrollCaixa4 = new JScrollPane(caixa4);
        scrollCaixa4.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        painel.add(scrollCaixa4);
        painel.add(Box.createRigidArea(new Dimension(0, 5)));

        caixa5 = new JTextArea("Derrota:\n" + "Ao perder 3 de vidas, o jogador perde. Perdes as vidas ao clicares na bomba.\nTambém é dado ao jogador 5 tabuleiros\nAo acabarem os tabuleiros o jogador perde");
        caixa5.setFont(new Font("Arial", Font.BOLD, 16));
        caixa5.setEditable(false);
        caixa5.setLineWrap(true);
        caixa5.setWrapStyleWord(true);
        caixa5.setBackground(Color.GRAY);
        caixa5.setForeground(Color.WHITE);
        JScrollPane scrollCaixa5 = new JScrollPane(caixa5);
        scrollCaixa5.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        painel.add(scrollCaixa5);
        painel.add(Box.createRigidArea(new Dimension(0, 5)));

        caixa6 = new JTextArea("Passar de nível:\n" + "Para passar de nível é preciso conseguir o número de pontos requeridos para passar\nCada casa com números dá pontos\nBloco 1- 1 Ponto\nBloco 2- 2+1=3 Pontos\nBloco 3- 3+2+1 = 6 Pontos\nBloco 4- 4+3+2+1 = 10 Pontos\netc...");
        caixa6.setFont(new Font("Arial", Font.BOLD, 16));
        caixa6.setEditable(false);
        caixa6.setLineWrap(true);
        caixa6.setWrapStyleWord(true);
        caixa6.setBackground(Color.GRAY);
        caixa6.setForeground(Color.WHITE);
        JScrollPane scrollCaixa6 = new JScrollPane(caixa6);
        scrollCaixa6.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        painel.add(scrollCaixa6);
        painel.add(Box.createRigidArea(new Dimension(0, 5)));

        caixa7 = new JTextArea("Vitória:\n" + "Para ganhar é preciso ganhar 21 níveis e derrotar todos os boses que vierem pelo caminho");
        caixa7.setFont(new Font("Arial", Font.BOLD, 16));
        caixa7.setEditable(false);
        caixa7.setLineWrap(true);
        caixa7.setWrapStyleWord(true);
        caixa7.setBackground(Color.GRAY);
        caixa7.setForeground(Color.WHITE);
        JScrollPane scrollCaixa7 = new JScrollPane(caixa7);
        scrollCaixa7.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        painel.add(scrollCaixa7);
        painel.add(Box.createRigidArea(new Dimension(0, 5)));

        caixa8 = new JTextArea("Boss:\n" + "De quatro em quatro níveis aparece um boss aleatorio\nNo nível 21 aparece um boss mais dificil que o normal");
        caixa8.setFont(new Font("Arial", Font.BOLD, 16));
        caixa8.setEditable(false);
        caixa8.setLineWrap(true);
        caixa8.setWrapStyleWord(true);
        caixa8.setBackground(Color.GRAY);
        caixa8.setForeground(Color.WHITE);
        JScrollPane scrollCaixa8 = new JScrollPane(caixa8);
        scrollCaixa8.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        painel.add(scrollCaixa8);
        painel.add(Box.createRigidArea(new Dimension(0, 5)));

        caixa9 = new JTextArea("Loja:\n" + "Vai gerar itens aleatórios que vai permitir o jogador conseguir mais pontos\nO nº maximo de itens que o jogador pode ter é 5.\nÉ possivel destruir itens, para dar a espaço a outros, clicando com o botão direito no item\nTambem é possivel aumentar o poder do jogador na loja\nO POder duplicará o nº de pontos obtidos dos blocos");
        caixa9.setFont(new Font("Arial", Font.BOLD, 16));
        caixa9.setEditable(false);
        caixa9.setLineWrap(true);
        caixa9.setWrapStyleWord(true);
        caixa9.setBackground(Color.GRAY);
        caixa9.setForeground(Color.WHITE);
        JScrollPane scrollCaixa9 = new JScrollPane(caixa9);
        scrollCaixa9.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        painel.add(scrollCaixa9);

        JScrollPane scrollPainel = new JScrollPane(painel);
        scrollPainel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPainel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPainel.getViewport().setBackground(Color.DARK_GRAY);

        add(scrollPainel);
    }
}