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

import com.fariseducation.Data.ObservedData.DataObserver;
import com.fariseducation.Data.ObservedData.ObservedGeneric;
import com.fariseducation.UIBase.UIComponent;

@SuppressWarnings("rawtypes")
public class UITextField extends UIComponent implements DataObserver {
    private JTextComponent textField;
    private boolean isFocused;
    private ObservedGeneric text;
    private DocumentListener listener;
    private boolean userHasInteracted;

    public UITextField(ObservedGeneric text, boolean multiLine) {
        this.text = text;
        this.text.addObserver(this);
        System.out.println("UITFI: " + text.stringObservers());
        System.out.println("UITFIO: " + text);

        this.userHasInteracted = true;
        build(multiLine);
        updateText();

        this.textField.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent e) {
                UITextField.this.isFocused = true;
                checkFirstInteraction();
            }
            @Override
            public void focusLost(FocusEvent e) {
                UITextField.this.isFocused = false;
            }
        });
    }
    public UITextField(String defaultText, boolean multiLine) {
        this.text = new ObservedGeneric<String>(defaultText);
        this.userHasInteracted = false;
        build(multiLine);
        updateText();

        this.textField.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent e) {
                UITextField.this.isFocused = true;
                checkFirstInteraction();
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
                checkFirstInteraction();

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

    public void setFocusable(boolean focusable) {
        this.textField.setFocusable(focusable);
    }

    protected void build(boolean multiLine) {
        if(multiLine) {
            JTextArea textArea = new JTextArea();
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);

            this.textField = textArea;
        }
        else this.textField = new JTextField();
    }

    protected void updateText() {
        if(!this.isFocused) this.textField.setText(this.text.getVal().toString());
    }

    private void checkFirstInteraction() {
        if(!this.userHasInteracted) this.textField.setText("");
        this.userHasInteracted = true;
    }

    @Override
    protected Component getAWTComponent() {
        return this.textField;
    }
    @Override
    public void updateAfterDataChange() {
        if(!this.isFocused || true) updateText();
    }
}
