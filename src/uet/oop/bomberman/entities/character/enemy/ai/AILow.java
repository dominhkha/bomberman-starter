package uet.oop.bomberman.entities.character.enemy.ai;

import java.util.Random;

public class AILow extends AI {

	@Override
	public int calculateDirection() {
            return random.nextInt(4);
	}

}
