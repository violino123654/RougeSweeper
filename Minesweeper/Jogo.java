package Minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Scanner;
import javax.swing.border.LineBorder;

public class Jogo extends JFrame {
    private JPanel Jogo;
    private JPanel Titulo;
    private JPanel Pontos;
    private JPanel Vidas = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
    private JPanel Itens;


    int linhas = 8;
    int colunas = 9;
    int vidas = 3;
    int moedas = 3;
    int tabuleiros = 5;

    JLabel[] vidasIMG = new JLabel[3];
    JButton[][] botoes = new JButton[linhas][colunas];
    int[][] mapa = new int[linhas][colunas];
    boolean[][] mapaBandeiras = new boolean[linhas][colunas];
    boolean[][] mapaEscavado = new boolean[linhas][colunas];
    JLabel tabuleirosDisponiveis = new JLabel();

    int bombasTotal = 10;
    int nbombas = 10;
    int escavarParaGanhar = linhas*colunas-bombasTotal;

    int pontosNecessarios = 100;
    int pontos = 0;
    int pontosEstaRonda = 0;
    JLabel pontosAdquiridos = new JLabel();


    int[] amuletos = new int[5];
    int[] utilizaveis = new int[3];
    int poder = 1;

    int boss = 0;
    int nivel = 1;

    int blocosClicadosAleatoriamente = 0;

    ImageIcon icone;
    boolean poderClicar = true;

    private void perder(){
        dispose();
        Perder perder = new Perder();
        perder.setTitle("Roguesweeper");
        perder.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        perder.pack();
        perder.setLocationRelativeTo(null);
        perder.setVisible(true);
    }

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

        if(valor < 0){
            if(!mapaEscavado[linha][coluna]){
                mapaEscavado[linha][coluna] = true;

                ImageIcon minaGIF = new ImageIcon(getClass().getResource("/gifs/gif/Mina.gif"));
                botoes[linha][coluna].setIcon(minaGIF);

                vidas--;

                if(valor == -2){
                    vidas = 0;
                }

                if(vidas > 0){
                    vidasIMG[vidas].setVisible(false);
                }else{
                    perder();
                }
            }
            return;
        }

        if(escavarParaGanhar == linhas*colunas-bombasTotal){
            ItensAoClicar(linha, coluna);
        }

        if(!mapaEscavado[linha][coluna]){
            mapaEscavado[linha][coluna] = true;

            if(valor != -1&&valor != -2){
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
            int w = 0;
            for (int l=-1; l<=1; l++){
                for (int c=-1; c<=1; c++){
                    if (linha + l >= 0 && linha + l < linhas && coluna + c >= 0 && coluna + c < colunas) {
                        if(!mapaEscavado[linha+l][coluna+c]){
                            w++;
                        }
                    }
                }
            }
            if(w == 8){
                blocosClicadosAleatoriamente++;
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
                if(mapa[l][c]>-1){
                    if(boss==3 && mapa[l][c]!=0){
                        pontosEstaRonda++;
                    }
                    else{
                        for(int r=0;r<=mapa[l][c];r++){
                            pontosEstaRonda += r;
                        }
                    }
                }
            }
        }
        pontosEstaRonda*=poder;
    }

    public void ItensAoTabuleiroGerar(){
        for(int i=0;i<5;i++){
            switch(amuletos[i]){
                case 0:{ //vazio
                    continue;
                }
                case 3:{ //Boneco pós apocaliptico
                    int BombaNuclear = 1;
                    while(BombaNuclear == 1){
                        int l = (int)(Math.random() * linhas);
                        int c = (int)(Math.random() * colunas);

                        if (mapa[l][c] == -1) {
                            mapa[l][c] = -2;
                            BombaNuclear--;
                        }
                    }
                    break;
                }
                default:{
                    continue;
                }
            }
        }
        if(boss==2){
            int BombaNuclear = 5;
            while(BombaNuclear != 0){
                int l = (int)(Math.random() * linhas);
                int c = (int)(Math.random() * colunas);

                if (mapa[l][c] == -1) {
                    mapa[l][c] = -2;
                    BombaNuclear--;
                }
            }
        }
        else if(boss==5){
            int BombaNuclear = 1;
            while(BombaNuclear == 1){
                int l = (int)(Math.random() * linhas);
                int c = (int)(Math.random() * colunas);

                if (mapa[l][c] == -1) {
                    mapa[l][c] = -2;
                    BombaNuclear--;
                }
            }
        }
    }

