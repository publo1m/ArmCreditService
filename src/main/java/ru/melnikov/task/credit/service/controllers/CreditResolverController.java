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
import ru.melnikov.task.credit.service.dto.request.LoanApplicationRequestDto;
import ru.melnikov.task.credit.service.entities.LoanAgreement;
import ru.melnikov.task.credit.service.services.CreditResolverService;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class CreditResolverController {

    private final CreditResolverService creditResolverService;

    @GetMapping("/")
    public String getMainPage() {
        return "main/MainPage";
    }

    @GetMapping("/getApplicationForm")
    public String getApplicationForm(Model model) {
        model.addAttribute("requestDto", new LoanApplicationRequestDto());
        return "forms/ApplicationForm";
    }

    @PostMapping("/sendApplication")
    public String sendApplication(@ModelAttribute("requestDto") @Valid LoanApplicationRequestDto requestDto,
                                  BindingResult bindingResult,
                                  Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            return "exceptions/ValidationErrorPage";
        }

        LoanAgreement agreement = creditResolverService.createApplication(requestDto);
        model.addAttribute("loanAgreement", agreement);
        return "forms/ContractSignaturePage";
    }

    @PostMapping("/signAgreement")
    public String signAgreement(@RequestParam("agreementId") UUID agreementId) {
        creditResolverService.signAgreement(agreementId);
        return "main/MainPage";
    }

    @PostMapping("/declineAgreement")
    public String declineAgreement(@RequestParam("agreementId") UUID agreementId) {
        creditResolverService.declineAgreement(agreementId);
        return "main/MainPage";
    }
}
