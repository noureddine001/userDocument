package com.valueit.userdocument.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId ; 
    private String firstName ;
    private String lastName ;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Document> documents ;

}
