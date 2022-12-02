package com.example.ourgroupbooksystem.ui.main;

public class BookDataVO {
    public String isbn;
    public String author;
    public String bookName;
    public String publishedDate;
    public String publisher;
    public Integer quantitiy;
    public Integer price;

    @Override
    public String toString() {
        return "BookDataVO{" +
                "isbn='" + isbn + '\'' +
                ", author='" + author + '\'' +
                ", bookName='" + bookName + '\'' +
                ", publishedDate='" + publishedDate + '\'' +
                ", publisher='" + publisher + '\'' +
                ", quantitiy=" + quantitiy +
                ", price=" + price +
                '}';
    }

    public Integer getQuantitiy() {
        return quantitiy;
    }

    public void setQuantitiy(Integer quantitiy) {
        this.quantitiy = quantitiy;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public BookDataVO(String isbn, String author, String bookName, String publishedDate, String publisher, Integer quantitiy, Integer price) {
        this.isbn = isbn;
        this.author = author;
        this.bookName = bookName;
        this.publishedDate = publishedDate;
        this.publisher = publisher;
        this.quantitiy = quantitiy;
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
}
