package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Message;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.character.enemy.ai.AI;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

import java.awt.*;
import java.util.Random;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Wall;

public abstract class Enemy extends Character {

	protected int _points;
	
	protected double _speed;
	protected AI _ai;

	protected final double MAX_STEPS;
	protected final double rest;
	protected double _steps;
	
	protected int _finalAnimation = 30;
	protected Sprite _deadSprite;
	
	public Enemy(int x, int y, Board board, Sprite dead, double speed, int points) {
		super(x, y, board);
		
		_points = points;
		_speed = speed;
		
		MAX_STEPS = Game.TILES_SIZE / _speed;
		rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
		_steps = MAX_STEPS;
		
		_timeAfter = 20;
		_deadSprite = dead;
	}
	
	@Override
	public void update() {
		animate();
		
		if(!_alive) {
			afterKill();
			return;
		}
		
		if(_alive)
			calculateMove();
	}
	
	@Override
	public void render(Screen screen) {
		
		if(_alive)
			chooseSprite();
		else {
			if(_timeAfter > 0) {
				_sprite = _deadSprite;
				_animate = 0;
			} else {
				_sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, _animate, 60);
			}
				
		}
			
		screen.renderEntity((int)_x, (int)_y - _sprite.SIZE, this);
	}
	
	@Override
	public void calculateMove() {
            double x1 = Game.getBomberSpeed();
            
          //  System.out.println(_ai.calculateDirection());
           if(_ai.calculateDirection()==0){
               move(_x,_y-2*x1);
           }
           else if(_ai.calculateDirection()==1){
               move(_x,_y+2*x1);
           }
           else if(_ai.calculateDirection()==2){
               move(_x+2*x1,_y);
           }
           else if(_ai.calculateDirection()==3){
               move(_x-2*x1,_y);
           }

		// TODO: Tính toán hướng đi và di chuyển Enemy theo _ai và cập nhật giá trị cho _direction
		// TODO: sử dụng canMove() để kiểm tra xem có thể di chuyển tới điểm đã tính toán hay không
		// TODO: sử dụng move() để di chuyển
		// TODO: nhớ cập nhật lại giá trị c�? _moving khi thay đổi trạng thái di chuyển
               }
	
	@Override
	public void move(double xa, double ya) {
		if(!_alive) return;
                if(this.canMove(xa, ya)){
                _y = ya;
		_x = xa;
                }
		
	}
	
	@Override
	public boolean canMove(double x, double y) {
            int i= this._sprite.SIZE;
        Entity e = _board.getEntity(x,y, this);
        Entity e1 = _board.getEntity(x+10, y, this);
       Entity e2 = _board.getEntity(x, y-8, this);
        //Entity e3 = _board.getEntity(x+5, y-5, this);
         Entity e4 = _board.getEntity(x+8, y-8, this);
         
      //  Entity e1 = _board.getEntity(x+10, y, this);
        //if(this.collide(e)==true) return false;
        if(e instanceof Wall||e1 instanceof Wall||e4 instanceof Wall||e2 instanceof Wall) return false;
        if((e instanceof LayeredEntity||e1 instanceof LayeredEntity||e4 instanceof LayeredEntity||e2 instanceof LayeredEntity)&&!(e instanceof Grass)) return false;
        return true;
	}

	@Override
	public boolean collide(Entity e) {
            
		// TODO: xử lý va chạm với Flame
		// TODO: xử lý va chạm với Bomber
		return true;
	}
	
	@Override
	public void kill() {
		if(!_alive) return;
		_alive = false;
		
		_board.addPoints(_points);

		Message msg = new Message("+" + _points, getXMessage(), getYMessage(), 2, Color.white, 14);
		_board.addMessage(msg);
	}
	
	
	@Override
	protected void afterKill() {
		if(_timeAfter > 0) --_timeAfter;
		else {
			if(_finalAnimation > 0) --_finalAnimation;
			else
				remove();
		}
	}
	
	protected abstract void chooseSprite();
}
