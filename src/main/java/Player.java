import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Player extends Square{
    private static int playerVaoId;
    private static int playerVboId;
    private static int playerEboId;
    private static int colorId;
    private static int vertexColorLocation;

    private static final int[] indexy =
            {
                    0, 1, 3,
                    1, 2, 3
            };

    private static float[] playerPos = {
            -1.0f, -1.0f, 0.0f,
            -0.8f, -1.0f, 0.0f,
            -0.8f, -0.8f, 0.0f,
            -1.0f, -0.8f, 0.0f,
    };

    private static final float[] color = {
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f,
    };

    private static int uniformMatrixLocation;

    public static Matrix4f matrix = new Matrix4f() // matrix creation
            .identity()
            .translate(0.25f, 0.25f, 0.25f);

    private static FloatBuffer matrixFloatBuffer = BufferUtils.createFloatBuffer(16);






    public static void init(long window) {
        playerVaoId = GL33.glGenVertexArrays();
        playerVboId = GL33.glGenBuffers();
        playerEboId = GL33.glGenBuffers();
        colorId = GL33.glGenBuffers();


        // Get uniform location
        uniformMatrixLocation = GL33.glGetUniformLocation(Shader.shaderProgramId, "player");

        GL33.glBindVertexArray(playerVaoId);

        // Tell OpenGL we are currently writing to this buffer (eboId)
        GL33.glBindBuffer(GL33.GL_ELEMENT_ARRAY_BUFFER, playerEboId);
        IntBuffer ib = BufferUtils.createIntBuffer(indexy.length)
                .put(indexy)
                .flip();
        GL33.glBufferData(GL33.GL_ELEMENT_ARRAY_BUFFER, ib, GL33.GL_STATIC_DRAW);

        // Change to VBOs...


        // Tell OpenGL we are currently writing to this buffer (vboId)
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, playerVboId);

        FloatBuffer fb = BufferUtils.createFloatBuffer(playerPos.length)
                .put(playerPos)
                .flip();

        // Send the buffer (positions) to the GPU
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, fb, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(0, 3, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(0);

        // Change to Color...
        // Tell OpenGL we are currently writing to this buffer (colorsId)
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, colorId);

        FloatBuffer cb = BufferUtils.createFloatBuffer(color.length)
                .put(color)
                .flip();

        // Send the buffer (positions) to the GPU
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, cb, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(1, 3, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(1);

        vertexColorLocation = GL33.glGetUniformLocation(Shader.shaderProgramId, "color");
        GL33.glUseProgram(Shader.shaderProgramId);
        GL33.glUniform4f(vertexColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);



        matrix.get(matrixFloatBuffer);
        GL33.glUniformMatrix4fv(uniformMatrixLocation, false, matrixFloatBuffer);

        // Clear the buffer from the memory (it's saved now on the GPU, no need for it here)
        MemoryUtil.memFree(fb);
        MemoryUtil.memFree(cb);

        matrix.get(matrixFloatBuffer);
        GL33.glUniformMatrix4fv(uniformMatrixLocation, false, matrixFloatBuffer);


    }

    public static void render(long window) {
        GL33.glBindVertexArray(Player.playerVaoId);
        GL33.glDrawElements(GL33.GL_TRIANGLES, 6, GL33.GL_UNSIGNED_INT, 0);

        matrix.get(matrixFloatBuffer);
        GL33.glUniformMatrix4fv(uniformMatrixLocation, false, matrixFloatBuffer);


    }

    public static void update(long window) {
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS) {
            matrix = matrix.translate(0.0001f, 0f, 0f);
        }

        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS) {
            matrix = matrix.translate(-0.0001f, 0f, 0f);
        }

        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS) {
            matrix = matrix.translate(0.0f, 0.0001f, 0f);
        }

        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_S) == GLFW.GLFW_PRESS) {
            matrix = matrix.translate(0.0f, -0.0001f, 0f);
        }

    }
}
