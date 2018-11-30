package uet.oop.bomberman.entities.character.enemy.ai;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.level.Coordinates;

public class AIMedium extends AI {

    Bomber _bomber;
    Enemy _e;
    Board _board;
    int t = 1;

    public AIMedium(Bomber bomber, Enemy e, Board _board) {
        _bomber = bomber;
        _e = e;
        this._board = _board;
    }

    @Override
    public int calculateDirection() {
        int c = 0;
        double x1 = 1.0;
        double _y0 = _e.getY();
        double _x0 = _e.getX();
        double i = _bomber.getX();
        double j = _bomber.getY();
        Entity e = null;
        if (t == 1) {
            if (i == _x0 && j == _y0) {
                ((Bomber) _bomber).kill();
                return 0;
            } else if (i == _x0 && j > _y0) {
                c = 1;
                e = _board.getEntityAt(Coordinates.pixelToTile(_x0), Coordinates.pixelToTile(_y0 + x1));
                if(_board.getBombs()==null){
                    t=1;
                }
                if (e instanceof Bomb) {
                    if (e.getX() == _x0) {
                        c = 0;
                        t=0;
                    }
                }

            } else if (i == _x0 && j < _y0) {

                c = 0;

            } else if (j == _y0 && i < _x0) {
                c = 3;
                e = _board.getEntityAt(Coordinates.pixelToTile(_x0-x1), Coordinates.pixelToTile(_y0));
                if(_board.getBombs()==null){
                    t=1;
                }
                if(e instanceof Bomb){
                    c=2;
                    t=0;
                }

            } else if (j == _y0 && i > _x0) {
                if (_e.canMove(_x0 + x1, _y0)) {
                    return c;
                }
                c = 2;

            } else if (i <= _x0 && j <= _y0) {

                if (_e.canMove(_x0, _y0 - x1)) {
                    c = 0;
                } else if (_e.canMove(_x0 + x1, _y0)) {
                    c = 2;
                } else {

                }
            } else if (i >= _x0 && j >= _y0) {

                if (_e.canMove(_x0 + x1, _y0)) {
                    c = 2;
                } else if (_e.canMove(_x0, _y0 + x1)) {
                    c = 1;
                } else {

                }
            } else if (i >= _x0 && j <= _y0) {

                if (_e.canMove(_x0, _y0 - x1)) {
                    c = 0;

                } else if (_e.canMove(_x0 + x1, _y0)) {
                    c = 2;
                } else {

                }
            } else if (i <= _x0 && j >= _y0) {

                if (_e.canMove(_x0 - x1, _y0)) {
                    c = 3;
                } else if (_e.canMove(_x0, _y0 + x1)) {
                    c = 1;
                } else {

                }

            }
        }
        return c;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

}
