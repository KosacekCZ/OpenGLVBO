import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL33;

import java.util.ArrayList;
import java.util.Objects;


public class Main {

    /*public String getKey() {
        return key;
    }

    /*public static String key =
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
                    "110011111001011111111101111111";*/
    /*public static float[][] pos2 = {
            {-0.19999999f, -1.0f, -0.19999999f + 0.4f, -1.0f + 0.4f},
            {0.6f, -1.0f, 0.6f + 0.4f, -1.0f + 0.4f},
            {-0.6f, -0.19999999f, 0.4f, -0.6f + 0.4f, -0.19999999f + 0.4f},
            {-0.19999999f, -0.19999999f, -0.19999999f + 0.4f, -0.19999999f + 0.4f},
            {-1.0f, 0.20000005f, -1.0f + 0.4f, 0.20000005f + 0.4f, 0.4f},
            {-1.0f, 0.6f, -1.0f + 0.4f, 0.6f + 0.4f},
            {-0.19999999f, 0.6f, -0.19999999f + 0.4f, 0.6f + 0.4f, }
    };*/
    public static String pos2nsfw =
            "-0.19999999;-1.0;0.4\n" +
                    "0.20000005;-1.0;0.4\n" +
                    "0.6;-1.0;0.4\n" +
                    "0.6;-0.6;0.4\n" +
                    "-1.0;-0.19999999;0.4\n" +
                    "-0.6;-0.19999999;0.4\n" +
                    "-0.19999999;-0.19999999;0.4\n" +
                    "0.6;-0.19999999;0.4\n" +
                    "-1.0;0.20000005;0.4\n" +
                    "-1.0;0.6;0.4\n" +
                    "-0.6;0.6;0.4\n" +
                    "-0.19999999;0.6;0.4";


    public static int w = 800;
    public static int h = 800;


    public static void main(String[] args) throws Exception {

        /*int a = (int) Math.sqrt(key.length());*/


        /*key = key.replace("\n", "");*/
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

        // Indices transform and float-ready edit

        String[] pos2nsfwString = pos2nsfw.split("\n");
        String[][] pos3nsfwString = new String[pos2nsfwString.length][3];

        for (int i = 0; i < pos2nsfwString.length; i++) {
            pos3nsfwString[i] = pos2nsfwString[i].split(";");
        }

        for (int i = 0; i < pos3nsfwString.length; i++) {
            for (int j = 0; j < 3; j++) {
                pos3nsfwString[i][j] += 'f';
            }
        }

        // render for each of array indices
        for (int i = 0; i < 12; i++) {
            float[] tempPos = new float[12];
            tempPos[0] = Float.parseFloat(pos3nsfwString[i][0]); // x1
            tempPos[1] = Float.parseFloat(pos3nsfwString[i][1]); // y1
            tempPos[2] = 0.0f; // z1

            tempPos[3] = Float.parseFloat(pos3nsfwString[i][0]) + Float.parseFloat(pos3nsfwString[i][2]); // x2
            tempPos[4] = Float.parseFloat(pos3nsfwString[i][1]); // x2
            tempPos[5] = 0.0f; // z2

            tempPos[6] = Float.parseFloat(pos3nsfwString[i][0]) + Float.parseFloat(pos3nsfwString[i][2]); // x3
            tempPos[7] = Float.parseFloat(pos3nsfwString[i][1]) + Float.parseFloat(pos3nsfwString[i][2]); // y3
            tempPos[8] = 0.0f; // z3

            tempPos[9] = Float.parseFloat(pos3nsfwString[i][0]); // x4
            tempPos[10] = Float.parseFloat(pos3nsfwString[i][1]) + Float.parseFloat(pos3nsfwString[i][2]); // y4
            tempPos[11] = 0.0f; // z4

            // System.out.println("Printed: " + pos3nsfwString[i][0] + ", " + pos3nsfwString[i][1] + ", " + pos3nsfwString[i][2]);

            Square sqr = new Square();
            sqr.setVrcholy(tempPos);
            pozice.add(sqr.getSquareVaoId());
        }

        /*float x = -1.0f;
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
        */


        Player.init(window);

        while (!GLFW.glfwWindowShouldClose(window)) {
            GL33.glUseProgram(Shader.shaderProgramId);

            if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS)
                GLFW.glfwSetWindowShouldClose(window, true);

            GL33.glClearColor(0.1f, 0.15f, 0.15f, 0.1f);
            GL33.glClear(GL33.GL_COLOR_BUFFER_BIT);

            Player.render(window);
            Player.update(window);

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
