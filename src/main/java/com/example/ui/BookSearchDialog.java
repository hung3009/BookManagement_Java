package com.example.ui;

import com.example.model.Book;
import com.example.model.BookBatch;
import com.example.service.BookBatchService;
import com.example.service.BookService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.util.List;

public class BookSearchDialog extends JDialog {

    private final JTable bookTable;
    private final DefaultTableModel bookTableModel;

    private final JTable batchTable;
    private final DefaultTableModel batchTableModel;

    private final BookService bookService;

    private JTextField bookIdTextField;
    private JTextField batchIdTextField;
    private JSpinner quantitySpinner;

    public BookSearchDialog(JFrame parentFrame) {
        super(parentFrame, "Tìm kiếm sách", true);

        this.bookService = new BookService();

        // Initialize book table
        bookTableModel = new DefaultTableModel();
        bookTableModel.addColumn("ID");
        bookTableModel.addColumn("Title");
        bookTableModel.addColumn("ISBN");
        bookTableModel.addColumn("Author");
        bookTableModel.addColumn("Category");
        bookTableModel.addColumn("Publication Date");
        bookTableModel.addColumn("Size");
        bookTable = new JTable(bookTableModel);

        // Initialize batch table
        batchTableModel = new DefaultTableModel();
        batchTableModel.addColumn("Batch ID");
        batchTableModel.addColumn("Book ID");
        batchTableModel.addColumn("Original Quantity");
        batchTableModel.addColumn("Current Quantity");
        batchTableModel.addColumn("Purchase Date");
        batchTableModel.addColumn("Unit Price");
        batchTableModel.addColumn("Receipt Detail ID");
        batchTableModel.addColumn("Storage Location");
        batchTable = new JTable(batchTableModel);

        // Create scroll panes for tables
        JScrollPane bookScrollPane = new JScrollPane(bookTable);
        JScrollPane batchScrollPane = new JScrollPane(batchTable);

        // Create panel for batch table
        JPanel batchPanel = new JPanel(new BorderLayout());
        batchPanel.add(batchScrollPane, BorderLayout.CENTER);
        
        JPanel southRightPanel = new JPanel(new BorderLayout());
        batchPanel.add(southRightPanel, BorderLayout.SOUTH);

        // Create input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2, 0, 10));

        JLabel bookIdLabel = new JLabel("ID Sách:");
        bookIdTextField = new JTextField();
        bookIdTextField.setEditable(false);
        inputPanel.add(bookIdLabel);
        inputPanel.add(bookIdTextField);

        JLabel batchIdLabel = new JLabel("ID Lô Sách:");
        batchIdTextField = new JTextField();
        batchIdTextField.setEditable(false);
        inputPanel.add(batchIdLabel);
        inputPanel.add(batchIdTextField);

        JLabel quantityLabel = new JLabel("Số Lượng:");
        quantitySpinner = new JSpinner();
        inputPanel.add(quantityLabel);
        inputPanel.add(quantitySpinner);
        
        JPanel chooseButtonPanel = new JPanel(new FlowLayout());
        southRightPanel.add(chooseButtonPanel, BorderLayout.SOUTH);

        JButton chooseButton = new JButton("Chọn");
        chooseButtonPanel.add(chooseButton);

        // Attach action listener to the choose button
        chooseButton.addActionListener(e -> handleChooseButtonClick());

        // Add input panel to the batch panel
        southRightPanel.add(inputPanel, BorderLayout.CENTER);

        // Create split pane to display book and batch panels side by side
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, bookScrollPane, batchPanel);
        splitPane.setResizeWeight(0.5); // Set initial size ratio

        // Add split pane to the content pane
        add(splitPane, BorderLayout.CENTER);

        // Attach mouse listener to the book table
        bookTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRowIndex = bookTable.getSelectedRow();

