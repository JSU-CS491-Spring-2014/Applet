
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;


/**
XMLTreeModel extends DefaultTreeModel, it contains methods that allow
for the easy manipulation of the TreeModel data and its nodes. 
 */

/**
 * XMLTreeModel extends DefaultTreeModel, it contains methods that allow
 * for the easy manipulation of the TreeModel data and its nodes. 
 */
public class XMLTreeModel extends DefaultTreeModel {

    private final int NODE_WIDTH = 260;
    private final int BUF_SPACE = 80;
    
    XMLTreeNode r;
    private int xMax, yMax;

    /**
     * Constructor creates a new XMLTreeModel.
     * @param root sets the root of this model
     */
    public XMLTreeModel(XMLTreeNode root) {
        super(root);
        r = root;
        xMax = 0;
        yMax = 0;
        setX(r);
        setY(r);
        yMax = yMax + BUF_SPACE;
    }

    /**
     * Resets the x and y values for the root node
     */
    public void resetXY() {
        yMax = 0;
        xMax = 0;
        setX(r);
        setY(r);
    }

    /**
     * Sets the X value for each XMLTreeNode in this TreeModel
     */
    public void setX(XMLTreeNode ro) {
        //The root is set to 0
        if (ro.isRoot()) {
            ro.setX(0);
        } //all other nodes are shifted right based on their distance from the node times the total space between nodes
        else {
            ro.setX(ro.getLevel() * (NODE_WIDTH + BUF_SPACE));
            xMax = NODE_WIDTH + BUF_SPACE + 10 + ro.getLevel() * (NODE_WIDTH + BUF_SPACE + NODE_WIDTH);
        }
        for (int i = 0; i < ro.getChildCount(); i++) {
            setX((XMLTreeNode) ro.getChildAt(i));
        }
    }

    /**
     * Sets the y value for each leaf XMLTreeNode in this TreeModel
     */
    public void setY(XMLTreeNode ro) {
        //go to children first
        if (!ro.isLeaf()) {
            for (int i = 0; i < ro.getChildCount(); i++) {
                setY((XMLTreeNode) ro.getChildAt(i));
            }
            calY(ro);
        } else {
            //heigth of the clause plus, small box, plus conj space
            ro.setY(yMax + 40);
            //yMax += ro.getClause().getH() + 18 + 10;
            yMax += 118;
        }
    }

    /**
     * Calculates the y value for non-leaf XMLTreeNodes	
     */
    public void calY(XMLTreeNode ro) {
        int fC = ((XMLTreeNode) ro.getFirstChild()).getY();
        int lC = ((XMLTreeNode) ro.getLastChild()).getY();
        ro.setY((fC + lC) / 2);
    }

    /**
     * @param newNode		the new parent node of all the nodes contained in groupNodes[]
     * @param groupNodes	an array of the nodes that are to grouped and made children of the newNode
     */
    public void groupNodes(XMLTreeNode newNode, XMLTreeNode[] groupNodes) {
        //Still need to get the right attributes for the newNode,
        //  have to pull out the right data from the groupNodes[]

        //Get the parent of the first node in the groupNodes[]
        XMLTreeNode parent = (XMLTreeNode) groupNodes[0].getParent();

        //Get the index of the first node and number of children
        int index = parent.getIndex(groupNodes[0]);
        int numInGroup = groupNodes.length;

        //Remove each node in groupNodes[] from parent
        for (int i = 0; i < numInGroup; i++) {
            ((DefaultMutableTreeNode) parent).remove(index);
        }

        //add all the nodes from groupNodes[] to newNode as children
        for (int i = 0; i < numInGroup; i++) {
            newNode.add(groupNodes[i]);
        }

        ((DefaultMutableTreeNode) parent).insert((DefaultMutableTreeNode) newNode, index);
    }
    
