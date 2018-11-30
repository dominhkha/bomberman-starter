package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.gui.Sound;
import uet.oop.bomberman.input.Keyboard;

public class FlameItem extends Item {
    // Keyboard input1;

    public FlameItem(int x, int y, Sprite sprite) {
        super(x, y, sprite);
        //  input1 = new Keyboard();
    }

    @Override
    public boolean collide(Entity e) {
        // TODO: xử lý Bomber ăn Item
        if (e instanceof Bomber) {
            remove();
            Game.addBombRadius(1);
            Sound.CollectSound();

            return true;
        }
        return false;
    }

}
