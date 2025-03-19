import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField nameField, quantityField, priceField;

    public Main() {
        frame = new JFrame("Inventory Management System");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Table setup
        tableModel = new DefaultTableModel(new String[]{"Item Name", "Quantity", "Price"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 2, 10, 10));

        formPanel.add(new JLabel("Item Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        formPanel.add(quantityField);

        formPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        formPanel.add(priceField);

        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Item");
        JButton updateButton = new JButton("Update Item");
        JButton deleteButton = new JButton("Delete Item");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        // Adding components to frame
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(formPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Add button action
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItem();
            }
        });

        // Update button action
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateItem();
            }
        });

        // Delete button action
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteItem();
            }
        });

        frame.setVisible(true);
    }

    // Add item to inventory
    private void addItem() {
        String name = nameField.getText();
        String quantity = quantityField.getText();
        String price = priceField.getText();

        if (name.isEmpty() || quantity.isEmpty() || price.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        tableModel.addRow(new Object[]{name, quantity, price});
        clearFields();
    }

    // Update selected item
    private void updateItem() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an item to update!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        tableModel.setValueAt(nameField.getText(), selectedRow, 0);
        tableModel.setValueAt(quantityField.getText(), selectedRow, 1);
        tableModel.setValueAt(priceField.getText(), selectedRow, 2);
        clearFields();
    }

    // Delete selected item
    private void deleteItem() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an item to delete!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        tableModel.removeRow(selectedRow);
        clearFields();
    }

    // Clear input fields
    private void clearFields() {
        nameField.setText("");
        quantityField.setText("");
        priceField.setText("");
    }

    public static void main(String[] args) {
        new Main();
    }
}
