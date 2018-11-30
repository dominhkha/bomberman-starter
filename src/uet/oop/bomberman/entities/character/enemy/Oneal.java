package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.ai.AIMedium;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

public class Oneal extends Enemy {

    public Oneal(int x, int y, Board board) {
        super(x, y, board, Sprite.oneal_dead, Game.getBomberSpeed(), 200);

        _sprite = Sprite.oneal_left1;
        _ai = new AIMedium(_board.getBomber(),this,_board);
        _direction = _ai.calculateDirection();
    }

    @Override
    protected void chooseSprite() {
        switch (_direction) {
            case 0:
            case 1:
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, _animate, 60);
                } else {
                    _sprite = Sprite.oneal_left1;
                }
                break;
            case 2:
            case 3:
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, _animate, 60);
                } else {
                    _sprite = Sprite.oneal_left1;
                }
                break;
        }
    }
   
/*
    public void calculateMove() {
        int t1 = 1;
        double x1 = 1.0;
        // t=1;
        
        for (double i = 0; i < Game.WIDTH; i++) {
            for (double j = 0; j < Game.HEIGHT; j++) {
                Entity e = _board.getEntity(i, j, this);
                if (e instanceof Bomber) {
                    double _x0 = _x;
                    double _y0 = _y;
                    t = 1;
                    if (i == _x && j == _y) {
                        ((Bomber) e).kill();
                        return;
                    }
                    if (i == _x0 && j >= _y0) {
                        t = 0;
                        c = 1;
                        super.calculateMove();
                        return;
                    } else if (i == _x0 && j < _y0) {
                        t = 0;
                        c=0;
                        super.calculateMove();
                        return;
                    } else if (j == _y0 && i <= _x0) {
                        t = 0;
                        c = 2;
                        super.calculateMove();
                                
                        return;
                    } else if (j == _y0 && i >= _x0) {
                        t = 0;
                        c = 3;
                        super.calculateMove();
                        return;
                    } else {
                        t = 1;
                        super.calculateMove();
                    }
                    /*
                   else if(i>=_x0&&j>=_y0){
                       t=0;
                       if(canMove(_x+x1,_y)){
                           c=2;
                       }
                       else if(canMove(_x,_y+x1)){
                           c=1;
                       }
                       else t=1;
                   }
                   else if(i>=_x0&&j<=_y0){
                       t=0;
                       if(canMove(_x,_y-x1)){
                           c=0;
                          
                       }
                       else if(canMove(_x+x1,_y)){
                           c=2;
                       }
                       else t=1;
                   }
                   else if(i<=_x0&&j<=_y0){
                       t=0;
                       if(canMove(_x,_y-x1)){
                           c=0;
                       }
                       else if(canMove(_x-x1,_y)){
                           c=3;
                       }
                       else t=1;
                   }
                   else if(i<=_x0&&j>=_y0){
                       t=0;
                       if(canMove(_x-x1,_y)){
                           c=3;
                       }
                       else if(canMove(_x,_y+x1)){
                           c=1;
                       }
                       else t=1;
                       
                   }
                     
                    
                    return;
                }
            }
        }

    }
    */


}
