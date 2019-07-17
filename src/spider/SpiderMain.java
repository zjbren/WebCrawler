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

import java.util.LinkedList;
import java.util.List;

public class SpiderMain {
    
    public static void main(String[] args)
    {
        String url1 = "http://www.harbourgolf.co.nz/";
        String url2 = "http://www.epicbooks.com/";
        List<String> seeds = new LinkedList<String>();
        seeds.add(url1);
        seeds.add(url2);
        
        String keyword = "slope";
        int depth = 5;
        Spider spider = new Spider(depth);
        
        spider.startSearch(seeds, keyword);
        
        if(!(spider.isDone()))
        {
            System.out.println("Did not find " + keyword + " at max depth " + depth);
        }
        
    }
    
}
