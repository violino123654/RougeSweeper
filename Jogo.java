package Minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.LineBorder;

public class Jogo extends JFrame {
    private JPanel Jogo;
    private JPanel Titulo;
    private JPanel Pontos;
    private JPanel Vidas = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
    private JPanel Itens;
    private Menu menu;
    private Loja loja;
    private Perder perder;

    int linhas = 8;
    int colunas = 9;
    int vidas = 3;

    JButton[][] botoes = new JButton[linhas][colunas];
    int[][] mapa = new int[linhas][colunas];
    boolean[][] mapaBandeiras = new boolean[linhas][colunas];
    boolean[][] mapaEscavado = new boolean[linhas][colunas];

    int bombasTotal = 10;
    int nbombas = 10;
    int escavarParaGanhar = linhas*colunas-bombasTotal;

    int pontosNecessarios = 100;
    int pontos = 0;
    JLabel pontosAdquiridos = new JLabel();

    int boss = 0;
    int niveisAteBoss = 4;

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

        if(!mapaEscavado[linha][coluna]){
            mapaEscavado[linha][coluna] = true;

            if(valor != -1){
                escavarParaGanhar--;
            }

            if(valor == 0){
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
                        else if(mapa[nl][nc]==-1 && mapaEscavado[nl][nc]==true){
                            bandeirasAoRedor++;
                        }
                    }
                }
            }

            if(bandeirasAoRedor == valor){
                for(int l=-1; l<=1; l++){
                    for(int c=-1; c<=1; c++){
                        int nl = linha+l;
                        int nc = coluna+c;
                        if(nl>=0 && nl<linhas && nc>=0 && nc<colunas){
                            if(!mapaEscavado[nl][nc] && !mapaBandeiras[nl][nc]){
                                escavar(nl,nc);
                            }
                        }
                    }
                }
            }
        }
    }

    public void contarPontos(){
        for(int  l=0;l<linhas;l++){
            for(int  c=0;c<colunas;c++){
                if(mapa[l][c]!=-1){
                    for(int r=0;r<=mapa[l][c];r++){
                        pontos += r;
                    }
                }
            }
        }
    }

    public Jogo(Menu menu) {
        setLayout(new BorderLayout());
        setResizable(false);
        this.menu = menu;

        Titulo = new JPanel();
        Pontos = new JPanel();
        Jogo = new JPanel();
        Itens = new JPanel();

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

        JButton butaoDeAvancar = new JButton();
        butaoDeAvancar.setText("Loja");

        Titulo.add(butaoDeAvancar);
        butaoDeAvancar.setVisible(false);
        Titulo.add(Vidas);
        add(Titulo, BorderLayout.NORTH);

        add(Pontos, BorderLayout.SOUTH);
        pontosAdquiridos.setText("0");
        Pontos.add(pontosAdquiridos);
        Pontos.setVisible(true);
        pontosAdquiridos.setVisible(true);

        add(Jogo, BorderLayout.CENTER);
        Jogo.setLayout(new GridLayout(linhas, colunas));
        Jogo.setVisible(true);

        ImageIcon barraItens = new ImageIcon(getClass().getResource("/imagens/png/Itens/BarraDeItens.png"));
        add(Itens, BorderLayout.EAST);
        JLabel barra = new JLabel(barraItens);
        Itens.add(barra);

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

                    int numAleatorio = (int)(Math.random() * 2);
                    if(numAleatorio == 1){
                        gifMina[0] = new ImageIcon(getClass().getResource("/gifs/gif/Mina.gif"));
                    }
                    else {
                        gifMina[0] = new ImageIcon(getClass().getResource("/gifs/gif/Mina2.gif"));
                    }
                }

                botao.addActionListener(e -> {
                    ImageIcon iconePreenchido;
                    int valor = mapa[linha][coluna];
                    if(valor==-1 && mapaBandeiras[linha][coluna]==false){

                        if(escavarParaGanhar==linhas*colunas-bombasTotal){
                            int l, c;
                            mapa[linha][coluna]=0;

                            do{
                                l = (int)(Math.random() * linhas);
                                c = (int)(Math.random() * colunas);
                            }while(mapa[l][c] == -1 || (l == linha && c == coluna));

                            mapa[l][c] = -1;
                            numeros();
                            escavar(linha, coluna);
                        }
                        else{
                            botao.setIcon(gifMina[0]);
                            vidas--;
                            if (vidas > 0){
                                vidasIMG[vidas].setVisible(false);
                                mapaEscavado[linha][coluna] = true;
                            }
                            else if(vidas <= 0){
                                dispose();
                                mapaEscavado[linha][coluna] = true;

                                Perder perder = new Perder();
                                perder.setTitle("Roguesweeper");
                                perder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                perder.pack();
                                perder.setLocationRelativeTo(null);
                                perder.setVisible(true);
                            }
                        }
                    }
                    else{
                        escavar(linha, coluna);
                    }

                    if(escavarParaGanhar==0){
                        contarPontos();
                        pontosAdquiridos.setText(Integer.toString(pontos));
                        if(pontos>=pontosNecessarios){
                            butaoDeAvancar.setVisible(true);
                        }
                        else{
                            for(int t=0; t<linhas; t++){
                                for(int g=0; g<colunas; g++){
                                    mapa[t][g] = 0;
                                    mapaBandeiras[t][g] = false;
                                    mapaEscavado[t][g] = false;
                                }
                            }
                            escavarParaGanhar = linhas*colunas-bombasTotal;
                            nbombas = bombasTotal;
                            while(nbombas>0){
                                int l = (int)(Math.random() * linhas);
                                int c = (int)(Math.random() * colunas);

                                if (mapa[l][c] != -1) {
                                    mapa[l][c] = -1;
                                    nbombas--;
                                }
                            }
                            icone = new ImageIcon(getClass().getResource("/imagens/png/Blocos/BlocoVazio.png"));
                            for(int t=0; t<linhas; t++){
                                for(int g=0; g<colunas; g++){
                                    botoes[t][g].setIcon(new ImageIcon(icone.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
                                }
                            }
                            numeros();
                        }
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

        butaoDeAvancar.addActionListener(e -> {
            setVisible(false);
            Loja loja = new Loja();
            loja.setTitle("Roguesweeper");
            loja.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loja.pack();
            loja.setLocationRelativeTo(null);
            loja.setVisible(true);
        });
    }

}