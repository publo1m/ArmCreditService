package ru.melnikov.task.credit.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponseDto {
    String familyName;
    String name;
    String patronymic;
    String passportDetails;
    String contactNumber;
    String maritalStatus;
    String registration;
    String employmentDetails;
}
