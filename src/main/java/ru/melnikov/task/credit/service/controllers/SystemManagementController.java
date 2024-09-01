package ru.melnikov.task.credit.service.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.melnikov.task.credit.service.dto.request.ClientPersonalDataRequestDto;
import ru.melnikov.task.credit.service.dto.response.ClientResponseDto;
import ru.melnikov.task.credit.service.dto.response.LoanAgreementResponseDto;
import ru.melnikov.task.credit.service.dto.response.LoanApplicationResponseDto;
import ru.melnikov.task.credit.service.services.SystemManagementService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SystemManagementController {

    private final SystemManagementService managementService;

    @GetMapping("/getAllClients")
    public String getAllClients(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                Model model) {
        List<ClientResponseDto> clients = managementService.getAllClients(page, size);
        long totalCount = managementService.getTotalClientsCount();

        model.addAttribute("clientList", clients);
        model.addAttribute("totalPages", (int) Math.ceil((double) totalCount / size));
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        return "display/AllClients";
    }

    @GetMapping("/getSearchForm")
    public String getSearchForm(Model model) {
        model.addAttribute("requestDto", new ClientPersonalDataRequestDto());
        return "forms/SearchForm";
    }

    @PostMapping("/findClient")
    public String findClient(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size,
                             @ModelAttribute("requestDto") @Valid ClientPersonalDataRequestDto requestDto,
                             BindingResult bindingResult,
                             Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            return "exceptions/ValidationErrorPage";
        }

        List<ClientResponseDto> clients = managementService.findClientByPersonalData(requestDto);

        if (clients.isEmpty()) return "display/UnsuccessfulSearchClient";
        model.addAttribute("clientList", clients);
        model.addAttribute("totalPages", (int) Math.ceil((double) clients.size() / size));
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        return "display/AllClients";
    }

    @GetMapping("/getAllAgreements")
    public String getAllAgreements(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   Model model) {
        List<LoanAgreementResponseDto> agreements = managementService.getAllAgreements(page, size);
        long totalCount = managementService.getTotalAgreementsCount();

        model.addAttribute("agreementList", agreements);
        model.addAttribute("totalPages", (int) Math.ceil((double) totalCount / size));
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        return "display/AllAgreements";
    }

    @GetMapping("/getAllApplications")
    public String getAllApplications(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     Model model) {
        List<LoanApplicationResponseDto> applications = managementService.getAllApplications(page, size);
        long totalCount = managementService.getTotalApplicationsCount();

        model.addAttribute("applicationList", applications);
        model.addAttribute("totalPages", (int) Math.ceil((double) totalCount / size));
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);

        return "display/AllApplications";
    }
}
