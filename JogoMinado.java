package Minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.LineBorder;

public class JogoMinado extends JFrame {
    private JPanel Jogo;
    private JPanel Titulo;
    private JPanel Vidas = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
    private Menu menu;

    int linhas = 8;
    int colunas = 9;
    int vidas = 3;

    JButton[][] botoes = new JButton[linhas][colunas];
    int[][] mapa = new int[linhas][colunas];
    boolean[][] mapaBandeiras = new boolean[linhas][colunas];
    boolean[][] mapaEscavado = new boolean[linhas][colunas];

    int nbombas = 10;
    int escavarParaGanhar = linhas*colunas-nbombas;

    ImageIcon icone;

    public void numeros() {
        for (int r = 0; r < linhas; r++) {
            for (int c = 0; c < colunas; c++) {
                if (mapa[r][c] == -1){
                    continue;
                }
                int n = 0;
                for (int vlinhas = -1; vlinhas <= 1; vlinhas++) {
                    for (int vcolunas = -1; vcolunas <= 1; vcolunas++) {

                        if (vlinhas == 0 && vcolunas == 0){
                            continue;
                        }

                        int nlinhas = r + vlinhas;
                        int ncolunas = c + vcolunas;

                        if (nlinhas >= 0 && nlinhas < linhas && ncolunas >= 0 && ncolunas < colunas) {
                            if (mapa[nlinhas][ncolunas] == -1) {
                                n++;
                            }
                        }
                    }
                }

                mapa[r][c] = n;
            }
        }
    }

