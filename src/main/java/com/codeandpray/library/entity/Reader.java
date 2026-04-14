package com.codeandpray.library.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
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
    private String firstname;

    @Column(name = "reader_lastname")
    private String lastname;

    @Column(name = "reader_phone")
    private int phone;

    @Column(name = "reader_address")
    private int address;

    @Column(name = "reader_status")
    private String status;

    @Column(name = "reader_registration_date")
    private Date registrationDate;

    @Column(name = "reader_email")
    private String email;

    @Column
//    @OneToMany()
    private List<Book> books;
}
