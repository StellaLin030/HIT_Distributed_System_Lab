package com.example.librarycommon.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
public class Book implements Serializable {
    private int id;
    private String title;
    private String author;
    private String publicationDate;
    private boolean isBorrowed;
}