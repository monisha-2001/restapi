package com.rest.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
//import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/book")
public class BookController {

//    public class MyException extends RuntimeException{ };

    @Autowired
    BookRepository bookRepository;

    @GetMapping
    public List<Book> getBookRecord(){
        //findall method return type is only list,so we have to mention the return type as list only
        return bookRepository.findAll();
    }

    @GetMapping(value = "{bookId}")
    public Book getBookById(@PathVariable(value = "bookId")Long bookId){
        return bookRepository.findById(bookId).get();
    }

//    @GetMapping(value = "{bookId}")
//    public Book getBookById(@PathVariable(value = "bookId")Long bookId) throws Exception {
//        if(!bookRepository.findById(bookId).isPresent()){
//            throw new MyException();
//        }
//        return bookRepository.findById(bookId).get();
//    }

//    @ExceptionHandler(MyException.class)
//    @ResponseStatus(value = HttpStatus.UNAUTHORIZED,reason = "Please probide a valid Id")
//    public void handler(){ }


    @PostMapping
    public Book createBookRecord(@RequestBody Book bookRecord){
        return bookRepository.save(bookRecord);
    }

    @PutMapping
    public Book updateBookRecord(@RequestBody Book bookRecord) throws ChangeSetPersister.NotFoundException {
        if(bookRecord == null||bookRecord.getBookId() == null) {
            throw new ChangeSetPersister.NotFoundException();
        }
        Optional<Book> optionalBook=bookRepository.findById(bookRecord.getBookId());
        if(!optionalBook.isPresent()){
            throw new ChangeSetPersister.NotFoundException();
        }
        Book existingBookRecord=optionalBook.get();
        existingBookRecord.setName(bookRecord.getName());
        existingBookRecord.setSummary(bookRecord.getSummary());
        existingBookRecord.setRating(bookRecord.getRating());

        return bookRepository.save(existingBookRecord);
    }

//    TODO: delete mapping we will write using TDD method
    @DeleteMapping(value = "{bookId}")
    public void deleteBookById(@PathVariable(value = "bookId") Long  bookId){ //throws ChangeSetPersister.NotFoundException {
//
//        if(!bookRepository.findById(bookId).isPresent()){
//            throw new ChangeSetPersister.NotFoundException();
//        }
         bookRepository.deleteById(bookId);



}

}
