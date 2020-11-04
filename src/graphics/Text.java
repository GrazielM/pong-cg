package graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.awt.TextRenderer;
import scene.Renderer;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Text {

    private TextRenderer textoRenderer;

    /**
     * Inicio Código
     */
    private void textosMenu(GL2 gl) {

        desenhaTextos("menu", textoRenderer);
//        desenhaTextos("creditos", textoRenderer);
    }

    /**
     * Método responsável por fazer a escrita dos textos nas cenas.
     *
     * @param cena         que será exibido os textos.
     * @param textRenderer que será usado para desenhar na cena.
     */
    public void desenhaTextos(String cena, TextRenderer textRenderer) {
        //Variaveis globais do método
        int posX = retornaMeio((Renderer.screenWidth), cena) - textRenderer.getFont().toString().length();
        int sizeFont = textRenderer.getFont().getSize();
        int espacamento = 10;
        List<String> strings = null;
        int posY = 0;

        if (cena.equals("menu")) {
            strings = leTexto("menu");
            posY = Renderer.screenHeight - (sizeFont + espacamento);
        } else if (cena.equals("creditos")) {
            strings = leTexto("creditos");
            posY = retornaMeio(Renderer.screenHeight, cena);
        }

        for (String string : strings) {
            textRenderer.beginRendering(Renderer.screenWidth, Renderer.screenHeight);
            textRenderer.setColor(Color.white);
            textRenderer.draw(string, posX, posY);
            textRenderer.endRendering();
            posY -= sizeFont + espacamento;
        }
    }

    /**
     * Calcula meio da tela.
     *
     * @param tamanho a ser divido por 2.
     * @return meio.
     */
    public int retornaMeio(int tamanho, String cena) {
        if (cena.equals("menu")) {
            return tamanho / 3;
        } else {
            return tamanho / 2;
        }
    }

    /**
     * Define qual o arquivo deve ser lido.
     *
     * @param arquivo que deverá ser lido.
     * @return String com os dados do arquivo.
     */
    public List<String> leTexto(String arquivo) {
        if (arquivo.equals("menu")) {
            return leArquivo("arquivo/textos-menu.txt");
        } else if (arquivo.equals("creditos")) {
            return leArquivo("arquivo/texto-creditos.txt");
        }
        return null;
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
