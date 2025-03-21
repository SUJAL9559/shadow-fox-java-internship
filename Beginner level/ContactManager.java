import java.util.Scanner;
import java.util.ArrayList;

public class ContactManager {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ArrayList<String> contacts = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n📞 Contact Management System");
            System.out.println("1. Add Contact");
            System.out.println("2. View Contacts");
            System.out.println("3. Update Contact");
            System.out.println("4. Delete Contact");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = getValidIntegerInput();  // ✅ Now ensures input is correctly read

            switch (choice) {
                case 1: addContact(); break;
                case 2: viewContacts(); break;
                case 3: updateContact(); break;
                case 4: deleteContact(); break;
                case 5: System.out.println("🚀 Exiting... Goodbye!"); return;
                default: System.out.println("❌ Invalid choice! Please try again.");
            }
        }
    }

    // ✅ Ensures only valid integer input is taken (fixes skipping issue)
    private static int getValidIntegerInput() {
        while (true) {
            String input = scanner.nextLine().trim(); // Always read as a full string
            try {
                return Integer.parseInt(input); // Convert input to an integer
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input! Please enter a number.");
                System.out.print("Try again: ");
            }
        }
    }

    private static void addContact() {
        System.out.print("Enter contact name: ");
        String name = scanner.nextLine().trim();  // ✅ Now correctly waits for input

        if (name.isEmpty()) {
            System.out.println("❌ Contact name cannot be empty.");
            return;
        }
        contacts.add(name);
        System.out.println("✅ Contact added: " + name);
    }

    private static void viewContacts() {
        System.out.println("\n📋 Contacts List:");
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            for (int i = 0; i < contacts.size(); i++) {
                System.out.println((i + 1) + ". " + contacts.get(i));
            }
        }
    }

    private static void updateContact() {
        viewContacts();
        if (contacts.isEmpty()) return;

        System.out.print("Enter contact number to update: ");
        int index = getValidIntegerInput() - 1;  // ✅ Now correctly reads input

        if (index >= 0 && index < contacts.size()) {
            System.out.print("Enter new name: ");
            String newName = scanner.nextLine().trim();
            if (newName.isEmpty()) {
                System.out.println("❌ New name cannot be empty.");
                return;
            }
            contacts.set(index, newName);
            System.out.println("✅ Contact updated.");
        } else {
            System.out.println("❌ Invalid contact number.");
        }
    }

    private static void deleteContact() {
        viewContacts();
        if (contacts.isEmpty()) return;

        System.out.print("Enter contact number to delete: ");
        int index = getValidIntegerInput() - 1;  // ✅ Now correctly reads input

        if (index >= 0 && index < contacts.size()) {
            System.out.println("🗑️ Deleted: " + contacts.remove(index));
        } else {
            System.out.println("❌ Invalid contact number.");
        }
    }
}
