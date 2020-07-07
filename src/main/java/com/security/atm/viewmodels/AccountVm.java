package com.security.atm.viewmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import netscape.javascript.JSObject;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.stream.StreamFilter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;

/**
 * View model for Account details.
 */
@XmlRootElement(name="account")
public class AccountVm {

    @JsonProperty
    private int id;
    @JsonProperty
    private String username;
    @JsonProperty
    private String password;
    @JsonProperty
    private String firstName;
    @JsonProperty
    private String lastName;
    @JsonProperty
    private String city;
    @JsonProperty
    private String balance;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public static AccountVm parse(String string,String type) throws Exception{
        if(type.contains("xml")){
            JAXBContext jc = JAXBContext.newInstance(AccountVm.class);
            XMLInputFactory xif = XMLInputFactory.newFactory();
            XMLStreamReader xsr = xif.createXMLStreamReader(new StringReader(string));
            xsr = xif.createFilteredReader(xsr, new StreamFilter() {
                @Override
                public boolean accept(XMLStreamReader reader) {
                    if(reader.getEventType() == XMLStreamReader.CHARACTERS) {
                        return !reader.getText().equals("\n");
                    }
                    return true;
                }

            });

            Unmarshaller unmarshaller = jc.createUnmarshaller();
            return (AccountVm) unmarshaller.unmarshal(xsr);
        }
        else{
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(string, AccountVm.class);
        }
    }
}
