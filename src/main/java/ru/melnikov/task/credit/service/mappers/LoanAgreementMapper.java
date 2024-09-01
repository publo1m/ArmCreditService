package ru.melnikov.task.credit.service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.melnikov.task.credit.service.dto.request.LoanApplicationRequestDto;
import ru.melnikov.task.credit.service.entities.LoanAgreement;

@Mapper(componentModel = "spring")
public interface LoanAgreementMapper {
    LoanAgreementMapper LOAN_AGREEMENT_MAPPER = Mappers.getMapper(LoanAgreementMapper.class);

    @Mapping(target = "isSigned", constant = "false")
    @Mapping(target = "dateSigning", expression = "java(null)")
    @Mapping(target = "loanApplication", ignore = true)
    LoanAgreement requestDtoToLoanAgreement(LoanApplicationRequestDto requestDto);
}
