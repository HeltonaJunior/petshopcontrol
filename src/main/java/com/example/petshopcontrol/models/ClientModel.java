package com.example.petshopcontrol.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table (name = "CLIENT_DB")
public class ClientModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private UUID idClient;
    private String name;
    private String cpf;
    private String address;
    private String namePet;
    private BigDecimal valueCredit;
}
