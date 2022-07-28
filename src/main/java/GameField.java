import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.Key;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 480;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 900;

    private Image dot;
    private Image apple;
    public int score=0;

    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int appleX1, appleX2, appleX3;
    private int appleY1, appleY2, appleY3;
    private int dots;
    private Timer timer;
    private boolean left=false;
    private boolean right=true;
    private boolean up=false;
    private boolean down=false;
    private boolean inGame = true;

    public void loadImage() {
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("dot.png");
        dot = iid.getImage();
    }

    public void createApple() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            appleX1 = random.nextInt(30) * DOT_SIZE;
            appleX2 = random.nextInt(30) * DOT_SIZE;
            appleX3 = random.nextInt(30) * DOT_SIZE;
            if (appleX1 != appleX2 && appleX3 != appleX2 && appleX1 != appleX3) {
                break;
            }
        }
        for (int i = 0; i < 100; i++) {
            appleY1 = random.nextInt(30) * DOT_SIZE;
            appleY2 = random.nextInt(30) * DOT_SIZE;
            appleY3 = random.nextInt(30) * DOT_SIZE;
            if (appleY1 != appleY2 && appleY3 != appleY2 && appleY1 != appleY3) {
                break;
            }
        }
    }

    public void initGame() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            y[i] = 48;
            x[i] = 48 - i * DOT_SIZE;
        }

        timer = new Timer(150, this);
        timer.start();
        createApple();
    }

    public void checkApple() {
        if (x[0] == appleX1 && y[0] == appleY1) {
            dots++;
            score++;
            Random random = new Random();
            for (int i = 0; i < 100; i++) {
                appleX1 = random.nextInt(30) * DOT_SIZE;
                if (appleX1 != appleX2 && appleX1 != appleX3) {
                    break;
                }
                for (int j = 0; j < 100; j++) {
                    appleY1 = random.nextInt(30) * DOT_SIZE;
                    if (appleY1 != appleY2 && appleY1 != appleY3) {
                        break;
                    }
                }
            }

        }
        if (x[0] == appleX2 && y[0] == appleY2) {
            dots++;
            score++;
            Random random = new Random();
            for (int i = 0; i < 100; i++) {
                appleX2 = random.nextInt(30) * DOT_SIZE;
                if (appleX2 != appleX1 && appleX2 != appleX3) {
                    break;
                }
                for (int j = 0; j < 100; j++) {
                    appleY2 = random.nextInt(30) * DOT_SIZE;
                    if (appleY2 != appleY1 && appleY2 != appleY3) {
                        break;
                    }
                }

            }
        }
        if (x[0] == appleX3 && y[0] == appleY3) {
            dots++;
            score++;
            Random random = new Random();
            for (int i = 0; i < 100; i++) {
                appleX3 = random.nextInt(30) * DOT_SIZE;
                if (appleX3 != appleX1 && appleX2 != appleX3) {
                    break;
                }
                for (int j = 0; j < 100; j++) {
                    appleY3 = random.nextInt(30) * DOT_SIZE;
                    if (appleY3 != appleY1 && appleY2 != appleY3) {
                        break;
                    }
                }
            }
        }
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(inGame){
            g.drawImage(apple, appleX1, appleY1,this);
            g.drawImage(apple, appleX2, appleY2,this);
            g.drawImage(apple, appleX3, appleY3,this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i],this);
            }
        }else{
            String str="Game Over";
            String str1="Your score's "+score;
            g.setColor(Color.WHITE);
            g.drawString(str, SIZE/(5/2), SIZE/2);
            g.drawString(str1, 1, 10);
        }
    }
    public void checkCollision(){
        for (int i = dots; i > 0; i--) {
            if(x[0]==x[i]&&y[0]==y[i]){
                inGame=false;
            }
            if(x[0]>SIZE){
                inGame=false;
            }
            if(x[0]<0){
                inGame=false;
            }
            if(y[0]>SIZE){
                inGame=false;
            }
            if(y[0]<0){
                inGame=false;
            }
        }
    }
    public void actionPerformed(ActiveEvent a){
        if(inGame){
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }
    public GameField(){
        setBackground(Color.BLACK);
        loadImage();
        initGame();
        addKeyListener(new FiledKeyListener());
        setFocusable(true);
    }
    public void move(){
        for (int i = dots; i > 0; i--) {
            x[i]=x[i-1];
            y[i]=y[i-1];

        }
        if(right){
            x[0]+=DOT_SIZE;
        }
        if(left){
            x[0]-=DOT_SIZE;
        }
        if(up){
            y[0]-=DOT_SIZE;
        }
        if(down){
            y[0]+=DOT_SIZE;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame){
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }


    class FiledKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent k){
            super.keyPressed(k);
            int key=k.getKeyCode();
            if(key==KeyEvent.VK_LEFT&&!right){
                left=true;
                up=false;
                down=false;
            }
            if(key==KeyEvent.VK_RIGHT&&!left){
                right=true;
                up=false;
                down=false;
            }
            if(key==KeyEvent.VK_UP&&!down){
                up=true;
                left=false;
                right=false;
            }
            if(key==KeyEvent.VK_DOWN&&!up){
                down=true;
                left=false;
                right=false;
            }
        }
    }
}

