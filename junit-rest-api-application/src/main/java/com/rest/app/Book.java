package com.rest.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "book_record")
@Data
@NoArgsConstructor  //these 4 annotations are from lombok,it reduce the code and need not to declare cons,getter and setter
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long BookId;

    @NonNull
    private String name;

    @NonNull
    private String summary;

    private int rating;
}
