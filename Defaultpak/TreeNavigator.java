
import com.sun.xml.internal.ws.util.StringUtils;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 *This is the class which has the methods to make the tree with a cursor.
 * @author vrock
 */
public class TreeNavigator 
{
    private TreeNode root;
    private TreeNode cursor;
    private TreeNode Parcursor;
    private String Nodedesc="";
    public TreeNavigator()
    {
        root=new TreeNode();
        cursor=root;
        Parcursor=root;
        String str[]={"tree is empty"};
        editCursor(str);
    }

    /**
     *This method converts the text file to a binary tree
     * @param treeFile: is the file name which is to be converted to a tree
     * @return: Newly made tree 
     * @throws IOException: if cannot find the tree then throw
     */
    public static TreeNavigator buildTree(String treeFile)throws IOException
    {
            TreeNavigator Tree=new TreeNavigator();
            String everything="";
            File read = new File(treeFile);
            Scanner Fin=new Scanner(read);
            while(Fin.hasNext())
            {
                everything+=Fin.nextLine()+"; \n";
            }
            String str="";
           for(int i=0;i<everything.length();i++)
           {
               
               char ch1=everything.charAt(i);
               if(ch1!=';')
               {
                    str=str+ch1;
               }
               else
                {
                      if(str.matches("^[01\\-\\n\\ ]+$"))   //this is to check if the String is in a form of 0s,1s and hyphens
                      {
                          str=str.replace("-","");
                          str=str.replace(" ","");
                          str=str.replace("\n","");
                          for(int j=1;j<str.length();j++)
                            {
                                char ch2=str.charAt(j);
                                if(ch2=='0')
                                {
                                    if(Tree.cursor.getLeft()==null)
                                    {
                                        TreeNode newnode=new TreeNode();
                                        Tree.getCursor().setLeft(newnode);
                                    }
                                    Tree.cursor=Tree.cursor.getLeft();
                                }
                                else if(ch2=='1')
                                {
                                     if(Tree.cursor.getRight()==null)
                                    {
                                        TreeNode newnode=new TreeNode();
                                        Tree.getCursor().setRight(newnode);
                                    }
                                    Tree.cursor=Tree.cursor.getRight();
                                }
                            }
                       }
                      
                    else if(str.equals("leaf"))  //sets the node leaf boolean according to the text file
                    {
                        Tree.cursor.setLeaf(true);
                        Tree.resetCursor();
                    }
                    else if(str.equals("nonleaf"))
                    {
                        Tree.cursor.setLeaf(false);
                        Tree.resetCursor();
                    }
                    else
                    {
                 
                       String arr[]=str.split(",",-1);                  
                       Tree.editCursor(arr);  /* if its not in form 0s and 1s and is 
                                               not leaf or nonleaf then edits thr cursor keywords*/
                    }
                    str="";
                    
                }
                   
             }
           System.out.println("Tree Loaded");
      
           return Tree;             
    }

    /**
     *This method finds the classification of a sentence given by the user
     * @param text- The sentence given by the user to find classification
     * @return String with classification
     */
    public String classify(String text)
    {
        TreeNode Fakecursor=root;
        String str="";
           for(int i=0;i<text.length();i++)
            {
                String[] key=Fakecursor.getKeywords();
                for(int j=0;j<key.length;j++)
                {
                    String keyword=key[j];
                    if(text.toLowerCase().indexOf(keyword.toLowerCase())>=0)
                    {
                        Fakecursor=Fakecursor.getRight();   // if it matches the keywords then go to yes node
                    }
                    else
                    {
                        Fakecursor=Fakecursor.getLeft();     // if not then no node
                    }
                }
                if(Fakecursor.isLeaf()==true)
                {
                    str= "Your request is classified as: "+Arrays.toString(Fakecursor.getKeywords());
                    break;
                }
            }
           return str;
    }

