package com.example.application.model;

import com.example.application.utils.StorageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

     @NotNull
     @Enumerated(EnumType.STRING)
     private StorageType type;

    public Storage(StorageType type) {
        this.type = type;
    }
}
