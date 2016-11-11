
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *This is the driver class for TreeNavigator
 * @author vrock
 */
public class DecisionTreeClassifier
{

    /**
     *This is the main Method of the class  
     * @param args
     */
    public static void main (String args[])
    {
        boolean quit=false;
         TreeNavigator ob1=new TreeNavigator();
        Scanner in=new Scanner(System.in);
        
        
         
           
        try {
            while(quit!=true)
            {
                System.out.println("Welcome to the Decision Tree Classifier! \n ");
                System.out.println("        I)Import a tree from a file\n" +
                           "        E)Edit current tree\n" +
                           "        C)Classify a Description\n" +
                           "        P)Show decision path for a Description\n" +
                           "        Q) Quit.");
                System.out.println("Enter something from the menu options:");
                char ch=in.next().charAt(0);
                in.nextLine();
                switch (Character.toUpperCase(ch)) 
                {
                    case 'I':
                        System.out.println("Please enter a filename: ");
                        String fileName = in.nextLine();
                        ob1=TreeNavigator.buildTree(fileName);
                        break;
                    case 'E':
                        boolean q=false;
                        if(ob1.getCursor()==ob1.getRoot())
                        {
                            System.out.println("Cursor is at Root");
                            
                        }
                        else
                        {
                            System.out.println("cursor is at"+ob1.getCursor().getKeywords().toString());
                        }
                        while(q!=true)
                        {
                            if(ob1.getCursor()==ob1.getRoot())
                            {
                                    System.out.println("Current node keywords:"+Arrays.toString(ob1.getCursor().getKeywords()));
                            }
                                    System.out.println("Please select an option:\n" +
                                                "\t E)Edit Keywords\n" +
                                                "\t C)Add Children\n" +
                                                "\t D)Delete Children, and Make Leaf \n" +
                                                "\t N)Cursor to No Child\n" +
                                                "\t Y)Cursor to Yes Child\n" +
                                                "\t R)Cursor to Root\n" +
                                                "\t P)Cursor to Parent\n"+
                                                "\t M) Main Menu");
                        
                                    System.out.println("Please select an Edit option:");
                                    char ch2=in.next().charAt(0);
                                    in.nextLine();
                                    switch (Character.toUpperCase(ch2)) 
                                    {
                                        case 'E':
                                           System.out.println("Please enter keywords for this node, separated by commas:");
                                           String keynew=in.nextLine();
                                           String arr[]=keynew.split(",",-1);
                                           ob1.editCursor(arr);
                                           System.out.println("Keywords updated to: "+Arrays.toString(ob1.getCursor().getKeywords()));
                                           break;
                                        case 'C':
                                            String noleaf="";
                                            String yesleaf="";
                                            if(ob1.getCursor().getLeft()!=null && ob1.getCursor().getRight()!=null)
                                            {
                                                System.out.println("Can't add Children as the Node already has Children");
                                                break;
                                            }
                                            if(ob1.getCursor().getLeft()!=null)
                                            {
                                                System.out.println("Please enter terminal text for the no leaf: ");
                                                noleaf=in.nextLine();
                                                TreeNode no=new TreeNode();
                                                no.setkeyHelper(noleaf);
                                                ob1.getCursor().setLeft(no);
                                                ob1.getCursor().getLeft().setLeaf(true);
                                            }
                                            else if(ob1.getCursor().getRight()!=null)
                                             {
                                                System.out.println("Please enter terminal text for the yes leaf: ");
                                                yesleaf=in.nextLine();   
                                                 TreeNode yes=new TreeNode();
                                                yes.setkeyHelper(yesleaf);
                                                ob1.getCursor().setRight(yes);
                                                ob1.getCursor().getRight().setLeaf(true);
                                                ob1.getCursor().setLeaf(false);
                                             }
                                            else
                                            {
                                                System.out.println("Please enter terminal text for the no leaf: ");
                                                noleaf=in.nextLine();
                                                System.out.println("Please enter terminal text for the yes leaf: ");
                                                yesleaf=in.nextLine(); 
                                                TreeNode no=new TreeNode();
                                                no.setkeyHelper(noleaf);
                                                ob1.getCursor().setLeft(no);
                                                ob1.getCursor().getLeft().setLeaf(true);
                                                TreeNode yes=new TreeNode();
                                                yes.setkeyHelper(yesleaf);
                                                ob1.getCursor().setRight(yes);
                                                ob1.getCursor().getRight().setLeaf(true);
                                                ob1.getCursor().setLeaf(false);
                                            }
                                            
                                           
                                            System.out.println("New Children are: yes - "+Arrays.toString(ob1.getCursor().getRight().getKeywords())
                                            +" and no - "+Arrays.toString(ob1.getCursor().getLeft().getKeywords()));
                                            break;
                                        case 'D':
                                            System.out.println("Please enter a terminal text for this node: ");
                                            String edit=in.nextLine();
                                            ob1.getCursor().setLeft(null);  //removes the children links to make it a leaf
                                            ob1.getCursor().setRight(null);
                                            ob1.getCursor().setLeaf(true);
                                            ob1.getCursor().setkeyHelper(edit);
                                            break;
                                        case 'N':
                                            if(ob1.getCursor().getLeft()!=null)
                                            {
                                               
                                                ob1.cursorLeft();
                                                break;
                                            }
                                            else
                                            {
                                                 if(ob1.getCursor().isLeaf()==true)
                                                {
                                                    System.out.println("Cursor is a Leaf,No Children");
                                                    break;
                                                }
                                                else
                                                 {
                                                     System.out.println("No Left Node, Cursor was unaffected");
                                                     break;
                                                 }
                                            }
                                            
                                        case 'Y':
                                             if(ob1.getCursor().getRight()!=null)
                                            {
                                               
                                                ob1.cursorRight();
                                                break;
                                            }
                                            else
                                            {
                                                 if(ob1.getCursor().isLeaf()==true)
                                                {
                                                    System.out.println("Cursor is a Leaf,No Children");
                                                    break;
                                                }
                                                else
                                                 {
                                                     System.out.println("No Right Node, Cursor was unaffected");
                                                     break;
                                                 }
                                            }
                                        case 'P':
                                           if(ob1.getCursor()==ob1.getRoot())
                                           {
                                               System.out.println("Cursor is at root, cant go to parent");
                                           }
                                           else
                                            ob1.setCursor2Parent();
                                          break;
                                        case 'R':
                                            ob1.resetCursor();
                                            System.out.println("Cursor set to Root");
                                            break;
                                        case 'M':
                                            q=true;
                                            
                                            break;
                                    }
                        }   
                        break;
                    case 'C':
                        System.out.println("Please enter some text:");
                        String str=in.nextLine();
                        System.out.println(ob1.classify(str));
                        break;
                    case 'P':
                        System.out.println("Please enter some text:");
                        String str2=in.nextLine();
                        System.out.println("Decision Path: "+ob1.path(str2));
                        break;
                    case 'Q':
                        quit=true;
                        System.out.println("GoodBye!");
                        break;
                        
                }
            }
            } catch (IOException ex) {
               System.out.println("Couldn't load Tree");
               System.out.println("Try Again!");
               DecisionTreeClassifier.main(args);
            }
        }
            
    } 
            
    
