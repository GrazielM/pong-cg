package graphics;

import com.jogamp.opengl.util.awt.TextRenderer;
import core.GameState;
import scene.Renderer;
import utils.Vector;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class GUI {

	private GameState gs;
	private int padding = 10;

	public GUI(GameState gs) {
		this.gs = gs;
	}

	public void draw() {

		//Variaveis globais do método
		TextRenderer textRenderer = new TextRenderer(new Font(Font.SANS_SERIF, Font.PLAIN, 26));
		String[] gui = new String[3];
		gui[0] = "Nivel " + gs.nivel;
		gui[1] = gs.vidas + " vidas restantes";
		gui[2] = "Pontuação: " + gs.score;

		int lineHeight = (int) textRenderer.getBounds(gui[0]).getHeight();
		int boxSize = lineHeight * gui.length;
		int yOffset = Renderer.screenHeight - boxSize;

		for (String string : gui) {
			textRenderer.beginRendering(Renderer.screenWidth, Renderer.screenHeight);
			textRenderer.setColor(Color.white);
			textRenderer.draw(string, padding, yOffset + padding);
			textRenderer.endRendering();
			yOffset -= lineHeight;
		}
	}
}
