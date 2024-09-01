package ru.melnikov.task.credit.service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.melnikov.task.credit.service.dto.request.LoanApplicationRequestDto;

import ru.melnikov.task.credit.service.entities.LoanApplication;

@Mapper(componentModel = "spring")
public interface LoanApplicationMapper {
    LoanApplicationMapper LOAN_APPLICATION_MAPPER = Mappers.getMapper(LoanApplicationMapper.class);

    @Mapping(target = "loanAmount", source = "requestDto.loanAmount")
    @Mapping(target = "isApprovedLoan", constant = "false")
    @Mapping(target = "loanTermMonths", source = "loanTermMonths")
    LoanApplication loanApplicationDtoToEntity(LoanApplicationRequestDto requestDto);
}