    /**Splits a single node into two separate nodes by creating a new XMLTreeNode with the same chapter
     * and verse as the selectedNode, and inserting the new node into the selectedNode's parent.
     * @param selectedNode		original node that will be split
     * @param dataSelected		the data for the selectedNode after the split has occurred
     * @param newData			the data for the new XMLTreeNode
     * @param newConj			the conjunction for the new XMLTreeNode
     */
    public void split(XMLTreeNode selectedNode, String dataSelected){
        
        int modifier = dataSelected.lastIndexOf(":")+2;//manditory shift to correct for verse and conjunction
        String newConj = "x";//selectedText.substring(0,selectedText.indexOf(' '));//AiDS
        int tCurser = selectedNode.getClause().getJTextPane().getCaretPosition() + modifier;//finds the location of the Caret/cursor for split

        if (dataSelected.substring(tCurser - 1, tCurser).equals(" ") || dataSelected.substring(tCurser, tCurser + 1).equals(" "))
        {
            String firstData = dataSelected.substring(modifier,tCurser);//AiDS
            String secondData = dataSelected.substring(tCurser);//AiDS
            //make new node using the same chapter and verse as the selected node, but using the newData and newConj
            XMLTreeNode secondNode = new XMLTreeNode(new Clause(secondData, newConj, selectedNode.getChap(), selectedNode.getVrse()));
            //set the data for the new node
            secondNode.setData(secondData);
            DiscourseAnalysisApplet.getXMLTreeModel().setNodeData(secondNode, secondData);
            
            //Set data of the original node
            selectedNode.setData(firstData);
            selectedNode.getClause().setTextArea(firstData);
            DiscourseAnalysisApplet.getXMLTreeModel().setNodeData(selectedNode, firstData);
            
            //get the parent of the selected node
            XMLTreeNode parent = (XMLTreeNode) selectedNode.getParent();
            
            //get the index for the selected node
            int x = parent.getIndex(selectedNode);
            //add the newNode right after the selectedNode
            parent.insert(secondNode, x + 1);
            NodePanel secondSplitNode = new NodePanel(secondNode, (parent.getX() + 40), parent.getY());

            System.out.println("first clause:   " + firstData);//AiDS stub
            System.out.println("second clause:  " + secondData);//AiDS stub

            DiscourseAnalysisApplet.getXMLTreeModel().insertNodeInto(secondNode, DiscourseAnalysisApplet.getRoot(), x + 1);     

            secondSplitNode.setBounds(1, 0 + 0, 2500, 2500);
            DiscourseAnalysisApplet.getNodePanel().add(secondSplitNode);

            resetXY(); 
        }
        else
            JOptionPane.showMessageDialog(null,"You can not split this node in the middle of a word.");   
    }

    /**Removes the selected node only if it is not a leaf node without deleting the children of the selected node.
     * Note: If you want to delete a leaf node, use the merge method
     * @param selectedNode		the node that will be removed
     */
    /*static public void remove(XMLTreeNode selectedNode) {
        //Not sure if we should allow the user to delete a leaf node
        // it will contain the raw data from the parsed file

        //Get the parent of the selected node
        XMLTreeNode parent = (XMLTreeNode) selectedNode.getParent();

        //If the node is not a leaf
        if (!selectedNode.isLeaf()) {
            //Get the children of the selected node
            XMLTreeNode[] children = new XMLTreeNode[selectedNode.getChildCount()];
            //get the index of the selected node
            int index = parent.getIndex(selectedNode);

            for (int i = 0; i < selectedNode.getChildCount(); i++) {
                //add the children of the selected node to the array
                children[i] = (XMLTreeNode) selectedNode.getChildAt(i);
            }

            //remove the selected node from its parent
            parent.remove(selectedNode);

            //add the selectedNode's children to its parent starting at index
            for (int i = 0; i < children.length; i++) {
                parent.insert(children[i], index + i);
            }
        }
    }*/

