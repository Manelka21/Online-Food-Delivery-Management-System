# Documentation - Food Delivery Management System

## Data Folder Structure
All data is stored in pipe-delimited text files in the `data/` directory.

- `users.txt`: Stores Customer and Driver details.
- `restaurants.txt`: Stores restaurant information.
- `menu_items.txt`: Stores menu items for each restaurant.
- `order_batches.txt`: Stores groups of orders placed at once.
- `orders.txt`: Stores individual orders.
- `order_items.txt`: Stores items within each order.
- `deliveries.txt`: Stores delivery assignments and status.
- `payments.txt`: Stores payment transactions.

## Backend Implementation
The backend is built with Spring Boot and uses a custom `FileRepository` pattern for data persistence instead of a traditional database.

## Frontend Applications
- `customer-app/`: For customers to browse and order food.
- `admin-app/`: For staff to manage the system.
