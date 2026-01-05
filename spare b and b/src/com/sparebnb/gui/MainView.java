package com.sparebnb.gui;

import com.sparebnb.controller.SystemManager;
import com.sparebnb.model.Booking;
import com.sparebnb.model.Guest;
import com.sparebnb.model.Property;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class MainView {

    // 1. GET THE CONTROLLER (Singleton from Sprint 2)
    private final SystemManager systemManager = SystemManager.getInstance();

    // 2. UI COMPONENTS
    private BorderPane layout;
    private ListView<Property> propertyListView; // List for browsing properties
    private TextArea detailsArea;
    private ComboBox<Guest> guestSelector;
    private TextField priceFilterField;
    private Label statusLabel;

    // --- NEW VARIABLES ADDED HERE ---
    private ListView<Booking> bookingListView; // List for "My Bookings" tab
    private DatePicker checkInPicker;          // Date selection for start
    private DatePicker checkOutPicker;         // Date selection for end
    // --------------------------------

    public MainView() {
        // Load dummy data so the list isn't empty when you run it
        seedData();
        // Build the screen
        createLayout();
    }

    private void createLayout() {
        layout = new BorderPane();
        layout.setPadding(new Insets(15));

        // --- TOP: User Selection ---
        HBox topBar = new HBox(10);
        topBar.setPadding(new Insets(0, 0, 15, 0));
        Label guestLabel = new Label("Select Current User:");

        guestSelector = new ComboBox<>();
        guestSelector.getItems().addAll(systemManager.getAllGuests());
        if (!guestSelector.getItems().isEmpty()) guestSelector.getSelectionModel().selectFirst();

        topBar.getChildren().addAll(guestLabel, guestSelector);
        layout.setTop(topBar);

        // --- LEFT: TabPane (CHANGED) ---
        // Replaced the simple VBox with a TabPane to hold "Browse" and "My Bookings"
        TabPane leftTabs = new TabPane();

        // TAB 1: Browse Properties (Original functionality)
        VBox propertyTabContent = new VBox(10);
        propertyTabContent.setPadding(new Insets(10, 0, 0, 0));

        Label filterLabel = new Label("Max Price Filter:");
        priceFilterField = new TextField();
        priceFilterField.setPromptText("e.g. 100");

        // FEATURE: Filter list when user types (Java Streams)
        priceFilterField.textProperty().addListener((obs, oldVal, newVal) -> filterList(newVal));

        propertyListView = new ListView<>();
        propertyListView.getItems().addAll(systemManager.getAllProperties());

        // FEATURE: Show details when clicked
        propertyListView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> showDetails(newVal)
        );

        propertyTabContent.getChildren().addAll(filterLabel, priceFilterField, new Label("Properties:"), propertyListView);
        Tab propTab = new Tab("Browse", propertyTabContent);
        propTab.setClosable(false);

        // TAB 2: My Bookings (NEW FEATURE - Cancel/Release items)
        VBox bookingTabContent = new VBox(10);
        bookingTabContent.setPadding(new Insets(10, 0, 0, 0));

        bookingListView = new ListView<>();
        refreshBookingList(); // Populate list initially

        Button cancelBtn = new Button("Cancel Selected Booking");
        cancelBtn.setOnAction(e -> cancelSelectedBooking());

        bookingTabContent.getChildren().addAll(new Label("Active Bookings:"), bookingListView, cancelBtn);
        Tab bookingTab = new Tab("My Bookings", bookingTabContent);
        bookingTab.setClosable(false);

        // Add tabs to the left pane
        leftTabs.getTabs().addAll(propTab, bookingTab);
        layout.setLeft(leftTabs);

        // --- CENTER: Details ---
        detailsArea = new TextArea();
        detailsArea.setEditable(false);
        detailsArea.setText("Select a property to view details...");
        layout.setCenter(detailsArea);

        // --- BOTTOM: Action Area & Dates (CHANGED) ---
        VBox bottomBar = new VBox(10);
        bottomBar.setPadding(new Insets(15, 0, 0, 0));

        // 1. Create Date Pickers
        checkInPicker = new DatePicker(LocalDate.now());
        checkInPicker.setPromptText("Check-In");
        checkOutPicker = new DatePicker(LocalDate.now().plusDays(1));
        checkOutPicker.setPromptText("Check-Out");

        // 2. Add them to a horizontal box
        HBox dateBox = new HBox(10, new Label("From:"), checkInPicker, new Label("To:"), checkOutPicker);

        Button bookButton = new Button("Book Selected Property");
        statusLabel = new Label("Status: Ready");

        // Use Lambda for event handling
        bookButton.setOnAction(e -> handleBooking());

        bottomBar.getChildren().addAll(dateBox, bookButton, statusLabel);
        layout.setBottom(bottomBar);
    }

    // --- LOGIC METHODS ---

    private void filterList(String priceText) {
        List<Property> allProps = systemManager.getAllProperties();

        if (priceText == null || priceText.isEmpty()) {
            propertyListView.getItems().setAll(allProps);
            statusLabel.setText("Status: Ready");
        } else {
            try {
                double maxPrice = Double.parseDouble(priceText);
                // ADVANCED FEATURE: Java Streams
                List<Property> filtered = allProps.stream()
                        .filter(p -> p.getPricePerNight() <= maxPrice)
                        .collect(Collectors.toList());
                propertyListView.getItems().setAll(filtered);
                statusLabel.setText("Status: Filter applied.");
            } catch (NumberFormatException e) {
                // IMPROVED: Error feedback
                statusLabel.setText("Status: Invalid price format. Please enter a number.");
                propertyListView.getItems().setAll(allProps);
            }
        }
    }

    private void showDetails(Property p) {
        if (p != null) {
            String info = "ID: " + p.getPropertyId() + "\n" +
                    "Type: " + p.getPropertyType() + "\n" +
                    "Address: " + p.getAddress() + "\n" +
                    "Price: $" + p.getPricePerNight() + "\n" +
                    "Owner: " + p.getOwner().getName() + "\n" +
                    "Facilities: " + p.getFacilities();
            detailsArea.setText(info);
        }
    }

    private void handleBooking() {
        Guest guest = guestSelector.getValue();
        Property prop = propertyListView.getSelectionModel().getSelectedItem();

        // 1. Validate User and Property
        if (guest == null || prop == null) {
            statusLabel.setText("Status: Please select a guest and a property first.");
            return;
        }

        // 2. Validate Dates (New Logic)
        LocalDate start = checkInPicker.getValue();
        LocalDate end = checkOutPicker.getValue();

        if (start == null || end == null) {
            statusLabel.setText("Status: Please select valid Check-In and Check-Out dates.");
            return;
        }

        // 3. Create Booking
        String result = systemManager.createBooking(guest.getGuestId(), prop.getPropertyId(), start, end);
        statusLabel.setText("Status: " + result);

        // 4. Update the My Bookings list immediately
        refreshBookingList();
    }

    // NEW Helper: Updates the "My Bookings" list from the SystemManager
    private void refreshBookingList() {
        if (bookingListView != null) {
            bookingListView.getItems().setAll(systemManager.getAllBookings());
        }
    }

    // NEW Helper: Cancels a booking selected in the "My Bookings" tab
    private void cancelSelectedBooking() {
        Booking selected = bookingListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            String result = systemManager.cancelBooking(selected.getBookingId());
            statusLabel.setText("Status: " + result);
            refreshBookingList(); // Refresh list to remove the cancelled item
        } else {
            statusLabel.setText("Status: Select a booking from the list to cancel.");
        }
    }

    private void seedData() {
        // Create dummy data if the system is empty
        if (systemManager.getAllHosts().isEmpty()) {
            systemManager.addHost("Alice Host", "alice@test.com");
            systemManager.addGuest("Bob Guest", "bob@test.com");
            systemManager.addProperty(1, "123 Main St", "Nice Flat", 100.0, "H1");
            systemManager.addProperty(2, "456 Side Rd", "Cheap Room", 40.0, "H1");
            systemManager.addProperty(1, "Penthouse", "Luxury", 500.0, "H1");
        }
    }

    public Parent getView() {
        return layout;
    }
}