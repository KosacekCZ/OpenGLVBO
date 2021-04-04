import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL33;

import java.util.Objects;



public class Main {

    public String getKey() {
        return key;
    }

    public static String key =
            "10111\n" +
            "10001\n" +
            "11101\n" +
            "01001\n" +
            "11111";

    static float x = -1.0f;
    static float y = -1.0f;

    public static float[] pos = { // startpos?
            -x + 0.03f,  y, 0.0f,
            -x + 0.03f,  y - 0.03f, 0.0f,
            -x,  y - 0.03f , 0.0f,
            -x,  y, 0.0f,
    };

    public static void main(String[] args) throws Exception {
        key = key.replace("\n", "");
        GLFW.glfwInit();
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        long window = GLFW.glfwCreateWindow(800, 600, "OpenGL", 0, 0);
        if (window == 0) {
            GLFW.glfwTerminate();
            throw new Exception("Can't open window");
        }
        GLFW.glfwMakeContextCurrent(window);


        GL.createCapabilities();
        GL33.glViewport(0, 0, 800, 600);

        GLFW.glfwSetFramebufferSizeCallback(window, (win, w, h) -> GL33.glViewport(0, 0, w, h));
        Shader.initShaders();


        while (!GLFW.glfwWindowShouldClose(window)) {
            GL33.glUseProgram(Shader.shaderProgramId);

            if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS)
                GLFW.glfwSetWindowShouldClose(window, true);

            GL33.glClear(GL33.GL_COLOR_BUFFER_BIT);

            /*GL33.glBindVertexArray(sqr1.getSquareVaoId());
            GL33.glDrawElements(GL33.GL_TRIANGLES, sqr1.getIndexy(), GL33.GL_UNSIGNED_INT, 0);

            GL33.glBindVertexArray(sqr2.getSquareVaoId());
            GL33.glDrawElements(GL33.GL_TRIANGLES, sqr2.getIndexy(), GL33.GL_UNSIGNED_INT, 0);*/


            int a = (int)Math.sqrt(key.length());

            for (int i = 0; i < key.length(); i++) {
                for (int j = 0; j < a; j++) {
                    if (key.charAt(i) == '1') {

                        Square sqr = new Square();
                        sqr.setVrcholy(pos);

                        GL33.glBindVertexArray(sqr.getSquareVaoId());
                        GL33.glDrawElements(GL33.GL_TRIANGLES, sqr.getIndexy(), GL33.GL_UNSIGNED_INT, 0);
                    }

                }
            }

            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }

        GLFW.glfwTerminate();
    }

}
