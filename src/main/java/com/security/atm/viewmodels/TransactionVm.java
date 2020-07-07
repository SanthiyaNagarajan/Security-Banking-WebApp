package com.security.atm.viewmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.stream.StreamFilter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;

/**
 * View model for transaction details.
 */
@XmlRootElement
public class TransactionVm {
    @JsonProperty
    private int accountId;
    @JsonProperty
    private String password;
    @JsonProperty
    private String amount;

    public String getAmount() {
        return amount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public static TransactionVm parse(String string, String type) throws Exception{
        if(type.contains("xml")){
            JAXBContext jc = JAXBContext.newInstance(CredentialsVm.class);
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
            return (TransactionVm) unmarshaller.unmarshal(xsr);
        }
        else{
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(string, TransactionVm.class);
        }
    }
}