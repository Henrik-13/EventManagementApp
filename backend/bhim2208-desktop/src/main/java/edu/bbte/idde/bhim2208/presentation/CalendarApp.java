package edu.bbte.idde.bhim2208.presentation;

import edu.bbte.idde.bhim2208.dataaccess.exception.EventNotFoundException;
import edu.bbte.idde.bhim2208.dataaccess.model.Event;
import edu.bbte.idde.bhim2208.service.EventService;
import edu.bbte.idde.bhim2208.service.EventServiceImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class CalendarApp extends JFrame {
    EventService eventService = new EventServiceImpl();
    private final JTable table;
    private final DefaultTableModel tableModel;

    private final JLabel titleLabel;
    private final JLabel locationLabel;
    private final JLabel dateLabel;
    private final JLabel timeLabel;
    private final JLabel descriptionLabel;

    private final JTextField titleField;
    private final JTextField locationField;
    private final JTextField dateField;
    private final JTextField timeField;
    private final JTextField descriptionField;
    private final JCheckBox online;

    private final JButton createButton;
    private final JButton deleteButton;
    private final JButton updateButton;

    private Integer selectedEventId;

    public CalendarApp() {
        super();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Calendar");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        titleLabel = new JLabel("Title");
        titleField = new JTextField();
        locationLabel = new JLabel("Location");
        locationField = new JTextField();
        dateLabel = new JLabel("Date");
        dateField = new JTextField();
        timeLabel = new JLabel("Time");
        timeField = new JTextField();
        descriptionLabel = new JLabel("Description");
        descriptionField = new JTextField();
        online = new JCheckBox("Online");

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2));
        inputPanel.add(titleLabel);
        inputPanel.add(titleField);
        inputPanel.add(locationLabel);
        inputPanel.add(locationField);
        inputPanel.add(dateLabel);
        inputPanel.add(dateField);
        inputPanel.add(timeLabel);
        inputPanel.add(timeField);
        inputPanel.add(descriptionLabel);
        inputPanel.add(descriptionField);
        inputPanel.add(online);
        add(inputPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        createButton = new JButton("Create");
        deleteButton = new JButton("Delete");
        updateButton = new JButton("Update");

        deleteButton.setEnabled(false);
        updateButton.setEnabled(false);

        buttonPanel.add(createButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        add(buttonPanel, BorderLayout.SOUTH);

        String[] columnNames = {"ID", "Title", "Location", "Date", "Time", "Description", "Online"};
        tableModel = new MyDefaultTableModel(columnNames);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        addRows();

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();
                selectedEventId = (Integer) table.getValueAt(selectedRow, 0);

                deleteButton.setEnabled(true);
                updateButton.setEnabled(true);
            }
        });

        createButton.addActionListener(e -> create());

        deleteButton.addActionListener(e -> delete());

        updateButton.addActionListener(e -> update());

        setVisible(true);
    }

    private void clearFields() {
        titleField.setText("");
        locationField.setText("");
        descriptionField.setText("");
        dateField.setText("");
        timeField.setText("");
        online.setSelected(false);
        selectedEventId = -1;
        deleteButton.setEnabled(false);
        updateButton.setEnabled(false);
        table.clearSelection();
    }

    private void create() {
        if (titleField.getText().isEmpty()
                || locationField.getText().isEmpty()
                || dateField.getText().isEmpty()
                || timeField.getText().isEmpty()
                || descriptionField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "The fields can't be empty!");
        } else {
            LocalDate date;
            try {
                date = LocalDate.parse(dateField.getText());
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex.getMessage());
                return;
            }
            LocalTime time;
            try {
                time = LocalTime.parse(timeField.getText());
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex.getMessage());
                return;
            }
            Event event = new Event(titleField.getText(),
                    locationField.getText(),
                    date,
                    time,
                    descriptionField.getText(),
                    online.isSelected());
            Integer id = eventService.createEvent(event);
            tableModel.addRow(new Object[]{id,
                    titleField.getText(),
                    locationField.getText(),
                    dateField.getText(),
                    timeField.getText(),
                    descriptionField.getText(),
                    online.isSelected()});
        }
        clearFields();
    }

    private void delete() {
        if (selectedEventId >= 0) {
            try {
                eventService.deleteEvent(selectedEventId);
                int selectedRow = table.getSelectedRow();
                tableModel.removeRow(selectedRow);
            } catch (EventNotFoundException ex) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex.getMessage());
            }
        }
        clearFields();
    }

    private String updateTitle(Integer id) throws EventNotFoundException {
        return titleField.getText().isEmpty() ? eventService.getEvent(id).getTitle() : titleField.getText();
    }

    private String updateLocation(Integer id) throws EventNotFoundException {
        return locationField.getText().isEmpty() ? eventService.getEvent(id).getLocation() : locationField.getText();
    }

    private LocalDate updateDate(Integer id) throws EventNotFoundException {
        LocalDate date = eventService.getEvent(id).getDate();
        if (!dateField.getText().isEmpty()) {
            try {
                date = LocalDate.parse(dateField.getText());
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex.getMessage());
            }
        }
        return date;
    }

    private LocalTime updateTime(Integer id) throws EventNotFoundException {
        LocalTime time = eventService.getEvent(id).getTime();
        if (!timeField.getText().isEmpty()) {
            try {
                time = LocalTime.parse(timeField.getText());
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex.getMessage());
            }
        }
        return time;
    }

    private String updateDescription(Integer id) throws EventNotFoundException {
        return descriptionField.getText().isEmpty()
                ? eventService.getEvent(id).getDescription() : descriptionField.getText();
    }

    private void update() {
        if (selectedEventId >= 0) {
            try {
                String title = updateTitle(selectedEventId);
                String location = updateLocation(selectedEventId);
                LocalDate date = updateDate(selectedEventId);
                LocalTime time = updateTime(selectedEventId);
                String description = updateDescription(selectedEventId);
                Event event = new Event(title, location, date, time, description, online.isSelected());
                eventService.updateEvent(selectedEventId, event);
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    if (tableModel.getValueAt(i, 0).equals(selectedEventId)) {
                        tableModel.setValueAt(title, i, 1);
                        tableModel.setValueAt(location, i, 2);
                        tableModel.setValueAt(date, i, 3);
                        tableModel.setValueAt(time, i, 4);
                        tableModel.setValueAt(description, i, 5);
                        tableModel.setValueAt(online.isSelected(), i, 6);
                        break;
                    }
                }
            } catch (EventNotFoundException ex) {
                JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), ex.getMessage());
            }
        }

        clearFields();
    }

    private void addRows() {
        var events = eventService.getEvents();
        for (Event event : events) {
            tableModel.addRow(new Object[]{
                    event.getId(),
                    event.getTitle(),
                    event.getLocation(),
                    event.getDate(),
                    event.getTime(),
                    event.getDescription(),
                    event.isOnline()
            });
        }
    }

    private static class MyDefaultTableModel extends DefaultTableModel {
        public MyDefaultTableModel(String... columnNames) {
            super(columnNames, 0);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

    public static void main(String[] args) {
        new CalendarApp();
    }
}
