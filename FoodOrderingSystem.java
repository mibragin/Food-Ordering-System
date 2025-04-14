import java.util.ArrayList;
import java.util.Scanner;

public class FoodOrderingSystem {
    static Hotel[] hotels = new Hotel[4]; // Corrected the number of hotels (index starts from 1)
    static User[] users = new User[100];
    static ArrayList<CartItem> cart = new ArrayList<>(); // To store items added to the cart
    static int total = 0;
    static int hotelChoice, foodChoice, food, n;
    static Scanner sc = new Scanner(System.in);
    static int userIndex = -1; // To track the current logged-in user

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n\n********************Welcome to Food Ordering System****************");
            System.out.println("\n\n1) SIGN UP");
            System.out.println("2) LOGIN");
            System.out.println("3) EXIT");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    signup();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    System.out.println("\n**********************Thank you. Visit again!*******************");
                    System.exit(0);
                default:
                    System.out.println("\nPlease enter a valid choice");
            }
        }
    }

    static void signup() {
        System.out.println("\n\n************Welcome to the signup page*************");
        sc.nextLine(); // clear the buffer
        System.out.print("Enter Your Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Your Age: ");
        int age = sc.nextInt();

        sc.nextLine(); // clear the buffer
        System.out.print("Enter Your Email: ");
        String email = sc.nextLine();

        System.out.print("Enter Password: ");
        String password1 = sc.nextLine();

        System.out.print("Confirm Password: ");
        String password2 = sc.nextLine();

        System.out.print("Enter Your Mobile Number: ");
        String mobile = sc.nextLine();

        if (validate(name, age, email, password1, password2, mobile)) {
            accountCheck(name, age, email, password1, mobile);
            login();
        }
    }

    static boolean validate(String name, int age, String email, String password1, String password2, String mobile) {
        if (!name.matches("[a-zA-Z]+")) {
            System.out.println("\nPlease enter a valid name");
            return false;
        }

        if (email.length() < 5 || !email.contains("@") || !email.contains(".")) {
            System.out.println("\nPlease enter a valid Email");
            return false;
        }

        if (!password1.equals(password2)) {
            System.out.println("\nPassword mismatch");
            return false;
        }

        if (password1.length() < 8 || password1.length() > 12 || !password1.matches(".*[A-Z].*") ||
            !password1.matches(".*[a-z].*") || !password1.matches(".*[0-9].*") || 
            !password1.matches(".*[@&#*].*")) {
            System.out.println("\nYour password is too weak. It must contain at least one uppercase letter, one lowercase letter, a number, and a special character (@, &, #, *)");
            return false;
        }

        if (age <= 0) {
            System.out.println("\nPlease enter a valid age");
            return false;
        }

        if (mobile.length() != 10 || !mobile.matches("[0-9]+")) {
            System.out.println("\nPlease enter a valid 10-digit mobile number");
            return false;
        }

        return true;
    }

    static void accountCheck(String name, int age, String email, String password, String mobile) {
        for (User user : users) {
            if (user != null && user.getEmail().equals(email)) {
                System.out.println("\nAccount already exists. Please login!");
                return;
            }
        }

        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) {
                users[i] = new User(name, age, email, password, mobile);
                System.out.println("\nAccount successfully created!");
                return;
            }
        }
    }

    static void login() {
        System.out.println("\n\n**************Welcome to the Login page ****************\n\n");

        sc.nextLine(); // clear the buffer
        System.out.print("Enter Your Email: ");
        String email = sc.nextLine();

        System.out.print("Enter Your Password: ");
        String password = sc.nextLine();

        for (int i = 0; i < users.length; i++) {
            if (users[i] != null && users[i].getEmail().equals(email)) {
                if (users[i].getPassword().equals(password)) {
                    userIndex = i;
                    System.out.println("\n\nWelcome " + users[i].getName() + ", You are successfully logged in\n\n ");
                    searchOptions();
                    return;
                } else {
                    System.out.println("\n\nInvalid Password! Please try again.\n");
                    return;
                }
            }
        }
        System.out.println("\nAccount doesn't exist. Please sign up!\n");
    }

    static void searchOptions() {
        System.out.println("We provide two ways of search");
        System.out.println("1) Search By Hotels");
        System.out.println("2) Search By Food");
        System.out.println("3) Exit");
        System.out.print("Enter your choice: ");
        int searchChoice = sc.nextInt();

        switch (searchChoice) {
            case 1:
                searchByHotels();
                break;
            case 2:
                searchByFood();
                break;
            case 3:
                return;
            default:
                System.out.println("Please enter a valid choice");
        }
    }

    static void initializeHotels() {
        hotels[1] = new Hotel("Aarya_Bhavan", new String[]{"Sandwich", "Pizza", "Fried_Rice", "Ice_Cream"}, new int[]{70, 100, 95, 50});
        hotels[2] = new Hotel("Banu_Hotel", new String[]{"Parotta", "Noodles", "Chicken_Rice", "Dosa"}, new int[]{15, 75, 80, 50});
        hotels[3] = new Hotel("SR_Bhavan", new String[]{"Chicken_Biriyani", "Prawn", "Falooda", "Burger"}, new int[]{90, 120, 35, 50});
    }

    static void searchByHotels() {
        initializeHotels();
        System.out.println("\n\nChoose the hotels\n1) " + hotels[1].getName() + "\n2) " + hotels[2].getName() + "\n3) " + hotels[3].getName() + "\n4) Exit");
        System.out.print("Select the hotel: ");
        hotelChoice = sc.nextInt();

        if (hotelChoice >= 1 && hotelChoice <= 3) {
            showHotelMenu(hotelChoice);
        } else if (hotelChoice == 4) {
            return;
        } else {
            System.out.println("Please enter a valid choice");
            searchByHotels();
        }
    }

    static void showHotelMenu(int hotelChoice) {
        total = 0;
        Hotel hotel = hotels[hotelChoice];

        while (true) {
            System.out.println("\n\nList of foods available in " + hotel.getName());
            String[] foods = hotel.getFoods();
            int[] prices = hotel.getPrices();

            for (int i = 0; i < foods.length; i++) {
                System.out.println((i + 1) + ") " + foods[i] + " - Rs. " + prices[i]);
            }
            System.out.println((foods.length + 1) + ") Cart");
            System.out.println((foods.length + 2) + ") Exit");
            System.out.print("Enter your choice: ");
            foodChoice = sc.nextInt();

            if (foodChoice >= 1 && foodChoice <= foods.length) {
                System.out.print("Enter the count of " + foods[foodChoice - 1] + ": ");
                n = sc.nextInt();
                addToCart(foods[foodChoice - 1], n, prices[foodChoice - 1]);
            } else if (foodChoice == foods.length + 1) {
                showCart();
            } else if (foodChoice == foods.length + 2) {
                return; // Return to hotel selection
            } else {
                System.out.println("Please enter a valid choice");
            }
        }
    }

    static void searchByFood() {
        initializeHotels();
        total = 0;

        while (true) {
            System.out.println("\n\nChoose the food:");
            int counter = 1;
            for (int i = 1; i <= 3; i++) {
                Hotel hotel = hotels[i];
                for (String foodItem : hotel.getFoods()) {
                    System.out.println(counter++ + ") " + foodItem);
                }
            }
            System.out.println(counter + ") Cart"); // Option for Cart
            System.out.println((counter + 1) + ") Exit"); // Option to Exit
            System.out.print("Enter your choice: ");
            food = sc.nextInt();

            if (food >= 1 && food < counter) {
                orderFood(food); // Use correct numbering logic
            } else if (food == counter) {
                showCart(); // Show cart if selected
            } else if (food == counter + 1) {
                return; // Exit option
            } else {
                System.out.println("Please enter a valid choice");
            }
        }
    }

    static void orderFood(int food) {
        int itemIndex = food - 1; // Adjust the index
        for (int i = 1; i <= 3; i++) {
            Hotel hotel = hotels[i];
            String[] foods = hotel.getFoods();
            int[] prices = hotel.getPrices();
            if (itemIndex < foods.length) {
                System.out.print("Enter the count of " + foods[itemIndex] + ": ");
                n = sc.nextInt();
                addToCart(foods[itemIndex], n, prices[itemIndex]);
                return;
            } else {
                itemIndex -= foods.length;  // Move to the next hotel's items
            }
        }
    }

    static void addToCart(String food, int quantity, int price) {
        cart.add(new CartItem(food, quantity, price * quantity));
        total += price * quantity;
        System.out.println(quantity + " " + food + " added to the cart.");
    }

    static void showCart() {
        System.out.println("\n***********Cart***********");
        for (CartItem item : cart) {
            System.out.println(item.quantity + " x " + item.food + " = Rs. " + item.totalPrice);
        }
        System.out.println("Total cost: Rs. " + total);
        System.out.println("***************************");
    
        System.out.println("\nDo you want to place the order? (yes/no)");
        sc.nextLine(); // Clear the buffer
        String confirmation = sc.nextLine(); // Read the confirmation
    
        if (confirmation.equalsIgnoreCase("yes")) {
            System.out.println("\nThank you for your order! Your total amount is Rs. " + total);
            cart.clear(); // Clear the cart after placing the order
            total = 0; // Reset total amount
            searchOptions(); // Return to search options
        } else if (confirmation.equalsIgnoreCase("no")) {
            System.out.println("\nYou can continue ordering or exit.");
            searchOptions(); // Return to search options
        } else {
            System.out.println("Invalid input. Please type 'yes' or 'no'.");
            showCart(); // Re-prompt if input is invalid
        }
    }
}

class Hotel {
    private String name;
    private String[] foods;
    private int[] prices;

    public Hotel(String name, String[] foods, int[] prices) {
        this.name = name;
        this.foods = foods;
        this.prices = prices;
    }

    public String getName() {
        return name;
    }

    public String[] getFoods() {
        return foods;
    }

    public int[] getPrices() {
        return prices;
    }
}

class CartItem {
    String food;
    int quantity;
    int totalPrice;

    public CartItem(String food, int quantity, int totalPrice) {
        this.food = food;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
}

class User {
    private String name;
    private int age;
    private String email;
    private String password;
    private String mobile;

    public User(String name, int age, String email, String password, String mobile) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getMobile() {
        return mobile;
    }
}
