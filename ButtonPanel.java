import javax.swing.text.JTextComponent;
import org.w3c.dom.Node;


/**
 * This is the new ButtonPanel. It no longer contains two ClausePanels. The UI
 * looks the same, but it was built differently. I used the NetBeans IDE GUI
 * builder. The associated ButtonPanel.form is included.
 */
public class ButtonPanel extends javax.swing.JPanel {

    private XMLTreeNode firstNodeSelection;     // Holds the Node used in the firstNodeSelection box
    private XMLTreeNode lastNodeSelection;      // Holds the Node used in the lastNodeSelection box

    /**
     * Toggle the enabled property for the buttons.
     *
     * @param enabled the value to assign to the enabled attribute of the
     * buttons
     */
    public final void setButtonsEnabled(boolean enabled) {
        //Button setting to disabled (some of these are initially disabled because of no functionality
        cmdOkay.setEnabled(enabled);
        cmdGroup.setEnabled(enabled);
        cmdSplit.setEnabled(enabled);
        cmdTreeModel.setEnabled(enabled);
    }

    /**
     * Creates new form ButtonPanel
     */
    public ButtonPanel() {
        initComponents();
        Clause temp = null;
        setButtonsEnabled(false);
    }

    /**
     * Sets the Combo Box with the correct number of Nodes to be grouped? That's
     * what their comment said. In reality, it lists 1 through n where n is the
     * total number of nodes minus the index of the node you select. The lower
     * down the tree you select a node, the smaller n will be.
     */
    public void populateComboBox() {
        comComboBox.removeAllItems(); // I added this. The previous would just keep adding them for forever.
        
        //Assigns a combobox to every node.
        try {
            XMLTreeNode parent = (XMLTreeNode) DiscourseAnalysisApplet.nodePanel.getSelected().getParent(); // Gets the parent. You read from left to right just like normal. Nodes to the right are child nodes.
            int currentI = parent.getIndex(DiscourseAnalysisApplet.nodePanel.getSelected()) + 1;
            int totalC = parent.getChildCount();
            for (int i = 0; i < totalC - currentI; i++) {
                comComboBox.addItem(new Integer(i + 1));
            }
        } catch (java.lang.NullPointerException npe) {
        }
    }
    
    /**
     * Returns the Node represented in the top box.
     *
     * @return the Node in the top box
     */
    public XMLTreeNode getFirstNodeSelection() {
        return firstNodeSelection;
    }

    /**
     * Returns the Node represented in the bottom box.
     *
     * @return the Node in the bottom box
     */
    public XMLTreeNode getLastNodeSelection() {
        return lastNodeSelection;
    }

