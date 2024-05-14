package com.example.librarycommon.service;

import com.example.librarycommon.model.Book;
import org.springframework.stereotype.Service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

@Service
public interface LibraryService extends Remote {
    List<Book> getAllBooks() throws RemoteException;
    Book getBook(String bookTitle) throws RemoteException;
    Book borrowBook(int bookId) throws RemoteException;
    Book returnBook(int bookId) throws RemoteException;
}
