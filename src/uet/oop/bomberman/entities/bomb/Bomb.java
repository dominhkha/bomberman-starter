package uet.oop.bomberman.entities.bomb;

import java.util.logging.Level;
import java.util.logging.Logger;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.AnimatedEntitiy;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

public class Bomb extends AnimatedEntitiy {

	protected double _timeToExplode = 120; //2 seconds
	public int _timeAfter = 20;
	
	protected Board _board;
	protected Flame[] _flames = new Flame[Game.getBombRate()] ;
	protected boolean _exploded = false;
	protected boolean _allowedToPassThru = true;
	
	public Bomb(int x, int y, Board board) {
		_x = x;
		_y = y;
		_board = board;
		_sprite = Sprite.bomb;
              
	}
	
	@Override
	public void update() {
		if(_timeToExplode > 0) 
			_timeToExplode--;
		else {
			if(!_exploded) 
				try {
                                    explode();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Bomb.class.getName()).log(Level.SEVERE, null, ex);
                        }
			else
				updateFlames();
			
			if(_timeAfter > 0) 
				_timeAfter--;
			else
				remove();
		}
			
		animate();
	}
	
	@Override
	public void render(Screen screen) {
		if(_exploded) {
			_sprite =  Sprite.bomb_exploded2;
			renderFlames(screen);
		} else
			_sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, _animate, 60);
		
		int xt = (int)_x << 4;
		int yt = (int)_y << 4;
		
		screen.renderEntity(xt, yt , this);
	}
	
	public void renderFlames(Screen screen) {
		for (int i = 0; i < _flames.length; i++) {
			_flames[i].render(screen);
		}
	}
	
	public void updateFlames() {
		for (int i = 0; i < _flames.length; i++) {
                   // System.out.println(_flames.length);
			_flames[i].update();
		}
	}

    /**
     * Xử lý Bomb nổ
     */
	protected void explode() throws InterruptedException {
		_exploded = true;
                _flames = new Flame[4];
                // Game.playSound("BOM_SOUND.wav");
                _flames[0] = new Flame((int)_x,(int)_y,0,Game.getBombRadius(),this._board);
                _flames[1] = new Flame((int)_x,(int)(_y),1,Game.getBombRadius(),this._board);
                _flames[2] = new Flame((int)(_x), (int)(_y),2,Game.getBombRadius(),this._board);
                _flames[3] = new Flame((int)(_x), (int)(_y),3,Game.getBombRadius(),this._board);

		// TODO: tạo các Flame
	}
	
	public FlameSegment flameAt(int x, int y) {
		if(!_exploded) return null;
		
		for (int i = 0; i < _flames.length; i++) {
			if(_flames[i] == null) return null;
			FlameSegment e = _flames[i].flameSegmentAt(x, y);
			if(e != null) return e;
		}
		
		return null;
	}

	@Override
	public boolean collide(Entity e) {
            if(e instanceof Flame){
                try {
                    this.explode();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Bomb.class.getName()).log(Level.SEVERE, null, ex);
                }
                return false;
            }
        // TODO: xử lý khi Bomber đi ra sau khi vừa đặt bom (_allowedToPassThru)
        // TODO: xử lý va chạm với Flame của Bomb khác
        return false;
	}
}
