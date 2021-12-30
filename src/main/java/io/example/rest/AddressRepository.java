package io.example.rest;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface AddressRepository extends MongoRepository<AddressItem, String> {

    @Query(value = "{ 'state': ?0 , 'city': ?1 , 'street': ?2 , 'number': ?3}")
    List<AddressItem> findAllByAddress(String state, String city, String street, String number);
}
