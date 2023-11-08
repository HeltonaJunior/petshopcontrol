package com.example.petshopcontrol.controllers;

import com.example.petshopcontrol.dtos.ClientRecordDto;
import com.example.petshopcontrol.models.ClientModel;
import com.example.petshopcontrol.repositories.ClientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ClientController {

    @Autowired
    ClientRepository clientRepository;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/clients")
    public ResponseEntity<ClientModel> saveClient(@RequestBody @Valid ClientRecordDto clientRecordDto){
        var clientModel = new ClientModel();
        BeanUtils.copyProperties(clientRecordDto, clientModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(clientRepository.save(clientModel));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/clients")
    public ResponseEntity<List<ClientModel>> getAllClients(){
        return ResponseEntity.status(HttpStatus.OK).body(clientRepository.findAll());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/clients/{cpf}")
    public ResponseEntity<Object> getOneClient(@PathVariable(value="cpf") UUID id){
        Optional<ClientModel> client0 = clientRepository.findById(id);
        if (client0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(client0.get());
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/clients/{cpf}")
    public ResponseEntity<Object> updateClient(@PathVariable(value="cpf") UUID id,
                                                @RequestBody @Valid ClientRecordDto clientRecordDto){
        Optional<ClientModel> client0 = clientRepository.findById(id);
        if (client0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found");
        }
        var clientModel = client0.get();
        BeanUtils.copyProperties(clientRecordDto, clientModel);
        return ResponseEntity.status(HttpStatus.OK).body(clientRepository.save(clientModel));
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/clients/{cpf}")
    public ResponseEntity<Object> deleteClient(@PathVariable(value="cpf") UUID id){
        Optional<ClientModel> client0 = clientRepository.findById(id);
        if(client0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found");
        }
        clientRepository.delete(client0.get());
        return ResponseEntity.status(HttpStatus.OK).body("Client deleted successfully");
    }

}
