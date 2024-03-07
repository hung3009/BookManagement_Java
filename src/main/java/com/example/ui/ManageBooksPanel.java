package com.example.ui;

import com.example.model.Book;
import com.example.model.BookCategory;
import com.example.model.Author;
import com.example.model.Publisher;
import com.example.service.AuthorService;
import com.example.service.BookCategoryService;
import com.example.service.PublisherService;
import com.example.service.BookService;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
import com.example.service.BookService;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class ManageBooksPanel extends JPanel {

    private final BookService bookService;
    private final AuthorService authorService;
    private final PublisherService publisherService;
    private final BookCategoryService bookCategoryService;

    private DefaultTableModel tableModel;
    private JTable bookTable;
    private JTextField titleField;
    private JTextField isbnField;
    private JComboBox<Author> authorComboBox;
    private JComboBox<Publisher> publisherComboBox;
    private JComboBox<BookCategory> bookCategoryComboBox;
    private JTextField numberOfPagesField;
    private JTextField sizeField;
    private JDateChooser publicationDateChooser;
    private JCheckBox isActiveCheckbox;
    private JButton addButton;
    private JButton updateButton;
    private JButton hideButton;
    private JButton showButton;
    private JTextField searchField;
    private JButton searchButton;
    private JComboBox<String> sortComboBox;
    private JButton sortButton;

    public ManageBooksPanel() {
        this.bookService = new BookService();
        this.authorService = new AuthorService();
        this.publisherService = new PublisherService();
        this.bookCategoryService = new BookCategoryService();
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // màn hình chính => border layout
        // titlePanel ==> phía bắc
        // contentPanel ==> trung tâm
        // trong contentPanel có 2 panel nữa là controlPanel chứa các input và các nút thêm sửa xóa
        // cùng với panel centerPanel
        // controlPanel thuộc phía bắc của contentPanel
        // centerPanel ==> trung tâm của contnetPanel
        // trong centerPanel lại có 2 panel nữa 1 cái để chứa chức năng tìm kiếm sắp xếp ở phía bắc
        // bảng jtable ==> trung tâm của centerPanel

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        titlePanel.add(new JLabel("Quản lý Sách"));
        titlePanel.setPreferredSize(new Dimension(0, 50));

        JPanel contenPanel = new JPanel(new BorderLayout());

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createTitledBorder("Danh sách Sách"));

        contenPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel searchAndSortPanel = new JPanel();
        searchAndSortPanel.setLayout(new BoxLayout(searchAndSortPanel, BoxLayout.X_AXIS));
        searchAndSortPanel.setPreferredSize(new Dimension(0, 50));

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());

        JPanel sortPanel = new JPanel();
        sortPanel.setLayout(new FlowLayout());

        searchField = new JTextField(15);
        searchButton = new JButton("Tìm kiếm");
        sortComboBox = new JComboBox<>(new String[]{"A-Z", "Z-A"});
        sortButton = new JButton("Sắp xếp");

        searchPanel.add(new JLabel("Tìm kiếm:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        sortPanel.add(new JLabel("Sắp xếp:"));
        sortPanel.add(sortComboBox);
        sortPanel.add(sortButton);

        searchAndSortPanel.add(searchPanel);
        searchAndSortPanel.add(Box.createHorizontalStrut(20));
        searchAndSortPanel.add(sortPanel);

        centerPanel.add(searchAndSortPanel, BorderLayout.NORTH);

        String[] columnNames = {"Số thứ tự", "ID", "Tiêu đề", "ISBN", "ID Thể loại", "ID Nhà xuất bản", "ID Tác giả",
                "Số trang", "Kích thước", "Ngày xuất bản", "Trạng thái"};
        tableModel = new DefaultTableModel(columnNames, 0);
        bookTable = new JTable(tableModel);
        bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScrollPane = new JScrollPane(bookTable);
        centerPanel.add(tableScrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new BorderLayout());

        contenPanel.add(controlPanel, BorderLayout.NORTH);

        // Panel for input fields (1x3 grid)
        JPanel inputPanel = new JPanel(new GridLayout(1, 3, 30, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Thông tin Sách"));

        JPanel inputLeftPanel = new JPanel();
        inputLeftPanel.setLayout(new GridLayout(3, 2, 0, 10));
        JPanel inputCenterPanel = new JPanel();
        inputCenterPanel.setLayout(new GridLayout(3, 2, 0, 10));
        JPanel inputRightPanel = new JPanel();
        inputRightPanel.setLayout(new GridLayout(3, 2, 0, 10));

        inputPanel.add(inputLeftPanel);
        inputPanel.add(inputCenterPanel);
        inputPanel.add(inputRightPanel);

        titleField = new JTextField(15);
        isbnField = new JTextField(15);
        bookCategoryComboBox = new JComboBox<BookCategory>();
        publisherComboBox = new JComboBox<Publisher>();
        authorComboBox = new JComboBox<Author>();
        numberOfPagesField = new JTextField(15);
        sizeField = new JTextField(15);
        publicationDateChooser = new JDateChooser();
        publicationDateChooser.setDateFormatString("yyyy-MM-dd");
        publicationDateChooser.setPreferredSize(titleField.getPreferredSize());
        isActiveCheckbox = new JCheckBox("Hoạt động");

        JLabel titleLabel = new JLabel("Tiêu đề:");
        JLabel isbnLabel = new JLabel("ISBN:");
        JLabel categoryIDLabel = new JLabel("Thể loại:");
        JLabel publisherIDLabel = new JLabel("Nhà xuất bản:");
        JLabel authorIDLabel = new JLabel("Tác giả:");
        JLabel numberOfPagesLabel = new JLabel("Số trang:");
        JLabel sizeLabel = new JLabel("Kích thước:");
        JLabel publicationDateLabel = new JLabel("Ngày xuất bản:");
        JLabel isActiveLabel = new JLabel("Trạng thái:");

        inputLeftPanel.add(titleLabel);
        inputLeftPanel.add(titleField);
        inputLeftPanel.add(isbnLabel);
        inputLeftPanel.add(isbnField);
        inputLeftPanel.add(categoryIDLabel);
        inputLeftPanel.add(bookCategoryComboBox);


        inputCenterPanel.add(authorIDLabel);
        inputCenterPanel.add(authorComboBox);
        inputCenterPanel.add(publicationDateLabel);
        inputCenterPanel.add(publicationDateChooser);
        inputCenterPanel.add(publisherIDLabel);
        inputCenterPanel.add(publisherComboBox);

        inputRightPanel.add(sizeLabel);
        inputRightPanel.add(sizeField);
        inputRightPanel.add(numberOfPagesLabel);
        inputRightPanel.add(numberOfPagesField);
        inputRightPanel.add(isActiveLabel);
        inputRightPanel.add(isActiveCheckbox);

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Chức năng"));
        addButton = new JButton("Thêm");
        updateButton = new JButton("Cập nhật");
        hideButton = new JButton("Ẩn");
        showButton = new JButton("Hiển thị");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(hideButton);
        buttonPanel.add(showButton);

        // Set action listeners for buttons
        addButton.addActionListener(e -> addBook());
        updateButton.addActionListener(e -> updateBook());
        hideButton.addActionListener(e -> hideBook());
        showButton.addActionListener(e -> showBook());

        // Add inputPanel to controlPanel
        controlPanel.add(inputPanel, BorderLayout.CENTER);
        controlPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Set action listeners for search and sort buttons
        searchButton.addActionListener(e -> searchBooks());
        sortButton.addActionListener(e -> sortBooks());

        // Add selection listener for bookTable
        bookTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = bookTable.getSelectedRow();
            if (selectedRow != -1) {
                populateFields(selectedRow);
            }
        });

        // Add components to the main layout
        add(titlePanel, BorderLayout.NORTH);
        add(contenPanel, BorderLayout.CENTER);
//        add(controlPanel, BorderLayout.NORTH);

        // Load initial data
        loadBooks();
        // Gọi các hàm để nạp dữ liệu vào combobox
        loadAuthors();
        loadPublishers();
        loadBookCategories();
    }

    private void loadBooks() {
        List<Book> books = bookService.getAllBooks();
        updateTable(books);
        clearFields();
    }

    private void loadAuthors() {
        List<Author> authors = authorService.getAllActiveAuthors();
        DefaultComboBoxModel<Author> authorComboBoxModel = new DefaultComboBoxModel<>(authors.toArray(new Author[0]));
        authorComboBox.setModel(authorComboBoxModel);
    }

    private void loadPublishers() {
        List<Publisher> publishers = publisherService.getAllActivePublishers();
        DefaultComboBoxModel<Publisher> publisherComboBoxModel = new DefaultComboBoxModel<>(publishers.toArray(new Publisher[0]));
        publisherComboBox.setModel(publisherComboBoxModel);
    }

    private void loadBookCategories() {
        List<BookCategory> categories = bookCategoryService.getAllActiveCategories();
        DefaultComboBoxModel<BookCategory> categoryComboBoxModel = new DefaultComboBoxModel<>(categories.toArray(new BookCategory[0]));
        bookCategoryComboBox.setModel(categoryComboBoxModel);
    }

    private void addBook() {
        try {
            // Lấy dữ liệu từ các trường nhập
            String title = titleField.getText().trim();
            String isbn = isbnField.getText().trim();
            BookCategory bookCategory = (BookCategory) bookCategoryComboBox.getSelectedItem();
            int categoryID = bookCategory.getCategoryID();
            Publisher publisher = (Publisher) publisherComboBox.getSelectedItem();
            int publisherID = publisher.getPublisherID();
            Author author = (Author) authorComboBox.getSelectedItem();
            int authorID = author.getAuthorID();
            int numberOfPages = Integer.parseInt(numberOfPagesField.getText().trim());
            String size = sizeField.getText().trim();
            java.util.Date publicationDate = publicationDateChooser.getDate();
            boolean isActive = isActiveCheckbox.isSelected();

            // Kiểm tra các trường nhập liệu
            if (!title.isEmpty() && !isbn.isEmpty() && categoryID > 0 && publisherID > 0 && authorID > 0
                    && numberOfPages >= 0 && !size.isEmpty() && publicationDate != null) {

                // Tạo đối tượng Book mới
                Book newBook = new Book(0, title, isbn, categoryID, publisherID, authorID,
                        numberOfPages, size, publicationDate, isActive);

                // Gọi phương thức thêm sách từ service
                if (bookService.addBook(newBook)) {
                    loadBooks();
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể thêm sách.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin sách.", "Lưu ý", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateBook() {
        Book selectedBook = getSelectedBook();
        if (selectedBook != null) {
            try {
                // Lấy dữ liệu từ các trường nhập
                String newTitle = titleField.getText().trim();
                String newIsbn = isbnField.getText().trim();

                BookCategory bookCategory = (BookCategory) bookCategoryComboBox.getSelectedItem();
                int newCategoryID = bookCategory.getCategoryID();
                Publisher publisher = (Publisher) publisherComboBox.getSelectedItem();
                int newPublisherID = publisher.getPublisherID();
                Author author = (Author) authorComboBox.getSelectedItem();
                int newAuthorID = author.getAuthorID();

                int newNumberOfPages = Integer.parseInt(numberOfPagesField.getText().trim());
                String newSize = sizeField.getText().trim();
                java.util.Date newPublicationDate = publicationDateChooser.getDate();
                boolean newIsActive = isActiveCheckbox.isSelected();

                // Kiểm tra các trường nhập liệu
                if (!newTitle.isEmpty() && !newIsbn.isEmpty() && newCategoryID > 0 && newPublisherID > 0 && newAuthorID > 0
                        && newNumberOfPages >= 0 && !newSize.isEmpty() && newPublicationDate != null) {

                    // Tạo đối tượng Book mới
                    Book updatedBook = new Book(selectedBook.getBookID(), newTitle, newIsbn, newCategoryID, newPublisherID,
                            newAuthorID, newNumberOfPages, newSize, newPublicationDate, newIsActive);

                    // Gọi phương thức cập nhật sách từ service
                    if (bookService.updateBook(updatedBook)) {
                        loadBooks();
                    } else {
                        JOptionPane.showMessageDialog(this, "Không thể cập nhật sách.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin sách.", "Lưu ý", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sách để cập nhật.", "Lưu ý", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void hideBook() {
        Book selectedBook = getSelectedBook();
        if (selectedBook != null) {
            // Gọi phương thức ẩn sách từ service
            if (bookService.deactivateBook(selectedBook.getBookID())) {
                loadBooks();
            } else {
                JOptionPane.showMessageDialog(this, "Không thể ẩn sách.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sách để ẩn.", "Lưu ý", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void showBook() {
        Book selectedBook = getSelectedBook();
        if (selectedBook != null) {
            // Gọi phương thức hiển thị sách từ service
            if (bookService.activateBook(selectedBook.getBookID())) {
                loadBooks();
            } else {
                JOptionPane.showMessageDialog(this, "Không thể hiển thị sách.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một sách để hiển thị.", "Lưu ý", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void searchBooks() {
        System.out.println("333");
        String keyword = searchField.getText().trim();
        List<Book> books = bookService.searchBooks(keyword);
        updateTable(books);
    }

    private void sortBooks() {
        System.out.println("444");
        String sortOrder = (String) sortComboBox.getSelectedItem();
        List<Book> books = bookService.getAllBooksSortedByTitle(sortOrder);
        updateTable(books);
    }

    private void populateFields(int selectedRow) {
        titleField.setText((String) bookTable.getValueAt(selectedRow, 2));
        isbnField.setText((String) bookTable.getValueAt(selectedRow, 3));
        numberOfPagesField.setText(String.valueOf(bookTable.getValueAt(selectedRow, 7)));
        sizeField.setText((String) bookTable.getValueAt(selectedRow, 8));

        // Set publication date
        try {
            String dateString = (String) bookTable.getValueAt(selectedRow, 9);
            java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            publicationDateChooser.setDate(date);
        } catch (Exception e) {
            publicationDateChooser.setDate(null);
        }

        // Set active checkbox
        isActiveCheckbox.setSelected((boolean) bookTable.getValueAt(selectedRow, 10));

        // Set selected items in ComboBoxes
        int authorID = (int) bookTable.getValueAt(selectedRow, 6);
        int publisherID = (int) bookTable.getValueAt(selectedRow, 5);
        int categoryID = (int) bookTable.getValueAt(selectedRow, 4);

        // Lấy danh sách tác giả, nhà xuất bản, thể loại từ ComboBoxModel
        DefaultComboBoxModel<BookCategory> categoryComboBoxModel = (DefaultComboBoxModel<BookCategory>) bookCategoryComboBox.getModel();
        DefaultComboBoxModel<Publisher> publisherComboBoxModel = (DefaultComboBoxModel<Publisher>) publisherComboBox.getModel();
        DefaultComboBoxModel<Author> authorComboBoxModel = (DefaultComboBoxModel<Author>) authorComboBox.getModel();

        // Tìm thể loại, nhà xuất bản, tác giả tương ứng từ ComboBoxModel
        BookCategory selectedCategory = findItemByID(categoryComboBoxModel, categoryID);
        Publisher selectedPublisher = findItemByID(publisherComboBoxModel, publisherID);
        Author selectedAuthor = findItemByID(authorComboBoxModel, authorID);

        // Set selected items in ComboBoxes
        bookCategoryComboBox.setSelectedItem(selectedCategory);
        publisherComboBox.setSelectedItem(selectedPublisher);
        authorComboBox.setSelectedItem(selectedAuthor);
    }

    private <T> T findItemByID(DefaultComboBoxModel<T> comboBoxModel, int itemID) {
        for (int i = 0; i < comboBoxModel.getSize(); i++) {
            T item = comboBoxModel.getElementAt(i);
            if (item instanceof BookCategory && ((BookCategory) item).getCategoryID() == itemID) {
                return item;
            } else if (item instanceof Publisher && ((Publisher) item).getPublisherID() == itemID) {
                return item;
            } else if (item instanceof Author && ((Author) item).getAuthorID() == itemID) {
                return item;
            }
        }
        return null;
    }


    private void updateTable(List<Book> books) {
        // Clear previous data
        tableModel.setRowCount(0);

        int i = 0;
        for (Book book : books) {
            i++;
            Object[] rowData = {
                    i,
                    book.getBookID(),
                    book.getTitle(),
                    book.getIsbn(),
                    book.getCategoryID(),
                    book.getPublisherID(),
                    book.getAuthorID(),
                    book.getNumberOfPages(),
                    book.getSize(),
                    new SimpleDateFormat("yyyy-MM-dd").format(book.getPublicationDate()),
                    book.isActive()
            };
            tableModel.addRow(rowData);
        }
    }

    private void clearFields() {
        titleField.setText("");
        isbnField.setText("");
        bookCategoryComboBox.setSelectedItem(null);
        publisherComboBox.setSelectedItem(null);
        authorComboBox.setSelectedItem(null);
        numberOfPagesField.setText("");
        sizeField.setText("");
        publicationDateChooser.setDate(null);
        isActiveCheckbox.setSelected(false);
    }

    private Book getSelectedBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow != -1) {
            int bookID = (int) bookTable.getValueAt(selectedRow, 1);
            String title = (String) bookTable.getValueAt(selectedRow, 2);
            String isbn = (String) bookTable.getValueAt(selectedRow, 3);
            int categoryID = (int) bookTable.getValueAt(selectedRow, 4);
            int publisherID = (int) bookTable.getValueAt(selectedRow, 5);
            int authorID = (int) bookTable.getValueAt(selectedRow, 6);
            int numberOfPages = (int) bookTable.getValueAt(selectedRow, 7);
            String size = (String) bookTable.getValueAt(selectedRow, 8);
            String dateString = (String) bookTable.getValueAt(selectedRow, 9);
            java.util.Date publicationDate;
            try {
                publicationDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            } catch (Exception e) {
                publicationDate = null;
            }
            boolean isActive = (boolean) bookTable.getValueAt(selectedRow, 10);

            return new Book(bookID, title, isbn, categoryID, publisherID, authorID,
                    numberOfPages, size, publicationDate, isActive);
        }
        return null;
    }
}
