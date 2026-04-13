package com.codeandpray.library.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Reader {

    @Id
    @Column(name = "reader_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reader_name")
    private String name;

    @Column(name = "reader_email")
    private String email;

    @Column
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "Book_id")
    private List<Book> books;

    public Reader(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
