import java.util.*;
public class SmartCanteen {
// MENUS
private static final Map<String, Integer> DRINKS = new LinkedHashMap<>();
private static final Map<String, Integer> SNACKS = new LinkedHashMap<>();
private static final Map<String, Integer> MEALS = new LinkedHashMap<>();
// ADMIN USERS
private static final Set<String> ADMINS = new HashSet<>();
public static void main(String[] args) {
initializeMenus();
Scanner sc = new Scanner(System.in);
int queue = 1;
while (true) {
System.out.println("\n=====================================");
System.out.println(" SMART CANTEEN ORDERING SYSTEM");
System.out.println("=====================================");
System.out.println("1. Customer Order");
System.out.println("2. Admin Menu");
System.out.println("3. Exit");
System.out.print("Choose: ");
int mainChoice = sc.nextInt();
sc.nextLine();
if (mainChoice == 1) {
queue = processOrder(sc, queue);
}
else if (mainChoice == 2) {
adminMenu(sc);
}
else if (mainChoice == 3) {
break;
} else {
System.out.println("Invalid choice. Try again.");
}
}
System.out.println("\nTHANK YOU FOR USING OUR SYSTEM!");
sc.close();
}
// ================= CUSTOMER =================
private static int processOrder(Scanner sc, int queue) {
System.out.print("\nEnter your name: ");
String name = sc.nextLine();
List<String> items = new ArrayList<>();
List<String> receiptItems = new ArrayList<>();
List<Integer> quantities = new ArrayList<>();
List<Integer> prices = new ArrayList<>();
int grandTotal = 0;
System.out.println("\n=== START ADDING ITEMS ===");
while (true) {
displayMenus();
int category = getValidCategory(sc);
Map<String, Integer> menu = getMenuByCategory(category);
String categoryName = getCategoryName(category);
displayCategoryMenu(menu, categoryName);
String item = selectItem(sc, menu);
int price = menu.get(item);
int qty = getValidQuantity(sc);
items.add(item);
receiptItems.add(item + " (add item)");
quantities.add(qty);
prices.add(price);
grandTotal += price * qty;
System.out.println(item + " added to order!");
System.out.print("\nAdd another item (yes/no): ");
String addMore = sc.nextLine().trim().toLowerCase();
if (!addMore.equals("yes")) {
break;
}
System.out.println();
}
System.out.println("\n=== ORDER SUMMARY ===");
System.out.println("Customer: " + name);
for (int i = 0; i < items.size(); i++) {
System.out.println((i+1) + ". " + items.get(i) + " x" + quantities.get(i) +
" = PHP" + (prices.get(i) * quantities.get(i)));
}
System.out.println("GRAND TOTAL: PHP" + grandTotal);
System.out.print("\nConfirm entire order (yes/no): ");
String confirm = sc.nextLine().trim().toLowerCase();
if (confirm.equals("yes")) {
System.out.println("\nORDER CONFIRMED!");
System.out.println("Queue #: " + queue);
printReceipt(queue, name, receiptItems, quantities, prices, grandTotal);
queue++;
} else {
System.out.println("Order Cancelled.");
}
return queue;
}
// ================= ADMIN =================
private static void adminMenu(Scanner sc) {
System.out.print("Enter admin username: ");
String username = sc.nextLine().trim();
System.out.print("Enter admin password: ");
String pass = sc.nextLine().trim();
if (!ADMINS.contains(username) || !pass.equals("admin123")) {
System.out.println("Invalid admin credentials!");
return;
}
System.out.println("Welcome, " + username + "!");
while (true) {
System.out.println("\n=== ADMIN MENU ===");
System.out.println("1. Add Item");
System.out.println("2. Remove Item");
System.out.println("3. View Menu");
System.out.println("4. Back");
System.out.print("Choose: ");
int choice = sc.nextInt();
sc.nextLine();
if (choice == 1) addItem(sc);
else if (choice == 2) removeItem(sc);
else if (choice == 3) displayAllMenus();
else if (choice == 4) break;
else System.out.println("Invalid choice!");
}
}
private static void addItem(Scanner sc) {
int cat = chooseCategory(sc);
System.out.print("Item name: ");
String name = sc.nextLine().trim();
System.out.print("Price: ");
int price = sc.nextInt();
sc.nextLine();
Map<String, Integer> menu = getMenuByCategory(cat);
menu.put(name, price);
System.out.println("Item '" + name + "' added successfully!");
}
private static void removeItem(Scanner sc) {
int cat = chooseCategory(sc);
Map<String, Integer> menu = getMenuByCategory(cat);
System.out.print("Item to remove: ");
String name = sc.nextLine().trim();
if (menu.containsKey(name)) {
menu.remove(name);
System.out.println("Item '" + name + "' removed!");
} else {
System.out.println("Item not found!");
}
}
// ================= UTILITIES =================
private static void initializeMenus() {
DRINKS.put("Coke", 20);
DRINKS.put("Water", 10);
DRINKS.put("Juice", 15);
DRINKS.put("Koolers", 15);
DRINKS.put("Coke Mismo", 20);
DRINKS.put("Mountain Dew", 20);
DRINKS.put("Chuckie", 30);
DRINKS.put("DutchMill", 30);
SNACKS.put("Chips", 15);
SNACKS.put("Bread", 10);
SNACKS.put("Biscuit", 12);
SNACKS.put("Calcheese", 15);
SNACKS.put("Richoco", 15);
SNACKS.put("Bread Pan", 13);
SNACKS.put("BengBeng", 10);
SNACKS.put("Candy", 5);
SNACKS.put("Hamburger", 30);
SNACKS.put("Hotdog", 25);
SNACKS.put("Hotdog w/Bun", 30);
MEALS.put("Burger", 50);
MEALS.put("Hotdog", 30);
MEALS.put("Rice Meal", 60);
MEALS.put("Hamburger", 50);
MEALS.put("Hotdog", 50);
MEALS.put("Hotdog w/Bun", 50);
MEALS.put("Giniling w/rice", 60);
MEALS.put("Chicken Curry w/rice", 60);
MEALS.put("Egg w/rice", 40);
MEALS.put("Veggie w/rice", 60);
// ADD 15 ADMIN USERNAMES
for (int i = 1; i <= 15; i++) {
ADMINS.add("admin" + i);
}
}
private static void displayMenus() {
System.out.println("\nCATEGORIES:");
System.out.println("1. Drinks");
System.out.println("2. Snacks");
System.out.println("3. Meals");
}
private static void displayAllMenus() {
System.out.println("\n=== DRINKS ===");
displayCategoryMenu(DRINKS, "Drinks");
System.out.println("\n=== SNACKS ===");
displayCategoryMenu(SNACKS, "Snacks");
System.out.println("\n=== MEALS ===");
displayCategoryMenu(MEALS, "Meals");
}
private static void displayCategoryMenu(Map<String, Integer> menu, String name) {
System.out.println("\n" + name + " MENU:");
for (Map.Entry<String, Integer> entry : menu.entrySet()) {
System.out.println(entry.getKey() + " - PHP" + entry.getValue());
}
}
private static int getValidCategory(Scanner sc) {
int cat;
while (true) {
System.out.print("Choose category (1-3): ");
if (sc.hasNextInt()) {
cat = sc.nextInt();
sc.nextLine();
if (cat >= 1 && cat <= 3) break;
} else {
sc.nextLine();
}
System.out.println("Invalid category. Try again.");
}
return cat;
}
private static int getValidQuantity(Scanner sc) {
int qty;
while (true) {
System.out.print("Quantity: ");
if (sc.hasNextInt()) {
qty = sc.nextInt();
sc.nextLine();
if (qty > 0) break;
} else {
sc.nextLine();
}
System.out.println("Quantity must be at least 1.");
}
return qty;
}
private static Map<String, Integer> getMenuByCategory(int category) {
switch (category) {
case 1: return DRINKS;
case 2: return SNACKS;
default: return MEALS;
}
}
private static String getCategoryName(int category) {
switch (category) {
case 1: return "Drinks";
case 2: return "Snacks";
default: return "Meals";
}
}
private static String selectItem(Scanner sc, Map<String, Integer> menu) {
String item;
while (true) {
System.out.print("Select item by name: ");
item = sc.nextLine().trim();
if (menu.containsKey(item)) break;
System.out.println("Item not found. Try again.");
}
return item;
}
private static int chooseCategory(Scanner sc) {
System.out.println("\n1. Drinks\n2. Snacks\n3. Meals");
int cat;
while (true) {
System.out.print("Choose category: ");
if (sc.hasNextInt()) {
cat = sc.nextInt();
sc.nextLine();
if (cat >= 1 && cat <= 3) break;
} else {
sc.nextLine();
}
System.out.println("Invalid category!");
}
return cat;
}
private static void printReceipt(int queue, String name, List<String> receiptItems, List<Integer> quantities,
List<Integer> prices, int grandTotal) {
System.out.println("\n========== RECEIPT ==========");
System.out.println("Queue #: " + queue);
System.out.println("Customer: " + name);
System.out.println("ORDER ITEMS:");
for (int i = 0; i < receiptItems.size(); i++) {
int subtotal = prices.get(i) * quantities.get(i);
System.out.println(" " + receiptItems.get(i) + " x" + quantities.get(i) +
" PHP" + prices.get(i) + " = PHP" + subtotal);
}
System.out.println("GRAND TOTAL: PHP" + grandTotal);
System.out.println("=============================");
}
}