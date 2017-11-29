package net.jmecn.scene;

import net.jmecn.math.Transform;

public abstract class Spatial {

    private Node parent;
    
    protected Transform localTransform = new Transform();
    protected Transform worldTransform = new Transform();
    
    public Transform getLocalTransform() {
        return localTransform;
    }
    public Transform getWorldTransform() {
        worldTransform.set(localTransform);
        if (parent != null) {
            // 合并父节点的空间变换
            worldTransform.combineWithParent(parent.getWorldTransform());
        }
        return worldTransform;
    }
    
    protected void setParent(Node parent) {
        if (parent == null && this.parent != null) {
            this.parent.detachChild(this);
        }
        this.parent = parent;
    }
    
    public Node getParent() {
        return parent;
    }
    
    public void removeFromParent() {
        if (parent != null) {
            parent.detachChild(this);
        }
    }
    
}
