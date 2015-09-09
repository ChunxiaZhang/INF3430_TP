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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Zoe
 */
public class SuiteChainee<T> {
    private Node<T> mHeader, mTail;
    private int mSize;
    private final String mPath, mOperateur;
    private final T mVal1, mVal2; 
    private final int mTaille;
    private final boolean mEtatVide;
     
    public SuiteChainee(String path, String operateur, T val1, T val2, int taille, boolean etatVide) {
        mHeader = null;
        mTail = null;
        mSize = 0;
        mPath = path;
        mOperateur = operateur;
        mVal1 = val1;
        mVal2 = val2;
        mTaille = taille;
        mEtatVide = etatVide;           
    }
   
   public boolean isEmpty() {
       return mHeader == null;
   }
   
   public void add(T element) {
       //if list is empty, add header
       if(isEmpty()) {
           mHeader = new Node<T>(element);
           mSize++;
           if(mTail == null) {
               mTail = mHeader;
           }
       } else { //add new element in tail
           mTail.next = new Node<T>(element);
           mTail = mTail.next;
           mSize++;
       }
       
    }
    
    public void removeAt(int position) {
        if(isEmpty()) {
            throw new RuntimeException("empty list, cannot delete"); 
        }
        if(position < 0 || position >= (mSize-1)) {
            throw new NullPointerException("position out of point, cannot delete"); 
        }
        //if the position is the fist element, delete header and change the header to the next
        if(position == 0) {
            mHeader = mHeader.next;
            mSize--;
            return;
        }
        int i = 0;
        Node<T> currentNode = mHeader;
        Node<T> previousNode = null;
        // move to the position
        while(i <= position) {
            i++;
            previousNode = currentNode;
            currentNode = currentNode.next;
        }
        //delete the node of this position
        previousNode.next = currentNode.next;
        mSize--;
    }
    
    public void removeItem(T element) {
        if(isEmpty()) {
            throw new RuntimeException("empty list, cannot delete");
        }
        
        if(mHeader.data.equals(element)) {
            mHeader = mHeader.next;
            mSize--;
            return;
        }
        
        Node<T> currentNode = mHeader;
        Node<T> previousNode = null;
        while(currentNode != null && !currentNode.data.equals(element)) {
            previousNode = currentNode;
            currentNode = currentNode.next;
        }
        if(currentNode == null) {
            throw new RuntimeException("no this element in list, cannot delete");
        }
        
        //delete current node
        previousNode = currentNode.next;
    }
    
    public void setAt(T element, int position) {
        if(position < 0 || position >= mSize) {
            throw new NullPointerException("position out of point, cannot delete"); 
        }
        Node<T> currentNode = mHeader;
        int i = 0;
        //move to the position
        while(i != position) {
            i++;
            currentNode = currentNode.next;
        }
        //change data of this position
        currentNode.data = element;
    }
    
    public T getAt(int position) {
        if(position < 0 || position >= mSize) {
            throw new NullPointerException("position out of point, cannot delete"); 
        }
        Node<T> currentNode = mHeader;
        int i = 0;
        //move to the position
        while(i != position) {
            i++;
            currentNode = currentNode.next;
        }
        return currentNode.data;
    }
    
    public int getSize() {
        return mSize;
    }
    
    public void reset() {
        mHeader = null;
    }
    
    public String getPath() {
        return mPath + ".txt";
    }
    
    public T getVal1() {
        return mVal1;
    }
    
    public T getVal2() {
        return mVal2;
    }
    
    public String getOperateur() {
        return mOperateur;
    }
    
    public int getTheLastIndex() {
        return mSize--;
    }
    
    public int getTaille() {
        return mTaille;
    }
    
    public boolean getEtatVide() {
        return mEtatVide;
    }
    
