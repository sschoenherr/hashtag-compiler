/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashtag;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JOptionPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.TabSet;
import javax.swing.text.TabStop;
import javax.swing.text.Utilities;

/**
 *
 * @author stephanie
 */
public class Interfaz extends javax.swing.JFrame {

    /**
     * Creates new form Interfaz
     */
    String FILE_PATH;
    boolean CONTENT_CHANGED; //bandera para ver si hay cambios en el textpane

    public Interfaz() {
        initComponents();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        final StyleContext cont = StyleContext.getDefaultStyleContext();
        final AttributeSet keyword = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.decode("#0000ff"));
        final AttributeSet plain = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
        final AttributeSet comment = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.decode("#969696"));
        final AttributeSet string = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.decode("#ce7b00"));

        DefaultStyledDocument doc = new DefaultStyledDocument() {
            @Override
            public void insertString(int offset, String str, AttributeSet a) throws BadLocationException { //cuando se insertan caracteres.
                super.insertString(offset, str, a);
                String text = getText(0, getLength());
                //int before = findLastNonWordChar(text, offset);
                int before = 0;
                /*if (before < 0) {
                 before = 0;
                 }*/
                //int after = findFirstNonWordChar(text, offset + str.length());
                int after = offset + str.length();
                int wordL = before;
                int wordR = before;

                while (wordR <= after) {
                    if (wordR == after || String.valueOf(text.charAt(wordR)).matches("(\\s)")) {
                        if (text.substring(wordL, wordR).matches(".*(\".*\")")) {
                            setCharacterAttributes(wordL, wordR - wordL, string, false);
                        } else {
                            if (text.substring(wordL, wordR).matches("(\\W)*(int|boolean|char|double|true|false|string|mainbegin|endmain|begin|end|if|else|do|function|return|case|switch|other|not"
                                    + "|while|for|break|print|and|or)")) {
                                setCharacterAttributes(wordL, wordR - wordL, keyword, false);
                            } else {
                                if (text.substring(wordL, wordR).matches("(.*#.*)")) {
                                    setCharacterAttributes(wordL, wordR - wordL, comment, false);
                                } else {
                                    setCharacterAttributes(wordL, wordR - wordL, plain, false);
                                }
                            }
                        }
                        wordL = wordR;
                    }
                    wordR++;
                }
            }

            @Override
            public void remove(int offs, int len) throws BadLocationException { //para cuando se borra algun caracter
                super.remove(offs, len);

                String text = getText(0, getLength());
                int before = findLastNonWordChar(text, offs);
                if (before < 0) {
                    before = 0;
                }
                int after = findFirstNonWordChar(text, offs);
                if (text.substring(before, after).matches(".*(\".*\")")) {
                    setCharacterAttributes(before, after - before, string, false);
                } else {
                    if (text.substring(before, after).matches("(\\W)*(int|boolean|char|double|true|false|string|mainbegin|endmain|begin|end|if|else|do|function|return|case|switch|other|not"
                            + "|while|for|break|print|and|or)")) {
                        setCharacterAttributes(before, after - before, keyword, false);
                    } else {
                        if (text.substring(before, after).matches("(.*#.*)")) {
                            setCharacterAttributes(before, after - before, comment, false);
                        } else {
                            setCharacterAttributes(before, after - before, plain, false);
                        }
                    }
                }
            }
        };

        TabStop[] tabs = new TabStop[20];
        for (int j = 0; j < tabs.length; j++) {
            tabs[j] = new TabStop((j + 1) * 28);
        }

        TabSet tabSet = new TabSet(tabs);
        AttributeSet paraSet = cont.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.TabSet, tabSet);

        this.codeTextPane.setStyledDocument(doc);
        this.codeTextPane.setParagraphAttributes(paraSet, false);
        LinePainter lp = new LinePainter(codeTextPane, Color.decode("#DDE6F3"));
        jsp.setViewportView(codeTextPane);
        LineNumber tln = new LineNumber(codeTextPane);
        tln.setBackground(Color.decode("#E0E0E0"));
        jsp.setRowHeaderView(tln);

        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
            }

            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                setContentChange(true);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                setContentChange(true);
            }
        };

        this.codeTextPane.getDocument().addDocumentListener(documentListener);
        this.codeTextPane.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                status.setText("Linea " + getRow(e.getDot(), (JTextComponent) e.getSource()) + ", Columna " + getColumn(e.getDot(), (JTextComponent) e.getSource()));
            }
        });
        CONTENT_CHANGED = false;
        getContentPane().setBackground(Color.decode("#E0E0E0"));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        runButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        console = new javax.swing.JTextArea();
        saveButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jsp = new javax.swing.JScrollPane();
        codeTextPane = new javax.swing.JTextPane();
        openButton = new javax.swing.JButton();
        status = new javax.swing.JLabel();
        filePathLabel = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Compilador");
        setPreferredSize(new java.awt.Dimension(1000, 660));

        runButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/gui/icons/run.png"))); // NOI18N
        runButton.setBorder(BorderFactory.createEmptyBorder());
        runButton.setFocusPainted(false);
        runButton.setFocusable(false);
        runButton.setPreferredSize(new java.awt.Dimension(33, 33));
        runButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runButtonActionPerformed(evt);
            }
        });

        console.setEditable(false);
        console.setBackground(new java.awt.Color(42, 42, 42));
        console.setColumns(20);
        console.setFont(new java.awt.Font("Lucida Console", 0, 12)); // NOI18N
        console.setForeground(new java.awt.Color(255, 255, 255));
        console.setRows(5);
        console.setToolTipText("");
        jScrollPane3.setViewportView(console);

        saveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/gui/icons/floppy-128.png"))); // NOI18N
        saveButton.setBorder(BorderFactory.createEmptyBorder());
        saveButton.setFocusPainted(false);
        saveButton.setFocusable(false);
        saveButton.setPreferredSize(new java.awt.Dimension(33, 33));
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        codeTextPane.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        codeTextPane.setFont(new java.awt.Font("Consolas", 0, 13)); // NOI18N
        jsp.setViewportView(codeTextPane);

        openButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/gui/icons/open.png"))); // NOI18N
        openButton.setBorder(BorderFactory.createEmptyBorder());
        openButton.setFocusPainted(false);
        openButton.setFocusable(false);
        openButton.setPreferredSize(new java.awt.Dimension(33, 33));
        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButtonActionPerformed(evt);
            }
        });

        status.setBackground(new java.awt.Color(255, 255, 255));
        status.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        status.setText("Linea 1, Columna 1");
        status.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        filePathLabel.setBackground(new java.awt.Color(255, 255, 255));
        filePathLabel.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        filePathLabel.setText("File:");
        filePathLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jMenu1.setText("File");

        openMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openMenuItem.setText("Open file...");
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(openMenuItem);

        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(exitMenuItem);

        menuBar.add(jMenu1);

        jMenu2.setText("Edit");
        menuBar.add(jMenu2);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(openButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(387, 387, 387)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 415, Short.MAX_VALUE)
                                .addComponent(runButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jsp, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(filePathLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(saveButton, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                        .addComponent(runButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(openButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jsp, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(filePathLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(status, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*
     The following are methods used in code above
     */
    private void setContentChange(boolean b) {
        CONTENT_CHANGED = b;
    }

    private int findLastNonWordChar(String text, int index) {
        while (--index >= 0) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
        }
        return index;
    }

    private int findFirstNonWordChar(String text, int index) {
        while (index < text.length()) {
            if (String.valueOf(text.charAt(index)).matches("\\W")) {
                break;
            }
            index++;
        }
        return index;
    }

    private int getRow(int pos, JTextComponent editor) {
        int rn = (pos == 0) ? 1 : 0;
        try {
            int offs = pos;
            while (offs > 0) {
                offs = Utilities.getRowStart(editor, offs) - 1;
                rn++;
            }
        } catch (BadLocationException e) {
            console.setText("Error: " + e.getMessage());
        }
        return rn;
    }

    private int getColumn(int pos, JTextComponent editor) {
        try {
            return pos - Utilities.getRowStart(editor, pos) + 1;
        } catch (BadLocationException e) {
            console.setText("Error: " + e.getMessage());
        }
        return -1;
    }

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        // TODO add your handling code here:
        if (FILE_PATH == null) { //es porque aun no ha abierto un archivo y quiere guardar lo que hay en el textpane...
            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Save As");
            int userSelection = fc.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fc.getSelectedFile();
                FILE_PATH = fileToSave.getAbsolutePath();
                save(FILE_PATH, this.codeTextPane.getText());
                this.filePathLabel.setText("File: " + FILE_PATH);
            }
        } else { //ya hay una referencia de un archivo abierto y quiere guardarlo.
            if (CONTENT_CHANGED) { //si hay cambios, entonces
                int dialogResult = JOptionPane.showConfirmDialog(null, "File will be overwritten. Are you sure?", "Warning", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    save(FILE_PATH, this.codeTextPane.getText());
                    CONTENT_CHANGED = false;
                }
            } else {
                this.console.setText("No changes have been made");
                //this.console.setForeground(Color.decode("#DA4939"));
            }

        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void save(String path, String content) {
        Writer writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(path));
            writer.write(content);
            writer.close();
        } catch (IOException ex) {
            this.console.setText("Error: " + ex.getMessage());
        }
    }
    private void runButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runButtonActionPerformed
        resetComponents();
        try {
            if (this.codeTextPane.getText().isEmpty()) {
                this.console.setText("Please provide a valid source code first.\nTry loading it from a file or write it in the text area above.");
            } else {
                //this.codeTextPane.setText(this.codeTextPane.getText().replaceAll("\t", "    "));
                Parser p = new Parser(new Lexer(new java.io.StringReader(this.codeTextPane.getText()))); //asi no depende del archivo.
                p.parse();
                p.AST.get(0).print("", true);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }//GEN-LAST:event_runButtonActionPerformed

    private void resetComponents() {
        this.console.setText("");
    }
    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
        JFileChooser jf = new JFileChooser();
        if (jf.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            FILE_PATH = jf.getSelectedFile().getPath();
            this.codeTextPane.setText("");
            FileReader fr = null;
            try {
                fr = new FileReader(FILE_PATH);
            } catch (FileNotFoundException ex) {
                this.console.setText("Error: " + ex.getMessage());
            }
            BufferedReader br = new BufferedReader(fr);
            StringBuilder str = new StringBuilder();
            String text;
            try {
                while ((text = br.readLine()) != null) {
                    str.append(text.replaceAll("\\t", "    ")).append("\n");
                }
                str.deleteCharAt(str.length() - 1); //removes empty line at the end.
                this.codeTextPane.setText(str.toString());
                this.filePathLabel.setText("File: " + FILE_PATH);
                CONTENT_CHANGED = false;
            } catch (IOException ex) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_openMenuItemActionPerformed

    private void openButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openButtonActionPerformed
        this.openMenuItemActionPerformed(evt);
    }//GEN-LAST:event_openButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interfaz().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane codeTextPane;
    public static javax.swing.JTextArea console;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JLabel filePathLabel;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jsp;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JButton openButton;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JButton runButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JLabel status;
    // End of variables declaration//GEN-END:variables
}
