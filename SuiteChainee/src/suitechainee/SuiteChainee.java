/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suitechainee;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Zoe
 */
public class SuiteChainee {

    private final String mPath, mOperateur;
    private final int mVal1, mVal2, mTaille;
    private int mTheLastIndex = 0;
    private final boolean mEtatVide;
    private int[] mList;
    private ICommand mICommand;
      
    public SuiteChainee(String path, String operateur, int val1, int val2, int taille, boolean etatVide) {
        
        mPath = path;
        mOperateur = operateur;
        mVal1 = val1;
        mVal2 = val2;
        mTaille = taille;
        mEtatVide = etatVide;
            
    }
    
    public void setList() {
        if (mEtatVide) {
            mList = null;
        } else {
            mList = new int[mTaille];
            mList[0] = mVal1;
            mList[1] = mVal2;
            mTheLastIndex++;
            for(int i = 2; i < mTaille; i++) {
                try{
                    int nextVal = mICommand.operate(mList[i-2], mList[i-1]);
                    add(nextVal); 
                } catch(Exception e) {
                    System.out.println(e.getMessage());
                    break;
                }
                  
            }
        }
    }
    
    public String getPath() {
        return mPath + ".txt";
    }
    
    public int getVal1() {
        return mVal1;
    }
    
    public int getVal2() {
        return mVal2;
    }
    
    public String getOperateur() {
        return mOperateur;
    }
    
    public int getTheLastIndex() {
        return mTheLastIndex;
    }
    
    public int getTaille() {
        return mTaille;
    }
    
    public boolean getEtatVide() {
        return mEtatVide;
    }
    
    public String getContenu() {
        StringBuilder contenu = new StringBuilder("MaList:");
        for(int i = 0; i < mTaille; i++) {
            contenu.append(mList[i]);
            if(i < mList.length - 1) {
                contenu.append(",");
            }
        }
        return contenu.toString();
    }
    
    public int[] getList() {
        return mList;
    }
    
    public void setCommand() {
        switch (mOperateur) {
            case "addition":
                mICommand = new Addition();
                break;
            case "soustraction":
                mICommand = new Soustraction();
                break;
            case "multiplication":
                mICommand = new Multiplication();
                break;
            case "division":
                mICommand = new Division();
                break;
            default:
                break;
        }
    }
    
    public void add(int element) {
        if (mTheLastIndex < mTaille - 1) {
            mTheLastIndex++;
            mList[mTheLastIndex] = element;
        } else {
            throw new NullPointerException("La list est complete");
        }
    }
    
    public void removeAt(int position) {
        int[] list = new int[mTaille - 1];
        if (position >= 0 && position < mTaille) {
            for(int i = 0; i < mTaille - 1; i++) {
                if(i < position) {
                    list[i] = mList[i];
                } else {
                    list[i] = mList[i+1];
                }
            }
            mList = list;
            mTheLastIndex--;
        } else {
            throw new NullPointerException("Position is out");
        }
    }
    
    public void removeItem(int element) {
        int position = -1;
        int[] list = new int[mTaille]; 
        for(int i = 0; i < mTaille; i++) {
            if(mList[i] == element) {
                position = i;
            }
        }
        if(position != -1) {
            for(int i = 0; i < mTaille - 1; i++) {
                if(i < position) {
                    list[i] = mList[i];
                } else {
                    list[i] = mList[i+1];
                }
            }
            mList = list;
            mTheLastIndex--;
        } else {
            throw new IllegalArgumentException("Element n'est pas dans la liste");
        }
    }
    
    public void setAt(int element, int position) {
        if(position >= 0 && position < mTaille) {
            mList[position] = element; 
        } else {
            throw new NullPointerException("Position is out");
        }
    }
    
    public int getAt(int position) {
        if(position >= 0 && position < mTaille) {
            return mList[position];
        } else {
            throw new NullPointerException("Position is out");
        }
    }
    
    public int getSize() {
        return mList.length;
    }
    
    public void reset() {
        mList = null;
    }
    
    private boolean isValide() {
        boolean isOperateurValide = false;
        boolean isTailleValide = false;
        
        switch (mOperateur) {
            case "addition":
            case "soustraction":
            case "multiplication":
            case "division":
                isOperateurValide = true;
                break;
            default:
                throw new IllegalArgumentException("Operateur n'est pas valide");
               
        }
        
        if(mTaille < 2 || mTaille > 10) {
            throw new IllegalArgumentException("La taille n'est pas valide");
        } else {
            isTailleValide = true;
        }
        return isOperateurValide && isTailleValide;
   
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // input parametres
        System.out.println("Enter operation(addition, soustraction, multiplication or division):");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        
        try {
            String strOperation = bufferRead.readLine();
            System.out.println("The operation is: " + strOperation);
        } catch (IOException ex) {
            Logger.getLogger(SuiteChainee.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Enter the first value (entier): ");
        try {
            String strVal1 = bufferRead.readLine();
            System.out.println("The first value is: " + strVal1);
        } catch (IOException ex) {
            Logger.getLogger(SuiteChainee.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
        SuiteChainee mSuiteChainee = new SuiteChainee("MaListe.properties", "division", -20, 2, 6, false);
        try{
            if(mSuiteChainee.isValide()) {
                mSuiteChainee.setCommand();
                mSuiteChainee.setList();
                if(mSuiteChainee.getList() != null) {
                    System.out.println(mSuiteChainee.getContenu());
                    createFile(mSuiteChainee);
                }
            } 
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
         
    }
    
    public static void createFile(SuiteChainee suiteChaine) {
        BufferedWriter output;
        File file = new File(suiteChaine.getPath());
        try {
            output = new BufferedWriter(new FileWriter(file));
            output.write("Parametre1:" + suiteChaine.getVal1());
            output.newLine();
            output.write("Parametre2:" + suiteChaine.getVal2());
            output.newLine();
            output.write("Parametre3:" + suiteChaine.getOperateur());
            output.newLine();
            output.write("Parametre4:" + suiteChaine.getTheLastIndex());
            output.newLine();
            output.write("Parametre5:" + suiteChaine.getTaille());
            output.newLine();
            output.write("Parametre6:" + suiteChaine.getContenu());
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(SuiteChainee.class.getName()).log(Level.SEVERE, null, ex);
        } 
       
    }
}
