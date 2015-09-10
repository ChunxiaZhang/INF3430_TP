/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suitechainee;

import static java.lang.Math.abs;

/**
 *
 * @author Zoe
 */
public class Multiplication implements ICommand{

    @Override
    public int operate(int val1, int val2) {
        Addition add = new Addition();
        int absVal1 = abs(val1);
        int res = 0;
        int absVal2 = abs(val2);
        if (absVal2 == 0) {
            res = 0;
        } else {
            while(absVal2-- != 0) {
                res += add.operate(0, absVal1);
                 
            }
        }
        if(!((val1 > 0 && val2 > 0) || (val1 < 0 && val2 < 0))) {
            res *= -1;
        }
        return res;
    }
    
}
