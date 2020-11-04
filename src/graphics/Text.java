package graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.sun.istack.internal.Nullable;
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

public class Text {

	public static final int SPLASH = 0;
	public static final int SUSPENDED = 1;
	public static final int GAME_OVER = 2;
	public static final int LEVEL_START = 3;

	private GameState gs;

	public Text(GameState gs) {
		this.gs = gs;
	}


	/**
	 * Método responsável por fazer a escrita dos textos nas cenas.
	 *
	 * @param texto que será exibido
	 */
	public void desenhaTextos(int texto) {

		//Variaveis globais do método
		TextRenderer textRenderer = new TextRenderer(new Font(Font.SANS_SERIF, Font.PLAIN, 24));
		List<String> strings;

		strings = leTexto(texto);
		if (texto == GAME_OVER) strings.set(0, strings.get(0).replace("[pontuacao]", String.valueOf(gs.score)));

		Vector centro;
		int lineHeight = (int) textRenderer.getBounds(strings.get(0)).getHeight();
		int boxSize = lineHeight * strings.size();
		int yOffset = Renderer.screenHeight - (Renderer.screenHeight - boxSize) / 2;

		for (String string : strings) {
			centro = retornaMeio(textRenderer, string);
			textRenderer.beginRendering(Renderer.screenWidth, Renderer.screenHeight);
			textRenderer.setColor(Color.white);
			textRenderer.draw(string, Renderer.screenWidth / 2 + (int) centro.x, yOffset + (int) centro.y);
			textRenderer.endRendering();
			yOffset -= lineHeight;
		}
	}

	/**
	 * Calcula meio da tela.
	 *
	 * @param tr    referência ao renderizador de texto
	 * @param texto texto a ser desenhado na tela
	 * @return meio.
	 */
	public Vector retornaMeio(TextRenderer tr, String texto) {
		Rectangle2D bounds = tr.getBounds(texto);
		return new Vector(-bounds.getCenterX(), -bounds.getCenterY());
	}

	/**
	 * Define qual o arquivo deve ser lido.
	 *
	 * @param texto constante indicando qual arquivo deve ler
	 * @return String com os dados do arquivo.
	 */
	public List<String> leTexto(int texto) {
		switch (texto) {
			case SPLASH:
				return leArquivo("arquivo/menu.txt");
			case LEVEL_START:
				return leArquivo("arquivo/novoNivel.txt");
			case SUSPENDED:
				return leArquivo("arquivo/pausa.txt");
			case GAME_OVER:
				return leArquivo("arquivo/creditos.txt");
			default:
				return null;
		}
	}

	/**
	 * Le o arquivo e devolve a representação em String.
	 *
	 * @param caminho onde está o arquivo.
	 * @return o texto do arquivo em String.
	 */
	private List<String> leArquivo(String caminho) {
		File file = new File(caminho);
		try (FileReader fileReader = new FileReader(file);
		     BufferedReader bufferedReader = new BufferedReader(fileReader)) {
			List<String> texto = new ArrayList<>();
			while (bufferedReader.ready()) {
				texto.add(bufferedReader.readLine());
			}
			return texto;
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return null;
	}

}
