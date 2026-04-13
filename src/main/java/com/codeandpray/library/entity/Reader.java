package com.codeandpray.library.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.AnyDiscriminatorImplicitValues;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Reader {

    @Id
    @Column(name = "reader_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reader_name")
    private String name;

    @Column(name = "reader_email")
    private String email;



    public Reader(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