    public void escavar(int linha, int coluna){


        if (linha < 0 || linha >= linhas || coluna < 0 || coluna >= colunas || mapaBandeiras[linha][coluna]){
            return;
        }

        int valor = mapa[linha][coluna];
        ImageIcon icone;

        // Se ainda não foi escavada, marca como escavada
        if(!mapaEscavado[linha][coluna]){
            mapaEscavado[linha][coluna] = true;

            if(valor != -1){
                escavarParaGanhar--;
            }

            if(valor == 0){
                // Bloco vazio: escava vizinhos
                icone = new ImageIcon(getClass().getResource("/imagens/png/Blocos/BlocoPreenchido.png"));
                botoes[linha][coluna].setIcon(new ImageIcon(icone.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));

                for (int r=-1; r<=1; r++){
                    for (int c=-1; c<=1; c++){
                        if(r!=0 || c!=0){
                            escavar(linha+r, coluna+c);
                        }
                    }
                }
                return;
            }
            else if(valor > 0){
                icone = new ImageIcon(getClass().getResource("/imagens/png/Blocos/Bloco"+valor+".png"));
                botoes[linha][coluna].setIcon(new ImageIcon(icone.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
            }
        }

        // Agora, se a célula já estiver escavada e for número, verifica bandeiras ao redor
        if(valor > 0){
            int bandeirasAoRedor = 0;
            for(int l=-1; l<=1; l++){
                for(int c=-1; c<=1; c++){
                    int nl = linha+l;
                    int nc = coluna+c;
                    if(nl>=0 && nl<linhas && nc>=0 && nc<colunas){
                        if(mapaBandeiras[nl][nc]){
                            bandeirasAoRedor++;
                        }
                    }
                }
            }

            // Se número de bandeiras == valor da célula, escava vizinhos não escavados
            if(bandeirasAoRedor == valor){
                for(int l=-1; l<=1; l++){
                    for(int c=-1; c<=1; c++){
                        int nl = linha+l;
                        int nc = coluna+c;
                        if(nl>=0 && nl<linhas && nc>=0 && nc<colunas){
                            if(!mapaEscavado[nl][nc] && !mapaBandeiras[nl][nc]){
                                // Atenção: aqui se for mina, vai explodir normalmente
                                escavar(nl,nc);
                            }
                        }
                    }
                }
            }
        }
    }

    public JogoMinado(Menu menu) {
        setLayout(new BorderLayout());
        setResizable(false);
        this.menu = menu;

        Titulo = new JPanel();
        Jogo = new JPanel();

        ImageIcon vidaIMG = new ImageIcon(getClass().getResource("/gifs/gif/Vida.gif"));

        JLabel[] vidasIMG = new JLabel[3];
        JLabel img1 = new JLabel(vidaIMG);
        JLabel img2 = new JLabel(vidaIMG);
        JLabel img3 = new JLabel(vidaIMG);

        vidasIMG[0] = img1;
        vidasIMG[1] = img2;
        vidasIMG[2] = img3;

        Vidas.add(vidasIMG[0]);
        Vidas.add(vidasIMG[1]);
        Vidas.add(vidasIMG[2]);

        Titulo.add(Vidas, BorderLayout.WEST);

        add(Titulo, BorderLayout.NORTH);
        add(Jogo, BorderLayout.CENTER);

        Jogo.setLayout(new GridLayout(linhas, colunas));

        while(nbombas>0){
            int l = (int)(Math.random() * linhas);
            int c = (int)(Math.random() * colunas);

            if (mapa[l][c] != -1) {
                mapa[l][c] = -1;
                nbombas--;
            }
        }

        numeros();

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                int linha = i;
                int coluna = j;
                JButton botao = new JButton();
                icone = new ImageIcon(getClass().getResource("/imagens/png/Blocos/BlocoVazio.png"));


                botao.setPreferredSize(new Dimension(64, 64));
                botao.setIcon(new ImageIcon(icone.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));

                botao.setBorderPainted(false);
                botao.setFocusPainted(false);
                botao.setOpaque(false);
                botao.setContentAreaFilled(false);

                final ImageIcon[] gifMina = new ImageIcon[1];
                if(mapa[i][j] == -1){
                    if(escavarParaGanhar==linhas*colunas-nbombas){
                        int l, c;
                        while(mapa[i][j]==-1){
                            mapa[i][j]=0;
                            l = (int)Math.random() * linhas;
                            c = (int)Math.random() * colunas;
                            if(mapa[l][c] != -1){
                                mapa[l][c] = -1;
                            }
                        }
                        numeros();
                    }
                    int numAleatorio = (int)(Math.random() * 2);
                    if(numAleatorio == 0){
                        gifMina[0] = new ImageIcon(getClass().getResource("/gifs/gif/Mina.gif"));
                    } else {
                        gifMina[0] = new ImageIcon(getClass().getResource("/gifs/gif/Mina2.gif"));
                    }
                }

                botao.addActionListener(e -> {
                    ImageIcon iconePreenchido;
                    int valor = mapa[linha][coluna];
                        if(valor==-1 && mapaBandeiras[linha][coluna]==false){
                            botao.setIcon(gifMina[0]);
                            vidas--;
                            if (vidas > 0){
                                vidasIMG[vidas].setVisible(false);
                                mapaEscavado[linha][coluna] = true;
                            }
                            else if(vidas <= 0){
                                dispose();
                                menu.setVisible(true);
                                mapaEscavado[linha][coluna] = true;
                            }
                        }
                        else{
                            escavar(linha, coluna);
                        }

                        if(escavarParaGanhar==0){
                            dispose();
                            JogoMinado jogo = new JogoMinado(menu);
                            jogo.setTitle("Minesweeper");
                            jogo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            jogo.pack();
                            jogo.setLocationRelativeTo(null);
                            jogo.setVisible(true);
                         }
                });

                botao.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e){
                        botao.setBorder(new LineBorder(Color.WHITE, 2));
                        botao.setBorderPainted(true);
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (SwingUtilities.isRightMouseButton(e)) {
                            ImageIcon icone;

                            if (!mapaBandeiras[linha][coluna] && mapaEscavado[linha][coluna] == false) {
                                icone = new ImageIcon(getClass().getResource("/imagens/png/Blocos/Bandeira.png"));
                                botao.setIcon(new ImageIcon(icone.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
                                mapaBandeiras[linha][coluna] = true;
                            }
                            else if(mapaBandeiras[linha][coluna] && mapaEscavado[linha][coluna] == false){
                                icone = new ImageIcon(getClass().getResource("/imagens/png/Blocos/BlocoVazio.png"));
                                botao.setIcon(new ImageIcon(icone.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
                                mapaBandeiras[linha][coluna] = false;
                            }
                        }
                    }

                    @Override
                    public void mouseExited(MouseEvent e){
                        botao.setBorder(UIManager.getBorder("Button.border"));
                        botao.setBorderPainted(false);
                    }});

                botoes[i][j] = botao;
                Jogo.add(botao);
            }
        }
    }

}
