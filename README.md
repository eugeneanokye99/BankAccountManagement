# Bank Account Management System

A comprehensive console-based banking application built with Java that demonstrates Object-Oriented Programming principles, data structures, and algorithms for efficient account and transaction management.

## 🚀 Features

### Core Functionality
- **Account Management**: Create and view bank accounts
- **Customer Management**: Support for Regular and Premium customers
- **Transaction Processing**: Deposit and withdrawal operations
- **Transaction History**: View complete transaction records
- **Menu Navigation**: User-friendly console interface

### Account Types
- **Savings Account**:
    - Earns 3.5% annual interest
    - Minimum balance requirement: $500
    - Interest calculation functionality

- **Checking Account**:
    - $1,000 overdraft limit
    - $10 monthly fee (waived for Premium customers)
    - Overdraft protection

### Customer Types
- **Regular Customer**: Standard banking services
- **Premium Customer**:
    - No monthly fees
    - Priority service
    - Minimum balance requirement: $10,000

## 🛠️ Technology Stack

- **Language**: Java 8+
- **Paradigm**: Object-Oriented Programming (OOP)
- **Data Structures**: Arrays for account and transaction storage
- **Algorithms**: Linear search, sorting for transaction history

## 📋 Prerequisites

- Java Development Kit (JDK) 8 or higher
- Any Java IDE (Eclipse, IntelliJ IDEA, VS Code) or command line

## ⚙️ Installation & Setup

### Method 1: Using an IDE

1. **Clone or Download the Project**
   ```bash
   git clone <repository-url>
   ```
   Or download the ZIP file and extract it

2. **Open in IDE**
    - Open your preferred Java IDE
    - Select "Open Project" and choose the project folder
    - Ensure all Java files are in the correct package structure

3. **Package Structure**
   ```
   src/
   ├── account/
   │   ├── Account.java
   │   ├── SavingsAccount.java
   |   ├── AccountManager.java
   │   └── CheckingAccount.java
   ├── customer/
   │   ├── Customer.java
   │   ├── RegularCustomer.java
   │   └── PremiumCustomer.java
   ├── transaction/
   │   ├── Transactable.java
   |   ├── TransactionManager.java
   │   └── Transaction.java
   └── Main.java
   ```

4. **Run the Application**
    - Locate `Main.java` in the root directory
    - Right-click and select "Run" or use the IDE's run button

### Method 2: Using Command Line

1. **Compile All Java Files**
   ```bash
   javac -d bin account/*.java customer/*.java transaction/*.java manager/*.java Main.java
   ```

2. **Run the Application**
   ```bash
   java -cp bin Main
   ```

## 🎯 How to Use

### 1. Main Menu Navigation
```
BANK ACCOUNT MANAGEMENT - MAIN MENU
1. Create Account
2. View Accounts
3. Process Transaction
4. View Transaction History
5. Exit
```

### 2. Creating an Account
1. Select option 1 from the main menu
2. Enter customer details:
    - Name, Age, Contact, Address
3. Choose customer type (Regular or Premium)
4. Select account type (Savings or Checking)
5. Enter initial deposit amount
6. System generates unique account number automatically

### 3. Viewing Accounts
- Select option 2 to view all accounts
- Displays account details, balances, and specific features
- Shows total accounts and bank balance

### 4. Processing Transactions
1. Select option 3
2. Enter account number
3. Choose transaction type (Deposit/Withdrawal)
4. Enter amount
5. Confirm transaction details
6. System updates balance and records transaction

### 5. Viewing Transaction History
- Select option 4
- Enter account number
- View all transactions in reverse chronological order
- See summary with totals and net change

## 🏗️ Project Structure & OOP Principles

### Classes Implemented (11 Total)
- **Abstract Classes**: `Account`, `Customer`
- **Concrete Classes**: `SavingsAccount`, `CheckingAccount`, `RegularCustomer`, `PremiumCustomer`
- **Interface**: `Transactable`
- **Management Classes**: `Transaction`, `AccountManager`, `TransactionManager`
- **Main Class**: `Main`

### OOP Principles Applied
- **Encapsulation**: Private fields with public getters/setters
- **Inheritance**: Account and Customer hierarchies
- **Polymorphism**: Method overriding in child classes
- **Abstraction**: Abstract classes and interface
- **Composition**: Manager classes containing arrays of objects

### Data Structures & Algorithms
- **Arrays**: For storing accounts and transactions
- **Linear Search**: To find accounts by number
- **Sorting**: Transaction history by timestamp
- **Static Counters**: For auto-generating unique IDs

## 🧪 Testing the Application

### Sample Test Scenarios

1. **View Accounts**
    - Verify 5 sample accounts display correctly
    - Check interest rates and fees show properly

2. **Create Accounts**
    - Test both Savings and Checking accounts
    - Verify Regular and Premium customer benefits

3. **Process Transactions**
    - Deposit: Any positive amount
    - Withdrawal: Check minimum balance (Savings) and overdraft (Checking)

4. **Transaction History**
    - View history for accounts with transactions
    - Verify summary calculations


## 🐛 Troubleshooting

### Common Issues

1. **Compilation Errors**
    - Ensure all files are in correct packages
    - Verify Java version compatibility (JDK 8+)

2. **Input Issues**
    - Follow exact input formats
    - For age: Enter numbers only
    - For amounts: Enter numeric values only

3. **Transaction Failures**
    - Savings: Maintain $500 minimum balance
    - Checking: Stay within $1,000 overdraft limit

### Sample Data
The application comes pre-loaded with 5 sample accounts:
- 3 Savings accounts
- 2 Checking accounts
- Sample transactions for testing

## 📝 Development Notes

### Key Features Implemented
- ✅ Auto-generated unique IDs (ACC001, TXN001, CUS001)
- ✅ Input validation and error handling
- ✅ Transaction confirmation system
- ✅ Comprehensive account details display
- ✅ Efficient search and management algorithms

### Extension Possibilities
- Add file persistence for data storage
- Implement interest compounding
- Add bank employee authentication
- Enhance reporting features

## 👥 Contributors

Developed as an educational project demonstrating Java OOP principles and data structures.

## 📄 License

This project is for educational purposes as part of a programming curriculum.

---

**Happy Banking! 🏦**
