package pl.coderslab.charity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.charity.model.Category;
import pl.coderslab.charity.model.Donation;
import pl.coderslab.charity.model.Institution;
import pl.coderslab.charity.repository.CategoryRepository;
import pl.coderslab.charity.repository.DonationRepository;
import pl.coderslab.charity.repository.InstitutionRepository;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DonationController {
    private final DonationRepository donationRepository;
    private final CategoryRepository categoryRepository;
    private final InstitutionRepository institutionRepository;
    @GetMapping("/form")
    public String showForm(Model model) {
        List<Category> categories = categoryRepository.findAll();
        List<Institution> institutions = institutionRepository.findAll();
        model.addAttribute("donation",new Donation());
        model.addAttribute("institutions",institutions);
        model.addAttribute("categories",categories);
        return "form";
    }
    @PostMapping("/form")
    public String showSummary(Donation donation, Model model) {
        model.addAttribute("donation", donation);
        return "summary";
    }
    @PostMapping("form/confirmed")
    public String addDonation(Donation donation) {
        donationRepository.save(donation);

        return "formConfirmation";
    }
}
