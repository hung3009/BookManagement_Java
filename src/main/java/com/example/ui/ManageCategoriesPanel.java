package com.example.ui;

import com.example.model.BookCategory;
import com.example.service.BookCategoryService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ManageCategoriesPanel extends JPanel {

    private final BookCategoryService bookCategoryService;

    private DefaultTableModel tableModel;
    private JTable categoryTable;
    private JTextField categoryNameField;
    private JButton addButton;
    private JButton updateButton;
    private JButton hideButton;
    private JButton showButton;
    private JTextField searchField;
    private JButton searchButton;
    private JComboBox<String> sortComboBox;
    private JButton sortButton;

    public ManageCategoriesPanel() {
        this.bookCategoryService = new BookCategoryService();
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        titlePanel.add(new JLabel("Quản lý Thể loại sách"));

        // Panel trung tâm
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createTitledBorder("Danh mục sách"));
        
     // Panel tìm kiếm và sắp xếp
        JPanel searchAndSortPanel = new JPanel();
        searchAndSortPanel.setLayout(new BoxLayout(searchAndSortPanel, BoxLayout.X_AXIS));
        searchAndSortPanel.setPreferredSize(new Dimension(0, 70));

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

        // Thêm khoảng trống giữa hai panel
        searchAndSortPanel.add(searchPanel);
        searchAndSortPanel.add(Box.createHorizontalStrut(20)); // Khoảng trống 20 pixels
        searchAndSortPanel.add(sortPanel);
        
        centerPanel.add(searchAndSortPanel, BorderLayout.NORTH);
        
     // Tạo bảng JTable thuộc panel trung tâm
        String[] columnNames = {"Số thứ tự", "ID", "Tên danh mục", "Trạng thái"};
        tableModel = new DefaultTableModel(columnNames, 0);
        categoryTable = new JTable(tableModel);
        categoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScrollPane = new JScrollPane(categoryTable);
        centerPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Panel chứa input và nút điều khiển
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.setPreferredSize(new Dimension(0, 150));

        categoryNameField = new JTextField(15);
        addButton = new JButton("Thêm");
        updateButton = new JButton("Cập nhật");
        hideButton = new JButton("Ẩn");
        showButton = new JButton("Hiển thị");
        

        controlPanel.add(new JLabel("Tên danh mục:"));
        controlPanel.add(categoryNameField);
        controlPanel.add(addButton);
        controlPanel.add(updateButton);
        controlPanel.add(hideButton);
        controlPanel.add(showButton);
        

        // Xác định sự kiện cho nút thêm
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCategory();
            }
        });

        // Xác định sự kiện cho nút cập nhật
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCategory();
            }
        });

        // Xác định sự kiện cho nút ẩn
        hideButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hideCategory();
            }
        });

        // Xác định sự kiện cho nút hiển thị
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCategory();
            }
        });

        // Xác định sự kiện cho nút tìm kiếm
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchCategories();
            }
        });

        // Xác định sự kiện cho nút sắp xếp
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortCategories();
            }
        });
        
     // Thêm sự kiện lắng nghe cho việc chọn một dòng trong bảng
        categoryTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = categoryTable.getSelectedRow();
            if (selectedRow != -1) {
                String categoryName = (String) categoryTable.getValueAt(selectedRow, 2);
                categoryNameField.setText(categoryName);
            }
        });

        // Thêm các panel vào màn hình quản lý danh mục sách
        add(titlePanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        // Load danh sách danh mục sách từ CSDL
        loadCategories();
    }

    private void loadCategories() {
        List<BookCategory> categories = bookCategoryService.getAllCategories();
        updateTable(categories);
        clearFields();
    }

    private void addCategory() {
        String categoryName = categoryNameField.getText().trim();
        if (!categoryName.isEmpty()) {
            BookCategory newCategory = new BookCategory(0, categoryName, true);
            if (bookCategoryService.addCategory(newCategory)) {
                loadCategories();
            } else {
                JOptionPane.showMessageDialog(this, "Không thể thêm danh mục sách.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên danh mục sách.", "Lưu ý", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateCategory() {
        BookCategory selectedCategory = getSelectedCategory();
        if (selectedCategory != null) {
            String newName = categoryNameField.getText().trim();
            if (!newName.isEmpty()) {
                selectedCategory.setCategoryName(newName);
                if (bookCategoryService.updateCategory(selectedCategory)) {
                    loadCategories();
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể cập nhật danh mục sách.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên mới cho danh mục sách.", "Lưu ý", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một danh mục sách để cập nhật.", "Lưu ý", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void hideCategory() {
        BookCategory selectedCategory = getSelectedCategory();
        if (selectedCategory != null) {
            selectedCategory.setActive(false);
            if (bookCategoryService.updateCategory(selectedCategory)) {
                loadCategories();
            } else {
                JOptionPane.showMessageDialog(this, "Không thể ẩn danh mục sách.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một danh mục sách để ẩn.", "Lưu ý", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void showCategory() {
        BookCategory selectedCategory = getSelectedCategory();
        if (selectedCategory != null) {
            selectedCategory.setActive(true);
            if (bookCategoryService.updateCategory(selectedCategory)) {
                loadCategories();
            } else {
                JOptionPane.showMessageDialog(this, "Không thể hiển thị danh mục sách.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một danh mục sách để hiển thị.", "Lưu ý", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void searchCategories() {
        String keyword = searchField.getText().trim();
        List<BookCategory> categories = bookCategoryService.searchCategories(keyword);
        updateTable(categories);
    }

    private void sortCategories() {
        String sortOrder = (String) sortComboBox.getSelectedItem();
        List<BookCategory> categories = bookCategoryService.sortCategories(sortOrder);
        updateTable(categories);
    }

    private void clearFields() {
        categoryNameField.setText("");
        searchField.setText("");
        categoryTable.clearSelection();
    }

    private BookCategory getSelectedCategory() {
        int selectedRow = categoryTable.getSelectedRow();
        if (selectedRow != -1) {
            int categoryId = (int) categoryTable.getValueAt(selectedRow, 1);
            return bookCategoryService.getCategoryById(categoryId);
        }
        return null;
    }

    // Phương thức cập nhật dữ liệu trong JTable
    private void updateTable(List<BookCategory> categories) {
        tableModel.setRowCount(0); // Xóa dữ liệu cũ

        int index = 0;
        for (BookCategory category : categories) {
            // Thêm dòng mới vào JTable
            tableModel.addRow(new Object[]{
                    ++index,
                    category.getCategoryID(),
                    category.getCategoryName(),
                    category.isActive() ? "Hoạt động" : "Ẩn"
            });
        }
    }
}
