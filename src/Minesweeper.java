package pkg15a.minesweeper;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Minesweeper extends JFrame implements KeyListener, MouseListener {

    Game game = new Game();
    private int mouseX = 0, mouseY = 0;
    
    public Minesweeper() {
        super("Minesweeper");
        add(new GamePane());
        this.addKeyListener(this);
        this.addMouseListener(this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Minesweeper frame = new Minesweeper();
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            }
        });
    }
    public class GamePane extends JPanel {
        public BufferedImage[] img = new BufferedImage[13];//img
        
        private int scale = 4;
        int tileHeight = 12, tileWidth = 12;

        public GamePane() {
            loadImages();
            game.gen(1);
            Timer timer = new Timer(50, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateMouse();
                    repaint();
                    game.open();
                    if(game.checkWin()){
                        //System.exit(0);
                    }
                }
            });
            timer.start();
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(game.width * tileWidth * scale, game.height * tileHeight * scale);
        }

        public void loadImages() {
            for (int i = 0; i < 13; i++) {//img
                try {
                    img[i] =  ImageIO.read(this.getClass().getResource(i+".png"));
                    Graphics2D g2d = img[i].createGraphics();
                    g2d.dispose();
                } catch (Exception ex) {
                    System.out.println("Missing Image");
                    ex.printStackTrace();
                }
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setTransform(AffineTransform.getScaleInstance(scale, scale));
            for (int x = 0; x < game.width; x++) {
                for (int y = 0; y < game.height; y++) {
                    if(game.r[x][y] && !game.m[x][y]){
                        g2d.drawImage(img[game.t[x][y]+1], x * tileWidth, y * tileHeight, this);
                    }else{
                        g2d.drawImage(img[0], x * tileWidth, y * tileHeight, this);
                        if(game.f[x][y]){
                            g2d.drawImage(img[10], x * tileWidth, y * tileHeight, this);
                        }
                        if(game.m[x][y] && game.r[x][y]){
                            g2d.drawImage(img[11], x * tileWidth, y * tileHeight, this);
                        }
                    }
                }
            }
            if(game.checkWin()){
                g2d.drawImage(img[12], ((game.width/2)*tileWidth)-23, ((game.height/2)*tileHeight)-20, this);
            }
            g2d.dispose();
        }
        private void updateMouse(){
            mouseX=(MouseInfo.getPointerInfo().getLocation().x-getLocationOnScreen().x)/tileWidth/scale;
            mouseY=(MouseInfo.getPointerInfo().getLocation().y-getLocationOnScreen().y)/tileHeight/scale;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_W){
            for (int x = 0; x < game.width; x++) {
                for (int y = 0; y < game.height; y++) {
                    game.r[x][y]= true;
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton()==MouseEvent.BUTTON1){
            if(game.m[mouseX][mouseY]){
                System.out.println("You Lost");
                System.exit(0);
            }
            game.reveal(mouseX, mouseY);
        }
        if(e.getButton()==MouseEvent.BUTTON3){
            game.f[mouseX][mouseY] = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}