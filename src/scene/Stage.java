package scene;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import core.GameState;
import utils.MovementBuffer;
import entities.EntityHandler;

public class Stage implements GLEventListener {
	private float xMin, xMax, yMin, yMax, zMin, zMax;
	GLU glu;
	EntityHandler scenePopulation;
	MovementBuffer movBuff;
	GameState gs;

	public void init(GLAutoDrawable drawable) {
		//dados iniciais da cena
		glu = new GLU();
		GL2 gl = drawable.getGL().getGL2();
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

		gs = new GameState();
		movBuff = new MovementBuffer();
		scenePopulation = new EntityHandler(gl, gs);

		//Estabelece as coordenadas do SRU (Sistema de Referencia do Universo)
		xMin = yMin = zMin = -1;
		xMax = yMax = zMax = 1;
	}

	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		gl.glLoadIdentity();

		scenePopulation.updateScene(movBuff);
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

		GL2 gl = drawable.getGL().getGL2();

		height = height == 0 ? 1 : height;

		float aspect = (float) width / height;

		gl.glViewport(0, 0, width, height);

		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();

		//Projeção ortogonal
		//true:   aspect >= 1 configura a altura de -1 para 1 : com largura maior
		//false:  aspect < 1 configura a largura de -1 para 1 : com altura maior
		if (width >= height)
			gl.glOrtho(xMin * aspect, xMax * aspect, yMin, yMax, zMin, zMax);
		else
			gl.glOrtho(xMin, xMax, yMin / aspect, yMax / aspect, zMin, zMax);

		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	public void dispose(GLAutoDrawable drawable) {
	}
}
