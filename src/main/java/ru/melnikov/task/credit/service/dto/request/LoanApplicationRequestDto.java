package ru.melnikov.task.credit.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanApplicationRequestDto {
    @NotBlank(message = "Family name is required")
    @Size(max = 15, message = "Family name must be up to 15 characters")
    @Pattern(regexp = "^[A-Za-zА-Яа-я]+$", message = "Family name can only contain letters")
    private String familyName;

    @NotBlank(message = "Name is required")
    @Size(max = 15, message = "Name must be up to 15 characters")
    @Pattern(regexp = "^[A-Za-zА-Яа-я]+$", message = "Name can only contain letters")
    private String name;

    @NotBlank(message = "Patronymic is required")
    @Size(max = 15, message = "Patronymic must be up to 15 characters")
    @Pattern(regexp = "^[A-Za-zА-Яа-я]+$", message = "Patronymic can only contain letters")
    private String patronymic;

    @NotBlank(message = "Passport details are required")
    @Pattern(regexp = "^\\d{10}$", message = "Passport details must be exactly 10 digits")
    private String passportDetails;

    @NotBlank(message = "Marital status is required")
    private String maritalStatus;

    @NotBlank(message = "Registration is required")
    @Size(max = 255, message = "Registration must be up 255 characters")
    private String registration;

    @NotBlank(message = "Contact number is required")
    @Pattern(regexp = "^\\d{11}$", message = "Contact number must be exactly 11 digits")
    private String contactNumber;

    @NotBlank(message = "Employment details are required")
    @Size(max = 255, message = "Employment details must be up 255 characters")
    private String employmentDetails;

    @NotBlank(message = "Loan amount is required")
    @Pattern(regexp = "^\\d{1,9}$", message = "Loan amount must be up to 9 digits")
    private String loanAmount;

    @NotBlank(message = "Loan term is required")
    @Pattern(regexp = "^(1[0-2]|[1-9])$", message = "Loan term must be a number between 1 and 12 months")
    private String loanTermMonths;
}
