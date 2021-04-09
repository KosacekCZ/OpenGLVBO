import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL33;

import java.util.ArrayList;
import java.util.Objects;


public class Main {

    public String getKey() {
        return key;
    }

    public static String key =
            "101111111000111011101010000001\n" +
                    "100000101101101100111011111111\n" +
                    "110011100111000110100010000100\n" +
                    "010110000000010010101110110111\n" +
                    "011100111101110110111010100001\n" +
                    "100011100111001100100000111011\n" +
                    "100000100010011000101111101010\n" +
                    "110011101110010111111000000010\n" +
                    "010110100001110000001010111010\n" +
                    "111100101111001111011010101011\n" +
                    "001000001000001010010110101001\n" +
                    "111011101011101011110100101101\n" +
                    "101010101010111000001110100111\n" +
                    "100010111010000111001010100001\n" +
                    "111110000011001101011010101101\n" +
                    "100100111101011001110001101001\n" +
                    "101100100101000011011011001001\n" +
                    "011001100101111010010010001011\n" +
                    "110111001110001110110110011010\n" +
                    "100100111011100010101100110010\n" +
                    "100100000000111010101011101011\n" +
                    "101111011110001011001010001001\n" +
                    "100001010011001001101011001101\n" +
                    "110011011101101100101001111001\n" +
                    "011010000100100110101011001001\n" +
                    "001011001101100010001000011001\n" +
                    "111001111001011010111011110011\n" +
                    "010000100001010011100010001110\n" +
                    "011110001111010000000111001000\n" +
                    "110011111001011111111101111111";


    public static int w = 1000;
    public static int h = 1000;

    public static void main(String[] args) throws Exception {

        int a = (int) Math.sqrt(key.length());


        key = key.replace("\n", "");
        GLFW.glfwInit();
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        long window = GLFW.glfwCreateWindow(w, h, "OpenGL", 0, 0);
        if (window == 0) {
            GLFW.glfwTerminate();
            throw new Exception("Can't open window");
        }
        GLFW.glfwMakeContextCurrent(window);


        GL.createCapabilities();
        GL33.glViewport(0, 0, w, h);

        GLFW.glfwSetFramebufferSizeCallback(window, (win, w, h) -> GL33.glViewport(0, 0, w, h));
        Shader.initShaders();

        ArrayList<Integer> pozice = new ArrayList<Integer>();

        float x = -1.0f;
        float y = 1.0f;
        int at = 0;

        for (int i = 0; i < a; i++) {
            for (int j = 0; j < a; j++) {
                if (key.charAt(at) == '1') {

                    float[] pos = {
                            x + 2.0f / (float) a, y, 0.0f,
                            x + 2.0f / (float) a, y - 2.0f / (float) a, 0.0f,
                            x, y - 2.0f / (float) a, 0.0f,
                            x, y, 0.0f,
                    };

                    Square sqr = new Square();
                    sqr.setVrcholy(pos);

                    pozice.add(sqr.getSquareVaoId());

                    x += 2.0f / (float) a;
                } else {
                    x += 2.0f / (float) a;
                }
                at++;
            }
            x = -1.0f;
            y -= 2.0f / (float) a;
        }


        while (!GLFW.glfwWindowShouldClose(window)) {
            GL33.glUseProgram(Shader.shaderProgramId);

            if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS)
                GLFW.glfwSetWindowShouldClose(window, true);

            GL33.glClearColor(0.1f, 0.15f, 0.15f, 0.1f);
            GL33.glClear(GL33.GL_COLOR_BUFFER_BIT);

            for (Integer integer : pozice) {
                GL33.glBindVertexArray(integer);
                GL33.glDrawElements(GL33.GL_TRIANGLES, 6, GL33.GL_UNSIGNED_INT, 0);
            }


            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }

        GLFW.glfwTerminate();
    }

}