    /**
     * Returns the number associated with the selected value in comComboBox.
     *
     * @return an int representing the selected value
     */
    public int getComboBoxSelectedIndex() {
        return comComboBox.getSelectedIndex();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmdOkay = new javax.swing.JButton();
        cmdCancel = new javax.swing.JButton();
        cmdTreeModel = new javax.swing.JButton();
        cmdSplit = new javax.swing.JButton();
        cmdEdit = new javax.swing.JButton();
        cmdRemove = new javax.swing.JButton();
        cmdGroup = new javax.swing.JButton();
        comComboBox = new javax.swing.JComboBox();

        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setFocusable(false);

        cmdOkay.setText("Okay");
        cmdOkay.setFocusable(false);
        cmdOkay.setNextFocusableComponent(cmdCancel);
        cmdOkay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdOkayActionPerformed(evt);
            }
        });

        cmdCancel.setText("Cancel");
        cmdCancel.setFocusable(false);
        cmdCancel.setNextFocusableComponent(cmdTreeModel);
        cmdCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCancelActionPerformed(evt);
            }
        });

        cmdTreeModel.setText("Tree Model");
        cmdTreeModel.setFocusable(false);
        cmdTreeModel.setNextFocusableComponent(cmdSplit);

        cmdSplit.setText("Split");
        cmdSplit.setFocusable(false);
        cmdSplit.setNextFocusableComponent(cmdEdit);
        cmdSplit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSplitActionPerformed(evt);
            }
        });

        cmdEdit.setText("Edit");
        cmdEdit.setFocusable(false);
        cmdEdit.setNextFocusableComponent(cmdRemove);
        cmdEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdEditActionPerformed(evt);
            }
        });

        cmdRemove.setText("Remove");
        cmdRemove.setFocusable(false);
        cmdRemove.setNextFocusableComponent(cmdGroup);
        cmdRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRemoveActionPerformed(evt);
            }
        });

        cmdGroup.setText("Group");
        cmdGroup.setFocusable(false);
        cmdGroup.setNextFocusableComponent(comComboBox);

        comComboBox.setFocusable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cmdGroup, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmdEdit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmdTreeModel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmdOkay, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmdCancel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdSplit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdRemove, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comComboBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdOkay)
                    .addComponent(cmdCancel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdTreeModel)
                    .addComponent(cmdSplit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdEdit)
                    .addComponent(cmdRemove))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdGroup)
                    .addComponent(comComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmdCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCancelActionPerformed
        cmdOkay.setEnabled(false);
        cmdEdit.setEnabled(true);
        workingClause.chooseFocus(false);
        workingClause.setTextArea(revertText);
        DiscourseAnalysisApplet.nodePanel.hideButtonPanel();
        
    }//GEN-LAST:event_cmdCancelActionPerformed

    
    //Action performed on "Okay" button clicking.
    private void cmdOkayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdOkayActionPerformed
        //This will commit edits made by user in the text area to the actual node.
        //Note that this will change the node in the program but will not be saved permenantly
        //until the saveXML is called.
        editedText = "";
        editedText = workingClause.getTextArea();
        XMLTreeModel.setNodeData(workingNode, editedText);
        //This part will reset the node area to a "Dormant" state and reset the buttons.
        DiscourseAnalysisApplet.nodePanel.hideButtonPanel();
        cmdOkay.setEnabled(false);
        cmdEdit.setEnabled(true);
        workingClause.chooseFocus(false);
    }//GEN-LAST:event_cmdOkayActionPerformed

    //This may need to be re-worked at some point, however, this allows the button panel actions
    //to get the informatin on the clause and node.
    //In Clause, when a area is clicked, it will send the information to this function
    public void associateClauseAndNode(Clause c, XMLTreeNode n) {
        workingClause = c;
        workingNode = n;
        revertText = workingClause.getTextArea();
    }
    private String revertText;
    private String editedText;
    private String selectedText;
    private Clause workingClause;
    private XMLTreeNode workingNode;
    
    public void editEnable(){
      workingClause.chooseFocus(true);
      cmdOkay.setEnabled(true);
      cmdEdit.setEnabled(false);
      cmdSplit.setEnabled(true);//AiDS
    }
    //Action performed when "Edit" button clicked.
    private void cmdEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdEditActionPerformed
      //Enable text area, disable the edit button, and enable the okay button.
      workingClause.chooseFocus(true);
      cmdOkay.setEnabled(true);
      cmdEdit.setEnabled(false);
      cmdSplit.setEnabled(true);//AiDS
      
    }//GEN-LAST:event_cmdEditActionPerformed

    //Remove button, not currently working.
    private void cmdRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRemoveActionPerformed
       
    }//GEN-LAST:event_cmdRemoveActionPerformed

    //AiDS
    //Action Performed by split button
    private void cmdSplitActionPerformed(java.awt.event.ActionEvent evt){
        workingClause.chooseFocus(true);
        selectedText = workingClause.toString();
        DiscourseAnalysisApplet.getXMLTreeModel().split(workingNode, selectedText);
        //DiscourseAnalysisApplet.nodePanel.hideButtonPanel();
        cmdOkay.setEnabled(false);
        cmdEdit.setEnabled(true);
        cmdSplit.setEnabled(false);
        workingClause.chooseFocus(false);
        //DiscourseAnalysisApplet.makeNodes(DiscourseAnalysisApplet.root, parent.workingNode.getParent());
        /*
        XMLTreeModel newTree = new XMLTreeModel(DiscourseAnalysisApplet.root);
        newTree.setRoot(DiscourseAnalysisApplet.root);
        newTree.reload();
        JTreePanel newJTreePanel = new JTreePanel(newTree ,DiscourseAnalysisApplet.nodePanel);
        DiscourseAnalysisApplet.setJTreePanel(newJTreePanel);
        newJTreePanel.setTreeModel(newTree);
        newJTreePanel.update();
        newTree.reload(workingNode);
        */
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdCancel;
    private javax.swing.JButton cmdEdit;
    private javax.swing.JButton cmdGroup;
    private javax.swing.JButton cmdOkay;
    private javax.swing.JButton cmdRemove;
    private javax.swing.JButton cmdSplit;
    private javax.swing.JButton cmdTreeModel;
    private javax.swing.JComboBox comComboBox;
    // End of variables declaration//GEN-END:variables
}
