package utils;

public class CustomUtils {

    // Print methods with various overloads
    public static void print(String message) {
        System.out.println(message);
    }

    public static void print() {
        System.out.println();
    }

    public static void print(Object obj) {
        System.out.println(obj);
    }

    public static void printf(String format, Object... args) {
        System.out.printf(format, args);
    }

    public static void printInline(String message) {
        System.out.print(message);
    }

    // Display methods with formatting
    public static void printHeader(String title) {
        int width = 50;
        print();
        printInline("┌");
        for (int i = 0; i < width; i++) printInline("─");
        print("┐");

        int padding = (width - title.length()) / 2;
        printInline("│");
        printInline(" ".repeat(padding));
        printInline(title);
        printInline(" ".repeat(width - padding - title.length()));
        print("│");

        printInline("└");
        for (int i = 0; i < width; i++) printInline("─");
        print("┘");
        print();
    }

    public static void printSection(String sectionTitle) {
        print("\n" + "─".repeat(50));
        print(sectionTitle);
        print("─".repeat(50));
    }

    public static void printSuccess(String message) {
        print("✓ " + message);
    }

    public static void printError(String message) {
        print("✗ " + message);
    }

    public static void printDivider() {
        print("─".repeat(80));
    }

    public static void printDivider(int length) {
        print("─".repeat(length));
    }
}