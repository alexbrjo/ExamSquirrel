/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.alexjo.examsquirrel;

import org.junit.Test;

/**
 *
 * @author Alex
 */
public class AppTest {
        
    @Test
    public void testApp () {
        SquirrelAPI e = new SquirrelAPI();
        String s = e.exam();
        for (int i = 0; i < s.length() - 40; i += 40) {
            System.out.println(s.substring(i, i + 40));
        }
    }
    
}
