import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL33;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Square {

    private float[] vrcholy = new float[12];

    private final int[] indexy =
            {
                    0, 1, 3,
                    1, 2, 3
            };

    private static final float[] color = {
            1.0f, 1.0f, 0.0f,
            1.0f, 1.0f, 0.0f,
            1.0f, 1.0f, 0.0f,
            1.0f, 1.0f, 0.0f,
    };

    private int squareVaoId;
    private int squareVboId;
    private int squareEboId;
    private int colorId;

    private FloatBuffer fb = BufferUtils.createFloatBuffer(vrcholy.length);
    private IntBuffer ib = BufferUtils.createIntBuffer(indexy.length);

    public Square() {
        squareVaoId = GL33.glGenVertexArrays();
        squareVboId = GL33.glGenBuffers();
        squareEboId = GL33.glGenBuffers();
        colorId = GL33.glGenBuffers();

        GL33.glBindVertexArray(squareVaoId);

        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, colorId);

        FloatBuffer cb = BufferUtils.createFloatBuffer(color.length)
                .put(color)
                .flip();

        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, cb, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(1, 3, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(1);

        GL33.glBindBuffer(GL33.GL_ELEMENT_ARRAY_BUFFER, squareEboId);
                ib = ib
                .put(indexy)
                .flip();
        GL33.glBufferData(GL33.GL_ELEMENT_ARRAY_BUFFER, ib, GL33.GL_STATIC_DRAW);


    }

    public float[] getVrcholy() {
        return vrcholy;
    }

    public void setVrcholy(float[] vrcholy) {

        this.vrcholy = vrcholy;
        GL33.glBindVertexArray(squareVaoId);
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, squareVboId);
        fb = fb.clear().put(vrcholy).flip();
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, fb, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(0, 3, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(0);
    }

    public int getSquareVaoId() {
        return squareVaoId;
    }

    public void setSquareVaoId(int squareVaoId) {
        this.squareVaoId = squareVaoId;
    }

    public int getIndexy() {
        return indexy.length;
    }

}
