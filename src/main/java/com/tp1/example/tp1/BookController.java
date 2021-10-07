package com.tp1.example.tp1;

import me.alidg.errors.annotation.ExceptionMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.lang.reflect.Array;
import java.util.ArrayList;

@RestController
public class BookController {
    private ArrayList<BookEntity> bookList;

    BookController(){
        this.bookList = new ArrayList<BookEntity>();
    }
    @GetMapping("/books/all")
    public ResponseEntity<ArrayList<BookEntity>> getBookList(){
        if(this.bookList.isEmpty())
            throw new BookListIsEmpty();
        return new ResponseEntity(this.bookList, HttpStatus.OK);
    }

    @PostMapping("/books/add")
    public ResponseEntity addBook(@RequestBody BookEntity book){
        if(book.getName().isEmpty() || book.getAuthor().isBlank() || book.getEsbn().isBlank())
            throw new FailedToAddBookException();
        this.bookList.add(book);
        return new ResponseEntity("/books/name=" + book.getName() + "&author=" + book.getAuthor(),HttpStatus.CREATED);
    }

    @DeleteMapping("/books/remove/one")
    public ResponseEntity deleteBook(@RequestBody BookDeleteForm deleteForm){
        int i = 0;
        for(BookEntity books : this.bookList){
            if(deleteForm.getAuthor().equals(books.getAuthor()) && deleteForm.getName().equals(books.getName())){
                this.bookList.remove(i);
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
            i++;
        }
        throw new FailedToAddBookException();
    }
}
