package net.jmecn.scene;

import net.jmecn.math.Transform;

/**
 * 代表三维空间，是Geometry和Node的父类。
 * 
 * @author yanmaoyuan
 *
 */
public abstract class Spatial {

    // 父节点
    private Node parent;
    // 相对空间变换
    private Transform localTransform = new Transform();
    // 世界空间变换
    private Transform worldTransform = new Transform();
    
    /**
     * 获得相对空间变换
     * @return
     */
    public Transform getLocalTransform() {
        return localTransform;
    }

    /**
     * 获得世界空间变换
     * @return
     */
    public Transform getWorldTransform() {
        worldTransform.set(localTransform);
        if (parent != null) {
            // 合并父节点的空间变换
            worldTransform.combineWithParent(parent.getWorldTransform());
        }
        return worldTransform;
    }
    
    /**
     * 从父节点中移除自己
     */
    public void removeFromParent() {
        if (parent != null) {
            parent.detachChild(this);
        }
    }
    
    /**
     * 设置父节点
     * @param newParent
     */
    protected void setParent(Node newParent) {
        if (newParent == null) {
            removeFromParent();
        }
        this.parent = newParent;
    }
    
    /**
     * 获得父节点
     * @return
     */
    public Node getParent() {
        return parent;
    }
    
}
