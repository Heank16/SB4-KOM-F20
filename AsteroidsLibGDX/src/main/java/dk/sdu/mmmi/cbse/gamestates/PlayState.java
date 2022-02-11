package dk.sdu.mmmi.cbse.gamestates;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.cbse.entities.Bullet;
import dk.sdu.mmmi.cbse.entities.FlyingSaucer;
import dk.sdu.mmmi.cbse.entities.Player;
import dk.sdu.mmmi.cbse.managers.GameKeys;
import dk.sdu.mmmi.cbse.managers.GameStateManager;

import java.util.ArrayList;

public class PlayState extends GameState {
	
	private ShapeRenderer sr;
	
	private Player player;
	private ArrayList<Bullet> bullets;
	private ArrayList<Bullet> enemyBullets;

	private FlyingSaucer flyingSaucer;
	private float fsTimer;
	private float fsTime;

	public PlayState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		
		sr = new ShapeRenderer();

		bullets = new ArrayList<Bullet>();

		player = new Player(bullets);

		fsTimer = 0;
		fsTime = 15;
		enemyBullets = new ArrayList<Bullet>();
	}
	
	public void update(float dt) {

		// Get user input
		handleInput();

		// Update player
		player.update(dt);

		// update player bullets
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).update(dt);
			if (bullets.get(i).shouldRemove()) {
				bullets.remove(i);
				i--;
			}
		}

		// update flying saucer
		if (flyingSaucer == null) {
			fsTimer += dt;
			if (fsTimer >= fsTime) {
				fsTimer = 0;
				int type = MathUtils.random() < 0.5 ? FlyingSaucer.SMALL : FlyingSaucer.LARGE;
				int direction = MathUtils.random() < 0.5 ? FlyingSaucer.RIGHT : FlyingSaucer.LEFT;
				flyingSaucer = new FlyingSaucer(type, direction, player, enemyBullets);
			}
		}
		// if there is as flying saucer already
		else {
			flyingSaucer.update(dt);
			if (flyingSaucer.shouldRemove()) {
				flyingSaucer = null;
			}
		}

		// update fs bullets
		for (int i = 0; i < enemyBullets.size(); i++) {
			enemyBullets.get(i).update(dt);
			if (enemyBullets.get(i).shouldRemove()) {
				enemyBullets.remove(i);
				i--;
			}
		}
	}
	
	public void draw() {
		// draw player
		player.draw(sr);

		// draw bullets
		for (Bullet bullet : bullets) {
			bullet.draw(sr);
		}

		// draw flying saucer
		if (flyingSaucer != null) {
			flyingSaucer.draw(sr);
		}

		// draw fs bullets
		for (Bullet enemyBullet : enemyBullets) {
			enemyBullet.draw(sr);
		}
	}
	
	public void handleInput() {
		player.setLeft(GameKeys.isDown(GameKeys.LEFT));
		player.setRight(GameKeys.isDown(GameKeys.RIGHT));
		player.setUp(GameKeys.isDown(GameKeys.UP));
		if (GameKeys.isPressed(GameKeys.SPACE)) {
			player.shoot();
		}
	}
	
	public void dispose() {}
	
}









