
/**
 *This class is the data structure class
 * @author vrock
 */
public class TreeNode 
{
    private String[] keywords;
    private TreeNode left;
    private TreeNode right;
    private boolean leaf;

    /**
     *Default constructor with null values
     */
    public TreeNode()
    {
        left=null;
        right=null;
        leaf=true;
        keywords=null;
    }

    /**
     *Constructor with given values
     * @param left- left node 
     * @param right - right node
     * @param keywords - keywords for the this node
     */
    public TreeNode(TreeNode left,TreeNode right,String[] keywords)
    {
         this.keywords=keywords;
         this.left=left;
         this.right=right;
    }
    
    /**
     *returns the Keywords of the Node
     * @return keywords
     */
    public String [] getKeywords()
    {
        return keywords;
    }

    /**
     *returns the Left Node of this Node
     * @return left
     */
    public TreeNode getLeft()
    {
        return left;
    }

    /**
     *Returns the Right Node of this Node
     * @return
     */
    public TreeNode getRight()
    {
        return right;
    }

    /**
     *This Method updates the Keywords with new Keywords
     * @param keywords
     */
    public void setKeywords(String[] keywords)
    {
        this.keywords=keywords;
    }

    /**
     *setter for Left Node
     * @param left- Left Node
     */
    public void setLeft(TreeNode left)
    {
        this.left=left;
    }

    /**
     *Setter for Right Node
     * @param right - Right Node
     */
    public void setRight(TreeNode right)
    {
        this.right=right;
    }

    /**
     *Setter for the Leaf
     * @param value- true or false if its a leaf or not
     */
    public void setLeaf(boolean value)
      {
          leaf=value;
          
             
      }

    /**
     *Returns if its a leaf of not
     * @return leaf - value True or False
     */
    public Boolean isLeaf()
    {
       if(this.left==null && this.right==null || leaf==true)
       {
           return true;
       }
       else
           return false;
    }

    /**
     *This is similar to editCursor but 
     * this is for any Node instead of just cursor
     * @param text - new Keywords
     */
    public void setkeyHelper(String text)
    {
        String str="";
        text=text+",";
        String[] temp;
        int count=0;
        for(int i=0;i<text.length();i++)
        {
            char ch=text.charAt(i);
            if(ch==',')
            {
                count++;   
            }
        }
        temp=new String[count];
        int tempcnt=0;
        for(int i=0;i<text.length();i++)
        {
            char ch=text.charAt(i);
            if(ch!=',')
            {
                str=str+ch;  
            }
            else
            {
                temp[tempcnt]=str;
                tempcnt++;
                str="";
            }
        }
        setKeywords(temp);
    }
}
