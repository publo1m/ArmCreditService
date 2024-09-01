package ru.melnikov.task.credit.service.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.melnikov.task.credit.service.dto.request.LoanApplicationRequestDto;
import ru.melnikov.task.credit.service.entities.Client;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientMapperTest {

    private ClientMapper clientMapper;

    @BeforeEach
    void setUp() {
        clientMapper = Mappers.getMapper(ClientMapper.class);
    }

    @Test
    void testRequestDtoToClient() {
        LoanApplicationRequestDto requestDto = new LoanApplicationRequestDto();
        requestDto.setFamilyName("Doe");
        requestDto.setName("John");
        requestDto.setPatronymic("Smith");
        requestDto.setPassportDetails("AB1234567");
        requestDto.setMaritalStatus("Single");
        requestDto.setRegistration("123 Street");
        requestDto.setContactNumber("1234567890");
        requestDto.setEmploymentDetails("Software Engineer");

        Client client = clientMapper.requestDtoToClient(requestDto);

        assertEquals("Doe", client.getFamilyName());
        assertEquals("John", client.getName());
        assertEquals("Smith", client.getPatronymic());
        assertEquals("AB1234567", client.getPassportDetails());
        assertEquals("Single", client.getMaritalStatus());
        assertEquals("123 Street", client.getRegistration());
        assertEquals("1234567890", client.getContactNumber());
        assertEquals("Software Engineer", client.getEmploymentDetails());
        assertEquals(List.of(), client.getLoanAgreementList());
        assertEquals(List.of(), client.getLoanApplicationList());
    }
}
