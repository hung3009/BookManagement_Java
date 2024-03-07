package com.example.ui;

import com.example.model.Role;
import com.example.service.RoleService;
import com.example.model.User;
import com.example.service.UserService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class ManageUsersPanel extends JPanel {

    private final UserService userService;
    private final RoleService roleService;

    private DefaultTableModel tableModel;
    private JTable userTable;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<Role> roleComboBox;
    private JTextField fullnameField;
    private JTextField emailField;
    private JTextField phoneNumberField;
    private JTextField addressField;
    private JTextField genderField;
    private JCheckBox isActiveCheckbox;
    private JButton addButton;
    private JButton updateButton;
    private JButton deactivateButton;
    private JButton activateButton;
    private JTextField searchField;
    private JButton searchButton;
    private JComboBox<String> sortComboBox;
    private JButton sortButton;
	private List<Role> roles;

    public ManageUsersPanel() {
        this.userService = new UserService();
        this.roleService = new RoleService();
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        titlePanel.add(new JLabel("User Management Panel"));
        titlePanel.setPreferredSize(new Dimension(0, 50));

        JPanel contentPanel = new JPanel(new BorderLayout());

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createTitledBorder("User List"));

        contentPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel searchAndSortPanel = new JPanel();
        searchAndSortPanel.setLayout(new BoxLayout(searchAndSortPanel, BoxLayout.X_AXIS));
        searchAndSortPanel.setPreferredSize(new Dimension(0, 50));

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());

        JPanel sortPanel = new JPanel();
        sortPanel.setLayout(new FlowLayout());

        searchField = new JTextField(15);
        searchButton = new JButton("Search");
        sortComboBox = new JComboBox<>(new String[]{"A-Z", "Z-A"});
        sortButton = new JButton("Sort");

        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        sortPanel.add(new JLabel("Sort:"));
        sortPanel.add(sortComboBox);
        sortPanel.add(sortButton);

        searchAndSortPanel.add(searchPanel);
        searchAndSortPanel.add(Box.createHorizontalStrut(20));
        searchAndSortPanel.add(sortPanel);

        centerPanel.add(searchAndSortPanel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Username", "Role", "Fullname", "Email", "Phone Number", "Address", "Gender", "Active"};
        tableModel = new DefaultTableModel(columnNames, 0);
        userTable = new JTable(tableModel);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScrollPane = new JScrollPane(userTable);
        centerPanel.add(tableScrollPane, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new BorderLayout());

        contentPanel.add(controlPanel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("User Information"));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Functions"));

        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        roleComboBox = new JComboBox<Role>();
        fullnameField = new JTextField(15);
        emailField = new JTextField(15);
        phoneNumberField = new JTextField(15);
        addressField = new JTextField(15);
        genderField = new JTextField(15);
        isActiveCheckbox = new JCheckBox("Active");

        inputPanel.add(new JLabel("Username:"));
        inputPanel.add(usernameField);
        inputPanel.add(new JLabel("Password:"));
        inputPanel.add(passwordField);
        inputPanel.add(new JLabel("Role:"));
        inputPanel.add(roleComboBox);
        inputPanel.add(new JLabel("Fullname:"));
        inputPanel.add(fullnameField);
        inputPanel.add(new JLabel("Email:"));
        inputPanel.add(emailField);
        inputPanel.add(new JLabel("Phone Number:"));
        inputPanel.add(phoneNumberField);
        inputPanel.add(new JLabel("Address:"));
        inputPanel.add(addressField);
        inputPanel.add(new JLabel("Gender:"));
        inputPanel.add(genderField);

        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deactivateButton = new JButton("Deactivate");
        activateButton = new JButton("Activate");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deactivateButton);
        buttonPanel.add(activateButton);

        controlPanel.add(inputPanel, BorderLayout.CENTER);
        controlPanel.add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addOrUpdateUser(true));
        updateButton.addActionListener(e -> addOrUpdateUser(false));
        deactivateButton.addActionListener(e -> deactivateOrActivateUser(false));
        activateButton.addActionListener(e -> deactivateOrActivateUser(true));

        searchButton.addActionListener(e -> searchUsers());
        sortButton.addActionListener(e -> sortUsers());

        userTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow != -1) {
                populateUserFields(selectedRow);
            }
        });

        add(titlePanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        loadUsers();
        loadRoles();
    }

    private void addOrUpdateUser(boolean isAdd) {
        try {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());
            Role selectedRole = (Role) roleComboBox.getSelectedItem();
            String fullname = fullnameField.getText();
            String email = emailField.getText();
            String phoneNumber = phoneNumberField.getText();
            String address = addressField.getText();
            String gender = genderField.getText();
            boolean isActive = isActiveCheckbox.isSelected();

            if (username.isEmpty() || password.isEmpty() || selectedRole == null || fullname.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            User selectedUser = getSelectedUser();

            if (selectedUser == null) {
                JOptionPane.showMessageDialog(this, "Please select a user.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Update the selected user with the new values
            selectedUser.setUsername(username);
            selectedUser.setPassword(password);
            selectedUser.setRoleID(selectedRole.getRoleID());
            selectedUser.setFullname(fullname);
            selectedUser.setEmail(email);
            selectedUser.setPhoneNumber(phoneNumber);
            selectedUser.setAddress(address);
            selectedUser.setGender(gender);
            selectedUser.setActive(isActive);

            if (isAdd) {
                userService.addUser(selectedUser);
                JOptionPane.showMessageDialog(this, "User added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                userService.updateUser(selectedUser);
                JOptionPane.showMessageDialog(this, "User updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }

            loadUsers();
            clearUserFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void deactivateOrActivateUser(boolean isActivate) {
        try {
            User selectedUser = getSelectedUser();

            if (selectedUser == null) {
                JOptionPane.showMessageDialog(this, "Please select a user.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            selectedUser.setActive(isActivate);
            userService.updateUser(selectedUser);

            if (isActivate) {
                JOptionPane.showMessageDialog(this, "User activated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "User deactivated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }

            loadUsers();
            clearUserFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchUsers() {
        try {
            String searchTerm = searchField.getText();

            if (searchTerm.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a search term.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<User> searchResults = userService.searchUsers(searchTerm);

            if (searchResults.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No matching users found.", "No Results", JOptionPane.INFORMATION_MESSAGE);
            } else {
                updateTable(searchResults);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


 // Inside ManageUsersPanel class
    private void sortUsers() {
        try {
            String sortType = (String) sortComboBox.getSelectedItem();
            // Convert the sort type to uppercase for consistency
            sortType = sortType.toUpperCase();
            
            List<User> sortedUsers = userService.sortUsers(sortType);
            updateTable(sortedUsers);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


 // Update the populateUserFields method
    private void populateUserFields(int selectedRow) {
        try {
            String userId = tableModel.getValueAt(selectedRow, 0).toString();
            User user = userService.getUserById(Integer.parseInt(userId));

            usernameField.setText(user.getUsername());
            passwordField.setText(user.getPassword());
            for(Role role : roles) {
            	if(user.getRoleID() == role.getRoleID()) {
            		roleComboBox.setSelectedItem(role);
            		break;
            	}
            }
            fullnameField.setText(user.getFullname());
            emailField.setText(user.getEmail());
            phoneNumberField.setText(user.getPhoneNumber());
            addressField.setText(user.getAddress());
            genderField.setText(user.getGender());
            isActiveCheckbox.setSelected(user.isActive());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

 // Add this method to get the role name from the role ID
    private String getRoleName(int roleID) {
        try {
            Role role = roleService.getRoleById(roleID);
            return (role != null) ? role.getRoleName() : "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    
 // Update the updateTable method
    private void updateTable(List<User> users) {
        tableModel.setRowCount(0);

        for (User user : users) {
            Object[] rowData = {
                    user.getUserID(),
                    user.getUsername(),
                    getRoleName(user.getRoleID()),  // Use getRoleName method
                    user.getFullname(),
                    user.getEmail(),
                    user.getPhoneNumber(),
                    user.getAddress(),
                    user.getGender(),
                    user.isActive() ? "Yes" : "No"
            };
            tableModel.addRow(rowData);
        }
    }

    private void clearUserFields() {
        usernameField.setText("");
        passwordField.setText("");
        roleComboBox.setSelectedIndex(0);
        fullnameField.setText("");
        emailField.setText("");
        phoneNumberField.setText("");
        addressField.setText("");
        genderField.setText("");
        isActiveCheckbox.setSelected(false);
    }

    private User getSelectedUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow != -1) {
            String userId = tableModel.getValueAt(selectedRow, 0).toString();
            return userService.getUserById(Integer.parseInt(userId));
        }
        return null;
    }

    private void loadUsers() {
        try {
            List<User> users = userService.getAllUsers();
            updateTable(users);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadRoles() {
        try {
            roles = roleService.getAllRoles();
            DefaultComboBoxModel<Role> roleComboBoxModel = new DefaultComboBoxModel<>(roles.toArray(new Role[0]));
            roleComboBox.setModel(roleComboBoxModel);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
