package com.demo.training.datanosql.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Person {
    @Id
    private Integer id;
    private String name;
    private String surname;
}
