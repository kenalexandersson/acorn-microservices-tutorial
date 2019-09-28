package com.acorn.tutorial.webapi.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class Item {

    private Long id;

    private String name;

    private Integer port;
}