    public void printList() {
        Node<T> currentNode = this.mHeader;
        if (isEmpty()) {    
            try {  
                throw new Exception("List is empty");  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        while(currentNode != null) {
            System.out.print(currentNode.data + " ");
            currentNode = currentNode.next;
        }
    }
    
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("MaList: ");
        Node<T> currentNode = this.mHeader;
        if (isEmpty()) {    
            result.append("is empty");
        }  
        while(currentNode != null) {
            result.append(String.valueOf(currentNode.data));
            currentNode = currentNode.next;
            if(currentNode != null) {
                result.append(",");
            }
        }
        return result.toString();
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // input parametres
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        
        String operation = inputOperation(bufferRead);
        int val1 = getIntInput("Enter the first value (entier): ", bufferRead);
        int val2 = getIntInput("Enter the second value (entier): ", bufferRead);
        int taille = inputTaille(bufferRead);
        boolean isVide = inputEtatVide(bufferRead);
        
        
        
        SuiteChainee<Integer> mList = new SuiteChainee<Integer>("MaListe.properties", operation, val1, val2, taille, isVide);
        //SuiteChainee<Integer> mList = new SuiteChainee<Integer>("MaListe.properties", "division", -20, 3, 6, false);
        
        ICommand iCommand = setCommand(operation);
        
        try {
            setList(mList, iCommand);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        mList.printList();
        createFile(mList);
    }
    
    public static ICommand setCommand(String operation) {
        switch (operation) {
            case "addition":
                return new Addition();
            case "soustraction":
                return new Soustraction();
            case "multiplication":
                return new Multiplication();
            case "division":
                return new Division();
            default:
                return null;
        }
    }
    
    public static boolean isEtatVideValide(String etatVide) {
        boolean isEtatVideValide = false;
        if(etatVide.toLowerCase().equals("y") || etatVide.toLowerCase().equals("n")) {
            isEtatVideValide = true;
        }
        return isEtatVideValide;
    }
    
    public static boolean isTailleValide(int taille) {
        boolean isTailleValide = false;
        if(taille < 2 || taille > 10) {
            isTailleValide = false;
        } else {
            isTailleValide = true;
        }
        return isTailleValide;
    }
    
    public static boolean isOperationValide(String operation) {
        boolean isOperationValide = false;
        
        switch (operation) {
            case "addition":
            case "soustraction":
            case "multiplication":
            case "division":
                isOperationValide = true;
                break;
            default:
                isOperationValide = false;
               
        }
    
        return isOperationValide;
   
    }
    
    public static boolean inputEtatVide(BufferedReader bufferRead) {
        boolean isVide = false;
        String notice = "Enter state of vide (Y/y: vide; N/n: not vide): ";
        String etat = getStringInput(notice, bufferRead);
        while(!isEtatVideValide(etat)) {
            etat = getStringInput(notice, bufferRead);
        }
        if(etat.toLowerCase().equals("y")) {
            isVide = true;
        } else {
            isVide = false;
        }
        return isVide;
    }
    
    public static String inputOperation(BufferedReader bufferRead) {
        String operation = "";
        String notice = "Enter operation(addition, soustraction, multiplication or division):";
        
        operation = getStringInput(notice, bufferRead);
        while(!isOperationValide(operation)){
            operation = getStringInput(notice, bufferRead);
        }
        return operation;
    }
    
    public static int inputTaille(BufferedReader bufferRead) {
        String notice = "Enter the taille (unsign entier from 2 to 10): ";
        int taille = getIntInput(notice, bufferRead);
        while(!isTailleValide(taille)) {
            taille = getIntInput(notice, bufferRead);
        }
        return taille;
    }
    
    public static String getStringInput(String notic, BufferedReader bufferRead) {
        System.out.println(notic);
        String result = "";
        try {
            result = bufferRead.readLine();
        } catch (IOException ex) {
            Logger.getLogger(SuiteChainee.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    public static Integer getIntInput(String notic, BufferedReader bufferRead) {
        System.out.println(notic);
        Integer result = null;
        try {
            result = Integer.valueOf(bufferRead.readLine());
        } catch (IOException ex) {
            Logger.getLogger(SuiteChainee.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public static void setList(SuiteChainee<Integer> list, ICommand command) {
        if(list.mEtatVide) {
            list.mHeader = null; //set empty list
        } else {
            list.add(list.mVal1);
            list.add(list.mVal2); 
            for(int i = 2; i < list.mTaille; i++) {
                int nextVal = command.operate(list.getAt(i-2), (int)list.getAt(i-1));
                list.add(nextVal);
            }
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
            output.write("Parametre6:" + suiteChaine.toString());
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(SuiteChainee.class.getName()).log(Level.SEVERE, null, ex);
        } 
       
    }
    
    
    private static class Node<T>{
        private T data;
        private Node<T> next;
        public Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }
        public Node(T data) {
            this(data, null);
        }
    }
}