    /**
     *This method makes a path according to the sentence given by the user 
     * and then at the end makes a decision
     * @return- String with the path of the cursor to the decision at the end
     */
    public String getPath()
    {
        TreeNode Fakecursor=root;
       
            boolean is=false;
           String str="";
           String temp="";
           for(int i=0;i<Nodedesc.length();i++)
            {
                String[] key=Fakecursor.getKeywords();
                for(int j=0;j<key.length;j++)
                {
                    String keyword=key[j];
                    if(Nodedesc.toLowerCase().indexOf(keyword.toLowerCase())>=0) /* if there is the word in 
                                                                                    the sentence then go to Right Node*/
                    {
                            temp=temp+" IS "+keyword+",";
                            is=true;
                           
                            break;
                    }
                        else
                        {
                            if(j==key.length-1)
                            {
                                temp=temp+" NOT "
                      +Arrays.toString(key).replace("[","").replace("]","")+",";
                                
                                break;
                            }
                        }
                }
                if(is==true)
                {
                    Fakecursor=Fakecursor.getRight();
                    if(Fakecursor.isLeaf())
                    {
                        break;
                    }
                    is=false;
                }
                else
                {
                    Fakecursor=Fakecursor.getLeft();
                    if(Fakecursor.isLeaf())
                    {
                        break;
                    }
                }
                str=""; 
        }
           temp=temp+"  Decision : "
      +Arrays.toString(Fakecursor.getKeywords()).replace("[","").replace("]","");
         return temp;
      
    }

    /**
     *helper method for getPath()
     * @param desc-The description given by the user
     * @return- String given by the getPath() method
     */
    public String path(String desc)
    {
        
        Nodedesc=desc;
        return getPath();
    }

    /**
     *This method recursively finds the parent of the class
     * @param temp - it is the node which parent has be found
     * @return - Parent of the node
     */
    public TreeNode go2Parent(TreeNode temp)
    {       
         return go2Parent(temp,root,null);
    }

    /**
     *This method is a helper for go2Parent() method
     * @param t- Node which parent has to be found
     * @param r- Root of the tree, changes by recursion
     * @param Parent- Parent of the node which has to be found
     * @return Parent
     */
    public TreeNode go2Parent(TreeNode t,TreeNode r,TreeNode Parent)
    {
        if(root==null)
        {
            return null;
        }
        
            else if(!r.equals(t))
            {
                try{
                    Parent=go2Parent(t,r.getLeft(),r);
                }
                catch(NullPointerException ex)
                {
                    Parent=null;
                }
                if(Parent==null)
                {
                    Parent = go2Parent(t, r.getRight(), r);
                }
            }
       
        return Parent;
    }

    /**
     *this method resets the cursor to root
     */
    public void resetCursor()
    {
        cursor=root;
    }

    /**
     *This method calls the go2Parent() to
     * set the cursor to its current Node's parent
     */
    public void setCursor2Parent()
    {
        cursor=go2Parent(cursor);
    }

    /**
     *This method moves the cursor to its No (Left) node
     */
    public void cursorLeft()
    {
      
            cursor=cursor.getLeft();
            if(cursor.isLeaf()==true)
            {
                System.out.print("Cursor is at leaf, message is "+Arrays.toString(cursor.getKeywords())+"\n");
            }
            else
            {
                System.out.print("Cursor is not a leaf, message is "+Arrays.toString(cursor.getKeywords())+"\n");
            }
        
       
    }

    /**
     *This method moves the cursor to its Yes(Right) node 
     */
    public void cursorRight()
    {
        cursor=cursor.getRight();
        if(cursor.isLeaf())
        {
            System.out.print("Cursor is at leaf, message is "+Arrays.toString(cursor.getKeywords())+"\n");
        }
        else
        {
            System.out.print("Cursor is not a leaf, message is "+Arrays.toString(cursor.getKeywords())+"\n");
        }
    }

    /**
     *This methods returns back the cursor of the Tree
     * @return - cursor
     */
    public TreeNode getCursor()
    {
        if(cursor!=null)
        {
             return cursor;
        }
        else
        {
            return null;
        }
    }

    /**
     *This method returns back the root of the Tree
     * @return - Root
     */
    public TreeNode getRoot()
    {
        return root;
    }

    /**
     *This method converts the String to String[] for keywords
     * of the node
     * @param text- String which has to be converted to String[]
     */
    public void editCursor(String text[])
    {
           cursor.setKeywords(text);
    }
    
    
}
