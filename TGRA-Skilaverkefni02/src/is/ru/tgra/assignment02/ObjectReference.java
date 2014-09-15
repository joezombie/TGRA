package is.ru.tgra.assignment02;

/**
 * Created by Johannes Gunnar Heidarsson on 14.9.2014.
 */
public class ObjectReference {
    public int firstIndex;
    public int vertexCount;
    public int openGLPrimitiveType;

    public ObjectReference(int fi, int vc, int pt){
        this.firstIndex = fi;
        this.vertexCount = vc;
        this.openGLPrimitiveType = pt;
    }
}