                if (selectedRowIndex != -1) {
                    int selectedBookID = (int) bookTable.getValueAt(selectedRowIndex, 0);

                    // Load data into batch table based on the selected book
                    loadDataToBatchTable(selectedBookID);

                    // Update UI
                    revalidate();
                    repaint();
                }
            }
        });

        // Load data into tables
        loadDataToBookTable();

        // Set the preferred size to make the dialog wider
        setPreferredSize(new Dimension(1200, 600));

        pack();
        setLocationRelativeTo(parentFrame);
    }

    private void loadDataToBookTable() {
        bookTableModel.setRowCount(0); // Clear existing data

        List<Book> books = bookService.getAllBooks();

        for (Book book : books) {
            Object[] rowData = {
                    book.getBookID(),
                    book.getTitle(),
                    book.getIsbn(),
                    book.getAuthorID(),
                    book.getCategoryID(),
                    book.getPublicationDate(),
                    book.getSize()
            };

            bookTableModel.addRow(rowData);
        }
    }

    private void loadDataToBatchTable(int selectedBookId) {
        batchTableModel.setRowCount(0); // Clear existing data

        List<BookBatch> batches = new BookBatchService().getBatchesByBookId(selectedBookId);

        for (BookBatch batch : batches) {
            Object[] rowData = {
                    batch.getLotID(),
                    batch.getBookID(),
                    batch.getQuantityOriginal(),
                    batch.getQuantityCurrent(),
                    batch.getPurchaseDate(),
                    batch.getUnitPrice(),
                    batch.getReceiptDetailID(),
                    batch.getStorageLocation()
            };

            batchTableModel.addRow(rowData);
        }

        // Attach mouse listener to the batch table
        batchTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedBatchRowIndex = batchTable.getSelectedRow();

                if (selectedBatchRowIndex != -1) {
                    int selectedBatchID = (int) batchTable.getValueAt(selectedBatchRowIndex, 0);
                    int selectedBookID = (int) batchTable.getValueAt(selectedBatchRowIndex, 1);
                    int currentQuantity = (int) batchTable.getValueAt(selectedBatchRowIndex, 3);

                    // Set values to input fields
                    bookIdTextField.setText(String.valueOf(selectedBookID));
                    batchIdTextField.setText(String.valueOf(selectedBatchID));

                    // Set default value for Spinner and limit max value
                    SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, currentQuantity, 1);
                    quantitySpinner.setModel(spinnerModel);
                }
            }
        });
    }

    private void handleChooseButtonClick() {
        int selectedBatchRowIndex = batchTable.getSelectedRow();

        if (selectedBatchRowIndex != -1) {
            int selectedBookID = (int) batchTable.getValueAt(selectedBatchRowIndex, 1);
            int currentQuantity = (int) batchTable.getValueAt(selectedBatchRowIndex, 3);

            // Set values to input fields
            bookIdTextField.setText(String.valueOf(selectedBookID));
            batchIdTextField.setText(String.valueOf(batchTable.getValueAt(selectedBatchRowIndex, 0)));

            // Set default value for Spinner and limit max value
//            quantitySpinner.setModel(new SpinnerNumberModel(1, 1, currentQuantity, 1));

            // Check if the user clicked OK
            if (!bookIdTextField.getText().isEmpty() && !batchIdTextField.getText().isEmpty()) {
                // Set values to static variables
                ManageOrdersPanel.bookBatchSelected = new BookBatch(
                        (int) batchTable.getValueAt(selectedBatchRowIndex, 0),
                        selectedBookID,
                        (int) batchTable.getValueAt(selectedBatchRowIndex, 2),
                        currentQuantity,
                        (Date) batchTable.getValueAt(selectedBatchRowIndex, 4),
                        (int) batchTable.getValueAt(selectedBatchRowIndex, 5),
                        (int) batchTable.getValueAt(selectedBatchRowIndex, 6),
                        (String) batchTable.getValueAt(selectedBatchRowIndex, 7)
                );
                try {
                	quantitySpinner.commitEdit();
				} catch (Exception e) {
					
				}
                ManageOrdersPanel.quantityBookSelected = (Integer) quantitySpinner.getValue();

                // Close the dialog
                setVisible(false);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một lô sách để đặt hàng.", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }





}
