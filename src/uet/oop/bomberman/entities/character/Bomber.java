package uet.oop.bomberman.entities.character;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.Destroyable;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.bomb.FlameSegment;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Tile;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.destroyable.DestroyableTile;
import static uet.oop.bomberman.graphics.Sprite.brick;
import uet.oop.bomberman.level.Coordinates;
import uet.oop.bomberman.level.FileLevelLoader;
import uet.oop.bomberman.level.LevelLoader;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.tile.item.FlameItem;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.gui.Sound;

public class Bomber extends Character {

    private List<Bomb> _bombs;
    protected Keyboard _input;
    public static int t = 1;
    /**
     * nếu giá trị này < 0 thì cho phép đặt đối tượng Bomb
     * tiếp theo, cứ mỗi lần đặt 1 Bomb mới, giá trị này sẽ
     * được reset v�? 0 và giảm dần trong mỗi lần update()
     */
    protected int _timeBetweenPutBombs = 0;
    int c = 0;

    public Bomber(int x, int y, Board board) {
        super(x, y, board);
        _bombs = _board.getBombs();
        _input = _board.getInput();
        _sprite = Sprite.player_right;
    }

    @Override
    public void update() {
        clearBombs();
        if (!_alive) {
            afterKill();
            return;
        }

        if (_timeBetweenPutBombs < -7500) {
            _timeBetweenPutBombs = 0;
        } else {
            _timeBetweenPutBombs--;
        }

        animate();

        calculateMove();

        try {
            detectPlaceBomb();
        } catch (InterruptedException ex) {
            Logger.getLogger(Bomber.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void render(Screen screen) {
        calculateXOffset();

        if (_alive) {
            chooseSprite();
        } else {
            _sprite = Sprite.player_dead1;
        }

        screen.renderEntity((int) _x, (int) _y - _sprite.SIZE, this);
    }

    public void calculateXOffset() {
        int xScroll = Screen.calculateXOffset(_board, this);
        Screen.setOffset(xScroll, 0);
    }

    /**
     * Kiểm tra xem có đặt được bom hay không? nếu có thì đặt
     * bom tại vị trí hiện tại của Bomber
     */
    private void detectPlaceBomb() throws InterruptedException {
        // TODO: kiểm tra xem phím đi�?u khiển đặt bom có được gõ và giá trị _timeBetweenPutBombs, Game.getBombRate() có th�?a mãn hay không
        // TODO:  Game.getBombRate() sẽ trả v�? số lượng bom có thể đặt liên tiếp tại th�?i điểm hiện tại
        // TODO: _timeBetweenPutBombs dùng để ngăn chặn Bomber đặt 2 Bomb cùng tại 1 vị trí trong 1 khoảng th�?i gian quá ngắn
        // TODO: nếu 3 đi�?u kiện trên th�?a mãn thì thực hiện đặt bom bằng placeBomb()
        // TODO: sau khi đặt, nhớ giảm số lượng Bomb Rate và reset _timeBetweenPutBombs v�? 0
        if (_input.space == true && Game.getBombRate() >= 0 && this._timeBetweenPutBombs < 0) {
            placeBomb(_x, _y);
            c = 1;
            Game.addBombRate(-1);
            this._timeBetweenPutBombs = 30;
            this.update();
        }
    }

    protected void placeBomb(double x, double y) throws InterruptedException {

        //int xBomber =x;
        // int yBomber = y;
        if (_board.getEntityAt(Coordinates.pixelToTile(x), Coordinates.pixelToTile(y - 8)) instanceof Bomb) {
            return;
        }

        Bomb e = new Bomb(Coordinates.pixelToTile(x), Coordinates.pixelToTile(y - 8), _board);
        //   _board.addEntity(Coordinates.pixelToTile(x) + Coordinates.pixelToTile(y)*31 , );
        // _board.addBomb(new Bomb(x,y,this._board));
        //  System.out.println("ddd");
        c = 1;
        _board.addBomb(e);
        Sound.PlaceBombSound();

        //Game.playSound("BOM_BOUND.wav");
        // TODO: thực hiện tạo đối tượng bom, đặt vào vị trí (x, y)
    }

    private void clearBombs() {
        Iterator<Bomb> bs = _bombs.iterator();

        Bomb b;
        while (bs.hasNext()) {
            b = bs.next();
            if (b.isRemoved()) {
                bs.remove();
                Game.addBombRate(1);
            }
        }

    }

    @Override
    public void kill() {
        if (!_alive) {
            return;
        }
        _alive = false;
        Sound.PlayerDieSound();
    }

    @Override
    protected void afterKill() {
        if (_timeAfter > 0) {
            --_timeAfter;
        } else {
            _board.endGame();
            Sound.FinishGameSound();
        }
    }

    @Override
    protected void calculateMove() {
        double x1 = Game.getBomberSpeed();
        collide(_board.getEntity(_x, _y, this));
        int x12 = 0;
        if (_input.down) {
            // _y = _y - x1;

            move(_x, _y + x1);
            this._direction = 2;
        } else if (_input.up) {
            // _y = _y  + x1;

            move(_x, _y - x1);
            this._direction = 0;
        } else if (_input.left) {
            // _x = _x -x1;
            move(_x - x1, _y);
            this._direction = 3;
        } else if (_input.right) {
            //  _x = _x +x1;
            move(_x + x1, _y);
            this._direction = 1;
        } else {
            _moving = false;
        }

        // TODO: xử lý nhận tín hiệu đi�?u khiển hướng đi từ _input và g�?i move() để thực hiện di chuyển
        // TODO: nhớ cập nhật lại giá trị c�? _moving khi thay đổi trạng thái di chuyển
    }

    @Override
    public boolean canMove(double x, double y) {
        Entity e = _board.getEntity(x, y, this);
        if (!this.collide(e)) {
            return false;
        }
        Entity a = _board.getEntity(Coordinates.pixelToTile(x), Coordinates.pixelToTile(y - 8), this);
        if (a instanceof Bomb) {
            if (this._timeBetweenPutBombs < -20) {
                return false;
            }

        }
        Entity e1 = null;
        Entity e2 = null;
        Entity e3 = null;

        if (e instanceof LayeredEntity) {
            if (((LayeredEntity) e).getTopEntity() instanceof FlameItem) {
                // _board.update();
                ((LayeredEntity) e).getTopEntity().collide(this);

            }
        }
        if (e instanceof LayeredEntity) {
            if (((LayeredEntity) e).getTopEntity() instanceof SpeedItem) {
                ((LayeredEntity) e).getTopEntity().collide(this);
            }
        }
        if (e instanceof LayeredEntity) {
            if (((LayeredEntity) e).getTopEntity() instanceof BombItem) {
                ((LayeredEntity) e).getTopEntity().collide(this);
            }
        }
        if (_input.down) {
            for (int i = 0; i < 10; i++) {
                e = _board.getEntity(x + i, y, this);
                if (!this.collide(e)) {
                    return false;
                }
                if (e instanceof Wall || e instanceof Bomb) {
                    return false;
                }
                if (e instanceof LayeredEntity) {
                    if (((LayeredEntity) e).getTopEntity() instanceof Brick) {
                        return false;
                    }
                }
            }

        }
        if (_input.up || _input.left || _input.right) {
            for (int i = 1; i < 11; i++) {
                e2 = _board.getEntity(x, y - i, this);
                //  System.out.println(e2);
                e3 = _board.getEntity(x + i, y - i, this);
                if (!this.collide(e2) || !this.collide(e3)) {
                    return false;
                }
                if (e2 instanceof Wall || (e2 instanceof Bomb)) {
                    return false;
                }
                if (e2 instanceof LayeredEntity) {
                    if (((LayeredEntity) e2).getTopEntity() instanceof Brick) {
                        return false;
                    }
                }
                if (e3 instanceof Wall || (e3 instanceof Bomb)) {
                    return false;
                }
                if (e3 instanceof LayeredEntity) {
                    if (((LayeredEntity) e3).getTopEntity() instanceof Brick) {
                        return false;
                    }

                }
            }

        }

        return this.collide(e);

        //Entity e1= _board.getEntity(x, y, this);
        //if( e instanceof Wall||e1 instanceof Wall||e2 instanceof Wall||e3 instanceof Wall) return false;
        // if(this.collide(e)) return true;
        //  return true;
    }

    @Override
    public void move(double xa, double ya) {

        if (canMove(xa, ya)) {
            _x = xa;
            _y = ya;

            // this.collide(_board.getEntity(xa, ya, this));
            _moving = true;

        } else {
            _moving = false;
        }

        // TODO: sử dụng canMove() để kiểm tra xem có thể di chuyển tới điểm đã tính toán hay không và thực hiện thay đổi t�?a độ _x, _y
        // TODO: nhớ cập nhật giá trị _direction sau khi di chuyển
    }

    @Override
    public boolean collide(Entity e) {
        // TODO: xử lý va chạm với Flame
        // TODO: xử lý va chạm với Enemy
        // System.out.println(e);
        
        double x = _x;
        double y = _y;
        Entity e1 = null;
        Entity e2 = null;
        Entity e3 = null;
        if (e instanceof Flame || e instanceof Enemy || e instanceof FlameSegment) {
            this.kill();
            return false;
        }
        if (e instanceof LayeredEntity) {
            if (((LayeredEntity) e).getTopEntity() instanceof Portal) {
                if (_board.detectNoEnemies()) {
                    if (t < Game.num_level) {
                        t++;
                        if (Game.getBombRadius() > 1) {
                            Game.addBombRadius(1 - Game.getBombRadius());
                        }
                        _board.nextLevel();
                    } // _board.nextLevel();
                    else {
                        _board.endGame();
                        Sound.FinishGameSound();
                    }

                    return false;
                }

                //   _board.nextLevel();
            }
            if (((LayeredEntity) e).getTopEntity() instanceof Grass) {
                if (e.getSprite() == (Sprite.oneal_dead)) {
                    this.kill();
                    Sound.PlayerDieSound();
                    return false;
                }
            }

        }

        for (int i = 1; i < 11; i++) {
            e = _board.getEntity(x + i - 1, y, this);
            e2 = _board.getEntity(x, y - i, this);
            e3 = _board.getEntity(x + i, y - i, this);
            if (e instanceof Flame || e instanceof Enemy || e instanceof FlameSegment) {
                this.kill();
            }

            if (e2 instanceof Flame || e2 instanceof Enemy || e2 instanceof FlameSegment) {
                this.kill();
            }

            if (e3 instanceof Flame || e3 instanceof Enemy || e3 instanceof FlameSegment) {
                this.kill();
            }

            //if(!this.collide(e)||!this.collide(e1)||!this.collide(e3)) return false;
        }

        return true;
    }

    private void chooseSprite() {
        switch (_direction) {
            case 0:
                _sprite = Sprite.player_up;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, _animate, 20);
                }
                break;
            case 1:
                _sprite = Sprite.player_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
            case 2:
                _sprite = Sprite.player_down;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, _animate, 20);
                }
                break;
            case 3:
                _sprite = Sprite.player_left;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, _animate, 20);
                }
                break;
            default:
                _sprite = Sprite.player_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
        }
    }
}