    /**Based on the boolean flag 'mergeDown' this method will merge the selectedNode with the node right before or right after it.
     * Note: Chapter and verse of the merged node will be the same as the selectedNode
     * @param selectedNode		node to be merged
     * @param conj				the desired conjuction of the merged nodes
     * @param newData				the desired data of the merged nodes
     * @param mergeDown		if true: will merge the selectedNode with the one right below it, else: will merge with the one right above the selectedNode
     */
    public void merge(XMLTreeNode selectedNode, String conj, String newData, boolean mergeDown) {//This method assumes that the chapter and verse of the 2 nodes are the same

        //getParent
        XMLTreeNode parent = (XMLTreeNode) selectedNode.getParent();
        //get index of the selected Node 
        int index = parent.getIndex(selectedNode);

        //If merge down then Merge
        if (mergeDown) {
            try {
                //Update the selected node
                selectedNode.setConj(conj);
                selectedNode.setData(newData);
                parent.remove((XMLTreeNode) parent.getChildAt(index + 1));
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        } else //Merge Up
        {
            try {
                //Update the selected node
                selectedNode.setConj(conj);
                selectedNode.setData(newData);
                parent.remove((XMLTreeNode) parent.getChildAt(index - 1));
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }
    }

    //Getters: for getting the information from the Clause object of the selected XMLTreeNode
    /**
     * @param sNode selected node
     * @return the data from the Clause object stored in the selected XMLTreeNode
     */
    public String getNodeData(XMLTreeNode sNode) {
        return sNode.getData();
    }

    /**
     * @param sNode selected node
     * @return the conjunction from the Clause object stored in the selected XMLTreeNode
     */
    public String getNodeConj(XMLTreeNode sNode) {
        return sNode.getConj();
    }

    /**
     * @param sNode selected node
     * @return the chapter from the Clause object stored in the selected XMLTreeNode
     */
    public String getNodeChap(XMLTreeNode sNode) {
        return sNode.getChap();
    }

    /**
     * @param sNode selected node
     * @return the verse from the Clause object stored in the selected XMLTreeNode
     */
    public String getNodeVrse(XMLTreeNode sNode) {
        return sNode.getVrse();
    }

    /**
     * @param sNode selected node
     * @return the x value from the Clause object stored in the selected XMLTreeNode
     */
    public int getNodeX(XMLTreeNode sNode) {
        return sNode.getX();
    }

    /**
     * @param sNode selected node
     * @return the y value from the Clause object stored in the selected XMLTreeNode
     */
    public int getNodeY(XMLTreeNode sNode) {
        return sNode.getY();
    }

    public int getXMax() {
        return xMax;
    }

    public int getYMax() {
        return yMax;
    }
    //Setters: for setting the information from the Clause object of the selected XMLTreeNode

    /**
     * @param sNode selected node
     * @param info	sets the data in the Clause object stored in the selected XMLTreeNode equal to info
     */
    static public void setNodeData(XMLTreeNode sNode, String info) {
        sNode.setData(info);
    }

    /**
     * @param sNode selected node
     * @param info	sets the conjunction in the Clause object stored in the selected XMLTreeNode equal to info
     */
    public void setNodeConj(XMLTreeNode sNode, String info) {
        sNode.setData(info);
    }

    /**
     * @param sNode selected node
     * @param info	sets the chapter in the Clause object stored in the selected XMLTreeNode equal to info
     */
    public void setNodeChap(XMLTreeNode sNode, String info) {
        sNode.setData(info);
    }

    /**
     * @param sNode selected node
     * @param info	sets the verse in the Clause object stored in the selected XMLTreeNode equal to info
     */
    public void setNodeVrse(XMLTreeNode sNode, String info) {
        sNode.setData(info);
    }

    /**
     * @param sNode selected node
     * @param info	sets the value of x in the Clause object stored in the selected XMLTreeNode equal to info
     */
    public void setNodeX(XMLTreeNode sNode, int info) {
        sNode.setX(info);
    }

    /**
     * @param sNode selected node
     * @param info	sets the value of y in the Clause object stored in the selected XMLTreeNode equal to info
     */
    public void setNodeY(XMLTreeNode sNode, int info) {
        sNode.setY(info);
    }
}