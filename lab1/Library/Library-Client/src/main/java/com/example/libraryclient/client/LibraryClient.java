package com.example.libraryclient.client;

import com.example.librarycommon.model.Book;
import com.example.librarycommon.service.LibraryService;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LibraryClient implements InitializingBean {
    @Autowired
    private LibraryService libraryService;
    private final Scanner scanner;

    public LibraryClient() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        run();
    }

    public void run() {
        try {
            System.out.println("欢迎使用图书馆服务！");
            while (true) {
                System.out.println("请选择操作:");
                System.out.println("0. 退出");
                System.out.println("1. 显示所有图书");
                System.out.println("2. 查找图书");
                System.out.println("3. 借阅图书");
                System.out.println("4. 归还图书");
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 0:
                        System.out.println("谢谢使用，再见！");
                        // System.exit(0);
                        return;
                    case 1:
                        displayAllBooks();
                        break;
                    case 2:
                        findBook();
                        break;
                    case 3:
                        borrowBook();
                        break;
                    case 4:
                        returnBook();
                        break;
                    default:
                        System.out.println("无效的选择，请重新输入。");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayAllBooks() {
        try {
            List<Book> books = libraryService.getAllBooks();
            System.out.println("图书馆中的图书 (编号, 书名, 作者, 出版日期, 是否借出): ");
            for (Book book : books) {
                System.out.println(book.getId()+", "+book.getTitle()+", "+book.getAuthor()+", "+book.getPublicationDate()+", "+book.isBorrowed());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void findBook() {
        try {
            System.out.println("请输入要查询的图书名称: ");
            String bookTitle = scanner.next();
            scanner.nextLine();

            Book foundBook = libraryService.getBook(bookTitle);
            if (foundBook != null) {
                System.out.println("查询成功 (编号, 书名, 作者, 出版日期, 是否借出): ");
                System.out.println(foundBook.getId()+", "+foundBook.getTitle()+", "+foundBook.getAuthor()+", "+foundBook.getPublicationDate()+", "+foundBook.isBorrowed());
            } else {
                System.out.println("查询失败: 图书不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void borrowBook() {
        try {
            System.out.println("请输入要借阅的图书编号: ");
            int bookId = scanner.nextInt();
            scanner.nextLine();

            Book borrowedBook = libraryService.borrowBook(bookId);
            if (borrowedBook != null) {
                System.out.println("成功借阅: " + borrowedBook.getTitle());
            } else {
                System.out.println("借阅失败: 图书不存在或已被借出");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void returnBook() {
        try {
            System.out.println("请输入要归还的图书编号: ");
            int bookId = scanner.nextInt();
            scanner.nextLine();

            Book returnedBook = libraryService.returnBook(bookId);
            if (returnedBook != null) {
                System.out.println("成功归还图书: " + returnedBook.getTitle());
            } else {
                System.out.println("归还失败: 图书不存在或未被借出");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
