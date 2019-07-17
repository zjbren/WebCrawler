/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spider;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author zjbre
 */
public class Spider {

    private static int max_depth;
    private Set<Node> visitedNodes = new HashSet<Node>();
    private Set<String> visitedUrl = new HashSet<String>();
    private List<Node> toVisit = new LinkedList<Node>();
    private boolean done = false;

    public Spider(int depth) {
        this.max_depth = depth;    
    }

    //Selects next page to search, checks against visited set to stop duplicates
    public Node nextPage() {
        Node next;
        do {
            if (toVisit.size() == 0) {
                return null;
            }
            next = this.toVisit.remove(0);
            if(next.getDepth() > this.max_depth)
            {
                return null;
            }
        } while (this.visitedUrl.contains(next.getUrl()));
        this.visitedNodes.add(next);
        this.visitedUrl.add(next.getUrl());
        return next;
    }
    
    public void startSearch(List<String> seeds, String keyword)
    {
        for(String s: seeds)
        {
           this.toVisit.add(new Node(0, s));
        }
        search(keyword);
    } 

    public void search(String keyword) {
        Node current = nextPage();
        boolean done = false;
        SpiderLeg leg = new SpiderLeg();
        if (current != null) {
            leg.crawl(current);

            this.done = leg.findKeyword(keyword);
            if (this.done) {
                System.out.println(String.format("**Success** Word %s found at %s", keyword, current.getUrl()));
            }else{
                this.toVisit.addAll(leg.getHyperLinks());
                if (current != null) {
                    search(keyword);
                }
            }

        }
    }

    public static int getMax_depth() {
        return max_depth;
    }

    public boolean isDone() {
        return done;
    }
}
