package com.macedo.Customer.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.macedo.Customer.dtos.AddressDTO;
import com.macedo.Customer.entities.Address;
import com.macedo.Customer.services.AddressService;

@RefreshScope
@RestController
@RequestMapping("addresses")
public class AddressResource {
    @Autowired
    private AddressService addressService;

    @GetMapping
    public ResponseEntity<List<AddressDTO>> getAddresses(AddressDTO filtro) {
        return new ResponseEntity<List<AddressDTO>>((addressService.getAddresses(filtro)), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Integer id) {
        return new ResponseEntity<Address>((addressService.getAddressById(id)), HttpStatus.OK);
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<List<AddressDTO>> getAddressesByCustomerId(@PathVariable Integer id) {
        return new ResponseEntity<List<AddressDTO>>(
                (addressService.getAddressesByCustomerId(id)),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AddressDTO> createAddress(@RequestBody AddressDTO Address) {
        return new ResponseEntity<AddressDTO>((addressService.createAddress(Address)), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Integer id, @RequestBody AddressDTO Address) {
        return new ResponseEntity<AddressDTO>((addressService.updateAddress(id, Address)), HttpStatus.OK);
    }

    @PatchMapping("{id}")
    public ResponseEntity<AddressDTO> patchAddress(@PathVariable Integer id,
            @RequestBody AddressDTO AddressIncompletaDTO) {
        return new ResponseEntity<AddressDTO>((addressService.patchAddress(id, AddressIncompletaDTO)), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Integer id) {
        addressService.deleteAddress(id);
        return ResponseEntity.ok().build();
    }

}