    public void funcaoItens(){
        for(int i=0;i<5;i++){
            switch(amuletos[i]){
                case 0:{ //vazio
                    continue;
                }
                case 1:{ //Venda de Olhos
                    if(blocosClicadosAleatoriamente>=1){
                        pontosEstaRonda *= (1.5*blocosClicadosAleatoriamente);
                    }
                    break;
                }
                case 2:{ //Curinga do Baralho
                    for(int l=0;l<linhas;l++){
                        for(int c=0;c<linhas;c++){
                            if(mapa[l][c]==0){
                                pontosEstaRonda += 5*poder;
                            }
                        }
                    }
                    break;
                }
                case 3:{ //Boneco pós apocaliptico
                    pontosEstaRonda *= 3;
                    break;
                }
                case 5:{ //Trevo de 4 folhas
                    if(Math.floor(Math.random() * 2) == 1){
                        moedas+=2;
                    }
                }
                case 6:{ //Mitosis
                    if(Math.floor(Math.random() * 2) == 1){
                        pontosEstaRonda *= 4;
                    }
                    else{
                        pontosEstaRonda = (pontosEstaRonda/3)*2;
                    }
                }
                case 7:{
                    int x = 3-vidas;
                    pontosEstaRonda = pontosEstaRonda + pontosEstaRonda*(x*2);
                }
            }
        }
    }

    public void ItensAoClicar(int linha, int coluna){
        for(int i=0;i<5;i++){
            switch(amuletos[i]){
                case 0:{ //vazio
                    continue;
                }
                case 4:{ // Lupa
                    int vidasPerdidas = 3-vidas;
                    if(vidasPerdidas == 0) {
                        break;
                    }
                    int reveladas = 0;
                    while(reveladas < vidasPerdidas){
                        int l = (int)(Math.random() * linhas);
                        int c = (int)(Math.random() * colunas);

                        if((mapa[l][c] == -1 || mapa[l][c] == -2) && !mapaEscavado[l][c]){
                            ImageIcon icone;
                            if(mapa[l][c] == -1){
                                icone = new ImageIcon(getClass().getResource("/gifs/gif/Mina.gif"));
                            }else{
                                icone = new ImageIcon(getClass().getResource("/imagens/png/Blocos/BombaNuclear.png"));
                            }
                            botoes[l][c].setIcon(new ImageIcon(icone.getImage().getScaledInstance(64,64,Image.SCALE_SMOOTH)));
                            reveladas++;
                        }
                    }
                    break;
                }
                case 8:{ //Caveira Fraturada
                    if(mapa[linha][coluna] <= -1){
                        pontosEstaRonda+=50*poder;
                    }
                }
            }
        }
    }

