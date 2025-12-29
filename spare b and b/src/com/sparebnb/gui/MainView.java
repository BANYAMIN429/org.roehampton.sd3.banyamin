package com.sparebnb.gui;

import com.sparebnb.controller.SystemManager;
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
    private ListView<Property> propertyListView;
    private TextArea detailsArea;
    private ComboBox<Guest> guestSelector;
    private TextField priceFilterField;
    private Label statusLabel;

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

        // --- LEFT: List & Filter ---
        VBox leftMenu = new VBox(10);
        leftMenu.setPadding(new Insets(0, 15, 0, 0));

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

        leftMenu.getChildren().addAll(filterLabel, priceFilterField, new Label("Properties:"), propertyListView);
        layout.setLeft(leftMenu);

        // --- CENTER: Details ---
        detailsArea = new TextArea();
        detailsArea.setEditable(false);
        detailsArea.setText("Select a property to view details...");
        layout.setCenter(detailsArea);

        // --- BOTTOM: Buttons ---
        VBox bottomBar = new VBox(10);
        bottomBar.setPadding(new Insets(15, 0, 0, 0));
        Button bookButton = new Button("Book Selected Property");
        statusLabel = new Label("Status: Ready");

        bookButton.setOnAction(e -> handleBooking());

        bottomBar.getChildren().addAll(bookButton, statusLabel);
        layout.setBottom(bottomBar);
    }

    // --- LOGIC METHODS ---

    private void filterList(String priceText) {
        List<Property> allProps = systemManager.getAllProperties();

        if (priceText == null || priceText.isEmpty()) {
            propertyListView.getItems().setAll(allProps);
        } else {
            try {
                double maxPrice = Double.parseDouble(priceText);
                // ADVANCED REQUIREMENT: Java Streams
                List<Property> filtered = allProps.stream()
                        .filter(p -> p.getPricePerNight() <= maxPrice)
                        .collect(Collectors.toList());
                propertyListView.getItems().setAll(filtered);
            } catch (NumberFormatException e) {
                // Ignore invalid input
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

        if (guest == null || prop == null) {
            statusLabel.setText("Status: Please select a guest and property.");
            return;
        }

        // Simulating dates for the demo
        LocalDate start = LocalDate.now().plusDays(1);
        LocalDate end = LocalDate.now().plusDays(3);

        String result = systemManager.createBooking(guest.getGuestId(), prop.getPropertyId(), start, end);
        statusLabel.setText("Status: " + result);
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