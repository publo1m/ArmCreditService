package ru.melnikov.task.credit.service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ru.melnikov.task.credit.service.dto.request.LoanApplicationRequestDto;
import ru.melnikov.task.credit.service.entities.Client;
import ru.melnikov.task.credit.service.entities.LoanAgreement;
import ru.melnikov.task.credit.service.entities.LoanApplication;
import ru.melnikov.task.credit.service.entities.enums.MaritalStatus;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientMapper CLIENT_MAPPER = Mappers.getMapper(ClientMapper.class);

    @Mapping(target = "familyName", source = "familyName")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "patronymic", source = "patronymic")
    @Mapping(target = "passportDetails", source = "passportDetails")
    @Mapping(target = "maritalStatus", source = "maritalStatus", qualifiedByName = "stringToMaritalStatus")
    @Mapping(target = "registration", source = "registration")
    @Mapping(target = "contactNumber", source = "contactNumber")
    @Mapping(target = "employmentDetails", source = "employmentDetails")
    @Mapping(target = "loanAgreementList", expression = "java(agreementList())")
    @Mapping(target = "loanApplicationList", expression = "java(applicationList())")
    Client requestDtoToClient(LoanApplicationRequestDto requestDto);

    default List<LoanAgreement> agreementList() {
        return new ArrayList<>();
    }

    default List<LoanApplication> applicationList() {
        return new ArrayList<>();
    }

    @Named("stringToMaritalStatus")
    default MaritalStatus stringToMaritalStatus(String maritalStatus) {
        return MaritalStatus.valueOf(maritalStatus);
    }
}