    public Jogo() {
        setLayout(new BorderLayout());
        setResizable(false);

        try {
            File file = new File("src/dadosJogador/dados.txt");
            Scanner scanner = new Scanner(file);
            moedas = scanner.nextInt();
            pontosNecessarios = scanner.nextInt();
            nivel = scanner.nextInt();
            poder = scanner.nextInt();
            tabuleiros = scanner.nextInt();
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

        if(nivel==21){
            boss = 1 + (int)(Math.random() * 4);
        }
        else if(nivel%4==0){
            boss = 1 + (int)(Math.random() * 9);
            while(boss<=4){
                boss = 1 + (int)(Math.random() * 9);
            }
        }
        else{
            boss = 0;
        }

        Titulo = new JPanel();
        Pontos = new JPanel();
        Jogo = new JPanel();
        Itens = new JPanel();

        ImageIcon vidaIMG = new ImageIcon(getClass().getResource("/gifs/gif/Vida.gif"));

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

        tabuleirosDisponiveis.setText("Tabuleiros: "+tabuleiros);

        Titulo.add(butaoDeAvancar);
        butaoDeAvancar.setVisible(false);
        Titulo.add(Vidas);
        Titulo.add(tabuleirosDisponiveis);
        add(Titulo, BorderLayout.NORTH);

        add(Pontos, BorderLayout.SOUTH);
        pontosAdquiridos.setText("Nível: "+nivel+"  Pontos para ganhar ronda: "+pontosNecessarios+" / "+pontos+"        ");
        Pontos.add(pontosAdquiridos);
        Pontos.setVisible(true);
        pontosAdquiridos.setVisible(true);


        JLabel BossIcone;
        Image BossImg;
        switch (boss){
            case 0:{
                BossImg = new ImageIcon(Jogo.class.getResource("/imagens/png/Bosses/BossIconeVazio.png")).getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                BossIcone = new JLabel(new ImageIcon(BossImg));
                BossIcone.setToolTipText("Um bem haja!");
                break;
            }
            case 1:{
                BossImg = new ImageIcon(Jogo.class.getResource("/imagens/png/Bosses/BossIconeVazio.png")).getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                BossIcone = new JLabel(new ImageIcon(BossImg));
                BossIcone.setToolTipText("Conquista: 4x pontos para acabar o nível");
                pontosNecessarios*=4;
                pontosAdquiridos.setText("Nível: "+nivel+"  Pontos para ganhar ronda: "+pontosNecessarios+" / "+pontos+"        ");
                break;
            }
            case 2:{
                BossImg = new ImageIcon(Jogo.class.getResource("/imagens/png/Bosses/BossIconeGuerra.png")).getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                BossIcone = new JLabel(new ImageIcon(BossImg));
                BossIcone.setToolTipText("Guerra: Metade das minas são bombas nucleares");
                break;
            }
            case 3:{
                BossImg = new ImageIcon(Jogo.class.getResource("/imagens/png/Bosses/BossIconeVazio.png")).getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                BossIcone = new JLabel(new ImageIcon(BossImg));
                BossIcone.setToolTipText("Fome: Todos os blocos numerados dão apenas 1 ponto");
                break;
            }
            case 4:{
                BossImg = new ImageIcon(Jogo.class.getResource("/imagens/png/Bosses/BossIconeMorte.png")).getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                BossIcone = new JLabel(new ImageIcon(BossImg));
                BossIcone.setToolTipText("Morte: -1 vida ao concluir um tabuleiro");
                break;
            }
            case 5:{
                BossImg = new ImageIcon(Jogo.class.getResource("/imagens/png/Bosses/BossIconeNuke.png")).getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                BossIcone = new JLabel(new ImageIcon(BossImg));
                BossIcone.setToolTipText("Inverno Nuclear: Uma mina é substituída por uma bomba nuclear");
                break;
            }
            case 6:{
                BossImg = new ImageIcon(Jogo.class.getResource("/imagens/png/Bosses/BossIconeOsso.png")).getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                BossIcone = new JLabel(new ImageIcon(BossImg));
                BossIcone.setToolTipText("Ossos Frágeis: Bombas dão x2 dano");
                break;
            }
            case 7:{
                BossImg = new ImageIcon(Jogo.class.getResource("/imagens/png/Bosses/BossIconeDuplasMInas.png")).getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                BossIcone = new JLabel(new ImageIcon(BossImg));
                BossIcone.setToolTipText("Bosnia: x1.5 minas");
                nbombas*=1.5;
                for(int i=0;i<linhas;i++){
                    for(int c=0;c<colunas;c++){
                        mapa[i][c]=0;
                    }
                }
                escavarParaGanhar = linhas*colunas-nbombas;
                numeros();
                break;
            }
            case 8:{
                BossImg = new ImageIcon(Jogo.class.getResource("/imagens/png/Bosses/BossIconeOuro.png")).getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                BossIcone = new JLabel(new ImageIcon(BossImg));
                BossIcone.setToolTipText("Ouro do Prospetor: -5 dinheiro ao desenterrar uma mina");
                break;
            }
            case 9:{
                BossImg = new ImageIcon(Jogo.class.getResource("/imagens/png/Bosses/BossIconeVazio.png")).getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                BossIcone = new JLabel(new ImageIcon(BossImg));
                BossIcone.setToolTipText("A Parede: 2x pontos para acabar o nível");
                pontosNecessarios*=2;
                pontosAdquiridos.setText("Nível: "+nivel+"  Pontos para ganhar ronda: "+pontosNecessarios+" / "+pontos+"        ");
                break;
            }
            default:{
                BossImg = new ImageIcon(Jogo.class.getResource("/imagens/png/Bosses/BossIconeVazio.png")).getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                BossIcone = new JLabel(new ImageIcon(BossImg));
                BossIcone.setToolTipText("Um bem haja!");
                break;
            }
        }
        Pontos.add(BossIcone);

        add(Jogo, BorderLayout.CENTER);
        Jogo.setLayout(new GridLayout(linhas, colunas));
        Jogo.setVisible(true);

        ImageIcon barraItens = new ImageIcon(getClass().getResource("/imagens/png/Itens/BarraDeItens.png"));
        JLabel barra = new JLabel(barraItens);
        Itens.add(barra);

        //ImageIcon barraUtilizaveis = new ImageIcon(getClass().getResource("/imagens/png/Itens/BarraDeUtilizaveis.png"));
        //JLabel itensUtilizaveis = new JLabel(barraUtilizaveis);
        //Vidas.add(itensUtilizaveis);

        JLayeredPane camadasItens = new JLayeredPane();
        camadasItens.setPreferredSize(new Dimension(64, 320));
        camadasItens.setLayout(null);
        Itens.setBounds(0, 0, 64, 400);
        camadasItens.add(Itens, Integer.valueOf(0));

        add(camadasItens, BorderLayout.EAST);

        for(int i=0;i<5;i++){
            int g = amuletos[i];
            ImageIcon itensImagem = new ImageIcon(getClass().getResource("/imagens/png/Itens/Amuletos/Item"+g+".png"));
            JLabel item = new JLabel(itensImagem);
            item.setIcon(new ImageIcon(itensImagem.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
            item.setBounds(0,5+(64*i),64,64);
            camadasItens.add(item, Integer.valueOf(i+1));
        }

        add(camadasItens, BorderLayout.EAST);

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

                final ImageIcon gifMina;
                if(mapa[i][j] == -1){

                    int numAleatorio = 1 + (int)(Math.random() * 2);

                    if(numAleatorio == 1){
                        gifMina = new ImageIcon(getClass().getResource("/gifs/gif/Mina.gif"));
                    }
                    else {
                        gifMina = new ImageIcon(getClass().getResource("/gifs/gif/Mina2.gif"));
                    }
                }
                else if(mapa[i][j] == -2){
                    gifMina = new ImageIcon(getClass().getResource("/imagens/png/Blocos/BombaNuclear.png"));
                }
                else{
                    gifMina = new ImageIcon(getClass().getResource("/gifs/gif/Mina.gif"));
                }


                botao.addActionListener(e -> {
                    ImageIcon iconePreenchido;
                    int valor = mapa[linha][coluna];
                    if(valor<0 && mapaBandeiras[linha][coluna]==false){//ao escavar mina

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
                            botao.setIcon(gifMina);
                            if(boss==6){ //Ossos Frageis
                                vidas-=2;
                                vidasIMG[vidas+1].setVisible(false);
                            }
                            else{
                                vidas--;
                            }
                            if(boss==8){
                                moedas-=5;
                            }

                            if(mapa[linha][coluna]==-2){
                                vidas = 0;
                            }
                            if (vidas > 0){
                                vidasIMG[vidas].setVisible(false);
                                mapaEscavado[linha][coluna] = true;
                            }
                            else if(vidas <= 0){
                                perder();
                            }
                        }
                    }
                    else{
                        escavar(linha, coluna);
                    }

                    if(escavarParaGanhar==0){
                        tabuleiros--;
                        contarPontos();
                        funcaoItens();
                        pontos += pontosEstaRonda;
                        pontosAdquiridos.setText("Nível: "+nivel+"  Pontos para ganhar ronda: "+pontosNecessarios+" / "+pontos+"        ");
                        tabuleirosDisponiveis.setText("Tabuleiros: "+tabuleiros);
                        pontosEstaRonda = 0;

                        if(nivel==21){
                            dispose();
                            Ganhar ganhar = new Ganhar();
                            ganhar.setTitle("Roguesweeper");
                            ganhar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            ganhar.pack();
                            ganhar.setLocationRelativeTo(null);
                            ganhar.setVisible(true);
                        }
                        else if(pontos>=pontosNecessarios){
                            butaoDeAvancar.setVisible(true);
                        }
                        else if(tabuleiros==0){
                            perder();
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
                            if(boss==4){ //Morte
                                vidas--;
                                vidasIMG[vidas].setVisible(false);
                                if(vidas <= 0){
                                    perder();
                                }
                            }
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
        ItensAoTabuleiroGerar();

        butaoDeAvancar.addActionListener(e -> {
            if(poderClicar){
                poderClicar = false;
                moedas += tabuleiros+1;
                nivel++;
                tabuleiros = 5;
                FileWriter writer = null;
                try {
                    writer = new FileWriter("src/dadosJogador/dados.txt");
                    writer.write(String.valueOf(moedas+"\n"+(int)(pontosNecessarios)+"\n"+nivel+"\n"+poder+"\n"+tabuleiros));
                    writer.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    writer = new FileWriter("src/dadosJogador/itens.txt");
                    for(int i=0;i<5;i++){
                        writer.write(String.valueOf(amuletos[i]+"\n"));
                    }
                    writer.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                setVisible(false);
                Loja loja = new Loja();
                loja.setTitle("Roguesweeper");
                loja.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                loja.pack();
                loja.setLocationRelativeTo(null);
                loja.setVisible(true);
            }
        });
    }

}