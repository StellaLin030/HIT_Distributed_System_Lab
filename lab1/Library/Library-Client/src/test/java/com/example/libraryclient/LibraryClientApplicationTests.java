package com.example.libraryclient;

import com.example.librarycommon.model.Book;
import com.example.librarycommon.service.LibraryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class LibraryClientApplicationTests {
    @Autowired
    private LibraryService libraryService;

    @Test
    void contextLoads() {
        try {
            List<Book> books = libraryService.getAllBooks();
            System.out.println("图书馆中的图书：");
            for (Book book : books) {
                System.out.println(book.getTitle() + " by " + book.getAuthor());
            }

            // 借阅图书
            Book borrowedBook = libraryService.borrowBook(1);
            if (borrowedBook != null) {
                System.out.println("成功借阅：" + borrowedBook.getTitle());
                log.info("成功借阅：" + borrowedBook.getTitle());
            } else {
                System.out.println("借阅失败：图书不存在或已被借出");
            }

            // 归还图书
            libraryService.returnBook(borrowedBook.getId());
            System.out.println("成功归还：" + borrowedBook.getTitle());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
