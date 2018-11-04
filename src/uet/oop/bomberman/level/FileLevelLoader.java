package uet.oop.bomberman.level;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Balloon;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.exceptions.LoadLevelException;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

public class FileLevelLoader extends LevelLoader {

    /**
     * Ma trận chứa thông tin bản đồ, mỗi phần tử lưu giá
     * trị kí tự đ�?c được từ ma trận bản đồ trong tệp
     * cấu hình
     */
    private static char[][] _map;
    private static int[] x;

    public FileLevelLoader(Board board, int level) throws LoadLevelException {
        super(board, level);
    }

    @Override

    public void loadLevel(int level) {
        // System.getProperty("user.home");
        ClassLoader c = ClassLoader.getSystemClassLoader();
        File file = new File(c.getResource("levels/Level1.txt").getFile());
        FileInputStream input;
        BufferedReader reader = null;
        try {
            input = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(input));

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileLevelLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            String line = reader.readLine();
            String line1 = new String(line);
            x = this.convertToInt(line1);
            char[][] _map1 = new char[x[1]][x[2]];
            _width = x[2];
            _height = x[1];
            _level = x[0];
            for (int i = 0; i < x[1]; i++) {
                line = reader.readLine();
                
                for (int j = 0; j < x[2]; j++) {
                    _map1[i][j] = line.charAt(j);
                   
                }
                
            }
            _map = new char[x[2]][x[1]];
            for(int i=0;i<x[1];i++){
                for(int j=0;j<x[2];j++){
                    _map[j][i] = _map1[i][j];
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(FileLevelLoader.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void createEntities() {
        // TODO: tạo các Entity của màn chơi
        // TODO: sau khi tạo xong, g�?i _board.addEntity() để thêm Entity vào game

        // TODO: phần code mẫu ở dưới để hướng dẫn cách thêm các loại Entity vào game
        // TODO: hãy xóa nó khi hoàn thành chức năng load màn chơi từ tệp cấu hình
        // thêm Wall
        for (int i = 0; i < _width; i++) {
            for (int  j= 0; j < _height; j++) {
                
                if (_map[i][j]== '#') {
                    int pos = i +j * _width;
                    Sprite sprite = Sprite.wall;
                    _board.addEntity(pos, new Wall(i, j, sprite));
                } else if (_map[i][j] == '*') {
                   // int xB = 3, yB = 1;
                    _board.addEntity(i + j * _width,
                            new LayeredEntity(i, j,
                                    new Grass(i, j, Sprite.grass),
                                    new Brick(i, j, Sprite.brick)
                            )
                    );
                }
                else if(_map[i][j]=='p'){
                    int xBomber = i, yBomber = j;
                    _board.addCharacter(new Bomber(Coordinates.tileToPixel(xBomber), Coordinates.tileToPixel(yBomber) + Game.TILES_SIZE, _board));
                   // _board.addCharacter(new Bomber(i, j + Game.TILES_SIZE, _board));

                        Screen.setOffset(0, 0);
                      
                        _board.addEntity(xBomber + yBomber * _width, new Grass(i, j, Sprite.grass));
                }
                else if (_map[i][j]=='1'){
                int xE = i, yE = j;
                    _board.addCharacter(new Balloon(Coordinates.tileToPixel(i), Coordinates.tileToPixel(j) + Game.TILES_SIZE, _board));
                     _board.addEntity(xE + yE * _width, new Grass(i, j, Sprite.grass));

            }
                else {
                    int xE = i, yE = j;
                   // _board.addCharacter(new Balloon(Coordinates.tileToPixel(i), Coordinates.tileToPixel(j) + Game.TILES_SIZE, _board));
                     _board.addEntity(xE + yE * _width, new Grass(i, j, Sprite.grass));

                }
            }
        }
        
        /*
       for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 20; y++) {
                int pos = x + y * _width;
                Sprite sprite = y == 0 || x == 0 || x == 10 || y == 10 ? Sprite.wall : Sprite.grass;
                _board.addEntity(pos, new Grass(x, y, sprite));
            }
        }
         */
        // thêm Bomber
        /*
        int xBomber = 1, yBomber = 1;
        _board.addCharacter(new Bomber(Coordinates.tileToPixel(xBomber), Coordinates.tileToPixel(yBomber) + Game.TILES_SIZE, _board));
        Screen.setOffset(0, 0);
        _board.addEntity(xBomber + yBomber * _width, new Grass(xBomber, yBomber, Sprite.grass));

        // thêm Enemy
        int xE = 2, yE = 1;
        _board.addCharacter(new Balloon(Coordinates.tileToPixel(xE), Coordinates.tileToPixel(yE) + Game.TILES_SIZE, _board));
        _board.addEntity(xE + yE * _width, new Grass(xE, yE, Sprite.grass));

        // thêm Brick
        int xB = 3, yB = 1;
        _board.addEntity(xB + yB * _width,
                new LayeredEntity(xB, yB,
                        new Grass(xB, yB, Sprite.grass),
                        new Brick(xB, yB, Sprite.brick)
                )
        );

        // thêm Item kèm Brick che phủ ở trên
        int xI = 1, yI = 2;
        _board.addEntity(xI + yI * _width,
                new LayeredEntity(xI, yI,
                        new Grass(xI, yI, Sprite.grass),
                        new SpeedItem(xI, yI, Sprite.powerup_flames),
                        new Brick(xI, yI, Sprite.brick)
                )
        );
         */
    }

    private int[] convertToInt(String line) {
        int[] x1 = new int[3];
        int a = 0, i = 0;
        int k = 0;

        while (i < line.length()) {
            if ((int) line.charAt(i) >= 48 && (int) line.charAt(i) <= 48 + 9) {
                a = a * 10 + (int) line.charAt(i) - 48;
            }
            if ((int) line.charAt(i) == 32 || i == line.length() - 1) {
                x1[k] = a;
                k++;
                a = 0;
            }
            i++;
        }

        return x1;
    }
}
