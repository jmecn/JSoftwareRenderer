package net.jmecn.scene;

import java.util.ArrayList;
import java.util.List;

/**
 * 节点
 * @author yanmaoyuan
 *
 */
public class Node extends Spatial {

    private List<Spatial> children;
    
    public Node() {
        children = new ArrayList<Spatial>();
    }
    
    /**
     * 添加子节点
     * @param spatial
     */
    public void attachChild(Spatial spatial) {
        children.add(spatial);
        spatial.setParent(this);
    }
    /**
     * 移除子节点
     * @param spatial
     */
    public void detachChild(Spatial spatial) {
        children.remove(spatial);
    }

    /**
     * 遍历场景，获取所有Geometry
     * @param list
     * @return
     */
    public List<Geometry> getGeometryList(List<Geometry> list) {
        if (list == null) {
            list = new ArrayList<Geometry>();
        }
        int len = children.size();
        for(int i=0; i<len; i++) {
            Spatial spatial = children.get(i);
            if (spatial instanceof Geometry) {
                list.add((Geometry) spatial);
            } else if (spatial instanceof Node) {
                // 递归
                Node node = (Node) spatial;
                node.getGeometryList(list);
            }
        }
        
        return list;
    }
}
