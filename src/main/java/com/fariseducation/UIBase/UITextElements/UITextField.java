package com.fariseducation.UIBase.UITextElements;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.function.Consumer;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import com.fariseducation.UIBase.UIComponent;

public class UITextField extends UIComponent {
    private JTextComponent textField;
    private boolean isFocused;
    private String text;
    private DocumentListener listener;

    public UITextField(String defaultText, boolean multiLine) {
        this.text = defaultText;
        build(multiLine);
        updateText();

        this.textField.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent e) {
                UITextField.this.isFocused = true;
            }
            @Override
            public void focusLost(FocusEvent e) {
                UITextField.this.isFocused = false;
            }
        });
    }
    public UITextField(boolean multiLine) {
        this("", multiLine);
    }
    public UITextField onFocusGained(Runnable action) {
        this.textField.removeFocusListener(this.textField.getFocusListeners()[0]);

        this.textField.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent e) {
                UITextField.this.isFocused = true;

                action.run();
            }
            @Override
            public void focusLost(FocusEvent e) {
                UITextField.this.isFocused = false;
            }
        });

        return this;
    }
    public UITextField onFocusLost(Runnable action) {
        this.textField.removeFocusListener(this.textField.getFocusListeners()[0]);

        this.textField.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent e) {
                UITextField.this.isFocused = true;
            }
            @Override
            public void focusLost(FocusEvent e) {
                UITextField.this.isFocused = false;

                action.run();
            }
        });

        return this;
    }
    public UITextField onTyping(Consumer<String> action) {
        if(this.textField instanceof JTextField) {
            ((JTextField)(this.textField)).getDocument().removeDocumentListener(this.listener);

            ((JTextField)(this.textField)).getDocument().addDocumentListener(new DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    action.accept(UITextField.this.textField.getText()); 
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    action.accept(UITextField.this.textField.getText()); 
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    action.accept(UITextField.this.textField.getText()); 
                }
            });
        }
        if(this.textField instanceof JTextArea) {
            ((JTextArea)(this.textField)).getDocument().removeDocumentListener(this.listener);

            ((JTextArea)(this.textField)).getDocument().addDocumentListener(new DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    action.accept(UITextField.this.textField.getText()); 
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    action.accept(UITextField.this.textField.getText()); 
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    action.accept(UITextField.this.textField.getText()); 
                }
            });
        }
        
        return this;
    }

    public boolean getFocusState() {
        return this.isFocused;
    }

    protected void build(boolean multiLine) {
        if(multiLine) this.textField = new JTextArea();
        else this.textField = new JTextField();
    }

    protected void updateText() {
        this.textField.setText(this.text);
        this.textField.repaint();
    }

    @Override
    protected Component getAWTComponent() {
        return this.textField;
    }
}
