package net.jmecn.math;

/**
 * 空间变换
 * @author yanmaoyuan
 *
 */
public class Transform {

    private Vector3f localScale;
    private Quaternion localRotation;
    private Vector3f localTranslation;
    
    public Transform() {
        localScale = new Vector3f(1, 1, 1);
        localRotation = new Quaternion(0, 0, 0, 1);
        localTranslation = new Vector3f(0, 0, 0);
    }

    public Vector3f getLocalScale() {
        return localScale;
    }

    public void setLocalScale(Vector3f scale) {
        this.localScale.set(scale);
    }

    public Quaternion getLocalRotation() {
        return localRotation;
    }

    public void setLocalRotation(Quaternion rotation) {
        this.localRotation.set(rotation);
    }

    public Vector3f getLocalTranslation() {
        return localTranslation;
    }

    public void setLocalTranslation(Vector3f translation) {
        this.localTranslation.set(translation);
    }
    
}
