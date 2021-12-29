package io.example.rest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
public class AddressItem {

    @Id
    private String id;
    private String state;
    private String city;
    private String street;
    private String number;
    private String postal;



}
