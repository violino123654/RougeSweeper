package Minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Loja extends JFrame {
    private JLayeredPane camadas;
    private JLabel lblMoedas;
    JPanel Itens = new JPanel();
    int moedas = 0;
    int pontos = 0;
    int nivel = 1;
    int poder = 1;
    int tabuleiros = 5;
    int[] amuletos = new int[5];
    int[] itensParaCompra = new int[3];
    JLayeredPane camadasItens = new JLayeredPane();

    boolean poderClicar = true;


    public Loja() {
        setLayout(new BorderLayout());
        setResizable(false);

        try {
            File file = new File("src/dadosJogador/dados.txt");
            Scanner scanner = new Scanner(file);
            moedas = scanner.nextInt();
            pontos = scanner.nextInt();
            nivel = scanner.nextInt();
            poder = scanner.nextInt();
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro não encontrado!");
        }
        try {

            File file = new File("src/dadosJogador/itens.txt");
            Scanner scanner = new Scanner(file);
            for(int i = 0; i < 5; i++) {
                amuletos[i] = scanner.nextInt();
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro não encontrado!");
        }

       ToolTipManager.sharedInstance().setInitialDelay(0);

        camadas = new JLayeredPane();
        camadas.setPreferredSize(new Dimension(450, 350));
        camadas.setLayout(null);


        JButton butaoDeAvancar = new JButton();
        butaoDeAvancar.setText("Continuar");

        ImageIcon barraItens = new ImageIcon(getClass().getResource("/imagens/png/Itens/BarraDeItens.png"));
        JLabel barra = new JLabel(barraItens);
        Itens.add(barra);

        camadasItens.setPreferredSize(new Dimension(64, 320));
        camadasItens.setLayout(null);
        Itens.setBounds(0, 0, 64, 400);
        camadasItens.add(Itens, Integer.valueOf(1));


        for(int i=0;i<5;i++){
            int g = amuletos[i];
            ImageIcon itensImagem = new ImageIcon(getClass().getResource("/imagens/png/Itens/Amuletos/Item"+g+".png"));
            JLabel item = new JLabel(itensImagem);
            item.setIcon(new ImageIcon(itensImagem.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
            item.setBounds(0,5+(64*i),64,64);
            camadasItens.add(item, Integer.valueOf(i+2));

            int finalI = i;
            item.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        amuletos[finalI] = 0;
                        item.setVisible(false);
                    }
                }
            });
        }


        ImageIcon loja2IMG = new ImageIcon(Loja.class.getResource("/imagens/png/loja2.png"));
        Image loja2Redimensionada = loja2IMG.getImage().getScaledInstance(450, 250, Image.SCALE_SMOOTH);
        JLabel imgLoja2 = new JLabel(new ImageIcon(loja2Redimensionada));
        imgLoja2.setBounds(0, 57, 450, 250);
        camadas.add(imgLoja2, Integer.valueOf(1));

        lblMoedas = new JLabel("Moedas: " + moedas);
        lblMoedas.setBounds(175, 20, 120, 30);
        lblMoedas.setForeground(Color.YELLOW);
        lblMoedas.setFont(new Font("Arial", Font.BOLD, 16));
        lblMoedas.setOpaque(true);
        lblMoedas.setBackground(new Color(0, 0, 0, 150));
        lblMoedas.setHorizontalAlignment(SwingConstants.CENTER);
        camadas.add(lblMoedas, Integer.valueOf(5));


        int x = 100;
        int y = 130;

        for(int i=0;i<3;i++){
            int l = 1 + (int)(Math.random() * 8);

            if(i>=1){
                while(l==itensParaCompra[i-1]){
                    l = 1 + (int)(Math.random() * 8);
                }
            }
            if(i==2){
                while(l==itensParaCompra[i-2]){
                    l = 1 + (int)(Math.random() * 8);
                }
            }
            JLabel itemv = null;
            Image ItemImg = new ImageIcon(Loja.class.getResource("/imagens/png/Itens/Amuletos/Item"+l+".png")).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            switch (l){
                    case 1:{
                        itemv = criarItem(ItemImg, x, y, 8, "Venda de olhos: 1.5x pontos a cada bloco clicado aleatoriamente no mapa", l, i);
                        break;
                    }
                    case 2:{
                        itemv = criarItem(ItemImg, x, y, 3, "Joker: +5 pontos por cada espaço vazio no mapa  (Sobe com Poder)", l, i);
                        break;
                    }
                    case 3:{
                        itemv = criarItem(ItemImg, x, y, 4, "Boneco pós apocaliptico: 3x pontos mas uma das minas é uma bomba nuclear", l, i);
                        break;
                    }
                    case 4:{
                        itemv = criarItem(ItemImg, x, y, 1, "Lupa: Revela minas no campo consoante o nº de vidas perdidas (1 vida perdida = 1 mina)", l, i);
                        break;
                    }
                    case 5:{
                        itemv = criarItem(ItemImg, x, y, 4, "Trevo de 4 folhas: 50% de chance de conseguir duas moedas ao completar um tabuleiro", l, i);
                        break;
                    }
                    case 6:{
                        itemv = criarItem(ItemImg, x, y, 3, "Mitosis: 50% de x4 pontos, 50% de perder um terço dos pontos acumulados nesta ronda", l, i);
                        break;
                    }
                    case 7:{
                        itemv = criarItem(ItemImg, x, y, 3, "Granada em formato de coração: x2 pontos a cada vida perdida", l, i);
                        break;
                    }
                    case 8:{
                        itemv = criarItem(ItemImg, x, y, 1, "Caveira Fraturada: +50 pontos ao levar dano  (Sobe com Poder)", l, i);
                        break;
                    }
                }
            itensParaCompra[i] = l;
            camadas.add(itemv, Integer.valueOf(4));
        }

        JButton aumentarPoder = new JButton();
        aumentarPoder.setText(" * Aumentar poder dos blocos  Preço: "+poder*5+" * ");
        aumentarPoder.setBounds(0, 298, 450, 30);
        aumentarPoder.addActionListener(e -> {
            if(moedas>=poder*5){
                int precoPoder = poder*5;
                moedas-=precoPoder;
                lblMoedas.setText("Moedas: " + moedas);
                camadas.repaint();
                poder++;
                precoPoder = poder*5;
                aumentarPoder.setText(" * Aumentar poder dos blocos  Preço: "+precoPoder+" * ");
            }
            else{
                JOptionPane.showMessageDialog(Loja.this, "Moedas insuficientes!");
            }
        });

        camadas.add(aumentarPoder, Integer.valueOf(3));

        add(camadas, BorderLayout.CENTER);
        add(camadasItens, BorderLayout.EAST);
        add(butaoDeAvancar, BorderLayout.SOUTH);

        ImageIcon vendedorIMG = new ImageIcon(Loja.class.getResource("/imagens/png/Vendedores/AmuletosDealer.png"));
        Image vendedorRedimensionado = vendedorIMG.getImage().getScaledInstance(150, 250, Image.SCALE_SMOOTH);
        JLabel imgVendedor = new JLabel(new ImageIcon(vendedorRedimensionado));
        add(imgVendedor, BorderLayout.WEST);

        butaoDeAvancar.addActionListener(e -> {
            if(poderClicar){
                FileWriter writer = null;
                try {
                    writer = new FileWriter("src/dadosJogador/dados.txt");
                    writer.write(moedas+"\n");
                    if(nivel%4==0){
                        pontos*=2;
                    }
                    else if((nivel-1)%4==0){
                        pontos/=1.5;
                    }
                    else{
                        pontos*=1.5;
                    }
                    writer.write((int)(pontos)+"\n"+nivel+"\n"+poder+"\n"+tabuleiros);
                    writer.close();
                }
                catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    writer = new FileWriter("src/dadosJogador/itens.txt");
                    for(int i=0;i<5;i++){
                        writer.write(String.valueOf(amuletos[i]+"\n"));
                    }
                    writer.close();
                }
                catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                setVisible(false);
                Jogo jogo = new Jogo();
                jogo.setTitle("Roguesweeper");
                jogo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                jogo.pack();
                jogo.setLocationRelativeTo(null);
                jogo.setVisible(true);
                poderClicar = false;
            }
        });

    }
    private void atualizarBarraItens(){
        camadasItens.removeAll();

        camadasItens.add(Itens, Integer.valueOf(1));
        for (int i = 0; i < 5; i++) {
            int g = amuletos[i];
            if (g != 0) {
                ImageIcon itensImagem = new ImageIcon(getClass().getResource("/imagens/png/Itens/Amuletos/Item"+g+".png"));
                JLabel item = new JLabel(new ImageIcon(itensImagem.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
                item.setBounds(0, 5 + (64 * i), 64, 64);
                camadasItens.add(item, Integer.valueOf(i + 2));

                int finalI = i;
                item.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (SwingUtilities.isRightMouseButton(e)) {
                            amuletos[finalI] = 0;
                            item.setVisible(false);
                        }
                    }
                });
            }
        }
    }

    private JLabel criarItem(Image img, int xItem, int yItem, int preco, String tooltip, int l, int i) {

        JLabel item = new JLabel(new ImageIcon(img));
        item.setBounds(xItem + (90*i), yItem, 80, 80);

        item.setToolTipText(tooltip);

        JLabel lblPreco = new JLabel("Preço: " + preco);
        lblPreco.setBounds(xItem + (90*i), yItem + 85, 80, 20);
        lblPreco.setHorizontalAlignment(SwingConstants.CENTER);
        lblPreco.setForeground(Color.YELLOW);
        lblPreco.setOpaque(true);
        lblPreco.setBackground(new Color(0, 0, 0, 150));

        camadas.add(lblPreco, Integer.valueOf(4));

        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (item.isVisible()){

                    boolean invCheio = false;
                    int c = 0;
                    for(int i=0;i<5;i++){
                        if(amuletos[i]!=0){
                            c++;
                        }
                    }
                    if(c==5){
                        invCheio = true;
                    }

                    if(invCheio){
                        JOptionPane.showMessageDialog(Loja.this, "Inventario Cheio!");
                    }
                    else if (moedas >= preco) {
                        moedas -= preco;
                        lblMoedas.setText("Moedas: " + moedas);
                        item.setVisible(false);
                        lblPreco.setVisible(false);
                        for(int i=0;i<5;i++){
                            if(amuletos[i]==0){
                                amuletos[i] = l;
                                i=4;
                            }
                            atualizarBarraItens();
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(Loja.this, "Moedas insuficientes!");
                    }
                }


            }
        });
        return item;
    }
}