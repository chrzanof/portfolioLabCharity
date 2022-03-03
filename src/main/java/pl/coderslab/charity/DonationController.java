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

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
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
    public String addDonation(HttpServletRequest request, Model model) {
        String pickUpComment = request.getParameter("pickUpComment");
        LocalTime pickUpTime =LocalTime.parse(request.getParameter("pickUpTime"));
        LocalDate pickUpDate = LocalDate.parse(request.getParameter("pickUpDate"));
        String zipCode = request.getParameter("zipCode");
        String city = request.getParameter("city");
        String street = request.getParameter("street");
        Integer quantity = Integer.parseInt(request.getParameter("quantity"));
        String categories = request.getParameter("cat");
        Long institutionId = Long.parseLong(request.getParameter("institutionId"));
        Institution institution = institutionRepository.findById(institutionId).get();
        List<String> ids = Arrays.asList(categories.split(" "));
        List<Category> categoryList = new ArrayList<>();
        for(String id: ids){
            categoryList.add(categoryRepository.findById(Long.parseLong(id)).get());
        }
        Donation donation = Donation.builder()
                .pickUpComment(pickUpComment)
                .pickUpDate(pickUpDate)
                .pickUpTime(pickUpTime)
                .zipCode(zipCode)
                .city(city)
                .street(street)
                .quantity(quantity)
                .institution(institution)
                .categories(categoryList)
                .build();
        donationRepository.save(donation);
        return "formConfirmation";
    }
}
