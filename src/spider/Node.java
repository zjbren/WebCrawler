/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spider;

/**
 *
 * @author zjbre
 */
public class Node {
    
    private int depth;
    private String url;

    public Node(int depth, String url) {
        this.depth = depth;
        this.url = url;
    }

    public int getDepth() {
        return depth;
    }

    public String getUrl() {
        return url;
    }
    
}
