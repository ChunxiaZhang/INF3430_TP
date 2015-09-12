/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suitechainee;

/**
 *
 * @author Zoe
 */
public class Addition implements ICommand{

    @Override
    public int operate(int val1, int val2) {
        int res = val1;
        if (val2 > 0) {
            while(val2-- != 0) {
                res++; 
            }
        } else if (val2 < 0) {
            while(val2++ != 0) {
                res--; 
            }
        }
        
        return res;
    }
    
}
