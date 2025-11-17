# SpareB&B Booking System

## 📖 Description

**SpareB&B** is a simple, console-based booking application built in Java. It models the core functionality of a short-term rental platform (like Airbnb or B&B). It follows a **Model-View-Controller (MVC)** design pattern to keep the code organized and maintainable.

This project manages four main types of data:
* **Hosts:** Users who own and list properties.
* **Guests:** Users who search for and book properties.
* **Properties:** The locations available for rent.
* **Bookings:** The records that link a Guest to a Property for a specific date range.

## ✨ Features

The system currently supports the following operations:

* **Host Management**
    * Add a new Host.
    * View a list of all registered Hosts.
* **Property Management**
    * Add a new Property (must be linked to an existing Host).
    * View a list of all Properties.
* **Guest Management**
    * Register a new Guest.
    * View a list of all registered Guests.
* **Booking Management**
    * Create a new Booking, linking a Guest to a Property.
    * **Smart Availability Check:** Automatically prevents double-booking by checking for date overlaps.
    * **Automatic Price Calculation:** Calculates the total price based on the property's nightly rate and the number of nights.
    * View a list of all active Bookings.
    * Cancel an existing Booking.
* **Search**
    * Search for properties based on a **maximum price per night**.

## 🏗️ Project Structure (MVC)

The project is organized using the **Model-View-Controller** pattern:

* **Model (`com.sparebnb.model`)**
    * `Host.java`: Represents a user who owns properties.
    * `Guest.java`: Represents a user who books properties.
    * `Property.java`: Represents a rentable property, linked to a `Host`.
    * `Booking.java`: Represents a booking, linking a `Guest` to a `Property`.
* **View (`com.sparebnb.view`)**
    * `Main.java`: The main "View" and entry point. It displays the console menu, takes user input, and prints output. It does not contain any business logic.
* **Controller (`com.sparebnb.controller`)**
    * `SystemManager.java`: The "brain" of the application. It contains all the business logic, manages the main data lists (`ArrayLists`), and handles requests from the `Main` view.

## 🚀 How to Run

You can compile and run this project from any standard Java IDE (like IntelliJ, Eclipse) or from the command line.

### Requirements
* Java Development Kit (JDK) 8 or higher.

### From the Command Line

1.  **Navigate to the `src` folder** in your project's root directory (assuming your `.java` files are in `sparebnb/src/com/...`).
    ```bash
    cd sparebnb/src
    ```

2.  **Compile all `.java` files:**
    ```bash
    # This command finds all .java files, compiles them, and places the .class files
    # in a new 'out' directory.
    javac -d ../out com/sparebnb/model/*.java com/sparebnb/controller/*.java com/sparebnb/view/*.java
    ```

3.  **Run the `Main` class** from the `out` directory:
    ```bash
    # Go to the 'out' directory
    cd ../out
    
    # Run the Main file using its full package name
    java com.sparebnb.view.Main
    ```

4.  You should now see the welcome message and the main menu:
    ```
    Welcome to SpareB&B
    
    --- Main Menu ---
    (1) Add a new Host
    (2) View all Hosts
    ...
    Enter your choice:
    ```
