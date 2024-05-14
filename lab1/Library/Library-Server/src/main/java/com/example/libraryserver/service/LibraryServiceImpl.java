package com.example.libraryserver.service;

import com.example.librarycommon.model.Book;
import com.example.librarycommon.service.LibraryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@Service
public class LibraryServiceImpl implements LibraryService {
    private List<Book> books;
    private String bookListPath = "D:\\Projects\\DistributedSystem\\lab1\\Library\\Library-Common\\src\\main\\java\\com\\example\\librarycommon\\model\\books.txt";
    public LibraryServiceImpl() throws RemoteException {
        books = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(bookListPath))) {
            // log.info("read successfully");
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String title = parts[1];
                String author = parts[2];
                String publicationDate = parts[3];
                boolean isBorrowed = Boolean.parseBoolean(parts[4]);
                books.add(new Book(id, title, author, publicationDate, isBorrowed));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Book> getAllBooks() throws RemoteException {
        return books;
    }

    @Override
    public Book getBook(String bookTitle) throws RemoteException {
        for (Book book : books) {
            if (Objects.equals(book.getTitle(), bookTitle)) {
                return book;
            }
        }
        return null;
    }

    @Override
    public Book borrowBook(int bookId) throws RemoteException {
        // 根据ID查找图书并借阅
        for (Book book : books) {
            if (book.getId() == bookId && !book.isBorrowed()) {
                book.setBorrowed(true);
                return book;
            }
        }
        // 图书不存在或已被借出
        return null;
    }

    @Override
    public Book returnBook(int bookId) throws RemoteException {
        // 标记图书为未借出状态
        for (Book book : books) {
            if (book.getId() == bookId && book.isBorrowed()) {
                book.setBorrowed(false);
                return book;
            }
        }
        // 图书不存在或未被借出
        return null;
    }
}
