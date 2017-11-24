package net.jmecn.math;

/**
 * 空间变换
 * @author yanmaoyuan
 *
 */
public class Transform {

    /**
     * 单位空间变换
     */
    public static final Transform IDENTITY = new Transform();
    
    private Vector3f scale = new Vector3f(1, 1, 1);// 比例变换
    private Quaternion rot = new Quaternion();// 旋转变换
    private Vector3f translation = new Vector3f(0, 0, 0);// 平移变换
    
    /**
     * 初始化空间变换
     */
    public Transform(Vector3f translation, Quaternion rot){
        this.translation.set(translation);
        this.rot.set(rot);
    }
    
    public Transform(Vector3f translation, Quaternion rot, Vector3f scale){
        this(translation, rot);
        this.scale.set(scale);
    }

    public Transform(Vector3f translation){
        this(translation, Quaternion.IDENTITY);
    }

    public Transform(Quaternion rot){
        this(Vector3f.ZERO, rot);
    }

    public Transform(){
        this(Vector3f.ZERO, Quaternion.IDENTITY);
    }
    
    /**
     * 单位化
     */
    public void loadIdentity() {
        scale.set(1, 1, 1);
        rot.set(0, 0, 0, 1);
        translation.set(0, 0, 0);
    }
    
    /**
     * 对空间进行空间变换
     * @param in
     * @param store
     * @return
     */
    public Vector3f transformVector(final Vector3f in, Vector3f store){
        if (store == null)
            store = new Vector3f();
        
        store.set(in);
        // 先缩放
        store.multLocal(scale);
        // 再旋转
        rot.mult(store, store);
        // 再平移
        store.addLocal(translation);
        return store;
    }

    /**
     * 对顶点进行逆变换
     * @param in
     * @param store
     * @return
     */
    public Vector3f transformInverseVector(final Vector3f in, Vector3f store){
        if (store == null)
            store = new Vector3f();

        // 先负平移
        in.subtract(translation, store);
        // 然后负旋转
        rot.inverse().mult(store, store);
        // 然后负缩放
        store.divideLocal(scale);

        return store;
    }
    
    /**
     * 三种变换转为4x4矩阵
     * @return
     */
    public Matrix4f toTransformMatrix() {
        Matrix4f trans = new Matrix4f();
        trans.setTranslation(translation);
        trans.setRotationQuaternion(rot);
        trans.setScale(scale);
        return trans;
    }
    
    /**
     * 4x4矩阵转为三种变换
     * @param mat
     */
    public void fromTransformMatrix(Matrix4f mat) {
        translation.set(mat.toTranslationVector());
        rot.set(mat.toRotationQuat());
        scale.set(mat.toScaleVector());
    }
    
    /**
     * 求空间变换的逆
     * @return
     */
    public Transform invert() {
        Transform t = new Transform();
        t.fromTransformMatrix(toTransformMatrix().invertLocal());
        return t;
    }
    
    /**
     * 在两个空间变换之间插值
     * @param t1
     * @param t2
     * @param delta
     */
    public void interpolateTransforms(Transform t1, Transform t2, float delta) {
        this.rot.slerp(t1.rot,t2.rot,delta);
        this.translation.interpolateLocal(t1.translation,t2.translation,delta);
        this.scale.interpolateLocal(t1.scale,t2.scale,delta);
    }
    
    /**
     * 根据“父”空间变换来计算当前空间变换。
     * 
     * @param parent
     * @return
     */
    public Transform combineWithParent(Transform parent) {
        scale.multLocal(parent.scale);
        parent.rot.mult(rot, rot);

        translation.multLocal(parent.scale);// 缩放
        parent.rot.multLocal(translation)   // 旋转
            .addLocal(parent.translation);  // 平移
        return this;
    }
    
    // 基本的Getter和Setter
    
    /**
     * 复制另一个空间变换的值
     * @param matrixQuat
     * @return
     */
    public Transform set(Transform matrixQuat) {
        this.translation.set(matrixQuat.translation);
        this.rot.set(matrixQuat.rot);
        this.scale.set(matrixQuat.scale);
        return this;
    }
    
    public Transform setTranslation(Vector3f trans) {
        this.translation.set(trans);
        return this;
    }
    
    public Transform setTranslation(float x,float y, float z) {
        translation.set(x,y,z);
        return this;
    }

    public Vector3f getTranslation(Vector3f trans) {
        if (trans==null)
            trans=new Vector3f();
        trans.set(this.translation);
        return trans;
    }
    
    public Vector3f getTranslation() {
        return translation;
    }

    public Transform setScale(Vector3f scale) {
        this.scale.set(scale);
        return this;
    }

    public Transform setScale(float x, float y, float z) {
        scale.set(x,y,z);
        return this;
    }
    
    public Transform setScale(float scale) {
        this.scale.set(scale, scale, scale);
        return this;
    }

    public Vector3f getScale(Vector3f scale) {
        if (scale==null)
            scale=new Vector3f();
        scale.set(this.scale);
        return scale;
    }
    
    public Vector3f getScale() {
        return scale;
    }
    
    public Transform setRotation(Quaternion rot) {
        this.rot.set(rot);
        return this;
    }

    public Quaternion getRotation(Quaternion quat) {
        if (quat==null)
            quat=new Quaternion();
        quat.set(rot);
        return quat;
    }
    
    public Quaternion getRotation() {
        return rot;
    }
}
