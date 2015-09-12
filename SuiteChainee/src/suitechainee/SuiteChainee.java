/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suitechainee;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * build a linked list
 * add, get, remove, reset element
 * Override toString()
 * @author Zoe
 * @param <T>
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
    
    /**
    * to confirm if operator and taille are both valid
    */
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
    * if head is null then the list is empty
     * @return 
    */
   public boolean isEmpty() {
       return mHeader == null;
   }
   
   /**
    * add a new element to the list
    * after adding an element increase list size
     * @param element
    */
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
    
   /**
    * remove an element in a position
    * move the pointer to the position, then let the previous node next point to the next node
    * after removing an element, decrease list size
     * @param position
    */
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
    
    /**
    * remove an element
    * if find the element in the list,then let the previous node next point to the next node
    * after removing an element, decrease list size
     * @param element
    */
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
        mSize--;
    }
    
    /**
    * to reset data with the new element at the position 
    * move the pointer to the position, than change this node data to the new element
     * @param element
     * @param position
    */
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
    
    /**
    * to get the data of the position
    * move the pointer to the position, then return the data of this node
     * @param position
     * @return 
    */
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
    
    /**
    * get the size of list
     * @return 
    */
    public int getSize() {
        return mSize;
    }
    
    /**
    * to reset list
    * let the head point null, then the list will be reset
    */
    public void reset() {
        mHeader = null;
    }
    
    /**
    * get the file path
     * @return 
    */
    public String getPath() {
        return mPath;
    }
    
    /**
    * get the first value
     * @return 
    */
    public T getVal1() {
        return mVal1;
    }
    
    /**
    * get the second value
     * @return 
    */
    public T getVal2() {
        return mVal2;
    }
    
    /**
    * get the operator
     * @return 
    */
    public String getOperateur() {
        return mOperateur;
    }
    
    /**
    * get the last element index
    * it should be the size reduce 1
     * @return 
    */
    public int getTheLastIndex() {
        return mSize--;
    }
    
    /**
    * get the taille (which is the user expected)
     * @return 
    */
    public int getTaille() {
        return mTaille;
    }
    
    /**
    * get the state of vide
     * @return 
    */
    public boolean getEtatVide() {
        return mEtatVide;
    }
    
    /**
    * override toString() 
     * @return 
    */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("");
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
     * notice user to input the required parameters
     * check the input is valid
     * construct a SuiteChainee with the parameters
     * create ICommand object with the operation that user expected
     * build the proper list according the parameters
     * create a file and write the list info to this file 
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Integer val1 = null, val2 = null, taille = null;
        String operateur = null;
        Boolean isVide = null;
        //create Ma_chaine.properties file, if it deos not existe
        File properFile = new File("Ma_chaine.properties");
        if(!properFile.exists()) {
            try {
                properFile.createNewFile();
                putProperListParameters();
                System.out.println("Please entrer propertes in Ma_chaine.properties file");
                return;
            } catch (IOException ex) {
                Logger.getLogger(SuiteChainee.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        try {
            //read parametrers from Ma_chaine.properties
            InputStream is = new FileInputStream("Ma_chaine.properties");
            Properties proper = new Properties();
            proper.load(is);
            is.close();
            try {
                val1 = Integer.valueOf(proper.getProperty("val1"));
                val2 = Integer.valueOf(proper.getProperty("val2"));
                taille = Integer.valueOf(proper.getProperty("taille"));
                operateur = proper.getProperty("operateur");
                
                if(proper.getProperty("etatVide") != null) {
                    String strIsVide = proper.getProperty("etatVide").toLowerCase();
                    if("true".equals(strIsVide)) {
                        isVide = true;
                    } else if("false".equals(strIsVide)) {
                        isVide = false;
                    }
                }
                
            } catch(NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SuiteChainee.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //create a new list
        try {
            SuiteChainee<Integer> mList = new SuiteChainee<Integer>("MaListe.properties", operateur, val1, val2, taille, isVide);
            //create an ICommand object according the operation
            ICommand iCommand = setCommand(operateur);

             try{
                if(mList.isValide()) {
                    try {
                        setList(mList, iCommand); //build list
                } catch(Exception e) {
                        System.out.println(e.getMessage());
                    }
                } 
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }

            System.out.println(mList.toString());

            // create a file than write the list info to this file
            createListElementsProperties();
            putProperListElements(mList);
        } catch(NullPointerException e) {
            
            System.out.println(e.getMessage());
            System.out.println("Please entrer propertes in Ma_chaine.properties file");
        }    
        
    }
    
    /**
    * to get the proper operation command according user's expect
     * @param operation
     * @return 
    */
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
    
    /**
    * to put Properties keys 
     * @throws java.io.IOException
    */
   public static void putProperListParameters() throws IOException {
        Properties prop = new Properties();
        prop.put("operateur", "");
        prop.put("val1", "");
        prop.put("val2", "");
        prop.put("taille", "");
        prop.put("etatVide", "");
        try {
            FileOutputStream fis = new FileOutputStream("Ma_chaine.properties");
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fis));
           
            prop.store(bw, "ma chaine parameters");
            bw.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SuiteChainee.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   /**
    * to put the result of the elements of list 
     * @param suiteChainee
    */
    public static void putProperListElements(SuiteChainee suiteChainee) throws IOException {
        Properties prop = new Properties();
        prop.put("val1", suiteChainee.getVal1().toString());
        prop.put("val2", suiteChainee.getVal2().toString());
        prop.put("operateur", suiteChainee.getOperateur());
        prop.put("index", String.valueOf(suiteChainee.getTheLastIndex()));
        prop.put("taille", String.valueOf(suiteChainee.getTaille()));
        prop.put("contenue", suiteChainee.toString());
        
        try {
            FileOutputStream fis = new FileOutputStream("resultsMaList.properties");
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fis));
            prop.store(bw, "the results");
            bw.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SuiteChainee.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
    * to create result list properties file
    */
   public static void createListElementsProperties() {
       File properFile = new File("resultsMaList.properties");
       if(!properFile.exists()) {
           try {
               properFile.createNewFile();
           } catch (IOException ex) {
               Logger.getLogger(SuiteChainee.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
   }
    
   
   
    /**
    * to build list according the parameters
     * @param list
     * @param command
    */
    public static void setList(SuiteChainee<Integer> list, ICommand command) {
        if(list.mEtatVide) {
            list.mHeader = null; //set empty list
        } else {
            list.add(list.mVal1);
            list.add(list.mVal2); 
            //get the following node data from the third node
            for(int i = 2; i < list.mTaille; i++) {
                int nextVal = command.operate(list.getAt(i-2), (int)list.getAt(i-1));
                list.add(nextVal);
            }
        }  
    }
    
   /**
 * node of list to save the data and point the next node
 * @param <T>
 */ 
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
