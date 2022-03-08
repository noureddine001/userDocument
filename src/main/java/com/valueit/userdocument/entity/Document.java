package com.valueit.userdocument.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long docId ;
    private String docName ;
    private String description ;
    private String LocalURL ;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user ;
}
