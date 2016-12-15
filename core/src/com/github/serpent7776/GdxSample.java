package com.github.serpent7776;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class GdxSample extends ApplicationAdapter {

	private SpriteBatch batch;
	private TextureAtlas atlas;
	private ParticleEffect particles;
	private Array<Sprite> sprites;
	private int index;

	private void setNextSprite() {
		final int count = sprites.size - 1;
		index = ++index % count;
		final Sprite sprite = sprites.get(index);
		for (ParticleEmitter emitter : particles.getEmitters()) {
			emitter.setSprite(sprite);
		}
	}

	@Override
	public void create() {
		batch = new SpriteBatch();
		atlas = new TextureAtlas(Gdx.files.internal("stars.atlas"));
		sprites = new Array<Sprite>();
		for (TextureAtlas.AtlasRegion region : atlas.getRegions()) {
			sprites.add(new Sprite(region));
		}
		particles = new ParticleEffect();
		particles.load(Gdx.files.internal("explosion.p"), atlas);
		final float width = Gdx.graphics.getWidth();
		final float height = Gdx.graphics.getHeight();
		particles.setPosition(width / 2, height / 2);
		Gdx.input.setInputProcessor(new InputAdapter() {

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				setNextSprite();
				particles.reset();
				return false;
			}

		});
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		final float delta = Gdx.graphics.getDeltaTime();
		particles.update(delta);
		particles.draw(batch);
		batch.end();
	}

	@Override
	public void dispose() {
		batch.dispose();
		atlas.dispose();
		particles.dispose();
	}

}
