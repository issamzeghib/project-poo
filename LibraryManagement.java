import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Scanner;
//كلاس الخاص بالكتاب ومعلوماته 
class Book {
    private String title, author, isbn, bookType;
    private boolean isAvailable;

    public Book(String title, String author, String isbn, String bookType) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.bookType = bookType;
        this.isAvailable = true;
    }

    public boolean borrowBook() {
        if (isAvailable) {
            isAvailable = false;
            return true;
        }
        return false;
    }

    public void returnBook() {
        isAvailable = true;
    }

    public String getDetails() {
        return title + " - " + author + " (ISBN: " + isbn + ") | " + (isAvailable ? "متاح" : "معار");
    }
}

//كلاس فيه الطالب المستعير والكتاب المستعار
class Borrower {
    private String name, studentId;
    private ArrayList<String> borrowedBooks;

    public Borrower(String name, String studentId) {
        this.name = name;
        this.studentId = studentId;
        this.borrowedBooks = new ArrayList<>();
    }

    public String borrowBook(Book book) {
        if (book.borrowBook()) {
            borrowedBooks.add(book.getDetails());
            return name + " استعار الكتاب '" + book.getDetails() + "'.";
        }
        return "الكتاب غير متاح للاستعارة.";
    }

    public String returnBook(Book book) {
        if (borrowedBooks.contains(book.getDetails())) {
            book.returnBook();
            borrowedBooks.remove(book.getDetails());
            return name + " استرجع الكتاب '" + book.getDetails() + "'.";
        }
        return "خطاء" + name + " لم يقم باستعارة هذا الكتاب.";
    }
}

//كلاس تع عملية الاستعارة وتاريخها وتاريخ الارجاع
class BorrowingProcess {
    private Book book;
    private Borrower borrower;
    private LocalDate borrowDate;
    private LocalDate returnDate;

    public BorrowingProcess(Book book, Borrower borrower) {
        this.book = book;
        this.borrower = borrower;
        this.borrowDate = LocalDate.now();
        this.returnDate = null;
    }

    public void completeReturn() {
        returnDate = LocalDate.now();
        borrower.returnBook(book);
    }

    public void showBorrowingDetails() {
        System.out.println("📖 الكتاب: " + book.getDetails() + " | المستعير: " + borrower);
        System.out.println("📅 تاريخ الإعارة: " + borrowDate + " | تاريخ الاسترجاع: " + (returnDate != null ? returnDate : "قيد الإعارة"));
    }
}

//  كلاس تع  المكتبة ويدير إضافة الكتب والمستعيرين وتنفيذ الإعارات
class Library {
    private ArrayList<Book> books;
    private ArrayList<Borrower> borrowers;

    public Library() {
        books = new ArrayList<>();
        borrowers = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
        System.out.println(" تمت إضافة الكتاب '" + book.getDetails() + "'.");
    }

    public void addBorrower(Borrower borrower) {
        borrowers.add(borrower);
        System.out.println(" تمت إضافة المستعير '" + borrower + "'.");
    }

    public void borrowBook(Borrower borrower, Book book) {
        System.out.println(borrower.borrowBook(book));
    }

    public void returnBook(Borrower borrower, Book book) {
        System.out.println(borrower.returnBook(book));
    }
}

//  تشغيل التطبيق داخل `LibraryManagement.java`
public class LibraryManagement {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        // إضافة كتب ومستعيرين
        Book book1 = new Book("Java Programming", "John Doe", "123456", "Paper");
        Book book2 = new Book("Data Structures", "Jane Smith", "789101", "E-Book");
        Borrower borrower1 = new Borrower("Issam", "S2025");

        library.addBook(book1);
        library.addBook(book2);
        library.addBorrower(borrower1);

        //الواجهة لتخرج للمستخدم
        while (true) {
            System.out.println("\n نظام إدارة المكتبة:");
            System.out.println("1. استعارة كتاب");
            System.out.println("2. استرجاع كتاب");
            System.out.println("3. عرض التفاصيل");
            System.out.println("4. خروج");
            System.out.print("أدخل الخيار: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("أدخل عنوان الكتاب: ");
                String title = scanner.nextLine();
                library.borrowBook(borrower1, book1.getDetails().contains(title) ? book1 : book2);

            } else if (choice == 2) {
                System.out.print("أدخل عنوان الكتاب: ");
                String title = scanner.nextLine();
                library.returnBook(borrower1, book1.getDetails().contains(title) ? book1 : book2);

            } else if (choice == 3) {
                System.out.println(" تفاصيل الكتب المتاحة:");
                System.out.println(book1.getDetails());
                System.out.println(book2.getDetails());

            } else if (choice == 4) {
                System.out.println(" خروج من النظام...");
                break;

            } else {
                System.out.println(" خيار غير صحيح، حاول مرة أخرى.");
            }
        }

        scanner.close();
    }
}
