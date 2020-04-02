package pkg15a.minesweeper;

public class Game {
    final int width = 15, height = 15;
    boolean[][] m = new boolean[width][height];
    boolean[][] r = new boolean[width][height];
    boolean[][] f = new boolean[width][height];
    int[][] t = new int[width][height];
    
    public void gen(int diff){
        for(int x = 0; x < width; x++){
            for(int y = 0; y < width; y++){
                if(Math.random()<(.13*diff)){
                    m[x][y] = true;
                }
            }
        }
    }
    public void reveal(int x, int y){
        r[x][y] = true;
    }
    public void open(){
        for(int x = 0; x < width; x++){
            for(int y = 0; y < width; y++){
                if(x > 0){
                    if(r[x-1][y] && !m[x][y] && t[x][y] < 2){
                        r[x][y] = true;
                    }
                }
                if(x < width-1){
                    if(r[x+1][y] && !m[x][y] && t[x][y] < 2){
                        r[x][y] = true;
                    }
                }
                if(y > 0){
                    if(r[x][y-1] && !m[x][y] && t[x][y] < 2){
                        r[x][y] = true;
                    }
                }
                if(y < height-1){
                    if(r[x][y+1] && !m[x][y] && t[x][y] < 2){
                        r[x][y] = true;
                    }
                }
                t[x][y] = getNum(x, y);
            }
        }
    }
    public int getNum(int x, int y){
        int n = 0;
        if(!m[x][y]){
            if(x > 0){
                if(m[x-1][y]){
                    n++;
                }
            }
            if(x < width-1){
                if(m[x+1][y]){
                    n++;
                }
            }
            if(y > 0){
                if(m[x][y-1]){
                    n++;
                }
            }
            if(y < height-1){
                if(m[x][y+1]){
                    n++;
                }
            }
            if(x > 0 && y > 0){
                if(m[x-1][y-1]){
                    n++;
                }
            }
            if(x < width-1 && y > 0){
                if(m[x+1][y-1]){
                    n++;
                }
            }
            if(x > 0 && y < height-1){
                if(m[x-1][y+1]){
                    n++;
                }
            }
            if(x < width-1 && y < height-1){
                if(m[x+1][y+1]){
                    n++;
                }
            }
        }
        return n;
    }
    public boolean checkWin(){
        boolean w = true;
        for(int x = 0; x < width; x++){
            for(int y = 0; y < width; y++){
                if(!r[x][y] && !m[x][y]){
                    w = false;
                }
            }
        }
        return w;
    }
}