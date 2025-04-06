/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esd.esd_6200.pojo;

/**
 *
 * @author simranchawla
 */
import lombok.Data;

import jakarta.persistence.*;

import lombok.Data;

@Entity
@Table(name = "book")
@Data
public class Book   {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "description")
    private String description;

    @Column(name = "copies")
    private int copies;

    @Column(name = "copies_available")
    private int copiesAvailable;

    @Column(name = "category")
    private String category;

    @Column(name = "img")
    private String img;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
