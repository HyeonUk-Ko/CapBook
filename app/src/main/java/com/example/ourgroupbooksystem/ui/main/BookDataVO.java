package com.example.ourgroupbooksystem.ui.main;

public class BookDataVO {
    public String isbn;
    public String author;
    public String bookName;
    public String publishedDate;
    public String publisher;
    public int quantitiy;
    public int price;

    public int getQuantitiy() {
        return quantitiy;
    }

    public void setQuantitiy(int quantitiy) {
        this.quantitiy = quantitiy;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public BookDataVO() {
    }

    public BookDataVO(String isbn, String author, String bookName, String publishedDate, String publisher) {
        this.isbn = isbn;
        this.author = author;
        this.bookName = bookName;
        this.publishedDate = publishedDate;
        this.publisher = publisher;
    }
}
