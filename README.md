# Warehouse Management System for Stationery Wholesaler

This is a **Warehouse Management System** created for a stationery wholesaler that operates **multiple branches**. Each branch consists of **several warehouses**, with each warehouse further divided into **multiple floors**, and each floor containing **several racks**. This system is designed to efficiently manage items across branches, allowing users to easily locate where specific items are stored, thus saving time during product searches.

## Key Features

- **Multi-Branch Management**: Manage multiple branches, each with multiple warehouses, floors, and racks.
- **Efficient Item Tracking**: Quickly locate and manage items stored in different branches and warehouses.
- **Real-Time Notifications**: Employees are notified when orders are processed by the Sales Department, allowing them to promptly prepare and pack orders.
- **Department Integration**:
  - **Admin Department**: Provides oversight, generates reports, and manages overall system operations.
  - **Sales Department**: Processes orders, which trigger notifications for the General Department to pack items.
  - **General Department**: Manages the movement of items from the storeroom to specific racks, based on item categories, ensuring efficient storage and retrieval.

## Workflow Overview

1. **Supplier Purchases**: The Admin can purchase items from suppliers. Once the supplier delivers the items, they are temporarily stored in the storeroom.
2. **Storeroom Management**: Employees from the General Department are responsible for moving items from the storeroom to designated racks based on the matching item categories. Only items placed in racks are eligible for order fulfillment.
3. **Packing Orders**: Items stored in the storeroom are **not** available for packing until they have been properly categorized and placed on the appropriate racks. This ensures quality control and smooth inventory tracking.
   
## Quality Control

To maintain the company's standards, items in the storeroom cannot be packed until they are organized on racks. This helps maintain a clear workflow across departments and ensures accurate tracking of inventory.

## Report Generation

Admins can use the built-in **report generation tool** to analyze data and make informed decisions based on warehouse operations and inventory status.
