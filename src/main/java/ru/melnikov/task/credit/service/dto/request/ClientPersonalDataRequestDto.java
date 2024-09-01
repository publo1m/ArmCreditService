package ru.melnikov.task.credit.service.dto.request;

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
public class ClientPersonalDataRequestDto {
    @Size(max = 15, message = "Family name must be up to 15 characters long")
    @Pattern(regexp = "^[A-Za-zА-Яа-я]+$|^$", message = "Family name can only contain letters")
    private String familyName;

    @Size(max = 15, message = "Name must be up to 15 characters long")
    @Pattern(regexp = "^[A-Za-zА-Яа-я]+$|^$", message = "Name can only contain letters")
    private String name;

    @Size(max = 15, message = "Patronymic must be up to 15 characters long")
    @Pattern(regexp = "^[A-Za-zА-Яа-я]*$|^$", message = "Patronymic can only contain letters")
    private String patronymic;

    @Pattern(regexp = "^\\d{10}$|^$", message = "Passport details must contain exactly 10 digits or be empty")
    private String passportDetails;

    @Size(max = 11, message = "Contact number must contain 11 digits or be empty")
    @Pattern(regexp = "^\\d+$|^$", message = "Contact number must contain only digits")
    private String contactNumber;
}
